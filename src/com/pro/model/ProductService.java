package com.pro.model;

import java.util.List;
import java.util.Set;

import com.mysql.jdbc.StringUtils;
import com.pro.model.ProVO;

public class ProductService {

	private ProDAO_interface dao;

	public ProductService() {
		dao = new ProDAO();
	}
	
	
	
	public ProVO addProduct(String Catagory_Id,String Product_Name,int Product_Price,String Product_Detail,int Product_Stock,String Product_Brand,byte[] Product_Lpic,byte[] Product_Lpic1,byte[] Product_Spic) {
		System.out.println("Service_addpro開始");
		ProVO ProVO = new ProVO();		
		ProVO.setCategory_Id(Catagory_Id);
		ProVO.setProduct_Name(Product_Name);
		ProVO.setProduct_Price(Product_Price);
		ProVO.setProduct_Detail(Product_Detail);
		ProVO.setProduct_Lpic(Product_Lpic);
		ProVO.setProduct_Lpic1(Product_Lpic1);
		ProVO.setProduct_Spic(Product_Spic);
//		ProVO.setProduct_Status(Product_Status);
		ProVO.setProduct_Stock(Product_Stock);
		ProVO.setProduct_Brand(Product_Brand);		
		dao.ProInsert(ProVO);
		System.out.println("Proadd_service_ok");
		return ProVO;
	}
	public List<ProVO> getAll () {
			return dao.getAll();
	}
	public List<ProVO> getAll_back () {
		return dao.getAllback();
	}
	public List<ProVO> getAll_front () {
		return dao.getAllfront();
	}
	
	public List<ProVO> getAllByCat (String category_Id) {
//		System.out.println("到SERVICE GETCAT");
//		System.out.println(category_Id);
//		System.out.println(StringUtils.isNullOrEmpty(category_Id));
		System.out.println("category_Id == null" + category_Id == null);
		if (category_Id == null) {
			System.out.println("到GETALL");
			return dao.getAll();
		} else {
			System.out.println("到CATGETALL");
			return dao.getAllByCat(category_Id);
		}
		
		
		
	}
	

	public ProVO getOneProduct(String product_Id) {
		System.out.println("into_getOneProduct_service");
		return dao.ProSelect(product_Id);
	}
	public void deleteProduct(String product_Id) {
		ProVO ProVO = new ProVO();
		System.out.println("into_service_del");
		
		ProVO.setProduct_Id(product_Id);
		ProVO.setProduct_Status(1);
		dao.ProDelete(ProVO);
		
	}
	public ProVO ProUpdate(String Product_Id,String Category_Id,String Product_Name,int Product_Price,String Product_Detail,int Product_Status,int Product_Stock,String Product_Brand,int Product_Hot,byte[] Product_Lpic,byte[] Product_Lpic1,byte[] Product_Spic) {
		
		
		ProVO ProVO = new ProVO();		
		ProVO.setProduct_Id(Product_Id);
		ProVO.setCategory_Id(Category_Id);
		ProVO.setProduct_Name(Product_Name);
		ProVO.setProduct_Price(Product_Price);
		ProVO.setProduct_Detail(Product_Detail);
		ProVO.setProduct_Lpic(Product_Lpic);
		ProVO.setProduct_Lpic1(Product_Lpic1);
		ProVO.setProduct_Spic(Product_Spic);
		ProVO.setProduct_Status(Product_Status);
		ProVO.setProduct_Stock(Product_Stock);
		ProVO.setProduct_Brand(Product_Brand);
		ProVO.setProduct_Hot(Product_Hot);
		
		dao.ProUpdate(ProVO);
		System.out.println("SERVISE_UPDATE_ok");
		return ProVO;
	}
}
