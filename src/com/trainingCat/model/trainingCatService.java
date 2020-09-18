package com.trainingCat.model;

import java.util.List;

public class trainingCatService {
	
	private trainingCatDAO_interface dao;
	
	public trainingCatService() {
		dao = new trainingCatDAO();
	}
	
	public List<trainingCatVO> getAll(){
		
		return dao.getAll();
	}

}
