package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.util.JDBCDataSource;
import in.co.sunrays.proj3.util.DataUtility;


/**
 * JDBC Implementation of TimeTableModel
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public class TimeTableModelJDBCImpl implements TimeTableModelInt{
	private static Logger log = Logger.getLogger(TimeTableModelJDBCImpl.class);
	
	/**
	 * Find next PK of TimeTable
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM TIMETABLEDTO");
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
	 * Add a TimeTable
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */
	
	public long add(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add started");
		Connection conn = null;
		System.out.println("in add");
		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());

		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDTO.getName());

		TimeTableDTO duplicatename = findTimeTableDuplicacy(dto.getCourseId(),dto.getSemester(),dto.getExamDate());
		TimeTableDTO duplicatename1 = findTimeTableDuplicacy(dto.getCourseId(),dto.getSemester(),dto.getSubjectId());
				int pk = 0;
				 if(duplicatename1!=null){
					throw new DuplicateRecordException("Time Table already exist");
					}

				 if (duplicatename!=null ) {
					throw new DuplicateRecordException("Time Table already exist");
					}
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement psmt = conn.prepareStatement("INSERT INTO TIMETABLEDTO VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			psmt.setInt(1, pk);
			psmt.setString(2, dto.getCreatedBy());
			psmt.setString(3, dto.getModifiedBy());
			psmt.setTimestamp(4, dto.getCreatedDateTime());
			psmt.setTimestamp(5, dto.getModifiedDateTime());
			psmt.setLong(6, dto.getCourseId());
			psmt.setString(7, dto.getCourseName());
			psmt.setLong(8, dto.getSubjectId());
			psmt.setString(9, dto.getSubjectName());
			psmt.setString(10, dto.getSemester());
			psmt.setDate(11, new java.sql.Date(dto.getExamDate().getTime()));
			psmt.setString(12, dto.getTime());

			psmt.executeUpdate();
			conn.commit();// End Transaction
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a TimeTable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 * @throws @throws
	 *             DatabaseException
	 */
	
	public void update(TimeTableDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		
	TimeTableDTO duplicatename = findTimeTableDuplicacy(dto.getCourseId(),dto.getSemester(), dto.getExamDate());
	int pk = 0;
	if (duplicatename!=null &&duplicatename.getId()!=dto.getId()) {
		throw new DuplicateRecordException("Time Table already exist");
	}



		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = cModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());

		SubjectModelInt sModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = sModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDTO.getName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement("UPDATE TIMETABLEDTO SET COURSEID=?,COURSENAME=?,SUBJECTID=?,SUBJECTNAME=?,SEMESTER=?,EXAMDATE=?,TIME=?,CREATEDBY=?,MODIFIEDBY=?,CREATEDDATETIME=?,MODIFIEDDATETIME=? WHERE ID=?");
			psmt.setLong(1, dto.getCourseId());
			psmt.setString(2, dto.getCourseName());
			psmt.setLong(3, dto.getSubjectId());
			psmt.setString(4, dto.getSubjectName());
			psmt.setString(5, dto.getSemester());
			psmt.setDate(6, new java.sql.Date(dto.getExamDate().getTime()));
			psmt.setString(7, dto.getTime());
			psmt.setString(8, dto.getCreatedBy());
			psmt.setString(9, dto.getModifiedBy());
			psmt.setTimestamp(10, dto.getCreatedDateTime());
			psmt.setTimestamp(11, dto.getModifiedDateTime());
			psmt.setLong(12, dto.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating TimeTable ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Delete a TimeTable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	
	public void delete(TimeTableDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement psmt = conn.prepareStatement("DELETE FROM TIMETABLEDTO WHERE ID=?");
			psmt.setLong(1, dto.getId());
			psmt.executeUpdate();
			conn.commit();
			psmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Ended");
	}

	/**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
	
	public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, Date examDate) throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLEDTO WHERE COURSEID=?  AND SEMESTER=? AND EXAMDATE = ?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, semester);
			pstmt.setDate(3, new java.sql.Date(examDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCourseId(rs.getLong(6));
				dto.setCourseName(rs.getString(7));
				dto.setSubjectId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSemester(rs.getString(10));
				dto.setExamDate(rs.getDate(11));  
				dto.setTime(rs.getString(12));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return dto;
	}

	/**
	 * Find TimeTableDuplicacy
	 * 
	 * @throws ApplicationException
	 */
	
	public TimeTableDTO findTimeTableDuplicacy(long courseId, String semester, long subjectId) throws ApplicationException {
		log.debug("Method FindTimeTable of Model TimeTable started");
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLEDTO WHERE COURSEID=?  AND SEMESTER=? AND SUBJECTID = ?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, courseId);
			pstmt.setString(2, semester);
			pstmt.setLong(3, subjectId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCourseId(rs.getLong(6));
				dto.setCourseName(rs.getString(7));
				dto.setSubjectId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSemester(rs.getString(10));
				dto.setExamDate(rs.getDate(11));  
				dto.setTime(rs.getString(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException(
					"Exception : Exception in getting TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method FindTimeTable of Model TimeTable End");
		return dto;
	}

	/**
     * Find TimeTable by pk
     * 
     * @param pk
     *            : get parameter
     * @return dto
     * @throws DatabaseException
     */
	
	public TimeTableDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLEDTO WHERE ID=?");
		TimeTableDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCourseId(rs.getLong(6));
				dto.setCourseName(rs.getString(7));
				dto.setSubjectId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSemester(rs.getString(10));
				dto.setExamDate(rs.getDate(11));  
				dto.setTime(rs.getString(12));
			}

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting TimeTable by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");

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
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("Select * From TimeTableDTO Where 1=1");
			
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if(dto.getCourseId()>0) {
				sql.append(" AND COURSEID = "+dto.getCourseId());
			}
			if(dto.getCourseName()!=null && dto.getCourseName().length()>0)
			{
				sql.append(" AND COURSENAME like '" + dto.getCourseName()+"%'");
			}
			if(dto.getSubjectName()!=null && dto.getSubjectName().trim().length()>0)
			{
				sql.append(" AND SUBJECTNAME like '" + dto.getSubjectName()+"%'");
			}
			if(dto.getSemester()!=null && dto.getSemester().length()>0)
			{
				sql.append(" AND SEMESTER like '" + dto.getSemester()+"'");
			}	
			if(dto.getExamDate()!=null) {
				sql.append(" AND EXAMDATE like '"+DataUtility.getDateString(dto.getExamDate())+"'");
			}
		}
		
		// if pageSize is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement psmt = conn.prepareStatement(sql.toString());
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				dto=new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCourseId(rs.getLong(6));
				dto.setCourseName(rs.getString(7));
				dto.setSubjectId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSemester(rs.getString(10));
				dto.setExamDate(rs.getDate(11));  
				dto.setTime(rs.getString(12));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
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
     * Get List of TimeTable
     * 
     * @return list : List of TimeTable
     * @throws DatabaseException
     */
	
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
     * Get List of TimeTable with pagination
     * 
     * @return list : List of TimeTable
     * @param pageNo
     *            : Current Page No.
     * @param pageSize
     *            : Size of Page
     * @throws DatabaseException
     */
	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM TIMETABLEDTO");
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
				TimeTableDTO dto = new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCourseId(rs.getLong(6));
				dto.setCourseName(rs.getString(7));
				dto.setSubjectId(rs.getLong(8));
				dto.setSubjectName(rs.getString(9));
				dto.setSemester(rs.getString(10));
				dto.setExamDate(rs.getDate(11));  
				dto.setTime(rs.getString(12));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of TimeTable");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		log.debug("Model list End");
		return list;
	}

}
