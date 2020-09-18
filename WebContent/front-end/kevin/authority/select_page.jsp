<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>�v���d��: Home</title>
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
			<td><h3>�v���޲z����</h3>
				<h4>
					<img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="100" border="0">
				</h4></td>
		</tr>
	</table>

	<p>EmpAuthority: Home</p>

	<h3>���u�v���d��:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
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
				<b>��ܭ��u�s��:</b> <select size="1" name="emp_id">
					<c:forEach var="employeeVO" items="${empSvc.all}">
						<option value="${employeeVO.emp_id}">${employeeVO.emp_id}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="�e�X">
			</FORM>
		</li>
		<li>
			<FORM METHOD="post" ACTION="authority.do">
				<b>��ܭ��u�m�W:</b> <select size="1" name="emp_id">
					<c:forEach var="employeeVO" items="${empSvc.all}">
						<option value="${employeeVO.emp_id}">${employeeVO.emp_name}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="�e�X">
			</FORM>
		</li>

	</ul>



</body>
</html>