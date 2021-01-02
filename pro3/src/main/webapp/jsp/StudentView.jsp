<%@page import="in.co.sunrays.proj3.controller.StudentCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility" %>
<%@page import="in.co.sunrays.proj3.util.ServletUtility" %>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.StudentDTO" scope="request"></jsp:useBean>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%if(dto.getId()>0){ %>
<title>Update Student</title>
<%}else{ %>
<title>Add Student</title>
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
				Update Student
				<%}else{ %>
				Add Student
				<%} %>
              
			  
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.STUDENT_CTL%>" method="post">
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
				<legend>Student Information</legend></fieldset>
			    <div class="row">
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">College Name</label>
                   <%
                 String err=ServletUtility.getErrorMessage("collegeId", request);
                
                		List list=(List)request.getAttribute("collegeList");
                 
                 %>
                 <%=HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeID()),list, err)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
				
				
				</div>
				</div>
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="name">Email Id<font color="red">*</font></label>
                
                  <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("login", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter userId@gmail.com" name="login"
                value="<%=DataUtility.getStringData(dto.getEmail())%>" <%=(dto.getId() > 0) ? "readonly" : ""%>>
                <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
              
                </div>
				</div>
				
				
				<div class="col-md-6">
				<div class="form-group">
                <label for="name">First Name<font color="red">*</font></label>
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("firstName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter FirstName"  name="firstName" 
                value="<%=DataUtility.getStringData(dto.getFname())%>"
                autofocus>
                <font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
                </div>
			    </div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="name">Last Name<font color="red">*</font></label>
                
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("lastName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>"  
                placeholder="Enter LastName" name="lastName" 
                value="<%=DataUtility.getStringData(dto.getLname())%>">
                <font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
               
                </div>
				</div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="name">Date Of Birth(mm/dd/yyyy)<font color="red">*</font></label>
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("dob", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>"   
                name="dob" readonly="readonly" placeholder="Click Here" value="<%=DataUtility.getDateString(dto.getDob())%>" id="datepicker">
                <font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>              
                </div>
				</div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="name">Mobile No<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("mobileNo", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter MobileNo" maxlength="10" name="mobileNo" value="<%=DataUtility.getStringData(dto.getMobileNo()) %>"> 
                <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
               
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
										name="operation" value="<%=StudentCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=StudentCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-warning"
										name="operation" value="<%=StudentCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=StudentCtl.OP_RESET%>" />
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
<div>
<div class="RegFooter mfooter">
<%@ include file="Footer.jsp"%>
</div>
</div>
<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/bootstrap-iso.css">
<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/jquery-ui.css">
	<script src="<%=ORSView.APP_CONTEXT%>/js/jquery-1.12.4.js"></script>
	<script src="<%=ORSView.APP_CONTEXT%>/js/jquery-ui.js"></script>
	
	<script>
		$(function() {
			$("#datepicker").datepicker({
				dateFormat : 'mm/dd/yy',
				defaultDate : "01/01/1982",
				changeMonth : true,
				changeYear : true,
				yearRange : '-35:-18' 
			});

		});
	</script>
</html>