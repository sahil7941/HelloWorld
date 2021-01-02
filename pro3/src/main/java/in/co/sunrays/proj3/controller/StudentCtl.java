package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.StudentDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.StudentModelInt; 
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Student functionality Controller. Performs operation for add, update and get
 * Student
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */

@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	/**
	 * create Logger class of log4j
	 */
	Logger log = Logger.getLogger("StudentCtl.class");

	/**
	 * preload method use for load data on jsp page
	 */

	protected void preload(HttpServletRequest request) {
		log.debug("StrudentCtl Method preload Started");
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		try {
			List l = model.list();// load full data
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("StrudentCtl Method preload Ended ");
	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("collegeId")))
		{
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}


		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alpha", "First Name"));
			pass = false;
		}else if (DataValidator.isWhiteSpace(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First Name"));
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
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id "));
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
		return pass;
	}

	/**
	 * set all Value in dto by requestParameter
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		StudentDTO dto = new StudentDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setFname(DataUtility.getString(request.getParameter("firstName")));

		dto.setLname(DataUtility.getString(request.getParameter("lastName")));

		dto.setEmail(DataUtility.getString(request.getParameter("login")));

		dto.setCollegeID(DataUtility.getLong(request.getParameter("collegeId")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateDTO(dto, request);

		return dto;
	}

	/**
	 * Contain Display Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("StudentCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));// it's id set
																	// by edit
																	// button of
																	// UserListView
		StudentModelInt model =ModelFactory.getInstance().getStudentModel() ;

		if (id > 0 || op != null) {
			StudentDTO dto;

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
		log.debug("StudentCtl Method doGet Ended");
	}

	/**
	 * Action Perform inside this method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("StudentCtl Method doPost Started");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		StudentModelInt model =ModelFactory.getInstance().getStudentModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			StudentDTO dto = (StudentDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage(" "+e.getMessage(), request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Started");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}

}
