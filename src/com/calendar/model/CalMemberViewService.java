package com.calendar.model;

import java.util.List;

public class CalMemberViewService {
	private CalMemberViewDaoI dao;
	
	public CalMemberViewService() {
		this.dao = new CalMemberViewDaoImpl();
	}
	
	public List<CalMemberViewVo> getAllCalMemberView() {
		return dao.selectAll();
	}
	
	public List<CalMemberViewVo> getAllCalMemberViewByMember_id(String member_id) {
		return dao.selectListByMember_id(member_id);
	}
	
	public static void main(String[] args) {
		CalMemberViewService svc = new CalMemberViewService();
		System.out.println(svc.getAllCalMemberViewByMember_id("M001"));
	}
}
