package com.ArtMes.model;

import java.util.Date;

import com.mem.model.MemVO;

public class ArtMesVO implements java.io.Serializable {
	private String msgNo;
	private String articleNo;
	private String memNo;
	private Date msgTime;
	private String msgCont;
	private String memberName;
	
	private MemVO memVO;
	
	
	@Override 
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleNo == null) ? 0 : articleNo.hashCode());
		result = prime * result + ((memNo == null) ? 0 : memNo.hashCode());
		result = prime * result + ((msgCont == null) ? 0 : msgCont.hashCode());
		result = prime * result + ((msgNo == null) ? 0 : msgNo.hashCode());
		result = prime * result + ((msgTime == null) ? 0 : msgTime.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtMesVO other = (ArtMesVO) obj;
		if (articleNo == null) {
			if (other.articleNo != null)
				return false;
		} else if (!articleNo.equals(other.articleNo))
			return false;
		if (memNo == null) {
			if (other.memNo != null)
				return false;
		} else if (!memNo.equals(other.memNo))
			return false;
		if (msgCont == null) {
			if (other.msgCont != null)
				return false;
		} else if (!msgCont.equals(other.msgCont))
			return false;
		if (msgNo == null) {
			if (other.msgNo != null)
				return false;
		} else if (!msgNo.equals(other.msgNo))
			return false;
		if (msgTime == null) {
			if (other.msgTime != null)
				return false;
		} else if (!msgTime.equals(other.msgTime))
			return false;
		return true;
	}
	
	
	
	
	public MemVO getMemVO() {
		return memVO;
	}


	public void setMemVO(MemVO memVO) {
		this.memVO = memVO;
	}


	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public Date getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(Date msgTime) {
		this.msgTime = msgTime;
	}
	public String getMsgCont() {
		return msgCont;
	}
	public void setMsgCont(String msgCont) {
		this.msgCont = msgCont;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	
	
}