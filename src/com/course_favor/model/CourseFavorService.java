package com.course_favor.model;

import java.util.List;

public class CourseFavorService {
	private CourseFavorDaoI dao;
	
	public CourseFavorService() {
		dao = new CourseFavorDaoImpl();
	}
	
	public int addCourseFavor(CourseFavorVo courseFavorVo) {
		return dao.insert(courseFavorVo);
	}
	
	public int deleteCourseFavor(CourseFavorVo courseFavorVo) {
		return dao.delete(courseFavorVo);
	}
	
	public List<CourseFavorVo> getListByMember_id(String member_id){
		return dao.selectListByMember_id(member_id);
	}
	
	public CourseFavorVo getOneCourseFavor(String member_id, String course_id) {
		CourseFavorVo vo = new CourseFavorVo();
		vo.setMember_id(member_id);
		vo.setCourse_id(course_id);
		return dao.selectOne(vo);
	}

}
