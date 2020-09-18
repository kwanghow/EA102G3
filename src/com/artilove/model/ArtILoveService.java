package com.artilove.model;

import java.util.List;

import com.article.model.ArtVO;

public class ArtILoveService {
		private ArtILoveDAO_interface ArtILoveDAO=new ArtILoveDAO();
		
		public void InsertILove (ArtILoveVO ArtILoveVO) {
			ArtILoveDAO.Insert(ArtILoveVO);	
		}
		
		public void  DeleteILove (String MemNo,String articleNo) {
			ArtILoveVO ArtILoveVO = new ArtILoveVO();
			System.out.println("into");
			ArtILoveVO.setArticleNo(articleNo);
			ArtILoveVO.setMemNo(MemNo);

			
			return ; 
		}
		
		public ArtILoveVO updateILove (ArtILoveVO ArtILoveVO) {
			System.out.println("dao1");
			return ArtILoveVO;	
		}
		
			public List<ArtILoveVO> getAllArtILove () {
				return ArtILoveDAO.getAll();		
			}
			
			public List<ArtILoveVO> getAllArtILoveByMemNo(String memNo) {
				return ArtILoveDAO.getAllByMemNo(memNo);		
			}
			

			public ArtILoveVO getOneArt(String article_no,String mem_no) {
				return ArtILoveDAO.Select(article_no, mem_no);
			}
}
