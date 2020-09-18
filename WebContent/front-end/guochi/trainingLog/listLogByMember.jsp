<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.trainingLog.model.*" %>

<jsp:useBean id="listLogByMemno" scope="session" type="java.util.Set<trainingLogVO>"/>
<jsp:useBean id="trainCat" scope="page" class="com.trainingCat.model.trainingCatService"/>
<%String memno = (String) session.getAttribute("memno"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>健身記錄清單</title>
<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
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
img{
	width:100px;
	height:80px;
}
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>健身紀錄清單</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/traningLog.jsp">回首頁</a> 
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
			<td>健身記錄編號</td>
			<td>會員編號</td>
			<td>健身項目編號</td>
			<td>體重</td>
			<td>照片記錄</td>
			<td>文字記錄</td>
			<td>健身日期</td>
			<td>修改</td>
			<td>刪除</td>
		</tr>
		
		<c:forEach var="trainingLogVO" items="${listLogByMemno}">
		<tr>
			<td>${trainingLogVO.trainingLog_no}</td>
			<td>${trainingLogVO.member_id}</td>
<!-- 			之後修改成訓練名稱名字 -->
<%-- 			<td>${trainingLogVO.trainingCat_no}</td> --%>
			<td>
				<c:forEach var="trainCatVO" items="${trainCat.all}">
					<c:if test="${trainingLogVO.trainingCat_no==trainCatVO.trainingCat_no}">
						${trainCatVO.trainingCat_name}
						</c:if>
				</c:forEach>
			</td>
			<td>${trainingLogVO.weight}</td>
<%-- 			<td>${trainingLogVO.photos}</td> --%>
			<td><img src="<%=request.getContextPath()%>/front-end/ReadPic?TRAININGLOG_NO=${trainingLogVO.trainingLog_no}"></td>

			<td>${trainingLogVO.training_item}</td>
			<td>${trainingLogVO.trainingLog_date}</td>
			
			<td>
				<FORM>
<%-- 					<a href="<%=request.getContextPath()%>/trainingLog/updateLog.jsp">修改</a> --%>

					<input type="submit" value="修改">
					<input type="hidden" name="action" value="get_One_For_Update">
					<input type="hidden" name="trainLogNo" value="${trainingLogVO.trainingLog_no}">		
					<input type="hidden" name="trainLogCat" value="${trainingLogVO.trainingCat_no}">
					<input type="hidden" name="weight" value="${trainingLogVO.weight}">
					<input type="hidden" name="photo" value="<%=request.getContextPath()%>/front-end/ReadPic?TRAININGLOG_NO=${trainingLogVO.trainingLog_no}">
					<input type="hidden" name="trainingItem" value="${trainingLogVO.training_item}">
				</FORM>
			</td>
			<td>
				<FORM ACTION="<%=request.getContextPath()%>/trainingLog/trainingLog.do" METHOD="POST">
					<input type="submit" value="刪除">
					<input type="hidden" name="action" value="delete">
					<input type="hidden" name="trainLogNo" value="${trainingLogVO.trainingLog_no}">
					<input type="hidden" name="memno" value="<%=memno%>">
					${trainingLogVO.trainingLog_no}
				</FORM>
			</td>
		
		</tr>
		</c:forEach>
		<tr>
			<td>
<!-- 				<input type="submit" value="新增紀錄"> -->
					<a href="<%=request.getContextPath()%>/trainingLog/addLog.jsp">新增資料</a>
			</td>
		</tr>
	</table>

</body>
</html>