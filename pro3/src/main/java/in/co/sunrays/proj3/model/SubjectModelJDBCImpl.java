package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.util.JDBCDataSource;

/**
 * JDBC Implementation of SubjectModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class SubjectModelJDBCImpl implements SubjectModelInt{
	
	private static Logger log = Logger.getLogger(SubjectModelJDBCImpl.class);

	/**
     * Find next PK of Subject
     * 
     * @throws DatabaseException
     */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK method started");
		Connection conn = null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM SUBJECTDTO");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
		}catch(Exception e) {
			log.error("Database Exception...",e);
			throw new DatabaseException("Exception in getting PK"+e.getMessage());
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK method ended");
		return pk+1;
	}
	
	/**
     * Add a Subject
     * 
     * @param dto
     * @throws DatabaseException
     * 
     */
	
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add method started");
		Connection conn = null;
		int pk=0;
		
		SubjectDTO dtoExist = findByName(dto.getName());
		if(dtoExist!=null) {
			throw new DuplicateRecordException("Subject name already exist");
		}
		
		CourseModelJDBCImpl courseModelJDBCImpl = new CourseModelJDBCImpl();
		CourseDTO courseDTO = courseModelJDBCImpl.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());
		
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO SUBJECTDTO VALUES(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, dto.getCreatedBy());
			pstmt.setString(3, dto.getModifiedBy());
			pstmt.setTimestamp(4, dto.getCreatedDateTime());
			pstmt.setTimestamp(5, dto.getModifiedDateTime());
			pstmt.setString(6, dto.getName());
			pstmt.setString(7, dto.getDescription());
			pstmt.setLong(8, dto.getCourseId());
			pstmt.setString(9, dto.getCourseName());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}catch(Exception e) {
			log.error("Database Exception...",e);
			try {
				conn.rollback();
			}catch(Exception ex) {
				throw new ApplicationException("Exception in add rollback"+ex.getMessage());
			}
			throw new ApplicationException("Exception in add Subject");
		}finally {
			JDBCDataSource.closeConnection(conn);
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
		log.debug("Model update Started");
        Connection conn = null;

        SubjectDTO dtoExist = findByName(dto.getName());

        // Check if updated id already exist
        if (dtoExist != null && dtoExist.getId() != dto.getId()) {
            throw new DuplicateRecordException("Subject Name is already exist");
        }

        // get Course Name
        CourseModelJDBCImpl cModel = new CourseModelJDBCImpl();
        CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
        dto.setCourseName(courseDTO.getName());

        try {

            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn.prepareStatement("UPDATE SUBJECTDTO SET Name=?,Description=?,CourseID=?,CourseNAME=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
            pstmt.setString(1, dto.getName());
            pstmt.setString(2, dto.getDescription());
            pstmt.setLong(3, dto.getCourseId());
            pstmt.setString(4, dto.getCourseName());
            pstmt.setString(5, dto.getCreatedBy());
            pstmt.setString(6, dto.getModifiedBy());
            pstmt.setTimestamp(7, dto.getCreatedDateTime());
            pstmt.setTimestamp(8, dto.getModifiedDateTime());
            pstmt.setLong(9, dto.getId());
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
            throw new ApplicationException("Exception in updating Subject ");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model update End");
		
	}

	/**
     * Delete a Subject
     * 
     * @param dto
     * @throws DatabaseException
     */
	
	public void delete(SubjectDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            PreparedStatement pstmt = conn
                    .prepareStatement("DELETE FROM SUBJECTDTO WHERE ID=?");
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
                    "Exception : Exception in delete Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model delete Started");
		
	}

	/**
     * Find college by name
     * 
     * @param name
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public SubjectDTO findByName(String name) throws ApplicationException {
		log.debug("Model findBy Name Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM SUBJECTDTO WHERE NAME=?");
        SubjectDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
                dto.setCourseId(rs.getLong(8));
                dto.setCourseName(rs.getString(9));
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting Subject by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findBy Name End");
        return dto;
	}

	/**
     * Find subject by pk
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public SubjectDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM SUBJECTDTO WHERE ID=?");
        SubjectDTO dto = null;
        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
                dto.setCourseId(rs.getLong(8));
                dto.setCourseName(rs.getString(9));
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting Subject by pk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        log.debug("Model findByPK End");
        return dto;
	}

	/**
     * Search Subject with pagination
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
		log.debug("Model search Started");
        StringBuffer sql = new StringBuffer("SELECT * FROM SUBJECTDTO WHERE 1=1");

        if (dto != null) {
            if (dto.getId() > 0) {
                sql.append(" AND id = " + dto.getId());
            }
            if(dto.getCourseId()>0) {
            	sql.append(" AND COURSEID = " + dto.getCourseId());
            }
            if (dto.getName() != null && dto.getName().trim().length() > 0) {
                sql.append(" AND NAME like '" + dto.getName()
                        + "%'");
            }
            if (dto.getDescription() != null && dto.getDescription().length() > 0) {
                sql.append(" AND DESCRIPTION like '" + dto.getDescription() + "%'");
            }

            if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
                sql.append(" AND Course_NAME = " + dto.getCourseName());
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
            	dto = new SubjectDTO();
            	dto.setId(rs.getLong(1));
            	dto.setCreatedBy(rs.getString(2));
            	dto.setModifiedBy(rs.getString(3));
            	dto.setCreatedDateTime(rs.getTimestamp(4));
            	dto.setModifiedDateTime(rs.getTimestamp(5));
            	dto.setName(rs.getString(6));
            	dto.setDescription(rs.getString(7));
            	dto.setCourseId(rs.getLong(8));
            	dto.setCourseName(rs.getString(9));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in search Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model search End");
        return list;
	}

	/**
     * Search Subject
     * 
     * @param dto
     *            : Search Parameters
     * @throws DatabaseException
     */
	
	public List search(SubjectDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
     * Get List of Subject
     * 
     * @return list : List of Subject
     * @throws DatabaseException
     */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * Get List of Subject with pagination
     * 
     * @return list : List of Subject
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
        ArrayList list = new ArrayList();
        StringBuffer sql = new StringBuffer("select * from SUBJECTDTO");
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
                SubjectDTO dto = new SubjectDTO();
                dto.setId(rs.getLong(1));
                dto.setCreatedBy(rs.getString(2));
                dto.setModifiedBy(rs.getString(3));
                dto.setCreatedDateTime(rs.getTimestamp(4));
                dto.setModifiedDateTime(rs.getTimestamp(5));
                dto.setName(rs.getString(6));
                dto.setDescription(rs.getString(7));
                dto.setCourseId(rs.getLong(8));
                dto.setCourseName(rs.getString(9));
                list.add(dto);
            }
            rs.close();
        } catch (Exception e) {
            log.error("Database Exception..", e);
            throw new ApplicationException(
                    "Exception : Exception in getting list of Subject");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        log.debug("Model list End");
        return list;
	}

}
