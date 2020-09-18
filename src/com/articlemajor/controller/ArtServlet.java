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
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??å?Ÿè?‰äº¤listOneEmp.jsp
				successView.forward(req, res);
				return;
				
			}catch(Exception e) {
				errorMsgs.add(":" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/articleList.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("get_All_back_article".equals(action)) {  // ¨Ó¦ÛlistAllEmp.jspªº½Ğ¨D
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				ArtService artSvc = new ArtService();
				List<ArtVO> articleList = artSvc.getAllArticle();
				
				String url = "/back-end/article/articleList.jsp";
				
				req.setAttribute("articleList", articleList);
				
				
				System.out.println("-------------------------------");
				RequestDispatcher successView = req.getRequestDispatcher(url); // ??å?Ÿè?‰äº¤listOneEmp.jsp
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
		
		
		if ("getOne_For_Display".equals(action)) { // ä¾†è‡ªselect_page.jsp??„è?‹æ??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.±µ¨ü½Ğ¨D°Ñ¼Æ**********************/
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
				
				
				/***************************2.¶}©l¬d¸ß¸ê®Æ*****************************************/
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

				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ*************/
				req.setAttribute("artVO", artVO); // è³‡æ?™å?–å‡º??„empVO?‰©ä»¶ï?Œå?˜å…¥req
				req.setAttribute("articleMesList", mesList);
				req.setAttribute("exist", exist);
				req.setAttribute("memVO", memVO);

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
				
				System.out.println("ServletGetONE:"+article_no);
				
				/***************************2.¶}©l¬d¸ß¸ê®Æ****************************************/
				ArtService artSvc = new ArtService();
				ArtVO artVO = artSvc.getOneArt(article_no);
				ArtCatDAO artCatDAO=new ArtCatDAO();
				List<ArtCatVO> artcatlist = artCatDAO.getAll();
					//List<ArtMesVO>  mesList  = dao.getAll(article_no);			
				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ************/
				req.setAttribute("artVO", artVO);         // ??ˆï‹ªï¿½î?“æ?ˆï¿½ï¿½î¡¼?Š¾ï¿½ï¿½?‚´mpVOï¿½ï§??ï¿½,?‘®î¦¶ï…¯req
				req.setAttribute("artcatlist", artcatlist); 
				String url = "/back-end/article/update_article.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ï¿½ï¿½??ï¿½î¸„ï¿½?¢æ¼? update_emp_input.jsp
				successView.forward(req, res);

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				errorMsgs.add("µLªk¨ú±o­n­×§ïªº¸ê®Æ:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_front_Update".equals(action)) { // ä¾†è‡ªlistAllEmp.jsp??„è?‹æ??

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.±µ¦¬½Ğ¨D°Ñ¼Æ****************************************/
				String article_no = req.getParameter("article_no");
				System.out.println("ServletGetONE:"+article_no);
				
				/***************************2.¶}©l¬d¸ß¸ê®Æ****************************************/
				ArtService artSvc = new ArtService();
				ArtVO artVO = artSvc.getOneArt(article_no);
				ArtCatDAO artCatDAO=new ArtCatDAO();
				List<ArtCatVO> artcatlist = artCatDAO.getAll();
					//List<ArtMesVO>  mesList  = dao.getAll(article_no);			
				/***************************3.¬d¸ß§¹¦¨¡A·Ç³ÆÂà¥æ************/
				req.setAttribute("artVO", artVO);         // ??ˆï‹ªï¿½î?“æ?ˆï¿½ï¿½î¡¼?Š¾ï¿½ï¿½?‚´mpVOï¿½ï§??ï¿½,?‘®î¦¶ï…¯req
				req.setAttribute("artcatlist", artcatlist); 
				String url = "/front-end/article/addArticle.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ï¿½ï¿½??ï¿½î¸„ï¿½?¢æ¼? update_emp_input.jsp
				successView.forward(req, res);
				return;

				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				errorMsgs.add("µLªk¨ú±o­n­×§ïªº¸ê®Æ:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { //¨Ó¦Ûupdate_emp_input.jspªº½Ğ¨D

			System.out.print("artNO" +req.getParameter("article_no"));
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
				
				
				String articleCatNo = req.getParameter("article_cat_no");
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹Ãş§O½s¸¹");
				} 
				String articleTitle= req.getParameter("article_title");
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹¼ĞÃD");
				} 
				String articleTitleSub = req.getParameter("article_Title_Sub");
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹¦¸¼ĞÃD");
				} 
				String articleContent = req.getParameter("article_Content");
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("½Ğ¿é¤J¤å³¹¤º®e");
				} 
				Integer articleReport = null;
				try {
				 articleReport = new Integer(req.getParameter("article_report"));
				} catch (NumberFormatException e) {
					articleReport = 0;
					errorMsgs.add("®w¦s½Ğ¿é¤J¼Æ¦r.");
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
					req.setAttribute("ArtVO", ArtVO); // §t¦³¿é¤J®æ¦¡¿ù»~ªºempVOª«¥ó,¤]¦s¤Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/article/listAllArticle.jsp");
					failureView.forward(req, res);
					return; //??”ï?ï¿½?°??‰ï¿½î¡?
				}
				
				/***************************2.¶}©l­×§ï¸ê®Æ*****************************************/
				ArtService artService = new ArtService();
				ArtVO  = artService.updateArticle(ArtVO);

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

        if ("insert".equals(action)) { // ¨Ó¦ÛaddEmp.jspªº½Ğ¨D 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/***********************1.±µ¨ü½Ğ¨D°Ñ¼Æ-¿é¤J®æ¦¡ªº¿ù»~³B²z*************************/
				String articleTitle = req.getParameter("articleTitle");
				
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("¤å³¹¼ĞÃD¤£¥i¬°ªÅ");
				}
				
				String articleTitleSub = req.getParameter("articleTitleSub").trim();
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("¤å³¹°Æ¼ĞÃD¤£¥i¬°ªÅ");
				}
				
				String articleCatNo = req.getParameter("articleCatNo").trim();
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("¤å³¹¤ÀÃş¤£¥i¬°ªÅ");
				}
				
				String articleContent = req.getParameter("articleContent").trim();
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("¤å³¹¤º®e¤£¥i¬°ªÅ");
				}
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher errView = req.getRequestDispatcher("/front-end/article/addArticle.jsp"); // ï¿½î?‡æ?“îµ¥ï¿½î?ï¿½î¸ï¿½?—½ï¿½ï¢æ¼±listAllEmp.jsp
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
				
				
				/***************************2.¶}©l·s¼W¸ê®Æ***************************************/
				ArtService artSvc = new ArtService();
				artSvc.InsertArticle(artvo);
				/***************************3.·s¼W§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/
				String url = "/ArtServlet?action=get_All_front_article";
				
				req.setAttribute("articleList", artSvc.getAllArticle());
				RequestDispatcher successView = req.getRequestDispatcher(url); // ï¿½î?‡æ?“îµ¥ï¿½î?ï¿½î¸ï¿½?—½ï¿½ï¢æ¼±listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
		}
        
        if ("update_front".equals(action)) { // ¨Ó¦ÛaddEmp.jspªº½Ğ¨D 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.

			try {
				/***********************1.±µ¨ü½Ğ¨D°Ñ¼Æ-¿é¤J®æ¦¡ªº¿ù»~³B²z*************************/
				
				String articleNo = req.getParameter("articleNo");
				if (articleNo == null || articleNo.trim().length() == 0) {
					errorMsgs.add("¤å³¹¤ÀÃş¤£¥i¬°ªÅ");
				}
				
				String articleTitle = req.getParameter("articleTitle");
				
				if (articleTitle == null || articleTitle.trim().length() == 0) {
					errorMsgs.add("¤å³¹¼ĞÃD¤£¥i¬°ªÅ");
				}
				
				String articleTitleSub = req.getParameter("articleTitleSub").trim();
				if (articleTitleSub == null || articleTitleSub.trim().length() == 0) {
					errorMsgs.add("¤å³¹°Æ¼ĞÃD¤£¥i¬°ªÅ");
				}
				
				String articleCatNo = req.getParameter("articleCatNo").trim();
				if (articleCatNo == null || articleCatNo.trim().length() == 0) {
					errorMsgs.add("¤å³¹¤ÀÃş¤£¥i¬°ªÅ");
				}
				
				String articleContent = req.getParameter("articleContent").trim();
				if (articleContent == null || articleContent.trim().length() == 0) {
					errorMsgs.add("¤å³¹¤º®e¤£¥i¬°ªÅ");
				}
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", errorMsgs);
					RequestDispatcher errView = req.getRequestDispatcher("/front-end/article/addArticle.jsp"); // ï¿½î?‡æ?“îµ¥ï¿½î?ï¿½î¸ï¿½?—½ï¿½ï¢æ¼±listAllEmp.jsp
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
				
				
				/***************************2.¶}©l·s¼W¸ê®Æ***************************************/
				ArtService artSvc = new ArtService();
				artSvc.updateArticle(artvo);
				/***************************3.·s¼W§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/
				String url = "/front-end/article/articleList.jsp";
				req.setAttribute("articleList", artSvc.getAllArticle());
				
				//req.setAttribute("artVO", artvo);
				RequestDispatcher successView = req.getRequestDispatcher(url); // ï¿½î?‡æ?“îµ¥ï¿½î?ï¿½î¸ï¿½?—½ï¿½ï¢æ¼±listAllEmp.jsp
				successView.forward(req, res);		
				return;
				
				/***************************¨ä¥L¥i¯àªº¿ù»~³B²z**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/article/addArticle.jsp");
				failureView.forward(req, res);
			}
        }
		
		
		if ("delete".equals(action)) { // ??˜ï?ïŠ®listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.±µ¨ü½Ğ¨D°Ñ¼Æ***************************************/
				String articleNo = new String(req.getParameter("articleNo"));
				
				/***************************2.¶}©l§R°£¸ê®Æ***************************************/
				ArtService ArtSvc = new ArtService();
				ArtSvc.DeletetArticle(articleNo);
				
				/***************************3.§R°£§¹¦¨¡A·Ç³ÆÂà¥æ(Send the Success view)***********/								
				String url = "/front-end/article/articleList.jsp";
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
