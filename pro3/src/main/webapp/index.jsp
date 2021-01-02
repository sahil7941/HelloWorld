<%@page import="in.co.sunrays.proj3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Index Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

 <!-- <link rel="stylesheet" href="web-fonts-with-css/css/fontawesome.css"> -->
  <!-- <link rel="stylesheet" href="web-fonts-with-css/css/fa-solid.css"> -->
  <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/bootstrap.css">
  <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/bootstrap.min.css">
  
  
  <!-- <script src="js/jquery-3.2.1.min.js"> </script>--><!--before use-->
  <!-- <script src="js/bootstrap.js"></script> -->
  
   <link href="https://fonts.googleapis.com/css?family=Pacifico" rel="stylesheet">
  
  
  <link href="https://fonts.googleapis.com/css?family=Autour+One" rel="stylesheet">
  
  
  <style>
  body{
	marign:0;
	padding:0;
	font-family: 'Varela Round', sans-serif;
	font-size: 16px;
}
.custom-nav{
	border:0px;
	-webkit-border-radius: 0px;
	-moz-border-radius: 0px;
	-ms-border-radius: 0px;
	-o-border-radius: 0px;
	border-radius: 0px;
	background-color: #fff!important;
	-webkit-box-shadow: 0 0 10px rgba(0,0,0,0.5);
	box-shadow: 0 0 10px rgba(0,0,0,0.5);
}
  
header{

	
	padding:125px 50px;
	  
	
	background-blend-mode: overlay;
}
.banner-title{
	font-family: 'Autour One', cursive;
	
	
	font-weight: 1000;
	font-size: 50px;
	margin-top:50px;
}
  
  .bodys1 {
   background: url(img/back.jpg) no-repeat center center fixed;
    
	    background-color: rgba(0,0,0,0.2);
	
	
	background-blend-mode: overlay;
	color:#fff;
    min-height:100%;
    background-attachment: fixed;
    background-repeat: no-repeat;
    background-size: cover;
    -moz-background-size: cover;
}  
  
  </style>
<!--   <meta name="viewport" content="width=device-width, initial-scale=1">
 -->  </head>
<body class="bodys1">
<header>
<div class="container">
<div class="row justify-content-center">
<div class="col-md-12"><h1 class="banner-title text-center">
<font color="">Welcome</font><font color=""> to the</font><font color=""> ORS</font></h1></div>
</div>

<div class="row justify-content-center mr-top" style="margin-top: 10px;">
			
		 <div class="text-center">
              <img class="img-fluid" src="assets/images/customLogo.jpg" class="" alt="" title="Rays Technology"/></div>
			</div> 
			


		<br>
<div class="row justify-content-center">
			<div class="text-center">
			<a href="<%=ORSView.WELCOME_CTL%>" class="btn btn-lg btn-primary text-center" style="border-radius:15px" title="Click Here" >
			
			Online Result System
			
			</a>
			</div><!-- col -->
		</div>
</div></header>
</body>
</html>