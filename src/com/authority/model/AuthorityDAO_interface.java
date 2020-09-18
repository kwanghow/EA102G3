package com.authority.model;

import java.util.List;
import java.util.Set;

import com.employee.model.EmployeeVO;
public interface AuthorityDAO_interface {
	
	public void insert(AuthorityVO authorityVO);
	public void update(AuthorityVO authorityVO);
	public void delete(String emp_id,String features_id);
	public List<AuthorityVO> findByPrimaryKey(String emp_id);
	public List<AuthorityVO> getAll();
	public Set<EmployeeVO> getEmpByFeatures(String features_id);
	
	
	
	
	
	
	

}
