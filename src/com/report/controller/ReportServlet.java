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
		
	   if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD 
       	
			
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***********************1.�����ШD�Ѽ�-��J�榡�����~�B�z*************************/
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
					/***************************2.�}�l�s�W���***************************************/
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
					
					/***************************3.�s�W�����A�ǳ����(Send the Success view)***********/
					res.getWriter().append("report success!") ;
					return;

					/*
					 * 		res.getWriter().append("{"
							+ "\"data\":{\"memName\":\""+memName+"\"}"
							+ "\"mesage\":\"success!\""
							+ "}") ;
					 * */

					/***************************��L�i�઺���~�B�z**********************************/
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
	
	   
	   
	   
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String article_no = req.getParameter("article_no");
				if( article_no == null||article_no.trim().length() == 0) {
					errorMsgs.add("no aritcle No. !");
					
				}
				System.out.println("ServletGetONE:"+article_no);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("�o�Ϳ��~�P���^�h");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				/***************************2.�}�l�d�߸��****************************************/
				ArtRepService artRepSvc = new ArtRepService();
				ArtRepVO artrepVO = artRepSvc.getOneArtRep(article_no);
				
				if( artrepVO == null) {
					errorMsgs.add("no aritcle data "+article_no+" found !");
				}
				System.out.println(errorMsgs);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("�o�Ϳ��~�P���^�h");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
							
				/***************************3.�d�ߧ����A�ǳ����************/
				req.setAttribute("artrepVO", artrepVO);         // ??���?��?���?����?��mpVO�??��,?��req
				String url = "/back-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ��??���?���? update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add( e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				System.out.println("�o�Ϳ��~�P�q�U�����^�h");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { //�Ӧ�update_emp_input.jsp���ШD

			System.out.print("artRepNO" +req.getParameter("article_no"));
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?��?��請�?��?�數-輸入?��式�?�錯�?**********************/				
				System.out.println("servlet up");
				
				
				
				String articleNo = req.getParameter("article_no");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("�п�J�峹�s��");
				} 
				System.out.println("articleNo--" + articleNo);
				
								
				Integer article_report = null;
				try {
					article_report = new Integer(req.getParameter("article_report"));
				} catch (NumberFormatException e) {
					article_report = 1;
					errorMsgs.add("�п�J���|���A.");
				}
				System.out.println("article_report--" + article_report);
				
				
				
				ArtVO ArtVO = new ArtVO();	
				ArtVO.setArticleNo(articleNo);
				ArtVO.setArticleReport(article_report);
			
				
				System.out.println("articleNo=" + articleNo);
				System.out.println("servlet up");
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ArtVO", ArtVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
					failureView.forward(req, res);
					return; //??��?��?��??���?
				}
				
				/***************************2.�}�l�ק���*****************************************/
				ArtService artSvc = new ArtService();
				ArtVO = artSvc.updateReport(ArtVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("ArtVO", ArtVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/back-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?��?��??����?����?,?��?��漱listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/update_article.jsp");
				failureView.forward(req, res);
			}
		}
}
}
