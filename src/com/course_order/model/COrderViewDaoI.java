package com.course_order.model;

import java.util.List;

public interface COrderViewDaoI {
	List<COrderViewVo> selectAll();	
	List<COrderViewVo> selectListByMember_id(String member_id);
	List<COrderViewVo> selectListByCoach_id(String coach_id);
	COrderViewVo selectOneById(String order_id);
}
