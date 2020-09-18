package com.course_favor.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jessica.utils.ConnectionUtils;

public class CourseFavorDaoImpl implements CourseFavorDaoI{

	@Override
	public int insert(CourseFavorVo courseFavorVo) {
		int updateCount = 0;
		String sql = "INSERT INTO COURSE_FAVOR (MEMBER_ID, COURSE_ID) VALUES (?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseFavorVo.getMember_id());
			pstmt.setString(2, courseFavorVo.getCourse_id());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int delete(CourseFavorVo courseFavorVo) {
		int updateCount = 0;
		String sql = "DELETE FROM COURSE_FAVOR WHERE MEMBER_ID = ? AND COURSE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseFavorVo.getMember_id());
			pstmt.setString(2, courseFavorVo.getCourse_id());
			updateCount = pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public List<CourseFavorVo> selectListByMember_id(String member_id) {
		List<CourseFavorVo> list = new ArrayList<CourseFavorVo>();
		CourseFavorVo favorVo = null;
		String sql = "SELECT * FROM COURSE_FAVOR where MEMBER_ID = ?";		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				favorVo = new CourseFavorVo();
				favorVo.setMember_id(rs.getString(1));
				favorVo.setCourse_id(rs.getString(2));
				list.add(favorVo);
			}			
		}catch(SQLException e) {
			throw new RuntimeException("SQLExeption!!!" + e.getMessage());
		}finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	public CourseFavorVo selectOne(CourseFavorVo courseFavorVo) {
		CourseFavorVo favorVo = null;
		String sql = "SELECT * FROM COURSE_FAVOR where MEMBER_ID = ? AND COURSE_ID = ?";		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseFavorVo.getMember_id());
			pstmt.setString(2, courseFavorVo.getCourse_id());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				favorVo = new CourseFavorVo();
				favorVo.setMember_id(rs.getString(1));
				favorVo.setCourse_id(rs.getString(2));
			}			
		}catch(SQLException e) {
			throw new RuntimeException("SQLExeption!!!" + e.getMessage());
		}finally {
			ConnectionUtils.close(con);
		}		
		return favorVo;
	}

}
