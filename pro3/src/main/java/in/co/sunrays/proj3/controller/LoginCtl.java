package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login Operation
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(urlPatterns={"/LoginCtl"})
public class LoginCtl extends BaseCtl{
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";
	private static Logger log = Logger.getLogger(LoginCtl.class);

     
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("LoginCtl Method populatebean Started");

		UserDTO userDto = new UserDTO();

		userDto.setId(DataUtility.getLong(request.getParameter("id")));
        userDto.setLogin(DataUtility.getString(request.getParameter("login")));
		userDto.setPassword(DataUtility.getString(request.getParameter("password")));
		log.debug("LoginCtl Method populatebean Ended");

		return userDto;
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("LoginCtl Method validate Started");

		boolean pass = true;
		String op = request.getParameter("operation");

		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		log.debug("LoginCtl Method validate Ended");
		return pass;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session;
		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(op)) {
			session = request.getSession();
			session.invalidate();
			ServletUtility.setSuccessMessage("Successfully Logout", request);
			ServletUtility.forward(getView(), request, response);
			return;
		}
		
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		log.debug(" Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		    UserModelInt model = ModelFactory.getInstance().getUserModel(); 
	        RoleModelInt role = ModelFactory.getInstance().getRoleModel(); 
	        if (OP_SIGN_IN.equalsIgnoreCase(op)) {
            UserDTO dto = (UserDTO) populateDTO(request);
            	try{
            		dto = model.authenticate(dto.getLogin(), dto.getPassword());
            			if (dto != null) {
            					session.setAttribute("userd", dto);// this session is use on
            					dto.setLastLogin(DataUtility.getCurrentTimestamp());
            					dto.setLastLoginIP(request.getRemoteAddr());
            					try {
            						model.update(dto);
            					} catch (DuplicateRecordException e) {
            						log.error(e);
            						ServletUtility.handleException(e, request, response);
            						return;
            					}
					long rollId = dto.getRoleId();
					RoleDTO roledto = role.findByPK(dto.getRoleId());
					String ro1=null;
					if (roledto != null) {
						ro1=roledto.getName();
						session.setAttribute("role", roledto.getName());
					}
					
					String str = request.getParameter("uri");
					String ro=request.getParameter("ro");
					if (str == "null" || "null".equalsIgnoreCase(str)) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						
					}
					else{
					ServletUtility.redirect(str, request, response);
					}
					return;
				}else {
					dto = (UserDTO) populateDTO(request);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}
                  }catch(ApplicationException e){
	                System.out.println(e.getMessage());
                   }
		
		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LOGIN_VIEW;
	}
	

}
