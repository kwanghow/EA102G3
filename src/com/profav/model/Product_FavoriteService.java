package com.profav.model;

import java.util.List;

import com.profav.model.Product_FavoriteJDBCDAO;

public class Product_FavoriteService {
	private Product_Favorite_interface dao;

	public Product_FavoriteService() {
		dao = new Product_FavoriteJDBCDAO();
	}
	public Product_FavoriteVO addFav(String member_id ,String product_id ) {
		Product_FavoriteVO FAVO = new Product_FavoriteVO();
		FAVO.setMember_id(member_id);
		FAVO.setProduct_id(product_id);
		dao.insert(FAVO);
		System.out.println("fav insert ok");
		return FAVO;
	}


	public List<Product_FavoriteVO> getAll() {
		return dao.getAll();
	}

	public List<Product_FavoriteVO> getFavBymemno(String member_id) {
		return dao.findByPrimaryKey(member_id);
	}

	public void deleteFav(String member_id, String product_id) {
		dao.delete(member_id, product_id);
	}

	public Product_FavoriteVO getOneFavor(String member_id, String product_id) {
		return dao.getOneFavor(member_id, product_id);
	}
	
	public static void main(String[] args) {
		Product_FavoriteService svc = new Product_FavoriteService ();
		System.out.println(svc.getOneFavor("M001","P001"));
		
	}
}
