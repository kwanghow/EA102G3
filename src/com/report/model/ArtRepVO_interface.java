package com.report.model;

	import java.util.List;
	public interface ArtRepVO_interface {
		public void insert(ArtRepVO ArtRepVO);
		public void update(ArtRepVO ArtRepVO);
		public void delete(String artrepNo);
		public ArtRepVO findByPrimaryKey(String article_no);
		public List<ArtRepVO> getAll();	
	
}
