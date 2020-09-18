package com.mem.model;

import java.sql.Date;

public class MemVO implements java.io.Serializable{
	private String member_Id;
	private String mem_Name;
	private String mem_Sex;
	private String mem_Account;
	private String mem_Psw;
	private byte[] mem_Img;
	private String mem_Email;
	private String mem_Phone;
	private Date mem_Birth;
	private String mem_Addr;
	private Integer mem_Close;
	private String mem_Card;
	
	public MemVO() {			
	}
	
	public String getMember_Id() {
		return member_Id;
	}
	public void setMember_Id(String member_Id) {
		this.member_Id = member_Id;
	}
	public String getMem_Name() {
		return mem_Name;
	}
	public void setMem_Name(String mem_Name) {
		this.mem_Name = mem_Name;
	}
	public String getMem_Sex() {
		return mem_Sex;
	}
	public void setMem_Sex(String mem_Sex) {
		this.mem_Sex = mem_Sex;
	}
	public String getMem_Account() {
		return mem_Account;
	}
	public void setMem_Account(String mem_Account) {
		this.mem_Account = mem_Account;
	}
	public String getMem_Psw() {
		return mem_Psw;
	}
	public void setMem_Psw(String mem_Psw) {
		this.mem_Psw = mem_Psw;
	}
	public byte[] getMem_Img() {
		return mem_Img;
	}
	public void setMem_Img(byte[] mem_Img) {
		this.mem_Img = mem_Img;
	}
	public String getMem_Email() {
		return mem_Email;
	}
	public void setMem_Email(String mem_Email) {
		this.mem_Email = mem_Email;
	}
	public String getMem_Phone() {
		return mem_Phone;
	}
	public void setMem_Phone(String mem_Phone) {
		this.mem_Phone = mem_Phone;
	}
	public Date getMem_Birth() {
		return mem_Birth;
	}
	public void setMem_Birth(Date mem_Birth) {
		this.mem_Birth = mem_Birth;
	}
	public String getMem_Addr() {
		return mem_Addr;
	}
	public void setMem_Addr(String mem_Addr) {
		this.mem_Addr = mem_Addr;
	}
	public Integer getMem_Close() {
		return mem_Close;
	}
	public void setMem_Close(Integer mem_Close) {
		this.mem_Close = mem_Close;
	}
	public String getMem_Card() {
		return mem_Card;
	}
	public void setMem_Card(String mem_Card) {
		this.mem_Card = mem_Card;
	}
		
}
