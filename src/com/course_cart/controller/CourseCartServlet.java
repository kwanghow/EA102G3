package com.course_cart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.model.CourseService;
import com.course.model.CourseVo;
import com.course_cart.model.CourseCart;
import com.course_cart.model.CourseCartItem;
import com.course_set.model.CourseSetService;
import com.course_set.model.CourseSetVo;
import com.google.gson.Gson;


public class CourseCartServlet extends HttpServlet {

	CourseService courseSvc = new CourseService();
	CourseSetService courseSetSvc = new CourseSetService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("經過" + req.getRequestURI());
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);			
			method.invoke(this, req, res);
		} catch(Exception e) {
			throw new RuntimeException("action參數有誤");
		}
	}
	
	protected void ajaxEditCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. 取得要增加的課程
			String course_id = req.getParameter("course_id");
			CourseVo courseVo = courseSvc.getOneCourseById(course_id);			
			// 2. 取得要增加的課程方案，若無指定則選擇第一個課程方案
			String set_id = req.getParameter("set_id");
			CourseSetVo courseSetVo;
			if(set_id == null || set_id.length() == 0 ) {
				courseSetVo = courseSetSvc.getAllCourseSetByCourse_id(course_id).get(0);
			}else {
				courseSetVo = courseSetSvc.getOneCourseSetById(set_id);
			}
			// 3. 把課程及課程方案包裝成購物車項目
			CourseCartItem item = new CourseCartItem(courseVo.getCourse_id(), "", courseVo.getCname(), courseSetVo.getSet_id(), courseSetVo.getLesson(), courseSetVo.getPrice());
			// 4. 找session裡是否有購物車，若無則新增一個
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			if(cart == null) {
				cart = new CourseCart();
				req.getSession().setAttribute("courseCart", cart);
			}
			// 5. 在購物車裡新增課程或改變課程方案
			int cartAction = cart.addOrUpdateItem(item);
			System.out.println("目前購物車狀態: " + cart);
			// 6. 回傳成功消息給前端
			if(cartAction == 1) // 成功
				out.write("addCartItemSucess");
			else if(cartAction == -1) {	// 更新
				Integer lesson = item.getLesson();
				Integer order_price = item.getOrder_price();
				Integer oneLessonPrice = (lesson == null || lesson == 0) ? 0 : (order_price/lesson);
				Integer totalprice = cart.getTotalprice();

				Map<String, String> map = new HashMap<String, String>();
				map.put("lesson", lesson.toString());
				map.put("order_price", new DecimalFormat(",###").format(order_price));
				map.put("oneLessonPrice", new DecimalFormat(",###").format(oneLessonPrice));
				map.put("totalprice", new DecimalFormat(",###").format(totalprice));

				out.write(new Gson().toJson(map));
			}			
		} catch(Exception e) {
			throw new RuntimeException("更新購物車出錯了!" + e.getMessage());
		}
	}

	protected void ajaxDeleteCartItem(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. 取得要增加的課程
			String course_id = req.getParameter("course_id");
			// 2. 刪除
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			cart.deleteItem(course_id);
			System.out.println("目前購物車狀態: " + cart);
			// 3. 回傳成功訊息
			out.write(new DecimalFormat(",###").format(cart.getTotalprice()));	
		} catch(Exception e) {
			throw new RuntimeException("刪除購物車出錯了!" + e.getMessage());
		}
	}

}
