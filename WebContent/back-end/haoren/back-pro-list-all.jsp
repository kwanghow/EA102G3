<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.model.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.sql.Timestamp"%>
<!DOCTYPE html>
<html lang="en">

<% 
ProductService productService = new ProductService();
	List<ProVO> list = productService.getAll_back();
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
	
	
	
	 <script src="https://code.jquery.com/jquery-3.4.1.js"
integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="crossorigin="anonymous"></script>
	
<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>�ӫ~�q��޲z</title>

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
	 
.sorting_asc{
width:80px
}

.sorting{
width:80px
}


	
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


          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">�s�W�ӫ~</h6>
              <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/back-end/haoren/back-pro-add.jsp">�s�W�ӫ~</a>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>�ӫ~�s��</th>
                      <th>�ӫ~�W��</th>
                      <th>�ӫ~���O</th>
                      <th>����</th>
                      <th>�w�s</th>
                      <th>�ӫ~�Ա�</th>
                      <th>�ӫ~�~�P</th>
                      <th>�ӫ~��</th>
                      <th>�ӫ~��</th>
                      <th>�ӫ~�Y��</th>
                      <th>������</th>
                      <th>�ӫ~���A</th>
                      <th></th>                      
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
                      <th></th>
                      <th></th>                      
                      <th></th>                      
                    </tr>
                  </tfoot>
                  <tbody>
                  <c:forEach var="proVO" items="${list}">
                  <tr>
                 <td>${proVO.product_Id}</td>
				<td>${proVO.product_Name}</td>
	 			<td>
					${categoryService.getOneCategory(proVO.getCategory_Id()).category_Name}
				</td>
				<td>${proVO.product_Price}</td>
				<td>${proVO.product_Stock}</td>			
				<td>				
				<button class="btn btn-info btn-icon-split" type="button" data-toggle="modal" data-target="#${proVO.product_Id}">�ӫ~��T</button></td>
				<td>${proVO.product_Brand}</td>
				<td><img src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=${proVO.product_Id}" 
				width="100px" height="100px"></td>
				
				<td><img src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic1&PRODUCT_ID=${proVO.product_Id}" 
				width="100px" height="100px"></td>
				
				<td><img src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Spic&PRODUCT_ID=${proVO.product_Id}"
				 width="100px" height="100px"></td>	
				 			
				<c:choose>
								 <c:when test="${proVO.product_Hot == 1}">								
								 <td>����</td>
								 </c:when>	
								 <c:otherwise>
								 <td></td>
								 </c:otherwise>							
				</c:choose>
				
				
				
				<c:choose>
			<c:when test="${(proVO.product_Status)==0}">	
				<td >�W�[��</td>
		    </c:when>					    		    
			<c:otherwise>
    			<td >�U�[��</td>
   			</c:otherwise> 
		</c:choose>
				
				<td>

					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/product/product.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="�ק�" class="btn btn-info btn-icon-split"> <input type="hidden"
							name="Product_Id" value="${proVO.product_Id}"> <input type="hidden"
							name="action" value="getOne_For_Update">
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
  
  
  <c:forEach var="proVO" items="${list}">
  <div class="modal fade" id="${proVO.product_Id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">�ӫ~�Ա�</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">      
        					<ul>
								<li>${proVO.product_Detail}</li>								
							</ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        
      </div>
    </div>
  </div>
</div>
  </c:forEach>
  
  
  
 
  
  
  
  

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
