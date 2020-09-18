<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="java.util.*"%>

<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
%>

<!DOCTYPE html>
<html lang="en">
<title>教練個人頁面 - coachPage</title>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap"
	rel="stylesheet">
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css">

<style>
body {
	background: linear-gradient(rgba(255, 255, 255, 0.5),
		rgba(255, 255, 255, 0.5)),
		url("<%=request.getContextPath()%>/front-end/assets/images/222.jpeg")
		center no-repeat fixed;
}

* {
	font-family: 微軟正黑體;
}

.form-signin .btn {
	padding: .5rem 1rem;
	font-size: 1.25rem;
	line-height: 1.5;
	border-radius: .3rem;
	color: #fff;
	background-color: #ed563b;
}

.InOut {
	color: #ed563b;
}

.InOut .btn {
	color: #fff;
	background-color: #ed563b;
}

.coach_pic {
	width: 300px;
	height: 300px;
	margin-top: 400px;
}

p {
	font-weight: 600;
}

.feature-item .right-content h4 {
	width: 540px;
}

.feature-item .right-content h4:after {
	content: "";
	width: 280px;
	height: 2px;
	background: #ddd;
	position: absolute;
	left: calc(100%/ 2 - 280px/ 4);
	margin-top: 29px;
}

#features select {
	margin-top: 15px;
}

.bg {
	position: fixed;
	top: 0;
	left: 0;
	bottom: 0;
	right: 0;
	z-index: 0;
}

.bg img {
	min-height: 100%;
	width: 100%;
	opacity: 0.5;
}
</style>
</head>

<body class="text-center">
	<!-- ***** Header Area Start ***** -->

	<%@ include file="headerh.file"%>
	<!-- ***** Header Area End ***** -->
	<!-- ***** Features Item Start ***** -->
	<section class="section" id="features">
		<div class="container">
			<div class="row">
				<div class="col-lg-6 offset-lg-3">
					<div class="section-heading">
						<h2>
							教練 <em>個人管理頁面</em>
						</h2>
						<img src="assets/images/line-dec.png" alt="waves">
						<p>${memLogIn.mem_Name}教練您好, 今天想如何開始呢?</p>
					</div>
				</div>
				<div class="col-lg-6">
					<ul class="features-items">
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png" alt="First One">
							</div>
							<div class="right-content">
								<h4>個人履歷修改</h4>
								<a
									href="<%=request.getContextPath()%>/front-end/updateCoach.jsp"
									class="text-button">前往頁面</a>
							</div>
						</li>
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png"
									alt="second one">
							</div>
							<div class="right-content">
								<h4>揪團申請</h4>
								<a href="#" class="text-button">前往頁面</a>
							</div>
						</li>
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png"
									alt="third gym training">
							</div>
							<div class="right-content">
								<h4>私人課程管理</h4>
								<a href="<%=request.getContextPath()%>/front-end/Jessica/coach/course_productList.jsp" class="text-button">課程管理</a>　　
								<a href="<%=request.getContextPath()%>/front-end/Jessica/coach/course_salesList.jsp" class="text-button">銷售管理</a>
							</div>
						</li>
					</ul>
				</div>
				<div class="col-lg-6">
					<ul class="features-items">
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png"
									alt="fourth muscle">
							</div>
							<div class="right-content">
								<h4>課程時刻表</h4>
								<a href="MyCalendar.jsp?coach_Id=${coachLogIn.coach_Id}"
									class="text-button">前往頁面</a>
							</div>
						</li>
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png"
									alt="training fifth">
							</div>
							<div class="right-content">
								<h4>上課紀錄</h4>
								<a href="#" class="text-button">前往頁面</a>
							</div>
						</li>
						<li class="feature-item">
							<div class="left-icon">
								<img src="assets/images/features-first-icon.png"
									alt="gym training">
							</div>
							<div class="right-content">
								<h4>直播管理</h4>
								<a
									href="<%=request.getContextPath()%>/front-end/kevin/stream/streamManager.jsp"
									class="text-button">前往頁面</a>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</section>
	<!-- ***** Features Item End ***** -->

	<!-- ***** Footer Start ***** -->
	<%@ include file="footerf.file"%>
	<!-- ***** Footer End ***** -->

	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/jquery-2.1.0.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/popper.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/bootstrap.min.js"></script>
	<!-- Plugins -->
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/scrollreveal.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/waypoints.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/jquery.counterup.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/imgfix.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/mixitup.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/accordions.js"></script>
	<!-- Global Init -->
	<script
		src="<%=request.getContextPath()%>/front-end/assets/js/custom.js"></script>
	<%@ include file="sweet.file"%>
</body>
</html>