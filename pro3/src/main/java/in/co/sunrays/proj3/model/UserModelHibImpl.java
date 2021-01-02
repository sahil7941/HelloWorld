package in.co.sunrays.proj3.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.util.EmailBuilder;
import in.co.sunrays.proj3.util.EmailMessage;
import in.co.sunrays.proj3.util.EmailUtility;
import in.co.sunrays.proj3.util.HibDataSource;

/**
 * Hibernate Implementation of UserModel
 * 
 * @author sahil khan
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class UserModelHibImpl implements UserModelInt {
	private static Logger log = Logger.getLogger(UserModelHibImpl.class);

	/**
	 * Add a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 * 
	 */
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		long pk = 0;
		UserDTO dtoExist = findByLogin(dto.getLogin());
		if (dtoExist != null) {
			throw new DuplicateRecordException("LoginId alredy exist");
		}

		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.save(dto);
			pk = dto.getId();
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception..", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in add user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
	 * Update a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update method started");
		Session session = null;
		Transaction transaction = null;
		UserDTO dtoExist = findByLogin(dto.getLogin());
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {
			throw new DuplicateRecordException("LoginID already exist");
		}
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.update(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in update user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model update method ended");
	}

	/**
	 * Delete a User
	 * 
	 * @param dto
	 * @throws DatabaseException
	 */
	public void delete(UserDTO dto) throws ApplicationException {
		log.debug("Model delete method started");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibDataSource.getSession();
			transaction = session.beginTransaction();
			session.delete(dto);
			transaction.commit();
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException("Exception in delete user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model delete mothod ended");
	}

	/**
	 * Find User by Login
	 * 
	 * @param login
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLogin method started");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (UserDTO) list.get(0);
			}
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception in getting user by Login" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model findByLogin method ended");
		return dto;
	}

	/**
	 * Find User by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return dto
	 * @throws DatabaseException
	 */
	public UserDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK method started");
		Session session = null;
		UserDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (UserDTO) session.get(UserDTO.class, pk);
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception in getting user by PK" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model finByPK method ended");
		return dto;
	}

	/**
	 * Search User
	 * 
	 * @param dto
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Search Users with pagination
	 * 
	 * @return list : List of Users
	 * @param dto
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search method started");
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (dto.getId() > 0) {
				criteria.add(Restrictions.eq("id", dto.getId()));
			}
			if (dto.getFname() != null && dto.getFname().length() > 0) {
				criteria.add(Restrictions.like("fname", dto.getFname() + "%"));
			}
			if (dto.getLname() != null && dto.getLname().length() > 0) {
				criteria.add(Restrictions.like("lname", dto.getLname() + "%"));
			}
			if (dto.getLogin() != null && dto.getLogin().length() > 0) {
				criteria.add(Restrictions.like("login", dto.getLogin() + "%"));
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				criteria.add(Restrictions.eq("dob", dto.getDob()));
			}
			if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
				criteria.add(Restrictions.eq("lastLogin", dto.getLastLogin()));
			}
			if (dto.getRoleId() > 0) {
				criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
			}
			if (dto.getUnSuccessfulLogin() > 0) {
				criteria.add(Restrictions.eq("unSuccessfulLogin", dto.getUnSuccessfulLogin()));
			}
			if (pageSize > 0) {
				criteria.setFirstResult((pageNo - 1) * pageSize);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception in search user" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model search method endeds");
		return list;
	}

	/**
	 * Get List of user
	 * 
	 * @return list : List of Users
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of User with pagination
	 * 
	 * @return list : List of Users
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
			Criteria criteria = session.createCriteria(UserDTO.class);
			if (pageSize > 0) {
				pageNo = pageNo - 1 * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} catch (HibernateException e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception in user list" + e.getMessage());
		} finally {
			session.close();
		}
		log.debug("Model list method ended");
		return list;
	}

	/**
	 * Change Password By pk
	 * 
	 * @param pk
	 *            ,oldPassword,newPassword : get parameter
	 * @return dto
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 */
	public boolean changePassword(Long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {
		log.debug("Model changePassword method started");
		boolean flag = false;
		UserDTO dtoExist = findByPK(id);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("Old password not matched");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Record not exist");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dtoExist.getLogin());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFname());
		map.put("lastName", dtoExist.getLname());

		String message = EmailBuilder.getChangePasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(dtoExist.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		log.debug("Model changePassword method ended");
		return flag;
	}

	/**
	 * User Authentication
	 * 
	 * @return dto : Contains User's information
	 * @param login
	 *            : User Login
	 * @param password
	 *            : User Password
	 * @throws ApplicationException
	 */
	public UserDTO authenticate(String login, String password) throws ApplicationException {
		log.debug("Model authenticate method started");
		Session session = HibDataSource.getSession();
		UserDTO dto = null;
		Query q = session.createQuery("from UserDTO where login=? and password=?");
		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (UserDTO) list.get(0);
		} else {
			dto = null;
		}
		log.debug("Model authenticate method ended");
		return dto;
	}

	/**
	 * Lock User for certain time duration
	 * 
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */
	public boolean lock(String login) throws RecordNotFoundException, ApplicationException {
		log.debug("Service lock started");
		boolean flag = false;
		UserDTO dtoExist = null;
		try {
			dtoExist = findByLogin(login);
			if (dtoExist != null) {
				dtoExist.setLocks(UserDTO.ACTIVE);
				update(dtoExist);
				flag = true;
			} else {
				throw new RecordNotFoundException("LoginId not exist");
			}
		} catch (DuplicateRecordException e) {
			log.error("Database Exception...", e);
			throw new ApplicationException("Exception in lock..." + e.getMessage());
		}
		log.debug("Service lock ended");
		return flag;
	}

	/**
	 * Get User Roles
	 * 
	 * @return List : User Role List
	 * @param dto
	 * @throws ApplicationException
	 */
	public List getRoles(UserDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Register a user
	 * 
	 * @param dto
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 *             : throws when user already exists
	 */
	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model registerUser method started");
		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());
		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();
		msg.setTo(dto.getLogin());
		msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return pk;
	}

	/**
	 * Reset Password of User with auto generated Password
	 * 
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */
	public boolean resetPassword(UserDTO dto) throws ApplicationException {
		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserDTO userData = findByPK(dto.getId());
		userData.setPassword(newPassword);
		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", dto.getLogin());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return true;
	}

	/**
	 * Send the password of User to his Email
	 * 
	 * @return boolean : true if success otherwise false
	 * @param login
	 *            : User Login
	 * @throws ApplicationException
	 * @throws RecordNotFoundException
	 *             : if user not found
	 */
	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		UserDTO userData = findByLogin(login);
		boolean flag = false;
		if (userData == null) {
			throw new RecordNotFoundException("EmailId does not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", userData.getLogin());
		map.put("password", userData.getPassword());
		map.put("firstName", userData.getFname());
		map.put("lastName", userData.getLname());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;
	}

}
