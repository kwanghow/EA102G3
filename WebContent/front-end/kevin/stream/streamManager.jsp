<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.stream.model.*"%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />
<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
	StreamService streamSvc = new StreamService();
	List<StreamVO> streamlist = streamSvc.getAll();
	pageContext.setAttribute("streamlist", streamlist);
	CoachVO coachVO = new CoachVO();
	if (memLogIn != null) {
		coachVO = coachSvc.getOneByMem(memLogIn.getMember_Id());
		String coach_id = coachVO.getCoach_Id();
		session.setAttribute("coach_id", coach_id);
	}
%>




<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>直播管理</title>


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

.main-content {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.5),
		rgb(255 255 255/ 0.5)),
		url('<%=request.getContextPath()%>/front-end/kevin/images/bg1.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: repeat;
	background-position: top center;
	min-height: 960px;
}

html, body, container {
	height: 100%;
}

footer {
    text-align: center;
    padding: 30px 0px;
    background: #343a40;
    width: 100%;
    position: fixed;
    bottom: 0;
}

footer p {
	color: white;
}
</style>
</head>
<body>
	<%@ include file="headerh.file"%>


	<div class="main-content"
		style="padding-top: 90px;">
		<div class="col col-sm-12" align="center"
			style="font-size: 40px; padding-bottom: 80px;">
<!-- 			<form method="post" -->
<%-- 				action="<%=request.getContextPath()%>/front-end/kevin/stream/stream.do"> --%>
				<button type="button" class="btn-dark" onclick="location.href='<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp'">開啟新直播</button>
				<input type="hidden" name="action" value="insertAJAX" />
<!-- 			</form> -->
		</div>
		<!-- &&streamVO.stream_vod!=null -->
		<c:forEach var="streamVO" items="${streamlist}">
			<c:if test="${streamVO.coach_id==coach_id}">
				<div class="row">
					<form action="<%=request.getContextPath()%>/front-end/kevin/stream/stream.do" method="post">
						<button type="submit" name="action" class="btn-dark bdelete"
							id="${streamVO.stream_id}" value="delete">刪除此直播紀錄</button>
						<input type="hidden" name="stream_id"
							value="${streamVO.stream_id}" />
					</form>
					<div class="col col-sm-12" align="center"
						style="font-size: 40px; font-weight: bolder;">直播編號:${streamVO.stream_id}</div>
					<div class="col col-sm-12" align="center" style="font-size: 40px;">直播標題:${streamVO.stream_header}</div>
					<div class="col col-sm-12" align="center" style="font-size: 40px;">
						直播日期:
						<fmt:formatDate value="${streamVO.stream_notice}"
							pattern="yyyy-MM-dd" />
					</div>
					<div class="col col-sm-12" align="center"
						style="padding-bottom: 80px;">
						<video width="640" height="360" id="${streamVO.stream_id}"
							src="<%=request.getContextPath()%>/BlobReader?stream_id=${streamVO.stream_id}"
							controls="controls" muted>
						</video>
					</div>


				</div>
			</c:if>
		</c:forEach>
	</div>














	<footer class="home">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<p>
						Copyright &copy; 2020 Training Studio - Modify by <a
							rel="nofollow" href="https://reurl.cc/yZ22W2"
							class="tm-text-link" target="_parent">FatBoy</a>
					</p>
					<!-- You shall support us a little via PayPal to info@templatemo.com -->
				</div>
			</div>
		</div>
	</footer>
	<%@ include file="js2.file"%>
</body>


</html>