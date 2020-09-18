<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.authority.model.* , com.employee.model.* , java.util.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	AuthorityService authoritySvc = new AuthorityService();
	List<AuthorityVO> list = authoritySvc.getAll();
	String[] features = request.getParameterValues("features");
	String emp_id = request.getParameter("emp_id");


%>





