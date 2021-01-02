package in.co.sunrays.proj3.model;

import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * JDBC Implementation of Marksheet Model
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class MarksheetModelJDBCImpl implements MarksheetModelInt {

    Logger log = Logger.getLogger(MarksheetModelJDBCImpl.class);

    /**
     * Find next PK of Marksheet
     * 
     * @throws DatabaseException
     */
    public Integer nextPK() throws DatabaseException {
        log.debug("Model nextPK Started");
        Connection conn = null;
        int pk = 0;
        try {
            conn = JDBCDataSource.getConnection();
            System.out.println("Connection Succesfully Establish");

            PreparedStatement pstmt = conn
                    .prepareStatement("select max(ID) from MARKSHEETDTO");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pk = rs.getInt(1);
            }
            rs.close();

        } catch (Exception e) {
            log.error(e);
            throw new DatabaseException("Exception in Marksheet getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model nextPK End");
        return pk + 1;
    }

    /**
     * Add a Marksheet
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */

    public long add(MarksheetDTO dto) throws ApplicationException,
            DuplicateRecordException {

        log.debug("Model add Started");

        Connection conn = null;

        // get Student Name
        StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
        StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
        dto.setName(studentDTO.getFname() + " " + studentDTO.getLname());

        MarksheetDTO duplicateMarksheet = findByRollNo(dto.getRollNo());
        int pk = 0;

        if (duplicateMarksheet != null) {
            throw new DuplicateRecordException("Roll Number already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();

            // Get auto-generated next primary key
            pk = nextPK();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("INSERT INTO MARKSHEETDTO VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, pk);
            pstmt.setString(2, dto.getCreatedBy());
            pstmt.setString(3, dto.getModifiedBy());
            pstmt.setTimestamp(14, dto.getCreatedDateTime());
            pstmt.setTimestamp(5, dto.getModifiedDateTime());
            pstmt.setString(6, dto.getRollNo());
            pstmt.setLong(7, dto.getStudentId());
            pstmt.setString(8, dto.getName());
            pstmt.setInt(9, dto.getPhysics());
            pstmt.setInt(10, dto.getChemistry());
            pstmt.setInt(11, dto.getMaths());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            log.error(e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("add rollback exception "
                        + ex.getMessage());
            }
            throw new ApplicationException("Exception in add marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model add End");
        return pk;
    }

    /**
     * Delete a Marksheet
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void delete(MarksheetDTO dto) throws ApplicationException {

        log.debug("Model delete Started");

        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM MARKSHEETDTO WHERE ID=?");
            pstmt.setLong(1, dto.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();

        } catch (Exception e) {
            log.error(e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                log.error(ex);
                throw new ApplicationException("Delete rollback exception "
                        + ex.getMessage());
            }
            throw new ApplicationException("Exception in delete marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model delete End");
    }

    /**
     * Find Marksheet by Roll No
     * 
     * @param rollNo
     *            : get parameter
     * @return dto
     * @throws DuplicateRecordException
     */
    public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
        log.debug("Model findByRollNo Started");

        StringBuffer sql = new StringBuffer(
                "SELECT * FROM MARKSHEETDTO WHERE ROLL_NO=?");
        MarksheetDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, rollNo);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setRollNo(rs.getString(6));
                dto.setStudentId(rs.getLong(7));
                dto.setName(rs.getString(8));
                dto.setPhysics(rs.getInt(9));
                dto.setChemistry(rs.getInt(10));
                dto.setMaths(rs.getInt(11));
            }
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(
                    "Exception in getting marksheet by roll no");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model findByRollNo End");
        return dto;
    }

    /**
     * Find Marksheet by PK
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */

    public MarksheetDTO findByPK(long pk) throws ApplicationException {
        log.debug("Model findByPK Started");

        StringBuffer sql = new StringBuffer(
                "SELECT * FROM MARKSHEETDTO WHERE ID=?");
        MarksheetDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setRollNo(rs.getString(6));
                dto.setStudentId(rs.getLong(7));
                dto.setName(rs.getString(8));
                dto.setPhysics(rs.getInt(9));
                dto.setChemistry(rs.getInt(10));
                dto.setMaths(rs.getInt(11));

            }
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(
                    "Exception in getting marksheet by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
    }

    /**
     * Update a Marksheet
     * 
     * @param dto
     * @throws DatabaseException
     */
    public void update(MarksheetDTO dto) throws ApplicationException,
            DuplicateRecordException {

        log.debug("Model update Started");

        Connection conn = null;
        MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());

        // Check if updated Roll no already exist
        if (dtoExist != null && dtoExist.getId() != dto.getId()) {
            throw new DuplicateRecordException("Roll No is already exist");
        }

        // get Student Name
        StudentModelInt sModel = ModelFactory.getInstance().getStudentModel();
        StudentDTO studentDTO = sModel.findByPK(dto.getStudentId());
        dto.setName(studentDTO.getFname() + " " + studentDTO.getLname());

        try {
            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("UPDATE MARKSHEETDTO SET ROLLNO=?,STUDENTID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
            pstmt.setString(1, dto.getRollNo());
            pstmt.setLong(2, dto.getStudentId());
            pstmt.setString(3, dto.getName());
            pstmt.setInt(4, dto.getPhysics());
            pstmt.setInt(5, dto.getChemistry());
            pstmt.setInt(6, dto.getMaths());
            pstmt.setString(7, dto.getCreatedBy());
            pstmt.setString(8, dto.getModifiedBy());
            pstmt.setTimestamp(9, dto.getCreatedDateTime());
            pstmt.setTimestamp(10, dto.getModifiedDateTime());
            pstmt.setLong(11, dto.getId());
            pstmt.executeUpdate();
            conn.commit(); // End transaction
            pstmt.close();
        } catch (Exception e) {
            log.error(e);
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception "
                        + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Marksheet ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model update End");

    }

    /**
     * Search Marksheet
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
    public List search(MarksheetDTO dto) throws ApplicationException {
        return search(dto, 0, 0);
    }

    /**
     * Search Marksheet with pagination
     * 
     * @return list : List of Marksheets
     * @param dto
     *            : Search Parameters
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * 
     * @throws DatabaseException
     */
    public List search(MarksheetDTO dto, int pageNo, int pageSize)
            throws ApplicationException {

        log.debug("Model  search Started");

        StringBuffer sql = new StringBuffer(
                "select * from MARKSHEETDTO where true");

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" AND id = " + dto.getId());
            }
            if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
                sql.append(" AND rollno like '" + dto.getRollNo() + "%'");
                System.out.println("sql" + sql);
            }
            if (dto.getName() != null && dto.getName().length() > 0) {
                sql.append(" AND name like '" + dto.getName() + "%'");
                System.out.println("sql" + sql);
            }
            if (dto.getPhysics() != null && dto.getPhysics() > 0) {
                sql.append(" AND physics = " + dto.getPhysics());
            }
            if (dto.getChemistry() != null && dto.getChemistry() > 0) {
                sql.append(" AND chemistry = " + dto.getChemistry());
            }
            if (dto.getMaths() != null && dto.getMaths() > 0) {
                sql.append(" AND maths = '" + dto.getMaths());
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
                dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setRollNo(rs.getString(6));
                dto.setStudentId(rs.getLong(7));
                dto.setName(rs.getString(8));
                dto.setPhysics(rs.getInt(9));
                dto.setChemistry(rs.getInt(10));
                dto.setMaths(rs.getInt(11));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException("Update rollback exception "
                    + e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model  search End");
        return list;
    }

    /**
     * Get List of Marksheet
     * 
     * @return list : List of Marksheets
     * @throws DatabaseException
     */
    public List list() throws ApplicationException {
        return list(0, 0);
    }

    /**
     * get List of Marksheet with pagination
     * 
     * @return list : List of Marksheets
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
    public List list(int pageNo, int pageSize) throws ApplicationException {

        log.debug("Model  list Started");

        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from MARKSHEETDTO");
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
                MarksheetDTO dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setRollNo(rs.getString(6));
                dto.setStudentId(rs.getLong(7));
                dto.setName(rs.getString(8));
                dto.setPhysics(rs.getInt(9));
                dto.setChemistry(rs.getInt(10));
                dto.setMaths(rs.getInt(11));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(
                    "Exception in getting list of Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model  list End");
        return list;

    }

    /**
     * get Merit List of Marksheet with pagination
     * 
     * @return list : List of Marksheets
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
    public List getMeritList(int pageNo, int pageSize)
            throws ApplicationException {
        log.debug("Model  MeritList Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer(
                "SELECT `ID`,`ROLLNO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS` , (PHYSICS + CHEMISTRY + MATHS) as total from `MARKSHEETDTO` where PHYSICS>=35 and CHEMISTRY>=35 and MATHS>=35 order by total desc");
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
                MarksheetDTO dto = new MarksheetDTO();
                dto.setId(rs.getLong(1));
                dto.setRollNo(rs.getString(6));
                dto.setName(rs.getString(8));
                dto.setPhysics(rs.getInt(9));
                dto.setChemistry(rs.getInt(10));
                dto.setMaths(rs.getInt(11));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error(e);
            throw new ApplicationException(
                    "Exception in getting merit list of Marksheet");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model  MeritList End");
        return list;
    }

}