package com.coach.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.coach.model.*;
import com.exp.model.*;
import com.exptype.model.*;
import com.mem.model.*;


@WebServlet("/coach.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class CoachServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/***************************  後台員工修改教練資料    *****************************************/
		/*修改教練專長審核*/
		if("update_isExp".equals(action)) {
			try {
				
				String coach_Id = req.getParameter("coach_Id");
				String[] exp_Id = req.getParameterValues("exp_Id");
				String[] isExps = req.getParameterValues("isExp");
								
				List <ExpVO> expList = new ArrayList<ExpVO>();
				
				for (int i=0; i<exp_Id.length; i++) {
					ExpVO expVO = new ExpVO();

					expVO.setCoach_Id(coach_Id);
					expVO.setExp_Id(exp_Id[i]);
					expVO.setIsExp(new Integer(isExps[i]));
					expList.add(expVO);	
				}
				
				ExpService expSvc = new ExpService();
				expSvc.updateIsExp(expList);
				
				String url = "/back-end/admin-2/back-coach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		/*修改教練權限*/
		if ("update_isCoach".equals(action)) {
			
			try {
				
				String coach_Id = req.getParameter("coach_Id");
				Integer isCoach = new Integer(req.getParameter("isCoach"));
				
				CoachService coachSvc = new CoachService();
				
				coachSvc.updateFromBack(coach_Id, isCoach);
				
				String url = "/back-end/admin-2/back-coach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
		
		if ("become_coach".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				// 會員編號
				String member_Id = req.getParameter("member_Id").trim();
				if (member_Id == null || member_Id.trim().length() == 0) {
					errorMsgs.add("會員編號不可為空, 請先<登入現有帳號>或<註冊會員>才能申請成為教練!");
				}
				// 相關經驗
				String experience = req.getParameter("experience").trim();
				if (experience == null || experience.trim().length() == 0) {
					errorMsgs.add("申請教練資格必須填寫相關經驗");
				}
				// 教練照片
				byte[] coach_Img = null;
				Part part = req.getPart("coach_Img");
				InputStream in = part.getInputStream();			
				byte[] buf = new byte[in.available()];
				in.read(buf);
				coach_Img = buf;
				in.close();
				// 專長(多筆)	
				String[] exp_Id = req.getParameterValues("exp_Id");
				if(exp_Id == null) {
					errorMsgs.add("申請教練資格必須至少選取一項專長");
				}
				
				CoachVO coachVO = new CoachVO();
				coachVO.setMember_Id(member_Id);
				coachVO.setCoach_Img(coach_Img);
				coachVO.setExperience(experience);	
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO); // 含有輸入格式錯誤的memVO物件,也存入req
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "申請失敗!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeCoach.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/				
				CoachService coachSvc = new CoachService();
				coachVO = coachSvc.insertWithExps(member_Id, experience, coach_Img, exp_Id);
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "申請成功!");
				
				/*************************** 3.申請完成,準備轉交(Send the Success view) *************/
				
				String url = "/front-end/kevin/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				
				/*************************** 其他可能的錯誤處理 *************************************/
			}catch(Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "申請失敗!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeCoach.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*************************** 前台教練修改資料   *****************************************/
		if ("update_Coach".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
			try {
				// 教練編號
				String coach_Id = req.getParameter("coach_Id");
				// 會員編號
				String member_Id = req.getParameter("member_Id");
				// 教練照片
				CoachService coachSvc = new CoachService();
				byte[] coach_Img = null;
				Part part = req.getPart("coach_Img");
				InputStream in = part.getInputStream();
				
				if(in.available() != 0) {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					coach_Img = buf;
					in.close();
				}else {
					coach_Img = coachSvc.getOneByCoach(coach_Id).getCoach_Img();
				}
				// 相關經驗
				String experience = req.getParameter("experience").trim();
				if (experience == null || experience.trim().length() == 0) {
					errorMsgs.add("教練經驗請勿空白");
				};

				// 是否為教練
				Integer isCoach = new Integer(req.getParameter("isCoach"));
		
				
				CoachVO coachVO = new CoachVO();
				coachVO.setCoach_Id(coach_Id);
				coachVO.setMember_Id(member_Id);
				coachVO.setCoach_Img(coach_Img);
				coachVO.setExperience(experience);
				coachVO.setIsCoach(isCoach);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO); // 含有輸入格式錯誤的memVO物件,也存入req
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "修改失敗!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateCoach.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				coachVO = coachSvc.updateCoach(coach_Id, member_Id, experience, isCoach, coach_Img);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("coachVO", coachVO);
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "修改成功!");
				String url = "/front-end/updateCoach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "修改失敗!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateCoach.jsp");
				failureView.forward(req, res);		
			}
		}
	}
}
