<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.stream.model.*"%>
<%@ page
	import="com.employee.model.* , com.authority.model.* ,java.util.* ,com.features.model.*"%>

<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
	StreamService streamSvc = new StreamService();
	List<StreamVO> list = streamSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>我就健</title>


	<%@ include file="js.file"%>



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

footer.home p {
	color: white;
}

footer.home {
    color: black;
	background: currentColor;
	text-align: center;
	padding: 30px 0px; <%--
	position: fixed; --%>
	width: 100%;
	bottom: 0;
	z-index: 1;
}

/* 圖片牆 */
div[class^="col"] {
	padding-left: 1px;
	padding-right: 1px;
	margin-bottom: 1px;
}

.row {
	margin-bottom: 1px;
}

.row>div>div {
	border: solid 1px #333;
}

.row:nth-of-type(1)>div[class^="col"]>div {
	height: 760px;
}

.row:nth-of-type(1)>div[class^="col"]>div.half {
	height: 380px;
}

.row:nth-of-type(1)>div[class^="col"]>div.half:nth-of-type(1) {
	margin-bottom: 1px;
}

.row:nth-of-type(2)>div[class^="col"]>div {
	height: 600px;
}

.row:nth-of-type(3)>div[class^="col"]>div {
	height: 600px;
}

/* 文章區 */
.nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active {
	color: #495057;
	background-color: rgba(145, 197, 192, 038);
	border-color: #dee2e6 #dee2e6 #fff;
}

a.nav-link {
	color: rgb(99 32 57/ 83%);
}

.active>.row {
	margin-bottom: 18px;
}

.active>.row>.col-12 {
	margin-bottom: 5px;
}

.active>.row>:nth-child(1) {
	font-size: 40px;
}

.active>.row>:nth-child(2) {
	font-size: 30px;
}

.active>.row>:nth-child(3) {
	font-size: 20px;
}

body {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.6),
		rgb(255 255 255/ 0.8)),
		url('<%=request.getContextPath()%>/front-end/kevin/images/bg1.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: top center;
}

.aritles .tab-pane>.row, .coaches>.container>.row {
	margin: 0;
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
	margin-top: 7%;
}

</style>
</head>
<body onload="connect();" onunload="disconnect();">

	<%@ include file="headerh.file"%>


	<!-- ***** Main Banner Area Start ***** -->
	<div class="main-banner" id="top">
		<video autoplay muted loop id="bg-video">
			<source src="<%=request.getContextPath()%>/front-end/kevin/images/GYM.mp4" type="video/mp4" />
		</video>

		<div class="video-overlay header-text">
			<div class="caption">
				<h6>work harder, get stronger</h6>
				<h2>
					easy with our <em>gym</em>
				</h2>
				<div class="main-button scroll-to-section">
					<%
						if (memLogIn == null) {
					%>
					<a href="<%=request.getContextPath()%>/front-end/becomeMember.jsp">Become
						a member</a>
					<%
						} else if(coachLogIn ==null){
					%><a href="<%=request.getContextPath()%>/front-end/becomeCoach.jsp">Become
						a coach</a>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<!-- ***** Main Banner Area End ***** -->





 



	<%-- 	<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" /> --%>
	<%-- 			<c:forEach var="coachVO" items="${coachSvc.all}"> --%>
	<%-- 			   <a href="coachInfo.jsp?coach_Id=${coachVO.coach_Id}"> --%>
	<%-- 					<img class="coach_pic" src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${coachVO.coach_Id}&type=coachPic"> --%>
	<!-- 			   </a>		 -->
	<%-- 			</c:forEach> --%>
	<%-- <a href="<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C01">123</a> --%>
	<section class="coaches" style="">
		<div class="container">
			<div class="row">
				<div class="col-12"
					style="font-size: 50px; text-align: center; color: #0e0e0e;">熱門教練</div>
				<div class="col-md-4">
					<div class="half"
						style="background: url('<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=C01&type=coachPic') center center; background-size: cover;"
						onclick="location.href=
'<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C01';"></div>
					<div class="half"
						style="background: url('<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=C02&type=coachPic') center center; background-size: cover;"
						onclick="location.href=
'<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C02';"></div>
				</div>
				<div class="col-md-8"
					style="background: url('<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=C03&type=coachPic') center center; background-size: cover;"
					onclick="location.href=
'<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C03';"></div>
			</div>

			<div class="row">
				<div class="col-md-6"
					style="background: url('<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=C04&type=coachPic') center center; background-size: cover;"
					onclick="location.href=
'<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C04';">
					<div></div>
				</div>
				<div class="col-md-6"
					style="background: url('<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=C05&type=coachPic') center center; background-size: cover;"
					onclick="location.href=
'<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=C05';">
					<div></div>
				</div>
			</div>
		</div>
	</section>
	
	
	
	<%@ include file="list-hot-product.jsp"%>


<!-- 	<section class="aritles" style=""> -->
<!-- 		<div class="container" style="height: 100%"> -->
<!-- 			<ul class="nav nav-tabs bar-tabs"> -->
<!-- 				<li class="nav-item"><a class="nav-link active" -->
<!-- 					href="#hotaritle" data-toggle="tab">熱門文章</a></li> -->
<!-- 				<li class="nav-item"><a class="nav-link" href="#newaritle" -->
<!-- 					data-toggle="tab">最新文章</a></li> -->
<!-- 				<li class="nav-item"><a class="nav-link" href="#stream" -->
<!-- 					data-toggle="tab">直播紀錄</a></li> -->
<!-- 			</ul> -->
<!-- 			<div class="tab-content"> -->
<!-- 				<div id="hotaritle" class="tab-pane fade show active"> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-12">文章標題</div> -->
<!-- 						<div class="col-12">文章副標題</div> -->
<!-- 						<div class="col-12">文章內容</div> -->
<!-- 					</div> -->

<!-- 				</div> -->

<!-- 				<div id="newaritle" class="tab-pane fade"> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-12">文章標題</div> -->
<!-- 						<div class="col-12">文章副標題</div> -->
<!-- 						<div class="col-12">文章內容</div> -->
<!-- 					</div> -->
<!-- 				</div> -->

<!-- 				<div id="stream" class="tab-pane fade"> -->
<%-- 					<c:forEach var="streamVO" items="${list}"> --%>
<!-- 						<div class="row"> -->
<%-- 														<div class="col-12">${coachLogIn.getMember_Id(streamVO.coach_id)}</div> --%>
<%-- 							<c:if test="${streamVO.stream_vod!=null}"> --%>
<%-- 								<div class="col-12" style="text-align: center;">${streamVO.stream_header}</div> --%>
<!-- 								<div class="col-12"> -->
<%-- 									<video width="480" height="320" id="${streamVO.stream_id}" --%>
<%-- 										src="<%=request.getContextPath()%>/BlobReader?stream_id=${streamVO.stream_id}" --%>
<!-- 										controls="controls" muted -->
<!-- 										style="display: block; margin: 0 auto;"> -->
<!-- 									</video> -->
<!-- 								</div> -->
<%-- 							</c:if> --%>
<!-- 						</div> -->
<%-- 					</c:forEach> --%>
<!-- 				</div> -->
<!-- 			</div> -->



<!-- 		</div> -->

<!-- 	</section> -->





<!-- 	<section class="course"> -->
<!-- 		<div class="container"> -->
<!-- 			<table class="table table-hover table-dark"> -->
<!-- 				<thead> -->
<!-- 					<tr> -->
<!-- 						<th scope="col">教練名字</th> -->
<!-- 						<th scope="col">課程名稱</th> -->
<!-- 						<th scope="col">最少上課人數</th> -->
<!-- 						<th scope="col">目前報名人數</th> -->
<!-- 					</tr> -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
<!-- 					<tr> -->
<!-- 						<th scope="row">劉燁瑜</th> -->
<!-- 						<td>健身</td> -->
<!-- 						<td>10</td> -->
<!-- 						<td>5</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th scope="row">李瑜隹</th> -->
<!-- 						<td>有氧</td> -->
<!-- 						<td>15</td> -->
<!-- 						<td>8</td> -->
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<th scope="row">黃若邦</th> -->
<!-- 						<td>重訓</td> -->
<!-- 						<td>17</td> -->
<!-- 						<td>16</td> -->
<!-- 					</tr> -->
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</div> -->
<!-- 	</section> -->


	<input type="hidden" id="isOk" value="${isOk}">
	<input type="hidden" id="okVal" value="${okVal}">



	<!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
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





<%@ include file="sweet.file"%>
<%@ include file="socket.file"%>

	
<%
 	session.removeAttribute("isOk");
 	session.removeAttribute("okVal");
%> 

</body>
</html>

