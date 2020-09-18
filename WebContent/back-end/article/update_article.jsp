<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.article.model.*"%>
<%@ page import="com.articlemajor.controller.*"%>
<%@ page import="com.report.model.*"  %>

<%
ArtRepVO ArtRepVO = (ArtRepVO) request.getAttribute("artrepVO");

System.out.println("in_up_art_jsp");
%>

<html>
<head>

<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>��x�޲z�|�� back-member</title>

  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/back-end/article/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/back-end/article/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="<%=request.getContextPath()%>/back-end/article/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

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
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">
      <!-- Nav Item - Tables -->
      <li class="nav-item active">
        <a class="nav-link" href="<%=request.getContextPath()%>/back-end/kevin/index/index.jsp">
          <i class="fas fa-fw fa-table"></i>
          <span>��x�޲z�t��</span></a>
      </li>
      <!-- �|���޲z -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>�|���޲z</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Custom Components:</h6>
            <a class="collapse-item" href="<%=request.getContextPath()%>/back-end/article/back-member.jsp">���u�`���޲z</a>
          </div>
        </div>
      </li>
       <!-- �нm�޲z -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>�нm�޲z</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Custom Components:</h6>
            <a class="collapse-item" href="<%=request.getContextPath()%>/back-end/article/back-coach.jsp">�нm�`���޲z</a>
          </div>
        </div>
      </li>
      
       <!-- �峹���|�޲z -->
      <li class="nav-item">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
          <i class="fas fa-fw fa-cog"></i>
          <span>�峹���|�޲z</span>
        </a>
        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Custom Components:</h6>
            <a class="collapse-item" href="<%=request.getContextPath()%>/back-end/article/listAllArticle.jsp">�峹���|�޲z</a>
          </div>
        </div>
      </li>

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
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Valerie Luna</span>
                <img class="img-profile rounded-circle" src="https://source.unsplash.com/QAB-WJcbgJk/60x60">
              </a>
            </li>

          </ul>

        </nav>
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
        <div class="container-fluid">

<title>�峹�� - update_emp_input.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<%-- <table id="table-1">
	<tr><td>
		 <h3>�峹�ק�- update_emp_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/article/listAllArticle.jsp"><img src="<%=request.getContextPath()%>/front-end/article/img/gym.jpg"" width="100" height="32" border="0">�^�޲z��</a></h4>
	
	</td></tr>
</table> --%>

<h3>�峹�ק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>







<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ArtRepServlet" name="form1" enctype="application/x-www-form-urlencoded">
<table>
	<tr>
		<td>�峹�s��:</td>
		<td><%=ArtRepVO.getArticleNo()%></td>
	</tr>
	<tr>
		<td>���|�H:</td>
		<td><%=ArtRepVO.getMemNo()%></td>
	</tr>
	<tr>
		<td>���|�s��:</td>
		<td><%=ArtRepVO.getArtrepNo()%></td>
	</tr>
	<tr>
		<td>���|�z��:</td>
		<td><input type="TEXT" name="Article_Reprea" size="45" disabled value="<%=ArtRepVO.getArticleReprea()%>"/></td>
		<input type= "hidden" name="Article_Reprea" value="<%=ArtRepVO.getArticleReprea()%>">
	</tr> 
	
	
	<tr>
		<td>���|:</td>
		<td>
		<input type="radio" name="article_report" value="1" checked>���B�z
		<input type="radio" name="article_report" value="2">�v�B�z���H�W
		<input type="radio" name="article_report" value="3">�v�B�z�v�U�[
		</td>
	</tr>
			
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="article_no" value="<%=ArtRepVO.getArticleNo()%>">
<input type="submit" value="�e�X�ק�">
</FORM>
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
  <script src="<%=request.getContextPath()%>/back-end/article/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/article/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/article/vendor/jquery-easing/jquery.easing.min.js"></script>
  <!-- Custom scripts for all pages-->
  <script src="<%=request.getContextPath()%>/back-end/article/js/sb-admin-2.min.js"></script>
  <!-- Page level plugins -->
  <script src="<%=request.getContextPath()%>/back-end/article/vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/article/vendor/datatables/dataTables.bootstrap4.min.js"></script>
  <!-- Page level custom scripts -->
  <script src="<%=request.getContextPath()%>/back-end/article/js/demo/datatables-demo.js"></script>


</body>
