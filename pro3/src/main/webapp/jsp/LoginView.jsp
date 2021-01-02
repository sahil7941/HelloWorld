<%@page import="in.co.sunrays.proj3.util.DataValidator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request"></jsp:useBean>
<%@page import="in.co.sunrays.proj3.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login </title>
<!-- <style type="text/css">
@media only screen and (max-width: 786px) {

.as{fixed-bottom}

 }
</style>
<style> -->


</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">
<div class="container-fluid" style="margin-top:80px; min-height:512px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
              Login
			  
            </h5>
          </div>
          
		  <div class="modal-body ">
            
			<form action="<%=ORSView.LOGIN_CTL%>" method="post">
                    <input type="hidden" name="uri" value="<%=request.getAttribute("uri")%>">
				     <input type="hidden" name="ro" value="<%=request.getAttribute("ro")%>">
					
					 <input type="hidden" name="id" value="<%=dto.getId()%>"> 
					 <input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
				     <input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>"> 
				     <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
				     <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
               
               
               
                <div class="form-group">
                <%
                String error=ServletUtility.getErrorMessage(request);
                String sesstionMeassagea= (String) request.getAttribute("message");
                String logoutSucess=ServletUtility.getSuccessMessage(request);
                %>
                
                <!--<label for="name" class="text-danger" style="align:center;">--------------Use for Mulipul error---------------</label>
                -->
                <!-- border-white bg-white -->
           
             <%--     <%if(ServletUtility.getSuccessMessage(request).equalsIgnoreCase("")) {%>
                <input type="text" class="form-control text-center text-success" value="<%=logoutSucess%>" readonly>
                --%>
             
             
             
             
             	<h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=logoutSucess%></h3>    
            
               <div class="form-group text-dark">
			  <i class="fas fa-envelope"></i> 	
			   
                <label for="email">Email-Id<font color="red">*</font>
                
                </label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("login", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>"
                placeholder="Enter Email Id" name="login" value="<%=DataUtility.getStringData(dto.getLogin())%>"  autofocus>
                
                <font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font>
                </div>
			    <!--<p class="help-block">Password should be at least 4 characters</p>-->
			    
			    <div class="form-group text-dark">
			    
			   <i class="fa fa-key"></i>  
                <label for="name">Password<font color="red">*</font>  
                <%
                String lo=ServletUtility.getErrorMessage("password", request);
                %>
               
                </label>
                
                <input type="password" class="<%=(lo.equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>"  placeholder="Enter Password"  name="password" value="<%=DataUtility.getStringData(dto.getPassword())%>">
                
               <font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font> 
                </div>
			 
			 	 <div class="row justify-content-center">
			 
			 <div class="col-md-3">
			  <div class="form-group">
			  
                <input type="submit" class="btn btn-primary btn-block" name="operation" value="<%=LoginCtl.OP_SIGN_IN%>"/ >  
               
               
               
               
               
                <!--  <button class="btn btn-primary btn-block">Submit
				 <i class="fas fa-sign-in-alt fa-lg"></i>
				 </button> -->
              </div></div>
               <div class="col-md-3">
			  <div class="form-group">
			  
                <input type="submit" class=" form-control btn btn-warning" name="operation" value="<%=LoginCtl.OP_SIGN_UP%>"/ ">  
               </div></div>
              
              
              
              </div>  
			  <div class="form-group fas fa-lock justify-content-center">
              <A class="text-secondary" href="<%=ORSView.FORGET_PASSWORD_CTL%>">Forget Password ?</A>&nbsp;&nbsp;
			<%--   <A href="<%=ORSView.USER_REGISTRATION_CTL%>">User Registraion</A>
			  --%> </div> 
            </form>
          </div>
         
        </div>
     </div>
</div>

</div>



</body>

 <div class="footer1 ">
<%@ include file="Footer.jsp"%>
</div> 
</html>