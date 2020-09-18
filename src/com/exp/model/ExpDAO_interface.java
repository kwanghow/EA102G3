package com.exp.model;

import java.util.List;

public interface ExpDAO_interface {
	public void insert(ExpVO expVO);
	public void update(ExpVO expVO);
	public void delete(String coach_Id, String exp_Id);
	public List<ExpVO> oneCoachHowManySkill(String coach_Id);
	public List<ExpVO> oneCoachHowManySkillIsExp(String coach_Id);
	public List<ExpVO> oneSkillHowManyCoach(String exp_Id);
	public List<ExpVO> getAll();
	
	//同時新增教練與專長(多筆)
	public void insert2 (ExpVO expVO, java.sql.Connection con);
	public void updateIsExp(List <ExpVO> expList);
}
