package com.articlecat.model;

import java.util.List;

public class ArtCatService {
private ArtCatVO_interface ArtCatDAO = new ArtCatDAO();
	
	public void InsertArtCat (ArtCatVO vo) {
		ArtCatDAO.insert(vo);
	}
	
	public void DeletetArtCat (String articleCatNo) {
		ArtCatDAO.delete(articleCatNo);
	}
	
	public void updateArticle (ArtCatVO vo) {
		ArtCatDAO.update(vo);	
	}
	
	public List<ArtCatVO> getall () {
		return ArtCatDAO.getAll();		
	}

	public ArtCatVO getOneArtCat(String articleCatNo) {
		return ArtCatDAO.findByPrimaryKey(articleCatNo);
	}
	

}
