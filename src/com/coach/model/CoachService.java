package com.coach.model;

import java.util.List;

import com.exp.model.ExpVO;

public class CoachService {
	
	private CoachDAO_interface dao;
	
	public CoachService() {
		dao = new CoachDAO();
	}
	
	public CoachVO addCoach(String member_Id, 
			String experience, byte[] coach_Img) {
		
		CoachVO coachVO = new CoachVO();
		
		coachVO.setMember_Id(member_Id);
		coachVO.setExperience(experience);
		coachVO.setCoach_Img(coach_Img);
		dao.insert(coachVO);
		
		return coachVO;
	}
	
	public CoachVO updateCoach(String coach_Id, String member_Id, 
			String experience, Integer isCoach, byte[] coach_Img) {
		
		CoachVO coachVO = new CoachVO();
		
		coachVO.setCoach_Id(coach_Id);
		coachVO.setMember_Id(member_Id);
		coachVO.setExperience(experience);
		coachVO.setIsCoach(isCoach);
		coachVO.setCoach_Img(coach_Img);
		dao.update(coachVO);
		
		return coachVO;
	}
	
	public void deleteCoach(String coach_Id) {
		dao.delete(coach_Id);
	}
	
	public CoachVO getOneByCoach(String coach_Id) {
		return dao.findByPrimaryKey(coach_Id);
	}
	
	public CoachVO getOneByMem(String member_Id) {
		return dao.findByMemId(member_Id);
	}
	
	public List<CoachVO> getAll() {
		return dao.getAll();
	}
	
	public CoachVO insertWithExps(String member_Id, 
			String experience, byte[] coach_Img, String[] exp_Id) {
		
		CoachVO coachVO = new CoachVO();
		
		coachVO.setMember_Id(member_Id);
		coachVO.setExperience(experience);
		coachVO.setCoach_Img(coach_Img);
		dao.insertWithExps(coachVO, exp_Id);
		
		return coachVO;
	}
	
	public void updateFromBack(String coach_Id, Integer isCoach) {
		dao.updateFromBack(coach_Id, isCoach);
	}
} 
