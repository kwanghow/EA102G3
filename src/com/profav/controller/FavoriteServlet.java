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
		
		
		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String str = req.getParameter("member_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�|���s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				List<Product_FavoriteVO> FAVO = favSvc.getFavBymemno(str.substring(1));
				if (FAVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
					failureView.forward(req, res);
					return;//�{�����_
				}
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				req.setAttribute("FAVO", FAVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/front-end/product_favorite/listOneFav.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
//	

        if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
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
					req.setAttribute("FAVO", FAVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/product_favorite/addFav.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				FAVO = favSvc.addFav(member_id, product_id);
				ProductService prosvc = new ProductService();
				ProVO proVO = prosvc.getOneProduct(product_id);
				req.setAttribute("proVO", proVO);
				System.out.println("fav_ins_off");
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/front-end/haoren/profavPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/list-all-product.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String favnum = req.getParameter("fav_num2");
				favnum.split(",");
				String[] favarray = favnum.split(",");
				String mem_no = favarray[0];
				String prod_no =favarray[1];
			
				
				/***************************2.�}�l�R�����***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				favSvc.deleteFav(mem_no.substring(1),prod_no.substring(3));
				List<Product_FavoriteVO> list = favSvc.getFavBymemno(mem_no.substring(1));
				req.setAttribute("FAVO", list);
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/front-end/product_favorite/listOneFav.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/listOneFav.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("cancel".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String member_id = req.getParameter("member_id");				
				String product_id =req.getParameter("product_id");
				System.out.println(member_id);
				System.out.println(product_id);
			
				
				/***************************2.�}�l�R�����***************************************/
				Product_FavoriteService favSvc = new Product_FavoriteService();
				favSvc.deleteFav(member_id,product_id);
				ProductService prosvc = new ProductService();
				ProVO proVO = prosvc.getOneProduct(member_id);
				req.setAttribute("proVO", proVO);
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/front-end/haoren/profavPage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/product_favorite/listAllFav.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}
}
