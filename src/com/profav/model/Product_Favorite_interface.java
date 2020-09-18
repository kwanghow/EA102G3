package com.profav.model;

import java.util.List;


public interface Product_Favorite_interface {
	  public void insert(Product_FavoriteVO Product_FavoriteVO);
      public void delete(String member_id ,String prod_no);
      public List<Product_FavoriteVO> findByPrimaryKey(String member_id);
      public List<Product_FavoriteVO> getAll();
      public Product_FavoriteVO getOneFavor(String member_id, String product_id);
	

}
