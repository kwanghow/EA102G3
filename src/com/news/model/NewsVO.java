package com.news.model;

public class NewsVO implements java.io.Serializable {
	private String news_id;
	private String news_spec;
	private String emp_id;
	private String news_content;
	private String news_header;
    public NewsVO() {
    	
    }
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	public String getNews_spec() {
		return news_spec;
	}
	public void setNews_spec(String news_spec) {
		this.news_spec = news_spec;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	public String getNews_header() {
		return news_header;
	}
	public void setNews_header(String news_header) {
		this.news_header = news_header;
	}
    
}
