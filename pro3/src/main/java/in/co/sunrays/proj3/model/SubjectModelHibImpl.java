package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of SubjectModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class SubjectModelHibImpl implements SubjectModelInt{
	
	private static Logger log = Logger.getLogger(SubjectModelHibImpl.class);

	/**
     * Add a Subject
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		
		long pk=0;
		
		SubjectDTO dtoExist = findByName(dto.getName());
		if(dtoExist!=null) {
			throw new DuplicateRecordException("Subject already exist");
		}
		
		///CourseModelHibImpl cModel = new CourseModelHibImpl();
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());
		
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
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in add subject"+e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a Subject
     * 
     * @param dto
     * @throws DatabaseException
     */
	
	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction  transaction = null;
		
		SubjectDTO duplicateSubject = findByName(dto.getName());
		if(duplicateSubject!=null && duplicateSubject.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Subject name already exist");
		}
		
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.update(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Ecxeption...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in update subject"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a Subject
     * 
     * @param dto
     * @throws DatabaseException
     */
	
	public void delete(SubjectDTO dto) throws ApplicationException {
		log.debug("Model delete method started");
		Session session=null;
		Transaction transaction=null;
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
			throw new ApplicationException("Exception in delete subject"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method ended");
	}

	/**
     * Find Subject by Name
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public SubjectDTO findByName(String name) throws ApplicationException {
		log.debug("Model findByName method started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(SubjectDTO) list.get(0);
			}else {
				dto=null;
			}
		}catch(HibernateException e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Exception in getting subject by name"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByName method ended");
		return dto;
	}

	/**
     * Find Subject by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public SubjectDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		SubjectDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(SubjectDTO) list.get(0);
			}else {
				dto=null;
			}
		}catch(HibernateException e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Exception in getting subject by pk"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Searches Subject with pagination
     * 
     * @return list : List of Subject
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	
	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null && dto.getName().length()>0) {
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0) {
				criteria.add(Restrictions.like("description", dto.getDescription()+"%"));
			}
			if(dto.getCourseId()>0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if(dto.getCourseName()!=null && dto.getCourseName().length()>0) {
				criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
			}
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in search Subject"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Searches Subject
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	
	public List search(SubjectDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of Subject
     * 
     * @return list : List of Subject
     * @throws DatabaseException
     */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * get List of Subject with pagination
     * 
     * @return list : List of Subject
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
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.debug("DataBase Exception...",e);
			throw new ApplicationException("Exception in Subject list"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
