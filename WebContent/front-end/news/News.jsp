<%@ page contentType="text/html; charset=UTF-8" pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.newsspec.model.*"%>

<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");

	NewsService newsSvc = new NewsService();
	List<NewsVO> list = newsSvc.getAll();
	pageContext.setAttribute("list", list);
	News_specService specSvc = new News_specService();
	List<News_specVO> listA = specSvc.getAll();
	pageContext.setAttribute("listA", listA);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>最新消息</title>



<%@ include file="socket.file"%>
<!-- Bootstrap 的 CSS -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/kevin/css/js/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/kevin/css/fonts/font-awesome.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/kevin/G3header.css">

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

<style type="text/css" media="screen">
body {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.6),
		rgb(255 255 255/ 0.6)),
		url('<%=request.getContextPath()%>/front-end/kevin/css/images/110.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: repeat;
	background-position: top center;
	height: auto !important;
	min-height: 650px;
}

#showCarousel  .carousel-inner>.carousel-item>a>img {
	display: block;
	width: 20%;
	height: 500px;
}

#showCarousel {
	margin: auto;
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

.post {
	text-align: center;
	padding: 80px 15px;
}

#showCarousel {
	padding-top: 80px;
}

p {
	font-size: 16px;
	line-height: 25px;
	color: #0e0e0e;
	font-weight: bold;
}

a {
	color: #ed563b;
}
</style>
</head>


<body onload="connect();" onunload="disconnect();">
	<%@ include file="header.file"%>
	<div class="container">
	</div>

	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<h1 style="font-weight: bold;padding-top:90px;">最新消息</h1>
				<hr>
			</div>
			<c:forEach var="newsVO" items="${list}" begin="1" end="6" step="1">
				<div class="col-lg-4 post">
					<div class="round-circle">
						<a
							href="<%=request.getContextPath()%>/front-end/news/NewsContent.jsp?news_id=${newsVO.news_id}">
							<img style="max-width: 100%;"
							src="<%=request.getContextPath()%>/front-end/news/images/${newsVO.news_id}.jpg" />
						</a>
					</div>

				</div>
			</c:forEach>
			<div class="col-lg-12">
				<a href="<%=request.getContextPath()%>/front-end/news/NewsList.jsp"
					style="font-weight: bold;">閱讀更多..</a>
				<hr>
			</div>
		</div>
	</div>




	<%@ include file="js.file"%>
	<%@ include file="sweet.file"%>

</body>
</html>