package com.dietCon.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dietCon.model.dietConDAO_interface;

public class dietConService {
	private dietConDAO_interface dao;
	
	public dietConService() {
		dao = new dietConDAO();
	}
	
	public dietConVO addDietContent(String diet_no,String dietIng_no,String diet_content) {
		
		dietConVO dietConVO = new dietConVO();
		
		dietConVO.setDiet_no(diet_no);
		dietConVO.setDietIng_no(dietIng_no);
		dietConVO.setDiet_content(diet_content);
		dao.insert(dietConVO);
		
		return dietConVO;
	}
	public Set<dietConVO> getOnediet(String dietno) {
		return dao.findByPrimaryKey(dietno);
	}
	
	public List<dietConVO> getAll(){
		return dao.getAll();
	}
	
	public List<dietConVO> getAll(Map<String,String[]> map){
		return dao.getAll(map);
	}
	
	public void delete(String dietCon_no) {
		dao.delete(dietCon_no);
	}
	public dietConVO update(String dietIng_no,String diet_content,String dietcon_no) {
		dietConVO dietConVO = new dietConVO();
		
		dietConVO.setDietIng_no(dietIng_no);
		dietConVO.setDiet_content(diet_content);
		dietConVO.setDietcon_no(dietcon_no);
		dao.update(dietConVO);
		
		return dietConVO;
	}

}
