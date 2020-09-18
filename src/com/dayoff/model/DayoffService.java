package com.dayoff.model;

import java.util.List;

public class DayoffService {
	
	private DayoffDAO_interface dao;
	
	public DayoffService() {
		dao = new DayoffDAO();
	}
	
	public DayoffVO addDayoff(String coach_Id, java.sql.Date dayoff_Date,
			 String dayoff_Time) {
		
		
		DayoffVO dayoffVO = new DayoffVO();
		
		dayoffVO.setCoach_Id(coach_Id);
		dayoffVO.setDayoff_Date(dayoff_Date);
		dayoffVO.setDayoff_Time(dayoff_Time);
		dao.insert(dayoffVO);
		
		return dayoffVO;
	}
	
	public DayoffVO updateDayoff(String dayoff_Id, String coach_Id, java.sql.Date dayoff_Date,
			 String dayoff_Time) {
		
		DayoffVO dayoffVO = new DayoffVO();
		
		dayoffVO.setDayoff_Id(dayoff_Id);
		dayoffVO.setCoach_Id(coach_Id);
		dayoffVO.setDayoff_Date(dayoff_Date);
		dayoffVO.setDayoff_Time(dayoff_Time);
		dao.update(dayoffVO);
		
		return dayoffVO;
	}
	
	public List<DayoffVO> oneCoachAllTime(String coach_Id) {
		return dao.oneCoachAllTime(coach_Id);
	}
	
	public List<DayoffVO> getAll() {
		return dao.getAll();
	}

}
