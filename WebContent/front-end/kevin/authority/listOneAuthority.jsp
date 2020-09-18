<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page
	import="com.authority.model.* ,java.util.* , com.employee.model.* , com.features.model.*"%>

<%
	AuthorityService authoritySvc = new AuthorityService();
	String emp_id = request.getParameter("emp_id");
	List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
	request.setAttribute("list", list);

	List<AuthorityVO> list1 = authoritySvc.getOneAuthority(emp_id);
	request.setAttribute("list1", list1);
	List<String> emp = new ArrayList<String>();
	for (AuthorityVO a : list1) {
		emp.add(a.getFeatures_id());
	}
	pageContext.setAttribute("emp", emp);

	FeaturesService feaSvc = new FeaturesService();
	List<FeaturesVO> list2 = feaSvc.getAll();
	request.setAttribute("list2", list2);

	EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO");
%>


<html>
<head>
<title>最新消息資訊 - listOneAuthority.jsp</title>

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
		<tr>
			<td>
				<h3>消息資料 - listOneNews.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>
	<FORM METHOD="post" ACTION="authority.do">
		<table>
			<tr>
				<th>員工編號</th>
				<th>員工姓名</th>
				<th>權限選擇</th>

			</tr>

			<td rowspan="${list2.size()+1}">${employeeVO.emp_id}</td>
			<td rowspan="${list2.size()+1}">${employeeVO.emp_name}</td>
			<c:forEach var="featuresVO" items="${list2}">

				<tr>
					<td><input type="checkbox" name="features"
						value="${featuresVO.features_id}"
						${(fn:contains(emp,featuresVO.features_id))? 'checked':''}>${featuresVO.features_name}</td>
				</tr>
			</c:forEach>
		</table>

		<input type="hidden" name="emp_id" value=<%=emp_id%>> <input
			type="hidden" name="action" value="update"> <input
			type="submit" value="增加權限">
	</Form>

</body>
</html>