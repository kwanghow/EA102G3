package com.coupon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jessica.utils.ConnectionUtils;

public class CouponDaoImpl implements CouponDaoI{

	@Override
	public int insert(int coupon_disc) {
		int updateCount = 0;
		String sql = "INSERT INTO COUPON (COUPON_ID, COUPON_DISC, STATE) "
				   + "VALUES ((select dbms_random.string('U', 8) str from dual), ?, 0)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon_disc);
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int deleteById(String coupon_id) {
		int updateCount = 0;
		String sql = "DELETE FROM COUPON where CDATE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon_id);
			updateCount = pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int updateStateById(String coupon_id, int state) {
		int updateCount = 0;
		String sql = "UPDATE COUPON SET STATE = ? WHERE COUPON_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setString(2, coupon_id);			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public CouponVo selectOneById(String coupon_id) {
		CouponVo couponVO = null;
		String sql = "SELECT * FROM COUPON WHERE COUPON_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coupon_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				couponVO = new CouponVo();
				couponVO.setCoupon_id(rs.getString(1));
				couponVO.setCoupon_disc(rs.getInt(2));
				couponVO.setState(rs.getInt(3));
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return couponVO;
	}

	@Override
	public CouponVo selectOneByPrice(Integer coupon_disc) {
		CouponVo couponVO = null;
		String sql = "SELECT * FROM COUPON WHERE COUPON_DISC = ? AND STATE = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, coupon_disc);
			pstmt.setInt(2, 0);
			rs = pstmt.executeQuery();
			
			rs.next();
			couponVO = new CouponVo();
			couponVO.setCoupon_id(rs.getString(1));
			couponVO.setCoupon_disc(rs.getInt(2));
			couponVO.setState(rs.getInt(3));
									
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return couponVO;
	}

}