package com.gpc_Schedule.model;

import java.sql.Date;
import java.util.List;


public class Gpc_ScheduleService {
	
	private Gpc_ScheduleDAO_interface dao;
	
	public Gpc_ScheduleService() {
		dao = new Gpc_ScheduleJDBCDAO();
	}
	
	public List<Gpc_ScheduleVO> getAll(){
		return dao.getAll();
	}
	
	public List<Gpc_ScheduleVO> getOneGpc_Schedule(String Id) {
		return dao.findById(Id);
	}
	
	public Gpc_ScheduleVO addGpc_Schedule(String gpc_Id, Date gpc_Date, String gpc_Time) {
		
		Gpc_ScheduleVO gpc_ScheduleVO = new Gpc_ScheduleVO();
		
		gpc_ScheduleVO.setGpc_Id(gpc_Id);
		gpc_ScheduleVO.setGpc_Date(gpc_Date);
		gpc_ScheduleVO.setGpc_Time(gpc_Time);
	
		dao.insert(gpc_ScheduleVO);
	
		return gpc_ScheduleVO;
	}
	
	//應該用不到
//	public List<Gpc_CoachViewVO> updateGpc_Schedule(String gpc_Id, Date gpc_Date, String gpc_time) {
//		
//		Gpc_CoachViewVO gpc_ScheduleVO = new Gpc_CoachViewVO();
//		
//		gpc_ScheduleVO.setGpc_Id(gpc_Id);
//		gpc_ScheduleVO.setGpc_Date(gpc_Date);
//		gpc_ScheduleVO.setGpc_time(gpc_time);
//		
//		dao.update(gpc_ScheduleVO);
//	
//		return dao.findById(gpc_Id);		
//	}
	

}
