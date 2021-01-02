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
 * Maarksheet Merit List functionality Controller. Performs operation for add,
 * update, delete and get Maarksheet List
 * 
 * @author Adapterpattern
 * @version 1.0 Copyright (c) Adapterpattern
 */

@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/**
	 * create Logger class of log4j
	 */
	Logger log = Logger.getLogger("MarksheetMeritListCtl.class");

	/**
	 * set value inside this method
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("MarksheetMeritListCtl Mehtod populateDTO Started");
		MarksheetDTO dto = new MarksheetDTO();

		log.debug("MarksheetMeritListCtl Mehtod populateDTO Ended");
		return dto;
	}

	/**
	 * Contains Submit logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("MarksheetListCtl Method doPost Started");
		// perform task by operation form jsp
		String op = DataUtility.getString(request.getParameter("operation"));
		// get checked box value

		// use for hold all data
		List list = null;
		// use for pageNo and pageSize and this value is hidden on jsp page
		// by defauld value is (int)0 by jsp
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = 10;
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

			if (OP_PREVIOUS.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)) {

				if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					// live current page and forward previous page
					pageNo--;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					// live current page and forward neat Page
					pageNo++;
				}

			} else if (OP_RESET.equalsIgnoreCase(op)) {
				// forword on new page
				ServletUtility.redirect(ORSView.MARKSHEET_MERIT_LIST_CTL, request, response);
				return;
			}

			// hold in list
			
				if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;
				}
			list = model.getMeritList(pageNo, pageSize);

			// check condition of list
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			// set all value
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
		log.debug("MarksheetMeritListCtl Method doPost Ended");
	}

	/**
	 * Contains Display Logic inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("MarksheetMeritListCtl Method doGet Started");

		int pageNo = 1;
		int pageSize = 10;
		List list = null;
		MarksheetDTO dto = new MarksheetDTO();
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();

		try {
			list = model.getMeritList(pageNo, pageSize);
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
		log.debug("MarksheetMeritListCtl Method doGet Started");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}

}
