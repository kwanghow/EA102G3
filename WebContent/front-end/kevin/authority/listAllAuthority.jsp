<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page
	import="com.employee.model.* , com.authority.model.* , com.features.model.*"%>

<%
	AuthorityService authoritySvc = new AuthorityService();
	List<AuthorityVO> list = authoritySvc.getAll();
	pageContext.setAttribute("list", list);

	EmployeeService empSvc = new EmployeeService();
	List<EmployeeVO> listEmp = empSvc.getAll();
	pageContext.setAttribute("listEmp", listEmp);
%>

<jsp:useBean id="featuresnameSvc" scope="page"
	class="com.features.model.FeaturesService" />
<html>
<head>
<title>所有消息資料 - listAllAuthority.jsp</title>

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
.toright{
	float:right;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有權限 - listAllAuthority.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="100" border="0">回首頁</a>
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
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>員工帳號</th>
			<th>員工密碼</th>
			<th>員工信箱</th>
			<th>員工電話</th>
			<th colspan="${featuresnameSvc.all.size()}">員工權限</th>
			<th>修改</th>

		</tr>

		<%-- 		<%@ include file="page1.file"%> --%>
		<c:forEach var="employeeVO" items="${listEmp}">
			<tr>
				<td>${employeeVO.emp_id}</td>
				<td>${employeeVO.emp_name}</td>
				<td>${employeeVO.emp_account}</td>
				<td>${employeeVO.emp_psw}</td>
				<td>${employeeVO.emp_email}</td>
				<td>${employeeVO.emp_phone}</td>
				<c:forEach var="authorityVO" items="${list}">
					<c:if test="${fn:contains(authorityVO.emp_id,employeeVO.emp_id)}">
						<td>
							${featuresnameSvc.getOneFeatures(authorityVO.features_id).features_name}
						</td>
					</c:if>
				</c:forEach>
				<td >
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/front-end/authority/authority.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改" > <input type="hidden"
							name="emp_id" value="${employeeVO.emp_id}"> <input
							type="hidden" name="action" value="getOne_For_Display">
					</FORM>
				</td>
			</tr>

		</c:forEach>


	</table>
	<%-- 	<%@ include file="page2.file"%> --%>

</body>
</html>