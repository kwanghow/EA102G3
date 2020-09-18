<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>trainingLog</title>
</head>
<style>
h3 {
	color: yellow;
	display: inline;
}

table {
	background-color: #003333;
	width: 30%;
	text-align: center;
	height: 80px;
}
</style>

<body>

	<table>
		<tr>
			<td><h3>trainingLog</h3></td>
		</tr>

	</table>

	<c:if test="${not empty errorMsgs}">
		<font color='red'>請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />

	<ul>
		<li>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/trainingLog/trainingLog.do">
				<b>顯示健身紀錄:</b> 
				
				<select size="1" name="memno">
					<c:forEach var="memVO" items="${memSvc.all}">
						<option value="${memVO.member_Id}">${memVO.member_Id}
					</c:forEach>
				</select> 
				<input type="submit" value="送出"> 
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="hidden" name="requestURL" value="<%=request.getServletPath()%>">
			</FORM>
		</li>
	</ul>
</html>