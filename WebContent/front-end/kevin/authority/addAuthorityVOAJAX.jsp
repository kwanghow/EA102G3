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
	System.out.println(emp_id);
	list = authoritySvc.getOneAuthority(emp_id);

	session.setAttribute("list", list);
%>



<label><input type="checkbox" name="features" value="F01">�|���޲z</label>
<br>
<label><input type="checkbox" name="features" value="F02">���u�޲z</label>
<br>
<label><input type="checkbox" name="features" value="F03">���ίd�����|�޲z</label>
<br>
<label><input type="checkbox" name="features" value="F04">�峹���|�޲z</label>
<br>
<label><input type="checkbox" name="features" value="F05">�M�������޲z</label>
<br>
<label><input type="checkbox" name="features" value="F06">�нm</label>
<br>
<label><input type="checkbox" name="features" value="F07">���κ޲z</label>
<br>
<label><input type="checkbox" name="features" value="F08">�̷s�����޲z</label>
<br>
<label><input type="checkbox" name="features" value="F09">�@��@�ҵ{�޲z</label>
<br>
<label><input type="checkbox" name="features" value="F10">�q��޲z</label>
<br>
<label><input type="checkbox" name="features" value="F11">�ӫ~�޲z</label>
<br>
<br>
<input type="checkbox" id="orChecked">
����/�Ͽ�/������
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" name="submit" value="�e�X�s�W">
</div>