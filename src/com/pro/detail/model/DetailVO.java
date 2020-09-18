package com.pro.detail.model;

import java.io.Serializable;


public class DetailVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String product_id;
	private String product_name;
	private int order_buymount;
	private int product_price;
	
	public DetailVO(String order_id, String product_id
			, int order_buymount, int product_price) {
		super();
		this.order_id = order_id;
		this.product_id = product_id;
		this.order_buymount = order_buymount;
		this.product_price = product_price;
	}
	public DetailVO() {};
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + order_buymount;
		result = prime * result + ((order_id == null) ? 0 : order_id.hashCode());
		result = prime * result + ((product_id == null) ? 0 : product_id.hashCode());
		result = prime * result + product_price;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetailVO other = (DetailVO) obj;
		if (order_buymount != other.order_buymount)
			return false;
		if (order_id == null) {
			if (other.order_id != null)
				return false;
		} else if (!order_id.equals(other.order_id))
			return false;
		if (product_id == null) {
			if (other.product_id != null)
				return false;
		} else if (!product_id.equals(other.product_id))
			return false;
		if (product_price != other.product_price)
			return false;
		return true;
	}


	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public int getOrder_buymount() {
		return order_buymount;
	}
	public void setOrder_buymount(int order_buymount) {
		this.order_buymount = order_buymount;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	
	

	


}
