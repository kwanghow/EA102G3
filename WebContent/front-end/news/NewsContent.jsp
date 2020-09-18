<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	String news_id = request.getParameter("news_id");
	NewsVO newsVO = newsSvc.getOneNews(news_id);
	pageContext.setAttribute("newsVO", newsVO);

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
<style>
* {
	font-family: 微軟正黑體;
}

.spec {
	padding-top: 15px;
}

body {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.6),
		rgb(255 255 255/ 0.6)),
		url('<%=request.getContextPath()%>/front-end/kevin/css/images/108.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: repeat;
	background-position: top center;
	height: auto !important;
	min-height: 650px;
}

html, body {
	padding: 0;
	margin: 0;
}

.main-content {
	min-height: 100%;
	padding-top: 50px;
	padding-bottom: 50px;
}

footer {
	text-align: center;
	padding: 30px 0px;
	background: #343a40;
}

footer p {
	color: white;
}
.col-12.newscontent {
    font-weight: bold;
}
</style>

</head>


<body onload="connect();" onunload="disconnect();">
	<%@ include file="header.file"%>

	<div class="container main-content">
		<div class="row">
			<div class="col-12" style="padding-top: 40px;">
				<a href="<%=request.getContextPath()%>/front-end/news/News.jsp"><button
						type="button" class="btn btn-dark">回到最新消息</button></a>
			</div>
			<div class="col-12 spec"
				style="text-align: center; font-size: 45px; font-weight: 600; color: #ed563b;">
				<img style="max-width: 100%;"
					src="<%=request.getContextPath()%>/front-end/news/images/${newsVO.news_id}.jpg" /></a>
				<c:forEach var="news_specVO" items="${listA}">
					<c:if test="${newsVO.news_spec==news_specVO.news_spec}">
						${news_specVO.news_specheader}
					</c:if>
				</c:forEach>
			</div>
			<div class="col-12 newsheader"
				style="text-align: center; font-size: 36px;">${newsVO.news_header}</div>
			<div class="col-12 newscontent"
				style="text-align: center; font-size: 26px; line-height: 2; font-family: 微軟正黑體; word-spacing: 2px; word-break: keep-all; padding-bottom: 5px; min-height: 547px;"><pre>${newsVO.news_content}</pre></div>
		</div>
	</div>

	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<p>
						Copyright &copy; 2020 Training Studio - Modify by <a
							rel="nofollow" href="https://reurl.cc/yZ22W2"
							class="tm-text-link" target="_parent">FatBoy</a>
					</p>

				</div>
			</div>
		</div>
	</footer>




	<%@ include file="js.file"%>
	<%@ include file="sweet.file"%>



</body>
</html>