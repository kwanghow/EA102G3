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

body {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.4),
		rgb(255 255 255/ 0.4)),
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

.contentnews {
	font-size: 18px;
	line-height: 25px;
	color: #0e0e0e;
	width: 300px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.newspadding {
	padding-top: 40px;
	font-size: 22px;
	font-weight: bold;
}

.newsHeader {
	color: #ed563b;
	font-weight: bold;
	font-size: 25px;
}

.main-content {
	min-height: 852px;
	padding-top: 50px;
	padding-bottom: 50px;
}

footer {
	text-align: center;
	padding: 32px 0px;
	background: #343a40;
}

footer p {
	color: white;
}
</style>

</head>


<body onload="connect();" onunload="disconnect();">
	<%@ include file="header.file"%>

	<div class="container main-content" style="padding-top: 90px;">
		<div class="row">
			<div class="col-4" style="padding-top: 0px;">
				<a href="<%=request.getContextPath()%>/front-end/news/News.jsp"><button
						type="button" class="btn btn-dark">回到最新消息</button></a>
			</div>
			<div class="col-4">
				<select id="newsspec">
					<c:forEach var="news_specVO" items="${listA}">
						<option value="${news_specVO.news_spec}">${news_specVO.news_specheader}
					</c:forEach>
				</select>
			</div>
			<div class="col-4" style="color: #23272b;">
				<b>關鍵字搜尋</b><input type="text" id="FindOneAJAX"></input>
				<button id="send">送出</button>
			</div>
		</div>

		<div class="row newsHeader">
			<div class="col-4">最新消息分類</div>
			<div class="col-4">最新消息標題</div>
			<div class="col-4">最新消息內容</div>
		</div>
		<div id="specAJAX">
			<c:forEach var="newsVO" items="${list}">
				<div class="row newspadding">
					<c:forEach var="news_specVO" items="${listA}">
						<c:if test="${newsVO.news_spec==news_specVO.news_spec}">
							<div class="col-4">${news_specVO.news_specheader}</div>
						</c:if>
					</c:forEach>
					<div class="col-4">${newsVO.news_header}</div>
					<div class="col-4">
						<a
							href="<%=request.getContextPath()%>/front-end/news/NewsContent.jsp?news_id=${newsVO.news_id}"><p
								class="contentnews">${newsVO.news_content}</p></a>
					</div>
				</div>
			</c:forEach>
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
	<script>
		$('#newsspec').change(function() {
			data = {};
			data.news_spec=$("#newsspec").find(":selected").val();
			data.action="SpecPlayAJAX";
			$.ajax({
			 type: "POST",
				 url: "<%=request.getContextPath()%>/front-end/kevin/news/news.do",
			 data: data,
			 dataType: "json",
			 success: function (data){
				 $('#specAJAX').html('');
				 for(var i =0;i<data.spec.length;i++){
				 $('#specAJAX').append(`<div class="row newspadding">
								<div class="col-4">`+data.specheader[i]+`</div>
								<div class="col-4">`+data.spec[i].news_header+`</div>
								<div class="col-4"><a href="<%=request.getContextPath()%>/front-end/news/NewsContent.jsp?news_id=`+data.spec[i].news_id+`">
								<p class="contentnews">`+data.spec[i].news_content+`</p></a>
								</div>`);
				 }
		     },
           error: function(){alert("AJAX-class發生錯誤囉!")}
       })
		});
		
		
		
		$('#send').click(function() {
			data = {};
			data.find=$('#FindOneAJAX').val();
			data.action="FindOneAJAX";
			$.ajax({
			 type: "POST",
				 url: "<%=request.getContextPath()%>/front-end/kevin/news/news.do",
			 data: data,
			 dataType: "json",
			 success: function (data){
				 $('#specAJAX').html('');
				 for(var i =0;i<data.finded.length;i++){
				 $('#specAJAX').append(`<div class="row newspadding">
								<div class="col-4">`+data.specheader[i]+`</div>
								<div class="col-4">`+data.finded[i].news_header+`</div>
								<div class="col-4"><a href="<%=request.getContextPath()%>/front-end/news/NewsContent.jsp?news_id=`+data.finded[i].news_id+`">
								<p class="contentnews">`+data.finded[i].news_content+`</p></a>
								</div>`);
				 }
		     },
           error: function(){alert("AJAX-class發生錯誤囉!")}
       })
		});
	</script>




</body>
</html>