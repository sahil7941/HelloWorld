package in.co.sunrays.proj3.model;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of TimeTableModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class TimeTableModelHibImpl implements TimeTableModelInt{
	
	private static Logger log = Logger.getLogger(TimeTableModelHibImpl.class);

	/**
     * Add a TimeTable
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		Session session = null;
		Transaction transaction = null;
		
		TimeTableDTO dto1 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getExamDate());
		TimeTableDTO dto2 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getSubjectId());
		
		if(dto1!=null) {
			throw new DuplicateRecordException("Timetable already exist");
		}
		if(dto2!=null) {
			throw new DuplicateRecordException("Timetable already exist");
		}
		
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = sModel.findByPK(dto.getSubjectId());
		dto.setCourseName(courseDTO.getName());
		dto.setSubjectName(subjectDTO.getName());
		long pk=0;
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
			throw new ApplicationException("Exception in add timetable");
		}finally {
			session.close();
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a TimeTable
     * 
     * @param dto
     * @throws DatabaseException
     */
	
	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		
		TimeTableDTO dto1 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getExamDate());
		TimeTableDTO dto2 = findTimeTableDuplicacy(dto.getCourseId(), dto.getSemester(), dto.getSubjectId());
		if(dto1!=null && dto1.getId()!=dto.getId()) {
			throw new DuplicateRecordException("TimeTable already exist");
		}
		if(dto2!=null && dto2.getId()!=dto.getId()) {
			throw new DuplicateRecordException("TimeTable already exist");
		}
		
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = sModel.findByPK(dto.getSubjectId());
		dto.setCourseName(courseDTO.getName());
		dto.setSubjectName(subjectDTO.getName());
		
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
			throw new ApplicationException("Exception in update timetable"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a TimeTable
     * 
     * @param dto
     * @throws DatabaseException
     */
	
	public void delete(TimeTableDTO dto) throws ApplicationException {
		log.debug("Model delete method started");
		Session session = null;
		Transaction transaction = null;
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		}catch(HibernateException  e) {
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in delete timetable"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method started");
	}

	/**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
	
	public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, Date examDate) throws ApplicationException {
		log.debug("Model findTimeTableDuplicacy method started");
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		Query q = session.createQuery("from TimeTableDTO where courseId=?  and semester=? and examDate=?");
		q.setLong(0, courseId);
		q.setString(1, semester);
		q.setDate(2, examDate);
		List list = q.list();
		if(list.size()>0) {
			dto=(TimeTableDTO) list.get(0);
		}else {
			dto=null;
		}
		log.debug("Model findTimeTableDuplicacy method ended");
		return dto;
	}

	/**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
	
	public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, long subjectId) throws ApplicationException {
		log.debug("Model findTimeTableDuplicacy method started");
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		Query q = session.createQuery("from TimeTableDTO where courseId=?  and semester=? and subjectId=?");
		q.setLong(0, courseId);
		q.setString(1, semester);
		q.setLong(2,  subjectId);
		List list = q.list();
		if(list.size()>0) {
			dto=(TimeTableDTO) list.get(0);
		}else {
			dto=null;
		}
		log.debug("Model findTimeTableDuplicacy method ended");
		return dto;
	}

	/**
     * Find TimeTable by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		TimeTableDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(TimeTableDTO) list.get(0);
			}else {
				dto=null;
			}
		}catch(HibernateException e) {
			log.error("Database Exception",e);
			throw new ApplicationException("Exception in getting timetable by pk"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Search TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	
	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Session session = null;
		List list  = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			
			if(dto.getId()>0){
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getCourseId()>0) {
				criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
			}
			if(dto.getSubjectId()>0) {
				criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
			}
			if(dto.getCourseName()!=null && dto.getCourseName().length()>0) {
				criteria.add(Restrictions.like("courseName", dto.getCourseName()+"%"));
			}
			if(dto.getSubjectName()!=null && dto.getSubjectName().length()>0) {
				criteria.add(Restrictions.like("subjectName", dto.getSubjectName()+"%"));
			}
			if(dto.getSemester()!=null) {
				criteria.add(Restrictions.eq("semester", dto.getSemester()));
			}
			if(dto.getExamDate()!=null) {
				criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
			}
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in search timetable"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Search TimeTable
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	
	public List search(TimeTableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of TimeTable
     * 
     * @return list : List of TimeTable
     * @throws DatabaseException
     */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * get List of TimeTable with pagination
     * 
     * @return list : List of TimeTable
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
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in TimeTable list"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
