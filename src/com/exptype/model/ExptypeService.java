package com.exptype.model;

import java.util.List;

public class ExptypeService {
	
	private ExptypeDAO_interface dao;
	
	public ExptypeService() {
		dao = new ExptypeDAO();
	}
	
	public ExptypeVO addExptype(String exp_Name) {
		
		ExptypeVO exptypeVO = new ExptypeVO();
		exptypeVO.setExp_Name(exp_Name);
		dao.insert(exptypeVO);
		
		return exptypeVO;
	}
	
	public void deleteExptype(String exp_Id) {
		dao.delete(exp_Id);
	}
	
	public ExptypeVO getOneExptype(String exp_Id) {
		return dao.findByPrimaryKey(exp_Id);
	}
	
	public List<ExptypeVO> getAll() {
		return dao.getAll();
	}
}
