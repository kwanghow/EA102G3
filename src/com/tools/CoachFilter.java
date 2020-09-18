package com.tools;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.coach.model.CoachVO;
import com.mem.model.MemVO;

public class CoachFilter implements Filter {
	
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
		CoachVO coachVO = (CoachVO)session.getAttribute("coachLogIn");
		MemVO memVO = (MemVO)session.getAttribute("memLogIn");
		
		if(coachVO == null && memVO != null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/becomeCoach.jsp");
			return;
						
		}if (coachVO != null && coachVO.getIsCoach() == 0) {
			session.setAttribute("loaction", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/front-end/kevin/index.jsp");
			return;
		}
		else {
			chain.doFilter(request, response);
		}	
	}
}
