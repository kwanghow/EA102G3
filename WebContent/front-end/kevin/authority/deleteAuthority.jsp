<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page
	import="com.authority.model.* , com.employee.model.* ,java.util.* , com.features.model.*"%>
	
	
	
	
	
	
<!DOCTYPE html>
<html>
<head>
<script src="css/js/jquery-2.1.0.min.js"></script>
<meta charset="BIG5">
<title>權限開通</title>
<style>
</style>

</head>

<%
	List<AuthorityVO> list = (List<AuthorityVO>) request.getAttribute("list");
	pageContext.setAttribute("list", list);
	List<String> list1 = new ArrayList<String>();
	for(AuthorityVO f :list){
		list1.add(f.getFeatures_id());
	}
	pageContext.setAttribute("list1", list1);
	FeaturesService featuresSvc = new FeaturesService();
	List<FeaturesVO> featureslist = featuresSvc.getAll();
	pageContext.setAttribute("RGB", featureslist);
	
%>
<body>
	<h4>
		<a href="select_page.jsp">回首頁</a>
	</h4>
	<jsp:useBean id="authorityService" scope="page"
		class="com.authority.model.AuthorityService" />
	<div id="checkboxfeatures">
		<b>員工編號</b> <b><%=request.getParameter("emp_id")%></b>

		<div id="checkbox"></div>
		<FORM METHOD="post" ACTION="authority.do">
		
		
		<c:forEach var="featuresVO" items="${RGB}">
				<c:if test="${fn:contains(list1,featuresVO.features_id)}">
						<label><input type="checkbox" name="features"
						 value="${featuresVO.features_id}">${featuresVO.features_name}</label>
						<br>
				</c:if>
		</c:forEach>
			<br> <input type="checkbox" id="orChecked">全選/反選/全不選 <br>
			<input type="hidden" name="emp_id" value=<%=request.getParameter("emp_id")%>>
			<input type="hidden" name="action" value="delete"><input
				type="submit" name="submit" value="送出刪除">
		</FORM>
	</div>
	<div id="showPanel"></div>
	<%-- ${(newsVO.news_spec==news_specVO.news_spec)?'selected':'' } --%>

	<script>
		function getInfo() {

			var features = new Array();
			var emp_id = $("#emp_id").val();
			$('input:checkbox:checked[name="features"]').each(function() {
				features.push($(this).val());
			});

			$.ajax({
				url : "addAuthorityAJAX.jsp",
				type : "POST",
				traditional : true,
				data : {
					features : features,
					emp_id : emp_id
				},
				success : function(data) {

					$("#showPanel").html(data);
				},
				error : function(XMLHttpRequest, status, error) {
					console.log(error)
				}
			})
		}
		window.addEventListener("load", function() {
			$('input[name="submit"]').click(getInfo);
		}, false);

		$(document).ready(function() {

			$("#orChecked").click(function() {
				if ($("#orChecked").prop("checked")) {//如果全選按鈕有被選擇的話（被選擇是true）
					$("input[name='features']").each(function() {
						$(this).prop("checked", true);//把所有的核取方框的property都變成勾選
					})
				} else {
					$("input[name='features']").each(function() {
						$(this).prop("checked", false);//把所有的核方框的property都取消勾選
					})
				}
			})
		})
	</script>

</body>
<script src="<%=request.getContextPath()%>/front-end/css/js/jquery-2.1.0.min.js"></script>

	<!-- Bootstrap -->
	<script src="<%=request.getContextPath()%>/front-end/css/js/popper.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/bootstrap.min.js"></script>

	<!-- Plugins -->
	<script src="<%=request.getContextPath()%>/front-end/css/js/scrollreveal.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/jquery.counterup.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/imgfix.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/mixitup.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/css/js/accordions.js"></script>

	<!-- Global Init -->
	<script src="<%=request.getContextPath()%>/front-end/css/js/custom.js"></script>

</html>