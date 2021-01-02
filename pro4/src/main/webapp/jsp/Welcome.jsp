<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 --><%@page import="in.co.sunrays.proj4.controller.ORSView"%>
<%@page import="in.co.sunrays.proj4.bean.UserBean"%>
<%@page import="in.co.sunrays.proj4.bean.RoleBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="">
<%@include file="Header.jsp"%>


<form action="<%=ORSView.WELCOME_CTL%>">

                <br><br><br><br><br><br><br><br><br>

                    <h1 align="Center">
                        <font size="14px" color="red">Welcome to Online Result System </font>
                    </h1>
                    <!-- 
                <br><br><br><br><br><br><br>
 -->                
                    
        
                    <%
                    UserBean beanUserBean = (UserBean) session.getAttribute("user");
                        if (beanUserBean != null) {
                            if (beanUserBean.getRoleId() == RoleBean.STUDENT) {
                    %> 
        
                    <h2 align="Center">
                        <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your Marksheet </a>
                    </h2>
                     
                      <%
                            }
                        }
                     %> 
                
                </form>
        
<%@include file="Footer1.jsp" %>
</body>
</html>