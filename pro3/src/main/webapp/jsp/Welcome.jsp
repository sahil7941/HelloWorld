<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Pacifico"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Autour+One"
	rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WelcomePage</title>
<style>
.banner-title {
	font-family: 'Autour One', cursive;
	font-weight: 900;
	font-size: 40px;
	text-transform: uppercase;
	margin-top: 30px;
	color: #FFFFFF;
	background: -webkit-linear-gradient(white, #0d3bff);
	-webkit-background-clip: text;
	-webkit-text-fill-color: ;
}
</style>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">

	<div class="container-fluid" style="margin-top: 100px;">

		<div class="row justify-content-center">

			<div class="col-md-12">

				<h1 class="banner-title text-center mt-5">

					WELCOME <br>
					<br>TO <br>
					<br> ONLINE RESULT SYSTEM

				</h1>
			</div>
		</div>

		<%
			session = request.getSession();
			UserDTO dto = (UserDTO) session.getAttribute("userd");
			if (dto != null) {
				if (dto.getRoleId() == RoleDTO.STUDENT) {
		%>
		<div class="row justify-content-center mr-top">
			<div class="col-md-12 text-center">
				<a href="<%=ORSView.GET_MARKSHEET_CTL%>"
					class="btn btn-lg btn-secondary text-center"
					style="border-radius: 15px">Get MarkSheet</a>
			</div>
			<!-- col -->
		</div>
		<%
			}
			} else {
		%>
		<div class="row justify-content-center mr-top"
			style="min-height: 48px;">
			<div class="col-md-12 text-center"></div>
			<!-- col -->
		</div>
		<%
			}
		%>
	</div>

	<div class="container" style="min-height: 228px"></div>

</body>


<div class="footer">
	<%@ include file="Footer.jsp"%>
</div>

</html>