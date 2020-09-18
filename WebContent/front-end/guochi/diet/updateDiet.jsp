<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.diet.model.*"%>
<%@ page import="com.dietCon.model.*"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String memno = (String) request.getAttribute("memno");
%>

<%
	String dietno = (String) request.getAttribute("dietno");
%>

<%
	String dietcon_no = (String) request.getAttribute("dietcon_no");
%>

<%  String diet_content = (String) request.getAttribute("diet_content"); %>


<html>
<head>
<meta charset="BIG5">
<title>修改飲食清單</title>

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
		<a href="<%=request.getContextPath()%>/select.jsp">回首頁</a>
	</h4>

	<h3>資料修改</h3>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/dietCon/dietCon.do">
		<jsp:useBean id="dietIngSvc" scope="page"
			class="com.dietIng.model.dietIngService" />
		<select size="1" name="dietIng">
			<c:forEach var="dietIngVO" items="${dietIngSvc.all}">
				<option value="${dietIngVO.dietIng_no}">${dietIngVO.dietIng_name}
			</c:forEach>
		</select>
		<table>
			<tr>
				<td>會員編號:</td>
				<td><%=memno%></td>
			</tr>
			<tr>
				<td>內容:</td>
				<td><textarea rows='5' cols='20' name='diet_content'><%=diet_content%></textarea></td>
			</tr>

		</table>
		<input type="submit" value="送出"> <input type="hidden"
			name="action" value="update"> <input type="hidden"
			name="memno" value="<%=memno%>">
			<input type="hidden" name="dietno" value="<%=dietno%>">
			<input type="hidden" name="dietcon_no" value="<%=dietcon_no%>">
			<%=dietno%>
<!-- 			<input type="hidden" name="diet_content" name="diet_content"> -->

	</FORM>


</body>
</html>