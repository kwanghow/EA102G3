package com.news.model;

import java.util.List;

import com.news.model.NewsDAO;
import com.news.model.NewsDAO_interface;

public class NewsService {
	private NewsDAO_interface dao;
	public NewsService() {
		dao = new NewsDAO();
	}
	public NewsVO addNews(String news_spec,String emp_id,
			String news_content,String news_header) {
		NewsVO newsVO=new NewsVO();
		newsVO.setNews_spec(news_spec);
		newsVO.setEmp_id(emp_id);
		newsVO.setNews_content(news_content);
		newsVO.setNews_header(news_header);
		
		dao.insert(newsVO);
		return newsVO;
		
	}
	public NewsVO updateNews(String news_spec,String emp_id,
			String news_content,String news_header,String news_id) {
		NewsVO newsVO = new NewsVO();
		newsVO.setEmp_id(emp_id);
		newsVO.setNews_content(news_content);
		newsVO.setNews_header(news_header);
		newsVO.setNews_spec(news_spec);
		newsVO.setNews_id(news_id);
		dao.update(newsVO);
		
		return newsVO;
		
	}
	public void deleteNews(String news_id) {
		dao.delete(news_id);
		
	}
	public NewsVO getOneNews(String news_id) {
		return dao.findByPrimaryKey(news_id);
		
	}
	public List<NewsVO> getAll(){
		return dao.getAll();
	}
	public List<NewsVO> getContent(String news_content){
		return dao.getContent(news_content);
	}
	

}
