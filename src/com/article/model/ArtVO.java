package com.article.model;
import java.util.Date;

public class ArtVO implements java.io.Serializable{
		private String articleNo;
		private String memId;
		private String articleCatNo;
		private String articleTitle;
		private String articleTitleSub;
		private String articleAuthor;
		private String articleContent;
		private Date postTime;
		private int articleReport;
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((articleAuthor == null) ? 0 : articleAuthor.hashCode());
			result = prime * result + ((articleCatNo == null) ? 0 : articleCatNo.hashCode());
			result = prime * result + ((articleContent == null) ? 0 : articleContent.hashCode());
			result = prime * result + ((articleNo == null) ? 0 : articleNo.hashCode());
			result = prime * result + articleReport;
			result = prime * result + ((articleTitle == null) ? 0 : articleTitle.hashCode());
			result = prime * result + ((articleTitleSub == null) ? 0 : articleTitleSub.hashCode());
			result = prime * result + ((memId == null) ? 0 : memId.hashCode());
			result = prime * result + ((postTime == null) ? 0 : postTime.hashCode());
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
			ArtVO other = (ArtVO) obj;
			if (articleAuthor == null) {
				if (other.articleAuthor != null)
					return false;
			} else if (!articleAuthor.equals(other.articleAuthor))
				return false;
			if (articleCatNo == null) {
				if (other.articleCatNo != null)
					return false;
			} else if (!articleCatNo.equals(other.articleCatNo))
				return false;
			if (articleContent == null) {
				if (other.articleContent != null)
					return false;
			} else if (!articleContent.equals(other.articleContent))
				return false;
			if (articleNo == null) {
				if (other.articleNo != null)
					return false;
			} else if (!articleNo.equals(other.articleNo))
				return false;
			if (articleReport != other.articleReport)
				return false;
			if (articleTitle == null) {
				if (other.articleTitle != null)
					return false;
			} else if (!articleTitle.equals(other.articleTitle))
				return false;
			if (articleTitleSub == null) {
				if (other.articleTitleSub != null)
					return false;
			} else if (!articleTitleSub.equals(other.articleTitleSub))
				return false;
			if (memId == null) {
				if (other.memId != null)
					return false;
			} else if (!memId.equals(other.memId))
				return false;
			if (postTime == null) {
				if (other.postTime != null)
					return false;
			} else if (!postTime.equals(other.postTime))
				return false;
			return true;
		}
		public String getArticleNo() {
			return articleNo;
		}
		public void setArticleNo(String articleNo) {
			this.articleNo = articleNo;
		}
		public String getMemId() {
			return memId;
		}
		public void setMemId(String memId) {
			this.memId = memId;
		}
		public String getArticleCatNo() {
			return articleCatNo;
		}
		public void setArticleCatNo(String articleCatNo) {
			this.articleCatNo = articleCatNo;
		}
		public String getArticleTitle() {
			return articleTitle;
		}
		public void setArticleTitle(String articleTitle) {
			this.articleTitle = articleTitle;
		}
		public String getArticleTitleSub() {
			return articleTitleSub;
		}
		public void setArticleTitleSub(String articleTitleSub) {
			this.articleTitleSub = articleTitleSub;
		}
		public String getArticleAuthor() {
			return articleAuthor;
		}
		public void setArticleAuthor(String articleAuthor) {
			this.articleAuthor = articleAuthor;
		}
		public String getArticleContent() {
			return articleContent;
		}
		public void setArticleContent(String articleContent) {
			this.articleContent = articleContent;
		}
		public Date getPostTime() {
			return postTime;
		}
		public void setPostTime(Date postTime) {
			this.postTime = postTime;
		}
		public int getArticleReport() {
			return articleReport;
		}
		public void setArticleReport(int articleReport) {
			this.articleReport = articleReport;
		}
		
		
}
		
		
		
	