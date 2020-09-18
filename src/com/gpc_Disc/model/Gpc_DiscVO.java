package com.gpc_Disc.model;
import java.sql.Timestamp;

public class Gpc_DiscVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	//
	private String gpc_Disc_Id;
	private String gpc_Id;
	private String member_Id;
	private String question;
	private String answer;
	private Timestamp init_Stamp;
	private Timestamp upl_Stamp;
	private Integer mute;
	
	//
	public String getGpc_Disc_Id() {
		return gpc_Disc_Id;
	}
	public void setGpc_Disc_Id(String gpc_Disc_Id) {
		this.gpc_Disc_Id = gpc_Disc_Id;
	}
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	public Integer getMute() {
		return mute;
	}
	public void setMute(Integer mute) {
		this.mute = mute;
	}
}
