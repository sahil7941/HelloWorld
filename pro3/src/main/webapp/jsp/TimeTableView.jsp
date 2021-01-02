<%@page import="java.sql.Time"%>
<%@page import="in.co.sunrays.proj3.controller.TimeTableCtl"%>

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
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.TimeTableDTO" scope="request"></jsp:useBean>
<%if(dto.getId()>0){ %>
<title>Update TimeTable</title>
<%}else{ %>
<title>Add TimeTable</title>
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
              Update TimeTable
              <%}else{ %>
              Add TimeTable
			  <%} %>
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.TIME_TABLE_CTL%>" method="post">
            
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
			  
			   <div class="form-group">
               
            <%--    <input type="text" 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"form-control text-center text-success font-weight-bold":"form-control text-center text-danger font-weight-bold"%>"
                value="<%=error%><%=Sucess%>" readonly style="font-size:22px; ">
               --%>
            
              <h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
            
            
            
            
               </div> 
			   
			   	<fieldset>
				<legend>TimeTable Information</legend></fieldset>
			    <div class="row">
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">Course Name <font color="red">*</font></label>
               
                <%
                 String cerr=ServletUtility.getErrorMessage("courseid", request);
                
                		List clist=(List)request.getAttribute("courseList");
                 
                 %>
                 <%=HTMLUtility.getList("courseid",String.valueOf(dto.getCourseId()),clist, cerr)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("courseid", request)%></font>
				
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
				
				<div class="col-md-12">
			    <div class="form-group">
			    
                <label for="name">Exam Date(mm/dd/yyyy)<font color="red">*</font></label>
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("examDate", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>"   
                name="examDate" readonly="readonly" placeholder="Click Here" value="<%=DataUtility.getDateString(dto.getExamDate())%>" id="datepicker">
                <font color="red"> <%=ServletUtility.getErrorMessage("examDate", request)%></font>              
               
                </div>
				</div>
<!-- -- --  --- ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->			
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">Semester<font color="red">*</font></label>
                    <%
                String er1=ServletUtility.getErrorMessage("semester", request);
                
                %>
                
                <% 
                HashMap<String,String> map1=new HashMap();
                		   map1.put("1","1");
                		   map1.put("2","2");
                		   map1.put("3","3");
                		   map1.put("4","4");
                		   map1.put("5","5");
                		   map1.put("6","6");
                		   map1.put("7","7");
                		   map1.put("8","8");
						
                
                String sen=HTMLUtility.getList("semester",dto.getSemester(),map1,er1);
                %>
               
                <%=sen %>
              
               <font color="red"><%=ServletUtility.getErrorMessage("semester", request)%></font>
             
				</div>
				</div>
				
				
				
<!-- -- --  --- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -->			
				
				
				
				
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="sel1">Exam Time<font color="red">*</font></label>
                    <%
                String er=ServletUtility.getErrorMessage("examTime", request);
                
                %>
                
                <% 
                HashMap<String,String> map=new HashMap();
                		   map.put("From 07AM To 10AM","From 07AM To 10AM");
						   map.put("From 11AM To 02PM","From 11AM To 02PM");
						   map.put("From 03AM To 06PM","From 03AM To 06PM");
						
                
                String gen=HTMLUtility.getList("examTime",dto.getTime(),map,er);
                %>
               
                <%=gen %>
              
               <font color="red"><%=ServletUtility.getErrorMessage("examTime", request)%></font>
             
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
										name="operation" value="<%=TimeTableCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=TimeTableCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn form-control btn-warning"
										name="operation" value="<%=TimeTableCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=TimeTableCtl.OP_RESET%>" />
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
	function noSunday(date) {
		return [ date.getDay() != 0, false ];
	};
		$(function() {
			$("#datepicker").datepicker({
				dateFormat : 'dd/mm/yy',
				minDate : new Date(),
				maxDate : "12/31/2021",
				changeMonth : true,
				changeYear : true,
				beforeShowDay : noSunday,
				yearRange : "2020:2021"
			});

		});
	</script>



</html>