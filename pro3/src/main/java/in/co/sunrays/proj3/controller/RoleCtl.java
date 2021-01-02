package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.dto.BaseDTO;
import in.co.sunrays.proj3.dto.RoleDTO;
import in.co.sunrays.proj3.exception.ApplicationException;
import in.co.sunrays.proj3.exception.DuplicateRecordException;
import in.co.sunrays.proj3.model.ModelFactory;
import in.co.sunrays.proj3.model.RoleModelInt;
import in.co.sunrays.proj3.util.DataUtility;
import in.co.sunrays.proj3.util.DataValidator;
import in.co.sunrays.proj3.util.PropertyReader;
import in.co.sunrays.proj3.util.ServletUtility;
/**
 * Role functionality Controller. Performs operation for add, update
 * @author Adapterpattern
 * @version 1.0
 * Copyright (c) Adapterpattern
 */
@WebServlet(name="RoleCtl",urlPatterns={"/ctl/RoleCtl"})
public class RoleCtl extends BaseCtl{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public static Logger log=Logger.getLogger(RoleCtl.class);
	/**
	 * check input data validate or not
	 */
	@Override
	protected boolean validate(HttpServletRequest request) throws Exception {
	    log.debug("Method validate Start");
	    boolean pass=true;
	    if (DataValidator.isNull(request.getParameter("roleName"))) {
			request.setAttribute("roleName", PropertyReader.getValue("error.require", "Role Name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(request.getParameter("roleName"))) {
			request.setAttribute("roleName", PropertyReader.getValue("error.alpha", "Role Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} 
	    
	    log.debug("Method validate End");
		return pass;
	}
	/**
	 *Set all input data into the bean 
	 */
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
         log.debug("Method populateDTO Start");
         
         RoleDTO dto=new RoleDTO();
        
        dto.setId(DataUtility.getLong(request.getParameter("id")));

 		dto.setName(DataUtility.getString(request.getParameter("roleName")));

 		dto.setDescription(DataUtility.getString(request.getParameter("description")));

 		populateDTO(dto, request);
         
         log.debug("Method populateDTO End");
		return dto;
	}
	
	/**
	 * Contains display logic inside this method
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         log.debug("Method doGet Start");
         long id=DataUtility.getLong(request.getParameter("id"));
         String op=DataUtility.getString(request.getParameter("operation"));
         RoleModelInt model=ModelFactory.getInstance().getRoleModel();
         if(id>0 || op!=null){
        	 try {
				RoleDTO dto=model.findByPK(id);
				ServletUtility.setDTO(dto, request);
			} catch (ApplicationException e) {
				log.error(e);
			   ServletUtility.handleException(e, request, response);
				return;
			} 
         }
         ServletUtility.forward(getView(), request, response); 
         log.debug("Method doGet End");
	}
	/**
	 * Contains Submit logics inside this method
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   log.debug("Method doPost Start");
	      
		   String op=DataUtility.getString(request.getParameter("operation"));
		   long id=DataUtility.getLong(request.getParameter("id"));
		  
		   RoleDTO dto=(RoleDTO) populateDTO(request);
		   
		   RoleModelInt model= ModelFactory.getInstance().getRoleModel();
		   
			if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			    
			   
				   try {
				 if(id>0)
				 {
				    model.update(dto);
				    ServletUtility.setDTO(dto, request);
				    ServletUtility.setSuccessMessage("Data is successfully Updated", request);  
				 }
				 else{
					 long pk=model.add(dto);
				     dto.setId(pk);
				     ServletUtility.setSuccessMessage("Data is successfully saved", request);  
				 }
				   
 
				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					//e.printStackTrace();
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setDTO(dto, request);
					ServletUtility.setErrorMessage(""+e.getMessage(), request);
				}
			  
			   
			}else if(OP_RESET.equalsIgnoreCase(op)){
			  ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			   return;
		   }else if(OP_CANCEL.equalsIgnoreCase(op)){
			  ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			   return;
		   }
		   ServletUtility.forward(getView(), request, response);
	       log.debug("Method doPost End");
	}
	/**
	 * 
	 */
	@Override
	protected String getView() {
		
		return ORSView.ROLE_VIEW;
	}

}
