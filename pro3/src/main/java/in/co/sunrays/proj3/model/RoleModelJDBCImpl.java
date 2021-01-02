package in.co.sunrays.proj3.model;

import  in.co.sunrays.proj3.dto.RoleDTO;
import  in.co.sunrays.proj3.exception.ApplicationException;
import  in.co.sunrays.proj3.exception.DatabaseException;
import  in.co.sunrays.proj3.exception.DuplicateRecordException;
import  in.co.sunrays.proj3.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * JDBC Implementation of Role Model
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class RoleModelJDBCImpl implements RoleModelInt {

    private static Logger log = Logger.getLogger(RoleModelJDBCImpl.class);

    /**
     * Find next PK of Role
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
                    .prepareStatement("SELECT MAX(ID) FROM ROLEDTO");
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
     * Add a Role
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
    public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException{
        log.debug("Model add Started");
        Connection conn = null;
        int pk = 0;
        
        RoleDTO duplicataRole = findByName(dto.getName());
        // Check if create Role already exist
        if (duplicataRole != null) {
            throw new DuplicateRecordException("Role already exists");
        }
        
        try {
            conn = JDBCDataSource.getConnection();
            pk = nextPK();
            // Get auto-generated next primary key
            System.out.println(pk + " in ModelJDBC");
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO ROLEDTO VALUES(?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, dto.getCreatedBy());
            pstmt.setString(3, dto.getModifiedBy());
            pstmt.setTimestamp(4, dto.getCreatedDateTime());
            pstmt.setTimestamp(5, dto.getModifiedDateTime());
            pstmt.setString(6, dto.getName());
            pstmt.setString(7, dto.getDescription());
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
                        "Exception : add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a Role
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void delete(RoleDTO dto) throws ApplicationException {
        log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM ROLEDTO WHERE ID=?");
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
                    "Exception : Exception in delete Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete Started");
    }

    /**
     * Find User by Role
     * 
     * @param name
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
    public RoleDTO findByName(String name) throws ApplicationException {
        log.debug("Model findBy EmailId Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ROLEDTO WHERE NAME=?");
        RoleDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new RoleDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));

            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting User by emailId");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findBy EmailId End");
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
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ROLEDTO WHERE ID=?");
        RoleDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new RoleDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
            }
            rs.close();
        } catch (Exception e) {
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
     * Update a Role
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void update(RoleDTO dto) throws ApplicationException,
            DuplicateRecordException {
        log.debug("Model update Started");
        Connection conn = null;
        
        RoleDTO duplicataRole = findByName(dto.getName());
        // Check if updated Role already exist
        if (duplicataRole != null && duplicataRole.getId() != dto.getId()) {
            throw new DuplicateRecordException("Role already exists");
        }
        try {
            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE ROLEDTO SET NAME=?,DESCRIPTION=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getDescription());
            pstmt.setString(3, dto.getCreatedBy());
            pstmt.setString(4, dto.getModifiedBy());
            pstmt.setTimestamp(5, dto.getCreatedDateTime());
            pstmt.setTimestamp(6, dto.getModifiedDateTime());
            pstmt.setLong(7, dto.getId());
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
            throw new ApplicationException("Exception in updating Role ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
    }

    /**
     * Search Role
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
    public List search(RoleDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    /**
     * Search Role with pagination
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
    public List search(RoleDTO dto, int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM ROLEDTO WHERE 1=1");

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" AND id = " + dto.getId());
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                sql.append(" AND NAME like '" + dto.getName() + "%'");
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                sql.append(" AND DESCRIPTION like '" + dto.getDescription() + "%'");
            }

        }

        // if page size is greater than zero then apply pagination
        if (pageSize > 0) {
            // Calculate start record index
            pageNo = (pageNo - 1) * pageSize;

            sql.append(" Limit " + pageNo + ", " + pageSize);
            // sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList list = new ArrayList();
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new RoleDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
    }

    /**
     * Get List of Role
     * 
     * @return list : List of Role
     * @throws DatabaseException
     */
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * Get List of Role with pagination
     * 
     * @return list : List of Role
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException {
        log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from ROLEDTO");
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
                RoleDTO dto = new RoleDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of Role");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model list End");
        return list;

    }
}