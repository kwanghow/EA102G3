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
		
		if("get_All_article_Mes".equals(action)) {  // ¨Ó¦ÛlistAllEmp.jspªº½Ğ¨D
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				
				ArtMesService artMesSvc = new ArtMesService();
				List<ArtMesVO> articleList = artMesSvc.getAllMesByArticleNo(action);
				
				String url = "/front-end/article/POST3.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??å?Ÿè?‰äº¤listOneEmp.jsp
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
		
		
		if ("getOne_For_Display".equals(action)) { // ä¾†è‡ªselect_page.jsp??„è?‹æ??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.±µ¨ü½Ğ¨D°Ñ¼Æ**********************/
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
				
				
				/***************************2.¶}©l¬d¸ß¸ê®Æ*****************************************/
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
				
				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // è³‡æ?™å?–å‡º??„empVO?‰©ä»¶ï?Œå?˜å…¥req
				req.setAttribute("mesList", mesList);
				String url = "/front-end/article/POST3.jsp";
				                         
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??å?Ÿè?‰äº¤listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR PAGE"+e.getMessage());
				errorMsgs.add("?„¡æ³•å?–å?—è?‡æ??:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
				
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
				
				System.out.println("article_no=:"+article_no);
				
				/***************************2.¶}©l¬d¸ß¸ê®Æ****************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesVO ArtMesVO = ArtMesSvc.getOneArt(article_no);
								
				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ************/
				req.setAttribute("ArtMesVO", ArtMesVO);         // ??ˆï‹ªï¿½î?“æ?ˆï¿½ï¿½î¡¼?Š¾ï¿½ï¿½?‚´mpVOï¿½ï§??ï¿½,?‘®î¦¶ï…¯req
				String url = "/front-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ï¿½ï¿½??ï¿½î¸„ï¿½?¢æ¼? update_emp_input.jsp
				successView.forward(req, res);

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				errorMsgs.add("µLªk¨ú±o­n­×§ïªº¸ê®Æ:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/articleList.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { //¨Ó¦Ûupdate_emp_input.jspªº½Ğ¨D

			System.out.println("servlet up");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?¥?”¶è«‹æ?‚å?ƒæ•¸-è¼¸å…¥? ¼å¼ç?„éŒ¯èª?**********************/				
				
						
				String msgNo = req.getParameter("msgNo");
				if (msgNo == null || msgNo.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¯d¨¥½s¸¹");
				} 
				String articleNo= req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹½s¸¹");
				} 
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J·|­û½s¸¹");
				} 
				
				java.sql.Date msgTime = null;
				try {
					msgTime = java.sql.Date.valueOf(req.getParameter("msgTime").trim());
				} catch (IllegalArgumentException e) {
					msgTime=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("½Ğ¿é¤J¯d¨¥®É¶¡");
				}
				
				String msgCont = req.getParameter("msgCont"); //³o¬q¤£½T©w«ç»ò¼g
				if (msgCont== null || msgCont.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¯d¨¥¤º®e");
				} 
				
			
						
				String memberName = null;
				try {
					memberName = new String(req.getParameter("memberName"));
				} catch (NumberFormatException e) {
					memberName = null;
					errorMsgs.add("·|­û¦WºÙ.");
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
					req.setAttribute("ArtMesVO", ArtMesVO); // §t¦³¿é¤J®æ¦¡¿ù»~ªºArtMesVOª«¥ó,¤]¦s¤Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp");
					failureView.forward(req, res);
					return; //??”ï?ï¿½?°??‰ï¿½î¡?
				}
				
				/***************************2.¶}©l­×§ï¸ê®Æ*****************************************/
				ArtMesService artMesService = new ArtMesService();
				ArtMesVO  = artMesService.updateArticle(ArtMesVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.­×§ï§¹¦¨,·Ç³ÆÂà¥æ(Send the Success view)*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // ¸ê®Æ®wupdate¦¨¥\«á,¥¿½TªºªºArtMesVOª«¥ó,¦s¤Jreq
				String url = "/front-end/article/POST3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?½? ¼??–ï¿½ï¿½î?ï¿½î¸ï¿½ï¿?,? §?¢æ¼±listOneEmp.jsp
				successView.forward(req, res);

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z*************************************/
			} catch (Exception e) {
				errorMsgs.add("­×§ï¸ê®Æ¥¢±Ñ:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // ¨Ó¦ÛaddEmp.jspªº½Ğ¨D 
        	
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.±µ¨ü½Ğ¨D°Ñ¼Æ-¿é¤J®æ¦¡ªº¿ù»~³B²z*************************/
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
				/***************************2.¶}©l·s¼W¸ê®Æ***************************************/
				Date msgTime = new Date(System.currentTimeMillis());
				ArtMesService mesSvc = new ArtMesService();
				ArtMesVO vo = new ArtMesVO();
				vo.setArticleNo(articleNo);
				vo.setMemNo(memNo);
				vo.setMsgCont(msgCont);
				vo.setMsgTime(msgTime);
				mesSvc.InsertArtMes(vo);
				
				/***************************3.·s¼W§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/
				res.getWriter().append("{"
						+ "\"message\":\"success!\""
						+ "}") ;
				res.addHeader("Content-Type", "json/text;charset=big5"); 
				res.getWriter().flush();
				return;

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append("{"
						+ "\"message\":\"ajax fail!\""
						+ "}") ;
				return;
			}
		}
		
		
		if ("delete".equals(action)) { // ??˜ï?ïŠ®listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.±µ¨ü½Ğ¨D°Ñ¼Æ***************************************/
				String msgNo = new String(req.getParameter("msgNo"));
				
				/***************************2.¶}©l§R°£¸ê®Æ***************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesSvc.DeletetArtMes(msgNo);
				
				/***************************3.§R°£§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/								
				String url = "/front-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// §R°£¦¨¥\«á¡AÂà¥æ¦^°e¥X§R°£ªº¨Ó·½ºô­¶
				successView.forward(req, res);
				
				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				errorMsgs.add("§R°£¸ê®Æ¥¢±Ñ:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
