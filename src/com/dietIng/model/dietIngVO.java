package com.dietIng.model;

import java.io.Serializable;

public class dietIngVO implements Serializable{
	
	private String dietIng_no;
	private String dietIng_name;
	private int cal;
	
	public String getDietIng_no() {
		return dietIng_no;
	}
	public void setDietIng_no(String dietIng_no) {
		this.dietIng_no = dietIng_no;
	}
	public String getDietIng_name() {
		return dietIng_name;
	}
	public void setDietIng_name(String dietIng_name) {
		this.dietIng_name = dietIng_name;
	}
	public int getCal() {
		return cal;
	}
	public void setCal(int cal) {
		this.cal = cal;
	}
	
	

}
