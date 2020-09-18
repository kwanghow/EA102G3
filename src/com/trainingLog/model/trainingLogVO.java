package com.trainingLog.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class trainingLogVO {
	
	private String trainingLog_no;
	private String member_id;
	private String trainingCat_no;
	private int weight;
	private byte[] photos;
	private String training_item;
	private Timestamp trainingLog_date;
	
	public String getTrainingLog_no() {
		return trainingLog_no;
	}
	public void setTrainingLog_no(String trainingLog_no) {
		this.trainingLog_no = trainingLog_no;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getTrainingCat_no() {
		return trainingCat_no;
	}
	public void setTrainingCat_no(String trainingCat_no) {
		this.trainingCat_no = trainingCat_no;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public byte[] getPhotos() {
		return photos;
	}
	public void setPhotos(byte[] photos) {
		this.photos = photos;
	}
	public String getTraining_item() {
		return training_item;
	}
	public void setTraining_item(String training_item) {
		this.training_item = training_item;
	}
	public Timestamp getTrainingLog_date() {
		return trainingLog_date;
	}
	public void setTrainingLog_date(Timestamp trainingLog_date) {
		this.trainingLog_date = trainingLog_date;
	}
	

}
