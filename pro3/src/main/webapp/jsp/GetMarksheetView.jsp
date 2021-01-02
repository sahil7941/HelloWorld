
<%@page import="in.co.sunrays.proj3.controller.GetMarksheetCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@page import="in.co.sunrays.proj3.util.DataUtility" %>
<%@page import="in.co.sunrays.proj3.dto.MarksheetDTO"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility" %>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO" scope="request"></jsp:useBean>

<title>Get Marksheet</title>

</head>


<%@include file="Header.jsp"%>
<body class="bodys bodys1">
<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post" name="frm">

 
 <div class="container-fluid" style="margin-top:65px; min-height:550px; margin-bottom:px;"><!--
  <h2>Card Header and Footer</h2>-->
  
    <div class="card">
	<div class="card-body">
	

  <!---w-25 width h-25 height -->
  
    <div class="modal-content" style="margin-bottom:-9px;">
   

		  <div class="modal-body" style="margin-bottom:-15px;">
		  
		  
		   <div class="modal-header justify-content-center bg-dark">
            <h4 class="modal-title " id="ModalTitle">
              Get Marksheet
            </h4>
          </div>
          
		  
		  <div class="row justify-content-center" style="margin-top:15px;">
			    <fieldset style="color:black; ">
				<legend>Roll No:</legend></fieldset>
			    
			    <div class="col-md-3">
			    <div class="form-group">
                
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("rollNo", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter Roll Number"  name="rollNo" 
                value="<%=ServletUtility.getParameter("rollNo", request)%>"
                autofocus>
                <font color="red" ><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
                  
                  
                </div>
				</div>
				
				
			    <div class="col-md-1">
			    <div class="form-group">
                <input type="submit" class="form-control text-white btn btn-success btn-block" value="<%=GetMarksheetCtl.OP_GO%>" name="operation">
                </div>
				</div>
				<div class="col-md-1">
			    <div class="form-group">
                <input type="submit" class="form-control  text-white btn btn-danger btn-block" value="<%=GetMarksheetCtl.OP_RESET%>" name="operation"/>
                </div>
				</div>
			   
			   
			   
			   </div>	
	    
	</div>
    </div>
	</div>
	<div class="container-fluid">
	
	<!--Action Perform for Delete and New-->
	
	

	<!--close card-body-->
	
      <div class="card-body justify-content-center text-left " style="margin-top:-20px;">
	    <%String error=ServletUtility.getErrorMessage(request); %>
	     <%if((ServletUtility.getErrorMessage(request)!=(""))){%>
        
        <input type="text" 
                class="form-control text-center text-danger font-weight-bold"
                value="<%=error%>" readonly style="font-size:22px; ">
   
    <%} %>
	 
	 
	 
	<%
						if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0)  {
					%>
   <table class="table table-responsive" style="width:100%; margin-top:-18;">
    
    <thead style="width:100%">
      
      <tr class="text-black" align="center">
	    
   
                    <th width="10%"><font color="white"></font></th>                   
                    <!-- <th width="10%"><font color="white"></font></th> -->
                    <th width="10%" colspan="5"><font color="">
                    
                    <table border="2" width="800px" height="" style="color:black;">
						<tr class="bg-dark text-white">
							<td colspan="6" align="center"><h3>MarkSheet</h3></td>
						</tr>
						<tr bgcolor="" class="bg-light">
						
							<td colspan="6" bgcolor=""><b>Roll No:</b> <%=DataUtility.getString(dto.getRollNo())%></td>
						</tr>
						<tr class="bg-light">
							<td colspan="6"><b>Name:</b> <%=DataUtility.getString(dto.getName())%></td>
						</tr>

						<tr class="bg-dark text-white">
							<td rowspan="2">Subjects</td>
							<td rowspan="2">Max Marks</td>
							<td rowspan="2">Min Marks</td>
							<td colspan="3" align="center">Marks Obtained</td>
						</tr>

						<tr class="bg-dark text-white">
							<td>Theory Marks</td>
							<td>Remark</td>
							<td>Grade</td>
						</tr>
						<tr class="bg-light">
							<td><font color=""><%=GetMarksheetCtl.Physics%></font></td>
							<td align="center"><%=GetMarksheetCtl.MAX_MARKS%></td>
							<td align="center"><%=GetMarksheetCtl.MIN_MARKS%></td>

							<td align="center"><%=DataUtility.getStringData(dto.getPhysics())%> <%-- <%=DataUtility.getIntegerData(dto.getChemistry()) %> --%>
								<%-- <%=Integer.parseInt(DataUtility.getStringData(dto.getPhysics())) %> --%>
							</td>




							<td align="center">
								<%
									String physics = ((100 >= DataUtility.getIntegerData(dto.getPhysics()))
												&& (DataUtility.getIntegerData(dto.getPhysics()) >= 35)) ? "Pass" : "Fail";
								%><%=physics%></td>
							<td align="center">
								<%
									if (100 >= DataUtility.getIntegerData(dto.getPhysics())
												&& DataUtility.getIntegerData(dto.getPhysics()) >= 80) {
								%>A<%
									} else
								%>
								<%
									if (79 >= DataUtility.getIntegerData(dto.getPhysics())
												&& DataUtility.getIntegerData(dto.getPhysics()) >= 60) {
								%>B<%
									} else
								%>
								<%
									if (59 >= DataUtility.getIntegerData(dto.getPhysics())
												&& DataUtility.getIntegerData(dto.getPhysics()) >= 40) {
								%>C<%
									} else
								%>
								<%
									if (39 >= DataUtility.getIntegerData(dto.getPhysics())
												&& DataUtility.getIntegerData(dto.getPhysics()) >= 0) {
								%>--<%
									}
								%>
							</td>
						</tr>
							<tr>
							<td>
							<font color=""><%=GetMarksheetCtl.Chemistry%></font></td>
							<td align="center"><%=GetMarksheetCtl.MAX_MARKS%></td>
							<td align="center"><%=GetMarksheetCtl.MIN_MARKS%></td>
							<td align="center"><%=DataUtility.getIntegerData(dto.getChemistry())%></td>
							<td align="center">
								<%
									String chemistry = ((100 >= DataUtility.getIntegerData(dto.getChemistry()))
												&& (DataUtility.getIntegerData(dto.getChemistry()) >= 35)) ? "Pass" : "Fail";
								%><%=chemistry%></td>
							<td align="center">
								<%
									if (100 >= DataUtility.getIntegerData(dto.getChemistry())
												&& DataUtility.getIntegerData(dto.getChemistry()) >= 80) {
								%>A<%
									} else
								%>
								<%
									if (79 >= DataUtility.getIntegerData(dto.getChemistry())
												&& DataUtility.getIntegerData(dto.getChemistry()) >= 60) {
								%>B<%
									} else
								%>
								<%
									if (59 >= DataUtility.getIntegerData(dto.getChemistry())
												&& DataUtility.getIntegerData(dto.getChemistry()) >= 40) {
								%>C<%
									} else
								%>
								<%
									if (39 >= DataUtility.getIntegerData(dto.getChemistry())
												&& DataUtility.getIntegerData(dto.getChemistry()) >= 0) {
								%>--<%
									}
								%>
							</td>
						</tr>

						<tr>
							<td><font color=""><%=GetMarksheetCtl.MATHS%></font></td>
							<td align="center"><%=GetMarksheetCtl.MAX_MARKS%></td>
							<td align="center"><%=GetMarksheetCtl.MIN_MARKS%></td>
							<td align="center"><%=DataUtility.getIntegerData(dto.getMaths())%></td>
							<td align="center">
								<%
									String maths = ((100 >= DataUtility.getIntegerData(dto.getMaths()))
												&& (DataUtility.getIntegerData(dto.getMaths()) >= 35)) ? "Pass" : "Fail";
								%><%=maths%></td>
							<td align="center">
								<%
									if (100 >= DataUtility.getIntegerData(dto.getMaths())
												&& DataUtility.getIntegerData(dto.getMaths()) >= 80) {
								%>A<%
									} else
								%>
								<%
									if (79 >= DataUtility.getIntegerData(dto.getMaths())
												&& DataUtility.getIntegerData(dto.getMaths()) >= 60) {
								%>B<%
									} else
								%>
								<%
									if (59 >= DataUtility.getIntegerData(dto.getMaths())
												&& DataUtility.getIntegerData(dto.getMaths()) >= 40) {
								%>C<%
									} else
								%>
								<%
									if (39 >= DataUtility.getIntegerData(dto.getMaths())
												&& DataUtility.getIntegerData(dto.getMaths()) >= 0) {
								%>--<%
									}
								%>
							</td>
						</tr>

						<tr>
							<td></td>
							<td align="center"><%=GetMarksheetCtl.TOTAL_MARKS%></td>
							<td></td>
							<td align="center">
								<%
									int marksTotal = (DataUtility.getIntegerData(dto.getPhysics())
												+ DataUtility.getIntegerData(dto.getChemistry())
												+ DataUtility.getIntegerData(dto.getMaths()));
								%><%=marksTotal%></td>
							<td colspan="3"></td>
						</tr>

						<tr bgcolor="" class="bg-dark text-white" >
							<td colspan="2"><b>Grand Total:</b></td>
							<td colspan="4"><%=marksTotal%> <b>Out OF </b><%=GetMarksheetCtl.TOTAL_MARKS%></td>
						</tr>
						<tr bgcolor=" "class="bg-light">
							<td colspan="2"><b>Result:</b></td>
							<td colspan="4">
								<%
									String result = ((physics.equals("Pass")) && (chemistry.equals("Pass")) && (maths.equals("Pass")))
												? "Pass" : "Fail";
								%><%=result%>
								<%-- <%
String result=if((physics.equals("Pass"))&&(chemistry.equals("Pass"))&&(maths.equals("Pass")))
		{ %>Pass<%}else{%>Fail<%} %> --%>

							</td>
						</tr>
						<tr bgcolor=" " class="bg-light">
							<td colspan="2"><b>Percent/Division:</b></td>
							<td colspan="4">
								<% 
int marksTo=DataUtility.getIntegerData(dto.getPhysics())+DataUtility.getIntegerData(dto.getChemistry())+DataUtility.getIntegerData(dto.getMaths());
double percent1=(double)marksTotal/GetMarksheetCtl.TOTAL_MARKS;
float percent=(float)percent1*100F;

float spercent1=Math.round(percent);
/* //String spercent=String.valueOf(percent);
//String spercent1=spercent.substring(0,4);
 */
%>


<%if(result.equals("Pass")){
	%> <% if(60<=percent){ %><%=spercent1%>(First Division) <% } else if((50<=percent)&&(59>=percent)){ %><%=spercent1%>(Second
								Division) <% }else if((40<=percent)&&(49>=percent)){ %><%=spercent1%>(Third
								Division) <% }else { %>Fail<%} %> <%}else{ %>.......<% } %>

							</td>
						</tr>
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						</table>
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    </font></th>
                   <!--  <th width="10%"><font color="white"></font></th>
                    <th width="10%"><font color="white"></font></th>
                    <th width="10%"><font color="white"></font></th> -->
                    <!-- <th width="10%"><font color="white"></font></th> -->
                    <th width="10%"><font color="white"></font></th>
      </tr>
          

  
    </thead>
   
    <tbody class="table table-hover">
   
  

      <tr class="bg-info text-white ">
      
      
      
            
      </tr>
   
      
    </tbody>

 
  
  </table>
  <%} %>
 
</div>
	  
	  </div>








	<br><!--
    <div class="card-footer justify-content-center bg-primary text-white">Footer
	
	
	</div>-->
  </div>
</div>
</form>
</body>
<div class="RegFooter mfooter">
<%@include file="Footer.jsp"%></div>
</html>