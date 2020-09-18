package com.course_order.model;

import java.util.List;

public class COrderViewService {
	private COrderViewDaoI dao;
	
	public COrderViewService() {
		dao = new COrderViewDaoImpl();
	}

	public List<COrderViewVo> getAllCOrderView() {
		return dao.selectAll();
	}
	
	public List<COrderViewVo> getAllCOrderViewByMember_id(String member_id) {
		return dao.selectListByMember_id(member_id);
	}
	
	public List<COrderViewVo> getAllCOrderViewByCoach_id(String coach_id) {
		return dao.selectListByCoach_id(coach_id);		
	}
	
	public COrderViewVo getOneCOrderViewById(String order_id) {
		return dao.selectOneById(order_id);
	}

}
