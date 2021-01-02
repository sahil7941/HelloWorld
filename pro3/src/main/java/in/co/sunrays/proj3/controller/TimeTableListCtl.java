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
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.TimeTableModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Time Table List functionality Controller. Performs operation for add, update
 * and get TimeTable List
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("TimeTableListCtl.class");

	/**
	 * contain value
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("TimeTableCtl Method preload Started");
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			List l = model.list();// load full data
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("TimeTableCtl Method preload Ended ");
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("TimeTableListCtl Method populateDTO Started");
		TimeTableDTO dto = new TimeTableDTO();

		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseIdN")));
		dto.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

		log.debug("TimeTableListCtl Method populateDTO Ended");
		return dto;

	}

	/**
	 * contain action Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("TimeTableListCtl Method doPost Started");
		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		TimeTableModelInt model =ModelFactory.getInstance().getTimeTableModel();

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
				ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
                   
				pageNo=pageNo;
				if (ids != null && ids.length > 0) {
					TimeTableDTO deletedto = new TimeTableDTO();
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
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}

			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
				return;
			}
			else
			
			
			if(OP_GO.equalsIgnoreCase(op)){	
				int pn=DataUtility.getInt(request.getParameter("pNo"));
				//System.out.println();
				request.setAttribute("pNo",pn);
				pageNo = pn;
				TimeTableDTO dto1=new TimeTableDTO();
				list = model.search(dto1, pageNo, pageSize);
				//System.out.println("--------------------------------------"+pn);
			}

			// hold in list
			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			
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
		log.debug("TimeTableListCtl Method doPost Ended");
	}

	/**
	 * contain Display Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("TimeTableListCtl Method doGet Started");

		TimeTableModelInt model = ModelFactory.getInstance().getTimeTableModel();
		TimeTableDTO dto = new TimeTableDTO();
		
		
		List list = null;
		int pageNo =1;
		int pn=DataUtility.getInt(request.getParameter("pNo"));
		
		
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
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
		log.debug("TimeTableListCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
