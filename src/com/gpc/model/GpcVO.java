package com.gpc.model;
import java.sql.Date;
import java.sql.Timestamp;

public class GpcVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	private String gpc_Id;
	private String coach_Id;
	private String exp_Id;
	private String gpc_Name;
	private String address;
	private String intro;
	private byte[] pic1;
	private byte[] pic2;
	private byte[] pic3;
	private Integer price;
	private Date pay_Start;
	private Date gpc_Start;
	private Integer mem_Min;
	private Integer mem_Max;
	private Timestamp init_Stamp;
	private Timestamp upl_Stamp;
	private Integer gpc_State;
	
	public String getGpc_Id() {
		return gpc_Id;
	}
	public void setGpc_Id(String gpc_Id) {
		this.gpc_Id = gpc_Id;
	}
	public String getCoach_Id() {
		return coach_Id;
	}
	public void setCoach_Id(String coach_Id) {
		this.coach_Id = coach_Id;
	}
	public String getExp_Id() {
		return exp_Id;
	}
	public void setExp_Id(String exp_Id) {
		this.exp_Id = exp_Id;
	}
	public String getGpc_Name() {
		return gpc_Name;
	}
	public void setGpc_Name(String gpc_Name) {
		this.gpc_Name = gpc_Name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
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
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Date getPay_Start() {
		return pay_Start;
	}
	public void setPay_Start(Date pay_Start) {
		this.pay_Start = pay_Start;
	}
	public Date getGpc_Start() {
		return gpc_Start;
	}
	public void setGpc_Start(Date gpc_Start) {
		this.gpc_Start = gpc_Start;
	}
	public Integer getMem_Min() {
		return mem_Min;
	}
	public void setMem_Min(Integer mem_Min) {
		this.mem_Min = mem_Min;
	}
	public Integer getMem_Max() {
		return mem_Max;
	}
	public void setMem_Max(Integer mem_Max) {
		this.mem_Max = mem_Max;
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
	public Integer getGpc_State() {
		return gpc_State;
	}
	public void setGpc_State(Integer gpc_State) {
		this.gpc_State = gpc_State;
	}
	
	
	
}
