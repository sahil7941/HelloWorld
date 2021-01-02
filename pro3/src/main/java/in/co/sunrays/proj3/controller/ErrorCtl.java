package in.co.sunrays.proj3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj3.util.ServletUtility;

/**
 * Error functionality Controller. Performs operation for error Page
 * 
 * @author Adapterpattern
 * @version 1.0 
 * Copyright (c) Adapterpattern
 */
@WebServlet(name = "ErrorCtl", urlPatterns = { "/ErrorCtl" })
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger("ErrorCtl.class");

	/**
	 * Contains Display Logic inside this method
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("ErrorCtl Mehtod doGet Started");
		System.out.println("hi i am");
		ServletUtility.forward(getView(), request, response);
		log.debug("ErrorCtl Mehtod doGet Ended");

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ERROR_VIEW;
	}

}
