<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ForgetPassword here</title>
<style type="text/css">
@media (max-width:50%) {
.masn
{
min-height:500px; 
      }
}

</style>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1 ">
<div class="container-fluid" style="margin-top:100px; min-height:400px; margin-bottom:0px;">

<div class="row justify-content-center" style=" min-height:506px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
              Forget Password
			  
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">
            
			   <div class="form-group">
                <!--<label for="name" class="text-danger" style="align:center;">--------------Use for Mulipul error---------------</label>
                -->
                
             <%--    <input type="text" 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"form-control text-center text-success font-weight-bold":"form-control text-center text-danger font-weight-bold"%>"
                value="<%=ServletUtility.getErrorMessage(request)%><%=ServletUtility.getSuccessMessage(request)%>" readonly style="font-size:22px;">
                 --%>
               <%
								String error = ServletUtility.getErrorMessage(request);
								//String sesstionMeassagea= (String) request.getAttribute("message");
								String Sucess = ServletUtility.getSuccessMessage(request);
							%>
              
           	<h3 
            class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
            style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
			   	<fieldset>
				<legend class="text-secondary">Submit your email address and we'll send you password</legend></fieldset>
			    <div class="row ">
			    
				<div class="col-md-12">
			    <div class="form-group ">
			    <i class="fa fa-envelope" aria-hidden="true"></i>
			    
                <label for="name ">Email-Id<font color="red">*</font>&nbsp;&nbsp;&nbsp;
                
                </label>
                <input type="email" class="<%=(ServletUtility.getErrorMessage("login",request).equalsIgnoreCase(""))?"form-control":"form-control border-danger" %>" 
                placeholder="Enter EmailId@gmail.com" name="login" autofocus>
                <font color="red"><%=ServletUtility.getErrorMessage("login",request)%></font>
                </div>
				
				<label for="name"></label>
				</div>
				
				</div>
			<div class="row  justify-content-center">
			  <div class="col-md-3">
			  <div class="form-group">
			  <input type="submit" class="btn btn-success btn-block" name="operation" value="Go"/>
			  <!--<button class="btn-primary btn-block form-control " vlaue="save">Save <i class="fas fa-save"></i></button>
              --></div></div>
			  
			  <div class="col-md-3">
			  <input type="submit" class="btn btn-danger btn-block" name="operation" value="Cancel"/>			  
			  <!--<button class="btn-primary btn-block form-control" vlaue="Reset">Reset <i class="fas fa-retweet"></i></button>
			  -->
			  <!--
                 <button class="btn btn-primary btn-block">Submit
				 
				 <i class="fas fa-sign-in-alt fa-lg"></i>
				 
				 </button>-->
              </div></div>
			  
		
				</div>
	</form>
          </div>
         
        </div>
     </div>
</div>
</body>

 <div class="footer1 footer12">
<%@ include file="Footer.jsp"%>
</div> 
</html>