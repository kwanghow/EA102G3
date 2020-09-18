package com.gpc_Disc.model;

import java.sql.Timestamp;
import java.util.List;


public class Gpc_DiscService {
	
	private Gpc_DiscDAO_interface dao;
	
	public Gpc_DiscService() {
		dao = new Gpc_DiscJDBCDAO();
	}
	
	public List<Gpc_DiscVO> getAll(){
		return dao.getAll();
	}
	
	public Gpc_DiscVO getOneGpc_Disc(String gpc_Disc_Id) {
		return dao.findByPK(gpc_Disc_Id);
	}
	
	public Gpc_DiscVO addGpc_Disc(
			String gpc_Id, String member_Id, String question, 
			String answer) {
		
		Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
		

		gpc_DiscVO.setGpc_Id(gpc_Id);
		gpc_DiscVO.setMember_Id(member_Id);
		gpc_DiscVO.setQuestion(question);
		gpc_DiscVO.setAnswer(answer);

	
		dao.insert(gpc_DiscVO);
	
		return gpc_DiscVO;
	}
	
	public Gpc_DiscVO updateGpc_Disc(String gpc_Disc_Id, //PK 
			String question, String answer, Integer mute) {
		
		Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
		
		gpc_DiscVO.setGpc_Disc_Id(gpc_Disc_Id); // PK
		gpc_DiscVO.setQuestion(question);
		gpc_DiscVO.setAnswer(answer);
		//修改後要更新時間戳記		
		gpc_DiscVO.setUpl_Stamp(new Timestamp(System.currentTimeMillis()));
		gpc_DiscVO.setMute(mute);
		
		dao.update(gpc_DiscVO);
	
		return dao.findByPK(gpc_Disc_Id);		
	}
	

}
