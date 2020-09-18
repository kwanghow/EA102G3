<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.model.*"%>

<%

ProVO productVO = (ProVO) request.getAttribute("ProVO");
%>

<jsp:useBean id="categorySvc" scope="page" class="com.product_category.model.CategoryService" />
<%@ page import="com.authority.model.* , com.employee.model.* ,com.features.model.*"%>	
<jsp:useBean id="authoritySvc" scope="page"	class="com.authority.model.AuthorityService" />
<%

EmployeeVO employeeVO=(EmployeeVO)session.getAttribute("employeeVO");
String emp_id=employeeVO.getEmp_id();
EmployeeService empSvc = new EmployeeService();
employeeVO=empSvc.getOneEmp(emp_id);
session.setAttribute("employeeVO", employeeVO);
pageContext.setAttribute("employeeVO",employeeVO);

//取全部權限
	FeaturesService feaSvc = new FeaturesService();
	List<FeaturesVO> list2 = feaSvc.getAll();
	request.setAttribute("list2", list2);
	//員工個人權限
		List<AuthorityVO> list1 = authoritySvc.getOneAuthority(emp_id);
		List<String> emp = new ArrayList<String>();
		for (AuthorityVO a : list1) {
			emp.add(a.getFeatures_id());
		}
		pageContext.setAttribute("emp", emp);
%>		
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>後台更新商品</title>

  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="<%=request.getContextPath()%>/back-end/haoren/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

<style>
* {
  font-family: 微軟正黑體;
}
.img{
  border:2px solid black;
  display:inline-block;
  margin-left:5px;
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
				<div class="sidebar-brand-text mx-3">後台管理系統</div>
			</a>


			<!-- Heading -->
			<div class="sidebar-heading">Addons</div>

			<li class="nav-item"><a class="nav-link collapsed" href="#"
				data-toggle="collapse" data-target="#collapseTwo"
				aria-expanded="true" aria-controls="collapseTwo"> <i
					class="fas fa-fw fa-cog"></i> <span>管理功能</span>
			</a>
				<div id="collapseTwo" class="collapse" aria-labelledby="headingTwo"
					data-parent="#accordionSidebar">
					<div class="bg-white py-2 collapse-inner rounded">
						<h6 class="collapse-header">管理功能:</h6>
						<c:forEach var="featuresVO" items="${list2}">
							<c:if test="${fn:contains(emp,featuresVO.features_id)}">
								<c:choose>
									<c:when test="${featuresVO.features_name eq '會員管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/admin-2/back-member.jsp">${featuresVO.features_name}</a>
										
									</c:when>
									<c:when test="${featuresVO.features_name eq '員工管理'}">
										<a class="collapse-item" href="#" id='empManage'>${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '揪團留言檢舉管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/KaiPing/gpc/back-gpc.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '文章檢舉管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/article/listAllArticle.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '專長種類管理'}">
										<a class="collapse-item" href="#">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '教練管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/admin-2/back-coach.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '揪團管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/KaiPing/gpc/back-gpc.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '最新消息管理'}">
										<a class="collapse-item"
											href="#<%--<%=request.getContextPath()%>/back-end/kevin/news/News.jsp--%>"
											id='newsManage'>${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '一對一課程管理'}">
										<a class="collapse-item" href="#">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '訂單管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/haoren/back-order-list-all.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:when test="${featuresVO.features_name eq '商品管理'}">
										<a class="collapse-item" href="<%=request.getContextPath()%>/back-end/haoren/back-pro-list-all.jsp">${featuresVO.features_name}</a>
									</c:when>
									<c:otherwise>
										<a class="collapse-item" href="#">請等候開通權限</a>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
						<%-- 						${(featuresVO.features_name eq "員工管理")?"id='empManage'":""}>${featuresVO.features_name} --%>



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
									<i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> 修改資料
								</button>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal"
									data-target="#logoutModal"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									登出
								</a>
							</div></li>

					</ul>

				</nav>
				<!-- End of Topbar -->
        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <h1 class="h3 mb-2 text-gray-800">商品更新</h1>

          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-body">
              <div class="table-responsive">
 		 
 		 		<form method="post" ACTION="<%=request.getContextPath()%>/product/product.do" name="form1" enctype="multipart/form-data">
<table class="table table-dark">
     
<tr>
	<td>商品編號:</td>
	<td><%=productVO.getProduct_Id()%></td>
</tr>
<tr>
	<td>商品名稱:</td>
	<td><input type="TEXT" name="Product_Name" size="45" value="<%=productVO.getProduct_Name()%>"/></td>
</tr>
<tr>
	<td>價格:</td>
	<td><input type="TEXT" name="Product_Price" size="45" value="<%=productVO.getProduct_Price()%>" /></td>
</tr>
<tr>
	<td>庫存量:</td>
	<td><input type="TEXT" name="Product_Stock" size="45" value="<%=productVO.getProduct_Stock()%>"/></td>
</tr>
<tr>
	<td>商品詳情:</td>
	<td><input type="TEXT" name="Product_Detail" size="45" value="<%=productVO.getProduct_Detail()%>"/></td>
</tr>
<tr>
	<td>商品品牌:</td>
	<td><input type="TEXT" name="Product_Brand" size="45" value="<%=productVO.getProduct_Brand()%>"/></td>
</tr>
<tr>
	<td>商品圖片:</td>
	<td><input type="file" name="Product_Lpic" size = "45" id="Pro_Lpic" accept="image/gif, image/jpeg, image/png" value="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=<%=productVO.getProduct_Id()%>"/></td>
</tr>
<tr>
	<td >商品圖片:</td>
	<td><input type="file" name="Product_Lpic1" size = "45" id="Pro_Lpic1" accept="image/gif, image/jpeg, image/png" value="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic1&PRODUCT_ID=<%=productVO.getProduct_Id()%>"/></td>
		
</tr>
<tr>
	<td>商品縮圖:</td>
	<td><input type="file" name="Product_Spic" size = "45" id="Pro_Spic" accept="image/gif, image/jpeg, image/png" value="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Spic&PRODUCT_ID=<%=productVO.getProduct_Id()%>"/></td>
	
</tr>			
<tr>
	<td>商品熱門:</td>
	<td><input type="TEXT" name="Product_Hot" size="45" value="<%=productVO.getProduct_Hot()%>"/></td>
</tr>
<tr>
	<td>商品狀態:</td>
	<td>
		<input type="radio" name="Product_Status" value="0" checked>上架中
		<input type="radio" name="Product_Status" value="1">下架中
	</td>
</tr>
<tr>
	<td>商品類別:<font color=red><b>*</b></font></td>
	<td>
		<select size="1" name="Category_Id" class="selectpicker">
			<c:forEach var="product_categoryVO" items="${categorySvc.all}">
				<option value="${product_categoryVO.category_Id}" ${(ProVO.category_Id==product_categoryVO.category_Id)? 'selected':'' }> ${product_categoryVO.category_Name}
			</c:forEach>
		</select>
	</td>
</tr>

</table>	
		
<div>	
	<div class="img"><img id="preview_Lpic"  src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=<%=productVO.getProduct_Id()%>"  height=200px width=200px/></div>
	<div class="img"><img id="preview_Lpic1" src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic1&PRODUCT_ID=<%=productVO.getProduct_Id()%>" height=200px width=200px/></div>
	<div class="img"><img id="preview_Spic"  src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Spic&PRODUCT_ID=<%=productVO.getProduct_Id()%>" height=200px width=200px/></div>
</div>
<div>	
	<input type="hidden" name="action" value="update">
	<input type="hidden" name="Product_Id" value="<%=productVO.getProduct_Id()%>">
	<input type="submit" value="送出修改">
</div>
	
</form>		
				 
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
  
  <%@ include file="/back-end/haoren/incloudJS.jsp"%>
  
<script>
window.onload = function() {
	//圖片一
	$("#Pro_Lpic").on('change', function(e){      
	  var file = this.files[0];
	  console.log("change1");
	  
	  var fr = new FileReader();
	  fr.onload = function (e) {
	    $('#preview_Lpic').attr('src', e.target.result);
	  };
	  console.log("load1");
	  fr.readAsDataURL(file);
	});

	//圖片二
	$("#Pro_Lpic1").on('change', function(e){      
	  var file = this.files[0];
	  console.log("change2");
	  var fr = new FileReader();
	  fr.onload = function (e) {
	    $('#preview_Lpic1').attr('src', e.target.result);
	  };
	  console.log("load2");
	  fr.readAsDataURL(file);
	});

	//圖片三
	$("#Pro_Spic").on('change', function(e){      
	  var file = this.files[0];
	  console.log("change3")
	  var fr = new FileReader();
	  fr.onload = function (e) {
	    $('#preview_Spic').attr('src', e.target.result);
	  };
	  console.log("load3");
	  fr.readAsDataURL(file);
	});
}
</script>
  

</body>
</html>
