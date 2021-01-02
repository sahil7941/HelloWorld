package in.co.sunrays.proj3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.exception.DatabaseException;
import in.co.sunrays.proj3.util.JDBCDataSource;


/**
 * JDBC implementation of faculty model
 * 
 * @author Strategy
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
public class FacultyModelJDBCImpl implements FacultyModelInt{
	
	private static Logger log = Logger.getLogger(FacultyModelJDBCImpl.class);
	
	/**
	 * Find next PK of Faculty
	 * 
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM FACULTYDTO");
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
	 * Add a Faculty
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 * 
	 */
	public long add(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();	
		CollegeDTO collegeDTO = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getName());
		
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();	
		CourseDTO courseDTO = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());
		
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDTO.getName());
		
		FacultyDTO dtoExist = findByEmailId(dto.getEmailId());
		if (dtoExist != null) { 
			  throw new DuplicateRecordException("Email already exists"); 
			  }
		 

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO facultydto VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, dto.getCreatedBy());
			pstmt.setString(3, dto.getModifiedBy());
			pstmt.setTimestamp(4, dto.getCreatedDateTime());
			pstmt.setTimestamp(5, dto.getModifiedDateTime());
			pstmt.setLong(6, dto.getCollegeId());
			pstmt.setLong(7, dto.getCourseId());
			pstmt.setString(8, dto.getCourseName());
			pstmt.setString(9, dto.getFirstName());
			pstmt.setString(10, dto.getLastName());
			pstmt.setLong(11, dto.getSubjectId());
			pstmt.setString(12, dto.getSubjectName());
			pstmt.setString(13, dto.getCollegeName());
			pstmt.setString(14, dto.getQualification());
			pstmt.setString(15, dto.getEmailId());
			pstmt.setDate(16, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(17, dto.getMobNo());
			pstmt.setString(18, dto.getGender());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			System.out.println("faculty add close");
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Update a faculty
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void update(FacultyDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;
		
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();	
		CollegeDTO collegeDTO = collegeModel.findByPK(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getName());
		
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();	
		CourseDTO courseDTO = courseModel.findByPK(dto.getCourseId());
		dto.setCourseName(courseDTO.getName());
		
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO subjectDTO = subjectModel.findByPK(dto.getSubjectId());
		dto.setSubjectName(subjectDTO.getName());

		FacultyDTO dtoExist = findByEmailId(dto.getEmailId());
		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("EmailId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("UPDATE FACULTYDTO SET COLLEGEID=?, COLLEGENAME=?, COURSEID=?, COURSENAME=?, SUBJECTID=?, SUBJECTNAME=?, FIRSTNAME=?, LASTNAME=?, QUALIFICATION=?, EMAILID=?, DOB=?, MOBNO=?, CREATEDBY=?, MODIFIEDBY=?, CREATEDDATETIME=?, MODIFIEDDATETIME=?, GENDER=? WHERE ID=?");

			pstmt.setLong(1, dto.getCollegeId());
			pstmt.setString(2, dto.getCollegeName());
			pstmt.setLong(3, dto.getCourseId());
			pstmt.setString(4, dto.getCourseName());
			pstmt.setLong(5, dto.getSubjectId());
			pstmt.setString(6, dto.getSubjectName());
			pstmt.setString(7, dto.getFirstName());
			pstmt.setString(8, dto.getLastName());
			pstmt.setString(9, dto.getQualification());
			pstmt.setString(10, dto.getEmailId());
			pstmt.setDate(11, new java.sql.Date(dto.getDob().getTime()));
			pstmt.setString(12, dto.getMobNo());
			pstmt.setString(13, dto.getCreatedBy());
			pstmt.setString(14, dto.getModifiedBy());
			pstmt.setTimestamp(15, dto.getCreatedDateTime());
			pstmt.setTimestamp(16, dto.getModifiedDateTime());
			pstmt.setString(17, dto.getGender());
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
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Delete a Faculty
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM FACULTYDTO WHERE ID=?");
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
			throw new ApplicationException("Exception : Exception in delete Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	/**
	 * Find Faculty by Email
	 * 
	 * @param login
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */
	public FacultyDTO findByEmailId(String emailId) throws ApplicationException {
		log.debug("Model findByEmail Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM FACULTYDTO WHERE EMAILID=?");
		FacultyDTO dto = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCollegeId(rs.getLong(6));
				dto.setCourseId(rs.getLong(7));
				dto.setCourseName(rs.getString(8));
				dto.setFirstName(rs.getString(9));
				dto.setLastName(rs.getString(10));
				dto.setSubjectId(rs.getLong(11));
				dto.setSubjectName(rs.getString(12));
				dto.setCollegeName(rs.getString(13));
				dto.setQualification(rs.getString(14));
				dto.setEmailId(rs.getString(15));
				dto.setDob(rs.getDate(16));
				dto.setMobNo(rs.getString(17));
				dto.setGender(rs.getString(18));


			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByEmail End");
		return dto;
	}

	/**
	 * Find Faculty by PK
	 * 
	 * @param pk
	 *            : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */
	public FacultyDTO findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM FACULTYDTO WHERE ID=?");
		FacultyDTO dto = null;
		Connection conn = null;
		
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			dto = new FacultyDTO();
			while (rs.next()) {
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCollegeId(rs.getLong(6));
				dto.setCourseId(rs.getLong(7));
				dto.setCourseName(rs.getString(8));
				dto.setFirstName(rs.getString(9));
				dto.setLastName(rs.getString(10));
				dto.setSubjectId(rs.getLong(11));
				dto.setSubjectName(rs.getString(12));
				dto.setCollegeName(rs.getString(13));
				dto.setQualification(rs.getString(14));
				dto.setEmailId(rs.getString(15));
				dto.setDob(rs.getDate(16));
				dto.setMobNo(rs.getString(17));
				dto.setGender(rs.getString(18));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting Faculty by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	/**
	 * Search Faculty with pagination
	 * 
	 * @return list : List of Faculties
	 * @param bean
	 *            : Search Parameters
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * 
	 * @throws DatabaseException
	 */
	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM FACULTYDTO WHERE 1=1");

		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRSTNAME like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().trim().length() > 0) {
				sql.append(" AND LASTNAME like '" + dto.getLastName() + "%'");
			}
			if (dto.getEmailId() != null && dto.getEmailId().length() > 0) {
				sql.append(" AND EMAILID like '" + dto.getEmailId() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + dto.getDob());
			}
			if (dto.getMobNo() != null && dto.getMobNo().length() > 0) {
				sql.append(" AND MOBNO = " + dto.getMobNo());
			}
			if (dto.getCollegeId() > 0) {
				sql.append(" AND COLLEGEID = " + dto.getCollegeId());
			}
			if (dto.getCourseId() > 0) {
				sql.append(" AND COURSEID = " + dto.getCourseId());
			}
			if (dto.getSubjectId() > 0) {
				sql.append(" AND SUBJECTID = " + dto.getSubjectId());
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
				dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCollegeId(rs.getLong(6));
				dto.setCourseId(rs.getLong(7));
				dto.setCourseName(rs.getString(8));
				dto.setFirstName(rs.getString(9));
				dto.setLastName(rs.getString(10));
				dto.setSubjectId(rs.getLong(11));
				dto.setSubjectName(rs.getString(12));
				dto.setCollegeName(rs.getString(13));
				dto.setQualification(rs.getString(14));
				dto.setEmailId(rs.getString(15));
				dto.setDob(rs.getDate(16));
				dto.setMobNo(rs.getString(17));
				dto.setGender(rs.getString(18));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		System.out.println("faculty model search end");
		return list;
	}

	/**
	 * Search Faculty
	 * 
	 * @param bean
	 *            : Search Parameters
	 * @throws DatabaseException
	 */
	public List search(FacultyDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * Get List of Faculty
	 * 
	 * @return list : List of Faculty
	 * @throws DatabaseException
	 */
	public List list() throws ApplicationException {
		return list(0,0);
	}

	/**
	 * Get List of Faculty with pagination
	 * 
	 * @return list : List of Faculty
	 * @param pageNo
	 *            : Current Page No.
	 * @param pageSize
	 *            : Size of Page
	 * @throws DatabaseException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from FACULTYDTO");
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
				FacultyDTO dto = new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setCreatedBy(rs.getString(2));
				dto.setModifiedBy(rs.getString(3));
				dto.setCreatedDateTime(rs.getTimestamp(4));
				dto.setModifiedDateTime(rs.getTimestamp(5));
				dto.setCollegeId(rs.getLong(6));
				dto.setCourseId(rs.getLong(7));
				dto.setCourseName(rs.getString(8));
				dto.setFirstName(rs.getString(9));
				dto.setLastName(rs.getString(10));
				dto.setSubjectId(rs.getLong(11));
				dto.setSubjectName(rs.getString(12));
				dto.setCollegeName(rs.getString(13));
				dto.setQualification(rs.getString(14));
				dto.setEmailId(rs.getString(15));
				dto.setDob(rs.getDate(16));
				dto.setMobNo(rs.getString(17));
				dto.setGender(rs.getString(18));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Faculty");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

}
