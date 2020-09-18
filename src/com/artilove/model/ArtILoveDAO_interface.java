
	package com.artilove.model;

	import java.util.*;

import com.artilove.model.*;;

	public interface ArtILoveDAO_interface {
		public void Insert(ArtILoveVO ArtiLoveVO);
		public void Delete(ArtILoveVO ArtILoveVO);
		public ArtILoveVO Select(String article_no,String mem_no);		
		public List<ArtILoveVO> getAll();
		public ArtILoveVO findByPrimaryKey(String articleNo);
	
		public List<ArtILoveVO> getAllByMemNo(String memberId) ;

		
		;
	}



