package com.exp.model;

import java.util.List;

public class ExpService {
	
	private ExpDAO_interface dao;
	
	public ExpService() {
		dao = new ExpDAO();
	}
	
	public ExpVO addExp(String coach_Id, String exp_Id) {
		
		
		ExpVO expVO = new ExpVO();
		
		expVO.setCoach_Id(coach_Id);
		expVO.setExp_Id(exp_Id);

		dao.insert(expVO);
		
		return expVO;
	}
	
	public ExpVO updateExp(String coach_Id, String exp_Id, 
			byte[] license, Integer isExp) {
		
		ExpVO expVO = new ExpVO();
		expVO.setCoach_Id(coach_Id);
		expVO.setExp_Id(exp_Id);
		expVO.setLicense(license);
		expVO.setIsExp(isExp);
		dao.update(expVO);
		
		return expVO;
	}
	
	public void deleteExp(String coach_Id, String exp_Id) {
		dao.delete(coach_Id, exp_Id);
	}
	
	public List<ExpVO> oneCoachHowManySkill(String coach_Id) {
		return dao.oneCoachHowManySkill(coach_Id);
	}
	
	public List<ExpVO> oneCoachHowManySkillIsExp(String coach_Id) {
		return dao.oneCoachHowManySkillIsExp(coach_Id);
	}
		
	public List<ExpVO> oneSkillHowManyCoach(String exp_Id) {
		return dao.oneSkillHowManyCoach(exp_Id);
	}
	
	public List<ExpVO> getAll() {
		return dao.getAll();
	}
	
	public void updateIsExp(List <ExpVO> expList) {
		dao.updateIsExp(expList);
	}

}
