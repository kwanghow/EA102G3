package com.ArtMes.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.ArtMes.model.ArtMesDAO;
import com.ArtMes.model.ArtMesService;
import com.ArtMes.model.ArtMesVO;
import com.mem.model.MemDAO;
import com.mem.model.MemVO;


public class ArtMesServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println(action);
		System.out.println("yes");
		req.getSession().setAttribute("memNo", "M001");
		
		if("get_All_article_Mes".equals(action)) {  // 來自listAllEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				
				ArtMesService artMesSvc = new ArtMesService();
				List<ArtMesVO> articleList = artMesSvc.getAllMesByArticleNo(action);
				
				String url = "/front-end/article/POST3.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�鈭子istOneEmp.jsp
				successView.forward(req, res);
				System.out.println("HI~I ");
				return;
				
			}catch(Exception e) {
				errorMsgs.add(":" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Display".equals(action)) { // 靘���泅elect_page.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接受請求參數**********************/
				String msg_no = req.getParameter("msg_no");
				if (msg_no == null || (msg_no.trim()).length() == 0) {
					errorMsgs.add("pls enter the article no");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp"); 
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
				
				/***************************2.開始查詢資料*****************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesVO ArtMesVO = ArtMesSvc.getOneArt(msg_no);
				if (ArtMesVO == null) {
					System.out.println("no data");
					errorMsgs.add("no data");
				}
				ArtMesDAO dao = new ArtMesDAO();
				List<ArtMesVO>  mesList  = dao.getAll(msg_no);
		
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
				/***************************3.查詢完成，準備轉交*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // 鞈��?��?����??�empVO?�拐辣�?��?���叵eq
				req.setAttribute("mesList", mesList);
				String url = "/front-end/article/POST3.jsp";
				                         
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�鈭子istOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR PAGE"+e.getMessage());
				errorMsgs.add("?�⊥���?��?��?��??:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
				
			}
			
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 靘���泓istAllEmp.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String article_no = req.getParameter("article_no");
				
				System.out.println("article_no=:"+article_no);
				
				/***************************2.開始查詢資料****************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesVO ArtMesVO = ArtMesSvc.getOneArt(article_no);
								
				/***************************3.查詢完成，準備轉交************/
				req.setAttribute("ArtMesVO", ArtMesVO);         // ??���迎蕭�?��?�嚙踝蕭謘�?�橘蕭嚙�?�惴pVO嚙踝��??�嚙�,?�殉朱��盍eq
				String url = "/front-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 嚙踝蕭??�嚙踐��嚙�?�Ｘ�? update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/articleList.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { //來自update_emp_input.jsp的請求

			System.out.println("servlet up");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?��?�嗉���?��?����-頛詨��?�澆���?���航�?**********************/				
				
						
				String msgNo = req.getParameter("msgNo");
				if (msgNo == null || msgNo.trim().length() == 0) {
					errorMsgs.add("請輸入留言編號");
				} 
				String articleNo= req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("請輸入文章編號");
				} 
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("請輸入會員編號");
				} 
				
				java.sql.Date msgTime = null;
				try {
					msgTime = java.sql.Date.valueOf(req.getParameter("msgTime").trim());
				} catch (IllegalArgumentException e) {
					msgTime=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入留言時間");
				}
				
				String msgCont = req.getParameter("msgCont"); //這段不確定怎麼寫
				if (msgCont== null || msgCont.trim().length() == 0) {
					errorMsgs.add("請輸入留言內容");
				} 
				
			
						
				String memberName = null;
				try {
					memberName = new String(req.getParameter("memberName"));
				} catch (NumberFormatException e) {
					memberName = null;
					errorMsgs.add("會員名稱.");
				}
				
				
				
				ArtMesVO ArtMesVO = new ArtMesVO();	
				ArtMesVO.setMsgNo(msgNo);
				ArtMesVO.setArticleNo(articleNo);
				ArtMesVO.setMemNo(memNo);
				ArtMesVO.setMsgTime(msgTime);
				ArtMesVO.setMsgCont(msgCont);
				ArtMesVO.setMemberName(memberName);
				System.out.println("servlet up");
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ArtMesVO", ArtMesVO); // 含有輸入格式錯誤的ArtMesVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp");
					failureView.forward(req, res);
					return; //??��?�嚙�?��??�嚙踐�?
				}
				
				/***************************2.開始修改資料*****************************************/
				ArtMesService artMesService = new ArtMesService();
				ArtMesVO  = artMesService.updateArticle(ArtMesVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // 資料庫update成功後,正確的的ArtMesVO物件,存入req
				String url = "/front-end/article/POST3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?��?��??�嚙踝蕭�?�嚙踐��嚙踝�?,?��?�Ｘ摹listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求 
        	
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接受請求參數-輸入格式的錯誤處理*************************/
				String msgCont = req.getParameter("msgCont");
				//String msgNoReg = "^$";
				if (msgCont == null || msgCont.trim().length() == 0) {
					res.getWriter().append("{"
							+ "\"message\":\"msgCont can't be null!\""
							+ "}") ;
					return;
				}
				
				String articleNo = req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					res.getWriter().append("{"
							+ "\"message\":\"articleNo can't be null!\""
							+ "}") ;
					return;
				}
				
				
				String memNo = ((String)req.getSession().getAttribute("memNo"));

				MemDAO memDao = new MemDAO();
				MemVO memvo = memDao.findByPrimaryKey(memNo);
			
				
				if (memvo == null) {
					errorMsgs.add("customer didn't log in!");
				}
				
				if(!errorMsgs.isEmpty()) {
					for(String mes:errorMsgs) {
						res.getWriter().append(mes) ;
					}
					res.getWriter().flush();
					return;
				}
				


				// Send the use back to the form, if there were errors		
				/***************************2.開始新增資料***************************************/
				Date msgTime = new Date(System.currentTimeMillis());
				ArtMesService mesSvc = new ArtMesService();
				ArtMesVO vo = new ArtMesVO();
				vo.setArticleNo(articleNo);
				vo.setMemNo(memNo);
				vo.setMsgCont(msgCont);
				vo.setMsgTime(msgTime);
				mesSvc.InsertArtMes(vo);
				
				/***************************3.新增完成，準備轉交(Send the Success view)***********/
				res.getWriter().append("{"
						+ "\"message\":\"success!\""
						+ "}") ;
				res.addHeader("Content-Type", "json/text;charset=big5"); 
				res.getWriter().flush();
				return;

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append("{"
						+ "\"message\":\"ajax fail!\""
						+ "}") ;
				return;
			}
		}
		
		
		if ("delete".equals(action)) { // ??��?���峽istAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接受請求參數***************************************/
				String msgNo = new String(req.getParameter("msgNo"));
				
				/***************************2.開始刪除資料***************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesSvc.DeletetArtMes(msgNo);
				
				/***************************3.刪除完成，準備轉交(Send the Success view)***********/								
				String url = "/front-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後，轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
