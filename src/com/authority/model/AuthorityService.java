package com.authority.model;

import java.util.List;
import java.util.Set;

import com.employee.model.EmployeeVO;

public class AuthorityService {
	private AuthorityDAO_interface dao;

	public AuthorityService() {
		dao = new AuthorityDAO();
	}

	public AuthorityVO addAuthority(String emp_id, String features_id) {
		AuthorityVO authorityVO = new AuthorityVO();
		authorityVO.setEmp_id(emp_id);
		authorityVO.setFeatures_id(features_id);
		dao.insert(authorityVO);

		return authorityVO;

	}

	public AuthorityVO updateAuthority(String emp_id, String features_id) {
		AuthorityVO authorityVO = new AuthorityVO();
		authorityVO.setEmp_id(emp_id);
		authorityVO.setFeatures_id(features_id);
		dao.update(authorityVO);

		return authorityVO;

	}

	public void deleteAuthority(String emp_id, String features_id) {
		AuthorityVO authorityVO = new AuthorityVO();
		authorityVO.setEmp_id(emp_id);
		authorityVO.setFeatures_id(features_id);
		dao.delete(emp_id, features_id);
	}

	public List<AuthorityVO> getOneAuthority(String emp_id) {
		return dao.findByPrimaryKey(emp_id);

	}
	public List<AuthorityVO> getAll(){
		return dao.getAll();
	}
	public Set<EmployeeVO> getEmpbyFeatures(String features_id){
		return dao.getEmpByFeatures(features_id);
	}

}
