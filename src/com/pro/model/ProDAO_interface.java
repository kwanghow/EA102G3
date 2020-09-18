
	package com.pro.model;

	import java.util.*;

import com.pro.model.ProVO;

	public interface ProDAO_interface {
		public void ProInsert(ProVO ProVO);
		public void ProUpdate(ProVO ProVO);
		public void ProDelete(ProVO ProVO);
		public ProVO ProSelect(String ProID);		
		public List<ProVO> getAll();
		public List<ProVO> getAllback();
		public List<ProVO> getAllfront();
		public List<ProVO> getAllByCat(String CateGory_Id);
	
		
		
		;
	}



