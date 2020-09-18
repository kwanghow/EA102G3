package com.course_favor.model;

public class CourseFavorVo implements java.io.Serializable{
	private String member_id;
	private String course_id;

	public CourseFavorVo() {}
	
	public CourseFavorVo(String member_id, String course_id) {
		this.member_id = member_id;
		this.course_id = course_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	
	@Override
	public String toString() {
		return "CourseFavorVo [member_id=" + member_id + ", course_id=" + course_id + "]";
	}	
}
