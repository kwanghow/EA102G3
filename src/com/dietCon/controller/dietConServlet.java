package com.dietCon.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.diet.model.dietVO;
import com.dietCon.model.dietConService;
import com.dietCon.model.dietConVO;
import com.mem.model.MemVO;

public class dietConServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException ,ServletException{
		
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		String requestURL = req.getParameter("requestURL");
		
		if("showDietContent".equals(action)){
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			try {

//				*********************1.接收參數************************
				String dietno = new String(req.getParameter("dietno"));
				String memno=  new String(req.getParameter("memno"));
				
//				*********************2.查詢資料************************
				
				dietConService dietConSvc = new dietConService();
				Set<dietConVO> set = dietConSvc.getOnediet(dietno);
//				System.out.println(set);
								
//				*********************3.轉交***************************
				session.setAttribute("listAlldietContent",set);
				req.setAttribute("dietno",dietno);
				req.setAttribute("memno",memno);
				String url = null;
				url = "/front-end/guochi/diet/listAllDietContext.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("查詢失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		if ("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
//				*********************取得參數************************
				String dietcon_no = new String(req.getParameter("dietcon_no"));
				String diet_no = new String(req.getParameter("diet_no"));
				String memno=  new String(req.getParameter("memno"));
				
				System.out.println("dietcon_no:"+dietcon_no);
				System.out.println("diet_no:"+diet_no);
				System.out.println("memno:"+memno);
				
//				***********************刪除************************
				dietConService dietConSvc = new dietConService();
				dietConSvc.delete(dietcon_no);
				System.out.println("刪除完成");
				
//				***********************取得Set*********************
				Set<dietConVO> set = dietConSvc.getOnediet(diet_no);
				
//				***********************刪除完成，準備轉交***************
				req.setAttribute("listAlldietContent",set);
				req.setAttribute("dietno",diet_no);
				req.setAttribute("memno",memno);
				String url = null;
				url = "/front-end/guochi/diet/listAllDietContext.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				
				errorMsgs.add("查詢失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
			
		}
		
		if("updateDiet".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				
				String dietcon_no = new String(req.getParameter("dietcon_no"));
				String dietno = new String(req.getParameter("dietno"));
				String memno = new String(req.getParameter("memno"));
				String diet_content = new String(req.getParameter("diet_content"));
				
				req.setAttribute("dietcon_no",dietcon_no);
				req.setAttribute("dietno",dietno);
				req.setAttribute("memno",memno);
				req.setAttribute("diet_content", diet_content);
				
				String url = "/front-end/guochi/diet/updateDiet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req,res);
				
			}catch (Exception e) {
				
				errorMsgs.add("更新失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			System.out.println("送出修改");
			try {
				
				String memno = new String(req.getParameter("memno"));
				String dietno = new String(req.getParameter("dietno"));
				String dietcon_no = new String(req.getParameter("dietcon_no"));
				String dietIng = new String(req.getParameter("dietIng"));
				String diet_content = new String(req.getParameter("diet_content"));
//				*********************修改*****************************
				dietConService dietConSvc = new dietConService();
				dietConSvc.update(dietIng, diet_content,dietcon_no);
				
				System.out.println("==========更新完成==============");
//				存取更新過後的資料
				Set<dietConVO> set = dietConSvc.getOnediet(dietno);
//				************************轉交**************************
				req.setAttribute("listAlldietContent",set);
				req.setAttribute("dietno",dietno);
				req.setAttribute("memno",memno);
				
				String url = null;
				url = "/front-end/guochi/diet/listAllDietContext.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				
				errorMsgs.add("更新失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	}

}
