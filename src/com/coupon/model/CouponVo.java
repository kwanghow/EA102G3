package com.coupon.model;

public class CouponVo implements java.io.Serializable{
	private String coupon_id;
	private Integer coupon_disc;
	private Integer state;

	public CouponVo() {}
	
	public CouponVo(String coupon_id, Integer coupon_disc, Integer state) {
		super();
		this.coupon_id = coupon_id;
		this.coupon_disc = coupon_disc;
		this.state = state;
	}

	public String getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}

	public Integer getCoupon_disc() {
		return coupon_disc;
	}

	public void setCoupon_disc(Integer coupon_disc) {
		this.coupon_disc = coupon_disc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "CouponVo [coupon_id=" + coupon_id + ", coupon_disc=" + coupon_disc + ", state=" + state + "]";
	}
}
