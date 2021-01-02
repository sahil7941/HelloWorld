<%@page import="in.co.sunrays.proj3.controller.LoginCtl"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="in.co.sunrays.proj3.dto.UserDTO"%>
<%@page import="in.co.sunrays.proj3.dto.RoleDTO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- use for responsive-->

<meta charest="utf-8">
<!-- use for hindi/english-->
<style type="text/css">
.no-drop {
	cursor: no-drop;
}
</style>
<%-- <link rel="stylesheet"
	href="<%=ORSView.APP_CONTEXT%>/web-fonts-with-css/css/fontawesome.css">
<link rel="stylesheet"
	href="<%=ORSView.APP_CONTEXT%>/web-fonts-with-css/css/fa-solid.css">
 --%> <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
    integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
    crossorigin="anonymous">
<!--
  <link href="https://use.fontawesome.com/releases/v5.0.6/css/all.css" rel="stylesheet">
  -->
<%-- <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/bootstrap.css">
 --%><link rel="stylesheet"
	href="<%=ORSView.APP_CONTEXT%>/css/bootstrap.min.css">

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery-3.2.1.min.js"></script>
<!--before use-->
<script src="<%=ORSView.APP_CONTEXT%>/js/bootstrap.js"></script>

<link href="https://fonts.googleapis.com/css?family=Pacifico"
	rel="stylesheet">
<!-- 
    <link rel="stylesheet" href="../css/bootstrap-iso.css">
    <link rel="stylesheet" href="./css/jquery-ui.css">
	<script src="./js/jquery-1.12.4.js"></script>
	<script src="./js/jquery-ui.js"></script>
	 -->

<style>
.fsize {
	font-size: 22px;
}

.bodys1 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/back.jpg');
	background-color: rgba(0, 0, 0, 0.3);
	background-blend-mode: overlay;
	color: #fff;
	min-height: 100%;
	background-attachment: fixed;
	background-repeat: no-repeat;
	background-size: cover;
	-moz-background-size: cover;
}

}
.bodys {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/back.jpg');
	background-color: rgba(0, 0, 0, 0.3);
	background-blend-mode: overlay;
	color: #fff;
	min-height: 100%;
	background-attachment: fixed;
	background-repeat: no-repeat;
	background-size: cover;
	-moz-background-size: cover;
}
}
</style>

<style>
@media ( min-width : 1023px) and (max-width: 1024px) {
	.RegFooter {
		position: fixed;
		left: 0;
		bottom: 0;
		width: 100%;
		text-align: center;
	}
	.bodys1 {
		background: url('<%=ORSView.APP_CONTEXT%>/img/back.jpg'); //
		background-image: image-url("beach.jpg");
		background-color: rgba(0, 0, 0, 0.3);
		padding-top: 80px;
		background-blend-mode: overlay;
		color: #fff;
		min-height: 100%;
		background-attachment: fixed;
		background-repeat: no-repeat;
		background-size: cover;
		-moz-background-size: cover;
	}
}
</style>



<style>
body {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/back.jpg');
	background-color: rgba(0, 0, 0, 0.2);
	background-blend-mode: overlay;
	color: #fff;
	padding: 0px 0px;
	box-sizing: border-box;
}

.navbar {
	border-bottom: black 0.4px solid;
	opacity: 0.8;
}

#ModalTitle {
	margin: 0;
	font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
		"Helvetica Neue", Arial, sans-serif, "Apple Color Emoji",
		"Segoe UI Emoji", "Segoe UI Symbol";
	font-size: 2rem;
	font-weight: normal;
	line-height: .6;
	color: white;
	opacity: 0.8;
}

label {
	display: inline-block;
	margin-bottom: .5rem;
}

@media ( min-width : 576px) {
}
</style>

</head>

<%
	UserDTO userdto = (UserDTO) session.getAttribute("userd");

	boolean userLoggedIn = userdto != null;

	String welcomeMsg = "Hi, ";
%>
<%
	if (userLoggedIn) {
		String role = (String) session.getAttribute("role");
		welcomeMsg += userdto.getFname() + " (" + role + ")";
	} else {

		welcomeMsg += "Guest";
	}
%>




<header> <!--Navbar--> <nav
	class="navbar  bg-dark navbar-expand-lg navbar-dark bg-secondary fixed-top ">

<div class="container-fluid">
<h1>
	<a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"> <strong><font color="red">R</font>ays</strong>
	</a></h1>
	<!-- it's use for there line -->
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent-7"
		aria-controls="navbarSupportedContent-7" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
		<!--Use for button-->
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent-7">
		<!--navbarSupportedContent-7 is use for menu word-spacing border-->
		<!--mr for margin-->

		<ul class="navbar-nav mr-auto">

			<li class="nav-item active"><a
				class="nav-link bg-dark text-white bg-secondary"
				href="<%=ORSView.WELCOME_CTL%>"> <i class="fa fa-home fa-lg"
					aria-hidden="true"></i> <span class="sr-only">(current)</span>
			</a></li>
			<%
				if (userLoggedIn) {
			%>

			<%
				if (userdto.getRoleId() != RoleDTO.ADMIN) {
			%>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-secondary" href="#"
				id="navbardrop" data-toggle="dropdown"> MarkSheet </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-secondary"
						href="<%=ORSView.GET_MARKSHEET_CTL%>">Get MarkSheet</a> <a
						class="dropdown-item text-white bg-secondary"
						href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">MarkSheet Merit
						List</a>
				</div></li>

			<%
				}
			%>

			<%
				if (userdto.getRoleId() == RoleDTO.ADMIN) {
			%>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fa fa-user" aria-hidden="true"></i> User </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.USER_CTL%>"><i class="fa fa-plus" aria-hidden="true"></i>Add User</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.USER_LIST_CTL%>"><i class="fas fa-list"></i>User List</a>
				</div></li>


			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fa fa-users" aria-hidden="true"></i> Role </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.ROLE_CTL%>"><i class="fa fa-plus" aria-hidden="true"></i>Add Role</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.ROLE_LIST_CTL%>"><i class="fas fa-list"></i>Role List</a>
				</div></li>


			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-university"></i> College </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.COLLEGE_CTL%>"><i class="fas fa-plus"></i>Add College</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.COLLEGE_LIST_CTL%>"><i class="fas fa-list"></i>College List</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-user-graduate"></i> Student </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.STUDENT_CTL%>"><i class="fas fa-plus"></i>Add Student</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.STUDENT_LIST_CTL%>"><i class="fas fa-list"></i>Student List</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-chalkboard-teacher"></i> Faculty </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.FACULTY_CTL%>"><i class="fas fa-plus"></i>Add Faculty</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.FACULTY_LIST_CTL%>"><i class="fas fa-list"></i>Faculty List</a>
				</div></li>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-laptop"></i> Course </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.COURSE_CTL%>"><i class="fas fa-plus"></i>Add Course</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.COURSE_LIST_CTL%>"><i class="fas fa-list"></i>Course List</a>
				</div></li>



			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-book-open"></i> Subject </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.SUBJECT_CTL%>"><i class="fas fa-plus"></i>Add Subject</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.SUBJECT_LIST_CTL%>"><i class="fas fa-list"></i>Subject List</a>
				</div></li>
			<li class="nav-item dropdown"><a  
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-list"></i> MarkSheet </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
				
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.MARKSHEET_CTL%>"><i class="fas fa-plus"></i>Add MarkSheet</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i class="fas fa-list"></i>Marksheet List</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.GET_MARKSHEET_CTL%>"><i class="fas fa-file-alt"></i>Get MarkSheet</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i class="fas fa-list"></i>MarkSheet Merit
						List</a>
				</div></li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"><i class="fas fa-clock"></i> TimeTable </a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.TIME_TABLE_CTL%>"><i class="fas fa-plus"></i>Add TimeTable</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.TIME_TABLE_LIST_CTL%>"><i class="fas fa-list"></i>TimeTable List</a>
				</div></li>


			<%
				}
			%>
			<%
				}
			%>
		</ul>

		<!-- 		
    <ul class="navbar-nav mr-right">
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle text-white bg-primary" href="#" id="navbardrop" data-toggle="dropdown">
        Hi i am guest
      </a>
	  bg color for dropdown-link-color
      <div class="dropdown-menu bg-primary">
        <a class="dropdown-item text-white bg-primary" href="MyprofileView.html">Myprofile</a>
        <a class="dropdown-item text-white bg-primary" href="ChangePasswordView.html">Change Password</a>
		<a class="dropdown-item text-white bg-primary" href="doc/index.html">JavaDoc</a>
        <a class="dropdown-item text-white bg-primary" href="LogoutView.html">Logout <i class="fas fa-sign-out-alt fa-lg"></i></a>
      </div>
	</li>	
	</ul> -->


		<ul class="navbar-nav mr-right">

			<%
				if (userLoggedIn) {
			%>

			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"> <%=welcomeMsg%>
			</a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.MY_PROFILE_CTL%>"><i class="fas fa-id-badge"></i>Myprofile</a> <a
						class="dropdown-item text-white bg-dark"
						href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i class="fas fa-key"></i>Change Password</a>
					<%
						if (userdto.getRoleId() == RoleDTO.ADMIN) {
					%>
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.JAVA_DOC_VIEW%>" target="blank"><i class="fas fa-file-alt"></i>JavaDoc</a>
					<%
						}
					%>
					<a class="dropdown-item text-white bg-dark"
						href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><i class="fas fa-sign-out-alt fa-lg"></i>Logout
						
					</a>
				</div></li>

			<%
				} else {
			%>

<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"> <%=welcomeMsg%>
			</a> <!--bg color for dropdown-link-color -->
				<div class="dropdown-menu bg-secondary">
				
					<a class=" dropdown-item text-white bg-dark "
						href="<%=ORSView.USER_REGISTRATION_CTL%>"><i class="fas fa-user-plus"></i>    Sign Up</a> <a
						class="dropdown-item text-white bg-dark "
						href="<%=ORSView.LOGIN_CTL%>"><i class="fas fa-sign-in-alt"></i>   Login</a>








<%-- <li class="nav-item dropdown"><a
				class="nav-link  text-white bg-dark" href="#"
				id="navbardrop" data-toggle="dropdown"> <%=welcomeMsg%>

			<li class="nav-item"><a class="nav-link text-white bg-dark"
				href="<%=ORSView.USER_REGISTRATION_CTL%>">Sign Up <i
					class="fas fa-user-plus"></i>

			</a></li>



			<li class="nav-item"><a class="nav-link text-white bg-dark"
				href="<%=ORSView.LOGIN_CTL%>">Login <i
					class="fas fa-sign-in-alt fa-lg"></i> <!--<i class="fas fa-sign-out-alt fa-lg"></i>-->
			</a></li>
 --%>			
                    
			<%
			
				}
			%>






		</ul>





		<!--
						<form class="form-inline">
                            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
                        </form>
						-->

	</div>
</div>
</nav> </header>

</html>