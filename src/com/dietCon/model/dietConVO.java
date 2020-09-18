package com.dietCon.model;

import java.io.Serializable;


public class dietConVO implements Serializable{
	
	private String dietcon_no;
	private String diet_no;
	private String dietIng_no;
	private String diet_content;
	public String getDiet_no() {
		return diet_no;
	}
	public void setDiet_no(String diet_no) {
		this.diet_no = diet_no;
	}
	public String getDietIng_no() {
		return dietIng_no;
	}
	public void setDietIng_no(String dietIng_no) {
		this.dietIng_no = dietIng_no;
	}
	public String getDiet_content() {
		return diet_content;
	}
	public void setDiet_content(String diet_content) {
		this.diet_content = diet_content;
	}
	public String getDietcon_no() {
		return dietcon_no;
	}
	public void setDietcon_no(String dietcon_no) {
		this.dietcon_no = dietcon_no;
	}
	
	

}
