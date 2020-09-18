<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page
	import="com.employee.model.* , com.authority.model.* ,java.util.* ,com.features.model.*"%>

<%  
    session.getAttribute("location");
    
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
	AuthorityService authoritySvc = new AuthorityService();
	
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

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>後台管理系統</title>

<!-- Custom fonts for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/kevin/index/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link
	href="<%=request.getContextPath()%>/back-end/kevin/index/css/sb-admin-2.min.css"
	rel="stylesheet">

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

<script Charset="UTF-8" type="text/javascript"
	src="<%=request.getContextPath()%>/back-end/kevin/index/vendor/jquery/jquery.js"></script>
<style>
.main-banner {
	height: 922px;
	padding-top: 100%;
	background-repeat: no-repeat;
	background-size: cover;
	background-position: center;
	opacity: 0.85;
}
</style>


</head>

<body id="page-top" onload="connect();" onunload="disconnect();">

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
			<div class="sidebar-heading"></div>

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
					<div
						class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-0 text-gray-800" id="headtop">${employeeVO.emp_name}&nbsp&nbsp&nbsp您好,歡迎登入</h1>
					</div>


					<!-- Content Row -->
					<div class="col-12" id="emp"
						style="height: 612px; overflow-y: scroll;"></div>
					<!-- Modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-xl">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">後台管理</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body" id="modalbody"></div>



								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">關閉</button>
									<button type="button" class="btn btn-primary" id="update"
										data-action="UpdateOneAJAX" data-dismiss="modal">完成</button>
								</div>
							</div>
						</div>
					</div>
				</div>


				<!-- Content Row -->
				<div class="row">

					<!-- Content Column -->
					<div class="col-lg-6 mb-4">


						<!-- /.container-fluid -->

					</div>
					<!-- End of Main Content -->

					<!-- Footer -->
					<footer class="sticky-footer">
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
			<a class="scroll-to-top rounded" href="#page-top"> <i
				class="fas fa-angle-up"></i>
			</a>

			<!-- Logout Modal-->
			<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">確定要登出?</h5>
							<button class="close" type="button" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
						</div>
						<div class="modal-body">若欲結束當前連線請點下方登出</div>
						<div class="modal-footer">
							<button class="btn btn-secondary" type="button"
								data-dismiss="modal">Cancel</button>
							<form method="post" class="user"
								action="<%=request.getContextPath()%>/front-end/kevin/emp/emp.do">
								<input type="submit" class="btn btn-primary btn-user btn-block"
									name="action" value="Logout">
							</form>
						</div>
					</div>
				</div>
			</div>

			<script>
			let featuresError='${featuresError}';
			if(featuresError!=''){
				alert(featuresError);
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/authority/authority.do",
					 data: {"action":"RemoveError"},
					 dataType: "text",
					 success: function (res){
						 console.log(res);
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        })
			}
			
			
			
			$(document).on('click','#empManage',function(){
				let post = {};
				let postNum = ""; // 點擊修改的文章
				data = {};
				data.action="DisplayAllAJAX";
				$('#emp').html(`<div class="row" style="margin-bottom: 15px">
						<div class="col-1">
							<b>員工編號</b>
						</div>
						<div class="col-1">
							<b>員工姓名</b>
						</div>
						<div class="col-2">
							<b>員工帳號</b>
						</div>
						<div class="col-1">
							<b>員工密碼</b>
						</div>
						<div class="col-2">
							<b>員工電話</b>
						</div>
						<div class="col-3">
							<b>員工信箱</b>
						</div>
						<div class="col-2">
							<b>修改</b>
						</div>
					</div>`);
				
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/authority/authority.do",
					 data: data,
					 dataType: "json",
					 success: function (data){	
					 $.each(data,function(i){
						 $('#emp').append(`<form class="empDetail `+data[i].emp_id+`" METHOD="post"
									ACTION="<%=request.getContextPath()%>/front-end/kevin/authority/authority.do">
									<div class="row" style="margin-bottom: 8px" id="emp">
										<div class="col-1">`+data[i].emp_id+`</div>
										<div class="col-1">`+data[i].emp_name+`</div>
										<div class="col-2">`+data[i].emp_account+`</div>
										<div class="col-1">`+data[i].emp_psw+`</div>
										<div class="col-2">`+data[i].emp_phone+`</div>
										<div class="col-3">`+data[i].emp_email+`</div>
										<div class="col-2">
											<button 
												type="button" class="btn btn-success `+data[i].emp_id+` emp" 
												name="action"
												data-toggle="modal" 
												data-target="#exampleModal"
												value="DisplayOneAJAX"
												data-emp_id=`+data[i].emp_id+`
												data-emp_name=`+data[i].emp_name+`
												data-emp_account=`+data[i].emp_account+`
												data-emp_psw=`+data[i].emp_psw+`
												data-emp_phone=`+data[i].emp_phone+`
												data-emp_email=`+data[i].emp_email+`
												data-action="DisplayOneAJAX"
												>修改</button>
										</div>
									</div>
									</form>`);
						 $('#headtop').text("會員管理");
						 }); 
					 
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        });
			});
			
//				最新消息管理
			$(document).on('click','#newsManage',function(){
				data = {};
				data.action="DisplayAllAJAX";
				$('#emp').html(`<div class="row" style="margin-bottom: 15px">
						<div class="col-1">
							<b>最新消息編號</b>
						</div>
						<div class="col-1">
							<b>最新消息分類</b>
						</div>
						<div class="col-1">
							<b>刊登員工編號</b>
						</div>
						<div class="col-2">
						<b>消息標題</b>
					    </div>
						<div class="col-4">
							<b>最新消息內容</b>
						</div>
						<div class="col-1">
							<b>修改</b>
						</div>
						<div class="col-1">
						<b>刪除</b>
					</div>
					</div>`);
				
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/news/news.do",
					 data: data,
					 dataType: "json",
					 success: function (data){
						 post = data;
					 $.each(data,function(i){
						 $('#emp').append(`<form class="newsDetail `+data[i].emp_id+`" METHOD="post"
									ACTION="<%=request.getContextPath()%>/front-end/kevin/news/news.do">
									<div class="row" style="margin-bottom: 8px" id="emp">
										<div class="col-1">`+data[i].news_id+`</div>
										<div class="col-1">`+data[i].news_spec+`</div>
										<div class="col-1">`+data[i].emp_id+`</div>
										<div class="col-2">`+data[i].news_header+`</div>
										<div class="col-4">`+data[i].news_content+`</div>
										<div class="col-1"><button 
										type="button" class="btn btn-success `+data[i].news_id+` news" 
											name="action"
											data-toggle="modal" 
											data-target="#exampleModal"
											value="UpdateOneAJAX"
											data-post_num=`+i+`
											data-action="UpdateOneAJAX"
											>修改</button></div>
											<form method="post" action="<%=request.getContextPath()%>/front-end/kevin/news/news.do">
										<div class="col-1">
											<button 
												type="submit" class="btn btn-success `+data[i].news_id+` news" 
												id="delete"
												name="action"
												value="DeleteOneAJAX"
												data-post_num=`+i+`
												data-action="DeleteOneAJAX"
												>刪除</button>
												<input type="hidden" name="news_id" value="`+data[i].news_id+`"/>
										</div>
									</div>
									</form>`);
						 $('#headtop').html(`最新消息管理&nbsp<button 
							type="button" class="col-4 btn-dark" id="insert" 
							name="action"
							value="InsertOneAJAX"
							data-post_num=`+i+`
							data-toggle="modal"
							data-target="#exampleModal"
							data-action="InsertOneAJAX">新增</button>`);
						 }); 
					 
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        });
			});
			
			//最新消息管理新增按鈕功能
			$(document).on('click',"#insert",function(){
				$("#modalbody").html(`<form>
						<div class="form-group">
							<label for="specnews">消息分類</label>
							<select id="specnews">
							  <option value="1">最新公告</option>
							  <option value="2">促銷活動</option>
							  <option value="3">推薦教練</option>
							</select>
						</div>
						<div class="form-group">
							<label for="idemp">員工帳號</label><br>
							<div id="idemp">${employeeVO.emp_id}</div>
						</div>
						<div class="form-group">
						<label for="headernews">消息標題</label> <input type="text" class="form-control" id="headernews">
					    </div>
						<div class="form-group">
							<label for="contentnews">最新消息內容</label> <textarea rows="4" cols="50" class="form-control" id="contentnews"></textarea>
						</div>
						</form>`);
			data = {};
			// 修改data
			postNum = $(this).data("post_num");	
			
			$(".modal-footer").unbind('click').on('click',"#update",function(){
				
				post[postNum].news_spec = $('#exampleModal form > div:nth-child(1) select').val();
				post[postNum].emp_id = $('#exampleModal form > div:nth-child(2) div').text();
				post[postNum].news_header = $('#exampleModal form > div:nth-child(3) input').val();
				post[postNum].news_content = $('#exampleModal form > div:nth-child(4) textarea').val();
				post[postNum].action = 'InsertOneAJAX';
				sendMessage();
				
				
				// 更新資料
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/news/news.do",
					 data: post[postNum],
					 dataType: "json",
					 success: function (res){
						 console.log(res);
// 						$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(1)').text(post[postNum].news_id);
// 			 			$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(2)').text(post[postNum].news_spec);
// 						$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(3)').text(post[postNum].emp_id);
// 						$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(4)').text(post[postNum].news_header);
// 						$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(5)').text(post[postNum].news_content);
						
						
						// 關閉彈窗
// 						$('[data-dismiss="modal"]').click();
				 		
						
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        })


			});
		});
			
			
			
			
			
			
			
			
			
			
			
			
				//最新消息管理修改按鈕功能
				$(document).on('click',":button.news",function(){
					$("#modalbody").html(`<form>
							<div class="form-group">
								<label>消息編號</label>
							</div>
							<div class="form-group">
								<label for="specnews">消息分類</label>
								<input type="text" class="form-control" id="specnews">
							</div>
							<div class="form-group">
								<label for="idemp">修改/發佈員工</label> <input type="text" class="form-control" id="idemp">
							</div>
							<div class="form-group">
							<label for="headernews">消息標題</label> <input type="text" class="form-control" id="headernews">
						    </div>
							<div class="form-group">
								<label for="contentnews">最新消息內容</label> <textarea rows="4" cols="50" class="form-control" id="contentnews"></textarea>
							</div>
							</form>`);
				data = {};
				// 修改data
				postNum = $(this).data("post_num");
					// 清空表單資料
					$('#exampleModal form > div:nth-child(1) label').text('')
					$('#exampleModal form > div:nth-child(2) input').val('')
					$('#exampleModal form > div:nth-child(3) input').val('')
					$('#exampleModal form > div:nth-child(4) input').val('')
					$('#exampleModal form > div:nth-child(5) input').val('')
					
					// 將按鈕資料放入彈窗
					$('#exampleModal form > div:nth-child(1) label').text(post[postNum].news_id);
					$('#exampleModal form > div:nth-child(2) input').val(post[postNum].news_spec);
					$('#exampleModal form > div:nth-child(3) input').val(post[postNum].emp_id);
					$('#exampleModal form > div:nth-child(4) input').val(post[postNum].news_header);
					$('#exampleModal form > div:nth-child(5) textarea').val(post[postNum].news_content);
					
//						$.ajax({
//							 type: "POST",
<%-- 							 url: "<%=request.getContextPath()%>/front-end/news/news.do", --%>
//							 data: data,
//							 dataType: "json",
//							 success: function (data){	
//								console.log(data);
//						     },
//				            error: function(){alert("AJAX-class發生錯誤囉!")}
//				        });
					
				$(".modal-footer").unbind('click').on('click',"#update",function(){
					
					
					post[postNum].news_id = $('#exampleModal form > div:nth-child(1) label').text();
					post[postNum].news_spec = $('#exampleModal form > div:nth-child(2) input').val();
					post[postNum].emp_id = $('#exampleModal form > div:nth-child(3) input').val();
					post[postNum].news_header = $('#exampleModal form > div:nth-child(4) input').val();
					post[postNum].news_content = $('#exampleModal form > div:nth-child(5) textarea').val();
					post[postNum].action = $(this).data("action");

					// 更新資料
					$.ajax({
						 type: "POST",
						 url: "<%=request.getContextPath()%>/front-end/kevin/news/news.do",
						 data: post[postNum],
						 dataType: "json",
						 success: function (res){
							 console.log(res);
							$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(1)').text(post[postNum].news_id);
				 			$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(2)').text(post[postNum].news_spec);
							$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(3)').text(post[postNum].emp_id);
							$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(4)').text(post[postNum].news_header);
							$('.col-12#emp form:nth-child('+(postNum+2)+') .row > div:nth-child(5)').text(post[postNum].news_content);
							
// 							$(":button.news").off('click');
							// 關閉彈窗
// 							$('[data-dismiss="modal"]').click();
					 		
							
					     },
			            error: function(){alert("AJAX-class發生錯誤囉!")}
			        })


				});
			});
				
			//員工管理修改按鈕功能
			$(document).on('click',":button.emp",function(){
				data = {};
				$("#modalbody").html(`<form>
						<div class="form-group">
							<label>員工編號</label>
						</div>
						<div class="form-group">
							<label for="nameemp">員工姓名</label> <input type="text"
								class="form-control" id="nameemp">
						</div>
						<div class="form-group">
							<label for="accountemp">員工帳號</label> <input type="text"
								class="form-control" id="accountemp">
						</div>
						<div class="form-group">
						<label for="pswemp">員工密碼</label> <input
							type="text" class="form-control" id="pswemp">
					</div>
						<div class="form-group">
							<label for="emailemp">電子郵件</label> <input type="email"
								class="form-control" id="emailemp"
								aria-describedby="emailHelp">
						</div>
						<div class="form-group">
						<label for="phoneemp">電話</label> <input type="text"
							class="form-control" id="phoneemp">
					</div>
						<label>選擇權限:</label>
						<div class="form-group">
							<input type="checkbox" name="features" value="F01">會員管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F02">員工管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F03">揪團留言檢舉管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F04">文章檢舉管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F05">專長種類管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F06">教練管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F07">揪團管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F08">最新消息管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F09">一對一課程管理<br>
						</div>

						<div class="form-group">
							<input type="checkbox" name="features" value="F10">訂單管理<br>
						</div>
						<div class="form-group">
							<input type="checkbox" name="features" value="F11">商品管理<br>
						</div>
						<div class="form-group">
						<input name="clickAll" id="clickAll" type="checkbox"> 全選/全不選
					    </div>
					</form>`);
				// 修改data
				data.emp_id = $(this).data("emp_id");
				data.emp_name = $(this).data("emp_name");
				data.emp_account = $(this).data("emp_account");
				data.emp_psw = $(this).data("emp_psw");
				data.emp_email = $(this).data("emp_email");
				data.emp_phone = $(this).data("emp_phone");
				data.action = $(this).data("action");
				
				// 清空表單資料
				$('#exampleModal form > div:nth-child(1) label').text('')
				$('#exampleModal form > div:nth-child(2) input').val('')
				$('#exampleModal form > div:nth-child(3) input').val('')
				$('#exampleModal form > div:nth-child(4) input').val('')
				$('#exampleModal form > div:nth-child(5) input').val('')
				$('#exampleModal form > div:nth-child(6) input').val('')
				
				// 將按鈕資料放入彈窗
				$('#exampleModal form > div:nth-child(1) label').text(data.emp_id)
				$('#exampleModal form > div:nth-child(2) input').val(data.emp_name)
				$('#exampleModal form > div:nth-child(3) input').val(data.emp_account)
				$('#exampleModal form > div:nth-child(4) input').val(data.emp_psw)
				$('#exampleModal form > div:nth-child(5) input').val(data.emp_email)
				$('#exampleModal form > div:nth-child(6) input').val(data.emp_phone)
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/authority/authority.do",
					 data: data,
					 dataType: "json",
					 success: function (data){	
						//放入權限
			 			$("input[name='features']").each(function(){
			 				$(this).prop("checked",false);
			 			if ($.inArray($(this).val(),data[0].authorityList)!==-1){
						 $(this).prop("checked",true);
			 			}
			 			}); 
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        });
				
				
				
				
				$(".modal-footer").unbind('click').on('click',"#update",function(){
				data.emp_id = $('#exampleModal form > div:nth-child(1) label').text();
				data.emp_name = $('#exampleModal form > div:nth-child(2) input').val();
				data.emp_account = $('#exampleModal form > div:nth-child(3) input').val();
				data.emp_psw = $('#exampleModal form > div:nth-child(4) input').val();
				data.emp_email = $('#exampleModal form > div:nth-child(5) input').val();
				data.emp_phone = $('#exampleModal form > div:nth-child(6) input').val();
				data.action = $(this).data("action");
				var features = new Array();
				$('input:checkbox:checked[name="features"]').each(function(i) {features[i] = this.value });
				data.features =features;
				// 更新資料
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/authority/authority.do",
					 data: data,
					 dataType: "json",
					 success: function (data){	
						$('.' + data.emp_id + ' .row > div:nth-child(1)').text(data.emp_id);
			 			$('.' + data.emp_id + ' .row > div:nth-child(2)').text(data.emp_name);
						$('.' + data.emp_id + ' .row > div:nth-child(3)').text(data.emp_account);
			 			$('.' + data.emp_id + ' .row > div:nth-child(4)').text(data.emp_psw);
						$('.' + data.emp_id + ' .row > div:nth-child(5)').text(data.emp_email);
						$('.' + data.emp_id + ' .row > div:nth-child(6)').text(data.emp_phone);
						
						// 將資料寫入按鈕
						$('button.'+ data.emp_id).data("emp_id",data.emp_id);
						$('button.'+ data.emp_id).data("emp_name",data.emp_name);
						$('button.'+ data.emp_id).data("emp_account",data.emp_account);
						$('button.'+ data.emp_id).data("emp_psw",data.emp_psw);
						$('button.'+ data.emp_id).data("emp_email",data.emp_email);
						$('button.'+ data.emp_id).data("emp_phone",data.emp_phone);
						
						// 關閉彈窗
// 						$('[data-dismiss="modal"]').click();
				 		
						
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        })
				});
			});
			
			
			
			
		
			//修改個人資料
			
			$(document).on('click','#profile',function(){
				data = {};
				data.emp_id="${employeeVO.emp_id}";
				data.action="MyProfile"
				
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/emp/emp.do",
					 data: data,
					 dataType: "json",
					 success: function (data){
						 $("#modalbody").html(`<form>
									<div class="form-group">
										<label>`+data.emp_id+`</label>
									</div>
									<div class="form-group">
										<label for="nameemp">員工姓名</label><input type="text"
											class="form-control" id="nameemp" value="`+data.emp_name+`">
									</div>
									<div class="form-group">
										<label for="accountemp">員工帳號</label> <input type="text"
											class="form-control" id="accountemp" value='`+data.emp_account+`'>
									</div>
									<div class="form-group">
									<label for="pswemp">員工密碼</label> <input
										type="text" class="form-control" id="pswemp" value='`+data.emp_psw+`'>
								</div>
									<div class="form-group">
										<label for="emailemp">電子郵件</label> <input type="email"
											class="form-control" id="emailemp"
											aria-describedby="emailHelp" value='`+data.emp_email+`'>
									</div>
									<div class="form-group">
									<label for="phoneemp">電話</label> <input type="text"
										class="form-control" id="phoneemp" value='`+data.emp_phone+`'>
								</div>
									</form>`);
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        });
				
				$(".modal-footer").unbind('click').on('click',"#update",function(){
					
					data.emp_id = $('#exampleModal form > div:nth-child(1) label').text();
					data.emp_name = $('#exampleModal form > div:nth-child(2) input').val();
					data.emp_account = $('#exampleModal form > div:nth-child(3) input').val();
					data.emp_psw = $('#exampleModal form > div:nth-child(4) input').val();
					data.emp_email = $('#exampleModal form > div:nth-child(5) input').val()
                    data.emp_phone = $('#exampleModal form > div:nth-child(6) input').val()
					data.action="ProfileEdit";
				
				$.ajax({
					 type: "POST",
					 url: "<%=request.getContextPath()%>/front-end/kevin/emp/emp.do",
					 data: data,
					 dataType: "json",
					 success: function (data){
				     },
		            error: function(){alert("AJAX-class發生錯誤囉!")}
		        });
			});
		});
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
// 			WebSocket測試
			var websocket = null;
			var memberCount=0;
			if(${empChatId==null}){
				var MyPoint = "/websocket/"+memberCount;
				memberCount++;
			}else{
				var MyPoint = "/websocket/${empChatId}";
			}
			var host = window.location.host;
			var path = window.location.pathname;
			var webCtx = path.substring(0, path.indexOf('/', 1));
			var endPointURL = "wss://" + window.location.host + webCtx + MyPoint;
			//判斷當前瀏覽器是否支援WebSocket
			console.log(endPointURL);
			function connect() {
			if ('WebSocket' in window) {
				websocket = new WebSocket(endPointURL);
			} else {
				alert('當前瀏覽器 Not support websocket')
			}
			websocket.onmessage = function(event) {
				var jsonObj = JSON.parse(event.data);
				var message = jsonObj.userName + ": " + jsonObj.message + "\r\n";
// 				Swal.fire({
// 					  title: '現在有新的最新消息發佈了!!，趕緊快去看最新消息',
// 					  width: 600,
// 					  padding: '3em',
<%-- 					  background: '#fff url(<%=request.getContextPath()%>/front-end/kevin/css/images/sweet.png)', --%>
// 					  backdrop: `
// 					    rgba(0,0,123,0.4)
<%-- 					    url("<%=request.getContextPath()%>/front-end/kevin/css/images/cat.gif") --%>
// 					    left top
// 					    no-repeat
// 					  `
// 					})
			};
		}
			function sendMessage() {
                var empChatId = "${empChatId}";
				var inputMessage = "被我抓到惹";
				var message = inputMessage.value;

					var jsonObj = {
						"empChatId" : empChatId,
						"message" : message
					};
					websocket.send(JSON.stringify(jsonObj));
					inputMessage.value = "";
				}
			
			function disconnect() {
				memberCount--;
				websocket.close();
			}
			
				
			
			
			
			
				
				
				</script>
				<script>
				$(document).on('click','#clickAll',function(){
					   if($("#clickAll").prop("checked")) {
					     $("input[name='features']").each(function() {
					         $(this).prop("checked", true);
					     });
					   } else {
					     $("input[name='features']").each(function() {
					         $(this).prop("checked", false);
					     });           
					   }
					});
				</script>

			<!-- Bootstrap core JavaScript-->
			<script
				src="<%=request.getContextPath()%>/back-end/kevin/index/vendor/jquery/jquery.min.js"></script>
			<script
				src="<%=request.getContextPath()%>/back-end/kevin/index/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

			<!-- Core plugin JavaScript-->
			<script
				src="<%=request.getContextPath()%>/back-end/kevin/index/vendor/jquery-easing/jquery.easing.min.js"></script>

			<!-- Custom scripts for all pages-->
			<script
				src="<%=request.getContextPath()%>/back-end/kevin/index/js/sb-admin-2.min.js"></script>
</body>
</html>