package com.features.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeaturesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException {
		
	
	doPost(req,res);
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String[] features = req.getParameterValues("features");
		
		
		
		
	}
}
