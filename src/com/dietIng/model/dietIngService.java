package com.dietIng.model;

import java.util.List;

public class dietIngService {
	
	private dietIngDAO_interface dao;
	
	public dietIngService() {
		dao = new dietIngDAO();
	}
	
	public dietIngVO addDietIng(String dietIng_no,String dietIng_name,int cal) {
		
		dietIngVO dietIngVO = new dietIngVO();
		dietIngVO.setDietIng_no(dietIng_no);
		dietIngVO.setDietIng_name(dietIng_name);
		dietIngVO.setCal(cal);
		dao.insert(dietIngVO);
		
		return dietIngVO;
	}
	
	public List<dietIngVO> getAll(){
		return dao.getAll();
	}

}
