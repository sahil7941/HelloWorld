package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 *
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	/**
	 * Create Logger Class of log4j
	 */
	private static Logger log = Logger.getLogger("CourseCtl.class");

	/**
	 * Check Validate of all Value
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("CourseCtl Method validate Started");
		
		boolean pass = true;		
		
		if (DataValidator.isNull(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		
		}  else if (!DataValidator.isNameWithSpace(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.alpha", "Role Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseDuration"))) {
			request.setAttribute("courseDuration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.alpha", "Description"));
			pass = false;
		}

		log.debug("CourseCtl Method validate Ended");
		return pass;
	}

	/**
	 * set all Value in dto by Parameter
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("CourseCtl Method populate Started");
		
		CourseDTO dto = new CourseDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("courseName")));

		dto.setDuration(DataUtility.getString(request.getParameter("courseDuration")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(dto, request);

		log.debug("CourseCtl Method populate Ended");
		
		return dto;
	}

	/**
	 * Contains Submit logics
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("CourseCtl Method doPost Started");
       //System.out.println("=============================co=======");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
        //System.out.println("---------------------------------"+op);
		// get Model
		CourseModelInt model =ModelFactory.getInstance().getCourseModel() ;

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseDTO dto = (CourseDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is Successfully updated", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);// it's set of pk value
					ServletUtility.setSuccessMessage("Data is Successfully saved", request);
				}
				// ServletUtility.setdto(dto, request);
				// it's dto use for update time
				

			} catch (ApplicationException e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage(""+e.getMessage(), request);

			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("CourseCtl Method doPost Ended");

	}

	/**
	 * Contains display logic
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("CourseCtl Method doget started");

		String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println();
		long id = DataUtility.getLong(request.getParameter("id"));
		// this function is basically use for collegeList edit button
		CourseModelInt model =ModelFactory.getInstance().getCourseModel();
		if (id > 0 || op != null) {
			CourseDTO dto;
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

		log.debug("CourseCtl Method doget Ended");
	}

	@Override
	protected String getView() {

		return ORSView.COURSE_VIEW;
	}

}
