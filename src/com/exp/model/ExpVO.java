package com.exp.model;

public class ExpVO implements java.io.Serializable{
	private String coach_Id;
	private String exp_Id;
	private byte[] license;
	private Integer isExp;
	
	public ExpVO() {
		
	}

	public String getCoach_Id() {
		return coach_Id;
	}

	public void setCoach_Id(String coach_Id) {
		this.coach_Id = coach_Id;
	}

	public String getExp_Id() {
		return exp_Id;
	}

	public void setExp_Id(String exp_Id) {
		this.exp_Id = exp_Id;
	}

	public byte[] getLicense() {
		return license;
	}

	public void setLicense(byte[] license) {
		this.license = license;
	}

	public Integer getIsExp() {
		return isExp;
	}

	public void setIsExp(Integer isExp) {
		this.isExp = isExp;
	}

}
