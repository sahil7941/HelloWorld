<%@page import="in.co.sunrays.proj4.controller.CourseCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 -->
<%@page import="in.co.sunrays.proj4.util.DataUtility"%>
<%@page import="in.co.sunrays.proj4.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
input[type=text], input[type=password], textarea {
	width: 186px;
	padding: 5px;
}

input[type=button], input[type=submit], input[type=reset] {
	padding: 5px 28px;
	border-radius: 4px;
}
</style>
</head>
<body>
	<form action="CourseCtl" method="POST">

		<div><%@ include file="Header.jsp"%></div>

		<script type="text/javascript"></script>


		<jsp:useBean id="bean" class="in.co.sunrays.proj4.bean.CourseBean"
			scope="request"></jsp:useBean>


		<%
			if (bean.getId() > 0) {
		%>
		<h1 align="center">Update Course</h1>
		<%
			} else {
		%>
		<h1 align="center">Add Course</h1>
		<%
			}
		%>

		<H2 align="center">
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font> <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
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
				<th align="left">Name<font color="red">*</font></th>
				<td><input style="width: 200px; padding: 5px;" type="text" name="name"
					placeholder="Enter Course Name"
					value="<%=DataUtility.getStringData(bean.getName())%>"><font
					color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>

			</tr>
			<tr>
				<th align="left">Description<font color="red">*</font></th>
				<td><textarea style="width: 200px; padding: 5px;"  name="description"
						style="resize: both;" placeholder="Description Of Course">
						<%=DataUtility.getStringData(bean.getDescription())%></textarea><font
					color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font></td>
			</tr>
			<tr>
				<th align="left">Duration<font color="red">*</font></th>
				<td>
					<%
						HashMap map = new HashMap();
						map.put("45 Days", "45 Day");
						map.put("6 Months", "6 Months");
						map.put("1 years", "1 years");
						map.put("2 years", "2 years");
						map.put("3 years", "3 years");
						map.put("4 years", "4 years");

						String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map, "Duration");
						DataUtility.getStringData("duration");
					%> <%=htmlList%> <font color="red"><%=ServletUtility.getErrorMessage("duration", request)%></font>

				</td>
			</tr>

			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td></td>
				<td><input type="submit" name="operation"
					value="<%=(bean.getId() <= 0) ? CourseCtl.OP_SAVE : CourseCtl.OP_UPDATE%>"
					style="width: 99px;"> <input type="submit" name="operation"
					value="<%=(bean.getId() <= 0) ? CourseCtl.OP_RESET : CourseCtl.OP_CANCEl%>" style="width: 97px;"></td>
			</tr>
		</table>


	</form>

	<div></div>

</body>
<%@ include file="Footer.jsp"%>
</html>