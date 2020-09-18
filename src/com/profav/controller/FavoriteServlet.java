package com.profav.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.pro.model.ProductService;
import com.pro.model.ProVO;
import com.profav.model.*;

public class FavoriteServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("member_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				List<Product_FavoriteVO> FAVO = favSvc.getFavBymemno(str.substring(1));
				if (FAVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("FAVO", FAVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/product_favorite/listOneFav.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
//	

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				System.out.println("fav insert");
				String member_id = req.getParameter("member_id");
				String product_id =req.getParameter("product_id");
				System.out.println(member_id);
				System.out.println(product_id);
				Product_FavoriteVO FAVO = new Product_FavoriteVO();
				FAVO.setMember_id(member_id);
				FAVO.setProduct_id(product_id);
				
		
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("FAVO", FAVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/addFav.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				FAVO = favSvc.addFav(member_id, product_id);
				ProductService prosvc = new ProductService();
				ProVO proVO = prosvc.getOneProduct(product_id);
				req.setAttribute("proVO", proVO);
				System.out.println("fav_ins_off");
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/haoren/profavPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/list-all-product.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String favnum = req.getParameter("fav_num2");
				favnum.split(",");
				String[] favarray = favnum.split(",");
				String mem_no = favarray[0];
				String prod_no =favarray[1];
			
				
				/***************************2.開始刪除資料***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				favSvc.deleteFav(mem_no.substring(1),prod_no.substring(3));
				List<Product_FavoriteVO> list = favSvc.getFavBymemno(mem_no.substring(1));
				req.setAttribute("FAVO", list);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/product_favorite/listOneFav.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/listOneFav.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("cancel".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String member_id = req.getParameter("member_id");				
				String product_id =req.getParameter("product_id");
				System.out.println(member_id);
				System.out.println(product_id);
			
				
				/***************************2.開始刪除資料***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				favSvc.deleteFav(member_id,product_id);
				ProductService prosvc = new ProductService();
				ProVO proVO = prosvc.getOneProduct(member_id);
				req.setAttribute("proVO", proVO);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/haoren/profavPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/listAllFav.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}
}
