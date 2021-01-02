package in.co.sunrays.proj3.controller;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.MarksheetModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	/**
	 * Create Logger class of log4j
	 */
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

	/**
	 * Validate logic inside this method
	 * @param request
	 * @return boolean type value
	 * 
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("GetMarksheetCTL Method validate Started");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;

		}
		log.debug("GetMarksheetCTL Method validate Ended");
		return pass;
	}

	/**
	 * Set Value inside this method
	 * @param request:HttpServletRequest
	 * @return userdto
	 * 
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("GetMarksheetCtl Method populateDTO Started");

		MarksheetDTO dto = new MarksheetDTO();

		/* dto.setId(DataUtility.getLong(request.getParameter("id"))); */

		dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		/*
		 * dto.setName(DataUtility.getString(request.getParameter("name")));
		 * 
		 * dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		 * 
		 * dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry"
		 * )));
		 * 
		 * dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
		 */

		log.debug("GetMarksheetCtl Method populateDTO Ended");

		return dto;
	}

	/**
	 * Contain of Display logic inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("GetMarksheetCtl Method doGet Started");
		ServletUtility.forward(getView(), request, response);
		log.debug("GetMarksheetCtl Method doGet Ended");
	}

	/**
	 * Contain of Submit logic inside this Method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 *
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("GetMarksheetCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get Marksheet Model
		MarksheetModelInt model =ModelFactory.getInstance().getMarksheetModel();

		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

		// long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				dto = model.findByRollNo(dto.getRollNo());
				if (dto != null) {
					ServletUtility.setDTO(dto, request);
				} else {
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		if(OP_RESET.equalsIgnoreCase(op)){
			ServletUtility.redirect(ORSView.GET_MARKSHEET_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("GetMarksheetCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}

}