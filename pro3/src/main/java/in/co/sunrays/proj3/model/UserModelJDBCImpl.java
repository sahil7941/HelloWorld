package in.co.sunrays.proj3.model;

import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.util.EmailBuilder;
import in.co.sunrays.proj3.util.EmailMessage;
import in.co.sunrays.proj3.util.EmailUtility;
import in.co.sunrays.proj3.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * JDBC Implementation of UserModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class UserModelJDBCImpl implements UserModelInt {
    private static Logger log = Logger.getLogger(UserModelJDBCImpl.class);

    /**
     * Find next PK of user
     * 
     * @throws DatabaseException
     */
    public Integer nextPK() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT MAX(ID) FROM USERDTO");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model nextPK End");
        return pk + 1;
    }

    /**
     * Add a User
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
    public long add(UserDTO dto) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model add Started");
        Connection conn = null;
        int pk = 0;

        UserDTO existDto = findByLogin(dto.getLogin());

        if (existDto != null) {
            throw new DuplicateRecordException("Login Id already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO USERDTO VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, dto.getCreatedBy());
            pstmt.setString(3, dto.getModifiedBy());
            pstmt.setTimestamp(4,dto.getCreatedDateTime());
            pstmt.setTimestamp(5,dto.getModifiedDateTime());
            pstmt.setString(6, dto.getFname());
            pstmt.setString(7, dto.getLname());
            pstmt.setString(8, dto.getLogin());
            pstmt.setString(9, dto.getPassword());
            pstmt.setDate(10, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(11, dto.getMobileNo());
            pstmt.setLong(12, dto.getRoleId());
            pstmt.setInt(13, dto.getUnSuccessfulLogin());
            pstmt.setString(14, dto.getGender());
            pstmt.setTimestamp(15, dto.getLastLogin());
            pstmt.setString(16, dto.getLocks());
            pstmt.setString(17, dto.getRegistredIP());
            pstmt.setString(18, dto.getLastLoginIP());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ApplicationException(
                        "Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add User");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a User
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void delete(UserDTO dto) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM USERDTO WHERE ID=?");
            pstmt.setLong(1, dto.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();

        } catch (Exception e) {
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException(
                    "Exception : Exception in delete User");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete Started");
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
        log.debug("Model findByLogin Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM USERDTO WHERE LOGIN=?");
        UserDTO dto = null;
        Connection conn = null;
        System.out.println("sql" + sql);

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by login");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByLogin End");
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
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM USERDTO WHERE ID=?");
        UserDTO dto = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    /**
     * Update a user
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void update(UserDTO dto) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;

        UserDTO dtoExist = findByLogin(dto.getLogin());
        // Check if updated LoginId already exist
        if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
            throw new DuplicateRecordException("LoginId is already exist");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE USERDTO SET FNAME=?,LNAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILENO=?,ROLEID=?,UNSUCCESSFULLOGIN=?,GENDER=?,LASTLOGIN=?,LOCKS=?,REGISTREDIP=?,LASTLOGINIP=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
            pstmt.setString(1, dto.getFname());
            pstmt.setString(2, dto.getLname());
            pstmt.setString(3, dto.getLogin());
            pstmt.setString(4, dto.getPassword());
            pstmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
            pstmt.setString(6, dto.getMobileNo());
            pstmt.setLong(7, dto.getRoleId());
            pstmt.setInt(8, dto.getUnSuccessfulLogin());
            pstmt.setString(9, dto.getGender());
            pstmt.setTimestamp(10, dto.getLastLogin());
            pstmt.setString(11, dto.getLocks());
            pstmt.setString(12, dto.getRegistredIP());
            pstmt.setString(13, dto.getLastLoginIP());
            pstmt.setString(14, dto.getCreatedBy());
            pstmt.setString(15, dto.getModifiedBy());
            pstmt.setTimestamp(16,dto.getCreatedDateTime());
            pstmt.setTimestamp(17,dto.getModifiedDateTime());
            pstmt.setLong(18, dto.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Database Exception..", e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(
                        "Exception : Delete rollback exception "
                                + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating User ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
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
     * Search User with pagination
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
    public List search(UserDTO dto, int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM USERDTO WHERE 1=1");

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" AND ID = " + dto.getId());
            }
            if (dto.getFname() != null && dto.getFname().length() > 0) {
                sql.append(" AND FNAME like '" + dto.getFname() + "%'");
            }
            if (dto.getLname() != null && dto.getLname().length() > 0) {
                sql.append(" AND LNAME like '" + dto.getLname() + "%'");
            }
            if (dto.getLogin() != null && dto.getLogin().length() > 0) {
                sql.append(" AND LOGIN like '" + dto.getLogin() + "%'");
            }
            if (dto.getPassword() != null && dto.getPassword().length() > 0) {
                sql.append(" AND PASSWORD like '" + dto.getPassword() + "%'");
            }
            if (dto.getDob() != null && dto.getDob().getDate() > 0) {
                sql.append(" AND DOB = " + dto.getGender());
            }
            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
                sql.append(" AND MOBILENO = " + dto.getMobileNo());
            }
            if (dto.getRoleId() > 0) {
                sql.append(" AND ROLEID = " + dto.getRoleId());
            }
            if (dto.getUnSuccessfulLogin() > 0) {
                sql.append(" AND UNSUCCESSFULLOGIN = "
                        + dto.getUnSuccessfulLogin());
            }
            if (dto.getGender() != null && dto.getGender().length() > 0) {
                sql.append(" AND GENDER like '" + dto.getGender() + "%'");
            }
            if (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
                sql.append(" AND LASTLOGIN = " + dto.getLastLogin());
            }
            if (dto.getRegistredIP() != null && dto.getRegistredIP().length() > 0) {
                sql.append(" AND REGISTREDIP like '" + dto.getRegistredIP() + "%'");
            }
            if (dto.getLastLoginIP() != null && dto.getLastLoginIP().length() > 0) {
                sql.append(" AND LASTLOGINIP like '" + dto.getLastLoginIP() + "%'");
            }

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        System.out.println(sql);
        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search user");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
    }

    /**
     * Get List of User
     * 
     * @return list : List of User
     * @throws DatabaseException
     */
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of User with pagination
     * 
     * @return list : List of users
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from USERDTO");
        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of users");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model list End");
        return list;

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
    public UserDTO authenticate(String login, String password)
            throws ApplicationException {
        log.debug("Model authenticate Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM USERDTO WHERE LOGIN = ? AND PASSWORD = ?");
        UserDTO dto = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

            }
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in get roles");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model authenticate End");
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
    public boolean lock(String login) throws RecordNotFoundException,
            ApplicationException {
        log.debug("Service lock Started");
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
            log.error("Application Exception..", e);
            throw new ApplicationException("Database Exception");
        }
        log.debug("Service lock End");
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
        log.debug("Model get roles Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM USERDTO WHERE ROLEID=?");
        Connection conn = null;
        List list = new ArrayList();
        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, dto.getRoleId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new UserDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setFname(rs.getString(6));
                dto.setLname(rs.getString(7));
                dto.setLogin(rs.getString(8));
                dto.setPassword(rs.getString(9));
                dto.setDob(rs.getDate(10));
                dto.setMobileNo(rs.getString(11));
                dto.setRoleId(rs.getLong(12));
                dto.setUnSuccessfulLogin(rs.getInt(13));
                dto.setGender(rs.getString(14));
                dto.setLastLogin(rs.getTimestamp(15));
                dto.setLocks(rs.getString(16));
                dto.setRegistredIP(rs.getString(17));
                dto.setLastLoginIP(rs.getString(18));

                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException("Exception : Exception in get roles");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model get roles End");
        return list;
    }

    /**
     * @param id
     *            : long id
     * @param old
     *            password : String oldPassword
     * @param newpassword
     *            : String newPassword
     * @throws DatabaseException
     */
    public boolean changePassword(Long id, String oldPassword,
            String newPassword) throws RecordNotFoundException,
            ApplicationException {

        log.debug("model changePassword Started");
        boolean flag = false;
        UserDTO dtoExist = null;

        dtoExist = findByPK(id);
        if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
            dtoExist.setPassword(newPassword);
            try {
                update(dtoExist);
            } catch (DuplicateRecordException e) {
                log.error(e);
                throw new ApplicationException("LoginId is already exist");
            }
            flag = true;
        } else {
            throw new RecordNotFoundException("Login not exist");
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

        log.debug("Model changePassword End");
        return flag;

    }

    public UserDTO updateAccess(UserDTO dto) throws ApplicationException {
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
    public long registerUser(UserDTO dto) throws ApplicationException,
            DuplicateRecordException {

        log.debug("Model add Started");

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

        String newPassword = String.valueOf(new Date().getTime()).substring(0,
                4);
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
        map.put("firstName", dto.getFname());
        map.put("lastName", dto.getLname());

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
    public boolean forgetPassword(String login) throws ApplicationException,
            RecordNotFoundException {
        UserDTO userData = findByLogin(login);
        boolean flag = false;

        if (userData == null) {
            throw new RecordNotFoundException("Email Id Does not matched.");

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