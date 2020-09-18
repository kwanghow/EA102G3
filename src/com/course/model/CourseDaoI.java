package com.course.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.course_set.model.CourseSetVo;

public interface CourseDaoI {
		
	int insert(CourseVo courseVo);
	int deleteById(String course_id);
	int update(CourseVo courseVo);
	List<CourseVo> selectAll();
	List<CourseVo> selectAllOnShelf();
	List<CourseVo> selectAll(Map<String, String[]> conditionMap);
	List<CourseVo> selectListByCoachId(String coach_id);
	CourseVo selectOneById(String course_id);
	String selectOneByCoachIdAndCname(String coach_id, String cname);
	Set<CourseSetVo> findCourseSetsById(String course_id);
}
