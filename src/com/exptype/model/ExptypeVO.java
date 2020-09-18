package com.exptype.model;

public class ExptypeVO implements java.io.Serializable{
	private String exp_Id;
	private String exp_Name;
	private byte[] exp_Img;
	private String exp_Words;
	
	public ExptypeVO() {
		
	}

	public String getExp_Id() {
		return exp_Id;
	}

	public void setExp_Id(String exp_Id) {
		this.exp_Id = exp_Id;
	}

	public String getExp_Name() {
		return exp_Name;
	}

	public void setExp_Name(String exp_Name) {
		this.exp_Name = exp_Name;
	}

	public byte[] getExp_Img() {
		return exp_Img;
	}

	public void setExp_Img(byte[] exp_Img) {
		this.exp_Img = exp_Img;
	}

	public String getExp_Words() {
		return exp_Words;
	}

	public void setExp_Words(String exp_Words) {
		this.exp_Words = exp_Words;
	}
	
	
	
}
