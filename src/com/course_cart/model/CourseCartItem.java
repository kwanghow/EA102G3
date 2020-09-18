package com.course_cart.model;

public class CourseCartItem implements java.io.Serializable{

	private String course_id;	
	private String picUrl;
	private String cname;
	private String set_id;
	private Integer lesson;
	private Integer order_price;
	
	public CourseCartItem() {}

	public CourseCartItem(String course_id, String picUrl, String cname, String set_id, Integer lesson,
			Integer order_price) {
		super();
		this.course_id = course_id;
		this.picUrl = picUrl;
		this.cname = cname;
		this.set_id = set_id;
		this.lesson = lesson;
		this.order_price = order_price;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
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

	@Override
	public String toString() {
		return "CourseCartItem [course_id=" + course_id + ", picUrl=" + picUrl + ", cname=" + cname + ", set_id="
				+ set_id + ", lesson=" + lesson + ", order_price=" + order_price + "]\n";
	}	
}
