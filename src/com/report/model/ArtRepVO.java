package com.report.model;

public class ArtRepVO {
	private String artrepNo;  /*article report number*/
	private String articleNo;
	private String memNo;
	private String ArticleReprea; /*article report*/
	private int MsgRepResult;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ArticleReprea == null) ? 0 : ArticleReprea.hashCode());
		result = prime * result + MsgRepResult;
		result = prime * result + ((articleNo == null) ? 0 : articleNo.hashCode());
		result = prime * result + ((artrepNo == null) ? 0 : artrepNo.hashCode());
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
		ArtRepVO other = (ArtRepVO) obj;
		if (ArticleReprea == null) {
			if (other.ArticleReprea != null)
				return false;
		} else if (!ArticleReprea.equals(other.ArticleReprea))
			return false;
		if (MsgRepResult != other.MsgRepResult)
			return false;
		if (articleNo == null) {
			if (other.articleNo != null)
				return false;
		} else if (!articleNo.equals(other.articleNo))
			return false;
		if (artrepNo == null) {
			if (other.artrepNo != null)
				return false;
		} else if (!artrepNo.equals(other.artrepNo))
			return false;
		if (memNo == null) {
			if (other.memNo != null)
				return false;
		} else if (!memNo.equals(other.memNo))
			return false;
		return true;
	}
	public String getArtrepNo() {
		return artrepNo;
	}
	public void setArtrepNo(String artrepNo) {
		this.artrepNo = artrepNo;
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
	public String getArticleReprea() {
		return ArticleReprea;
	}
	public void setArticleReprea(String articleReprea) {
		ArticleReprea = articleReprea;
	}
	public int getMsgRepResult() {
		return MsgRepResult;
	}
	public void setMsgRepResult(int string) {
		MsgRepResult = string;
	}
	
	

}
