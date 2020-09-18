package com.coupon.model;

public interface CouponDaoI {
	int insert(int coupon_disc);	
	int deleteById(String coupon_id);	
	int updateStateById(String coupon_id, int state);	
	CouponVo selectOneById(String coupon_id);
	CouponVo selectOneByPrice(Integer coupon_disc);
}
