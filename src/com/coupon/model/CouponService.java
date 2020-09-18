package com.coupon.model;

public class CouponService {
	private CouponDaoI dao;
	
	public CouponService() {
		dao = new CouponDaoImpl();
	}
	
	public int addCoupon(int coupon_disc) {
		return dao.insert(coupon_disc);
	}
	
	public int deleteCoupon(String coupon_id) {
		return dao.deleteById(coupon_id);
	}
	
	public int updateCouponState(String coupon_id, int state) {
		return dao.updateStateById(coupon_id, state);
	}
	
	public CouponVo getOneCouponById(String coupon_id) {
		return dao.selectOneById(coupon_id);
	}
	
}
