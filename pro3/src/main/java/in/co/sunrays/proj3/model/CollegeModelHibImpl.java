package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

//import com.fasterxml.jackson.databind.deser.impl.SetterlessProperty;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of CollegeModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class CollegeModelHibImpl implements CollegeModelInt{
	private static Logger log = Logger.getLogger(CollegeModelHibImpl.class);

	/**
     * Add a College
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		long pk = 0;
		CollegeDTO duplicateCollegeName = findByName(dto.getName());
		if(duplicateCollegeName!=null) {
			throw new DuplicateRecordException("College name already exist");
		}
		
		Session session = null;
		Transaction transaction = null;
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception..",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in add college"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a Collage
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		CollegeDTO dtoExist = findByName(dto.getName());
		if(dtoExist!=null && dtoExist.getId()!=dto.getId()) {
			throw new DuplicateRecordException("College is already exist");
		}
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.update(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in update college"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a College
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void delete(CollegeDTO dto) throws ApplicationException {
		log.debug("Model delete method started");
		Session session = null;
		Transaction transaction = null;
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in delete college"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method ended");
	}

	/**
     * Find College by Name
     * 
     * @param collage
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CollegeDTO findByName(String name) throws ApplicationException {
		log.debug("Model findByName method started");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if(list.size()>0) {
				dto=(CollegeDTO) list.get(0);
			}
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in getting college by name"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByName method ended");
		return dto;
	}

	/**
     * Find Collage by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CollegeDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		CollegeDTO dto = null;
		try {
			session=HibDataSource.getSession();
			dto=(CollegeDTO) session.get(CollegeDTO.class, pk);
		}catch(HibernateException e) {
			log.error("DataBase Exception...",e);
			throw new ApplicationException("Exception in getting college by PK"+e.getMessage());
		}finally{
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Searches Colleges with pagination
     * 
     * @return list : List of Colleges
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
            Criteria criteria = session.createCriteria(CollegeDTO.class);

            if (dto.getId() > 0) {
                criteria.add(Restrictions.eq("id", dto.getId()));
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                criteria.add(Restrictions.like("name", dto.getName() + "%"));
            }
            if (dto.getAddress() != null && dto.getAddress().length() > 0) {
                criteria.add(Restrictions.like("address", dto.getAddress()+"%"));
            }
            if (dto.getState() != null && dto.getState().length() > 0) {
                criteria.add(Restrictions.like("state", dto.getState() + "%"));
            }
            if (dto.getCity() != null && dto.getCity().length() > 0) {
                criteria.add(Restrictions.like("city", dto.getCity() + "%"));
            }
            if (pageSize > 0) {
                criteria.setFirstResult(((pageNo - 1) * pageSize));
                criteria.setMaxResults(pageSize);
            }
            list = criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in search college"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Search College
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	public List search(CollegeDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of College
     * 
     * @return list : List of College
     * @throws DatabaseException
     */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * get List of College with pagination
     * 
     * @return list : List of College
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list method started");
		Session session = null;
		List list = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in college list"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
