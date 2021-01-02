<%@page import="in.co.sunrays.proj3.controller.FacultyCtl"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.FacultyDTO" scope="request" ></jsp:useBean>
<%if(dto.getId()>0){ %>
<title>Update Faculty</title>
<%}else{ %>
<title>Add Faculty</title>
<%} %>

</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">
<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:15px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
							<%
								if (dto.getId() > 0) {
							%>
							Update Faculty
							<%
								} else {
							%>
							Add Faculty
							<%} %>
						</h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.FACULTY_CTL%>" method="post">
            
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
				<legend>Faculty Information</legend></fieldset>
			    <div class="row">
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">College Name<font color="red">*</font></label>
                
                 <%
                 String err=ServletUtility.getErrorMessage("collegeId", request);
                
                		List list=(List)request.getAttribute("collegeList");
                 
                 %>
                 <%=HTMLUtility.getList("collegeId",String.valueOf(dto.getCollegeId()),list, err)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font>
				
				
				</div>
				</div>
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">Course Name<font color="red">*</font></label>
                
                  <%
                 String cerr=ServletUtility.getErrorMessage("courseId", request);
                
                		List clist=(List)request.getAttribute("courseList");
                 
                 %>
                 <%=HTMLUtility.getList("courseId",String.valueOf(dto.getCourseId()),clist, cerr)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
				
				</div>
				</div>
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">Subject Name<font color="red">*</font></label>
                      <%
                 String serr=ServletUtility.getErrorMessage("subjectId", request);
                
                		List slist=(List)request.getAttribute("subjectList");
                 
                 %>
                 <%=HTMLUtility.getList("subjectId",String.valueOf(dto.getSubjectId()),slist, serr)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
				
				</div>
				</div>
				
				
				<div class="col-md-6">
				<div class="form-group">
                <label for="name">First Name<font color="red">*</font></label>
                
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("firstName", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter FirstName"  name="firstName" 
                value="<%=DataUtility.getStringData(dto.getFirstName())%>"
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
                value="<%=DataUtility.getStringData(dto.getLastName())%>">
                <font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
                
                </div>
				</div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="name">Email Id<font color="red">*</font></label>
                  <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("login", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter userId@gmail.com" name="login"
                value="<%=DataUtility.getStringData(dto.getEmailId())%>">
                <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
               </div>
				</div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="sel1">Gender<font color="red">*</font></label>
                 <%
                String er=ServletUtility.getErrorMessage("gender", request);
                
                %>
                
                <% 
                HashMap<String,String> map=new HashMap();
                map.put("Male","Male");
                map.put("Female","Female");
                
                String gen=HTMLUtility.getList("gender",dto.getGender(),map,er);
                %>
               
                <%=gen %>
              
               <font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
             
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
                placeholder="Enter MobileNo" maxlength="10" name="mobileNo" value="<%=DataUtility.getStringData(dto.getMobNo()) %>"> 
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
										name="operation" value="<%=FacultyCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=FacultyCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-warning"
										name="operation" value="<%=FacultyCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=FacultyCtl.OP_RESET%>" />
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