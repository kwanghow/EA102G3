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
		
		if("get_All_article_Mes".equals(action)) {  // �Ӧ�listAllEmp.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				
				ArtMesService artMesSvc = new ArtMesService();
				List<ArtMesVO> articleList = artMesSvc.getAllMesByArticleNo(action);
				
				String url = "/front-end/article/POST3.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�交listOneEmp.jsp
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
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ�**********************/
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
				
				
				/***************************2.�}�l�d�߸��*****************************************/
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
				
				/***************************3.�d�ߧ����A�ǳ����*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // 資�?��?�出??�empVO?��件�?��?�入req
				req.setAttribute("mesList", mesList);
				String url = "/front-end/article/POST3.jsp";
				                         
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�交listOneEmp.jsp
				successView.forward(req, res);
				return;
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR PAGE"+e.getMessage());
				errorMsgs.add("?��法�?��?��?��??:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
				
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
				
				System.out.println("article_no=:"+article_no);
				
				/***************************2.�}�l�d�߸��****************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesVO ArtMesVO = ArtMesSvc.getOneArt(article_no);
								
				/***************************3.�d�ߧ����A�ǳ����************/
				req.setAttribute("ArtMesVO", ArtMesVO);         // ??���?��?���?����?��mpVO�??��,?��req
				String url = "/front-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ��??���?���? update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/articleList.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { //�Ӧ�update_emp_input.jsp���ШD

			System.out.println("servlet up");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.?��?��請�?��?�數-輸入?��式�?�錯�?**********************/				
				
						
				String msgNo = req.getParameter("msgNo");
				if (msgNo == null || msgNo.trim().length() == 0) {
					errorMsgs.add("�п�J�d���s��");
				} 
				String articleNo= req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("�п�J�峹�s��");
				} 
				String memNo = req.getParameter("memNo");
				if (memNo == null || memNo.trim().length() == 0) {
					errorMsgs.add("�п�J�|���s��");
				} 
				
				java.sql.Date msgTime = null;
				try {
					msgTime = java.sql.Date.valueOf(req.getParameter("msgTime").trim());
				} catch (IllegalArgumentException e) {
					msgTime=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�d���ɶ�");
				}
				
				String msgCont = req.getParameter("msgCont"); //�o�q���T�w���g
				if (msgCont== null || msgCont.trim().length() == 0) {
					errorMsgs.add("�п�J�d�����e");
				} 
				
			
						
				String memberName = null;
				try {
					memberName = new String(req.getParameter("memberName"));
				} catch (NumberFormatException e) {
					memberName = null;
					errorMsgs.add("�|���W��.");
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
					req.setAttribute("ArtMesVO", ArtMesVO); // �t����J�榡���~��ArtMesVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp");
					failureView.forward(req, res);
					return; //??��?��?��??���?
				}
				
				/***************************2.�}�l�ק���*****************************************/
				ArtMesService artMesService = new ArtMesService();
				ArtMesVO  = artMesService.updateArticle(ArtMesVO);

				System.out.println("servlet up VO");
				
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("ArtMesVO", ArtMesVO); // ��Ʈwupdate���\��,���T����ArtMesVO����,�s�Jreq
				String url = "/front-end/article/POST3.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ?��?��??����?����?,?��?��漱listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/POST3.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD 
        	
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ�-��J�榡�����~�B�z*************************/
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
				/***************************2.�}�l�s�W���***************************************/
				Date msgTime = new Date(System.currentTimeMillis());
				ArtMesService mesSvc = new ArtMesService();
				ArtMesVO vo = new ArtMesVO();
				vo.setArticleNo(articleNo);
				vo.setMemNo(memNo);
				vo.setMsgCont(msgCont);
				vo.setMsgTime(msgTime);
				mesSvc.InsertArtMes(vo);
				
				/***************************3.�s�W�����A�ǳ����(Send the Success view)***********/
				res.getWriter().append("{"
						+ "\"message\":\"success!\""
						+ "}") ;
				res.addHeader("Content-Type", "json/text;charset=big5"); 
				res.getWriter().flush();
				return;

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				res.getWriter().append("{"
						+ "\"message\":\"ajax fail!\""
						+ "}") ;
				return;
			}
		}
		
		
		if ("delete".equals(action)) { // ??��?�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String msgNo = new String(req.getParameter("msgNo"));
				
				/***************************2.�}�l�R�����***************************************/
				ArtMesService ArtMesSvc = new ArtMesService();
				ArtMesSvc.DeletetArtMes(msgNo);
				
				/***************************3.�R�������A�ǳ����(Send the Success view)***********/								
				String url = "/front-end/article/listAllArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��A���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
