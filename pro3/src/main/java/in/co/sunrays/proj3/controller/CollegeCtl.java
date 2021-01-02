package in.co.sunrays.proj3.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.CollegeDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.CollegeModelInt;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;
/**
 * College functionality Controller. Performs operation for add, update
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name="CollegeCtl",urlPatterns={"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl{
	


	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static Logger log=Logger.getLogger("CollegeCtl.class");
	/**
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) throws Exception {
	   log.debug("Method validate Start");
	     boolean pass=true;
		if (DataValidator.isNull(request.getParameter("collegeName"))) {
			request.setAttribute("collegeName", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		
		} 
		 else if (!DataValidator.isNameWithSpace(request.getParameter("collegeName"))) {
				request.setAttribute("collegeName", PropertyReader.getValue("error.alpha", "College Name"));
				pass = false;
			}
		
		
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("cityName"))) {
			request.setAttribute("cityName", PropertyReader.getValue("error.require", "City Name"));
			pass = false;
		} 
		 else if (!DataValidator.isName2(request.getParameter("cityName"))) {
				request.setAttribute("cityName", PropertyReader.getValue("error.alpha", "City Name"));
				pass = false;
			}
		
		if (DataValidator.isNull(request.getParameter("stateName"))) {
			request.setAttribute("stateName", PropertyReader.getValue("error.require", "State Name"));
			pass = false;
		}
		 else if (!DataValidator.isName2(request.getParameter("stateName"))) {
				request.setAttribute("stateName", PropertyReader.getValue("error.alpha", "State Name"));
				pass = false;
			}
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("Mobile no must start with 6,7,8,9"));
			pass = false;
		}
	     
		
		
	    log.debug("Method validate End");
		return pass;
	}
	/**
	 * 
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
         log.debug("Method populateDTO Start");
        
        CollegeDTO dto=new CollegeDTO(); 
        dto.setId(DataUtility.getLong(request.getParameter("id")));

 		dto.setName(DataUtility.getString(request.getParameter("collegeName")));

 		dto.setAddress(DataUtility.getString(request.getParameter("address")));

 		dto.setState(DataUtility.getString(request.getParameter("stateName")));

 		dto.setCity(DataUtility.getString(request.getParameter("cityName")));

 		dto.setPhoneNo(DataUtility.getString(request.getParameter("mobileNo")));

 		populateDTO(dto, request);
         
         log.debug("Method populateDTO End");
		return dto;
	}
	
	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CollegeCtl Method doget started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		// this function is basically use for collegeList edit button
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		if (id > 0 || op != null) {
			CollegeDTO dto;
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

		log.debug("CollegeCtl Method doget Ended");
	}
	/**
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("CollegeCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		// get Model
		CollegeModelInt model =ModelFactory.getInstance().getCollegeModel() ;

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeDTO dto = (CollegeDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = model.add(dto);
					dto.setId(pk);// it's set of pk value
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				// ServletUtility.setdto(dto, request);
				// it's dto use for update time
				

			} catch (ApplicationException e) {

				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;

			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage(" "+e.getMessage(), request);

			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("CollegeCtl Method doPost Ended");

	}

	/**
	 * 
	 */
	@Override
	protected String getView() {
		
		return ORSView.COLLEGE_VIEW;
	}

}
