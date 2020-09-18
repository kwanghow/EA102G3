package com.articlecat.model;

public class ArtCatVO {
	private String articleCatNo;
	private String articleCatName;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleCatName == null) ? 0 : articleCatName.hashCode());
		result = prime * result + ((articleCatNo == null) ? 0 : articleCatNo.hashCode());
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
		ArtCatVO other = (ArtCatVO) obj;
		if (articleCatName == null) {
			if (other.articleCatName != null)
				return false;
		} else if (!articleCatName.equals(other.articleCatName))
			return false;
		if (articleCatNo == null) {
			if (other.articleCatNo != null)
				return false;
		} else if (!articleCatNo.equals(other.articleCatNo))
			return false;
		return true;
	}
	public String getArticleCatNo() {
		return articleCatNo;
	}
	public void setArticleCatNo(String articleCatNo) {
		this.articleCatNo = articleCatNo;
	}
	public String getArticleCatName() {
		return articleCatName;
	}
	public void setArticleCatName(String articleCatName) {
		this.articleCatName = articleCatName;
	}
	
}
