package com.ArtMes.model;

import java.util.Date;
import java.util.List;

import java.util.List;

	public class ArtMesService {
		private ArtMes_interface ArtMesDAO = new ArtMesDAO();
		
		public void InsertArtMes (ArtMesVO vo) {
			ArtMesDAO.insert(vo);
		}
		
		public void DeletetArtMes (String msgNo) {
			ArtMesDAO.delete(msgNo);
		}
		
		public ArtMesVO updateArticle (ArtMesVO ArtMesVO) {
			ArtMesDAO.update(ArtMesVO);	
			return ArtMesVO;
			
		}
		
		public List<ArtMesVO> getAllMesByArticleNo (String articleNo) {
			
			return ArtMesDAO.getAll(articleNo);		
		}

		public ArtMesVO getOneArt(String msgNo) {
			return ArtMesDAO.findByPrimaryKey(msgNo);
		}
		
		
	}
	
	


