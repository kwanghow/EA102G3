package com.emp.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.employee.model.EmployeeVO;

public class empFilter implements Filter {

	public void init(FilterConfig config) {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session=req.getSession();
		EmployeeVO employeeVO=(EmployeeVO)session.getAttribute("employeeVO");
		if(employeeVO==null) {
			session.setAttribute("location",req.getRequestURI());
			
			res.sendRedirect(req.getContextPath()+"/back-end/kevin/index/login.jsp");
		}else {
			String empChatId=employeeVO.getEmp_id();
			session.setAttribute("empChatId",empChatId);
			chain.doFilter(request, response);
		}
	}
}