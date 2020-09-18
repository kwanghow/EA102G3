package com.product_category.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.pro.model.ProVO;
import com.product_category.model.CategoryService;
import com.product_category.model.Product_categoryVO;

public class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getProductByCategory".equals(action)) {
			try {
				//取得分類編號
				String category_Id = req.getParameter("category_Id");
				System.out.println("category_Id=" + category_Id);
				CategoryService categoryService = new CategoryService();
				
				//傳給前面
				Set<ProVO> set = categoryService.getProductsByCategory(category_Id);
				req.setAttribute("set", set);
				
				
				//轉交
				String url = "/front-end/product/get-product-by-category.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
}
}
