package com.mem.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemVO;

@WebServlet("/member/memberAjax.do")
public class MemAjaxServlet extends HttpServlet {       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("經過" + req.getRequestURI());
		req.setCharacterEncoding("UTF-8");		
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			HttpSession session = req.getSession();
			MemVO memberVo = (MemVO) session.getAttribute("memLogIn");			
			if(memberVo == null) {
				session.setAttribute("location", req.getParameter("location"));
				System.out.println("loaction: "+req.getParameter("location"));
				out.write("NoLogIn");
				return;
			}else {
				out.write("logIn");
			}			
		} catch(Exception e) {
			throw new RuntimeException("判斷登入出錯拉!" + e.getMessage());
		}

	}

}
