package com.pro.StatusMap;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StatusMap implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();		
		Map<Integer,String> Product_Status = new HashMap<>();
		Product_Status.put(new Integer(0), "�W�[��");
		Product_Status.put(new Integer(1), "�U�[��");
		context.setAttribute("prodStatus", Product_Status);
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
}