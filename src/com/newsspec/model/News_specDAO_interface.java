package com.newsspec.model;

import java.util.List;
import java.util.Set;

import com.news.model.NewsVO;

public interface News_specDAO_interface {
	public void insert(News_specVO news_specVO);

	public void update(News_specVO news_specVO);

	public void delete(String news_spec);

	public News_specVO findByPrimaryKey(String news_spec);

	public List<News_specVO> getAll();

	// 查詢某部門的員工(一對多)(回傳 Set)
	public Set<NewsVO> getNewsByNews_spec(String news_spec);
}
