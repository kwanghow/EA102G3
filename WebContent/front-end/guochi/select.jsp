<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>HOME</title>

<style>
h3 {
	color: yellow;
	display: inline;
}

table {
	background-color: #003333;
	width: 30%;
	text-align: center;
	height: 80px;
}
</style>

<!-- /front-end/guochi -->

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;display=swap"
	rel="stylesheet">

<title>Training Studio - Free CSS Template</title>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
	integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
	crossorigin="anonymous"></script>

<!--

TemplateMo 548 Training Studio

https://templatemo.com/tm-548-training-studio

-->
<!-- Additional CSS Files -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi//assets/css/font-awesome.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-end/guochi//assets/css/templatemo-training-studio.css">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">

</head>
<body>

	<!-- 	<div id="js-preloader" class="js-preloader loaded"> -->
	<!-- 		<div class="preloader-inner"> -->
	<!-- 			<span class="dot"></span> -->
	<!-- 			<div class="dots"> -->
	<!-- 				<span></span> <span></span> <span></span> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->

	<!-- 	<header class="header-area header-sticky background-header"> -->
	<!-- 		<div class="container"> -->
	<!-- 			<div class="row"> -->
	<!-- 				<div class="col-12"> -->
	<!-- 					<nav class="main-nav"> -->
	<!-- 						***** Logo Start ***** -->
	<!-- 						<a href="index.html" class="logo">我就<em> 健</em></a> -->
	<!-- 						***** Logo End ***** -->
	<!-- 						***** Menu Start ***** -->
	<!-- 						<ul class="nav"> -->
	<!-- 							<li class="scroll-to-section"><a href="#top" class="active">Home</a></li> -->
	<!-- 							<li class="scroll-to-section"><a href="#features" class="">About</a></li> -->
	<!-- 							<li class="scroll-to-section"><a href="#our-classes" -->
	<!-- 								class="">Classes</a></li> -->
	<!-- 							<li class="scroll-to-section"><a href="#schedule" class="">Schedules</a></li> -->
	<!-- 							<li class="scroll-to-section"><a href="#contact-us">Contact</a></li> -->
	<!-- 							<li class="main-button"><a href="#">Sign Up</a></li> -->
	<!-- 						</ul> -->
	<!-- 						<a class="menu-trigger"> <span>Menu</span> -->
	<!-- 						</a> -->
	<!-- 						***** Menu End ***** -->
	<!-- 					</nav> -->
	<!-- 				</div> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</header> -->

	<!-- 	<table> -->
	<!-- 		<tr> -->
	<!-- 			<td><h3>HOME</h3></td> -->
	<!-- 		</tr> -->

	<!-- 	</table> -->

	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<%-- 	<ul><a href="<%=request.getContextPath()%>">全部的會員</a></ul> --%>

	<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
	<ul>


		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/aaa/diet.do">
				<b>顯示飲食清單:</b> <select size="1" name="memno">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.member_Id}">${memVO.member_Id}
					</c:forEach>
				</select> <input type="submit" value="送出"> <input type="hidden"
					name="action" value="listEmps_ByDeptno_A">
			</FORM>
		</li>

		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/aaa/diet.do">
				<b>輸入編號 (M001):</b> <input type="text" name="memno"> <input
					type="submit" value="送出"> <input type="hidden"
					name="action" value="getOne_For_Display">
			</FORM>
		</li>
	</ul>

	<!-- Button trigger modal -->
	<button type="button" class="btn btn-primary" data-toggle="modal"
		data-target="#exampleModal" data-whatever="@mdo">Open modal
		for @mdo</button>

	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">New message</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form>
						<div class="form-group">
							<label for="recipient-name" class="col-form-label">Recipient:</label>
							<input type="text" class="form-control" id="recipient-name">
						</div>
						<div class="form-group">
							<label for="message-text" class="col-form-label">Message:</label>
							<textarea class="form-control" id="message-text"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Send message</button>
				</div>
			</div>
		</div>
	</div>

	<form method="post" ACTION="<%=request.getContextPath()%>/aaa/diet.do">
		<select size="1" name="memno">
			<c:forEach var="memVO" items="${memSvc.all}">
				<option value="${memVO.member_Id}">${memVO.member_Id}
			</c:forEach>
		</select> <input type="submit" name="action" value="calendar">
	</form>






</body>
</html>