package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

import sun.print.resources.serviceui_es;

/**
 * Hibernate Implementation of CourseModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class CourseModelHibImpl implements CourseModelInt{
	private static Logger log = Logger.getLogger(CourseModelHibImpl.class);

	/**
     * Add a Course
     * 
     * @param dto
     * @throws DatabaseException
     * @throws DuplicateRecordException
     */
	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		long pk=0;
		CourseDTO dtoExist=findByName(dto.getName());
		if(dtoExist!=null) {
			throw new DuplicateRecordException("Course name already exist");
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
			log.error("Database Exception...",e);
			if(transaction==null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in add course"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model add method deleted");
		return pk;
	}

	/**
     * Update a Course
     * 
     * @param dto
     * @throws DatabaseException
     * @throws DuplicateRecordException
     */
	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		CourseDTO dtoExist = findByName(dto.getName());
		if(dtoExist!=null) {
			throw new DuplicateRecordException("Course name already exist");
		}
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.update(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Exception in update course"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a Course
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void delete(CourseDTO dto) throws ApplicationException {
		log.debug("Model delete method ended");
		Session session = null;
		Transaction transaction = null;
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		}catch(HibernateException e) {
			e.printStackTrace();
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in delete coourse"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method ended");
	}

	/**
     * Find Course by Name
     * 
     * @param collage
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CourseDTO findByName(String name) throws ApplicationException {
		log.debug("Model findByName method started");
		Session session = null;
		CourseDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("Name", name));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(CourseDTO) list.get(0);
			}
		}catch(HibernateException e) {
			e.printStackTrace();
			log.error("Database  Exception...",e);
			throw new ApplicationException("Exception in getting course by name"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByName method ended");
		return dto;
	}

	/**
     * Find Course by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CourseDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		CourseDTO dto = null;
		try {
			session=HibDataSource.getSession();
			dto=(CourseDTO) session.get(CourseDTO.class, pk);
		}catch(HibernateException e){
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in getting course by PK"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Searches Course with pagination
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
	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria=session.createCriteria(CourseDTO.class);
			
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null && dto.getName().length()>0) {
				criteria.add(Restrictions.like("Name", dto.getName()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0) {
				criteria.add(Restrictions.like("description", dto.getDescription()+"%"));
			}
			if(dto.getDuration()!=null && dto.getDuration().length()>0) {
				criteria.add(Restrictions.like("duration", dto.getDuration()+"%"));
			}
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in search course"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Search Course
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	public List search(CourseDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of Course
     * 
     * @return list : List of College
     * @throws DatabaseException
     */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * get List of Course with pagination
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
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in course list"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
