<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.controller.MyProfileCtl"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.proj3.controller.ORSView" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Profile View</title>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO" scope="request"></jsp:useBean>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys1">
<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:65px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
              My Profile 
			  
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">
             
             
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
            
			   	<input type="hidden" name="id" value="<%=dto.getId()%>"> <input
						type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>">
					<input type="hidden" name="modifiedBy"
						value="<%=dto.getModifiedBy()%>"> <input type="hidden"
						name="createdDatetime"
						value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
					<input type="hidden" name="modifiedDatetime"
						value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">
			   
			   
			   	<fieldset>
				<legend>Personal Information</legend></fieldset>
			    <div class="row">
			    <div class="col-md-12">
			    <div class="form-group">
                <label for="email">EmailId(UserId)<font color="red">*</font></label>
                
                <input type="email" class="form-control" title="Can't be change UserId"  name="login" value="<%=DataUtility.getStringData(dto.getLogin())%>" placeholder="Enter userId@gmail.com" readonly>
               
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
				
		     	
				
				<div class="col-md-12">
			   <div class="form-group">
                <label for="name">Mobile No<font color="red">*</font></label>
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("mobileNo", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter MobileNo(+0000000000)" maxlength="10" name="mobileNo" value="<%=DataUtility.getStringData(dto.getMobileNo()) %>"> 
                <font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
                
                </div>
				</div>
				
				
				</div>	
	       	<div class="row justify-content-center">
			  <div class="col-md-1"></div>
			  <div class="col-md-4">
			  <div class="form-group">
			  <input type="submit" class="btn btn-info btn-block" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>" name="operation"/>
			 <!-- <button class="btn-primary btn-block form-control " vlaue="save">Save <i class="fas fa-save"></i></button>
              --></div></div>
			  
			  <div class="col-md-4">
			  <input type="submit" class="btn btn-success btn-block" value="<%=MyProfileCtl.OP_SAVE%>" name="operation"/>			  
			  <!--<button class="btn-primary btn-block form-control " vlaue="Reset">Reset <i class="fas fa-retweet"></i></button>
			  -->
			  <!--
                 <button class="btn btn-primary btn-block">Submit
				 
				 <i class="fas fa-sign-in-alt fa-lg"></i>
				 
				 </button>-->
              </div>
<div class="col-md-1"></div>			  
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