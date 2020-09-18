<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.diet.model.*"%>
<%@ page import="com.dietCon.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="listAlldietContent" scope="session"
	type="java.util.Set<dietConVO>" />
<%-- <jsp:useBean id="dietConSvc" scope="page" class="com.dietCon.model.dietConService"/> --%>
<jsp:useBean id="dietIngSvc" scope="page"
	class="com.dietIng.model.dietIngService" />
<%-- <%dietConVO dietConVO = (dietConVO) request.getAttribute("listAlldietContent");%> --%>

<%
	String dietno = (String) request.getAttribute("dietno");
%>
<%
	String memno = (String) request.getAttribute("memno");
%>



<html>
<head>
<meta charset="BIG5">
<title>DietContent</title>

<style>
h5, label {
	color: black;
}

input[type="submit"] {
	background: #fd7e14;
	border: none;
	margin-top: 15px;
}

input[type="button"] {
	background: #fd7e14;
	border: none;
}

html, body {
	background: #232d39;
}

table#table-1 {
	background-color: #fd7e14;
	border: 1px solid #fd7e14;
	text-align: center;
	margin-top: 10%;
}

table#buttons {
	border-style: hidden;
}

tr#bbuuttoonn {
	float: left;
	border-style: hidden;
}

td#buttons {
	border-style: hidden;
	padding-left: 15px;
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
	padding: 2px;
	text-align: center;
}
</style>
<!-- 					JS 								-->
<script src="${pageContext.request.contextPath}/front-end/guochi/assets/js/jquery.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/moment.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;display=swap"
	rel="stylesheet">

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

	<!-- 	Modal ----updateDiet		-->
	<div class="modal fade" id="updateDiet" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">修改明細</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/dietCon/dietCon.do"
						style="margin-bottom: 0px;">

						<div>
							<label for="recipient-name" class="col-form-label">吃了什麼:</label>
						</div>

						<div>

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
							<button type="submit" class="btn btn-primary" id="submitButton">修改</button>
							<!-- <input type="submit" value="送出">  -->
							<input type="hidden" name="action" value="update"> <input
								type="hidden" name="memno" value="<%=memno%>"><%=memno%>
							<input type="hidden" name="requestURL"
								value="<%=request.getServletPath()%>"> <input
								type="hidden" name="dietno" value="<%=dietno%>"> 
								<input
								type="hidden" name="dietcon_no" id="dietcon_no" value="">

						</div>

					</FORM>
				</div>

			</div>
		</div>
	</div>

	<!-- 	Modal ----addDiet		-->

	<div class="modal fade" id="addDiet" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">新增一筆明細</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/aaa/diet.do"
						style="margin-bottom: 0px;">

						<div>
							<label for="recipient-name" class="col-form-label">吃了什麼:</label>
						</div>

						<div>

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
							<button type="submit" class="btn btn-primary" id="submitButton">新增</button>
							<!-- <input type="submit" value="送出">  -->
							<input type="hidden" name="action" value="insert"> <input
								type="hidden" name="memno" value="<%=memno%>">
							<input type="hidden" name="requestURL"
								value="<%=request.getServletPath()%>"> <input
								type="hidden" name="dietno" value="<%=dietno%>">

						</div>

					</FORM>
				</div>

			</div>
		</div>
	</div>

	<!----- Header Area ----->
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
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp">直播區</a></li>                        
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
							<h3>飲食紀錄清單</h3>
							
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
						<td>飲食內容編號</td>
						<td>食物</td>
						<td>卡路里</td>
						<td>描述吃了什麼</td>
						<td>刪除</td>
						<td>修改</td>
					</tr>


					<c:forEach var="dietConVO" items="${listAlldietContent}">
						<tr>
							<td>${dietConVO.dietcon_no}</td>
<%-- 							<td>${dietConVO.dietno}</td> --%>
<%-- 							<td>${dietConVO.dietIng_no}</td> --%>
							<td><c:forEach var="dietIngVO" items="${dietIngSvc.all}">
									<c:if test="${dietConVO.dietIng_no==dietIngVO.dietIng_no}">
							${dietIngVO.dietIng_name}
							
						</c:if>
								</c:forEach></td>
								<td><c:forEach var="dietIngVO" items="${dietIngSvc.all}">
									<c:if test="${dietConVO.dietIng_no==dietIngVO.dietIng_no}">
							
							${dietIngVO.cal}
						</c:if>
								</c:forEach></td>
							<td>${dietConVO.diet_content}</td>

							<td>
								<form METHOD="post"
									action="<%=request.getContextPath()%>/dietCon/dietCon.do">
									<input type="submit" value="刪除" class="btn btn-primary">
									<input type="hidden" name="action" value="delete"> <input
										type="hidden" name="dietcon_no"
										value="${dietConVO.dietcon_no}"> <input type="hidden"
										name="diet_no" value="${dietConVO.diet_no}"> <input
										type="hidden" name="memno" value="<%=memno%>"> <input
										type="hidden" name="requestURL"
										value=<%=request.getServletPath()%>>
								</form>
							</td>

							<td>
								<!-- 								<FORM METHOD="post" --> <%-- 									ACTION="<%=request.getContextPath()%>/dietCon/dietCon.do"> --%>
								<input type="button" value="修改" class="btn btn-primary"
								data-toggle="modal" data-target="#updateDiet" data-update="${dietConVO.dietcon_no}">
								<!-- 									<input type="hidden" name="action" value="updateDiet"> -->
								<!-- 									<input type="hidden" name=dietcon_no --> <%-- 										value="${dietConVO.dietcon_no}"> <input type="hidden" --%>
								<%-- 										name="memno" value="<%=memno%>"> <input type="hidden" --%>
								<%-- 										name="diet_content" value="${dietConVO.diet_content}"> --%>
								<%-- 									<input type="hidden" name="dietno" value="<%=dietno%>"> --%>
								<!-- 								</FORM> -->
							</td>
						</tr>

					</c:forEach>
					<%-- 		<jsp:getProperty name="listAlldietContent" property="dietConVO"/> --%>

				</table>
				<table id="buttons">
					<tr id="bbuuttoonn">
						<td id="buttons">
							<form METHOD="post"
								action="<%=request.getContextPath()%>/aaa/diet.do">
								<input type="submit" value="刪除整筆飲食紀錄" class="btn btn-primary">
								<input type="hidden" name="action" value="delete"> <input
									type="hidden" name="dietno" value="<%=dietno%>"> <input
									type="hidden" name="memno" value="<%=memno%>">
							</form>
						</td>
						<td id="buttons"><input type="button" class="btn btn-primary"
							data-toggle="modal" data-target="#addDiet" value="新增一筆明細">
							<!-- 							<FORM METHOD="post" --> <%-- 								ACTION="<%=request.getContextPath()%>/aaa/diet.do"> --%>
							<!-- 								<input type="submit" value="新增一筆明細" class="btn btn-primary"> -->
							<!-- 								<input type="hidden" name="requestURL" --> <%-- 									value="<%=request.getServletPath()%>"> <input --%>
							<%-- 									type="hidden" name="dietno" value="<%=dietno%>"> <input --%>
							<!-- 									type="hidden" name="action" value="addDiet"> <input -->
							<%-- 									type="hidden" name="memno" value="<%=memno%>"> --%>
							<!-- 							</FORM> --></td>
						<td id="buttons">
							<input type="button" value="回飲食/健身紀錄" class="btn btn-primary" onclick="location.href='<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp'">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>

	<script>
	$('#updateDiet').on('show.bs.modal', function (event) {
		  var button = $(event.relatedTarget); // Button that triggered the modal
		  var update = button.data('update'); // Extract info from data-* attributes
		  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
		  
		  var modal = $(this);
		  
		  $('#dietcon_no').val(update);
		  
// 		  modal.find('.modal-body input').val(update);
		
	})
	</script>



</body>
</html>