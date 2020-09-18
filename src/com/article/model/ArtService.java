package com.article.model;

import java.util.List;

public class ArtService {
	private ArtVO_interface ArtDAO = new ArtDAO();
	
	public void InsertArticle (ArtVO ArtVO) {
		ArtDAO.insert(ArtVO);
	}
	
	public ArtVO DeletetArticle (String articleNo) {
		ArtVO ArtVO = new ArtVO();
		System.out.println("into");
		ArtVO.setArticleNo(articleNo);
		ArtVO.setArticleReport(3);
		ArtDAO.delete(ArtVO);
		
		return ArtVO; 
	}
	
	public ArtVO updateArticle (ArtVO ArtVO) {
		ArtDAO.update(ArtVO);
		System.out.println("dao1");
		return ArtVO;	
	}
	public ArtVO updateReport (ArtVO ArtVO) {
		ArtDAO.update_report(ArtVO);
		System.out.println("aervice_upreport");
		return ArtVO;	
	}
	
	public List<ArtVO> getAllArticle () {
		return ArtDAO.getAll();		
	}
	
	public List<ArtVO> getAllArticleRepored () {
		return ArtDAO.getAllReported();		
	}

	public ArtVO getOneArt(String articleNo) {
		return ArtDAO.findByPrimaryKey(articleNo);
	}
	
	
}
