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
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.CourseModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.SubjectModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Subject List functionality Controller. Performs operation for add, update and
 * get Subject List
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger("SubjectListCtl.class");

	/**
	 * contain Preload value on View page inside this method
	 */

	@Override
	protected void preload(HttpServletRequest request) {
		log.debug("SubjectCtl Method preload Started");
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			List l = model.list();// load full data
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
		log.debug("SubjectCtl Method preload Ended ");
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("SubjectListCtl Method populateDTO Started");
		SubjectDTO dto = new SubjectDTO();

		dto.setName(DataUtility.getString(request.getParameter("subjectName")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseIdN")));

		log.debug("SubjectListCtl Method populateDTO Ended");
		return dto;

	}

	/**
	 * Contain action logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("SubjectListCtl Method doPost Started");
		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();

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
				ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				   pageNo = pageNo;
				if (ids != null && ids.length > 0) {
					SubjectDTO deletedto = new SubjectDTO();
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
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
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
		log.debug("SubjectListCtl Method doPost Ended");
	}

	/**
	 * Contain Display logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("SubjectListCtl Method doGet Started");

		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO dto = new SubjectDTO();
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
		log.debug("SubjectListCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
