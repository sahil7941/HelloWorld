package in.co.sunrays.proj3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
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
 * User functionality Controller. Performs operation for add, update, and
 * reset,get User
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("UserCtl.class");

	/**
	 * preload all value inside this method
	 *
	 */

	protected void preload(HttpServletRequest request) {
		log.debug("UesrCtl Method preload Satrted");
		RoleModelInt model=ModelFactory.getInstance().getRoleModel();
		//RoleModel model = new RoleModel();
		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		log.debug("UesrCtl Method preload Ended");
	}

	/**
	 * Check Validate of all Value inside this method
	 */
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alpha", "First Name"));
			pass = false;
		}
		else if (DataValidator.isWhiteSpace(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alpha", "Last Name"));
			pass = false;
		}
		else if (DataValidator.isWhiteSpace(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasslength(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Min-6 and Max-12 digit"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		} else if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Confirm  Password  should not be matched");

			// ServletUtility.setErrorMessage("Confirm Password should not be
			// matched.", request);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roleId")))
	  {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role Id"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("Mobile no must start with 6,7,8,9"));
			pass = false;
		}
		
		
		
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}

		return pass;
	}

	/**
	 * set all Value in bean by Parameter
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setFname(DataUtility.getString(request.getParameter("firstName")));

		dto.setLname(DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));

		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		dto.setRegistredIP(request.getRemoteAddr());

		populateDTO(dto, request);

		return dto;

	}

	/**
	 * contain action Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));// it's id set
																	// by edit
																	// button of
																	// UserListView
		//UserModel model = new UserModel();
          UserModelInt model=ModelFactory.getInstance().getUserModel();
		if (id > 0 || op != null) {
			UserDTO dto;
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
		log.debug("UserCtl Method doGet Ended");
	}

	/**
	 * contain Display Logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("UserCtl Method doPost Started");
		long id = DataUtility.getLong(request.getParameter("id"));
		String op = DataUtility.getString(request.getParameter("operation"));
		//UserModel model = new UserModel();
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		if (OP_SAVE.equalsIgnoreCase(op) ||OP_UPDATE.equalsIgnoreCase(op))  {
			
			
			UserDTO dto = (UserDTO) populateDTO(request);

			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.setDTO(dto, request);
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
				ServletUtility.setErrorMessage("UserId(EmailId) already exists", request);
				request.setAttribute("login","LoginId is already exists ");
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Started");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_VIEW;
	}

}
