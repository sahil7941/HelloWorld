
<%@page import="in.co.sunrays.proj3.controller.MarksheetMeritListCtl"%>
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
<title>Marksheet Merit List</title>

</head>


<%@include file="Header.jsp"%>
<body class="bodys bodys1">
<form action="<%=ORSView.JESPER_CTL%>" method="get" name="frm">
<%
int pageNo=ServletUtility.getPageNo(request);
int pageSize=ServletUtility.getPageSize(request);
int index=((pageNo-1)*pageSize)+1;

List list = ServletUtility.getList(request);

%>
  <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
  <input type="hidden" name="pageSize" value="<%=pageSize%>">
 
 <div class="container-fluid" style="margin-top:65px; min-height:550px; margin-bottom:px;"><!--
  <h2>Card Header and Footer</h2>-->
  
    <div class="card">
	<div class="card-body">
	

  <!---w-25 width h-25 height -->
  
    <div class="modal-content" style="margin-bottom:-9px;">
   

		  <div class="modal-body" style="margin-bottom:-15px;">
		  
		  
		   <div class="modal-header justify-content-center bg-dark">
            <h6 class="modal-title " id="ModalTitle">
              Marksheet Merit List
            </h6>
          </div>
		  
		  
		  
		
               <div class="row" style="margin-top:5px;">
			  </div>	
	    
	</div>
    </div>
	</div>
	<div class="container-fluid">
	
	<!--Action Perform for Delete and New-->
	
	
	<div class="row justify-content-center">
	<div class="col-md-2 ">
	<div class="form-group">
	<!-- <input type="submit" class="form-control bg-secondary text-white" value="(click here)Print MeritList" name="operation" /> -->
   
   
    <center>
    <a href="<%=ORSView.JESPER_CTL%>" target="blank" 
    class="form-control bg-dark text-white"><font>Print Merit List</font></a>
    </center>
    </div>
    
    
	</div>
	</div>
	<!--close card-body-->
	
      <div class="card-body justify-content-center text-left" style="margin-top:-20px;">
	 

   <table class="table table-responsive  table-bordered bg-primary" style="width:100%; margin-top:-18;">
    <thead style="width:100%">
      <tr class="bg-dark text-white " align="center">
	    
   
                    <th width="10%"><font color="white">S.No.</font></th>                   
                    <th width="20%"><font color="white">RollNo</font></th>
                    <th width="30%"><font color="white">Student Name</font></th>
                    <th width="10%"><font color="white">Physics</font></th>
                    <th width="10%"><font color="white">Chemistry</font></th>
                    <th width="10%"><font color="white">Maths</font></th>
                    <th width="20%"><font color="white">Total</font></th>
                    <th width="20%"><font color="white">Percentage</font></th>
      </tr>
          
          
  
   <%
                String error=ServletUtility.getErrorMessage(request);
                //String sesstionMeassagea= (String) request.getAttribute("message");
                String onRecord=ServletUtility.getErrorMessage("onerecord",request);
                String Sucess=ServletUtility.getSuccessMessage(request);
                %>
     <%if((ServletUtility.getErrorMessage(request)!=(""))){%>
        <tr class="bg-white" width="100%" >
        <th colspan="8">
        <input type="text" 
                class="form-control text-center text-danger font-weight-bold"
                value="<%=error%>" readonly style="font-size:26px; ">
    </th></tr>
    <%} %>
  
    </thead>
   
    <tbody class="table table-hover">
   
       
     
      <%
	       
              Iterator it = list.iterator();
              while (it.hasNext()) {
                  MarksheetDTO rdto = (MarksheetDTO)it.next();
          %>

      <tr class="bg-light text-dark ">
              <td align="center"><%=index++%></td>
              <td align="center"><%=rdto.getRollNo()%></td>
              <td align="center"><%=rdto.getName()%></td>
              <td align="center"><%=rdto.getPhysics()%></td>
              <td align="center"><%=rdto.getChemistry()%></td>
              <td align="center"><%=rdto.getMaths()%></td>
              
              <td align="center">
                 	<%
									int marksTotal = (DataUtility.getIntegerData(rdto.getPhysics())
												+ DataUtility.getIntegerData(rdto.getChemistry())
												+ DataUtility.getIntegerData(rdto.getMaths()));
								%><%=marksTotal%>
                    
              </td>
               <td align="center"><%=marksTotal/3%>%</td>
      </tr>
   <%} %>
      
    </tbody>
  <tfoot class="bg-secondary" width="100%">
  <tr class="bg-dark" width="100%" >
                     <th colspan="8"><font color="white"></font></th> 
  </tr>
  </tfoot>
  
  </table>
  
 
</div>
	  
<div class="row justify-content-center">
	<div class="col-md-1 ">
	<div class="form-group">
	<!-- <input type="submit" class="form-control bg-secondary text-white" value="(click here)Print MeritList" name="operation" /> -->
   
   
    <center>
    <a href="<%=ORSView.WELCOME_CTL%>"
    class="btn btn-secondary"><font>Back</font></a>
    </center>
    </div>
    
    
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