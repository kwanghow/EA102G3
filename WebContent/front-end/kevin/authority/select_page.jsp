<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>權限查詢: Home</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
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

</head>
<body bgcolor='white'>

	<table id="table-1">

		<tr>
			<td><h3>權限管理首頁</h3>
				<h4>
					<img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="100" border="0">
				</h4></td>
		</tr>
	</table>

	<p>EmpAuthority: Home</p>

	<h3>員工權限查詢:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<ul>
		<li><a href='listAllAuthority.jsp'>List</a> all Emps. <br>
		<br></li>


		<jsp:useBean id="empSvc" scope="page"
			class="com.employee.model.EmployeeService" />

		<li>
			<FORM METHOD="post" ACTION="authority.do">
				<b>選擇員工編號:</b> <select size="1" name="emp_id">
					<c:forEach var="employeeVO" items="${empSvc.all}">
						<option value="${employeeVO.emp_id}">${employeeVO.emp_id}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post" ACTION="authority.do">
				<b>選擇員工姓名:</b> <select size="1" name="emp_id">
					<c:forEach var="employeeVO" items="${empSvc.all}">
						<option value="${employeeVO.emp_id}">${employeeVO.emp_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FORM>
		</li>

	</ul>



</body>
</html>