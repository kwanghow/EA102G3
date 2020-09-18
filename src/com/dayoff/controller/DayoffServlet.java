package com.dayoff.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;

import com.mem.model.*;
import com.coach.model.*;
import com.dayoff.model.*;

@WebServlet("/dayoff.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class DayoffServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("Display_calendar".equals(action)) {
			
			try {
				String coach_Id = req.getParameter("coach_Id");
				
				DayoffService dayoffSvc = new DayoffService();
				List<DayoffVO> list = dayoffSvc.oneCoachAllTime(coach_Id);
				
				JSONArray arr = new JSONArray(list);

				
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(arr.toString());
				out.flush();
				out.close();

			}catch (Exception e) {
				System.out.println("無法取得要查詢的資料" + e.getMessage());
			}
		}
		
		if("insert_dayoff".equals(action)) {
			
			try {
				CoachVO coachVO = (CoachVO)session.getAttribute("coachLogIn");
				String coach_Id = coachVO.getCoach_Id();
				java.sql.Date dayoff_Date = java.sql.Date.valueOf(req.getParameter("dayoff_Date"));
				String dayoff_Time = "111111111111111111111111";
				
				System.out.println(coach_Id);
				System.out.println(dayoff_Date);
				
				DayoffService dayoffSvc = new DayoffService();
				DayoffVO dayoffVO = dayoffSvc.addDayoff(coach_Id, dayoff_Date, dayoff_Time);
				
				// JSON一定要打的這幾行
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("新增成功");
				out.flush();
				out.close();
				
				
			}catch (Exception e) {
				System.out.println("新增失敗" + e.getMessage());
			}
		}
		
	}
}
