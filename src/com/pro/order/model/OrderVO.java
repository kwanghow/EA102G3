package com.pro.order.model;

import java.io.Serializable;
import java.sql.Timestamp;


public class OrderVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String order_id;
	private String member_id;
	private int order_pay;
	private int delivery;
	private String order_address;
	private Timestamp order_date;
	private int order_fee;	
	private int order_status;
	
	
	public OrderVO() {};

	public OrderVO(String order_id, String member_id, String order_address, Integer order_fee, Integer order_pay, Integer delivery, Integer order_status, Timestamp order_date) {
		this.order_id = order_id;
		this.member_id = member_id;
		this.order_pay = order_pay;
		this.delivery = delivery;
		this.order_address = order_address;
		this.order_fee = order_fee;
		this.order_date = order_date;
		this.order_status = order_status;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public int getOrder_pay() {
		return order_pay;
	}
	public void setOrder_pay(int order_pay) {
		this.order_pay = order_pay;
	}
	public int getDelivery() {
		return delivery;
	}
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}
	public String getOrder_address() {
		return order_address;
	}
	public void setOrder_address(String order_address) {
		this.order_address = order_address;
	}
	public Timestamp getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Timestamp order_date) {
		this.order_date = order_date;
	}
	public int getOrder_fee() {
		return order_fee;
	}
	public void setOrder_fee(int order_fee) {
		this.order_fee = order_fee;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	
	
	

	
	


}
