<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.trainingLog.model.*"%>

<%
	String memno = (String) session.getAttribute("memno");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>新增健身記錄</title>
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
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>
</head>
<body>
	<h4>
		<a href="<%=request.getContextPath()%>/traningLog.jsp">回首頁</a>
	</h4>
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
	<FORM ACTION="<%=request.getContextPath()%>/trainingLog/trainingLog.do" METHOD=post enctype="multipart/form-data">
		<table>
			<tr>
				<td>訓練項目: <select name="trainingCat" size=1>
						<c:forEach var="trainingCatVO" items="${trainingCatSvc.all}">
							<option value="${trainingCatVO.trainingCat_no}">${trainingCatVO.trainingCat_name}
						</c:forEach>
				</select>
				</td>
			</tr>

			<tr>
				<td>體重: <input type="text" name="weight" size="5"></td>

			</tr>

			<tr>
				<td>訓練紀錄: <textarea name="training_item" cols="20" rows="5"></textarea>
				</td>
			</tr>

			<tr>
				<td>照片紀錄: <input id="pic" type="file" name="upfile1"></td>
			</tr>



		</table>

		<input type="submit" value="新增"><%=memno%>
		<input type="hidden" name="action" value="insert">
		<input type="hidden" name="memno" value="<%=memno%>">
	</FORM>

</body>
</html>