<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="com.news.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.stream.model.*"%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />
<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
	if(coachLogIn!=null){
	String coach_id=coachLogIn.getCoach_Id();
	pageContext.setAttribute("coach_id", coach_id);
	}
	StreamService streamSvc = new StreamService();
	List<StreamVO> streamlist = streamSvc.getAll();
	List<StreamVO> livestream = new ArrayList<StreamVO>();
	livestream = streamlist.stream().filter(streamalive -> streamalive.getStream_status() == 1)
			.collect(Collectors.toList());
	pageContext.setAttribute("livestream", livestream);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>直播首頁</title>




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
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.5),
		rgb(255 255 255/ 0.5)),
		url('<%=request.getContextPath()%>/front-end/kevin/images/bg1.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: no-repeat;
	background-position: top center;
	height: auto !important;
	min-height: 863px;
}

html, body, container {
	height: 100%;
}

footer {
	text-align: center;
	padding: 30px 0px;
	background: #343a40;
	width: 100%;
}

footer p {
	color: white;
}
</style>
</head>
<body>
	<%@ include file="headerh.file"%>


	<div class="container main-content"
		style="padding-top: 90px; min-height: 852px;">
		<div class="col col-sm-12" align="center"
			style="font-size: 40px; padding-bottom: 80px;"></div>
		<!-- &&streamVO.stream_vod!=null -->


		<%
			if (coachLogIn != null) {
		%>
		<div class="col col-sm-12" align="center"
			style="font-size: 40px; padding-bottom: 80px;">
			<input type="text" name="stream_header" id="stream_header"/>
			<input type="hidden" id="coach_id" value="${coach_id}"/>
			<button type="button" class="btn-dark" id="sendVO">開啟新直播</button>
			<input type="hidden" name="action" value="insertAJAX" />
		</div>
		<%
			}
		%>
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
<script>
    $('#sendVO').click(function(){
//		測試用
    let coach_id='${coach_id}';
    let stream_header=$('#stream_header').val();
    let newBroadcast= {"action":"insertAJAX", "coach_id":coach_id,"stream_header":stream_header};
	 $.ajax({
		 type: "POST",
		 url: "<%=request.getContextPath()%>/front-end/kevin/stream/stream.do",
		 data: newBroadcast,
		 dataType: "json",
		 success: function (data){
			 window.location.href='<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp';	
	     },
        error: function(){
       	 
        }
    });

    });
</script>



</html>