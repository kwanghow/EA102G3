package com.course_set.model;

import java.util.List;

public interface CourseSetDaoI {
	
	int insert(CourseSetVo courseSetVo);	
	int deleteById(String set_id);	
	int update(CourseSetVo courseSetVo);	
	List<CourseSetVo> selectAll();	
	List<CourseSetVo> selectListByCourse_id(String course_id);	
	CourseSetVo selectOneById(String set_id);
}
