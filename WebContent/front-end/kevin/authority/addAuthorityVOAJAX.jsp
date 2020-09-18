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



<label><input type="checkbox" name="features" value="F01">會員管理</label>
<br>
<label><input type="checkbox" name="features" value="F02">員工管理</label>
<br>
<label><input type="checkbox" name="features" value="F03">揪團留言檢舉管理</label>
<br>
<label><input type="checkbox" name="features" value="F04">文章檢舉管理</label>
<br>
<label><input type="checkbox" name="features" value="F05">專長種類管理</label>
<br>
<label><input type="checkbox" name="features" value="F06">教練</label>
<br>
<label><input type="checkbox" name="features" value="F07">揪團管理</label>
<br>
<label><input type="checkbox" name="features" value="F08">最新消息管理</label>
<br>
<label><input type="checkbox" name="features" value="F09">一對一課程管理</label>
<br>
<label><input type="checkbox" name="features" value="F10">訂單管理</label>
<br>
<label><input type="checkbox" name="features" value="F11">商品管理</label>
<br>
<br>
<input type="checkbox" id="orChecked">
全選/反選/全不選
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" name="submit" value="送出新增">
</div>