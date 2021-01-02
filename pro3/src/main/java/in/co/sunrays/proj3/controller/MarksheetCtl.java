package in.co.sunrays.proj3.controller;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.MarksheetDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.MarksheetModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.StudentModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	/**
	 * create logger class of log4j
	 */
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	/**
	 * preload method use for load data on jsp page
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		StudentModelInt model =ModelFactory.getInstance().getStudentModel();
		try {
			List l = model.list();
			request.setAttribute("studentList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	/**
	 * check validation inside this method
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MarksheetCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}
         if(DataValidator.isNull(request.getParameter("physics"))){
        	request.setAttribute("physics", PropertyReader.getValue("error.require","Physics Marks"));
        	 pass=false;
         }  
		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can not be greater than 100");
			pass = false;
		}
          if(DataValidator.isNull(request.getParameter("chemistry"))){
        	  request.setAttribute("chemistry", PropertyReader.getValue("error.require","Chemistry Marks"));
        	  pass=false;
          }
		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can not be greater than 100");
			pass = false;
		}
		// if getInt do not get to any value then autoset (0) value..
		 if(DataValidator.isNull(request.getParameter("maths"))){
       	  request.setAttribute("maths", PropertyReader.getValue("error.require","Maths Marks"));
       	  pass=false;
         }
		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can not be greater than 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		log.debug("MarksheetCtl Method validate Ended");

		return pass;
	}

	/**
	 * Set Value inside this method
	 * 
	 * @param request
	 * @return userdto
	 * 
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populateDTO Started");

		MarksheetDTO dto = new MarksheetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		//dto.setName(DataUtility.getString(request.getParameter("name")));
        //getInt value is set of byDefould (0) place of null
		 if(DataValidator.isNotNull(request.getParameter("physics"))
					&& DataValidator.isInteger(request.getParameter("physics")))
		 {
			 
			 dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
			 System.out.println("..................................."+dto.getPhysics());
		 }
		 
		 if(DataValidator.isNotNull(request.getParameter("chemistry"))
					&& DataValidator.isInteger(request.getParameter("chemistry")))
		 {
		 dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		 }
		 
		if(DataValidator.isNotNull(request.getParameter("maths"))
					&& DataValidator.isInteger(request.getParameter("maths")))
		 {
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
		}

		dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		populateDTO(dto, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populateDTO Ended");

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
		log.debug("MarksheetCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		
		Long id = DataUtility.getLong(request.getParameter("id"));// it's id set by edit button of UserListView
		MarksheetModelInt  model = ModelFactory.getInstance().getMarksheetModel(); 

		if (id > 0 || op != null) {
			MarksheetDTO dto;

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
		log.debug("MarksheetCtl Method doGet Ended");
	}

	/**
	 * Contain of action logic inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("MarksheetCtl Method doPost Started");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

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
				ServletUtility.setErrorMessage(""+e.getMessage(), request);
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doPost Started");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW;
	}

}