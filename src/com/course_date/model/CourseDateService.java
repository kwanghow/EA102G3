package com.course_date.model;

import java.util.List;

public class CourseDateService {
	private CourseDateDaoI dao;
	
	public CourseDateService() {
		dao = new CourseDateDaoImpl();
	}
	
	public int addCourseDate(CourseDateVo CourseDateVo) {
		return dao.insert(CourseDateVo);
	}
	
	public int deleteCourseDate(String cdate_id) {
		return dao.deleteById(cdate_id);
	}
	
	public int updateCDateState(String cdate_id, int state) {
		return dao.updateStateById(cdate_id, state);
	}

	public List<CourseDateVo> getAllCourseDate() {
		return dao.selectAll();
	}
	
	public List<CourseDateVo> getAllCourseDateByOrder_id(String order_id) {
		return dao.selectListByOrder_id(order_id);
	}
	
	public CourseDateVo getOneCourseDateById(String cdate_id) {
		return dao.selectOneById(cdate_id);
	}
	
}
