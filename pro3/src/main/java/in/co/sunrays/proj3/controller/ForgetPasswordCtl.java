package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Forget Password functionality Controller. Performs operation for Forget
 * Password
 * 
 * @author Adapterpattern
 * @version 1.0 Copyright (c) Adapterpattern
 */
@WebServlet(name = "ForgetPasswordCtl", urlPatterns = { "/ForgetPasswordCtl" })
public class ForgetPasswordCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	/**
	 * Declare to the Logger class of log4j
	 */
	Logger log = Logger.getLogger("ForgetPasswordCtl.class");

	/**
	 * Validate logic inside this method
	 * 
	 * @param request
	 * @return boolean type value
	 * 
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("ForgetPasswordCtl Method validate Started");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		log.debug("ForgetPasswordCtl Method validate Started");
		return pass;
	}

	/**
	 * Set Value inside this method
	 * 
	 * @param request
	 * @return userBean
	 * 
	 */

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("ForgetPasswordCtl Method populatebean Started");

		UserDTO bean = new UserDTO();

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		log.debug("ForgetPasswordCtl Method populatebean Ended");

		return bean;
	}

	

	/**
	 * Display Logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ForgetPasswordCtl Method doGet Started");

		ServletUtility.forward(getView(), request, response);

		log.debug("ForgetPasswordCtl Method doGet Ended");
	}
      
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("ForgetPasswordCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		System.out.println(op);
		

		// get model
		//UserModelHibImpl model = new UserModelHibImpl();
          UserModelInt modelInt=ModelFactory.getInstance().getUserModel();
		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				UserDTO dto = (UserDTO) populateDTO(request);		
				
				//System.out.println("Login:" + bean.getLogin());
				
				boolean flag=modelInt.forgetPassword(dto.getLogin());
				//System.out.println("==================="+flag);
				
				if (flag == true) {
				//System.out.println("After fPassword:" + bean.getLogin());
				
				ServletUtility.setSuccessMessage("Password has been sent on your email id.", request);
				}
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
				log.error(e);
			
			} catch (ApplicationException e) {
			    
				log.error(e);
			    //ServletUtility.setErrorMessage("Please Check Internet Connection", request);
		
				// System.out.println(e.getMessage());
			    
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
			
		
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
			return;

		}

		
		log.debug("ForgetPasswordCtl Method doPost Ended");
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FORGET_PASSWORD_VIEW;
	}

}
