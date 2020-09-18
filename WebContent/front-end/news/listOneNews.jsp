<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.news.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  NewsVO newsVO = (NewsVO) request.getAttribute("newsVO"); //newsServlet.java(Concroller), 存入req的newsVO物件
%>

<jsp:useBean id="news_specSvc" scope="page" class="com.newsspec.model.News_specService" />

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
		<th>最新消息編號</th>
		<th>最新消息標題</th>
		<th>最新消息類別</th>
		<th>員工ID</th>
		<th>最新消息內容</th>
		
		
	</tr>
	<tr>
		<td><%=newsVO.getNews_id()%></td>
		<td><%=newsVO.getNews_header()%></td>
		<td><c:forEach var="news_specVO" items="${news_specSvc.all}">
                    <c:if test="${newsVO.news_spec==news_specVO.news_spec}">
	                     ${news_specVO.news_specheader}
                    </c:if>
                </c:forEach>
			</td>
		<td><%=newsVO.getEmp_id()%></td>
		<td><%=newsVO.getNews_content()%></td>
		
		
	</tr>
</table>

</body>
</html>