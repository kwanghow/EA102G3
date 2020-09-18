<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.order.model.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<!DOCTYPE html>
<html lang="en">

<% 
OrderService OrderService = new OrderService();
List<OrderVO> list = OrderService.getAll();
pageContext.setAttribute("list", list);
	
	%>
<%@ page import="com.authority.model.* , com.employee.model.* ,com.features.model.*"%>	
<jsp:useBean id="authoritySvc" scope="page"	class="com.authority.model.AuthorityService" />
<%

EmployeeVO employeeVO=(EmployeeVO)session.getAttribute("employeeVO");
String emp_id=employeeVO.getEmp_id();
EmployeeService empSvc = new EmployeeService();
employeeVO=empSvc.getOneEmp(emp_id);
session.setAttribute("employeeVO", employeeVO);
pageContext.setAttribute("employeeVO",employeeVO);

//�������v��
	FeaturesService feaSvc = new FeaturesService();
	List<FeaturesVO> list2 = feaSvc.getAll();
	request.setAttribute("list2", list2);
	//���u�ӤH�v��
		List<AuthorityVO> list1 = authoritySvc.getOneAuthority(emp_id);
		List<String> emp = new ArrayList<String>();
		for (AuthorityVO a : list1) {
			emp.add(a.getFeatures_id());
		}
		pageContext.setAttribute("emp", emp);
List<AuthorityVO> authorityList=authoritySvc.getOneAuthority(emp_id);
List<String> featuresList = new ArrayList<String>();
for (AuthorityVO a:authorityList){
	featuresList.add(a.getFeatures_id());
}

if(!featuresList.contains("F11")) {
	session.setAttribute("featuresError", "�L���v���гq���޲z���}�q");
	session.setAttribute("location",request.getRequestURI());
	response.sendRedirect(request.getContextPath()+"/back-end/kevin/index/index.jsp");
}
%>




	
	
<jsp:useBean id="categoryService" scope="page"	class="com.product_category.model.CategoryService" />
<jsp:useBean id="detailSvc" scope="page" class="com.pro.detail.model.DetailService" />

	<script src="https://code.jquery.com/jquery-2.1.4.min.js"></script>		
	
	
	
	 <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/bootstrap.css"></script>
	
<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>��x�q��޲z</title>

  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">



<style >
tr td{
	text-align:center;
	 
	
}

</style>
</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
<!-- Sidebar -->
		<ul
			class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
			id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a class="sidebar-brand d-flex align-items-center justify-content-center" href="<%=request.getContextPath()%>/back-end/kevin/index/index.jsp">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3">��x�޲z�t��</div>
			</a>


			<!-- Heading -->
			<div class="sidebar-heading">Addons</div>

			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseTwo"
				aria-expanded="true" aria-controls="collapseTwo"> <i
					class="fas fa-fw fa-cog"></i> <span>�޲z�\��</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
					data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">�޲z�\��:</h6>
						<c:forEach var="featuresVO" items="${list2}">
							<c:if test="${fn:contains(emp,featuresVO.features_id)}">
								<c:choose>
									<c:when test="${featuresVO.features_name eq '�|���޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/admin-2/back-member.jsp">${featuresVO.features_name}</a>
										
									</c:when>
									<c:when test="${featuresVO.features_name eq '���u�޲z'}">
										<a class="collapse-item" href="#" id='empManage'>${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '���ίd�����|�޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/KaiPing/gpc/back-gpc.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�峹���|�޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/article/listAllArticle.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�M�������޲z'}">
										<a class="collapse-item" href="#">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�нm�޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/admin-2/back-coach.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '���κ޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/KaiPing/gpc/back-gpc.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�̷s�����޲z'}">
										<a class="collapse-item"
											href="#<%--<%=request.getContextPath()%>/back-end/kevin/news/News.jsp--%>"
											id='newsManage'>${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�@��@�ҵ{�޲z'}">
										<a class="collapse-item" href="#">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�q��޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/haoren/back-order-list-all.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '�ӫ~�޲z'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/haoren/back-pro-list-all.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:otherwise>
										<a class="collapse-item" href="#">�е��Զ}�q�v��</a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
						<%-- 						${(featuresVO.features_name eq "���u�޲z")?"id='empManage'":""}>${featuresVO.features_name} --%>



					</div>
				</div></li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

       <!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>


					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..." aria-label="Search"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<div class="topbar-divider d-none d-sm-block"></div>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">${employeeVO.emp_name}</span>
						</a> <!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<button type="button" class="dropdown-item" id="profile"
									data-toggle="modal" data-target="#exampleModal">
									<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> �ק���
								</button>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal"
									data-target="#logoutModal"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									�n�X
								</a>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800"></h1>
          <p class="mb-4"> <a target="_blank" href="#"></a>.</p>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
            <th>�q��s��</th>
			<th>�|��ID</th>
			<th>�I�ڤ覡</th>
			<th>�B�e�覡</th>			
			<th>�R�a�a�}</th>
			<th>�q�榨�߮ɶ�</th>
			<th>�B�O</th>
			<th>�q�檬�A</th>
			<th>�q�����</th>
			<th>�q��ק�</th>
			<th>��ܭq�檬�A</th>             
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
            <th></th>
			<th></th>
			<th></th>
			<th></th>			
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>
			<th></th>           
                    </tr>
                  </tfoot>
                  <tbody>
                  <c:forEach var="OrderVO" items="${list}">                  
                 <tr class="all">
				<td>${OrderVO.order_id}</td>
				<td>${OrderVO.member_id}</td>
				
			<!-- �I�ڤ覡 -->
		<c:choose>
			<c:when test="${(OrderVO.order_pay)==0 }">	
				<td>�H�Υd�I��</td>
		    </c:when>				
			<c:otherwise>
    			<td>�f��I��</td>
   			</c:otherwise> 
		</c:choose>	
			<!-- �I�ڤ覡 -->		
			
			<!-- �B�e�覡 -->
		<c:choose>
			<c:when test="${(OrderVO.delivery)==0 }">	
				<td>�W�Ө��f</td>
		    </c:when>				
			<c:otherwise>
    			<td>�f�B�H�e</td>
   			</c:otherwise> 
		</c:choose>	
			<!-- �B�e�覡 -->	
			
				<td>${OrderVO.order_address}</td>
				
				
				<td><fmt:formatDate value="${OrderVO.order_date}" type="time" pattern="MM/dd hh:mm"/><br></td>
				<td>${OrderVO.order_fee}</td>
				
			<!-- �q�檬�A -->
		<c:choose>
			<c:when test="${(OrderVO.order_status)==0 }">	
				<td id="status">�w�U��w�I��</td>
		    </c:when>	
		    <c:when test="${(OrderVO.order_status)==1 }">	
				<td id="status">�w�X�f</td>
		    </c:when>	
		    <c:when test="${(OrderVO.order_status)==2 }">	
				<td id="status">����</td>
		    </c:when>				
			<c:otherwise>
    			<td id="status">�q�����</td>
   			</c:otherwise> 
		</c:choose>	
			<!-- �q�檬�A -->
			<!--�q��Ա� -->
			<td><button class="btn btn-info btn-icon-split" type="button" data-toggle="modal" data-target="#${OrderVO.order_id}">�ӫ~��T</button></td>
			<!--�q��Ա� -->
				 		
				<td>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/order.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�ק�" class="btn btn-info btn-icon-split"> <input type="hidden"
							name="Order_Id" value="${OrderVO.order_id}"> <input type="hidden"
							name="action" value="getOne_For_Update">
					</FORM>
				</td>
				
				
				<td>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/order.do"
						style="margin-bottom: 0px;">
					<select size="1" name="Order_status" class="Order_status">        			
         			 <option value="0">�w�U��w�I��
         			 <option  value="1">�w�X�f         			       			
	      			 </select>
						<input type="hidden" name="Order_id" value="${OrderVO.order_id}">
						<input type="hidden" name="member_id" value="${OrderVO.member_id}">
						<button type="button" class="orderStateSubmit btn btn-info btn-icon-split">�ק�q�檬�A</button>
						<input type="hidden"name="action" value="update_order_status">
					</FORM>
				</td>	
			</tr>
		</c:forEach>
                    
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
        <!-- /.container-fluid -->

      </div>
      <!-- End of Main Content -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>Copyright &copy; Your Website 2020</span>
          </div>
        </div>
      </footer>
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">��</span>
          </button>
        </div>
        <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
        <div class="modal-footer">
          <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
          <a class="btn btn-primary" href="login.html">Logout</a>
        </div>
      </div>
    </div>
  </div>
  
  
  
                      <c:forEach var="OrderVO" items="${list}">   
 <div class="modal fade" id="${OrderVO.order_id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content modal-xl">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">�ӫ~�Ա�</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">      
        <div class="container">
        
        
       
        <c:forEach var="detailVO" items="${detailSvc.getAllOrder(OrderVO.order_id)}">
    <ul class="list-group">
    <li class="list-group-item list-group-item-danger de-header" >�ӫ~�W�� ${detailVO.product_name}</li>
  <li class="list-group-item list-group-item-light de-con" >�ʶR�ƶq:${detailVO.order_buymount}</li>
  <li class="list-group-item list-group-item-light de-con">�ӫ~���:${detailVO.product_price}</li>
      </ul>				
		</c:forEach> 
								
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
  </c:forEach> 
  
  <%-- socket --%>
<%@ include file="/back-end/haoren/socket.jsp"%>

  <script>
  $(document).ready(function(){
	  $('.orderStateSubmit').click(function(){
		  var Order_id = $(this).prev().prev().val();
		  var Order_status = $(this).prev().prev().prev().val();
		  var member_id = $(this).prev().val();
		  var status = $(this).parents('tr').find('#status');
		 $.ajax({
			 type: 'POST',
			 url: '${pageContext.request.contextPath}/order.do',
			 data: {"Order_id":Order_id, "Order_status":Order_status, "action":"ajax_update_order_status"},
			 success: function (data){
				if(data === 'updateSucess'){
				 	var obj = {				 						
					 	"sender" : self,
					 	"receiver" : member_id,
					 	"message" : "�A���q��["+Order_id+"]�w�g�X�f�O", 					
					 	"url" : "${pageContext.request.contextPath}/front-end/haoren/orderPage.jsp"
				 	}
				 	webSocket.send(JSON.stringify(obj));
				};
				if(Order_status == 0 ){
				$(status).replaceWith('<td id="status">�w�U��w�I��</td>');
				}else{
				$(status).replaceWith('<td id="status">�w�X�f</td>');
				}
			 },
			 error: function(xhr, ajaxOptions, thrownError){
			     console.log(xhr.status);
			     console.log(thrownError);
			 }
		}); 		  
	  }); 
  });
  
  window.onload = function() {
		connect();
	};
	 </script>
  
  
  
  

  <!-- Bootstrap core JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/jquery/jquery.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="<%=request.getContextPath()%>/back-end/haoren/js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/haoren/vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="<%=request.getContextPath()%>/back-end/haoren/js/demo/datatables-demo.js"></script>

</body>

</html>
