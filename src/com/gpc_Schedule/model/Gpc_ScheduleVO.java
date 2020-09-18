package com.gpc_Schedule.model;
import java.sql.Date;


public class Gpc_ScheduleVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//
	private String gpc_Id;		
	private Date gpc_Date;
	private String gpc_Time;
	
	//
	public String getGpc_Id() {
		return gpc_Id;
	}
	public void setGpc_Id(String gpc_Id) {
		this.gpc_Id = gpc_Id;
	}
	public Date getGpc_Date() {
		return gpc_Date;
	}
	public void setGpc_Date(Date gpc_Date) {
		this.gpc_Date = gpc_Date;
	}
	public String getGpc_Time() {
		return gpc_Time;
	}
	public void setGpc_Time(String gpc_Time) {
		this.gpc_Time = gpc_Time;
	}	
	
	//
	
	
	

}
