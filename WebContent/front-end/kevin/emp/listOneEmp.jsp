<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.employee.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO"); //newsServlet.java(Concroller), 存入req的newsVO物件
%>


<html>
<head>
<title>最新消息資訊 - listOneEmp.jsp</title>

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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>消息資料 - listOneNews.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>員工編號</th>
		<th>員工姓名</th>
		<th>員工帳號</th>
		<th>員工密碼</th>
		<th>員工信箱</th>
		<th>員工電話</th>
		
		
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