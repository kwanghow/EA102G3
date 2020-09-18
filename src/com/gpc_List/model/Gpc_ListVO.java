package com.gpc_List.model;
import java.sql.Date;
import java.sql.Timestamp;

public class Gpc_ListVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//
	private String gpc_Id;		// 複合主鍵
	private String member_Id;	// 複合主鍵
//	private Integer numcard;	// 其實不需要
	private Date pay_Init;
	private Date pay_Exp;
	private Timestamp init_Stamp;
	private Timestamp upl_Stamp;
	private Integer mem_State;
	
	//
	public String getGpc_Id() {
		return gpc_Id;
	}
	public void setGpc_Id(String gpc_Id) {
		this.gpc_Id = gpc_Id;
	}
	public String getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}
// 其實不需要	
//	public Integer getNumcard() {
//		return numcard;
//	}
//	public void setNumcard(Integer numcard) {
//		this.numcard = numcard;
//	}
	public Date getPay_Init() {
		return pay_Init;
	}
	public void setPay_Init(Date pay_Init) {
		this.pay_Init = pay_Init;
	}
	public Date getPay_Exp() {
		return pay_Exp;
	}
	public void setPay_Exp(Date pay_Exp) {
		this.pay_Exp = pay_Exp;
	}
	public Timestamp getInit_Stamp() {
		return init_Stamp;
	}
	public void setInit_Stamp(Timestamp init_Stamp) {
		this.init_Stamp = init_Stamp;
	}
	public Timestamp getUpl_Stamp() {
		return upl_Stamp;
	}
	public void setUpl_Stamp(Timestamp upl_Stamp) {
		this.upl_Stamp = upl_Stamp;
	}
	public Integer getMem_State() {
		return mem_State;
	}
	public void setMem_State(Integer mem_State) {
		this.mem_State = mem_State;
	}
	
	

}
