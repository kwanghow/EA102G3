package com.dietIng.model;

import java.util.List;

public interface dietIngDAO_interface {
	
	public dietIngVO findByPrimaryKey(String dietIng_no);
	public void insert(dietIngVO dietIngVO);
	public void delete(String dietIng_no);
	public List<dietIngVO> getAll();

}
