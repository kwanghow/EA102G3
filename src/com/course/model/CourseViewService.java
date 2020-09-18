package com.course.model;

import java.util.List;
import java.util.Map;

public class CourseViewService {
	private CourseViewDaoI dao;
	
	public CourseViewService() {
		dao = new CourseViewDaoImpl();
	}
	
	public List<CourseViewVo> getAllCourse() {
		return dao.selectAll();
	}
	
	public List<CourseViewVo> getAllCourseOnShelf() {
		return dao.selectAllOnShelf();
	}
	
	public List<CourseViewVo> getAllCourse(Map<String, String[]> paramMap) {
		return dao.selectAll(paramMap);
	}
	
	public CourseViewVo getOneCourseById(String course_id) {
		return dao.selectOneById(course_id);
	}
	
	public static void main(String[] args) {
		System.out.println(new CourseViewService().getAllCourse());
	}
}
