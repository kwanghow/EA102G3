package com.diet.model;

import java.util.*;

import com.dietCon.model.dietConVO;

public interface dietDAO_interface {
	public void insert(dietVO dietVO);
	public void update(dietVO dietVO);
	public void delete(String dietno);
	public dietVO findByPrimaryKey(String dietno);
	public List<dietVO> getAll();//�ƦX�d��
	public void insertWithDietCon(dietVO dietVO,List<dietConVO> list);
	public Set<dietVO> getDietByMemno(String memno);
	

}
