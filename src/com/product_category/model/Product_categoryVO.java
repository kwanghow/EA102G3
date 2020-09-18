package com.product_category.model;

import java.io.Serializable;

public class Product_categoryVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String category_Id;
	private String category_Name;
	
	public Product_categoryVO() {
		super();
	}

	public String getCategory_Id() {
		return category_Id;
	}

	public void setCategory_Id(String category_Id) {
		this.category_Id = category_Id;
	}

	public String getCategory_Name() {
		return category_Name;
	}

	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}
	
}
