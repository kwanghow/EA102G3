<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page
	import="com.employee.model.* , com.authority.model.* ,java.util.* ,com.features.model.*"%>

<%
	
%>



<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>後台管理 - Login</title>

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

<style>
* {
	font-family: 微軟正黑體;
}
</style>
</head>

<body class="bg-gradient-primary">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
							<div class="col-lg-6">
								<div class="p-5">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-4">歡迎來到我就健!</h1>
									</div>

									<c:if test="${not empty errorMsgs}">
										<font>請修正以下錯誤</font>
										<ul>
											<c:forEach var="message" items="${errorMsgs}">
												<li>${message}</li>
											</c:forEach>
										</ul>
									</c:if>



									<form method="post" class="user"
										action="<%=request.getContextPath()%>/front-end/kevin/emp/emp.do">
										<div class="form-group">
											<input type="text" name="emp_account"
												class="form-control form-control-user"
												id="exampleInputEmail" aria-describedby="emailHelp"
												placeholder="Enter Your Account...">
										</div>
										<div class="form-group">
											<input type="password" name="emp_psw"
												class="form-control form-control-user"
												id="exampleInputPassword" placeholder="Password">
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input"
													id="customCheck"> 
											</div>
										</div>
										<input type="submit"
											class="btn btn-primary btn-user btn-block" name="action"
											value="Login">
										<hr>
									</form>
									<hr>



									<div class="text-center">
										<a class="small"
											href="<%=request.getContextPath()%>/back-end/kevin/index/forgot-password.jsp">Forgot
											Password?</a>
									</div>
									<div class="text-center">
										<a class="small"
											href="<%=request.getContextPath()%>/back-end/kevin/index/register.jsp">Create
											an Account!</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

			</div>

		</div>

	</div>

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

