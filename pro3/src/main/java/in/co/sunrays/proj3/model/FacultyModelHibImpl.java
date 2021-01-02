package in.co.sunrays.proj3.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of FacultyModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class FacultyModelHibImpl implements FacultyModelInt{
	
	private static Logger log = Logger.getLogger(FacultyModelHibImpl.class);

	/**
     * Add a Faculty
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		Session session = null;
		Transaction transaction = null;
		long pk=0;
		
		FacultyDTO dtoExist = findByEmailId(dto.getEmailId());
		if(dtoExist!=null) {
			throw new DuplicateRecordException("EmailId already exist");
		}
		
		CollegeModelInt cModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegedto = cModel.findByPK(dto.getCollegeId());
		
		CourseModelInt coModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO coursedto = coModel.findByPK(dto.getCourseId());
		
		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		
		dto.setCollegeName(collegedto.getName());
		dto.setCourseName(coursedto.getName());
		dto.setSubjectName(subjectdto.getName());
		
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
			throw new ApplicationException("Exception in add faculty"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a Faculty
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		
		FacultyDTO dtoExist = findByEmailId(dto.getEmailId());
		if(dtoExist!=null && dtoExist.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Email id already exist");
		}
		
		CollegeModelInt cModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegedto = cModel.findByPK(dto.getCollegeId());
		
		CourseModelInt coModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO coursedto = coModel.findByPK(dto.getCourseId());
		
		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectdto = sModel.findByPK(dto.getSubjectId());
		
		dto.setCollegeName(collegedto.getName());
		dto.setCourseName(coursedto.getName());
		dto.setSubjectName(subjectdto.getName());
		
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.update(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in update faculty"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a Faculty
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void delete(FacultyDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in delete Faculty"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method ended");
	}

	/**
     * Find Faculty by email
     * 
     *  by Login
     * 
     * @param login
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		log.debug("Model findByEmailId method started");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("emailId", emailId));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(FacultyDTO) list.get(0);
			}
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in getting faculty by email"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByEmailId method ended");
		return dto;
	}

	/**
     * Find Faculty by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public FacultyDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		FacultyDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(FacultyDTO) list.get(0);
			}
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in getting faculty by pk"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Searches Faculty with pagination
     * 
     * @return list : List of Faculty
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getCollegeId()>0) {
				criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
			}
			if(dto.getCourseId()>0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if(dto.getSubjectId()>0) {
				criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}
			if(dto.getFirstName()!=null && dto.getFirstName().length()>0) {
				criteria.add(Restrictions.like("firstName", dto.getFirstName()+"%"));
			}
			if(dto.getLastName()!=null && dto.getLastName().length()>0) {
				criteria.add(Restrictions.like("lastName", dto.getLastName()+"%"));
			}
			if(dto.getCourseName()!=null && dto.getCourseName().length()>0) {
				criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
			}
			if(dto.getSubjectName()!=null && dto.getSubjectName().length()>0) {
				criteria.add(Restrictions.like("subjectName", dto.getSubjectName()+"%"));
			}
			if(dto.getCollegeName()!=null && dto.getCollegeName().length()>0) {
				criteria.add(Restrictions.like("collegeName", dto.getCollegeName()+"%"));
			}
			if(dto.getEmailId()!=null && dto.getEmailId().length()>0) {
				criteria.add(Restrictions.like("emailId", dto.getEmailId()+"%"));
			}
			if(pageSize>0) {
				criteria.setFirstResult((pageNo-1)*pageSize);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in faculty search"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Search Faculty
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of Faculty
     * 
     * @return list : List of Faculty
     * @throws DatabaseException
     */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	
	/**
     * get List of Faculty with pagination
     * 
     * @return list : List of Faculty
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
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in faculty list"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
