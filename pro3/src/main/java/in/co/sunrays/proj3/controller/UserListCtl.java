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
import in.co.sunrays.proj3.exception.RecordNotFoundException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.model.UserModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;

/**
 * User functionality Controller. Performs operation for add, update and get
 * User
 * 
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	/**
	 * create Logger class of log4j
	 */
	Logger log = Logger.getLogger("UserListCtl.class");
	
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModelInt roleModel = ModelFactory.getInstance().getRoleModel();
		
		try {
			List l = roleModel.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
		dto.setFname(DataUtility.getString(request.getParameter("firstName")));
		dto.setLogin(DataUtility.getString(request.getParameter("login")));
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return dto;

	}

	/**
	 * contain Display logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("UserListCtl Method doGet Started");
		List list = null;
		// String op=DataUtility.getString(request.getParameter("opration"));
		int pageNo;
		int pn=DataUtility.getInt(request.getParameter("pNo"));
		if(pn>0)
		{
			pageNo = pn;
			//System.out.println("--------------------------------------"+pn);
		}else{
			pageNo = 1;	
		}
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserDTO dto = new UserDTO();
		UserModelInt model =ModelFactory.getInstance().getUserModel();

		try {
			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			
			list = model.search(dto, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Rocord Found", request);
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

		log.debug("UserListCtl Method doGet Ended");
	}

	/**
	 * contain Display action inside this method
	 * @throws ServletException
	 * @throws IOException
	 * 
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doPost Start");
		List list = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		UserDTO dto = (UserDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("chk_1");

		// System.out.println("bittu"+ifs);

		UserModelInt model = ModelFactory.getInstance().getUserModel();

		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
				list = model.search(dto, pageNo, pageSize);
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = pageNo;
				// System.out.println(ids);

				if (ids != null && ids.length > 0) {

					UserDTO deletedto = new UserDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						try {
							model.delete(deletedto);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					list = model.search(dto, pageNo, pageSize);	
					ServletUtility.setSuccessMessage("Data is successfully deleted", request);

				} else {

					ServletUtility.setErrorMessage("Select atleast one record", request);
					list = model.list(pageNo, pageSize);
					/*
					 * ServletUtility.setErrorMessage(
					 * "Select at least one record", request);
					 */
				}
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;

			}

			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			
			//list = model.search(dto, pageNo, pageSize);
			
			//ServletUtility.setList(list, request);
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setDTO(dto, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("UserListCtl doPost End");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}

}
