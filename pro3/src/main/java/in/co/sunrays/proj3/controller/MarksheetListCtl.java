package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.MarksheetModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Maarksheet functionality Controller. Performs operation for add, update,
 * delete and get Maarksheet
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */

@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/**
	 * Create logger class of log4j
	 */
	Logger log = Logger.getLogger("MarksheetListCtl.class");

	@Override
	protected void preload(HttpServletRequest request) {
		List list = null;
		MarksheetDTO mdto = new MarksheetDTO();
		MarksheetModelInt mModel = ModelFactory.getInstance().getMarksheetModel();
		try {
			list = mModel.list();
			request.setAttribute("MarksheetList", list);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	/**
	 * set all Value in dto by Parameter
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {

		MarksheetDTO dto = new MarksheetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("mIdRoll")));
		
		dto.setName(DataUtility.getString(request.getParameter("studentName")));

		return dto;
	}

	/**
	 * contain action logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("MarksheetListCtl Method doPost Started");
		// perform task by operation form jsp
		String op = DataUtility.getString(request.getParameter("operation"));
		// get checked box value
		String[] ids = request.getParameterValues("chk_1");

		// use for hold all data
		List list = null;
		// use for pageNo and pageSize and this value is hidden on jsp page
		// by defauld value is (int)0 by jsp
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		// therefore
		// 1:- first page pageSize(no of column):-10 on first page by
		// PropertyReader
		pageNo = (pageNo == 0) ? 1 : pageNo;
		// use for change page by next button
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		// cast dto obj
		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_PREVIOUS.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					// stay on first Page
					pageNo = 1;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					// live current page and forward previous page
					pageNo--;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					// live current page and forward neat Page
					pageNo++;
				}
				list = model.search(dto, pageNo, pageSize);
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				// redirect Marksheet view
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;
			} else

			if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = pageNo;
				// check of the (CheckedBox value)

				if (ids != null && ids.length > 0) {
					MarksheetDTO deletedto = new MarksheetDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						try {
							// try catch is not use but i define
							model.delete(deletedto);
						} catch (ApplicationException e) {
							log.error(e);
						}
					}
					list = model.search(dto, pageNo, pageSize);
					ServletUtility.setSuccessMessage("Data is successfully deleted ", request);
				} else 		
				{
					list = model.list(pageNo, pageSize);
					request.setAttribute("onerecord", "Select atleast One Record");
					// ServletUtility.setErrorMessage("Please,Select at lest One
					// Record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				// forword on new page
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}

			List pno=model.search(dto);
			request.setAttribute("pno",pno);
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
		log.debug("MarksheetListCtl Method doPost Ended");
	}

	/**
	 * contain display logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("MarksheetListCtl Method doGet Started");
		List list = null;
		// hold page size from PropertyReader
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

		// store button value
		// String op=DataUtility.getString(request.getParameter("operation"));
		// cast to populateDTO
		// MarksheetDTO dto=(MarksheetDTO)populateDTO(request);
		// create model object
		
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		MarksheetDTO dto = new MarksheetDTO();
		try {
			
			List pno=model.search(dto);
			request.setAttribute("pno",pno);
			
			list = model.search(dto, pageNo, pageSize);
			// set list in set Attribute
			ServletUtility.setList(list, request);
			// check list is null or not
			if (list == null || list.size() == 0) {
				// when condition is true
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			// suppose Condition is false,then
			ServletUtility.setList(list, request);
			// set pageNo in set Attribute
			ServletUtility.setPageNo(pageNo, request);
			// set pageSize in set Attribute
			ServletUtility.setPageSize(pageSize, request);
			// with forward page but this forward method throws io/Servlet
			// Exception
			System.out.println("--------------------------d-f----------------------");
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			// any (only class exception) is handle by this
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("MarksheetListCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
