package com.course_order.model;

import java.util.List;
import java.util.Set;

import com.coupon.model.CouponService;
import com.course_cart.model.CourseCart;
import com.course_cart.model.CourseCartItem;
import com.course_date.model.CourseDateVo;


public class CourseOrderService {
	private CourseOrderDaoI dao;
	
	public CourseOrderService() {
		dao = new CourseOrderDaoImpl();
	}
	
	public int addCourseOrder(CourseCart cart, String member_id) {
		int addCount = 0;
		CourseCartItem item;
		CourseOrderVo orderVo;		

		for(String course_id : cart.getItems().keySet()) {
			item = cart.getItems().get(course_id);
			orderVo = new CourseOrderVo();
			
			orderVo.setMember_id(member_id);
			orderVo.setSet_id(item.getSet_id());
			orderVo.setLesson(item.getLesson());
			orderVo.setOrder_price(item.getOrder_price());
			orderVo.setTotal_price(item.getOrder_price());
			
			addCount += dao.insert(orderVo);
		}		
		return addCount;
	}

	public void COrdercheckOut(String order_id) {
		dao.updateStateById(order_id, 3);		
	}
	
	public void COrdercheckOut(String order_id, String coupon_id) {
		Integer order_price = dao.selectOneById(order_id).getOrder_price();
		Integer disc = new CouponService().getOneCouponById(coupon_id).getCoupon_disc();
		Integer total_price = order_price - disc;
		if(total_price < 0) {
			dao.updatePrice(order_id, coupon_id, order_price, 0);
		}else {
			dao.updatePrice(order_id, coupon_id, disc, total_price);
		}		
	}
	
	public int updateCOrderCom(CourseOrderVo courseOrderVo) {
		return dao.updateComment(courseOrderVo);
	}
	
	public int updateCOrderState(String order_id, int state) {
		return dao.updateStateById(order_id, state);
	}
	
	public int updateCOrderBookLesson(String order_id, int book_lesson) {
		return dao.updateBookLessonById(order_id, book_lesson);
	}

	public List<CourseOrderVo> getAllCourseOrder() {
		return dao.selectAll();
	}
	
	public List<CourseOrderVo> getAllCourseOrderByMember_id(String member_id) {
		return dao.selectListByMember_id(member_id);
	}
	
	public List<CourseOrderVo> getAllCourseOrderBySet_id(String set_id){
		return dao.selectListBySet_id(set_id);
	}
	
	public CourseOrderVo getOneCourseOrderById(String order_id) {
		return dao.selectOneById(order_id);
	}
	
	public Set<CourseDateVo> getCourseDatesById(String order_id) {
		return dao.findCourseDatesById(order_id);
	}	
	
	public static void main(String[] args) {
//		CourseCart cart = new CourseCart();
//		Map<String, CourseCartItem> items = cart.getItems();
//		items.put("C20003", new CourseCartItem("C20003", "", "噶瑪蘭戰士-潘弘旻：泰拳教練/現役國手", "CS20008", 1, 3000));
//		
		CourseOrderService svc = new CourseOrderService();
//		svc.addCourseOrder(cart, "M001");
		
		System.out.println(svc.getOneCourseOrderById("CO20001"));
	}
}
