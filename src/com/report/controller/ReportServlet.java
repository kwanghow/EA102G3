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
		
	   if ("insert".equals(action)) { // ¨Ó¦ÛaddEmp.jspªº½Ğ¨D 
       	
			
				List<String> errorMsgs = new LinkedList<String>();
				// Store this set in the request scope, in case we need to
				// send the ErrorPage view.
				req.setAttribute("errorMsgs", errorMsgs);

				try {
					/***********************1.±µ¨ü½Ğ¨D°Ñ¼Æ-¿é¤J®æ¦¡ªº¿ù»~³B²z*************************/
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
					/***************************2.¶}©l·s¼W¸ê®Æ***************************************/
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
					
					/***************************3.·s¼W§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/
					res.getWriter().append("report success!") ;
					return;

					/*
					 * 		res.getWriter().append("{"
							+ "\"data\":{\"memName\":\""+memName+"\"}"
							+ "\"mesage\":\"success!\""
							+ "}") ;
					 * */

					/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
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
	
	   
	   
	   
		if ("getOne_For_Update".equals(action)) { // ä¾†è‡ªlistAllEmp.jsp??„è?‹æ??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.±µ¦¬½Ğ¨D°Ñ¼Æ****************************************/
				String article_no = req.getParameter("article_no");
				if( article_no == null||article_no.trim().length() == 0) {
					errorMsgs.add("no aritcle No. !");
					
				}
				System.out.println("ServletGetONE:"+article_no);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("µo¥Í¿ù»~¡PÂà¥æ¦^¥h");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				/***************************2.¶}©l¬d¸ß¸ê®Æ****************************************/
				ArtRepService artRepSvc = new ArtRepService();
				ArtRepVO artrepVO = artRepSvc.getOneArtRep(article_no);
				
				if( artrepVO == null) {
					errorMsgs.add("no aritcle data "+article_no+" found !");
				}
				System.out.println(errorMsgs);

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp"); 
					System.out.println("µo¥Í¿ù»~¡PÂà¥æ¦^¥h");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
							
				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ************/
				req.setAttribute("artrepVO", artrepVO);         // ??ˆï‹ªï¿½î?“æ?ˆï¿½ï¿½î¡¼?Š¾ï¿½ï¿½?‚´mpVOï¿½ï§??ï¿½,?‘®î¦¶ï…¯req
				String url = "/back-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ï¿½ï¿½??ï¿½î¸„ï¿½?¢æ¼? update_emp_input.jsp
				successView.forward(req, res);

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add( e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				System.out.println("µo¥Í¿ù»~¡P±q¤U­±Âà¥æ¦^¥h");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) { //¨Ó¦Ûupdate_emp_input.jspªº½Ğ¨D

			System.out.print("artRepNO" +req.getParameter("article_no"));
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?¥?”¶è«‹æ?‚å?ƒæ•¸-è¼¸å…¥? ¼å¼ç?„éŒ¯èª?**********************/				
				System.out.println("servlet up");
				
				
				
				String articleNo = req.getParameter("article_no");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹½s¸¹");
				} 
				System.out.println("articleNo--" + articleNo);
				
								
				Integer article_report = null;
				try {
					article_report = new Integer(req.getParameter("article_report"));
				} catch (NumberFormatException e) {
					article_report = 1;
					errorMsgs.add("½Ğ¿é¤JÀËÁ|ª¬ºA.");
				}
				System.out.println("article_report--" + article_report);
				
				
				
				ArtVO ArtVO = new ArtVO();	
				ArtVO.setArticleNo(articleNo);
				ArtVO.setArticleReport(article_report);
			
				
				System.out.println("articleNo=" + articleNo);
				System.out.println("servlet up");
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ArtVO", ArtVO); // §t¦³¿é¤J®æ¦¡¿ù»~ªºempVOª«¥ó,¤]¦s¤Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
					failureView.forward(req, res);
					return; //??”ï?ï¿½?°??‰ï¿½î¡?
				}
				
				/***************************2.¶}©l­×§ï¸ê®Æ*****************************************/
				ArtService artSvc = new ArtService();
				ArtVO = artSvc.updateReport(ArtVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.­×§ï§¹¦¨,·Ç³ÆÂà¥æ(Send the Success view)*************/
				req.setAttribute("ArtVO", ArtVO); // ¸ê®Æ®wupdate¦¨¥\«á,¥¿½TªºªºempVOª«¥ó,¦s¤Jreq
				String url = "/back-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?½? ¼??–ï¿½ï¿½î?ï¿½î¸ï¿½ï¿?,? §?¢æ¼±listOneEmp.jsp
				successView.forward(req, res);

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("­×§ï¸ê®Æ¥¢±Ñ:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/update_article.jsp");
				failureView.forward(req, res);
			}
		}
}
}
