package com.profav.model;

import java.io.Serializable;

public class Product_FavoriteVO implements Serializable{
	private String member_id;
	private String product_id;
	
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	
	
	
	
	

}
