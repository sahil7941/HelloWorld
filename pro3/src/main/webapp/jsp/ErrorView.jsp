<%@page import="in.co.sunrays.proj3.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page isErrorPage="true"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
</head>
<body bgcolor="white">

	<br>
	<br>
	<br>

	<h1 align="center">
		<font color="red">OOPS...!! Plzzz.. Check Your Connection</font>
	</h1>

	<h1 align="center">
		<a href="<%=ORSView.WELCOME_CTL%>"><%=BaseCtl.OP_BACK%></a>
	</h1>

</body>
</html>