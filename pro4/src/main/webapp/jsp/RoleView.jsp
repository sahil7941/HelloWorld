 <%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>  --%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<%@page import="in.co.sunrays.proj4.controller.RoleCtl"%>
<%@page import="in.co.sunrays.proj4.controller.BaseCtl"%>
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>Insert title here</title>
</head>
<%@include file="Header.jsp"%>
<body bgcolor="">
	


	<form action="RoleCtl" method="post">

		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.RoleBean"
			scope="request"></jsp:useBean>

		<center>

			<%
				if (bean.getId() > 0) {
			%>
			<h1>Update Role</h1>
			<%
				} else {
			%>
			<h1>Add Role</h1>
			<%
				}
			%>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table align="center" style="margin-left: 40%">
				<tr>
					<th align="left">Name<font style="color: red">*</font></th>
					<td><input style="width: 200px; padding: 5px;" type="text"
						name="name" placeholder="Enter Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description<font style="color: red">*</font></th>
					<td><input style="width: 200px; padding: 5px;" type="text"
						name="description" placeholder="Enter Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"><font
						color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td></td>
					<td><input
						style="padding: 5px 28px; border-radius: 4px; width: 99px;"
						type="submit" name="operation"
						value="<%=(bean.getId() <= 0) ? RoleCtl.OP_SAVE : RoleCtl.OP_UPDATE%>"
						style="width: 99px"> <input
						style="padding: 5px 28px; border-radius: 4px; width: 99px;"
						type="submit" name="operation" value="<%=(bean.getId() <= 0) ? RoleCtl.OP_RESET : RoleCtl.OP_CANCEl%>"
						style="width: 97px"></td>
				</tr>
			</table>
	</form>
	</center>


</body>
<br><br><br><br>
<%@include file="Footer1.jsp"%>	
</html>