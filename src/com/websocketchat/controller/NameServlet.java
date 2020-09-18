package com.websocketchat.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class NameServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String memId = req.getParameter("memId");
		String reciver = req.getParameter("reciver");
		
		if(memId !=null && memId.length()>0 ) {
			req.setAttribute("userName", memId);
		}else {
			req.setAttribute("userName", "@"+UUID.randomUUID().toString().replaceAll("-", "0"));
		}
		
		req.setAttribute("memId", memId);
		req.setAttribute("reciver", reciver);
		req.setAttribute("reciverName", FriendWS.findName(reciver));
		req.setAttribute("selfName", FriendWS.findName((String)req.getAttribute("userName")));

		RequestDispatcher dispatcher = req.getRequestDispatcher("/chat2.jsp");
		dispatcher.forward(req, res);
		return;
	}
}
