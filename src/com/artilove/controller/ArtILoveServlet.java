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
			//確定會員帳號是否確認
			MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");
			if (memVO == null ) {
				errorMsgs.add("請輸入會員編號");
			}
			//TODO 請登入會員畫面
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
				return; //程式中斷
			}
			

			ArtILoveService faSvc = new ArtILoveService();
			List<ArtILoveVO> faVOlist = faSvc.getAllArtILoveByMemNo(memVO.getMember_Id());
			
			if (faVOlist == null  || faVOlist.isEmpty()) {
				errorMsgs.add("查無資料");
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
			errorMsgs.add("無法取得資料 : " + e.getMessage());
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
		//確定會員帳號是否確認
		articleNo = req.getParameter("articleNo");
		if (articleNo == null || (articleNo.trim()).length() == 0 ) {
			errorMsgs.add("請輸入會員編號");
		}
		//TODO 請登入會員畫面
		if (!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/POST3.jsp");
			failureView.forward(req, res);
			return; //程式中斷
		}
		
		String memNo = null;
		
		try {
			memNo = new String("memNo");
		} catch(Exception e) {
			errorMsgs.add("會員編號格式不正確");
		}
		//TODO 請登入會員畫面
		if	(!errorMsgs.isEmpty()) {
			RequestDispatcher failureView = req
					.getRequestDispatcher("/front-end/article/articleList.jsp");
			failureView.forward(req, res);
			return; //程式中斷
		}
		ArtILoveService faSvc = new ArtILoveService();
		ArtILoveVO faVOlist = faSvc.getOneArt(articleNo,memNo);
		
		if (faVOlist == null) {
			errorMsgs.add("查無資料");
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
		errorMsgs.add("無法取得資料 : " + e.getMessage());
		RequestDispatcher failureView = req
				.getRequestDispatcher("/front-end/article/POST3.jsp");
		failureView.forward(req, res);
	}
}	
	
if ("insert".equals(action)) {
	
	List<String> errorMsgs = new LinkedList<String>();
	
	try {
	/***********************1.接受請求參數-輸入格式的錯誤處理*************************/
		String articleNo = req.getParameter("articleNo").trim();
		System.out.println("articleNo"+articleNo);
		
		if (articleNo == null || articleNo.trim().length() == 0) {
			errorMsgs.add("文章編號 : 請勿空白");
		}
		
		
		MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");		
		if (memVO == null ) {
			errorMsgs.add("會員未登入！");
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
		
		/***************************2.開始新增資料***************************************/
		ArtILoveService facSvc = new ArtILoveService();
		facSvc.InsertILove(faVO);
		System.out.println("after add");
		/***************************3.新增完成，準備轉交(Send the Success view)***********/
		res.getWriter().append("success!");
		return;
		
		/***************************其他可能的錯誤處理**********************************/
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
		/***************************1.接受請求參數***************************************/
		String articleNo = new String(req.getParameter("articleNo"));
		System.out.println(articleNo+","+articleNo);
		
		/***************************2.開始刪除資料***************************************/
		ArtILoveService facSvc = new ArtILoveService();
		MemVO memVO = (MemVO)req.getSession().getAttribute("memLogIn");
		if (memVO == null ) {
			errorMsgs.add("會員未登入！");
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




	
	


