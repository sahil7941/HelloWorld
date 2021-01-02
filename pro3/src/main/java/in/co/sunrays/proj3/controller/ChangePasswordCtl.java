package in.co.sunrays.proj3.controller;




import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * ChangePassword functionality Controller. Performs operation for changePassword
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {
	


	
public static Logger log=Logger.getLogger("ChangePasswordCtl.class");
 
 public static final String OP_CANCEL = "Cancel";
 public static final String OP_SAVE_CHANGES = "Save Changes";

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", PropertyReader.getValue("error.require", "Current Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPasslength(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "Min-6 and Max-12 length"));
			pass = false;
		}

		if (request.getParameter("oldPassword").equals(request.getParameter("newPassword"))) {

			request.setAttribute("newPassword", "Please Enter Different Password ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}

		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "Confirm  Password  does not Match");

			// ServletUtility.setErrorMessage("Confirm Password should not be
			// matched.", request);
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		
		log.debug("ChangePasswordCtl Mehtod populateBean Started");
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword(request.getParameter("oldPassword"));
		userDTO.setConfirmPassword(request.getParameter("confirmPassword"));
		populateDTO(userDTO, request);
		log.debug("ChangePasswordCtl Mehtod populateBean Ended");
		return userDTO;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("ChangePasswordCtl Method doPost Started");

		HttpSession session = request.getSession(true);

		UserDTO userDTO = (UserDTO) session.getAttribute("userd");
		UserDTO dto = (UserDTO) populateDTO(request);

		long id = userDTO.getId();

		//UserModel model = new UserModel();
         UserModelInt model=ModelFactory.getInstance().getUserModel();  
		String op = DataUtility.getString(request.getParameter("operation"));
		if (OP_SAVE_CHANGES.equals(op)) {

			try {

				boolean flag = model.changePassword(id, dto.getPassword(), dto.getConfirmPassword());
				if (flag == true) {
					dto = model.findByLogin(userDTO.getLogin());
					session.setAttribute("userd", dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Password has been changed Successfully.", request);
				}
			}
			 catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage("Current Password is Invalid", request);
			}
			catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("ChangePasswordCtl Method doPost Ended");
	}

		
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("ChangePasswordCtl Mehtod doGet Started");
		ServletUtility.forward(getView(), request, response);
		log.debug("ChangePasswordCtl Mehtod doGet Ended");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
