package com.tools;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.mem.model.MemVO;

public class LoginFilter implements Filter {
	
	ServletContext context = null;
	
	public void init (FilterConfig config) {
		context = config.getServletContext();
	}
	
	public void destory() {
		context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		MemVO memVO = (MemVO)session.getAttribute("memLogIn");
		if(memVO == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/logIn2.jsp");
			return;
		
		}else {
			chain.doFilter(request, response);
		}	
	}
}
