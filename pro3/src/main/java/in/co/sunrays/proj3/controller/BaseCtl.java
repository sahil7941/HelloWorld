package in.co.sunrays.proj3.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.UserDTO;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.ServletUtility;

/*
import com.raystech.proj3.dto.UserDTO;
import com.raystech.proj3.util.DataUtility;
import com.raystech.proj3.util.DataValidator;
import com.raystech.proj3.util.ServletUtility;
*/
/**
 * Servlet implementation class BaseCtl
 */
public abstract class BaseCtl extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	
	
	
	/**
	 * Save operation key constant
	 */
	public static final String OP_SAVE = "Save";
	public static final String OP_UPDATE = "Update";

	/**
	 * Cancel operation key constant
	 */
	public static final String OP_CANCEL = "Cancel";

	/**
	 * Reset operation key constant
	 */
	public static final String OP_RESET = "Reset";

	/**
	 * Delete operation key constant
	 */

	public static final String OP_DELETE = "Delete";

	/**
	 * List operation key constant
	 */
	public static final String OP_LIST = "List";

	/**
	 * Search operation key constant
	 */
	public static final String OP_SEARCH = "Search";

	/**
	 * View operation key constant
	 */
	public static final String OP_VIEW = "View";

	/**
	 * Next operation key constant
	 */
	public static final String OP_NEXT = "Next";

	/**
	 * Previous operation key constant
	 */
	public static final String OP_PREVIOUS = "Previous";

	/**
	 * New operation key constant
	 */
	public static final String OP_NEW = "New";

	/**
	 * Go operation key constant
	 */
	public static final String OP_GO = "Go";

	/**
	 * Back operation key constant
	 */
	public static final String OP_BACK = "Back";

	/**
	 * Logout operation key constant
	 */
	public static final String OP_LOG_OUT = "Logout";

	/**
	 * Success message key constant
	 */
	public static final String MSG_SUCCESS = "success";

	/**
	 * Error message key constant
	 */
	public static final String MSG_Error = "error";


	/**
	 * Use Button for Subject Name and Marks
	 */
	
	public static final String Physics = "Physics";
	public static final String Chemistry = "Chemistry";
	public static final String MATHS = "Maths";
	public static final int MAX_MARKS = 100;
	public static final int MIN_MARKS = 35;
	public static final int TOTAL_MARKS = MAX_MARKS * 3;

	
	
	
	
	
	
	
	
	/**
	 * Validates input data entered by User
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected boolean validate(HttpServletRequest request) throws Exception {
		return true;
	}

	/**
	 * Loads list and other data required to display at HTML form
	 *
	 * @param request
	 */

	protected  void preload(HttpServletRequest request) {
	}

	/**
	 * Populates bean object from request parameters
	 *
	 * @param request
	 * @return BaseBean
	 */

	protected BaseDTO populateDTO(HttpServletRequest request) {
		return null;

	}

	/**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return BaseBean
	 */
	protected BaseDTO populateDTO(BaseDTO dto, HttpServletRequest request) {
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserDTO userbean = (UserDTO) request.getSession().getAttribute("userd");

		if (userbean == null) {
			createdBy = "root";
			modifiedBy = "root";

		} else {
			modifiedBy = userbean.getLogin();
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDateTime(DataUtility.getCurrentTimestamp());

		} else {
			dto.setCreatedDateTime(DataUtility.getCurrentTimestamp());

		}
		dto.setModifiedDateTime(DataUtility.getCurrentTimestamp());

		return dto;

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		preload(request);

		String op = DataUtility.getString(request.getParameter("operation"));
		
		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op)) {
			// Check validation, If fail then send back to page with error
			// messages
			try {
				if (!validate(request)) {
					BaseDTO dto = populateDTO(request);
					ServletUtility.setDTO(dto, request);
					ServletUtility.forward(getView(), request, response);
					return;
					}
				}catch (Exception e){
				e.printStackTrace();
			}
		}
		super.service(request, response);
	}

	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return String
	 */
	protected abstract String getView();

}