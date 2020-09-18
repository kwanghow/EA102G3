package com.coach.model;

public class CoachVO implements java.io.Serializable{
	private String coach_Id;
	private String member_Id;
	private String experience;
	private Integer isCoach;
	private byte[] coach_Img;
	
	public CoachVO() {
	}

	public String getCoach_Id() {
		return coach_Id;
	}

	public void setCoach_Id(String coach_Id) {
		this.coach_Id = coach_Id;
	}

	public String getMember_Id() {
		return member_Id;
	}

	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public Integer getIsCoach() {
		return isCoach;
	}

	public void setIsCoach(Integer isCoach) {
		this.isCoach = isCoach;
	}

	public byte[] getCoach_Img() {
		return coach_Img;
	}

	public void setCoach_Img(byte[] coach_Img) {
		this.coach_Img = coach_Img;
	}
	
}
