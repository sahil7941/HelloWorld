package in.co.sunrays.proj3.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 *
 * @author Abstract Factory
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	static final long serialVersionUID = 1L;

	public static final String OP_SIGN_UP = "SignUp";
	

	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserRegistrationCtl Method validate Started");
		boolean pass = true;

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (!DataValidator.isName2(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alpha", "First Name"));
			pass = false;
		}
		
		else if (DataValidator.isWhiteSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First Name"));
			pass = false;
		}
		
		
		
		
		
		
		
		
		if (DataValidator.isNull(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (!DataValidator.isName2(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alpha", "Last Name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login "));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("Mobile no must start with 6,7,8,9"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasslength(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.pwdlength", "Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			// ServletUtility.setErrorMessage("ConfirmPassword is not matched",
			// request);

			request.setAttribute("confirmPassword", "Confirm password does not match ");
			pass = false;

		}
		log.debug("UserRegistrationCtl method validate end");
		System.out.println(pass);
		return pass;

	}

	/**
	 * Populates bean object from request parameters
	 *
	 * @param request
	 * @return
	 */
	protected BaseDTO populateDTO(HttpServletRequest request) {

		log.debug("UserRegistrationCtl Method populatedto Started");

		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(RoleDTO.STUDENT);

		dto.setFname(DataUtility.getString(request.getParameter("firstName")));

		dto.setLname(DataUtility.getString(request.getParameter("lastName")));

		dto.setLogin(DataUtility.getString(request.getParameter("login")));

		dto.setPassword(DataUtility.getString(request.getParameter("password")));

		dto.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));

		dto.setDob(DataUtility.getDate(request.getParameter("dob")));
		
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		
	

		populateDTO(dto, request);

		log.debug("UserRegistrationCtl Method populatedto Ended");

		return dto;

	}

	/**
	 * Display concept of user registration
	 */

	protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {

		log.debug("UserRegistrationCtl Method doGet Started");
		ServletUtility.forward(getView(), request, response);

	}

	/**
	 * Submit concept of user registration
	 */
	protected void doPost(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
			throws javax.servlet.ServletException, java.io.IOException {

		log.debug("UserRegistrationCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				long pk = model.registerUser(dto);
				dto.setId(pk);
				request.getSession().setAttribute("Userdto", dto);
				ServletUtility.setSuccessMessage("You have been registered successfully ", request);
				ServletUtility.forward(ORSView.USER_REGISTRATION_VIEW, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
		}

		log.debug("UserRegistrationCtl Method doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
