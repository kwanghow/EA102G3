package com.gpc_CoachView.model;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;


public class Gpc_CoachViewService {
	
	private Gpc_CoachViewDAO_interface dao;
	
	public Gpc_CoachViewService() {
		dao = new Gpc_CoachViewJDBCDAO();
	}
	
	public List<Gpc_CoachViewVO> findPeriod_3MM(String coach_Id){
		java.sql.Date startDate = new java.sql.Date(System.currentTimeMillis());//本日
		
		java.util.Date date = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 105); //加15週後
		java.util.Date endUtilDate = cal.getTime();
		java.sql.Date endDate = new java.sql.Date(endUtilDate.getTime());
		
		return dao.findPeriod_3MM(coach_Id, startDate, endDate);
	}
	

	

}
