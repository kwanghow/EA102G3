package com.course_order.model;

public class COrderViewVo implements java.io.Serializable{
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
	private String course_id;
	private String coach_id;
	private String cname;
	private String exp_name;
	private String loc;
	private String pic1url;	

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
	public String getPic1url() {
		return pic1url;
	}
	public void setPic1url(String pic1url) {
		this.pic1url = pic1url;
	}
	
	@Override
	public String toString() {
		return "COrderViewVo [order_id=" + order_id + ", member_id=" + member_id + ", set_id=" + set_id + ", lesson="
				+ lesson + ", order_price=" + order_price + ", coupon_id=" + coupon_id + ", disc=" + disc
				+ ", total_price=" + total_price + ", book_lesson=" + book_lesson + ", initstamp=" + initstamp
				+ ", com_star=" + com_star + ", com_content=" + com_content + ", com_date=" + com_date + ", state="
				+ state + ", course_id=" + course_id + ", coach_id=" + coach_id + ", cname=" + cname + ", exp_name="
				+ exp_name + ", loc=" + loc + "]";
	}
	
}
