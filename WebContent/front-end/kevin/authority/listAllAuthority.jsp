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
<title>�Ҧ�������� - listAllAuthority.jsp</title>

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

	<h4>�����m�߱ĥ� EL ���g�k����:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>�Ҧ��v�� - listAllAuthority.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="100" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>���u�s��</th>
			<th>���u�m�W</th>
			<th>���u�b��</th>
			<th>���u�K�X</th>
			<th>���u�H�c</th>
			<th>���u�q��</th>
			<th colspan="${featuresnameSvc.all.size()}">���u�v��</th>
			<th>�ק�</th>

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
						<input type="submit" value="�ק�" > <input type="hidden"
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