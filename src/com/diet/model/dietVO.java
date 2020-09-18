package com.diet.model;
import java.sql.Date;
import java.sql.Timestamp;

public class dietVO implements java.io.Serializable{
	private String dietno;
	private String member_id;
	private Timestamp diet_date;
	
	public String getDietno() {
		return dietno;
	}
	public void setDietno(String dietno) {
		this.dietno = dietno;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Timestamp getDiet_date() {
		return diet_date;
	}
	public void setDiet_date(Timestamp diet_date) {
		this.diet_date = diet_date;
	}

	
	

}
