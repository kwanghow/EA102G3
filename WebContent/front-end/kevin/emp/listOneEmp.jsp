<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
  EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO"); //newsServlet.java(Concroller), �s�Jreq��newsVO����
%>


<html>
<head>
<title>�̷s������T - listOneEmp.jsp</title>

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
	width: 600px;
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

<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>������� - listOneNews.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>���u�s��</th>
		<th>���u�m�W</th>
		<th>���u�b��</th>
		<th>���u�K�X</th>
		<th>���u�H�c</th>
		<th>���u�q��</th>
		
		
	</tr>
	<tr>
		<td>${employeeVO.emp_id}</td>
        <td>${employeeVO.emp_name}</td>
        <td>${employeeVO.emp_account}</td>
        <td>${employeeVO.emp_psw}</td>
        <td>${employeeVO.emp_email}</td>
        <td>${employeeVO.emp_phone}</td>		
		
	</tr>
</table>

</body>
</html>