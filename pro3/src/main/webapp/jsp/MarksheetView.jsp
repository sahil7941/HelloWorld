<%@page import="in.co.sunrays.proj3.controller.MarksheetCtl"%>
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
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO" scope="request"></jsp:useBean>
<%if(dto.getId()>0) {%>
<title>Update Marksheet</title>
<%}else{ %>
<title>Add Marksheet</title>
<%} %>
</head>
<%@ include file="Header.jsp"%>
<body class="bodys bodys1">
<div class="container-fluid" style="margin-top:15px; min-height:510px; margin-bottom:30px;">

<div class="row justify-content-center" style=" min-height:526px;"><!-- jumbotron use for bg-->

<div class="col-md-6 text-dark">

        <div class="modal-content" style="margin-top:60px;">
          
		  <div class="modal-header justify-content-center bg-dark">
            <h5 class="modal-title " id="ModalTitle">
							<%if(dto.getId()>0) {%>
							Update MarkSheet
							<%}else{ %>
							Add MarkSheet
							<%} %>
							
			  
            </h5>
          </div>
          
		  <div class="modal-body">
            
			<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
            
            
			           <input type="hidden" name="id" value="<%=dto.getId()%>"> 
			           <input
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
			  <h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%><%=Sucess%></h3>    
            
			   	<fieldset>
				<legend>MarkSheet Information</legend></fieldset>
			    <div class="row">
				
				<div class="col-md-6">
				<div class="form-group">
                <label for="name">RollNo<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("rollNo", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter RollNo"  name="rollNo" 
                value="<%=DataUtility.getStringData(dto.getRollNo())%>"
                autofocus>
                <font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
                 
                </div>
			    </div>
				
				<div class="col-md-6">
			    <div class="form-group">
                <label for="sel1">Student Name<font color="red">*</font></label>
                        <%
                 String serr=ServletUtility.getErrorMessage("studentId", request);
                
                		List slist=(List)request.getAttribute("studentList");
                 
                 %>
                 <%=HTMLUtility.getList("studentId",String.valueOf(dto.getStudentId()),slist, serr)%>  
                 <font color="red"><%=ServletUtility.getErrorMessage("studentId", request)%></font>
				
				</div>
				</div>
				
				
				<div class="col-md-12">
				<div class="form-group">
                <label for="name">Physics Marks<font color="red">*</font></label>
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("physics", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Physics"  name="physics" 
                value="<%=DataUtility.getStringData(dto.getPhysics())%>" maxlength="3">
                <font color="red"><%=ServletUtility.getErrorMessage("physics", request)%></font>
                 
                </div>
			    </div>
				
				<div class="col-md-12">
			    <div class="form-group">
                <label for="name">Chemistry Marks<font color="red">*</font></label>
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("chemistry", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Chemistry"  name="chemistry" 
                value="<%=DataUtility.getStringData(dto.getChemistry())%>" maxlength="3">
                <font color="red"><%=ServletUtility.getErrorMessage("chemistry", request)%></font>
                </div>
				</div>
			    

                	
				<div class="col-md-12">
			    <div class="form-group">
                <label for="name">Maths Marks<font color="red">*</font></label>
                 <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("maths", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Maths"  name="maths" 
                value="<%=DataUtility.getStringData(dto.getMaths())%>" maxlength="3">
                <font color="red"><%=ServletUtility.getErrorMessage("maths", request)%></font>
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
										name="operation" value="<%=MarksheetCtl.OP_UPDATE%>" />
										
										<%} else{ %>
										<input type="submit" class="btn  form-control btn-success"
											name="operation" value="<%=MarksheetCtl.OP_SAVE%>" />
											<%} %>
									</div>
								</div>

								<div class="col-md-3">
									<%
										if (dto.getId() > 0) {
									%>
									<input type="submit" class="btn  form-control btn-warning"
										name="operation" value="<%=MarksheetCtl.OP_CANCEL%>" />
									<%
										} else {
									%>
									<input type="submit" class="btn form-control btn-danger"
										name="operation" value="<%=MarksheetCtl.OP_RESET%>" />
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
</html>