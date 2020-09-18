<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.news.model.*"%>

<%
  NewsVO newsVO = (NewsVO) request.getAttribute("newsVO"); //EmpServlet.java (Concroller) �s�Jreq��streamVO���� (�]�A�������X��streamVO, �]�]�A��J��ƿ��~�ɪ�streamVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�̷s�����ק� - update_news_input.jsp</title>

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
	<tr><td>
		 <h3>������ƭק� - update_news_input.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��ƭק�:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="news.do" name="form1">
<table>
	<tr>
		<td>�����s��:<font color=red><b>*</b></font></td>
		<td><%=newsVO.getNews_id()%></td>
	</tr>
	<tr>
		<td>�������D:</td>
		<td><input type="TEXT" name="news_header" size="45"	value="<%=newsVO.getNews_header()%>" /></td>
	</tr>
<!-- 	<tr> -->
<!-- 		<td>�������O:</td> -->
<%-- 		<td><input type="TEXT" name="news_spec" size="45" value="<%=newsVO.getNews_spec()%>" /></td> --%>
<!-- 	</tr> -->
	<tr>
		<td>���uID:</td>
		<td><input type="TEXT" name="emp_id" size="45"	value="<%=newsVO.getEmp_id()%>" /></td>
	</tr>
	<tr>
		<td>�������e:</td>
		<td><input name="news_content"  type="text" value="<%=newsVO.getNews_content()%>"></td>
	</tr>
	
	

	<jsp:useBean id="news_specSvc" scope="page" class="com.newsspec.model.News_specService" />
	<tr>
		<td>��������:<font color=red><b>*</b></font></td>
		<td><select size="1" name="news_spec">
			<c:forEach var="news_specVO" items="${news_specSvc.all}">
				<option value="${news_specVO.news_spec}" ${(newsVO.news_spec==news_specVO.news_spec)?'selected':'' } >${news_specVO.news_specheader}
			</c:forEach>
		</select></td>
	</tr>

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="news_id" value="<%=newsVO.getNews_id()%>">
<input type="submit" value="�e�X�ק�"></FORM>
</body>





</html>