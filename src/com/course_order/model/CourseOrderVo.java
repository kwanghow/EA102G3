package com.course_order.model;

import java.sql.Timestamp;

public class CourseOrderVo implements java.io.Serializable{
	private String order_id;
	private String member_id;
	private String set_id;
	private Integer lesson;
	private Integer order_price;
	private String coupon_id;
	private Integer disc;
	private Integer total_price;
	private Integer book_lesson;
	private java.sql.Timestamp initstamp;
	private Integer com_star;
	private String com_content;
	private java.sql.Timestamp com_date;
	private Integer state;

	public CourseOrderVo() {}
	
	public CourseOrderVo(String order_id, String member_id, String set_id, Integer lesson, Integer order_price,
			String coupon_id, Integer disc, Integer total_price, Integer book_lesson, Timestamp initstamp,
			Integer com_star, String com_content, Timestamp com_date, Integer state) {
		super();
		this.order_id = order_id;
		this.member_id = member_id;
		this.set_id = set_id;
		this.lesson = lesson;
		this.order_price = order_price;
		this.coupon_id = coupon_id;
		this.disc = disc;
		this.total_price = total_price;
		this.book_lesson = book_lesson;
		this.initstamp = initstamp;
		this.com_star = com_star;
		this.com_content = com_content;
		this.com_date = com_date;
		this.state = state;
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
	public String getSet_id() {
		return set_id;
	}
	public void setSet_id(String set_id) {
		this.set_id = set_id;
	}
	public Integer getLesson() {
		return lesson;
	}
	public void setLesson(Integer lesson) {
		this.lesson = lesson;
	}
	public Integer getOrder_price() {
		return order_price;
	}
	public void setOrder_price(Integer order_price) {
		this.order_price = order_price;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public Integer getDisc() {
		return disc;
	}
	public void setDisc(Integer disc) {
		this.disc = disc;
	}
	public Integer getTotal_price() {
		return total_price;
	}
	public void setTotal_price(Integer total_price) {
		this.total_price = total_price;
	}
	public Integer getBook_lesson() {
		return book_lesson;
	}
	public void setBook_lesson(Integer book_lesson) {
		this.book_lesson = book_lesson;
	}
	public java.sql.Timestamp getInitstamp() {
		return initstamp;
	}
	public void setInitstamp(java.sql.Timestamp initstamp) {
		this.initstamp = initstamp;
	}
	public Integer getCom_star() {
		return com_star;
	}
	public void setCom_star(Integer com_star) {
		this.com_star = com_star;
	}
	public String getCom_content() {
		return com_content;
	}
	public void setCom_content(String com_content) {
		this.com_content = com_content;
	}
	public java.sql.Timestamp getCom_date() {
		return com_date;
	}
	public void setCom_date(java.sql.Timestamp com_date) {
		this.com_date = com_date;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "CourseOrderVo [order_id=" + order_id + ", member_id=" + member_id + ", set_id=" + set_id + ", lesson="
				+ lesson + ", order_price=" + order_price + ", coupon_id=" + coupon_id + ", disc=" + disc
				+ ", total_price=" + total_price + ", book_lesson=" + book_lesson + ", initstamp=" + initstamp
				+ ", com_star=" + com_star + ", com_content=" + com_content + ", com_date=" + com_date + ", state="
				+ state + "]";
	}
}
