package com.course_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coach.model.CoachService;
import com.coach.model.CoachVO;
import com.coupon.model.CouponService;
import com.coupon.model.CouponVo;
import com.course.model.CourseViewService;
import com.course.model.CourseVo;
import com.course_cart.model.CourseCart;
import com.course_cart.model.CourseCartItem;
import com.course_order.model.COrderViewService;
import com.course_order.model.COrderViewVo;
import com.course_order.model.CourseOrderService;
import com.course_order.model.CourseOrderVo;
import com.google.gson.Gson;
import com.jessica.utils.ParamUtils;
import com.mem.model.MemVO;

public class CourseOrderServlet extends HttpServlet {
	CourseOrderService orderSvc = new CourseOrderService();
	COrderViewService orderViewSvc = new COrderViewService();
	CourseViewService courseViewSvc = new CourseViewService();
	CoachService coachSvc = new CoachService();
	
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
			e.printStackTrace();
		}
	}
	
	protected void createOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		try {
			// 1. ���o�|���s��
			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");
			String member_id = memberVo.getMember_Id();
			// 2. ���oCart
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			// 3. �ͦ��q��			
			int addCount = orderSvc.addCourseOrder(cart, member_id);
			req.getSession().removeAttribute("courseCart");
			// 4. �ɦV�q�歶��
			req.getSession().setAttribute("addCount", addCount);
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/member/course_orders.jsp");
		} catch(Exception e) {
			throw new RuntimeException("���ͭq��X���F!" + e.getMessage());
		}
	}
	
	protected void ajaxCreateOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. ���o�|���s��
			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");	
			String member_id = memberVo.getMember_Id();
			// 2. ���oCart
			CourseCart cart = (CourseCart) req.getSession().getAttribute("courseCart");
			// 3. �ͦ��q��			
			int addCount = orderSvc.addCourseOrder(cart, member_id);
			
			// 4. �]�˱нm�W��
			List<String> receivers = new LinkedList<String>();
			Set<String> course_ids = cart.getItems().keySet();
			for(String course_id: course_ids) {
				CourseCartItem item = cart.getItems().get(course_id);
				String coach_id = courseViewSvc.getOneCourseById(item.getCourse_id()).getCoach_id();
				String receiver = coachSvc.getOneByCoach(coach_id).getMember_Id();
				receivers.add(receiver);
			}
			
			// 5. �M���ʪ���
			req.getSession().removeAttribute("courseCart");
			// 6. ��^�нm�W��
			Gson gson = new Gson();			
			out.write(gson.toJson(receivers));
			
		} catch(Exception e) {
			throw new RuntimeException("���ͭq��X���F!" + e.getMessage());
		}
	}

	protected void ajaxUpdateCom(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		CourseOrderVo orderVo = ParamUtils.copyParamToBean(new CourseOrderVo(), req.getParameterMap());
		System.out.println(orderVo);
		res.getWriter().write("123");
	}
	
	protected void ordersFwCheckout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> msgs = new LinkedList<String>();
		req.setAttribute("msgs", msgs);
		
		try {
			String order_id = req.getParameter("order_id");
			CourseOrderVo orderVo = orderSvc.getOneCourseOrderById(order_id);
			if(orderVo == null || orderVo.getState() != 2) {
				msgs.add("�ӭq�椣�s�b�άO�q��D�I�ڶ��q�A�L�k���b�A�۰ʸ���^�q��C��");				
				req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);				
				return;
			}
			req.setAttribute("orderVo", orderVo);
			req.getRequestDispatcher("/front-end/Jessica/member/course_checkout.jsp").forward(req, res);
			return;
			
		} catch(Exception e) {
			msgs.add("�ӭq�椣�s�b�άO�q��D�I�ڶ��q�A�L�k���b�A�۰ʸ���^�q��C��" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);	
			return;
		}
	}

	// �ǭ�����
	protected void cancelOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {		
		try {
			String order_id = req.getParameter("order_id");
			orderSvc.updateCOrderState(order_id, 1);
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/member/course_orders.jsp");
		} catch(Exception e) {
			throw new RuntimeException("�����q��X���F!" + e.getMessage());
		}
	}
	
	// �нm����
	protected void cancelOrder2(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {		
		try {
			String order_id = req.getParameter("order_id");
			orderSvc.updateCOrderState(order_id, 1);
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/coach/course_salesList.jsp");
		} catch(Exception e) {
			throw new RuntimeException("�����q��X���F!" + e.getMessage());
		}
	}
	
	protected void acceptOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {		
		try {
			String order_id = req.getParameter("order_id");
			orderSvc.updateCOrderState(order_id, 2);
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/coach/course_salesList.jsp");
		} catch(Exception e) {
			throw new RuntimeException("�����q��X���F!" + e.getMessage());
		}
	}

	protected void payOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String order_id = req.getParameter("order_id");
			String coupon_id = req.getParameter("coupon_id");			
			
			CouponVo couponVo = new CouponService().getOneCouponById(coupon_id);
			if(couponVo == null) {
				orderSvc.COrdercheckOut(order_id);
			}else {
				orderSvc.COrdercheckOut(order_id, coupon_id);
			}
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/member/course_checkout_sucess.jsp?order_id="+order_id);			
		} catch(Exception e) {
			throw new RuntimeException("�q�浲�b�X���F!" + e.getMessage());
		}
	}

	protected void getOrderlistByMember_id(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> msgs = new LinkedList<String>();
		req.setAttribute("msgs", msgs);
		
		try {
			// 1. ���o�|���s��
			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");
			String member_id = memberVo.getMember_Id();
			
			// 2. �d�����q��C��
			List<COrderViewVo> list = orderViewSvc.getAllCOrderViewByMember_id(member_id);
			if(list.isEmpty()) { // �d�L�q��
				req.setAttribute("orderlistByMember_id", "noResult");
				req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);
				return;
			}
			
			// 3. ���o���w�q�檬�A
			String order_state = req.getParameter("order_state");
			
			// 4_1. �S�����w���A�A��^�����C��
			if(order_state == null || order_state.length() == 0) {
				req.setAttribute("orderlistByMember_id", list);
				req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);
				return;	
			}
			
			// 4_2. �����w���A�A�z��C��
			Integer orderState = Integer.parseInt(order_state);
		    List<COrderViewVo> listByState = list.stream()
		    		.filter(vo -> vo.getState() == orderState)
		    		.collect(Collectors.toList());
		    
		    // 5_1. �z���L���G
			if(listByState.isEmpty()) {
				req.setAttribute("orderlistByMember_id", "noResult");
				req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);
				return;
			}		    
		    
		    // 5_2. �z��ᦳ���G
			req.setAttribute("orderlistByMember_id", listByState);
			req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);    
		
		} catch(Exception e) {
			msgs.add("�L�k�d�߭q��C��" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/member/course_orders.jsp").forward(req, res);	
			return;
		}
	}

	protected void getOrderlistByCoach_id(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> msgs = new LinkedList<String>();
		req.setAttribute("msgs", msgs);
		
		try {
			// 1. ���o�нm�s��			
			CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
			String coach_id = coachVo.getCoach_Id();
			
			// 2. �d�����q��C��
			List<COrderViewVo> list = orderViewSvc.getAllCOrderViewByCoach_id(coach_id);
			if(list.isEmpty()) { // �d�L�q��
				req.setAttribute("orderlistByCoach_id", "noResult");
				req.getRequestDispatcher("/front-end/Jessica/coach/course_salesList.jsp").forward(req, res);
				return;
			}
			
			// 3. ���o���w�q�檬�A
			String order_state = req.getParameter("order_state");
			
			// 4_1. �S�����w���A�A��^�����C��
			if(order_state == null || order_state.length() == 0) {
				req.setAttribute("orderlistByCoach_id", list);
				req.getRequestDispatcher("/front-end/Jessica/coach/course_salesList.jsp").forward(req, res);
				return;	
			}
			
			// 4_2. �����w���A�A�z��C��
			Integer orderState = Integer.parseInt(order_state);
		    List<COrderViewVo> listByState = list.stream()
		    		.filter(vo -> vo.getState() == orderState)
		    		.collect(Collectors.toList());
		    
		    // 5_1. �z���L���G
			if(listByState.isEmpty()) {
				req.setAttribute("orderlistByCoach_id", "noResult");
				req.getRequestDispatcher("/front-end/Jessica/coach/course_salesList.jsp").forward(req, res);
				return;
			}
		    
		    // 5_2. �z��ᦳ���G
			req.setAttribute("orderlistByCoach_id", listByState);
			req.getRequestDispatcher("/front-end/Jessica/coach/course_salesList.jsp").forward(req, res);    
		
		} catch(Exception e) {
			msgs.add("�L�k�d�߭q��C��" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/coach/course_salesList.jsp").forward(req, res);	
			return;
		}
	}
}

