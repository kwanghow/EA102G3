package com.calendar.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jessica.utils.ConnectionUtils;

public class CalMemberViewDaoImpl implements CalMemberViewDaoI{

	@Override
	public List<CalMemberViewVo> selectAll() {
		List<CalMemberViewVo> list = new ArrayList<CalMemberViewVo>();
		CalMemberViewVo vo = null;
		String sql = "SELECT MEMBER_ID, VIEW_DATE, VIEW_TIME, VIEW_EVENT, VIEW_DESC, VIEW_STATE FROM CAL_MEMBER_VIEW";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CalMemberViewVo();
				vo.setMember_id(rs.getString(1));
				vo.setView_date(rs.getDate(2));
				vo.setView_time(rs.getString(3));
				vo.setView_event(rs.getString(4));
				vo.setView_desc(rs.getString(5));
				vo.setView_state(rs.getInt(6));
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
	public List<CalMemberViewVo> selectListByMember_id(String member_id) {
		List<CalMemberViewVo> list = new ArrayList<CalMemberViewVo>();
		CalMemberViewVo vo = null;
		String sql = "SELECT MEMBER_ID, VIEW_DATE, VIEW_TIME, VIEW_EVENT, VIEW_DESC, VIEW_STATE FROM CAL_MEMBER_VIEW WHERE MEMBER_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CalMemberViewVo();
				vo.setMember_id(rs.getString(1));
				vo.setView_date(rs.getDate(2));
				vo.setView_time(rs.getString(3));
				vo.setView_event(rs.getString(4));
				vo.setView_desc(rs.getString(5));
				vo.setView_state(rs.getInt(6));
				list.add(vo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

}
