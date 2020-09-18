package com.gpc_CoachView.model;
import java.sql.Date;


public class Gpc_CoachViewVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//
	private String coach_Id;		
	private Date view_Date;
	private String view_Time;
	
	public String getCoach_Id() {
		return coach_Id;
	}
	public void setCoach_Id(String coach_Id) {
		this.coach_Id = coach_Id;
	}
	public Date getView_Date() {
		return view_Date;
	}
	public void setView_Date(Date view_Date) {
		this.view_Date = view_Date;
	}
	public String getView_Time() {
		return view_Time;
	}
	public void setView_Time(String view_Time) {
		this.view_Time = view_Time;
	}
	

	
	
	

}
