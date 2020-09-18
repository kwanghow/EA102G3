package com.diet.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.dietCon.model.dietConVO;

public class DietService {
	
	private dietDAO_interface  dao;
	
	public DietService() {
		dao = new dietDAO();
	}
	
	public dietVO addDiet(String memberID,Timestamp dietdate) {
		
		dietVO dietVO = new dietVO();
		dietVO.setMember_id(memberID);
		dietVO.setDiet_date(dietdate);
		dao.insert(dietVO);
		
		return dietVO;
	}
	
	public void deldiet(String dietno) {
		
		dao.delete(dietno);
	}
	
	public dietVO getOneDiet(String memberID) {
		
		return dao.findByPrimaryKey(memberID);
	}
	
	public List<dietVO> getAll(){
		return dao.getAll();
		
	}
	
	public void insertWithDietCon(dietVO dietVO, List<dietConVO> list) {
		dao.insertWithDietCon(dietVO, list);
	}
	public Set<dietVO> getDietByMemno(String memno){
		return dao.getDietByMemno(memno);
		
	}
	public dietVO update(String dietno,Timestamp diet_date) {
		
		dietVO dietVO = new dietVO();
		
		dietVO.setDiet_date(diet_date);
		dietVO.setDietno(dietno);
		
		dao.update(dietVO);
		return dietVO;
		
	}

}
