<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.diet.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- <jsp:useBean id="listDiet_ByMemberID" scope="request" type="java.util.Set<dietVO>"/> --%>

<%
		 	MemVO memVO = (MemVO) request.getAttribute("memVO"); 
 %> 

<%-- <% --%>
<!-- // 	DietService dietSvc = new DietService(); -->
<!-- // 	dietVO dietVO = dietSvc.getOneDiet(memVO.getMember_id()); -->
<!-- // 	//List<dietVO> list = dao.getAll(); -->
<%-- %> --%>

<html>
<head>
<meta charset="BIG5">
<title>�|����� - listOneMember</title>

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
</style>
</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr><td>
			<h3>�|�����</h3>
			<h4><a href="<%=request.getContextPath()%>/select.jsp">�^����</a></h4>
		</td></tr>
	</table>
	
	<table>
		<tr>
			<td>�|���s��</td>
			<td>�|���W��</td>
			<td>�|���b��</td>
			<td>�|���ʧO</td>

		</tr>
		

		<tr>
			<td><%=memVO.getMember_Id()%></td>
			<td><%=memVO.getMem_Name()%></td>
			<td><%=memVO.getMem_Account()%></td>
			<td><%=memVO.getMem_Sex()%></td>
			<c:forEach var="dietVO" items="${list}">
				<td>${dietVO.dietno}</td>
			</c:forEach>
		</tr>
	</table>

</body>
</html>