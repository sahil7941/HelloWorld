<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.controller.UserRegistrationCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration here</title>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.UserDTO"
	scope="request"></jsp:useBean>


</head>
<%@ include file="Header.jsp"%>

<body class="bodys bodys1">
	<div class="container-fluid"
		style="margin-top: 15px; min-height: 510px; margin-bottom: 15px;">

		<div class="row justify-content-center" style="min-height: 526px;">
			<!-- jumbotron use for bg-->

			<div class="col-md-6 text-dark">

				<div class="modal-content" style="margin-top: 60px;">

					<div class="modal-header  justify-content-center bg-dark">
						<h5 class="modal-title " id="ModalTitle">User Registration</h5>
					</div>

					<div class="modal-body">

						<form action="<%=ORSView.USER_REGISTRATION_CTL%>" method="post">

							<input type="hidden" name="id" value="<%=dto.getId()%>">
							<input type="hidden" name="createdBy"
								value="<%=dto.getCreatedBy()%>"> <input type="hidden"
								name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
								type="hidden" name="createdDatetime"
								value="<%=DataUtility.getTimestamp(dto.getCreatedDateTime())%>">
							<input type="hidden" name="modifiedDatetime"
								value="<%=DataUtility.getTimestamp(dto.getModifiedDateTime())%>">



							<%
								String error = ServletUtility.getErrorMessage(request);
								//String sesstionMeassagea= (String) request.getAttribute("message");
								String Sucess = ServletUtility.getSuccessMessage(request);
							%>
	<h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    

							<fieldset>
								<legend>Personal Information</legend>
							</fieldset>

							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<i class="fa fa-user" aria-hidden="true"></i> <label
											for="name">First Name<font color="red">*</font></label> <input type="text"
											class="<%=(ServletUtility.getErrorMessage("firstName", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
											placeholder="Enter FirstName" name="firstName"
											value="<%=DataUtility.getStringData(dto.getFname())%>"
											autofocus> <font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<i class="fa fa-user" aria-hidden="true"></i> <label
											for="name">Last Name<font color="red">*</font></label> <input type="text"
											class="<%=(ServletUtility.getErrorMessage("lastName", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
											placeholder="Enter LastName" name="lastName"
											value="<%=DataUtility.getStringData(dto.getLname())%>">
										<font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label for="sel1"><i class="fas fa-male"></i>/<i class="fas fa-female"></i>Gender<font color="red">*</font></label>


										<%
											String er = ServletUtility.getErrorMessage("gender", request);
										%>

										<%
											HashMap<String, String> map = new HashMap();
										%>

										<%
											map.put("Male", "Male");
											map.put("Female", "Female");

											String gen = HTMLUtility.getList("gender", dto.getGender(), map, er);
										%>

										<%=gen%>

										<font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>

										<!--  <select class="form-control" id="sel1">
                <option value="">Select</option>
				<option value="Male">Male</option>
                <option value="Female">Female</option>
				</select> -->

									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<i class="fa fa-birthday-cake" aria-hidden="true"></i> <label
											for="name">Date Of Birth(mm/dd/yyyy)<font color="red">*</font></label> <input
											type="text"
											class="<%=(ServletUtility.getErrorMessage("dob", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
											name="dob" readonly="readonly" placeholder="Click Here"
											value="<%=DataUtility.getDateString(dto.getDob())%>"
											id="datepicker"> <font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font>

									</div>
								</div>

							</div>

							<div class="form-group">
								<i class="fas fa-mobile-alt"></i> <label for="name">Mobile
									No<font color="red">*</font></label> <input type="text"
									class="<%=(ServletUtility.getErrorMessage("mobileNo", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
									placeholder="Enter MobileNo" maxlength="10" name="mobileNo"
									value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
								<font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
							</div>

							<fieldset>
								<legend>Account Infromation</legend>
								<hr></hr>
							</fieldset>
							<div class="form-group">
							<i class="fas fa-envelope"></i>
								<label for="email">Email(UserId)<font color="red">*</font></label> <input type="text"
									class="<%=(ServletUtility.getErrorMessage("login", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
									placeholder="Enter userId@gmail.com" name="login"
									value="<%=DataUtility.getStringData(dto.getLogin())%>">
								<font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font>
							</div>
							<!--<p class="help-block">Password should be at least 4 characters</p>-->
							<div class="form-group">
							 <i class="fa fa-key"></i>  
								<label for="name">Password<font color="red">*</font></label> <input type="password"
									class="<%=(ServletUtility.getErrorMessage("password", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
									placeholder="Enter Password" maxlength="12" name="password"
									value="<%=DataUtility.getStringData(dto.getPassword())%>">
								<font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font>
							</div>

							<div class="form-group">
							 <i class="fa fa-key"></i>  
								<label for="name">Confirm Password<font color="red">*</font></label> <input
									type="password"
									class="<%=(ServletUtility.getErrorMessage("confirmPassword", request).equalsIgnoreCase(""))
					? "form-control"
					: "form-control border border-danger"%>"
									placeholder="Enter ConfirmPassword" maxlength="12"
									name="confirmPassword"
									value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
								<font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
							</div>



							<div class="row justify-content-center">
								<div class="col-md-0"></div>
								<div class="col-md-3">
									<div class="form-group">
										<input type="submit" class="btn btn-success btn-block"
											name="operation" value="<%=UserRegistrationCtl.OP_SIGN_UP%>" />
									</div>
								</div>


								<div class="col-md-3">
									<input type="submit" class="btn btn-danger btn-block"
										name="operation" value="<%=UserRegistrationCtl.OP_RESET%>" />

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
<br>
<br>
<div class="RegFooter mfooter ">
	<%@ include file="Footer.jsp"%>
</div>

<%-- <link rel="stylesheet"
	href="<%=ORSView.APP_CONTEXT%>/css/bootstrap-iso.css"> --%>
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