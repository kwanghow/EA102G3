package com.coupon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coupon.model.CouponService;
import com.coupon.model.CouponVo;
import com.google.gson.Gson;

public class CouponServlet extends HttpServlet { //coupon.do

	CouponService couponSvc = new CouponService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("竒筁" + req.getRequestURI());
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);			
			method.invoke(this, req, res);
		} catch(Exception e) {
			throw new RuntimeException("action把计Τ粇"+e.getMessage());
		}
	}
	
	protected void ajaxVerifyCoupon(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			String coupon_id = req.getParameter("coupon_id");
			Integer order_price = Integer.valueOf(req.getParameter("order_price"));

			CouponVo vo = couponSvc.getOneCouponById(coupon_id);
			if(vo == null || vo.getState() == 1) {
				out.write("cantUse");
				return;
			}
			
			Map<String, String> map = new HashMap<String, String>();
			Integer total_price = order_price - vo.getCoupon_disc();
			if(total_price < 0) {
				map.put("coupon_disc", vo.getCoupon_disc().toString());
				map.put("total_price", "0");
				map.put("msg", "ч基ㄩ肂羆基");
				out.write(new Gson().toJson(map));
				return;
			}else {
				map.put("coupon_disc", vo.getCoupon_disc().toString());
				map.put("total_price", total_price.toString());
				map.put("msg", "ч基ㄩノ");
				out.write(new Gson().toJson(map));
				return;
			}			

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
