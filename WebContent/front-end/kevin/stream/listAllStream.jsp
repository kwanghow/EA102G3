<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.stream.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
    StreamService streamSvc = new StreamService();
    List<StreamVO> list = streamSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>�Ҧ�������� - listAllEmp.jsp</title>

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

<h4>�����m�߱ĥ� EL ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>�Ҧ�������� - listAllEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>�����s��</th>
		<th>�нmID</th>
		<th>VOD</th>
		<th>�w�i���</th>
		<th>�������D</th>		
		<th>�ק�</th>
		<th>�R��</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="streamVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${streamVO.stream_id}</td>
			<td>${streamVO.coach_id}</td>
			
			<c:choose>
			<c:when test="${streamVO.stream_vod!=null}">
			<td><a class="pull-left">
			<video width="240" height="160" id="${streamVO.stream_id}"src="<%=request.getContextPath()%>/BlobReader?stream_id=${streamVO.stream_id}"
			controls="controls" muted>
			</video></a></td>
			</c:when>
			<c:otherwise>
			<td>�L��������</td>
			</c:otherwise>
			</c:choose>
			<td><fmt:formatDate value="${streamVO.stream_notice}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${streamVO.stream_header}</td>			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/stream/stream.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="stream_id"  value="${streamVO.stream_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/stream/stream.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="stream_id"  value="${streamVO.stream_id}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>