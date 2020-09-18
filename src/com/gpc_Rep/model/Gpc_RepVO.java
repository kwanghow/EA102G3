package com.gpc_Rep.model;


public class Gpc_RepVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//
	private String gpc_Rep_Id; //PK
	private String gpc_Disc_Id;
	private String member_Id;
	private String reason;
	private Integer rep_state;
	
	public String getGpc_Rep_Id() { //PK
		return gpc_Rep_Id;
	}
	public void setGpc_Rep_Id(String gpc_Rep_Id) { //PK
		this.gpc_Rep_Id = gpc_Rep_Id;
	}
	public String getGpc_Disc_Id() {
		return gpc_Disc_Id;
	}
	public void setGpc_Disc_Id(String gpc_Disc_Id) {
		this.gpc_Disc_Id = gpc_Disc_Id;
	}
	public String getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getRep_state() {
		return rep_state;
	}
	public void setRep_state(Integer rep_state) {
		this.rep_state = rep_state;
	}
	
}
