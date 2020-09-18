package com.course.model;

import java.sql.Timestamp;
import java.util.Arrays;

public class CourseVo implements java.io.Serializable{
	private String course_id;
	private String coach_id;
	private String cname;
	private String intro;
	private String exp_id;
	private String loc;
	private byte[] pic1;
	private byte[] pic2;
	private byte[] pic3;
	private java.sql.Timestamp initstamp;
	private Integer state;
	
	public CourseVo() {}
	
	public CourseVo(String course_id, String coach_id, String cname, String intro, String exp_id, String loc,
			byte[] pic1, byte[] pic2, byte[] pic3, Timestamp initstamp, Integer state) {
		super();
		this.course_id = course_id;
		this.coach_id = coach_id;
		this.cname = cname;
		this.intro = intro;
		this.exp_id = exp_id;
		this.loc = loc;
		this.pic1 = pic1;
		this.pic2 = pic2;
		this.pic3 = pic3;
		this.initstamp = initstamp;
		this.state = state;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCoach_id() {
		return coach_id;
	}

	public void setCoach_id(String coach_id) {
		this.coach_id = coach_id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getExp_id() {
		return exp_id;
	}

	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public byte[] getPic1() {
		return pic1;
	}

	public void setPic1(byte[] pic1) {
		this.pic1 = pic1;
	}

	public byte[] getPic2() {
		return pic2;
	}

	public void setPic2(byte[] pic2) {
		this.pic2 = pic2;
	}

	public byte[] getPic3() {
		return pic3;
	}

	public void setPic3(byte[] pic3) {
		this.pic3 = pic3;
	}

	public java.sql.Timestamp getInitstamp() {
		return initstamp;
	}

	public void setInitstamp(java.sql.Timestamp initstamp) {
		this.initstamp = initstamp;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CourseVo ["
				+ "course_id=" + course_id 
				+ ", coach_id=" + coach_id 
				+ ", cname=" + cname 
				+ ", exp_id=" + exp_id 
				+ ", loc=" + loc 
				+ ", initstamp=" + initstamp 
				+ ", state=" + state
				+ ", intro=" + intro + "]\n";
	}	

}
