package com.product_category.model;

import java.util.List;
import java.util.Set;

import com.pro.model.ProVO;




public interface Product_categoryDAO_interface {
    public void insert(Product_categoryVO product_categoryVO);
    public void update(Product_categoryVO product_categoryVO);
    public Product_categoryVO findByPrimaryKey(String category_Id);
    public List<Product_categoryVO> getAll();
    public Set<ProVO> getProductsByCategory(String category_Id);
  
  
  
  
}
