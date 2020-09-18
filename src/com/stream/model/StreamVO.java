package com.stream.model;

import java.sql.Date;

public class StreamVO implements java.io.Serializable{
	private String stream_id;
	private String coach_id;
	private byte[]  stream_vod;
	private String  stream_header;
	private Date    stream_notice;
	private Integer   stream_status;
	
	public Date getStream_notice() {
		return stream_notice;
	}

	public void setStream_notice(Date stream_notice) {
		this.stream_notice = stream_notice;
	}

	public StreamVO() {
		
	}

	public String getStream_id() {
		return stream_id;
	}

	public void setStream_id(String stream_id) {
		this.stream_id = stream_id;
	}

	public String getCoach_id() {
		return coach_id;
	}

	public void setCoach_id(String coach_id) {
		this.coach_id = coach_id;
	}

	public byte[] getStream_vod() {
		return stream_vod;
	}

	public void setStream_vod(byte[] stream_vod) {
		this.stream_vod = stream_vod;
	}

	public String getStream_header() {
		return stream_header;
	}

	public void setStream_header(String stream_header) {
		this.stream_header = stream_header;
	}

	public Integer getStream_status() {
		return stream_status;
	}

	public void setStream_status(Integer stream_status) {
		this.stream_status = stream_status;
	}

	
	
	
	
	
	
}
