package com.ArtMes.model;
import java.util.List;

	public interface ArtMes_interface {	
		public void insert(ArtMesVO ArtMesVO);
		public void update(ArtMesVO ArtMesVO);
		public void delete(String msgNo);
		public ArtMesVO findByPrimaryKey(String msgNo);
		public List<ArtMesVO> getAll();
		public List<ArtMesVO> getAll(String ArticleNo);
	
		          

	}

	
	

