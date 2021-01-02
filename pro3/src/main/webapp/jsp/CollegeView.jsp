<%@page import="in.co.sunrays.proj3.controller.CollegeCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility" %>
<%@page import="in.co.sunrays.proj3.util.ServletUtility" %>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <%if(dto.getId()>0){ %>
  <title>Update College</title>
<%}else { %>
<title>Add College</title>
<%} %>

</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">
<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:65px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
              <%if(dto.getId()>0){ %>
              Update College
              <%}else{ %>
              Add College
			  <%} %>
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.COLLEGE_CTL%>" method="post">
        
            <%
                String error=ServletUtility.getErrorMessage(request);
                //String sesstionMeassagea= (String) request.getAttribute("message");
                String Sucess=ServletUtility.getSuccessMessage(request);
                %>      
           
            <h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
            
			   
			     <input type="hidden" name="id" value="<%=dto.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=dto.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
        
			   
			   
			   
			   	<fieldset>
				<legend>College Information</legend></fieldset>
			    <div class="row">
			    <div class="col-md-12">
			    <div class="form-group">
                <label for="name">College Name<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("collegeName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter CollegeName"  name="collegeName" 
                value="<%=DataUtility.getStringData(dto.getName())%>"
                autofocus>
                <font color="red"><%=ServletUtility.getErrorMessage("collegeName", request)%></font>
                
                </div>
				</div>
				<div class="col-md-12">
			    <div class="form-group">
                <label for="name">Phone No<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("mobileNo", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter MobileNo(+0000000000)" maxlength="10" name="mobileNo" value="<%=DataUtility.getStringData(dto.getPhoneNo()) %>"> 
                <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
               
                </div>
				</div>
				
				</div>
				
				<div class="row">
			    <div class="col-md-12">
			    <div class="form-group">
                <label for="comment">Address<font color="red">*</font></label>
                
                <textarea 
                class="<%=(ServletUtility.getErrorMessage("address", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                rows="3" id="comment" placeholder="Enter Address" name="address"><%=DataUtility.getStringData(dto.getAddress())%></textarea>
                <font color="red"><%=ServletUtility.getErrorMessage("address", request) %> </font>
                 </div>
				</div></div>
				
				
				<div class="row">
			    <div class="col-md-6">
			    <div class="form-group">
                <label for="name">City<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("cityName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter City Name"  name="cityName" 
                value="<%=DataUtility.getStringData(dto.getCity())%>">
                <font color="red"><%=ServletUtility.getErrorMessage("cityName", request)%></font>
               
                </div>
				</div>
				<div class="col-md-6">
			    <div class="form-group">
                <label for="name">State<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("stateName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter state Name"  name="stateName" 
                value="<%=DataUtility.getStringData(dto.getState())%>">
                <font color="red"><%=ServletUtility.getErrorMessage("stateName", request)%></font>
               
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
										name="operation" value="<%=CollegeCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=CollegeCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-warning"
										name="operation" value="<%=CollegeCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=CollegeCtl.OP_RESET%>" />
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
<div class="RegFooter mfooter">
<%@ include file="Footer.jsp"%>
</div>
</html>