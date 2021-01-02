package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.FacultyModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for add, update, and
 * reset,get Faculty
 * 
 * @author Adapterpattern
 * @version 1.0 Copyright (c) Adapterpattern
 */

@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {
	/**
	 * Create Logger class of log4j
	 */
	Logger log = Logger.getLogger("FacultyCtl.class");
	private static final long serialVersionUID = 1L;

	/**
	 * preload method use for load data on jsp page
	 */

	protected void preload(HttpServletRequest request) {
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel(); 
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CourseModelInt  courseModel = ModelFactory.getInstance().getCourseModel();

		try {
			List sl = subjectModel.list();
			request.setAttribute("subjectList", sl);
			List col = collegeModel.list();
			request.setAttribute("collegeList", col);
			List cl = courseModel.list();
			request.setAttribute("courseList", cl);

		} catch (ApplicationException e) {

			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
	}

	/**
	 * Check data is valid or not inside this method
	 */
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alpha", "First Name"));
			pass = false;
		}else if (DataValidator.isWhiteSpace(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "first Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alpha", "Last Name"));
			pass = false;
		}else if (DataValidator.isWhiteSpace(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Email Id "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("Mobile no must start with 6,7,8,9"));
			pass = false;
		}
		
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		return pass;
	}

	/**
	 * set all Value in dto by Parameter
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populateDTO Started");

		FacultyDTO dto = new FacultyDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		
		// int p=DataUtility.getInt(request.getParameter("courseId"));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		// dto.setCourseId(DataUtility.getLong("1"));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setEmailId(DataUtility.getString(request.getParameter("login")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		populateDTO(dto, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * Contains Submit logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel() ;

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			FacultyDTO dto = (FacultyDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage(""+e.getMessage(), request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		// log.debug("UserCtl Method doPost Started");
	}

	/**
	 * Contains display logic inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		FacultyModelInt model =ModelFactory.getInstance().getFacultyModel();
		if (op != null || id > 0) {
			FacultyDTO dto = null;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDTO(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_VIEW;
	}

}
