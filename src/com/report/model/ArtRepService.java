package com.report.model;

import java.util.List;
import com.article.model.ArtDAO;
import com.article.model.ArtVO;
import com.article.model.ArtVO_interface;

public class ArtRepService {

		private ArtRepVO_interface ArtRepDAO = new ArtRepDAO();
		
		public void InsertArtRep (ArtRepVO vo) {
			ArtRepDAO.insert(vo);
		}
		
		public void DeletetArtRep (String artrepNo) {
			ArtRepDAO.delete(artrepNo);
		}
		
		public ArtRepVO updateArtRep (ArtRepVO vo) {
			ArtRepDAO.update(vo);	
			return vo;
		}
		
		public List<ArtRepVO> getAllArticle () {
			return ArtRepDAO.getAll();		
		}

		public ArtRepVO getOneArtRep(String article_no) {
			System.out.println("into_rep_service");
			return ArtRepDAO.findByPrimaryKey(article_no);
		}
		
		
	}



