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
		System.out.println("�g�L" + req.getRequestURI());
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);			
			method.invoke(this, req, res);
		} catch(Exception e) {
			throw new RuntimeException("action�ѼƦ��~");
		}
	}
	
	protected void ajaxEditCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. ���o�n�W�[���ҵ{
			String course_id = req.getParameter("course_id");
			CourseVo courseVo = courseSvc.getOneCourseById(course_id);			
			// 2. ���o�n�W�[���ҵ{��סA�Y�L���w�h��ܲĤ@�ӽҵ{���
			String set_id = req.getParameter("set_id");
			CourseSetVo courseSetVo;
			if(set_id == null || set_id.length() == 0 ) {
				courseSetVo = courseSetSvc.getAllCourseSetByCourse_id(course_id).get(0);
			}else {
				courseSetVo = courseSetSvc.getOneCourseSetById(set_id);
			}
			// 3. ��ҵ{�νҵ{��ץ]�˦��ʪ�������
			CourseCartItem item = new CourseCartItem(courseVo.getCourse_id(), "", courseVo.getCname(), courseSetVo.getSet_id(), courseSetVo.getLesson(), courseSetVo.getPrice());
			// 4. ��session�̬O�_���ʪ����A�Y�L�h�s�W�@��
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			if(cart == null) {
				cart = new CourseCart();
				req.getSession().setAttribute("courseCart", cart);
			}
			// 5. �b�ʪ����̷s�W�ҵ{�Χ��ܽҵ{���
			int cartAction = cart.addOrUpdateItem(item);
			System.out.println("�ثe�ʪ������A: " + cart);
			// 6. �^�Ǧ��\�������e��
			if(cartAction == 1) // ���\
				out.write("addCartItemSucess");
			else if(cartAction == -1) {	// ��s
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
			throw new RuntimeException("��s�ʪ����X���F!" + e.getMessage());
		}
	}

	protected void ajaxDeleteCartItem(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. ���o�n�W�[���ҵ{
			String course_id = req.getParameter("course_id");
			// 2. �R��
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			cart.deleteItem(course_id);
			System.out.println("�ثe�ʪ������A: " + cart);
			// 3. �^�Ǧ��\�T��
			out.write(new DecimalFormat(",###").format(cart.getTotalprice()));	
		} catch(Exception e) {
			throw new RuntimeException("�R���ʪ����X���F!" + e.getMessage());
		}
	}

}
