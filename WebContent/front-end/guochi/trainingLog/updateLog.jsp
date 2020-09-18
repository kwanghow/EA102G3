<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.trainingLog.model.*"%>

<jsp:useBean id="listLogByMemno" scope="session"
	type="java.util.Set<trainingLogVO>" />

<%
	String memno = (String) session.getAttribute("memno");
%>

<%
	Integer weight = (Integer) request.getAttribute("weight");
%>
<%
	String trainLogNo = (String) request.getAttribute("trainLogNo");
%>
<%
	String trainLogCat = (String) request.getAttribute("trainLogCat");
%>
<%
	String photo = (String) request.getAttribute("photo");
%>
<%
	String trainingItem = (String) request.getAttribute("trainingItem");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>新增健身記錄</title>
<style>
input[type="submit"] {
	background: #fd7e14;
	border: none;
}

table#table-1 {
	background-color: #CCCCFF;
	border: 1px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
	border-collapse: collapse;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 1px;
}

img {
	max-width: 600px;
}

table#content {
	margin-left: 35%;
	margin-top: 130px;
	background-color: transparent;
	color: azure;
}

td.no-border {
	border-bottom-color: transparent;
}

a {
	color: #fd7e14;
}
</style>

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;display=swap"
	rel="stylesheet">
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

<!-- Modal -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<!-- Additional CSS Files -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/font-awesome.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/templatemo-training-studio.css">

</head>
<body>
	<header class="header-area header-sticky">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- logo -->
						<a href="<%=request.getContextPath()%>/front-end/kevin/index.jsp"
							class="logo">我就<em> 健</em></a>
						<!-- menu -->
						<ul class="nav">
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/news/News.jsp">最新消息</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/Jessica/course/explore.jsp">課程</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/listAllGpc.jsp">揪團</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/haoren/list-all-product.jsp">商城</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/article/articleList.jsp">健身文章</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/kevin/stream/streamIndex.jsp">直播區</a></li>
							<!-- 會員下拉 -->
							<li class="scroll-to-section dropdown"><a href="#"
								id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="ture">會員/教練專區</a>
								<ul class="dropdown-menu" role="menu"
									aria-labelledby="navbarDropdown">
									<li><a class="dropdown-item m1"
										href="<%=request.getContextPath()%>/front-end/memberPage.jsp">會員專區</a></li>
									<li><a class="dropdown-item c1"
										href="<%=request.getContextPath()%>/front-end/coachPage.jsp">教練專區</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp">飲食/健身紀錄</a></li>
									<li style="display: none;"><a class="dropdown-item"
										href="#">none</a></li>
								</ul></li>

							<!-- 會員登入訊息 -->
							<c:if test="${empty sessionScope.memLogIn}">
								<li class="main-button"><a
									href="<%=request.getContextPath()%>/front-end/mem.do?action=logIn">
										Log In </a></li>
							</c:if>
							<c:if test="${not empty sessionScope.memLogIn}">
								<li class="main-button"><a
									href="<%=request.getContextPath()%>/front-end/mem.do?action=logOut">
										${sessionScope.memLogIn.member_Id}${sessionScope.memLogIn.mem_Name}
								</a></li>
							</c:if>
						</ul>
						<a class='menu-trigger'><span>Menu</span></a>
					</nav>
				</div>
			</div>
		</div>
	</header>

	<div class="main-banner" id="top">
		<video autoplay="" muted="" loop="" id="bg-video">
			<source
				src="${pageContext.request.contextPath}/assets/images/gym-video.mp4"
				type="video/mp4">
		</video>

		<div class="video-overlay header-text">
			<div class="caption"></div>
			<div>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<jsp:useBean id="trainingCatSvc" scope="page"
					class="com.trainingCat.model.trainingCatService" />
				<FORM
					ACTION="<%=request.getContextPath()%>/trainingLog/trainingLog.do"
					METHOD=post enctype="multipart/form-data">
					<table id="content">
						<tr>
							<td>訓練項目: <select name="trainingCat" size=1>
									<c:forEach var="trainingCatVO" items="${trainingCatSvc.all}">
										<c:choose>
											<c:when test="${trainingCatVO.trainingCat_no==trainLogCat}">
												<option value="${trainingCatVO.trainingCat_no}"
													selected="selected">${trainingCatVO.trainingCat_name}
											</c:when>
											<c:otherwise>
												<option value="${trainingCatVO.trainingCat_no}">${trainingCatVO.trainingCat_name}
											</c:otherwise>
										</c:choose>

									</c:forEach>
							</select>
							</td>
						</tr>

						<tr>
							<td>體重: <input type="text" name="weight" size="5"
								value="<%=weight%>"></td>

						</tr>

						<tr>
							<td class="no-border">訓練紀錄:</td>
						</tr>
						<tr>
							<td><textarea name="training_item" cols="50" rows="5"><%=trainingItem%></textarea>
							</td>
						</tr>

						<tr>
							<td>照片紀錄: <img id="mem_pic_preview" src="<%=photo%>">
								<input id="pic" type="file" id="pic" name="upfile1"
								class="btn btn-primary"> <br> <img id="blah"
								src="#" alt="預覽圖片" />
							</td>
						</tr>


						<tr>
							<td><input type="submit" value="修改" class="btn btn-primary">
								<input type="hidden" name="action" value="update"> <input
								type="hidden" name="memno" value="<%=memno%>"> <input
								type="hidden" name="trainLogNo" value=<%=trainLogNo%>> <input
								type="button" value="刪除這筆紀錄" class="btn btn-primary"
								onclick="location.href='<%=request.getContextPath()%>/trainingLog/trainingLog.do?action=delete&trainLogNo=<%=trainLogNo%>&memno=<%=memno%>'"></td>
						</tr>
						<tr>
							<td>
								<h4>
									<input type="button" value="回飲食/健身紀錄" class="btn btn-primary"
										onclick="location.href='<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp'">

								</h4>
							</td>
						</tr>
					</table>

				</FORM>


			</div>
		</div>
	</div>

	<script>
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#blah').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#pic").change(function() {
			readURL(this);
		});
	</script>

</body>
</html>