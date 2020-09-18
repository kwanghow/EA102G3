<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page
	import="com.employee.model.* , com.authority.model.* ,java.util.* ,com.features.model.*"%>

<%
	String emp_id = request.getParameter("emp_id");
	String[] features = request.getParameterValues("features");
	AuthorityService authoritySvc = new AuthorityService();
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

	List<String> list3 = new ArrayList<String>();
	for (FeaturesVO a : list2) {
		list3.add(a.getFeatures_id());
	}

	EmployeeVO employeeVO = (EmployeeVO) request.getAttribute("employeeVO"); //EmpServlet.java (Concroller) �s�Jreq��streamVO���� (�]�A�������X��streamVO, �]�]�A��J��ƿ��~�ɪ�streamVO����)
%>

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/css/js/bootstrap.min.css">
<script
	src="<%=request.getContextPath()%>/front-end/css/js/jquery-2.1.0.min.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>���u��ƭק� - update_authority_input.jsp</title>

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
				<h3>���u��ƭק� - update_authority_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/index/index.jsp"><img
						src="<%=request.getContextPath()%>/front-end/images/facebook.png"
						width="100" height="32" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>��ƭק�:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	<div class="displayblock">
		<div class="col-4">
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/front-end/authority/authority.do"
				name="form1">
				<table>
					<tr>
						<td>���u�s��:</td>
						<td><font color=red><%=employeeVO.getEmp_id()%></font></td>
					</tr>

					<tr>
						<td>���u�m�W:</td>
						<td><input type="TEXT" name="emp_name" size="45"
							value="<%=employeeVO.getEmp_name()%>" /></td>
					</tr>

					<tr>
						<td>���u�b��:</td>
						<td><input type="TEXT" name="emp_account" size="45"
							value="<%=employeeVO.getEmp_account()%>" /></td>
					</tr>

					<tr>
						<td>���u�K�X:</td>
						<td><input type="TEXT" name="emp_psw" size="45"
							value="<%=employeeVO.getEmp_psw()%>" /></td>
					</tr>
					<tr>
						<td>���u�H�c:</td>
						<td><input type="TEXT" name="emp_email" size="45"
							value="<%=employeeVO.getEmp_email()%>" /></td>
					</tr>
					<tr>
						<td>���u�q��:</td>
						<td><input type="TEXT" name="emp_phone" size="45"
							value="<%=employeeVO.getEmp_phone()%>" /></td>
					</tr>
				</table>
				<label>����v��:</label> <br>
				<c:forEach var="featuresVO" items="${list2}">
					<input type="checkbox" name="features"
						value="${featuresVO.features_id}"${(fn:contains(emp,featuresVO.features_id))? 'checked':''}>${featuresVO.features_name}
							<br>
				</c:forEach>
				<br> <input type="checkbox" id="orChecked">����/�Ͽ�/������ <br>
				<br> <input type="hidden" name="action" value="insert">
				<input type="hidden" name="emp_id"
					value="<%=employeeVO.getEmp_id()%>"> <input type="submit"
					value="�e�X�ק�">
			</FORM>
		</div>
	</div>



	<script>
		// 		function getInfo() {

		// 			var features = new Array();
		// 			$('input:checkbox:checked[name="features"]').each(function() {
		// 				features.push($(this).val());
		// 			});

		// 			$.ajax({
		// 				url : "addAuthorityAJAX.jsp",
		// 				type : "POST",
		// 				traditional : true,
		// 				data : {
		// 					features : features
		// 				},
		// 				success : function(data) {

		// 					$("#showPanel").text(data);
		// 				},
		// 				error : function(XMLHttpRequest, status, error) {
		// 					console.log(error)
		// 				}
		// 			})
		// 		}
		// 		window.addEventListener("load", function() {
		// 			$('input[name="submit"]').click(getInfo);
		// 		}, false);

		$(document).ready(function() {

			$("#orChecked").click(function() {
				if ($("#orChecked").prop("checked")) {//�p�G������s���Q��ܪ��ܡ]�Q��ܬOtrue�^
					$("input[name='features']").each(function() {
						$(this).prop("checked", true);//��Ҧ����֨���ت�property���ܦ��Ŀ�
					})
				} else {
					$("input[name='features']").each(function() {
						$(this).prop("checked", false);//��Ҧ����֤�ت�property�������Ŀ�
					})
				}
			})
		})
	</script>
</body>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/jquery-2.1.0.min.js"></script>

<!-- Bootstrap -->
<script src="<%=request.getContextPath()%>/front-end/css/js/popper.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/bootstrap.min.js"></script>

<!-- Plugins -->
<script
	src="<%=request.getContextPath()%>/front-end/css/js/scrollreveal.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/waypoints.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/jquery.counterup.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/imgfix.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/css/js/mixitup.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/css/js/accordions.js"></script>

<!-- Global Init -->
<script src="<%=request.getContextPath()%>/front-end/css/js/custom.js"></script>




</html>