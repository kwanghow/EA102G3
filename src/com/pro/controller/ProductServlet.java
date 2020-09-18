package com.pro.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.pro.model.ProductService;
import com.pro.model.ProVO;

@MultipartConfig(fileSizeThreshold = 1920 * 1080, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		//用類別編號找商品
		if ("getOneCat_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String Category_Id = req.getParameter("Category_Id");
				System.out.println("getOneCat"+req.getParameter("Category_Id"));
				if (Category_Id == null || (Category_Id.trim()).length() == 0) {
					errorMsgs.add("請輸入類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}				
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService ProSvc = new ProductService();
				List<ProVO> list = ProSvc.getAllByCat(Category_Id);				
				if (list == null) {
					errorMsgs.add("查無資料");
				}				
				for (ProVO aProduct : list) {	
					int Status = aProduct.getProduct_Status();
				
				if(Status==1) {
					errorMsgs.add("此商品已下架");
				}
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list); // 資料庫取出的empVO物件,存入req
				System.out.println("SERVLET 已存List進去");
				String url = "/back-end/haoren/listCatPro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		//找單一商品
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String Product_Id = req.getParameter("Product_Id");
				System.out.println("************" + Product_Id + "*********************");
				
				/***************************2.開始查詢資料****************************************/
				ProductService productService = new ProductService();
				ProVO ProVO = productService.getOneProduct(Product_Id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("ProVO", ProVO);  
				String url = "/back-end/haoren/back-pro-update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/back-pro-list-all.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				System.out.println("到達SEVLET UPD");
				
				String Product_Id = req.getParameter("Product_Id");
				if (Product_Id == null || Product_Id.trim().length() == 0) {
					errorMsgs.add("商品ID");
				}
				System.out.println("Product_IdUP = " + Product_Id);
				
				
				String Category_Id = req.getParameter("Category_Id");
				if (Category_Id == null || Category_Id.trim().length() == 0) {
					errorMsgs.add("請選擇商品分類");
				}
				System.out.println("Category_IdUP = " + Category_Id);
				
				
				String Product_Name = req.getParameter("Product_Name");
				System.out.println("Product_NameUP = " + Product_Name);
				
				
				Integer Product_Price = null;
				try {
					Product_Price = new Integer(req.getParameter("Product_Price").trim());
				} catch (NumberFormatException e) {
					Product_Price = 0;
					errorMsgs.add("價格請輸入數字.");
				}
				System.out.println("Product_PriceUP = " + Product_Price);
				
				
				String Product_Detail = req.getParameter("Product_Detail");
				if (Product_Detail == null || Product_Detail.trim().length() == 0) {
					errorMsgs.add("請輸入商品內容.");
				}
				System.out.println("Product_DetailUP = " + Product_Detail);		
				
				
				Integer Product_Stock = null;
				try {
					Product_Stock = new Integer(req.getParameter("Product_Stock").trim());
				} catch (NumberFormatException e) {
					Product_Stock = 0;
					errorMsgs.add("庫存請輸入數字.");
				}
				System.out.println("Product_StockUPUP = " + Product_Stock);
				
				
				String Product_Brand = req.getParameter("Product_Brand");
				if (Product_Brand == null || Product_Brand.trim().length() == 0) {
					errorMsgs.add("請選擇品牌");
				}
				System.out.println("Product_BrandUP = " + Product_Brand);
				
				Integer Product_Hot = null;
				try {
					Product_Hot = new Integer(req.getParameter("Product_Hot").trim());
				} catch (NumberFormatException e) {
					Product_Hot = 0;
					errorMsgs.add("熱門度請輸入0or1.");
				}
				System.out.println("Product_HotUP = " + Product_Hot);
				
				Integer Product_Status = null;
				
				try {
					Product_Status = new Integer(req.getParameter("Product_Status").trim());
					System.out.println("Product_StatusUP = " + Product_Status);
				} catch (NumberFormatException e) {
					Product_Status = 1;
					errorMsgs.add("狀態碼請輸入0or1.");
				}
				
	
				//抓圖片
				byte[] Product_Lpic = null;
				byte[] Product_Lpic1 = null;
				byte[] Product_Spic = null;
				
				Part part_Lpic = req.getPart("Product_Lpic");			
				InputStream is_Lpic = part_Lpic.getInputStream();
				if(is_Lpic.available()!=0) {
				byte[] buffer1 = new byte[is_Lpic.available()];							
				is_Lpic.read(buffer1);
				Product_Lpic = ImageUtil.shrink(buffer1, 600);
				} else {
					ProductService proService = new ProductService();
					Product_Lpic = proService.getOneProduct(Product_Id).getProduct_Lpic();
				}
				
				
				
				
				
				Part part_Lpic1 = req.getPart("Product_Lpic1");
				InputStream is_Lpic1 = part_Lpic1.getInputStream();
				if(is_Lpic1.available()!=0) {
					byte[] buffer2 = new byte[is_Lpic1.available()];							
					is_Lpic1.read(buffer2);
					Product_Lpic1 = ImageUtil.shrink(buffer2, 600);
					} else {
						ProductService proService = new ProductService();
						Product_Lpic1 = proService.getOneProduct(Product_Id).getProduct_Lpic1();
					}
				
				Part part_Spic = req.getPart("Product_Spic");
				InputStream is_Spic = part_Spic.getInputStream();
				if(is_Spic.available()!=0) {
					byte[] buffer3 = new byte[is_Spic.available()];							
					is_Spic.read(buffer3);
					Product_Spic = ImageUtil.shrink(buffer3, 600);

					} else {
						ProductService proService = new ProductService();
						Product_Spic = proService.getOneProduct(Product_Id).getProduct_Spic();
					}
				
				
				ProVO ProVO = new ProVO();	
				ProVO.setProduct_Id(Product_Id);
				ProVO.setCategory_Id(Category_Id);
				ProVO.setProduct_Name(Product_Name);
				ProVO.setProduct_Price(Product_Price);
				ProVO.setProduct_Detail(Product_Detail);				
				ProVO.setProduct_Status(Product_Status);
				ProVO.setProduct_Stock(Product_Stock);
				ProVO.setProduct_Brand(Product_Brand);
				ProVO.setProduct_Hot(Product_Hot);
				ProVO.setProduct_Lpic(Product_Lpic);
				ProVO.setProduct_Lpic1(Product_Lpic1);
				ProVO.setProduct_Spic(Product_Spic);
//				System.out.println("庫存 : " +"\t" + ProVO.getProduct_Stock());
				System.out.println("商品編號上 : " + "\t" + ProVO.getProduct_Id());
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ProVO", ProVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/haoren/back-pro-update.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productService = new ProductService();
				ProVO = productService.ProUpdate(Product_Id,Category_Id,Product_Name,Product_Price,Product_Detail,Product_Status,Product_Stock,Product_Brand,Product_Hot,Product_Lpic,Product_Lpic1,Product_Spic);
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("ProVO",ProVO);
				String url = "/back-end/haoren/back-pro-list-all.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/back-pro-update.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
        	Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
			
			
			String Product_Name = req.getParameter("Product_Name");
			if (Product_Name == null || Product_Name.trim().length() == 0) {
				errorMsgs.put("Product_Name","請輸入商品名稱.");
			}
			System.out.println("Product_NameSV = " + Product_Name);
			
			
			Integer Product_Price = null;
			try {
				Product_Price = new Integer(req.getParameter("Product_Price").trim());
			} catch (NumberFormatException e) {
				Product_Price = 0;
				errorMsgs.put("Product_Price","價格請輸入數字.");
			}
			
			Integer Product_Stock = null;
			try {
				Product_Stock = new Integer(req.getParameter("Product_Stock").trim());
			} catch (NumberFormatException e) {
				Product_Stock = 0;
				errorMsgs.put("Product_Stock","庫存請輸入數字.");
			}
			System.out.println("Product_StockSV = " + Product_Stock);	

			
			String Product_Detail = req.getParameter("Product_Detail");
			if (Product_Detail == null || Product_Detail.trim().length() == 0) {
				errorMsgs.put("Product_Detail","請輸入商品詳情.");
			}
			System.out.println("Product_DetailSV = " + Product_Detail);
			
			
			String Product_Brand = req.getParameter("Product_Brand");
			if (Product_Brand == null || Product_Brand.trim().length() == 0) {
				errorMsgs.put("Product_Brand","請輸入品牌");
			}
			System.out.println("Product_BrandSV = " + Product_Brand);			
		
			String Category_Id = req.getParameter("Category_Id");
			if (Category_Id == null || Category_Id.trim().length() == 0) {
				errorMsgs.put("Category_Id","請選擇商品分類");
			}
			
			
			System.out.println("Category_IdSV = " + Category_Id);
			byte[] buffer = new byte[8192];
			
			//抓出圖片資料
					
			ByteArrayOutputStream baos1 = new ByteArrayOutputStream();	
			Part part_Lpic = req.getPart("Product_Lpic");			
			InputStream is_Lpic = part_Lpic.getInputStream();
			byte[] buffer1 = new byte[8192];
			int i;
			while((i = is_Lpic.read(buffer1)) != -1){				
				baos1.write(buffer1, 0, i);
			}
			byte[] Product_Lpic = baos1.toByteArray();
			
			
			ByteArrayOutputStream baos2 = new ByteArrayOutputStream();	
			Part part_Lpic1 = req.getPart("Product_Lpic1");
			InputStream is_Lpic1 = part_Lpic1.getInputStream();
			byte[] buffer2 = new byte[8192];
			while((i = is_Lpic1.read(buffer2)) != -1){
				baos2.write(buffer2, 0, i);
			}
			byte[] Product_Lpic1 = baos2.toByteArray();
			
			ByteArrayOutputStream baos3 = new ByteArrayOutputStream();	
			Part part_Spic = req.getPart("Product_Spic");
			InputStream is_Spic = part_Spic.getInputStream();
			byte[] buffer3 = new byte[8192];
			while((i = is_Spic.read(buffer3)) != -1){
				baos3.write(buffer3, 0, i);
			}
			byte[] Product_Spic = baos3.toByteArray();

				ProVO ProVO = new ProVO();				
				ProVO.setCategory_Id(Category_Id);
				ProVO.setProduct_Name(Product_Name);
				ProVO.setProduct_Price(Product_Price);
				ProVO.setProduct_Detail(Product_Detail);
				ProVO.setProduct_Stock(Product_Stock);
				ProVO.setProduct_Brand(Product_Brand);
				ProVO.setProduct_Lpic(Product_Lpic);
				ProVO.setProduct_Lpic1(Product_Lpic1);
				ProVO.setProduct_Spic(Product_Spic);
				
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ProVO", ProVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/haoren/back-pro-add.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				ProductService productService = new ProductService();
				ProVO = productService.addProduct(Category_Id,Product_Name,Product_Price,Product_Detail,Product_Stock,Product_Brand,Product_Lpic,Product_Lpic1,Product_Spic);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/haoren/back-pro-list-all.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/haoren/back-pro-add.jsp");
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
				String Product_Id = req.getParameter("Product_Id");
				System.out.println("SERVLETdelete--" + req.getParameter("product_Id"));
				
				/***************************2.開始刪除資料***************************************/
				ProductService productService = new ProductService();
				productService.deleteProduct(Product_Id);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/product/pro_list_all.jsp";
				System.out.println("刪除完成,準備轉交");
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/pro_list_all.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
	
