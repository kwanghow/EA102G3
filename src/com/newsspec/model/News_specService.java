package com.newsspec.model;

import java.util.List;
import java.util.Set;

import com.news.model.NewsVO;

public class News_specService {
	private News_specDAO_interface dao;

	public News_specService() {
		dao = new News_specDAO();
	}

	public News_specVO addNews_spec(String news_spec,String news_specheader) {
		News_specVO news_specVO = new News_specVO();
		news_specVO.setNews_spec(news_spec);
		news_specVO.setNews_specheader(news_specheader);
		dao.insert(news_specVO);
		return news_specVO;
		
		
	}
	
	public void deleteNews_spec(String news_spec) {
		dao.delete(news_spec);
	}
	public News_specVO getOneNews_spec(String news_spec) {
		return dao.findByPrimaryKey(news_spec);
	}
	public List<News_specVO> getAll(){
		return dao.getAll();
	}
	public Set<NewsVO> getNewsByNews_spec(String news_spec){
		return dao.getNewsByNews_spec(news_spec);
	}
	
	
	
}
