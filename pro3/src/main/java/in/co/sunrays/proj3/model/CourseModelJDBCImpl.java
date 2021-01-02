package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of CourseModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class CourseModelJDBCImpl implements CourseModelInt{
	
	private static Logger log = Logger.getLogger(CourseModelJDBCImpl.class);
	
	/**
     * Find next PK of Course
     * 
     * @throws DatabaseException
     */
	public static Integer nextPK() throws DatabaseException  {
		log.debug("Model nextPk method started");
		Connection conn = null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM COURSEDTO");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
		}catch(Exception e) {
			log.error("DataBase Exception...",e);
			throw new DatabaseException("Exception in getting pk"+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPk method ended");
		return pk+1;
	}

	 /**
     * Add a Course
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	public long add(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		Connection conn = null;
		int pk=0;
		CourseDTO duplicateCourseName = findByName(dto.getName());
		if(duplicateCourseName!=null) {
			throw new DuplicateRecordException("Course name already exist");
		}
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO COURSEDTO VALUES(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, dto.getCreatedBy());
			pstmt.setString(3, dto.getModifiedBy());
			pstmt.setTimestamp(4, dto.getCreatedDateTime());
			pstmt.setTimestamp(5, dto.getModifiedDateTime());
			pstmt.setString(6, dto.getName());
			pstmt.setString(7, dto.getDescription());
			pstmt.setString(8, dto.getDuration());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}catch(Exception e) {
			log.error("Database Exception...",e);
			try {
				conn.rollback();
			}catch(Exception ex) {
				throw new ApplicationException("Add rollback Exception"+ex.getMessage());
			}
			throw new ApplicationException("Exception in add course"+e.getMessage());
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add method ended");
		return pk;
	}

	/**
     * Update a Course
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void update(CourseDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		CourseDTO dtoExist = findByName(dto.getName());

		// Check if updated Course already exist
		if (dtoExist != null && dtoExist.getId() != dto.getId()) {

			throw new DuplicateRecordException("Course is already exist");
		}

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE COURSEDTO SET NAME=?,DESCRIPTION=?,DURATION=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setString(3, dto.getDuration());
			pstmt.setString(4, dto.getCreatedBy());
			pstmt.setString(5, dto.getModifiedBy());
			pstmt.setTimestamp(6, dto.getCreatedDateTime());
			pstmt.setTimestamp(7, dto.getModifiedDateTime());
			pstmt.setLong(8, dto.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Course ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
		
	}

	/**
     * Delete a Course
     * 
     * @param dto
     * @throws DatabaseException
     */
	public void delete(CourseDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM COURSEDTO WHERE ID=?");
			pstmt.setLong(1, dto.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
		
	}

	/**
     * Find Course by name
     * 
     * @param login
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CourseDTO findByName(String name) throws ApplicationException {
		log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM COURSEDTO WHERE NAME=?");
		CourseDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setName(rs.getString(6));
				dto.setDescription(rs.getString(7));
				dto.setDuration(rs.getString(8));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Course by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return dto;
	}

	/**
     * Find course by pk
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	public CourseDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM COURSEDTO WHERE ID=?");
		CourseDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setName(rs.getString(6));
				dto.setDescription(rs.getString(7));
				dto.setDuration(rs.getString(8));
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Course by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	/**
     * Search Course with pagination
     * 
     * @return list : List of Course
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
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM COURSEDTO WHERE 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getName() != null && dto.getName().trim().length() > 0) {
				sql.append(" AND NAME like '" + dto.getName() + "%'");
			}
			if (dto.getDescription() != null && dto.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + dto.getDescription() + "%'");
			}
			if (dto.getDuration() != null && dto.getDuration().length() > 0) {
				sql.append(" AND DURATION like '" + dto.getDuration() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setName(rs.getString(6));
				dto.setDescription(rs.getString(7));
				dto.setDuration(rs.getString(8));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Course");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
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
     * Get List of Course
     * 
     * @return list : List of Course
     * @throws DatabaseException
     */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * Get List of Course with pagination
     * 
     * @return list : List of Course
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from COURSEDTO");
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CourseDTO dto = new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setName(rs.getString(6));
				dto.setDescription(rs.getString(7));
				dto.setDuration(rs.getString(8));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

}
