package com.course.model;

public class CoursePojo {
		
	private String course_id; // CourseVo
	private String cname;
	private String loc;	
private String pic1url;
private String courseUrl;	
	private String coach_name; // MemVO	
private String coach_img; // CoachVO
private String coachUrl;	
	private String exp_name; // ExptypeVO	
	private String lowPrice; // CourseSetVo
	private String HighPrice;	
private String isFavor; // CourseFavorVo
	
	public CoursePojo() {}

	public String getCourse_id() {
		return course_id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getPic1url() {
		return pic1url;
	}

	public void setPic1url(String pic1url) {
		this.pic1url = pic1url;
	}

	public String getCourseUrl() {
		return courseUrl;
	}

	public void setCourseUrl(String courseUrl) {
		this.courseUrl = courseUrl;
	}

	public String getCoach_name() {
		return coach_name;
	}

	public void setCoach_name(String coach_name) {
		this.coach_name = coach_name;
	}

	public String getCoach_img() {
		return coach_img;
	}

	public void setCoach_img(String coach_img) {
		this.coach_img = coach_img;
	}

	public String getCoachUrl() {
		return coachUrl;
	}

	public void setCoachUrl(String coachUrl) {
		this.coachUrl = coachUrl;
	}

	public String getExp_name() {
		return exp_name;
	}

	public void setExp_name(String exp_name) {
		this.exp_name = exp_name;
	}

	public String getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(String lowPrice) {
		this.lowPrice = lowPrice;
	}

	public String getHighPrice() {
		return HighPrice;
	}

	public void setHighPrice(String highPrice) {
		HighPrice = highPrice;
	}

	public String getIsFavor() {
		return isFavor;
	}

	public void setIsFavor(String isFavor) {
		this.isFavor = isFavor;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

}
