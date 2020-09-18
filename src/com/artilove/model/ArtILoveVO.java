package com.artilove.model;

import java.util.Date;

import com.article.model.ArtVO;

public class ArtILoveVO implements java.io.Serializable{
	private String articleNo;
	private String memNo;
	private ArtVO artVO;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleNo == null) ? 0 : articleNo.hashCode());
		result = prime * result + ((memNo == null) ? 0 : memNo.hashCode());
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
		ArtILoveVO other = (ArtILoveVO) obj;
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
		return true;
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
	public ArtVO getArtVO() {
		return artVO;
	}
	public void setArtVO(ArtVO artVO) {
		this.artVO = artVO;
	}


}

