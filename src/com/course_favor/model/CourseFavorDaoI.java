package com.course_favor.model;

import java.util.List;

public interface CourseFavorDaoI {
	int insert(CourseFavorVo courseFavorVo);
	int delete(CourseFavorVo courseFavorVo);
	List<CourseFavorVo> selectListByMember_id(String member_id);
	CourseFavorVo selectOne(CourseFavorVo courseFavorVo);
}
