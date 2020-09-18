package com.pro.order.model;
import java.util.List;

import com.pro.order.model.OrderVO;
import com.pro.cart.model.Cart;
import com.pro.detail.model.DetailVO;

public interface OrderDAO_interface {
		public void insert(OrderVO OrderVO);
        public void update(OrderVO OrderVO);
        
        public void deleteFromFront(OrderVO OrderVO);
        public List<OrderVO> getAll();
        
      //�P�ɷs�W�q��+�q�����
    	public OrderVO insertWithOrderLists(OrderVO OrderVO, List<Cart> list);
    	//�Y�|�����Ҧ��q��
    	public List<OrderVO> getAllOrderByMemNo_All(String member_id);    	
    	public List<OrderVO> getAllOrderByMemNo_Pay(String member_id);    	
    	public List<OrderVO> getAllOrderByMemNo_Com(String member_id);    	
    	public List<OrderVO> getAllOrderByMemNo_Can(String member_id);    	
    	//���ܭq�檬�A
    	public void updatestatus(OrderVO OrderVO);
    	
		public OrderVO findByPrimaryKey(String order_id);
		
}
