<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="in.co.sunrays.proj3.controller.RoleCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility" %>
<%@page import="in.co.sunrays.proj3.util.ServletUtility" %>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.RoleDTO" scope="request"></jsp:useBean>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%if((dto.getId())>0){ %>
<title>Update Role</title>
<%}else { %>
<title>Add Role</title>
<%} %>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys1">

<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:65px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
            <%if((dto.getId())>0){ %>
              Update Role
			  <%}else {%>
			  Add Role
			  <%} %>
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.ROLE_CTL%>" method="Post">
                  
                   <input type="hidden" name="id" value="<%=dto.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=dto.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
                
                  
                  
                  
                 <%
                String error=ServletUtility.getErrorMessage(request);
                //String sesstionMeassagea= (String) request.getAttribute("message");
                String Sucess=ServletUtility.getSuccessMessage(request);
                %>
			  
			  <h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
            
			   	<fieldset>
				<legend>Role Information</legend></fieldset>
			    <div class="row">
			    <div class="col-md-12">
			    <div class="form-group">
                <label for="name">Role Name<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("roleName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Role Name"  name="roleName" 
                value="<%=DataUtility.getStringData(dto.getName())%>"
                autofocus>
                <font color="red"><%=ServletUtility.getErrorMessage("roleName", request)%></font>
               
                </div>
				</div>
				<div class="col-md-12">
			    <div class="form-group">
                <label for="comment">Description<font color="red">*</font></label>
                
                <textarea 
                class="<%=(ServletUtility.getErrorMessage("description", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                rows="3" id="comment" placeholder="Enter Role Description" name="description"><%=DataUtility.getStringData(dto.getDescription())%></textarea>
                <font color="red"><%=ServletUtility.getErrorMessage("description", request) %> </font>
                </div>
				</div>
		     	</div>	
			    
			<div class="row justify-content-center d-flex">

								<div class="col-md-3">
									<div class="form-group">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-success"
										name="operation" value="<%=RoleCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=RoleCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-warning"
										name="operation" value="<%=RoleCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=RoleCtl.OP_RESET%>" />
									<%
										}
									%>
									<!--
                 <button class="btn btn-primary btn-block">Submit
				 
				 <i class="fas fa-sign-in-alt fa-lg"></i>
				 
				 </button>-->
								</div>
							</div>
            </form>
          </div>
         
        </div>
     </div>
</div>

</div>

</body>
<div class="mfooter">
<%@ include file="Footer.jsp"%>
</div>
</html>