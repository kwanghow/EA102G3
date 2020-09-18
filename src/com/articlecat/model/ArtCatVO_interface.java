package com.articlecat.model;

import java.util.List;


	public interface ArtCatVO_interface {
	public void insert(ArtCatVO artCatVO);
	public void update(ArtCatVO artCatVO);
	public void delete(String articleCatNo);
	public ArtCatVO findByPrimaryKey(String articleCatNo);
	public List<ArtCatVO> getAll();
	
}


