package in.co.sunrays.proj3.controller;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.util.DataUtility;
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
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 * 
 * @author Adapterpattern
 * @version 1.0 Copyright (c) Adapterpattern
 */
@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	/**
	 * create Logger class of log4j
	 */
	private static Logger log = Logger.getLogger(RoleListCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModelInt rModel = ModelFactory.getInstance().getRoleModel();
		try {
			List roleList = rModel.list();
			request.setAttribute("roleList", roleList);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * set all Value in dto by Parameter
	 */

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		RoleDTO dto = new RoleDTO();
		
		dto.setName(DataUtility.getString(request.getParameter("roleName")));
		dto.setCreatedBy(DataUtility.getString(request.getParameter("createdBy")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setId(DataUtility.getLong(request.getParameter("roleId")));
		return dto;
	}

	/**
	 * Contains Display logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleListCtl doGet Start");
		List list = null;
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
		RoleDTO dto = (RoleDTO) populateDTO(request);

		//String op = DataUtility.getString(request.getParameter("operation"));
		//System.out.println(op);
		
		RoleModelInt  model = ModelFactory.getInstance().getRoleModel();
		try {
			
			List pageno=model.search(dto);
			request.setAttribute("pageno",pageno);
			list = model.search(dto, pageNo, pageSize);
			
			ServletUtility.setList(list, request);
			
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			// ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("RoleListCtl doGet End");
	}

	/**
	 * Contains Submit logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleListCtl doPost Start");
		List list = null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		RoleDTO dto = (RoleDTO) populateDTO(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("chk_1");

		RoleModelInt  model = ModelFactory.getInstance().getRoleModel();

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
				ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
				return;
			} 
			else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = pageNo;
				if (ids != null && ids.length > 0) {
					
					RoleDTO deletedto = new RoleDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getInt(id));
						try {
							model.delete(deletedto);
						} catch (ApplicationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ServletUtility.setSuccessMessage("Data is successfully deleted", request);
					list = model.search(dto, pageNo, pageSize);
				} else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
					//ServletUtility.setErrorMessage("Select atleast one record ", request);
					/*
					 * ServletUtility.setErrorMessage(
					 * "Select at least one record", request);
					 */
				   list = model.list(pageNo, pageSize);	   
				}
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;

			}
			List pageno=model.search(dto);
			
			request.setAttribute("pageno",pageno);
			
			//list = model.search(dto, pageNo, pageSize);
			
			ServletUtility.setList(list, request);
			
			System.out.println("========================+++++++++++++++++++++++++++++++++++++++"+list.size());
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
		log.debug("RoleListCtl doPost End");
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}

}