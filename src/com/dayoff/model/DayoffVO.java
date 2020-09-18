package com.dayoff.model;

import java.sql.Date;

public class DayoffVO implements java.io.Serializable{
	private String dayoff_Id;
	private String coach_Id;
	private Date dayoff_Date;
	private String dayoff_Time;
	
	public DayoffVO() {
		
	}

	public String getDayoff_Id() {
		return dayoff_Id;
	}

	public void setDayoff_Id(String dayoff_Id) {
		this.dayoff_Id = dayoff_Id;
	}

	public String getCoach_Id() {
		return coach_Id;
	}

	public void setCoach_Id(String coach_Id) {
		this.coach_Id = coach_Id;
	}

	public Date getDayoff_Date() {
		return dayoff_Date;
	}

	public void setDayoff_Date(Date dayoff_Date) {
		this.dayoff_Date = dayoff_Date;
	}

	public String getDayoff_Time() {
		return dayoff_Time;
	}

	public void setDayoff_Time(String dayoff_Time) {
		this.dayoff_Time = dayoff_Time;
	}

}
