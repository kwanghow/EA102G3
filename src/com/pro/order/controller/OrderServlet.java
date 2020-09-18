package com.pro.order.controller;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.mem.model.MemVO;
import com.pro.cart.model.Cart;
import com.pro.order.model.OrderService;
import com.pro.order.model.OrderVO;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutOneTime;
import ecpay.payment.integration.domain.InvoiceObj;


@MultipartConfig(fileSizeThreshold = 1920 * 1080, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class OrderServlet extends HttpServlet {

	/**
	 * 
	 */
	
	static String getDateTime(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String strDate = sdFormat.format(date);
		return strDate;
		}
	
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println(action);
		
		
//		綠界
		if("so_checkout".equals(action)) {
//			String so_id = req.getParameter("so_id");
//			String type =req.getParameter("type");
			String member_id = req.getParameter("member_id");
//			String delivery = req.getParameter("delivery");
			String amount = (req.getParameter("amountStr"));
			String loca = (req.getParameter("loca"));
//			String status = req.getParameter("status");
			HttpSession session = req.getSession();
			Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
			System.out.println(buylist);
			String allPro =  member_id + "#";
			for (Cart Cart : buylist) {
								
				 allPro +=  Cart.getProduct_Name() + "#" ;
			};
				
			
			
			
			InvoiceObj invoice = null;
//			System.out.println(so_id);
			System.out.println(allPro);
			System.out.println(member_id);
//			System.out.println(delivery);
//			System.out.println(est_time);
			System.out.println(amount);
//			System.out.println(del_address);
//			System.out.println(recipient);
//			System.out.println(pay_via);
			System.out.println(new String(req.getRequestURL()));
			System.out.println("-------new String(req.getRequestURL())--------");
			AllInOne allInOne = new AllInOne("");
			AioCheckOutOneTime obj = new AioCheckOutOneTime();
			Date date = new Date();
			DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			System.out.println(sdf.format(date));
			
//			obj.setMerchantTradeNo(so_id+date.getTime());
			obj.setMerchantTradeNo("P0"+date.getTime());
			obj.setMerchantTradeDate(sdf.format(date));			
			obj.setTotalAmount(amount);
			obj.setTradeDesc("test Description"); 
			obj.setItemName(allPro);			
//			obj.setReturnURL(req.getContextPath()+"/front_end/c2cproMain/c2csoMain.do");	
			obj.setReturnURL(new String(req.getRequestURL()));
			obj.setNeedExtraPaidInfo("N");
			obj.setRedeem("N");
			obj.setClientBackURL(loca);
//			obj.setOrderResultURL("http://localhost:8081/EA102G2/front_end/c2cproMain/c2cbuyer.jsp");
			obj.setChooseSubPayment("Credit");
			res.setContentType("text/html; charset=UTF-8");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			String form = allInOne.aioCheckOut(obj, invoice);
			out.println("<HTML>");
			out.println("<HEAD><TITLE></TITLE></HEAD>");
			out.println("<BODY>");
			out.print(form);
			out.println("</BODY></HTML>");

		}
		
		 
		
		//訂單完成
				if ("complete".equals(action)) { // 來自listAllEmp.jsp的請求

					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
						/***************************1.接收請求參數****************************************/
						String Order_Id = req.getParameter("Order_Id");
						System.out.println("************" + Order_Id + "*********************");
						
						/***************************2.開始查詢資料****************************************/
						OrderService OrderService = new OrderService();
						OrderService.complete_order(Order_Id);										
						/***************************3.查詢完成,準備轉交(Send the Success view)************/
						String url = "/front-end/haoren/orderPage.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
						successView.forward(req, res);

						/***************************其他可能的錯誤處理**********************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/haoren/orderPage.jsp");
						failureView.forward(req, res);
					}
				}
				
				//訂單取消
				if ("cancel".equals(action)) { // 來自listAllEmp.jsp的請求
					
					List<String> errorMsgs = new LinkedList<String>();
					// Store this set in the request scope, in case we need to
					// send the ErrorPage view.
					req.setAttribute("errorMsgs", errorMsgs);
					
					try {
						/***************************1.接收請求參數****************************************/
						String Order_Id = req.getParameter("Order_Id");
						System.out.println("************" + Order_Id + "*********************");
						
						/***************************2.開始查詢資料****************************************/
						OrderService OrderService = new OrderService();
						OrderService.cancel_order(Order_Id);										
						/***************************3.查詢完成,準備轉交(Send the Success view)************/
						String url = "/front-end/haoren/orderPage.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
						successView.forward(req, res);
						
						/***************************其他可能的錯誤處理**********************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
						RequestDispatcher failureView = req
								.getRequestDispatcher("/front-end/haoren/orderPage.jsp");
						failureView.forward(req, res);
					}
				}
		
		
		
		
		
		//單獨拉一個訂單做修改
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String Order_Id = req.getParameter("Order_Id");
				System.out.println("************" + Order_Id + "*********************");
				
				/***************************2.開始查詢資料****************************************/
				OrderService OrderService = new OrderService();
				OrderVO OrderVO = OrderService.getOneOrder(Order_Id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("OrderVO", OrderVO);  
				String url = "/back-end/haoren/back-order-update.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/back-order-list-all.jsp");
				failureView.forward(req, res);
			}
		}
		
		//訂單修改
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				System.out.println("servlet_update_start");
				
				
				String order_id = req.getParameter("order_id");
				if (order_id == null || order_id.trim().length() == 0) {
					errorMsgs.add("請輸入訂單.");
				}
				System.out.println("order_idSV-- = " + order_id);
				
				String member_id = req.getParameter("member_id");
				if (member_id == null || member_id.trim().length() == 0) {
					errorMsgs.add("請輸入買家資料.");
				}
				System.out.println("member_idSV-- = " + member_id);
				
				
					
				Integer order_pay = null;
				try {
					order_pay = new Integer(req.getParameter("order_pay").trim());
				} catch (NumberFormatException e) {
					order_pay = 0;
					errorMsgs.add("請選擇付款方式.");
				}
				System.out.println("order_paySV-- = " + order_pay);	
				
				Integer delivery = null;
				try {
					delivery = new Integer(req.getParameter("delivery").trim());
				} catch (NumberFormatException e) {
					delivery = 0;
					errorMsgs.add("請選擇運送方式.");
				}
				System.out.println("deliverySV-- = " + delivery);	

				
				String order_address = req.getParameter("order_address");
				if (order_address == null || order_address.trim().length() == 0) {
					errorMsgs.add("請輸入地址.");
				}
				System.out.println("order_addressSV-- = " + order_address);
				
			
				Integer order_fee = null;
				try {
					order_fee = new Integer(req.getParameter("order_fee").trim());
				} catch (NumberFormatException e) {
					order_fee = 0;
					errorMsgs.add("請輸入運費");
				}
				System.out.println("order_feeSV-- = " + order_fee);	
				
				
				
				
				

				OrderVO OrderVO = new OrderVO();			
				OrderVO.setOrder_id(order_id);
				OrderVO.setMember_id(member_id);
				OrderVO.setOrder_pay(order_pay);
				OrderVO.setDelivery(delivery);
				OrderVO.setOrder_address(order_address);				
				OrderVO.setOrder_fee(order_fee);				
//				System.out.println("庫存 : " +"\t" + OrderVO.getProduct_Stock());
				System.out.println("order_id : " + "\t" + OrderVO.getOrder_id());
				
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("OrderVO", OrderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/haoren/back-order-update.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				OrderService OrderService = new OrderService();
				OrderVO = OrderService.ProUpdate(order_id,member_id, order_pay, delivery, order_address, order_fee);
				System.out.println("order_id1 : " + "\t" + OrderVO.getOrder_id());
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("OrderVO",OrderVO);
				String url = "/back-end/haoren/back-order-list-all.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/back-order-update.jsp");
				failureView.forward(req, res);
			}
		}
		//加入訂單
        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			
			String member_id = req.getParameter("member_id");//			
			
			System.out.println("member_idSV-- = " + member_id);
//			
//			String coupon_id = req.getParameter("coupon_id");
//			if (coupon_id == null || coupon_id.trim().length() == 0) {
//				errorMsgs.add("請輸入折價券.");
//			}
//			System.out.println("coupon_idSV-- = " +coupon_id);
			
			
			
			
			
			
				
			Integer order_pay = null;
			try {
				order_pay = new Integer(req.getParameter("order_pay").trim());
			} catch (NumberFormatException e) {
				order_pay = 0;
				errorMsgs.put("order_pay","請選擇付款方式.");
			}
			System.out.println("order_paySV-- = " + order_pay);	
			
			Integer delivery = null;
			try {
				delivery = new Integer(req.getParameter("delivery").trim());
			} catch (NumberFormatException e) {
				delivery = 0;
				errorMsgs.put("delivery","請選擇運送方式.");
			}
			System.out.println("deliverySV-- = " + delivery);	

			
			
			String address_shop = req.getParameter("shop_address");
			String address = req.getParameter("order_address");
			String county = req.getParameter("county");
			String district = req.getParameter("district");
			String zipcode = req.getParameter("zipcode");
			
			System.out.println("address_shop"+address_shop);
			System.out.println("address" + address);
			System.out.println(address == "");
			System.out.println(address_shop == "");
			String order_address = null;
			if(!(address == "")  )  {
				 order_address = zipcode + county + district + address;
				System.out.println("order_addressSV-- = " + order_address);
			}else if(!(address_shop == "")) {
				order_address = address_shop;
				System.out.println("order_addressSV-- = " + order_address);
			}
			
			
			
			
			
		
			Integer order_fee = null;
			try {
				order_fee = new Integer(req.getParameter("order_fee").trim());
			} catch (NumberFormatException e) {
				order_fee = 0;
				errorMsgs.put("order_fee","請輸入運費");
			}
			System.out.println("order_feeSV-- = " + order_fee);	
			
			
			Integer order_status = null;
			try {
				order_status = new Integer(req.getParameter("order_status").trim());
			} catch (NumberFormatException e) {
				delivery = 0;
				errorMsgs.put("order_status","請選擇上架狀態.");
			}
			System.out.println("order_statusSV-- = " + order_status);	
			
			
//			String creditCard_Num = req.getParameter("creditCard_Num");
//			if (creditCard_Num == null || creditCard_Num.trim().length() == 0) {
//				errorMsgs.add("請輸入信用卡資訊");
//			}
//			String last_Three_Num = req.getParameter("last_Three_Num");
//			if (last_Three_Num == null || last_Three_Num.trim().length() == 0) {
//				errorMsgs.add("請輸入信用卡後三碼");
//			}
//			
//			if (order_pay.equals("1")) {
//				if (creditCard_Num.length() != 16) {
//					errorMsgs.add("信用卡號請填16碼數字");
//				}
//				if (last_Three_Num.length() != 3) {
//					errorMsgs.add("末三碼請填3個數字");
//				}
//			}
//			
//			System.out.println("Card_IdSV = " + creditCard_Num +"//" + last_Three_Num);

			Timestamp time= new Timestamp(System.currentTimeMillis());
			

				OrderVO OrderVO = new OrderVO();			
//				OrderVO.setOrder_id(Order_id);
				OrderVO.setMember_id(member_id);
				OrderVO.setOrder_pay(order_pay);
				OrderVO.setDelivery(delivery);
				OrderVO.setOrder_address(order_address);				
				OrderVO.setOrder_fee(order_fee);
				OrderVO.setOrder_status(order_status);
				OrderVO.setOrder_date(time);
				
			
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("OrderVO", OrderVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/haoren/checkout.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				HttpSession session = req.getSession();
				Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
				
				
				OrderService OrderService = new OrderService();
				OrderVO = OrderService.insertWithOrderLists(OrderVO,buylist);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("OrderVO", OrderVO);
				String url = "/front-end/haoren/complet.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("message",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/haoren/checkout.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		//更改訂單狀態
		if ("update_order_status".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String order_id = req.getParameter("Order_id");
				System.out.println("SERVLETupdate_order_id--" + order_id);
				Integer order_status = new Integer(req.getParameter("Order_status").trim());
				System.out.println("SERVLETupdate_order_status--" + order_status);
				
				/***************************2.開始修改資料***************************************/
				OrderService OrderService = new OrderService();
				OrderService.UpdateStatus(order_id,order_status);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/haoren/back-order-list-all.jsp";
				System.out.println("update_order_status_OK");
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("修改狀態失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/haoren/back-order-list-all.jsp");
				failureView.forward(req, res);
			}
		}
		//更改訂單狀態
		if ("ajax_update_order_status".equals(action)) { // 來自listAllEmp.jsp
			res.setContentType("text/plain;charset=UTF-8");
			try {
				/***************************1.接收請求參數***************************************/
				String order_id = req.getParameter("Order_id");
				System.out.println("SERVLETupdate_order_id--" + order_id);
				Integer order_status = new Integer(req.getParameter("Order_status").trim());
				System.out.println("SERVLETupdate_order_status--" + order_status);
				
				/***************************2.開始修改資料***************************************/
				OrderService OrderService = new OrderService();
				OrderService.UpdateStatus(order_id, order_status);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				System.out.println("update_order_status_OK");
				res.getWriter().write("updateSucess");
				return;				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				System.out.println("ajax更新order失敗"+e.getMessage());
			}
		}
	}
}
	
