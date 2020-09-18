<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.authority.model.* , com.employee.model.*"%>
<%
	request.setCharacterEncoding("UTF-8");
	AuthorityService authoritySvc = new AuthorityService();
	String[] features = request.getParameterValues("features");
	String show = "";
	for (int i = 0; i < features.length; i++) {
		show += features[i]+"----------";
	}
%>
<%=show%>




