package com.calendar.model;

public class CalMemberViewVo implements java.io.Serializable{
	private String member_id;
	private java.sql.Date view_date;
	private String view_time;
	private String view_event;
	private String view_desc;
	private Integer view_state;
		
	public CalMemberViewVo() {}
	
	public CalMemberViewVo(String member_id, java.sql.Date view_date, String view_time, String view_event, String view_desc,
			Integer view_state) {
		super();
		this.member_id = member_id;
		this.view_date = view_date;
		this.view_time = view_time;
		this.view_event = view_event;
		this.view_desc = view_desc;
		this.view_state = view_state;
	}
	
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public java.sql.Date getView_date() {
		return view_date;
	}
	public void setView_date(java.sql.Date view_date) {
		this.view_date = view_date;
	}
	public String getView_time() {
		return view_time;
	}
	public void setView_time(String view_time) {
		this.view_time = view_time;
	}
	public String getView_event() {
		return view_event;
	}
	public void setView_event(String view_event) {
		this.view_event = view_event;
	}
	public String getView_desc() {
		return view_desc;
	}
	public void setView_desc(String view_desc) {
		this.view_desc = view_desc;
	}
	public Integer getView_state() {
		return view_state;
	}
	public void setView_state(Integer view_state) {
		this.view_state = view_state;
	}
	
	@Override
	public String toString() {
		return "CalMemberViewVo [member_id=" + member_id + ", view_date=" + view_date + ", view_time=" + view_time
				+ ", view_event=" + view_event + ", view_desc=" + view_desc + ", view_state=" + view_state + "]";
	}	
	
}
