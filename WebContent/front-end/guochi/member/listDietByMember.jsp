<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.diet.model.*"%>
<%@ page import="com.dietCon.model.*"%>
<%-- <%@ page import ="java.sql.Timestamp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="listDiet_ByMemberID" scope="session"
	type="java.util.Set<dietVO>" />

<%
	String memno = (String) request.getAttribute("memno");
%>
<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>
<%-- <%dietVO dietVO = (dietVO) request.getAttribute("dietVO");%> --%>

<%
	DietService dietSvc = new DietService();

	List<dietVO> list = dietSvc.getAll();
%>
<!DOCTYPE html>
<html>
<!-- <head> -->
<!-- <meta charset="BIG5"> -->
<!-- <title>會員資料 - DIET</title> -->

<style>

h5, label {
	color: black;
}

input[type="submit"] {
	background: #fd7e14;
	border: none;
}

html, body {
	background: #232d39;
}

table#table-1 {
	background-color: #fd7e14;
	border: 2px solid black;
	text-align: center;
	margin-top: 10%;
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
	width: 800px;
	background-color: transparent;
	margin-top: 5px;
	margin-bottom: 5px;
	margin-left: 20%;
}

table, th, td {
	border: 1px solid #CCCCFF;
	color: white;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>
<!-- </head> -->
<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;display=swap"
	rel="stylesheet">

<title>會員的所有飲食紀錄</title>

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
<!-- Modal -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">
<!-- Additional CSS Files -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/assets/css/font-awesome.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/templatemo-training-studio.css">

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

	<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <!-- logo -->
                    <a href="<%=request.getContextPath()%>/front-end/kevin/index.jsp" class="logo">我就<em> 健</em></a>
                    <!-- menu -->
                    <ul class="nav">
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/news/News.jsp">最新消息</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/Jessica/course/explore.jsp">課程</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/listAllGpc.jsp">揪團</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/haoren/list-all-product.jsp">商城</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/article/articleList.jsp">健身文章</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/kevin/stream/streamIndex.jsp">直播區</a></li>                        
                        <!-- 會員下拉 -->                        
                        <li class="scroll-to-section dropdown">
                            <a href="#" id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="ture">會員/教練專區</a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="navbarDropdown">
                            	<li><a class="dropdown-item m1" href="<%=request.getContextPath()%>/front-end/memberPage.jsp">會員專區</a></li>
                                <li><a class="dropdown-item c1" href="<%=request.getContextPath()%>/front-end/coachPage.jsp">教練專區</a></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp">飲食/健身紀錄</a></li>
                                <li style="display: none;"><a class="dropdown-item" href="#">none</a></li>
                            </ul>
                        </li>        

                        <!-- 會員登入訊息 -->
                        <c:if test="${empty sessionScope.memLogIn}">
                        	<li class="main-button">
                        		<a href="<%=request.getContextPath()%>/front-end/mem.do?action=logIn">
                        			Log In
                        		</a>
                        	</li>
                        </c:if>
                        <c:if test="${not empty sessionScope.memLogIn}">
                        	<li class="main-button">
                        		<a href="<%=request.getContextPath()%>/front-end/mem.do?action=logOut">
                        			${sessionScope.memLogIn.member_Id}${sessionScope.memLogIn.mem_Name}
                        		</a>
                        	</li>
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
				<table id="table-1">
					<tr>
						<td>
							<h3>飲食紀錄資料</h3>
							<h4>
								<a href="<%=request.getContextPath()%>/select.jsp">回首頁</a>
							</h4>
						</td>
					</tr>
				</table>

				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<table>
					<tr>
						<td>飲食紀錄編號</td>
						<td>會員編號</td>
						<td>紀錄時間</td>
						<td>查看內容</td>
						<td>刪除紀錄</td>


					</tr>
					<c:forEach var="dietVO" items="${listDiet_ByMemberID}">


						<tr>
							<td>${dietVO.dietno}</td>
							<td>${dietVO.member_id}</td>
							<td>${dietVO.diet_date}</td>

							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/dietCon/dietCon.do"
									style="margin-bottom: 0px;">
									<input type="submit" value="查看飲食明細"class="btn btn-primary"> <input
										type="hidden" name="dietno" value="${dietVO.dietno}">${dietVO.dietno}
									<%-- 							<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>"> --%>
									<!--送出本網頁的路徑給Controller-->
									<!-- 目前尚未用到  -->
									<input type="hidden" name="action" value="showDietContent">
									<input type="hidden" name="memno" value="<%=memno%>">
								</FORM>
							</td>

							<td>
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/aaa/diet.do"
									style="margin-bottom: 0px;">
									<input type="submit" value="刪除"class="btn btn-primary"> <input type="hidden"
										name="requestURL" value="<%=request.getServletPath()%>">
									<!--送出本網頁的路徑給Controller-->
									<input type="hidden" name="dietno" value="${dietVO.dietno}">
									<input type="hidden" name="memno" value="${dietVO.member_id}">${dietVO.member_id}
									<input type="hidden" name="action" value="delete">
								</FORM>
							</td>

						</tr>

					</c:forEach>

				</table>

				<table>
<!-- 					<tr> -->
<!-- 						<td> -->
<!-- 							<FORM METHOD="post" -->
<%-- 								ACTION="<%=request.getContextPath()%>/aaa/diet.do" --%>
<!-- 								style="margin-bottom: 0px;"> -->

<!-- 								<input type="submit" value="新增飲食紀錄"class="btn btn-primary"> <input -->
<!-- 									type="hidden" name="requestURL" -->
<%-- 									value="<%=request.getServletPath()%>"> <input --%>
<!-- 									type="hidden" name="action" value="addDiet"> <input -->
<%-- 									type="hidden" name="memno" value="<%=memno%>"> --%>

<!-- 							</FORM> -->


<!-- 						</td> -->
<!-- 					</tr> -->

					<tr>
						<td>
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#exampleModal" data-whatever="@getbootstrap"style="background-color:#fd7e14;">Modal新增</button>

							<div class="modal fade" id="exampleModal" tabindex="-1"
								role="dialog" aria-labelledby="exampleModalLabel"
								aria-hidden="true">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="exampleModalLabel">新增飲食紀錄</h5>
											<button type="button" class="close" aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<FORM METHOD="post"	ACTION="<%=request.getContextPath()%>/aaa/diet.do"
												style="margin-bottom: 0px;">

												<div>
													<label for="recipient-name" class="col-form-label">吃了什麼:</label>
												</div>
												
												<div>
													<jsp:useBean id="dietIngSvc" scope="page"
														class="com.dietIng.model.dietIngService" />
													<select size="1" name="dietIng">
														<c:forEach var="dietIngVO" items="${dietIngSvc.all}">
															<option value="${dietIngVO.dietIng_no}">${dietIngVO.dietIng_name}
														</c:forEach>
													</select>
												</div>
												
												<div class="form-group">
													<label for="message-text" class="col-form-label">吃的內容:</label>
													<textarea class="form-control" id="message-text"
														name="diet_content"></textarea>
												</div>
												
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-dismiss="modal">關閉</button>
													<button type="submit" class="btn btn-primary">新增</button>
													<!-- <input type="submit" value="送出">  -->
													<input type="hidden" name="action" value="insert">
													<input type="hidden" name="memno" value="<%=memno%>"><%=memno%>
													<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
													
												</div>
												
											</FORM>
										</div>
									</div>
								</div>
							</div>
							
						</td>
					</tr>

				</table>




			</div>
		</div>

	</div>


</body>

</html>