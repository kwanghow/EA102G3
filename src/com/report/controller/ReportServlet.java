package com.report.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.*;

import com.report.model.*;
import com.article.model.ArtDAO;
import com.article.model.ArtService;
import com.article.model.ArtVO;
import com.mem.model.MemDAO;

public class ReportServlet extends HttpServlet { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		req.getSession().setAttribute("memNo", "M001");
		
	   if ("insert".equals(action)) { // 來自addEmp.jsp的請求 
       	
			
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***********************1.接受請求參數-輸入格式的錯誤處理*************************/
					String Report = req.getParameter("Report");
					//String msgNoReg = "^$";
					if (Report == null || Report.trim().length() == 0) {
						errorMsgs.add("empty comment can't be inserted !");
					}
					
					String articleNo = req.getParameter("articleNo").trim();
					if (articleNo == null || articleNo.trim().length() == 0) {
						errorMsgs.add("articleNo can't be null");
					}
					System.out.println("articleNo=" + articleNo);
					int msgrepresult = 1;
					
					
					String memNo = ((String)req.getSession().getAttribute("memNo"));

					MemDAO memDao = new MemDAO();
					String memName = memDao.findByPrimaryKey(memNo).getMem_Name();
				
					
					if (memNo == null || memNo.trim().length() == 0) {
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
					ArtRepService RepSvc = new ArtRepService();
					ArtRepVO vo = new ArtRepVO();
					
					if(RepSvc.getOneArtRep(articleNo) != null ) {
						res.getWriter().append("the article has been reported !") ;
						return;
					}
					
					
					vo.setArticleNo(articleNo);
					vo.setMemNo(memNo);
					vo.setArticleReprea(Report);  ///todo
					vo.setMsgRepResult(msgrepresult);
					RepSvc.InsertArtRep(vo);
					
					ArtDAO artDao = new ArtDAO();
					ArtVO artVO = artDao.findByPrimaryKey(articleNo);
					artVO.setArticleReport(1);
					artDao.update(artVO);
					
					/***************************3.新增完成，準備轉交(Send the Success view)***********/
					res.getWriter().append("report success!") ;
					return;

					/*
					 * 		res.getWriter().append("{"
							+ "\"data\":{\"memName\":\""+memName+"\"}"
							+ "\"mesage\":\"success!\""
							+ "}") ;
					 * */

					/***************************其他可能的錯誤處理**********************************/
				} catch (Exception e) {
					e.printStackTrace();
					res.getWriter().append("{"
							+ "\"data\":{\"memName\":\"\"}"
							+ "\"mesage\":\"ajax fail!\""
							+ "}") ;
					res.getWriter().flush();
					return;
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
				if( article_no == null||article_no.trim().length() == 0) {
					errorMsgs.add("no aritcle No. !");
					
				}
				System.out.println("ServletGetONE:"+article_no);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("發生錯誤·轉交回去");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				/***************************2.開始查詢資料****************************************/
				ArtRepService artRepSvc = new ArtRepService();
				ArtRepVO artrepVO = artRepSvc.getOneArtRep(article_no);
				
				if( artrepVO == null) {
					errorMsgs.add("no aritcle data "+article_no+" found !");
				}
				System.out.println(errorMsgs);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("發生錯誤·轉交回去");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
							
				/***************************3.查詢完成，準備轉交************/
				req.setAttribute("artrepVO", artrepVO);         // ??���迎蕭�?��?�嚙踝蕭謘�?�橘蕭嚙�?�惴pVO嚙踝��??�嚙�,?�殉朱��盍eq
				String url = "/back-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 嚙踝蕭??�嚙踐��嚙�?�Ｘ�? update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add( e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				System.out.println("發生錯誤·從下面轉交回去");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { //來自update_emp_input.jsp的請求

			System.out.print("artRepNO" +req.getParameter("article_no"));
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?��?�嗉���?��?����-頛詨��?�澆���?���航�?**********************/				
				System.out.println("servlet up");
				
				
				
				String articleNo = req.getParameter("article_no");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("請輸入文章編號");
				} 
				System.out.println("articleNo--" + articleNo);
				
								
				Integer article_report = null;
				try {
					article_report = new Integer(req.getParameter("article_report"));
				} catch (NumberFormatException e) {
					article_report = 1;
					errorMsgs.add("請輸入檢舉狀態.");
				}
				System.out.println("article_report--" + article_report);
				
				
				
				ArtVO ArtVO = new ArtVO();	
				ArtVO.setArticleNo(articleNo);
				ArtVO.setArticleReport(article_report);
			
				
				System.out.println("articleNo=" + articleNo);
				System.out.println("servlet up");
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ArtVO", ArtVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
					failureView.forward(req, res);
					return; //??��?�嚙�?��??�嚙踐�?
				}
				
				/***************************2.開始修改資料*****************************************/
				ArtService artSvc = new ArtService();
				ArtVO = artSvc.updateReport(ArtVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("ArtVO", ArtVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?��?��??�嚙踝蕭�?�嚙踐��嚙踝�?,?��?�Ｘ摹listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/update_article.jsp");
				failureView.forward(req, res);
			}
		}
}
}
