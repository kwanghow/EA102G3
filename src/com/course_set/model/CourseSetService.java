package com.course_set.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.course_order.model.CourseOrderService;
import com.course_order.model.CourseOrderVo;

public class CourseSetService {
	private CourseSetDaoI dao;
	
	public CourseSetService() {
		dao = new CourseSetDaoImpl();
	}
	
	public int addCourseSet(CourseSetVo courseSetVo) {
		return dao.insert(courseSetVo);
	}

	public int updateCourseSet(CourseSetVo courseSetVo) {
		return dao.update(courseSetVo);
	}

	public List<CourseSetVo> getAllCourseSet() {
		return dao.selectAll();
	}
	
	public List<CourseSetVo> getAllCourseSetByCourse_id(String course_id) {
		return dao.selectListByCourse_id(course_id);
	}
	
	public CourseSetVo getOneCourseSetById(String set_id) {
		return dao.selectOneById(set_id);
	}
	
	public Set<CourseOrderVo> getCOrderFromCSet(String set_id){	
		Set<CourseOrderVo> orderSet = new LinkedHashSet<CourseOrderVo>();
		
		CourseOrderService orderSvc = new CourseOrderService();
		List<CourseOrderVo> orders = orderSvc.getAllCourseOrderBySet_id(set_id);
		for(CourseOrderVo orderVo : orders) {
			orderSet.add(orderVo);
		}
		return orderSet;
	}
	
	public int[] getAllCourseSetPrice(String course_id) {
		List<CourseSetVo> list = dao.selectListByCourse_id(course_id);
		int[] arr = new int[list.size()];
		for(int i=0; i<arr.length; i++) {
			arr[i] = list.get(i).getPrice();
		}
		return arr;
	}
	
	public int getCSetPriceAvg(String course_id) {
		List<CourseSetVo> list = dao.selectListByCourse_id(course_id);
		int[] lesson = new int[list.size()];
		int[] price = new int[list.size()];
		for(int i=0; i<list.size(); i++) {
			lesson[i] = list.get(i).getLesson();
			price[i] = list.get(i).getPrice();			
		}
		int lessonSum = 0;
		for(int i: lesson) {
			lessonSum += i;
		}
		int priceSum = 0;
		for(int i: price) {
			priceSum += i;
		}
		return priceSum / lessonSum;
	}

}
