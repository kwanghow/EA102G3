package com.course.model;

import java.util.List;
import java.util.Map;

public interface CourseViewDaoI {
	List<CourseViewVo> selectAll();
	List<CourseViewVo> selectAllOnShelf();
	List<CourseViewVo> selectAll(Map<String, String[]> conditionMap);
	CourseViewVo selectOneById(String course_id);
}
