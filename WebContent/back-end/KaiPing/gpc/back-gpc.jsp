<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.gpc.model.*"%>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />

<%
GpcService gpcSvc = new GpcService();
List<GpcVO> list = gpcSvc.getAll();
pageContext.setAttribute("list",list);
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

if(!featuresList.contains("F07")) {
	session.setAttribute("featuresError", "�L���v���гq���޲z���}�q");
	session.setAttribute("location",request.getRequestURI());
	response.sendRedirect(request.getContextPath()+"/back-end/kevin/index/index.jsp");
}
%>	






<!DOCTYPE html>
<html>

<head>


  <title>��O���κ޲z</title>

  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/back-end/admin-2/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/back-end/admin-2/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="<%=request.getContextPath()%>/back-end/admin-2/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

<style>
	* {
		font-family: �L�n������;
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
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">

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
          <h1 class="h3 mb-2 text-gray-800">��O���κ޲z</h1>
		  <h3><span id="errorMsgs">${errorMsgs.exception}</span></h3>
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-body">
              <div class="table-responsive">
			 	<table class="table table-dark">
				  <tr>
				    <th scope="col">���νҵ{�s��</th>
				    <th scope="col">�нm</th>
				    <th scope="col">�ҵ{���O</th>
				    <th scope="col">�ҵ{�W��</th>
				    <th scope="col">ú�O�}�l���</th>
				    <th scope="col">��ƫ����W�O</th>
				    <th scope="col">�O��</th>
				    <th scope="col">�ӹΥثe���A</th>

				  </tr>
				  <%@ include file="page1.file" %>
				  <c:forEach var="gpcVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				  <tr>
				    <td>${gpcVO.gpc_Id}</td>
				    <td>${memSvc.getOneMem(coachSvc.getOneByCoach(gpcVO.coach_Id).member_Id).mem_Name}</td>
				    <td>${exptypeSvc.getOneExptype(gpcVO.exp_Id).exp_Name}</td>
				    <td>${gpcVO.gpc_Name}</td>
				    <td><fmt:formatDate value="${gpcVO.pay_Start}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${gpcVO.init_Stamp}" pattern="yyyy-MM-dd HH:mm:ss"/></td>	  <!--���ծĪG��  -->
					<td>${gpcVO.price}</td>
					<td>
						<c:set var="state" value="${gpcVO.gpc_State}"/>			              	
						<c:choose>
							<c:when test="${state == 0}">
						       	�f�֤�
						    </c:when>
							<c:when test="${state == 1}">
								�f�ֳq�L	
						    </c:when>
						    <c:when test="${state == 2}">
						       	���q�L�f��
						    </c:when>
						    <c:otherwise>
						    	�ӽҵ{�w����
						    </c:otherwise>
						</c:choose>
					</td>
					<td>
					  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/KaiPing/gpc/gpc.do" style="margin-bottom: 0px;">
					     <input type="submit" value="�֭�">
					     <input type="hidden" name="gpc_Id"  value="${gpcVO.gpc_Id}">
					     <input type="hidden" name="gpc_State"  value="1">
					     <input type="hidden" name="action"	value="update_GpcState"></FORM>
					</td>
					<td>
					  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/KaiPing/gpc/gpc.do" style="margin-bottom: 0px;">
					     <input type="submit" value="��^">
					     <input type="hidden" name="gpc_Id"  value="${gpcVO.gpc_Id}">
					     <input type="hidden" name="gpc_State"  value="2">
					     <input type="hidden" name="action"	value="update_GpcState"></FORM>
					</td>
				  </tr>
				  </c:forEach>
				 </table>
				 <%@ include file="page2.file" %>
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

  <!-- Bootstrap core JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/admin-2/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/admin-2/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/admin-2/vendor/jquery-easing/jquery.easing.min.js"></script>
  <!-- Custom scripts for all pages-->
  <script src="<%=request.getContextPath()%>/back-end/admin-2/js/sb-admin-2.min.js"></script>
  <!-- Page level plugins -->
  <script src="<%=request.getContextPath()%>/back-end/admin-2/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/admin-2/vendor/datatables/dataTables.bootstrap4.min.js"></script>
  <!-- Page level custom scripts -->
  <script src="<%=request.getContextPath()%>/back-end/admin-2/js/demo/datatables-demo.js"></script>

</body>

</html>
