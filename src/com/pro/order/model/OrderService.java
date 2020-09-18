package com.pro.order.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.pro.cart.model.Cart;
import com.pro.order.model.OrderVO;

public class OrderService {

	private OrderDAO dao;

	public OrderService() {
		dao = new OrderDAO();
	}
	

//	String member_id,  String coupon_id, int order_pay, int delivery, String order_address, int order_fee
//	, int order_status, String creditCard_Num, String last_Three_Num
	
	
	public OrderVO addOrder(String member_id, int order_pay, int delivery, String order_address, int order_fee, int order_status) {
		
		System.out.println("Service_addOrd Start");
		OrderVO OrderVO = new OrderVO();
		OrderVO.setMember_id(member_id);
		OrderVO.setOrder_pay(order_pay);
		OrderVO.setDelivery(delivery);
		OrderVO.setOrder_address(order_address);
		OrderVO.setOrder_fee(order_fee);
		OrderVO.setOrder_status(order_status);
		dao.insert(OrderVO);
		System.out.println("ProINS成功" + member_id );
		return OrderVO;
	}

	public List<OrderVO> getAll() {
		return dao.getAll();
	}
	public List<OrderVO> getAll(String member_id) {
		return dao.getAllOrderByMemNo_All(member_id);
	}
	public List<OrderVO> getAll_Pay(String member_id) {
		return dao.getAllOrderByMemNo_Pay(member_id);
	}
	public List<OrderVO> getAll_Com(String member_id) {
		return dao.getAllOrderByMemNo_Com(member_id);
	}
	public List<OrderVO> getAll_Can(String member_id) {
		return dao.getAllOrderByMemNo_Can(member_id);
	}

	public void complete_order(String order_id) {
		dao.complete_order(order_id);
	}
	public void cancel_order(String order_id) {
		dao.cancel_order(order_id);
	}
	
	
	public OrderVO getOneOrder(String order_id) {
		System.out.println("到達GETONEPRODUCT");
		return dao.findByPrimaryKey(order_id);
	}
	public OrderVO UpdateStatus(String order_id,int order_status) {
		OrderVO OrderVO = new OrderVO();
		System.out.println("service update_status start");
		
		OrderVO.setOrder_id(order_id);
		OrderVO.setOrder_status(order_status);
		dao.updatestatus(OrderVO);
		System.out.println("service update_status off");
		return OrderVO;
	}
	public OrderVO ProUpdate(String order_id,String member_id, int order_pay, int delivery, String order_address, int order_fee) {
		System.out.println("Service update start");
		OrderVO OrderVO = new OrderVO();
		OrderVO.setOrder_id(order_id);
		OrderVO.setMember_id(member_id);
		OrderVO.setOrder_pay(order_pay);
		OrderVO.setDelivery(delivery);
		OrderVO.setOrder_address(order_address);
		OrderVO.setOrder_fee(order_fee);
//		OrderVO.setOrder_status(order_status);
		
		dao.update(OrderVO);
		System.out.println("Service update off ");
		return OrderVO;
	}
	public OrderVO insertWithOrderLists(OrderVO OrderVO,List<Cart> list) {
		dao.insertWithOrderLists(OrderVO,list);
		return OrderVO;
	}
}
