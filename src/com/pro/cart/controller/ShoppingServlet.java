package com.pro.cart.controller;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.pro.cart.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mysql.jdbc.StringUtils;


public class ShoppingServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");		
		HttpSession session = req.getSession();
		Vector<Cart> buylist = (Vector<Cart>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");
		String category_id = req.getParameter("category_id");
		String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
		req.setAttribute("requestURL", requestURL); // 送出修改的來源網頁路徑, 存入req
		String whichPage = req.getParameter("whichPage");
		System.out.println("requestURL=" + requestURL);
		req.setAttribute("whichPage", whichPage);
		System.out.println("whichPage" + whichPage);
	

		if (!action.equals("CHECKOUT")) {

			// 刪除購物車中的書籍
			if (action.equals("DELETE")||action.equals("PAY_DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.removeElementAt(d);
				System.out.println("delete_complete");
			}
			// 新增書籍至購物車中
			else if (action.equals("ADD")) {
				System.out.println("start_ADD");
				// 取得後來新增的書籍
				Cart acart = getCart(req);
				// 新增第一本書籍至購物車時
				if (buylist == null) {
					System.out.println("start_ADD_new_one");
					buylist = new Vector<Cart>();
					buylist.add(acart);
				} else {
					if (buylist.contains(acart)) {
						Cart innerCart = buylist.get(buylist.indexOf(acart));
						innerCart.setOrder_buymount(innerCart.getOrder_buymount() + acart.getOrder_buymount());
					} else {
						buylist.add(acart);
					}
						} // end of if name matches
					} // end of for
			
			
//判斷是在shop還是checkout
			session.setAttribute("shoppingcart", buylist);
			System.out.println(action);
		if(action.equals("PAY_DELETE")) {
//			重新計算總額
			int total = 0;
			System.out.println("buylist.size()=" + buylist.size());
			if(buylist.size() != 0) {
			for (int i = 0; i < buylist.size(); i++) {
				Cart order = buylist.get(i);
				int price = order.getProduct_Price();
				int quantity = order.getOrder_buymount();
				total += (price * quantity);				
			}
		}else {
			total = 0;
		}	
			
			if(total<3000) {
				total +=60;
			}else if(total==0) {
				total = 0;
			}
			
			
			String amount = String.valueOf(total);
			session.setAttribute("amountStr", amount);
			String url = "/front-end/haoren/checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
			
		}else{
			System.out.println(category_id);
			
			if(category_id.equals("null")||category_id.equals("")) {
				if(whichPage==null) {
					String url = "/front-end/haoren/list-all-product.jsp";
					System.out.println("url" + url);
					RequestDispatcher rd = req.getRequestDispatcher(url);
					rd.forward(req, res);
				}else {
				
				String url = "/front-end/haoren/list-all-product.jsp?whichPage="+ whichPage; // 送出修改的來源網頁的第幾頁(只用於:istAllEmp.jsp)和修改的是哪一筆
				System.out.println("urlnull=" + url);
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				}
				
			
			}else {		
				
				String url = "/front-end/haoren/list-all-product.jsp?Category_Id="+ category_id + "&whichPage=" + whichPage;
				System.out.println("urlcat" + url);
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
			}
		}
			
			
		}

		// 結帳，計算購物車書籍價錢總數
		else if (action.equals("CHECKOUT")) {
			int total = 0;
			if(buylist.size() != 0) {
			for (int i = 0; i < buylist.size(); i++) {
				Cart order = buylist.get(i);
				int price = order.getProduct_Price();
				int quantity = order.getOrder_buymount();
				total += (price * quantity);
			}
		}else {
			total = 0;
		}
			
			if(total<3000) {
				total +=60;
			}else if(total==0) {
				total = 0;
			}
			

			String amount = String.valueOf(total);
			System.out.println("amount=" + amount);
			session.setAttribute("amountStr", amount);
			
			String url = "/front-end/haoren/checkout.jsp";
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}
	}

	private Cart getCart(HttpServletRequest req) {

		String Product_Id = req.getParameter("product_Id");
		String Product_Name = req.getParameter("product_Name");
		Integer Order_buymount = new Integer(req.getParameter("order_buymount"));
		Integer Product_Price = new Integer(req.getParameter("product_price"));

System.out.println("Product_Id=" + Product_Id);
System.out.println("Product_Name" + Product_Name);
System.out.println("order_buymount" + Order_buymount);
System.out.println("Product_Price" + Product_Price);
		Cart bk = new Cart();

		bk.setProduct_Id(Product_Id);
		bk.setProduct_Name(Product_Name);
		bk.setOrder_buymount((new Integer(Order_buymount)));
		bk.setProduct_Price((new Integer(Product_Price)));
		return bk;
	}
}
