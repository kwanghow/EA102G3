package com.product_category.model;

import java.util.List;
import java.util.Set;

import com.pro.model.ProVO;

public class CategoryService {

	private Product_categoryDAO_interface dao;

	public CategoryService() {
		dao = new Product_categoryJDBCDAO();
	}

	public Product_categoryVO addCategory(String category_name) {
		Product_categoryVO product_categoryVO = new Product_categoryVO();
		product_categoryVO.setCategory_Name(category_name);

		return product_categoryVO;
	}

	public List<Product_categoryVO> getAll() {
		System.out.println("¨Ó¥ÎGETALL¤F");
		return dao.getAll();
	}

	public Product_categoryVO getOneCategory(String category_Id) {
		return dao.findByPrimaryKey(category_Id);
	}

	public Set<ProVO> getProductsByCategory(String category_Id) {
		return dao.getProductsByCategory(category_Id);
	}

}
