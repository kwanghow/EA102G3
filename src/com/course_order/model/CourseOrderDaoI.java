package com.course_order.model;

import java.util.List;
import java.util.Set;

import com.course_date.model.CourseDateVo;

public interface CourseOrderDaoI {
	int insert(CourseOrderVo courseOrderVo);
	void updatePrice(String order_id, String coupon_id, Integer disc, Integer total_price);
	int updateComment(CourseOrderVo courseOrderVo);
	int updateStateById(String order_id, int state);
	int updateBookLessonById(String order_id, int book_lesson);
	List<CourseOrderVo> selectAll();	
	List<CourseOrderVo> selectListByMember_id(String member_id);
	List<CourseOrderVo> selectListBySet_id(String set_id);
	CourseOrderVo selectOneById(String order_id);	
	Set<CourseDateVo> findCourseDatesById(String order_id);
}
