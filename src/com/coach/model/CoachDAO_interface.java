package com.coach.model;
import java.util.*;

import com.exp.model.ExpVO;

public interface CoachDAO_interface {
	public void insert(CoachVO coachVO);
	public void update(CoachVO coachVO);
	public void delete(String coach_Id);
	public CoachVO findByPrimaryKey(String coach_Id);
	public CoachVO findByMemId(String member_Id);
	public List<CoachVO> getAll();
	//同時新增教練(單筆)與專長(多筆)
	public void insertWithExps(CoachVO coachVO, String[] exp_Id);
	
	public void updateFromBack(String coach_Id, Integer isCoach);
	
	public void updateImg(CoachVO coachVO);
}
