package com.course_date.model;

import java.util.List;

public interface CourseDateDaoI {
	int insert(CourseDateVo courseDateVo);	
	int deleteById(String cdate_id);	
	int updateStateById(String cdate_id, int state);	
	List<CourseDateVo> selectAll();	
	List<CourseDateVo> selectListByOrder_id(String order_id);	
	CourseDateVo selectOneById(String cdate_id);
}
