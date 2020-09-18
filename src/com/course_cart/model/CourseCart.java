package com.course_cart.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CourseCart implements java.io.Serializable{
	private Map<String, CourseCartItem> items = new LinkedHashMap<String, CourseCartItem>();
	
	public Map<String, CourseCartItem> getItems() {
		return items;
	}

	public void setItems(Map<String, CourseCartItem> items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("CourseCart [ Totalprice=" + getTotalprice()+", items= [ ");
		for(String key: items.keySet()) {
			str.append(items.get(key).getCourse_id() + ", ");
		}
		str.append("]");
		return str.toString();
	}
	
	public boolean isCartItem(String course_id) {
		CourseCartItem item = items.get(course_id);
		if(item == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 新增或更改購物車項目
	 * @param cartItem
	 * @return 回傳1代表新增，-1代表更新方案
	 */
	public int addOrUpdateItem(CourseCartItem cartItem) {
		CourseCartItem item = items.get(cartItem.getCourse_id());
		if(item == null) {
			items.put(cartItem.getCourse_id(), cartItem);
			return 1;
		} else {
			item.setSet_id(cartItem.getSet_id());
			item.setLesson(cartItem.getLesson());
			item.setOrder_price(cartItem.getOrder_price());
			return -1;
		}		
	}
	
	public void deleteItem(String course_id) {
		items.remove(course_id);
	}
	
	public void clearCart() {
		items.clear();
	}
	
	public Integer getTotalprice() {
		Integer totalPrice = 0;
		for(String key: items.keySet()) {
			totalPrice += items.get(key).getOrder_price();
		}
		return totalPrice;
	}	

}
