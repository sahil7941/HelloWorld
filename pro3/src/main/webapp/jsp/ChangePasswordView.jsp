<%@page import="in.co.sunrays.proj3.controller.ChangePasswordCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ChangePassword</title>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request"></jsp:useBean>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">
<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:65px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
              Change Password
			  
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">
            
			   <% String error=ServletUtility.getErrorMessage(request);
                //String sesstionMeassagea= (String) request.getAttribute("message");
                String Sucess=ServletUtility.getSuccessMessage(request);
                %>
			   <div class="form-group">
                <!--<label for="name" class="text-danger" style="align:center;">--------------Use for Mulipul error---------------</label>
                -->
                
             <%--    <input type="text" 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"form-control text-center text-success font-weight-bold":"form-control text-center text-danger font-weight-bold"%>"
                value="<%=error%><%=Sucess%>" readonly style="font-size:22px; ">
                 --%>
            		<h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
            
			   
			   	<fieldset>
				<legend>Password Information</legend></fieldset>
			    <div class="row">
			    
				<div class="col-md-12">
			    <div class="form-group">
                <label for="name">Current Password <font color="red">*</font></label>
                
                <input type="password" 
                class="<%=(ServletUtility.getErrorMessage("oldPassword", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Password" maxlength="12" name="oldPassword"
                value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "": DataUtility.getString(request.getParameter("oldPassword")))%>>
               <font color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font>
              
                </div>
				</div>
				
				<div class="col-md-12">
			    <div class="form-group">
				
                <label for="name">New Password<font color="red">*</font></label>
                   <input type="password" 
                class="<%=(ServletUtility.getErrorMessage("newPassword", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter ConfirmPassword" maxlength="12" name="newPassword"
                value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? "": DataUtility.getString(request.getParameter("newPassword")))%>>
                <font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font>
              
				</div>
				</div>
		     	
				<div class="col-md-12">
			    <div class="form-group">
				
                <label for="name">Retype-New Password<font color="red">*</font></label>
              
                <input type="password" 
                class="<%=(ServletUtility.getErrorMessage("confirmPassword", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter ConfirmPassword" maxlength="12" name="confirmPassword"
              		value=<%=DataUtility.getString(request
                    .getParameter("confirmPassword") == null ? "" : DataUtility
                    .getString(request.getParameter("confirmPassword")))%>>
                     <font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
                
				</div>
				</div>
				</div>	
			    
			<div class="row justify-content-center">
			  <div class="col-md-0"></div>
			  <div class="col-md-3">
			  <div class="form-group">
			  
			  <input type="submit" class="btn btn-success btn-block" value="<%=ChangePasswordCtl.OP_SAVE_CHANGES%>" name="operation"/>
			  
			  
			  <!--<button class="btn-primary btn-block form-control " vlaue="save">Save <i class="fas fa-save"></i></button>
              --></div></div>
			  
			  <div class="col-md-3">
			  <input type="submit" class="btn btn-danger btn-block" value="<%=ChangePasswordCtl.OP_CANCEL%>" name="operation"/>			  
			  <!--<button class="btn-primary btn-block form-control" vlaue="Reset">Reset <i class="fas fa-retweet"></i></button>
			  -->
			  <!--
                 <button class="btn btn-primary btn-block">Submit
				 
				 <i class="fas fa-sign-in-alt fa-lg"></i>
				 
				 </button>-->
              </div>
	  
			 </div>	
				</div>
            </form>
          </div>
         
        </div>
     </div>
</div>

</body>
<%@ include file="Footer.jsp"%>
</html>