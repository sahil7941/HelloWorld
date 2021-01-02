package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.SubjectDTO;
import in.co.sunrays.proj3.controller.BaseCtl;
import in.co.sunrays.proj3.controller.ORSView;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt; 
import in.co.sunrays.proj3.util.DataUtility;
import  in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Subject functionality Controller. Performs operation for add, update and get
 * Subject
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	/**
	 * creste Logger class of log4j
	 */
	Logger log = Logger.getLogger("SubjectCtl.class");

	private static final long serialVersionUID = 1L;

	protected void preload(HttpServletRequest request) {
		log.debug("SubjectCtl Method preload Started");
		CourseModelInt model =ModelFactory.getInstance().getCourseModel();
		try {
			List l = model.list();// load full data
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("SubjectCtl Method preload Ended ");
	}

	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		   

		if (DataValidator.isNull(request.getParameter("subjectName"))) {
			request.setAttribute("subjectName", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		else if (!DataValidator.isNameWithSpace(request.getParameter("subjectName"))) {
			request.setAttribute("subjectName", PropertyReader.getValue("error.alpha", "Subject Name"));
			pass = false;
		}
		
		
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		return pass;
	}

	/**
	 * set all Value in dto by Parameter
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("SubjectCtl Method populate Started");
		SubjectDTO dto = new SubjectDTO();

		// int couid=DataUtility.getInt(request.getParameter("courseid"));
		
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setName(DataUtility.getString(request.getParameter("subjectName")));

		dto.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(dto, request);
        log.debug("SubjectCtl Method populate Ended");
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
		log.debug("SubjectCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get Model
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			SubjectDTO dto = (SubjectDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
					// ServletUtility.setSuccessMessage("Data is successfully
					// saved", request);
					// ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL,
					// request, response);
					// return;

				} else {
					long pk = model.add(dto);
					dto.setId(pk);// it's set of pk value
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				// ServletUtility.setdto(dto, request);//it's dto use for set
				// id on viewpage
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
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("SubjectCtl Method doPost Ended");

	}

	/**
	 * Contain Display logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();
		if (op != null || id > 0) {
			SubjectDTO dto = null;
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
		return ORSView.SUBJECT_VIEW;
	}

}
