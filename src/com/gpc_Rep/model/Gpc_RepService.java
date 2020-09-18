package com.gpc_Rep.model;

import java.util.List;


public class Gpc_RepService {
	
	private Gpc_RepDAO_interface dao;
	
	public Gpc_RepService() {
		dao = new Gpc_RepJDBCDAO();
	}
	
	public List<Gpc_RepVO> getAll(){
		return dao.getAll();
	}
	
	public Gpc_RepVO getOneGpc_Rep(String gpc_Rep_Id) {
		return dao.findByPK(gpc_Rep_Id);
	}
	
	public Gpc_RepVO addGpc_Rep(
			String gpc_Disc_Id, String member_Id, String reason ) {
		
		Gpc_RepVO gpc_RepVO = new Gpc_RepVO();
		
		gpc_RepVO.setGpc_Disc_Id(gpc_Disc_Id);
		gpc_RepVO.setMember_Id(member_Id);
		gpc_RepVO.setReason(reason);
	
		dao.insert(gpc_RepVO);
	
		return gpc_RepVO;
	}
	
	public Gpc_RepVO updateGpc_Rep(String gpc_Rep_Id, //PK 
			String reason, Integer rep_state) {
		
		Gpc_RepVO gpc_RepVO = new Gpc_RepVO();
		
		gpc_RepVO.setGpc_Rep_Id(gpc_Rep_Id); // PK
		gpc_RepVO.setReason(reason);
		gpc_RepVO.setRep_state(rep_state);
		
		dao.update(gpc_RepVO);
	
		return dao.findByPK(gpc_Rep_Id);		
	}
	

}
