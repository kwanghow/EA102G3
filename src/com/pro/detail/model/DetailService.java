package com.pro.detail.model;

import java.util.List;


public class DetailService {

	private DetailDAO_interface dao;

	public DetailService() {
		dao = new DetailDAO();
	}
	

	

	public List<DetailVO> getAllOrder(String order_id) {
		List<DetailVO> list = dao.getAllByOrderNo(order_id);
		 for(DetailVO DVO : list){
	 	System.out.println("DVO.getProduct_name()=" + DVO.getProduct_name());
	 }
		return list;
	}
	public void delete(String order_id) {
		 dao.delete(order_id);
	}
}
