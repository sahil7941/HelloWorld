package in.co.sunrays.proj3.model;

import java.util.List;

import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.HibDataSource;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import  in.co.sunrays.proj3.model.RoleModelInt;

/**
 * Hibernate Implementation of RoleModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class RoleModelHibImpl implements RoleModelInt {
	
	private static Logger log = Logger.getLogger(RoleModelHibImpl.class);

	/**
     * Add a Role
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		long pk=0;
		
		RoleDTO duplicateRole = findByName(dto.getName());
		if(duplicateRole!=null) {
			throw new DuplicateRecordException("Role already exist");
		}
		Session session = HibDataSource.getSession();
		Transaction transaction = null;
		try {
			transaction=session.beginTransaction();
			session.save(dto);
			pk=dto.getId();
			transaction.commit();
		}catch(HibernateException e) {
			e.printStackTrace();
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in add user"+e.getMessage());
		}finally {
			if(session!=null) {
				session.close();
			}
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a Role
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		
		RoleDTO duplicateRole = findByName(dto.getName());
		if(duplicateRole!=null && duplicateRole.getId()!=dto.getId()) {
			throw new DuplicateRecordException("Role already Exist");
		}
		try {
			session=HibDataSource.getSession();
			transaction=session.beginTransaction();
			session.update(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("DataBase Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in Role update"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
     * Delete a Role
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void delete(RoleDTO dto) throws ApplicationException {
		log.debug("Model delete method started while an application is started");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			if(transaction!=null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in role delete"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model delete method ended");
	}

	/**
     * Find Role by Name
     * 
     * @param name
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public RoleDTO findByName(String name) throws ApplicationException {
		log.debug("Model findByName method started");
		Session session = null;
		RoleDTO dto = null;
		try {
			session=HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("name", name));
			List list  = criteria.list();
			if(list.size()==1) {
				dto=(RoleDTO) list.get(0);
			}
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in get role by name"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByName method ended");
		return dto;
	}

	/**
     * Find Role by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public RoleDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		RoleDTO dto = null;
		Session session = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
			if(list.size()==1) {
				dto=(RoleDTO) list.get(0);
			}else {
				dto=null;
			}
		}catch(HibernateException e) {
			log.error(e);
			throw new ApplicationException("Exception in getting role by pk"+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model findByPK method ended");
		return dto;
	}

	/**
     * Searches Roles with pagination
     * 
     * @return list : List of Roles
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if(dto.getId()>0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if(dto.getName()!=null && dto.getName().length()>0) {
				criteria.add(Restrictions.like("name", dto.getName()+"%"));
			}
			if(dto.getDescription()!=null && dto.getDescription().length()>0) {
				criteria.add(Restrictions.like("description", dto.getDescription()+"%"));
			}
			if(pageSize>0) {
				criteria.setFirstResult(((pageNo-1)*pageSize));
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception...",e);
			throw new ApplicationException("Exception in search role.."+e.getMessage());
		}finally {
			session.close();
		}
		log.debug("Model search method ended");
		return list;
	}

	/**
     * Searches Role
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	public List search(RoleDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Gets List of Role
     * 
     * @return list : List of Roles
     * @throws DatabaseException
     */
	public List list() throws ApplicationException {
		return list(0,0);
	}

	/**
     * get List of Role with pagination
     * 
     * @return list : List of Roles
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
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(RoleDTO.class);
			if(pageSize>0) {
				pageNo=(pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list=criteria.list();
		}catch(HibernateException e) {
			log.error("Database Exception....",e);
			throw new ApplicationException("Exception in Role list");
		}finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

}
