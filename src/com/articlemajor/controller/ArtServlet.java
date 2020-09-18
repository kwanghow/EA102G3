package com.articlemajor.controller;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ArtMes.model.ArtMesDAO;
import com.ArtMes.model.ArtMesVO;
import com.article.model.ArtService;
import com.article.model.ArtVO;
import com.articlecat.model.ArtCatDAO;
import com.articlecat.model.ArtCatVO;
import com.artilove.model.ArtILoveDAO;
import com.coach.model.CoachVO;
import com.mem.model.MemVO;


public class ArtServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		System.out.println(action);
		System.out.println(req.getParameter("article_no"));
		
		if("get_All_front_article".equals(action)) {  
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				ArtService artSvc = new ArtService();
				List<ArtVO> articleList = artSvc.getAllArticle();
				
				String url = "/front-end/article/articleList.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�交listOneEmp.jsp
				successView.forward(req, res);
				return;
				
			}catch(Exception e) {
				errorMsgs.add(":" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/articleList.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("get_All_back_article".equals(action)) {  // �Ӧ�listAllEmp.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				ArtService artSvc = new ArtService();
				List<ArtVO> articleList = artSvc.getAllArticle();
				
				String url = "/back-end/article/articleList.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??��?��?�交listOneEmp.jsp
				successView.forward(req, res);
				System.out.println("HI~I ");
				return;
				
			}catch(Exception e) {
				errorMsgs.add(":" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/articleList.jsp");
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
				String article_no = req.getParameter("article_no");
				if (article_no == null || (article_no.trim()).length() == 0) {
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
				ArtService artSvc = new ArtService();
				ArtVO artVO = artSvc.getOneArt(article_no);
				if (artVO == null) {
					System.out.println("no data");
					errorMsgs.add("no data");
				}
				ArtMesDAO dao = new ArtMesDAO();
				List<ArtMesVO>  mesList  = dao.getAll(article_no);
		
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/article/POST3.jsp");
					failureView.forward(req, res);
					return;//the programming disconnect
				}
				
				MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");

				boolean exist = false;
				ArtILoveDAO iloveDao = new ArtILoveDAO();
				if(memVO !=null && iloveDao.Select( article_no,memVO.getMember_Id())!= null){
					exist = true;
				}
				System.out.println(exist);

				/***************************3.�d�ߧ����A�ǳ����*************/
				req.setAttribute("artVO", artVO); // 資�?��?�出??�empVO?��件�?��?�入req
				req.setAttribute("articleMesList", mesList);
				req.setAttribute("exist", exist);
				req.setAttribute("memVO", memVO);

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
				
				System.out.println("ServletGetONE:"+article_no);
				
				/***************************2.�}�l�d�߸��****************************************/
				ArtService artSvc = new ArtService();
				ArtVO artVO = artSvc.getOneArt(article_no);
				ArtCatDAO artCatDAO=new ArtCatDAO();
				List<ArtCatVO> artcatlist = artCatDAO.getAll();
					//List<ArtMesVO>  mesList  = dao.getAll(article_no);			
				/***************************3.�d�ߧ����A�ǳ����************/
				req.setAttribute("artVO", artVO);         // ??���?��?���?����?��mpVO�??��,?��req
				req.setAttribute("artcatlist", artcatlist); 
				String url = "/back-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ��??���?���? update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_front_Update".equals(action)) { // 來自listAllEmp.jsp??��?��??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				String article_no = req.getParameter("article_no");
				System.out.println("ServletGetONE:"+article_no);
				
				/***************************2.�}�l�d�߸��****************************************/
				ArtService artSvc = new ArtService();
				ArtVO artVO = artSvc.getOneArt(article_no);
				ArtCatDAO artCatDAO=new ArtCatDAO();
				List<ArtCatVO> artcatlist = artCatDAO.getAll();
					//List<ArtMesVO>  mesList  = dao.getAll(article_no);			
				/***************************3.�d�ߧ����A�ǳ����************/
				req.setAttribute("artVO", artVO);         // ??���?��?���?����?��mpVO�??��,?��req
				req.setAttribute("artcatlist", artcatlist); 
				String url = "/front-end/article/addArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ��??���?���? update_emp_input.jsp
				successView.forward(req, res);
				return;

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { //�Ӧ�update_emp_input.jsp���ШD

			System.out.print("artNO" +req.getParameter("article_no"));
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
				
				
				String articleCatNo = req.getParameter("article_cat_no");
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("�п�J�峹���O�s��");
				} 
				String articleTitle= req.getParameter("article_title");
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("�п�J�峹���D");
				} 
				String articleTitleSub = req.getParameter("article_Title_Sub");
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("�п�J�峹�����D");
				} 
				String articleContent = req.getParameter("article_Content");
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("�п�J�峹���e");
				} 
				Integer articleReport = null;
				try {
				 articleReport = new Integer(req.getParameter("article_report"));
				} catch (NumberFormatException e) {
					articleReport = 0;
					errorMsgs.add("�w�s�п�J�Ʀr.");
				}
				
				
				
				ArtVO ArtVO = new ArtVO();	
				ArtVO.setArticleNo(articleNo);
				ArtVO.setArticleCatNo(articleCatNo);
				ArtVO.setArticleTitle(articleTitle);
				ArtVO.setArticleTitleSub(articleTitleSub);
				ArtVO.setArticleContent(articleContent);
				ArtVO.setArticleReport(articleReport);
				System.out.println(articleReport);
				
				System.out.println("articleCatNo=" + articleCatNo);
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
				ArtService artService = new ArtService();
				ArtVO  = artService.updateArticle(ArtVO);

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

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/***********************1.�����ШD�Ѽ�-��J�榡�����~�B�z*************************/
				String articleTitle = req.getParameter("articleTitle");
				
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("�峹���D���i����");
				}
				
				String articleTitleSub = req.getParameter("articleTitleSub").trim();
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("�峹�Ƽ��D���i����");
				}
				
				String articleCatNo = req.getParameter("articleCatNo").trim();
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("�峹�������i����");
				}
				
				String articleContent = req.getParameter("articleContent").trim();
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("�峹���e���i����");
				}
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher errView = req.getRequestDispatcher("/front-end/article/addArticle.jsp"); // ��?��?���?���?���漱listAllEmp.jsp
					errView.forward(req, res);
					return;
				}
				
				
				ArtVO  artvo= new ArtVO();
				artvo.setArticleCatNo(articleCatNo);
				artvo.setArticleTitleSub(articleTitleSub);
				artvo.setArticleTitle(articleTitle);
				artvo.setArticleContent(articleContent);
				artvo.setPostTime(new Date());
				CoachVO coachVO = (CoachVO)req.getSession().getAttribute("coachLogIn");
				MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");

				artvo.setArticleAuthor(memVO.getMem_Name());
				artvo.setMemId(memVO.getMember_Id());
				artvo.setArticleReport(0);

				//String memId = (String) req.getSession().getAttribute("memId");
				//MemVO mvo = new MemDAO().findByPrimaryKey(memId);
				//artvo.setMemId(memId);
				
				
				/***************************2.�}�l�s�W���***************************************/
				ArtService artSvc = new ArtService();
				artSvc.InsertArticle(artvo);
				/***************************3.�s�W�����A�ǳ����(Send the Success view)***********/
				String url = "/ArtServlet?action=get_All_front_article";
				
				req.setAttribute("articleList", artSvc.getAllArticle());
				RequestDispatcher successView = req.getRequestDispatcher(url); // ��?��?���?���?���漱listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("update_front".equals(action)) { // �Ӧ�addEmp.jsp���ШD 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/***********************1.�����ШD�Ѽ�-��J�榡�����~�B�z*************************/
				
				String articleNo = req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("�峹�������i����");
				}
				
				String articleTitle = req.getParameter("articleTitle");
				
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("�峹���D���i����");
				}
				
				String articleTitleSub = req.getParameter("articleTitleSub").trim();
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("�峹�Ƽ��D���i����");
				}
				
				String articleCatNo = req.getParameter("articleCatNo").trim();
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("�峹�������i����");
				}
				
				String articleContent = req.getParameter("articleContent").trim();
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("�峹���e���i����");
				}
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher errView = req.getRequestDispatcher("/front-end/article/addArticle.jsp"); // ��?��?���?���?���漱listAllEmp.jsp
					errView.forward(req, res);
					return;
				}
				
				
				ArtVO  artvo= new ArtVO();
				artvo.setArticleNo(articleNo);
				artvo.setArticleCatNo(articleCatNo);
				artvo.setArticleTitleSub(articleTitleSub);
				artvo.setArticleTitle(articleTitle);
				artvo.setArticleContent(articleContent);
				artvo.setPostTime(new Date());
				// TODO 2313213213
				CoachVO coachVO = (CoachVO)req.getSession().getAttribute("coachLogIn");
				MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");

				artvo.setArticleAuthor(memVO.getMem_Name());
				artvo.setMemId(memVO.getMember_Id());
				artvo.setArticleReport(0);

				//String memId = (String) req.getSession().getAttribute("memId");
				//MemVO mvo = new MemDAO().findByPrimaryKey(memId);
				//artvo.setMemId(memId);
				
				
				/***************************2.�}�l�s�W���***************************************/
				ArtService artSvc = new ArtService();
				artSvc.updateArticle(artvo);
				/***************************3.�s�W�����A�ǳ����(Send the Success view)***********/
				String url = "/front-end/article/articleList.jsp";
				req.setAttribute("articleList", artSvc.getAllArticle());
				
				//req.setAttribute("artVO", artvo);
				RequestDispatcher successView = req.getRequestDispatcher(url); // ��?��?���?���?���漱listAllEmp.jsp
				successView.forward(req, res);		
				return;
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
        }
		
		
		if ("delete".equals(action)) { // ??��?�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String articleNo = new String(req.getParameter("articleNo"));
				
				/***************************2.�}�l�R�����***************************************/
				ArtService ArtSvc = new ArtService();
				ArtSvc.DeletetArticle(articleNo);
				
				/***************************3.�R�������A�ǳ����(Send the Success view)***********/								
				String url = "/front-end/article/articleList.jsp";
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
