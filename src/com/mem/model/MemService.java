package com.mem.model;

import java.util.List;

public class MemService {
	
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemDAO();
	}
	
	public MemVO addMem(String mem_Name, String mem_Sex,
			String mem_Account, String mem_Psw, byte[] mem_Img,
			String mem_Email, String mem_Phone, java.sql.Date mem_Birth,
			String mem_Addr, String mem_Card) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMem_Name(mem_Name);
		memVO.setMem_Sex(mem_Sex);
		memVO.setMem_Account(mem_Account);
		memVO.setMem_Psw(mem_Psw);
		memVO.setMem_Img(mem_Img);
		memVO.setMem_Email(mem_Email);
		memVO.setMem_Phone(mem_Phone);
		memVO.setMem_Birth(mem_Birth);
		memVO.setMem_Addr(mem_Addr);
//		memVO.setMem_Close(mem_Close);
		memVO.setMem_Card(mem_Card);
		dao.insert(memVO);
		
		return memVO;
	}
	
	public MemVO updateMem (String member_Id, String mem_Name, String mem_Sex,
			String mem_Account, String mem_Psw, byte[] mem_Img,
			String mem_Email, String mem_Phone, java.sql.Date mem_Birth,
			String mem_Addr,Integer mem_Close, String mem_Card) {
		
		MemVO memVO = new MemVO();
		
		memVO.setMember_Id(member_Id);
		memVO.setMem_Name(mem_Name);
		memVO.setMem_Sex(mem_Sex);
		memVO.setMem_Account(mem_Account);
		memVO.setMem_Psw(mem_Psw);
		memVO.setMem_Img(mem_Img);
		memVO.setMem_Email(mem_Email);
		memVO.setMem_Phone(mem_Phone);
		memVO.setMem_Birth(mem_Birth);
		memVO.setMem_Addr(mem_Addr);
		memVO.setMem_Close(mem_Close);
		memVO.setMem_Card(mem_Card);
		dao.update(memVO);
		
		return memVO;
	}
	
	public void deleteMem(String member_Id) {
		dao.delete(member_Id);
	}
	
	public MemVO getOneMem(String member_Id) {
		return dao.findByPrimaryKey(member_Id);
	}
	
	public List<MemVO> getAll() {
		return dao.getAll();
	}
	
	public MemVO logIn(String mem_Account) {
		return dao.logIn(mem_Account);
	}
	
	public void updateFromBack(String member_Id, Integer mem_Close) {
		dao.updateFromBack(member_Id, mem_Close);
	}
}
