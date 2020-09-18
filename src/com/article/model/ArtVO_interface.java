package com.article.model;

import java.util.List;
public interface ArtVO_interface {
	public void insert(ArtVO artVO);
	public void update(ArtVO artVO);
	public void delete(ArtVO artVO);
	public ArtVO findByPrimaryKey(String articleNo);
	public List<ArtVO> getAll();
	public void update_report(ArtVO artVO);
	public List<ArtVO> getAllReported() ;
	public List<ArtVO> getAllLoved() ;


	
}
