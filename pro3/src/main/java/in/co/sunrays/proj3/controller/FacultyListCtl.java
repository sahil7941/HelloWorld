package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.dto.CourseDTO;
import in.co.sunrays.proj3.dto.FacultyDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.FacultyModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Faculty List functionality Controller. Performs operation for add, update,
 * delete and get faculty list
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */

@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	/**
	 * Create Logger class of log4j
	 *
	 */
	Logger log = Logger.getLogger("FacultyListCtl.class");

	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("FacultyListCtl method preload Started");

		List clist = null;
		CollegeDTO cdto = new CollegeDTO();
		CollegeModelInt cModel =ModelFactory.getInstance().getCollegeModel();

		List colist = null;
		CourseDTO codto = new CourseDTO();
		CourseModelInt coModel = ModelFactory.getInstance().getCourseModel();

		try {
			clist = cModel.list();
			request.setAttribute("collegeList", clist);

			colist = coModel.list();
			request.setAttribute("courseList", colist);

		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.debug("FacultyListCtl method preload Ended");

	}

	/**
	 * set all Value in dto by requestParameter
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("FacultyListCtl Method populateDTO Started");
		FacultyDTO dto = new FacultyDTO();
		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeIdN")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseIdN")));
		// dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setEmailId(DataUtility.getString(request.getParameter("email")));
		log.debug("FacultyListCtl Method populateDTO Ended");
		return dto;
	}

	/**
	 * Contains Display Logic inside method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("FacultyListCtl Method doGet Started");
		List list = null;
		int pageNo;
		int pn=DataUtility.getInt(request.getParameter("pNo"));
		if(pn>0)
		{
			pageNo = pn;
			//System.out.println("--------------------------------------"+pn);
		}else{
			pageNo = 1;	
		}
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		FacultyDTO dto = new FacultyDTO();
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();

		try {
			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			
			list = model.search(dto, pageNo, pageSize);
			// ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("FacultyListCtl Method doGet Ended");

	}

	/**
	 * Cantain Action Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("FacultyListCtl Method doPost Started");
		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();

		String op = DataUtility.getString(request.getParameter("operation"));
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		// img
		String[] ids = request.getParameterValues("chk_1");
		List list = null;

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				}
				list = model.search(dto, pageNo, pageSize);
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo=pageNo;
				if (ids != null && ids.length > 0) {
					FacultyDTO deletedto = new FacultyDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						try {
							model.delete(deletedto);
						} catch (ApplicationException e) {
							log.error(e);
						}

					}
					list = model.search(dto, pageNo, pageSize);
					ServletUtility.setSuccessMessage("Data is successfully deleted ", request);
				} else {
					list = model.list(pageNo, pageSize);
					request.setAttribute("onerecord", "Select atleast One Record");
				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			}
			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			// hold in list
			//list = model.search(dto, pageNo, pageSize);
			// check condition of list
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			// set all value
			ServletUtility.setDTO(dto, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			// forward on view
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("FacultyListCtl Method doPost Ended");
	}

	@Override
	protected String getView() {

		return ORSView.FACULTY_LIST_VIEW;
	}

}
