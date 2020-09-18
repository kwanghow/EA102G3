package com.course.model;

public class CourseViewVo implements java.io.Serializable{
	private String course_id;
	private String coach_id;
	private String coach_name;
	private String coach_sex;
	private String cname;
	private String exp_id;
	private String exp_name;
	private String loc;
	private java.sql.Timestamp initstamp;
	private Integer state;
	private Integer price_max;
	private Integer price_min;
	private Integer price_avg;
	private String intro;	
	private String isFavor;
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
	public String getCoach_name() {
		return coach_name;
	}
	public void setCoach_name(String coach_name) {
		this.coach_name = coach_name;
	}
	public String getCoach_sex() {
		return coach_sex;
	}
	public void setCoach_sex(String coach_sex) {
		this.coach_sex = coach_sex;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getExp_id() {
		return exp_id;
	}
	public void setExp_id(String exp_id) {
		this.exp_id = exp_id;
	}
	public String getExp_name() {
		return exp_name;
	}
	public void setExp_name(String exp_name) {
		this.exp_name = exp_name;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
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
	public Integer getPrice_max() {
		return price_max;
	}
	public void setPrice_max(Integer price_max) {
		this.price_max = price_max;
	}
	public Integer getPrice_min() {
		return price_min;
	}
	public void setPrice_min(Integer price_min) {
		this.price_min = price_min;
	}
	public Integer getPrice_avg() {
		return price_avg;
	}
	public void setPrice_avg(Integer price_avg) {
		this.price_avg = price_avg;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getIsFavor() {
		return isFavor;
	}
	public void setIsFavor(String isFavor) {
		this.isFavor = isFavor;
	}
	
	@Override
	public String toString() {
		return "CourseViewVo [course_id=" + course_id + ", coach_id=" + coach_id + ", coach_name=" + coach_name
				+ ", coach_sex=" + coach_sex + ", cname=" + cname + ", exp_id=" + exp_id + ", exp_name=" + exp_name
				+ ", loc=" + loc + ", initstamp=" + initstamp + ", state=" + state + ", price_max=" + price_max
				+ ", price_min=" + price_min + ", price_avg=" + price_avg + ", isFavor=" + isFavor + "]\n";
	}	
	
}
