
<%@page import="in.co.sunrays.proj3.controller.MarksheetListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="in.co.sunrays.proj3.util.DataUtility"%>
<%@page import="in.co.sunrays.proj3.dto.MarksheetDTO"%>
<%@page import="in.co.sunrays.proj3.util.ServletUtility"%>
<%@page import="in.co.sunrays.proj3.util.HTMLUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Marksheet List</title>
<jsp:useBean id="dto" class="in.co.sunrays.proj3.dto.MarksheetDTO"
	scope="request"></jsp:useBean>
<style type="text/css">
/* td:HOVER{
background-color:#9a0d0d; */
color
:white
;
 

}
</style>
<script type="text/javascript">
	function checkedAll() {

		var totalElement = document.forms[0].elements.length;
		//alert(totalElement);
		for (var i = 0; i < totalElement; i++) {
			var en = document.forms[0].elements[i].name;

			//alert(en);
			if (en != undefined & en.indexOf("chk_") != -1) {
				document.forms[0].elements[i].checked = document.frm.chk_all.checked;

			}
		}
	}
	function check() {
		var en = document.forms[0].elements[8].name;
		if (en != undefined & en.indexOf("chk_") != -1) {
			document.forms[0].elements[8].checked = document.frm.chk_all.unchecked;
		}
	}
</script>
</head>


<%@include file="Header.jsp"%>
<body class="bodys bodys1">

	<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post" name="frm">


		<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;

			List list = ServletUtility.getList(request);
		%>
		<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
			type="hidden" name="pageSize" value="<%=pageSize%>">

		<div class="container-fluid"
			style="margin-top: 65px; min-height: 550px; margin-bottom: px;">
			<!--
  <h2>Card Header and Footer</h2>-->

			<div class="card">
				<div class="card-body">


					<!---w-25 width h-25 height -->

					<div class="modal-content" style="margin-bottom: -9px;">

						<!--   <div class="modal-header justify-content-center">
	  
          </div>-->

						<div class="modal-body" style="margin-bottom: -15px;">


							<div class="modal-header justify-content-center bg-dark">
								<h4 class="modal-title " id="ModalTitle">Marksheet List
									</h4>
							</div>





							<!--action for Search and Reset-->

							<%-- <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post"> --%>
							<div class="row" style="margin-top: 5px;">

								<div class="col-md-3">
									<div class="form-group">

										<%
											String err1 = ServletUtility.getErrorMessage("mId", request);

											List mlist = (List) request.getAttribute("MarksheetList");
										%>
										<abbr title="Select Roll Number"> <%=HTMLUtility.getList("mIdRoll", String.valueOf(dto.getId()), mlist, err1)%>

										</abbr>
									</div>
								</div>

								<div class="col-md-3">
									<div class="form-group">

										<input type="text"
											class="<%=(ServletUtility.getErrorMessage("studentName", request).equalsIgnoreCase("")) ? "form-control"
					: "form-control border border-danger"%>"
											placeholder="Enter Student Name" name="studentName"
											value="<%=ServletUtility.getParameter("studentName", request)%>">


									</div>
								</div>


								<div class="col-md-1">
									<div class="form-group">
										<input type="submit"
											class="form-control text-white btn btn-success btn-block"
											value="<%=MarksheetListCtl.OP_SEARCH%>" name="operation">
									</div>
								</div>
								<div class="col-md-1">
									<div class="form-group">
										<input type="submit"
											class="form-control text-white btn btn-info btn-block"
											value="<%=MarksheetListCtl.OP_RESET%>" name="operation" />
									</div>
								</div>
								<div class="col-md-3">

									<ul class="pagination float-right">
										<div class="row">
											<li class="page-item">
												<%-- <input type="submit" class="btn page-link text-white bg-primary"  value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation" <%=(pageNo==1)? "disabled" : "" %>> --%>
												<input type="submit"
												class="<%=(pageNo == 1) ? "btn page-link text-secondary no-drop" : "btn page-link text-white bg-secondary"%>"
												value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation"
												<%=(pageNo == 1) ? "disabled" : ""%>>
											</li>
											<%
												List l1 = (List) request.getAttribute("pno");

												int value = 1;
												for (int i = 1; i <= l1.size(); i = i + 10) {
													value++;
												}
												int value1 = value - 1;
											%>


											<li class="page-item ">
												<%--   
      <% List l1=(List)request.getAttribute("pno"); 
                    %> --%> <input type="submit"
												class="<%=((list.size() < pageSize) || (l1.size() == pageSize * pageNo))
					? "btn page-link text-secondary no-drop" : "btn page-link text-white bg-secondary"%>"
												value="<%=MarksheetListCtl.OP_NEXT%>" name="operation"
												<%=((list.size() < pageSize) || (l1.size() == pageSize * pageNo)) ? "disabled" : ""%>>

											</li>
										</div>
									</ul>
								</div>










							</div>

						</div>
					</div>
				</div>
				<!--close card-body-->

				<div class="card-body justify-content-center text-left"
					style="margin-top: -20px;">
					<div class="container"></div>


					<div class="container-fluid">

						<!--Action Perform for Delete and New-->


						<div class="row justify-content-center">


							<div class="col-md-5">
								<%
									String error = ServletUtility.getErrorMessage(request);
									//String sesstionMeassagea= (String) request.getAttribute("message");
									String onRecord = ServletUtility.getErrorMessage("onerecord", request);
									String Sucess = ServletUtility.getSuccessMessage(request);
								%>

								<div class="form-group">

									<h3
										class="<%=(ServletUtility.getErrorMessage("onerecord", request).equalsIgnoreCase(""))
					? "text-center text-success" : "text-center text-danger"%>"
										style="margin-bottom: 25px;"><%=onRecord%><%=Sucess%></h3>



									<%--  <input type="text" 
                class="<%=(ServletUtility.getErrorMessage("onerecord",request).equalsIgnoreCase(""))?"form-control text-center text-success font-weight-bold":"form-control text-center text-danger font-weight-bold"%>"
                value="<%=onRecord%><%=Sucess%>" readonly> --%>
								</div>
							</div>
							<%-- 	<div class="col-md-3">
    
    <ul class="pagination float-right">
    <div class="row">
    <li class="page-item">
    
    <input type="submit" class="page-link text-white bg-primary"  value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation" <%=(pageNo==1)? "disabled" : "" %>>
   <input type="submit" class="<%=(pageNo==1)? "page-link text-blue" : "page-link text-white bg-primary"%>"  value="<%=MarksheetListCtl.OP_PREVIOUS%>" name="operation" <%=(pageNo==1)? "disabled" : "" %>>
    </li>
									     <%  List l1=(List)request.getAttribute("pno");  
               
           
                	    int value=1;
                	    for(int i=1;i<=l1.size();i=i+10){
                	    	value++;
                	    }
                	    int value1=value-1;
                %>
 <%
 

    for(int i=pageNo;i<=pageNo+2;i++){ %>
    
    <li class="page-item <%=(value1>=i)?"border border-white":"disabled"%>">
    <a class="page-link <%=(pageNo==i)?"bg-primary text-white":""%>" href="MarksheetListCtl?pno=<%=i%>"><%=i%></a></li>
      

 <%} %>
 

    
    
    <li class="page-item ">
    
      <% List l1=(List)request.getAttribute("pno"); 
                    %>
      <input type="submit" class="<%=((list.size()<pageSize)||(l1.size()==pageSize*pageNo))? "page-link text-blue" : "page-link text-white bg-primary"%>"  value="<%=MarksheetListCtl.OP_NEXT%>" name="operation" <%=((list.size()<pageSize)||(l1.size()==pageSize*pageNo))? "disabled" : "" %>>
   
    </li>
    </div>
    </ul> 
    </div> --%>
						</div>
					</div>

					<h3
						class="<%=(ServletUtility.getErrorMessage(request).equalsIgnoreCase("")) ? "text-center text-success"
					: "text-center text-danger"%>"
						style="margin-bottom: 25px;"><%=error%></h3>


					<%
						if (list != null && list.size() != 0) {
					%>
					<!--<h2>Basic Table</h2>
  <p>The .table class adds basic styling (light padding and horizontal dividers) to a table:</p>-->

					<table class="table table-responsive table-bordered"
						style="width: 100%; margin-top: -25px;">

						<thead style="width: 100%">
							<tr class="bg-dark text-white">



								<th width="10%">
									<div class="form-check">
										<label class="form-check-label"> <input
											type="checkbox" class="form-check-input" name="chk_all"
											onchange="checkedAll()">Select All
										</label>
									</div>
								</th>

								<th width="15%">RollNo</th>
								<th width="25%">StudentName</th>
								<th width="15%">Physics</th>
								<th width="15%">Chemistry</th>
								<th width="15%">Math</th>
								<th width="15%">Percentage</th>
								<th width="35%">&nbsp;&nbsp;&nbsp;Edit&nbsp;&nbsp;</th>
							</tr>



						</thead>

						<tbody>



							<%
								Iterator it = list.iterator();
									while (it.hasNext()) {
										MarksheetDTO rdto = (MarksheetDTO) it.next();
							%>

							<tr class="bg-light text-dark">
								<td width="10%">
									<div class="form-check">
										<label class="form-check-label"> <input
											type="checkbox" class="form-check-input" id="sid"
											name="chk_1" onchange="check()" value="<%=rdto.getId()%>">
											<%=index++%>





										</label>
									</div>

								</td>
								<td width="20%" class="mouse"><%=rdto.getRollNo()%></td>
								<td width="20%" class="mouse"><%=rdto.getName()%></td>
								<td width="15%" class="mouse"><%=rdto.getPhysics()%></td>
								<td width="10%" class="mouse"><%=rdto.getChemistry()%></td>
								<td width="20%" class="mouse"><%=rdto.getMaths()%></td>
								
                 	<%
									int marksTotal = (DataUtility.getIntegerData(rdto.getPhysics())
												+ DataUtility.getIntegerData(rdto.getChemistry())
												+ DataUtility.getIntegerData(rdto.getMaths()));
								%>
                    
              
               <td align="center"><%=marksTotal/3%>%</td>

								<td width="20%"><a href="MarksheetCtl?id=<%=rdto.getId()%>"
									style="text-decoration: none; color: black;"> <i
										class="fas fa-edit">Edit</i></a></td>


								</td>
							</tr>
							<%
								}
							%>

						</tbody>

						<tfoot class="bg-secondary" width="100%">


							<tr class="bg-dark" width="100%">
								<th colspan="8"><font color="white"></font></th>
							</tr>
						</tfoot>
					</table>
					<div class="row justify-content-center">

						<div class="col-md-1">
							<div class="form-group">
								<input type="submit"
									class="form-control text-white btn btn-primary btn-block"
									value="<%=MarksheetListCtl.OP_NEW%>" name="operation" />
							</div>
						</div>

						<div class="col-md-1">
							<div class="form-group">
								<input type="submit"
									class="<%=(list.size() == 0) ? "form-control bg-danger text-white no-drop"
						: "form-control text-white btn btn-danger btn-block"%>"
									value="<%=MarksheetListCtl.OP_DELETE%>" name="operation"
									<%=(list.size() == 0) ? "disabled" : ""%>>

							</div>
						</div>
					</div>
					<%
						} else {
					%>
					<div class="row justify-content-center" style="margin-top: 10px;">
						<div class="col-md-1">
							<div class="form-group">
								<input type="submit"
									class="form-control text-white btn btn-secondary btn-block"
									value="<%=MarksheetListCtl.OP_BACK%>" name="operation" />
							</div>
						</div>
					</div>
					<%
						}
					%>
				</div>

			</div>



			<!--
<div class="row bg-primary text-white " style="margin-top:20px; line-height:2em; border-bottom:black 2px solid;">
	<div class="col-2 font-weight-bold text-center text-capitalize">sNO.</div>
	<div class="col-2 font-weight-bold text-center text-capitalize">name</div>
	<div class="col-2 font-weight-bold text-center text-capitalize">role</div>
	<div class="col-3 font-weight-bold text-center text-capitalize">date</div>
	<div class="col-3 font-weight-bold text-center text-capitalize">state</div>
	</div>
-->







			<br>
			<!--
    <div class="card-footer justify-content-center bg-primary text-white">Footer
	
	
	</div>-->
		</div>
		</div>
	</form>
</body>
<div class="RegFooter mfooter">
	<%@include file="Footer.jsp"%></div>
</html>