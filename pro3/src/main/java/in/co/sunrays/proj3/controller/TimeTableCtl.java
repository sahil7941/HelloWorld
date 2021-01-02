package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.TimeTableDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.model.TimeTableModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Time Table functionality Controller. Performs operation for add, update and
 * get TimeTable
 * 
 * @author Adapterpattern
 * @version 1.0 Copyright (c) Adapterpattern
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("TimeTableCtl");

	/**
	 * preload all value inside this method
	 */
	protected void preload(HttpServletRequest request) {
		log.debug("TimeTableCtl Method preload Started");
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();

		try {
			List sList = subjectModel.list();
			request.setAttribute("subjectList", sList);
			List cList = courseModel.list();
			request.setAttribute("courseList", cList);

		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("TimeTableCtl Method preload Ended");
	}

	/**
	 * Check Validate of all Value
	 */
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("courseid", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examDate"))) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		return pass;

	}

	/**
	 * set all Value in dto by Parameter
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("TimeTableCtl Method populateDTO Started");
		TimeTableDTO dto = new TimeTableDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		// int couid=DataUtility.getInt(request.getParameter("courseid"));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseid")));

		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		
		
		java.util.Date da=DataUtility.getDate(request.getParameter("examDate"));
		String h=DataUtility.getDateString(da);
		
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		
		dto.setTime(DataUtility.getString(request.getParameter("examTime")));
		dto.setSemester(DataUtility.getString(request.getParameter("semester")));

		populateDTO(dto, request);
		log.debug("TimeTableCtl Method populateDTO Ended");
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
		log.debug("TimeTableCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("hi i am id:--" + id);
		// get Model
		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
			try {
				if (id > 0) {
					System.out.println("hi i am id 1:--" + id);

					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);// it's set of pk value
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				// ServletUtility.setDTO(dto, request);
				// it's dto use for update time
				

			} catch (ApplicationException e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				//ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage(e.getMessage(), request);

			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeCtl Method doPost Ended");

	}

	/**
	 * Contain Display Login inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		if (op != null || id > 0) {
			TimeTableDTO dto = null;
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
		return ORSView.TIMETABLE_VIEW;
	}

}
