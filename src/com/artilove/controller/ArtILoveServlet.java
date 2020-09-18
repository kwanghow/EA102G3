package com.artilove.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.articlecat.model.ArtCatDAO;
import com.articlecat.model.ArtCatVO;
import com.artilove.model.ArtILoveDAO;
import com.artilove.model.ArtILoveService;
import com.artilove.model.ArtILoveVO;
import com.mem.model.MemVO;



public class ArtILoveServlet extends HttpServlet {
	private static final ArtILoveVO ArtILoveVO = null;

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws ServletException, IOException{
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req,HttpServletResponse res) 
			throws ServletException, IOException{
	
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("get_all_fov".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
								
		try {
			//�T�w�|���b���O�_�T�{
			MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");
			if (memVO == null ) {
				errorMsgs.add("�п�J�|���s��");
			}
			//TODO �еn�J�|���e��
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
				return; //�{�����_
			}
			

			ArtILoveService faSvc = new ArtILoveService();
			List<ArtILoveVO> faVOlist = faSvc.getAllArtILoveByMemNo(memVO.getMember_Id());
			
			if (faVOlist == null  || faVOlist.isEmpty()) {
				errorMsgs.add("�d�L���");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/kevin/index.jsp");
				failureView.forward(req, res);
				return;
			}
			
			ArtCatDAO actDao = new ArtCatDAO();
			
			Map<String, String> artCatMap = new HashMap<String, String>();
			for(ArtCatVO vo : actDao.getAll()) {
				artCatMap.put(vo.getArticleCatNo(), vo.getArticleCatName());
			};
			
			req.getSession().setAttribute("artCatMap", artCatMap);
			req.getSession().setAttribute("faVOlist", faVOlist);
			String url = "/front-end/article/Love.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.add("�L�k���o��� : " + e.getMessage());
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/POST3.jsp");
			failureView.forward(req, res);
		}
	}	
		
	if("getOne_For_FAV".equals(action)) {
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
				
		String articleNo = null;
		
	try {
		//�T�w�|���b���O�_�T�{
		articleNo = req.getParameter("articleNo");
		if (articleNo == null || (articleNo.trim()).length() == 0 ) {
			errorMsgs.add("�п�J�|���s��");
		}
		//TODO �еn�J�|���e��
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/POST3.jsp");
			failureView.forward(req, res);
			return; //�{�����_
		}
		
		String memNo = null;
		
		try {
			memNo = new String("memNo");
		} catch(Exception e) {
			errorMsgs.add("�|���s���榡�����T");
		}
		//TODO �еn�J�|���e��
		if	(!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/articleList.jsp");
			failureView.forward(req, res);
			return; //�{�����_
		}
		ArtILoveService faSvc = new ArtILoveService();
		ArtILoveVO faVOlist = faSvc.getOneArt(articleNo,memNo);
		
		if (faVOlist == null) {
			errorMsgs.add("�d�L���");
		}
		
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/articleList.jsp");
			failureView.forward(req, res);
			return;
		}
		
		req.getSession().setAttribute("faVOlist", faVOlist);
		String url = "/front-end/article/POST3.jsp";
		RequestDispatcher successView = req.getRequestDispatcher(url);
		successView.forward(req, res);
		
	} catch (Exception e) {
		errorMsgs.add("�L�k���o��� : " + e.getMessage());
		RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/article/POST3.jsp");
		failureView.forward(req, res);
	}
}	
	
if ("insert".equals(action)) {
	
	List<String> errorMsgs = new LinkedList<String>();
	
	try {
	/***********************1.�����ШD�Ѽ�-��J�榡�����~�B�z*************************/
		String articleNo = req.getParameter("articleNo").trim();
		System.out.println("articleNo"+articleNo);
		
		if (articleNo == null || articleNo.trim().length() == 0) {
			errorMsgs.add("�峹�s�� : �ФŪť�");
		}
		
		
		MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");		
		if (memVO == null ) {
			errorMsgs.add("�|�����n�J�I");
		}
		
		if (!errorMsgs.isEmpty()) {
			res.setHeader("Content-Type", "text/plain; charset=utf-8");
			req.setAttribute("errorMsgs", errorMsgs);
			res.getWriter().append(errorMsgs.get(0));
			return;
		}
		
		ArtILoveVO faVO = new ArtILoveVO();
		faVO.setArticleNo(articleNo);
		faVO.setMemNo(memVO.getMember_Id());
		
//		req.setAttribute("faVO",faVO);
		
		/***************************2.�}�l�s�W���***************************************/
		ArtILoveService facSvc = new ArtILoveService();
		facSvc.InsertILove(faVO);
		System.out.println("after add");
		/***************************3.�s�W�����A�ǳ����(Send the Success view)***********/
		res.getWriter().append("success!");
		return;
		
		/***************************��L�i�઺���~�B�z**********************************/
	} catch (Exception e) {
		e.printStackTrace();
		errorMsgs.add(e.getMessage());
		res.getWriter().append(errorMsgs.get(0));
		return;
	}
}


if ("delete".equals(action)) {
	System.out.println("in delete");
	
	List<String> errorMsgs = new LinkedList<String>();
	req.setAttribute("errorMsgs", errorMsgs);
	
	try {
		/***************************1.�����ШD�Ѽ�***************************************/
		String articleNo = new String(req.getParameter("articleNo"));
		System.out.println(articleNo+","+articleNo);
		
		/***************************2.�}�l�R�����***************************************/
		ArtILoveService facSvc = new ArtILoveService();
		MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");
		if (memVO == null ) {
			errorMsgs.add("�|�����n�J�I");
		}
		
		if (!errorMsgs.isEmpty()) {
			res.setHeader("Content-Type", "text/plain; charset=utf-8");
			req.setAttribute("errorMsgs", errorMsgs);
			res.getWriter().append(errorMsgs.get(0));
			return;
		}
		
		facSvc.DeleteILove(memVO.getMember_Id() ,articleNo);
		res.getWriter().append("success!");

	} catch (Exception e) {
		res.getWriter().append("fail!");

		System.out.println(e.getMessage());

		}
	}
}
}




	
	


