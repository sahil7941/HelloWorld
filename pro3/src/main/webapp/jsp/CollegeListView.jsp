<%@page import="in.co.sunrays.proj3.model.CollegeModelInt"%>
<%@page import="in.co.sunrays.proj3.controller.CollegeListCtl"%>
<%@page import="in.co.sunrays.proj3.model.ModelFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<%@page import="in.co.sunrays.proj3.util.DataUtility" %>
<%@page import="in.co.sunrays.proj3.dto.CollegeDTO"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility" %>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>College List</title>
<style type="text/css">
/* td:HOVER{
background-color:#9a0d0d; */ 
color:white; 
}
</style>
<script type="text/javascript">
function checkedAll()
{
	
	var totalElement=document.forms[0].elements.length;
	//alert(totalElement);
	for(var i=0;i<totalElement;i++){
		var en=document.forms[0].elements[i].name;
		
		//alert(en);
		if(en!=undefined & en.indexOf("chk_")!=-1)
		{	
			document.forms[0].elements[i].checked=document.frm.chk_all.checked;
		 
		}		
	}
}
function check(){
	var en=document.forms[0].elements[6].name;
	if(en!=undefined & en.indexOf("chk_")!=-1)
	{	
		document.forms[0].elements[6].checked=document.frm.chk_all.unchecked;
	}	
}
</script>
</head>


<%@include file="Header.jsp"%>
<body class="bodys bodys1">
<form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="post" name="frm">
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.CollegeDTO" scope="request"></jsp:useBean>
<%
int pageNo=ServletUtility.getPageNo(request);
int pageSize=ServletUtility.getPageSize(request);
int index=((pageNo-1)*pageSize)+1;
CollegeModelInt cModel = ModelFactory.getInstance().getCollegeModel();
List list = ServletUtility.getList(request);

%>
 <div class="container-fluid" style="margin-top:65px; min-height:550px; margin-bottom:px;"><!--
  <h2>Card Header and Footer</h2>-->
  
    <div class="card">
	<div class="card-body">
	

  <!---w-25 width h-25 height -->
  
    <div class="modal-content" style="margin-bottom:-9px;">
   
   <!--   <div class="modal-header justify-content-center">
	  
          </div>-->
		 
		  <div class="modal-body" style="margin-bottom:-15px;">
		  
		  
		   <div class="modal-header justify-content-center bg-dark">
            <h4 class="modal-title " id="ModalTitle">
              College List
            </h4>
          </div>
		  
		  
		  
		  
		  
		  <!--action for Search and Reset-->
		  
		  <%-- <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post"> --%>
               <div class="row" style="margin-top:10px;">
			   
			    <div class="col-md-3">
			    <div class="form-group">
                <%List collegeList = (List)request.getAttribute("collegeList"); %>
                <%=HTMLUtility.getList("collegeId", String.valueOf(dto.getId()), "College", collegeList) %>
                </div>
				</div>
				<div class="col-md-3">
			    <div class="form-group">
			    
                <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("city", request).equalsIgnoreCase(""))?"form-control":"form-control border border-danger"%>" 
                placeholder="Enter City"  name="city" 
                value="<%=ServletUtility.getParameter("city", request)%>">
                </div>
				</div>
			
			    <div class="col-md-1">
			    <div class="form-group">
                <input type="submit" class="form-control text-white btn btn-success btn-block" value="<%=CollegeListCtl.OP_SEARCH%>" name="operation">
                </div>
				</div>
				<div class="col-md-1">
			    <div class="form-group">
                <input type="submit" class="form-control text-white btn btn-info btn-block" value="<%=CollegeListCtl.OP_RESET%>" name="operation"/>
                </div>
				</div>
				<div class="col-md-3">
    <ul class="pagination float-right">
    <div class="row">
    <li class="page-item">
    
    <%-- <input type="submit" class="page-link text-white bg-primary"  value="<%=CollegeListCtl.OP_PREVIOUS%>" name="operation" <%=(pageNo==1)? "disabled" : "" %>> --%>
   <input type="submit" class="<%=(pageNo==1)? "btn page-link text-secondary no-drop" : "btn page-link text-white bg-secondary"%>"  value="<%=CollegeListCtl.OP_PREVIOUS%>" name="operation" <%=(pageNo==1)? "disabled" : "" %>>
    </li>
 
 <% List l1=(List)request.getAttribute("pageno"); 
 
 
 int value=1;
 for(int i=1;i<=l1.size();i=i+10){
 	value++;
 }
 int value1=value-1;
       %>
 
    <li class="page-item ">
    
    <%--   <% List l1=(List)request.getAttribute("pageno"); 
                    %> --%>
      <input type="submit" class="<%=((list.size()<pageSize)||(l1.size()==pageSize*pageNo))? "btn page-link text-secondary no-drop" : "btn page-link text-white bg-secondary"%>"  value="<%=CollegeListCtl.OP_NEXT%>" name="operation" <%=((list.size()<pageSize)||(l1.size()==pageSize*pageNo))? "disabled" : "" %>>
   
    </li>
    </div>
    </ul> 
    </div>
				
				
				
				
				
				
				
			</div>	
	    <!-- </form> -->
	</div>
    </div>
	</div>
	<!--close card-body-->
	
      <div class="card-body justify-content-center text-left" style="margin-top:-20px;">
	  <div class="container">


</div>
	  
	  
	<div class="container-fluid">
	
	<!--Action Perform for Delete and New-->
	
	<%-- <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post"> --%>
	<div class="row justify-content-center">
	
	<!-- </form> -->
	<div class="col-md-5">
	    <%
                String error=ServletUtility.getErrorMessage(request);
                //String sesstionMeassagea= (String) request.getAttribute("message");
              //  String onRecord=ServletUtility.getErrorMessage("onerecord",request);
                String Sucess=ServletUtility.getSuccessMessage(request);
                %>
			  
			   <div class="form-group">
              
                <h3
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>" style="margin-bottom:25px; ">
                <%=error%><%=Sucess%></h3> 
              
                
              <%--   <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("onerecord",request).equalsIgnoreCase(""))?"form-control text-center text-success font-weight-bold":"form-control text-center text-danger font-weight-bold"%>"
                value="<%=onRecord%><%=Sucess%>" readonly> --%>
               
               </div> 
	</div>
	
	</div></div>
	
	<%--   <h3 
                class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase(""))?"text-center text-success":"text-center text-danger"%>"
                style="margin-bottom:25px; "><%=error%></h3>      --%>
 
<%if(list!=null && list.size()!=0) {%>
 <!--<h2>Basic Table</h2>
  <p>The .table class adds basic styling (light padding and horizontal dividers) to a table:</p>-->            
  <table class="table table-responsive table-bordered" style="width:100%; margin-top:-25px;">
    <thead style="width:100%">
      <tr class="bg-dark text-white">
	    
	    
	    
	    <th width="15%">
	<div class="form-check">
    <label class="form-check-label">
          <input type="checkbox" class="form-check-input" name="chk_all" onchange="checkedAll()">Select All
   </label>    
    </div></th>
   
        <th width="15%">CollegeName</th>
		<th width="15%">Address</th>
		<th width="15%">City</th>
		<th width="15%">State</th>
		<th width="15%">phoneNo</th>
        <!-- <th width="15%">CreatedBy</th> -->
        <th width="15%">Edit</th>
      </tr>
      
    
    </thead>
     
    <tbody>
   
       
     
      <%
	       
              Iterator it = list.iterator();
              while (it.hasNext()) {
                  CollegeDTO rdto = (CollegeDTO)it.next();
          %>

      <tr class="bg-light text-dark">
        <td width="10%">
		<div class="form-check">
        <label class="form-check-label">
        
        
          <input type="checkbox" class="form-check-input" id="sid" name="chk_1" onchange="check()" 
          value="<%=rdto.getId()%>" >
             <%=index++%>
        
        
        
        
        
        </label>
        </div>
		
		</td>
        <td width="20%" class="mouse"><%=rdto.getName()%></td>
        <td width="20%" class="mouse"><%=rdto.getAddress()%></td>
		<td width="15%" class="mouse"><%=rdto.getCity()%></td>
		<td width="10%" class="mouse"><%=rdto.getState()%></td>
		<td width="20%" class="mouse"><%=rdto.getPhoneNo()%></td>
<%-- 		<td width="20%" class="mouse"><%=rdto.getCreatedBy()%></td>
 --%>		<td width="20%">
		
              <a href="CollegeCtl?id=<%=rdto.getId()%>" style="text-decoration:none; color:black; ">
              <i class="fas fa-edit">Edit</i></a></td>
        
		
		</td>
      </tr>
   <%} %>
 
    </tbody>
  <tfoot class="bg-secondary" width="100%">

  
  <tr class="bg-dark" width="100%" >
                     <th colspan="8"><font color="white"></font></th> 
  </tr>
  </tfoot>
  
  </table>
  <div class="row justify-content-center">
  <div class="col-md-1">
	<div class="form-group">
    <input type="submit" class="form-control text-white btn btn-primary btn-block" value="<%=CollegeListCtl.OP_NEW%>" name="operation" />
    </div>
	</div>
	
	<div class="col-md-1">
	<div class="form-group">
    <input type="submit" class="<%=(list.size()==0)?"form-control bg-danger text-white no-drop" :"form-control text-white btn btn-danger btn-block "%>" value="<%=CollegeListCtl.OP_DELETE%>" name="operation" <%=(list.size()==0)? "disabled" : "" %>>
    
    </div>
	</div>
  
  
  
  </div>
  
  <%}else{ %>
  <div class="row justify-content-center" style="margin-top: 10px;">
	<div class="col-md-1">
	<div class="form-group">
    <input type="submit" class="form-control text-white btn btn-secondary btn-block" value="<%=CollegeListCtl.OP_BACK%>" name="operation" />
    </div>
	</div></div>
  <%} %>
</div>
	  
	  </div>


  <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">







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