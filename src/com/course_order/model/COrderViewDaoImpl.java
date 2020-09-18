package com.course_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jessica.utils.ConnectionUtils;

public class COrderViewDaoImpl implements COrderViewDaoI{

	@Override
	public List<COrderViewVo> selectAll() {
		List<COrderViewVo> list = new ArrayList<COrderViewVo>();
		COrderViewVo vo = null;
		String sql = "SELECT * FROM COURSE_ORDER_VIEW ORDER BY ORDER_ID DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new COrderViewVo();
				vo.setOrder_id(rs.getString(1));
				vo.setMember_id(rs.getString(2));
				vo.setSet_id(rs.getString(3));
				vo.setLesson(rs.getInt(4));
				vo.setOrder_price(rs.getInt(5));
				vo.setCoupon_id(rs.getString(6));
				vo.setDisc(rs.getInt(7));
				vo.setTotal_price(rs.getInt(8));
				vo.setBook_lesson(rs.getInt(9));
				vo.setInitstamp(rs.getTimestamp(10));
				vo.setCom_star(rs.getInt(11));
				vo.setCom_content(rs.getString(12));
				vo.setCom_date(rs.getTimestamp(13));
				vo.setState(rs.getInt(14));
				vo.setCourse_id(rs.getString(15));
				vo.setCoach_id(rs.getString(16));
				vo.setCname(rs.getString(17));
				vo.setExp_name(rs.getString(18));
				vo.setLoc(rs.getString(19));
				list.add(vo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public List<COrderViewVo> selectListByMember_id(String member_id) {
		List<COrderViewVo> list = new ArrayList<COrderViewVo>();
		COrderViewVo vo = null;
		String sql = "SELECT * FROM COURSE_ORDER_VIEW WHERE MEMBER_ID = ? ORDER BY ORDER_ID DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new COrderViewVo();
				vo.setOrder_id(rs.getString(1));
				vo.setMember_id(rs.getString(2));
				vo.setSet_id(rs.getString(3));
				vo.setLesson(rs.getInt(4));
				vo.setOrder_price(rs.getInt(5));
				vo.setCoupon_id(rs.getString(6));
				vo.setDisc(rs.getInt(7));
				vo.setTotal_price(rs.getInt(8));
				vo.setBook_lesson(rs.getInt(9));
				vo.setInitstamp(rs.getTimestamp(10));
				vo.setCom_star(rs.getInt(11));
				vo.setCom_content(rs.getString(12));
				vo.setCom_date(rs.getTimestamp(13));
				vo.setState(rs.getInt(14));
				vo.setCourse_id(rs.getString(15));
				vo.setCoach_id(rs.getString(16));
				vo.setCname(rs.getString(17));
				vo.setExp_name(rs.getString(18));
				vo.setLoc(rs.getString(19));
				list.add(vo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public List<COrderViewVo> selectListByCoach_id(String coach_id) {
		List<COrderViewVo> list = new ArrayList<COrderViewVo>();
		COrderViewVo vo = null;
		String sql = "SELECT * FROM COURSE_ORDER_VIEW WHERE COACH_ID = ? ORDER BY ORDER_ID DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coach_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new COrderViewVo();
				vo.setOrder_id(rs.getString(1));
				vo.setMember_id(rs.getString(2));
				vo.setSet_id(rs.getString(3));
				vo.setLesson(rs.getInt(4));
				vo.setOrder_price(rs.getInt(5));
				vo.setCoupon_id(rs.getString(6));
				vo.setDisc(rs.getInt(7));
				vo.setTotal_price(rs.getInt(8));
				vo.setBook_lesson(rs.getInt(9));
				vo.setInitstamp(rs.getTimestamp(10));
				vo.setCom_star(rs.getInt(11));
				vo.setCom_content(rs.getString(12));
				vo.setCom_date(rs.getTimestamp(13));
				vo.setState(rs.getInt(14));
				vo.setCourse_id(rs.getString(15));
				vo.setCoach_id(rs.getString(16));
				vo.setCname(rs.getString(17));
				vo.setExp_name(rs.getString(18));
				vo.setLoc(rs.getString(19));
				list.add(vo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	@Override
	public COrderViewVo selectOneById(String order_id) {
		COrderViewVo vo = null;
		String sql = "SELECT * FROM COURSE_ORDER_VIEW WHERE ORDER_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new COrderViewVo();
				vo.setOrder_id(rs.getString(1));
				vo.setMember_id(rs.getString(2));
				vo.setSet_id(rs.getString(3));
				vo.setLesson(rs.getInt(4));
				vo.setOrder_price(rs.getInt(5));
				vo.setCoupon_id(rs.getString(6));
				vo.setDisc(rs.getInt(7));
				vo.setTotal_price(rs.getInt(8));
				vo.setBook_lesson(rs.getInt(9));
				vo.setInitstamp(rs.getTimestamp(10));
				vo.setCom_star(rs.getInt(11));
				vo.setCom_content(rs.getString(12));
				vo.setCom_date(rs.getTimestamp(13));
				vo.setState(rs.getInt(14));
				vo.setCourse_id(rs.getString(15));
				vo.setCoach_id(rs.getString(16));
				vo.setCname(rs.getString(17));
				vo.setExp_name(rs.getString(18));
				vo.setLoc(rs.getString(19));
			}				
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return vo;
	}
	
}
