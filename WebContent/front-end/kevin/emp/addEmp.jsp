<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>

<%
  EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>�̷s�����s�W - addEmp.jsp</title>

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
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>���u��Ʒs�W - addNews.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>��Ʒs�W:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="emp.do" name="form1">
		<table>

			<tr>
				<td>���u�m�W:</td>
				<td><input type="TEXT" name="emp_name" size="45"
					value="<%= (employeeVO==null)? "" : employeeVO.getEmp_name()%>" /></td>
			</tr>
			<tr>
				<td>���u�b��:</td>
				<td><input type="TEXT" name="emp_account" size="45"
					value="<%= (employeeVO==null)? "" : employeeVO.getEmp_account()%>" /></td>
			</tr>
			<tr>
				<td>���u�K�X:</td>
				<td><input type="TEXT" name="emp_psw" size="45"
					value="<%= (employeeVO==null)? "" : employeeVO.getEmp_psw()%>" /></td>
			</tr>
			
			
			<tr>
				<td>���u�H�c:</td>
				<td><input type="TEXT" name="emp_email" size="45"
					value="<%= (employeeVO==null)? "" : employeeVO.getEmp_email()%>" /></td>
			</tr>
			<tr>
				<td>���u���:</td>
				<td><input type="TEXT" name="emp_phone" size="45"
					value="<%= (employeeVO==null)? "" : employeeVO.getEmp_phone()%>" /></td>
			</tr>





		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="�e�X�s�W">
	</FORM>
</body>




</html>