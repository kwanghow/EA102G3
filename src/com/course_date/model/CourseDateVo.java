package com.course_date.model;

import java.sql.Date;
import java.sql.Timestamp;

public class CourseDateVo implements java.io.Serializable{
	private String cdate_id;
	private String order_id;
	private java.sql.Date cdate;
	private String ctime;
	private java.sql.Timestamp initstamp;
	private Integer state;

	public CourseDateVo() {}
	
	public CourseDateVo(String cdate_id, String order_id, Date cdate, String ctime, Timestamp initstamp,
			Integer state) {
		super();
		this.cdate_id = cdate_id;
		this.order_id = order_id;
		this.cdate = cdate;
		this.ctime = ctime;
		this.initstamp = initstamp;
		this.state = state;
	}
	
	public String getCdate_id() {
		return cdate_id;
	}
	public void setCdate_id(String cdate_id) {
		this.cdate_id = cdate_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public java.sql.Date getCdate() {
		return cdate;
	}
	public void setCdate(java.sql.Date cdate) {
		this.cdate = cdate;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
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
		return "CourseDateVo [cdate_id=" + cdate_id + ", order_id=" + order_id + ", cdate=" + cdate + ", ctime=" + ctime
				+ ", initstamp=" + initstamp + ", state=" + state + "]";
	}
}
