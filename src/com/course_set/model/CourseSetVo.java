package com.course_set.model;

public class CourseSetVo implements java.io.Serializable{
	private String set_id;
	private String course_id;
	private Integer lesson;
	private Integer price;

	public CourseSetVo() {}
	
	public CourseSetVo(String set_id, String course_id, Integer lesson, Integer price) {
		this.set_id = set_id;
		this.course_id = course_id;
		this.lesson = lesson;
		this.price = price;
	}

	public String getSet_id() {
		return set_id;
	}

	public void setSet_id(String set_id) {
		this.set_id = set_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public Integer getLesson() {
		return lesson;
	}

	public void setLesson(Integer lesson) {
		this.lesson = lesson;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CourseSetVo ["
				+ "set_id=" + set_id 
				+ ", course_id=" + course_id 
				+ ", lesson=" + lesson 
				+ ", price=" + price + "]";
	}

}
