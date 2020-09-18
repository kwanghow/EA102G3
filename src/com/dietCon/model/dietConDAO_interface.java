package com.dietCon.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface dietConDAO_interface {
	
	public void insert (dietConVO dietConVO);
	public void insert2 (dietConVO dietConVO,java.sql.Connection con);
	public void update (dietConVO dietConVO);
	public void delete (String dietCon_no);
	public Set<dietConVO> findByPrimaryKey(String diet_no);
	public List<dietConVO> getAll();
	public List<dietConVO> getAll(Map<String,String[]> map);
}
