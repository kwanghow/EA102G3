package com.course_date.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jessica.utils.ConnectionUtils;

public class CourseDateDaoImpl implements CourseDateDaoI{

	@Override
	public int insert(CourseDateVo courseDateVo) {
		int updateCount = 0;
		String sql = "INSERT INTO COURSE_DATE (CDATE_ID, ORDER_ID, CDATE, CTIME, STATE) " 
				   + "VALUES ('CD20' || LPAD(COURSE_SET_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, 0)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseDateVo.getOrder_id());
			pstmt.setDate(2, courseDateVo.getCdate());
			pstmt.setString(3, courseDateVo.getCtime());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int deleteById(String cdate_id) {
		int updateCount = 0;
		String sql = "DELETE FROM COURSE_DATE where CDATE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cdate_id);
			updateCount = pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int updateStateById(String cdate_id, int state) {
		int updateCount = 0;
		String sql = "UPDATE COURSE_DATE SET STATE = ? WHERE CDATE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setString(2, cdate_id);			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public List<CourseDateVo> selectAll() {
		List<CourseDateVo> list = new ArrayList<CourseDateVo>();
		CourseDateVo dateVO = null;
		String sql = "SELECT * FROM COURSE_DATE ORDER BY CDATE_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dateVO = new CourseDateVo();
				dateVO.setCdate_id(rs.getString(1));
				dateVO.setOrder_id(rs.getString(2));
				dateVO.setCdate(rs.getDate(3));
				dateVO.setCtime(rs.getString(4));
				dateVO.setInitstamp(rs.getTimestamp(5));
				dateVO.setState(rs.getInt(6));
				list.add(dateVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public List<CourseDateVo> selectListByOrder_id(String order_id) {
		List<CourseDateVo> list = new ArrayList<CourseDateVo>();
		CourseDateVo dateVO = null;
		String sql = "SELECT * FROM COURSE_DATE WHERE ORDER_ID = ? ORDER BY CDATE_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dateVO = new CourseDateVo();
				dateVO.setCdate_id(rs.getString(1));
				dateVO.setOrder_id(rs.getString(2));
				dateVO.setCdate(rs.getDate(3));
				dateVO.setCtime(rs.getString(4));
				dateVO.setInitstamp(rs.getTimestamp(5));
				dateVO.setState(rs.getInt(6));
				list.add(dateVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public CourseDateVo selectOneById(String cdate_id) {
		CourseDateVo dateVO = null;
		String sql = "SELECT * FROM COURSE_DATE WHERE CDATE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cdate_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dateVO = new CourseDateVo();
				dateVO.setCdate_id(rs.getString(1));
				dateVO.setOrder_id(rs.getString(2));
				dateVO.setCdate(rs.getDate(3));
				dateVO.setCtime(rs.getString(4));
				dateVO.setInitstamp(rs.getTimestamp(5));
				dateVO.setState(rs.getInt(6));
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return dateVO;
	}

}
