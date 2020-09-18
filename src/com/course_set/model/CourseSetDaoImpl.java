package com.course_set.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jessica.utils.ConnectionUtils;

public class CourseSetDaoImpl implements CourseSetDaoI{

	@Override
	public int insert(CourseSetVo courseSetVo) {
		int updateCount = 0;
		String sql = "INSERT INTO COURSE_SET (SET_ID, COURSE_ID, LESSON, PRICE) "
					+ "VALUES ('CS20' || LPAD(COURSE_SET_SEQ.NEXTVAL, 3, '0'), ?, ?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseSetVo.getCourse_id());
			pstmt.setInt(2, courseSetVo.getLesson());
			pstmt.setInt(3, courseSetVo.getPrice());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int deleteById(String set_id) {
		int updateCount = 0;
		String sql = "DELETE FROM COURSE_SET where SET_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set_id);
			updateCount = pstmt.executeUpdate();			
		} catch (Exception e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int update(CourseSetVo courseSetVo) {
		int updateCount = 0;
		String sql = "UPDATE COURSE_SET SET COURSE_ID = ?, LESSON = ?, PRICE = ? WHERE SET_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, courseSetVo.getCourse_id());
			pstmt.setInt(2, courseSetVo.getLesson());
			pstmt.setInt(3, courseSetVo.getPrice());
			pstmt.setString(4, courseSetVo.getSet_id());
			updateCount = pstmt.executeUpdate();		
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public List<CourseSetVo> selectAll() {
		List<CourseSetVo> list = new ArrayList<CourseSetVo>();
		CourseSetVo courseSetVo = null;
		String sql = "SELECT * FROM COURSE_SET ORDER BY SET_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseSetVo = new CourseSetVo();
				courseSetVo.setSet_id(rs.getString(1));
				courseSetVo.setCourse_id(rs.getString(2));
				courseSetVo.setLesson(rs.getInt(3));
				courseSetVo.setPrice(rs.getInt(4));
				list.add(courseSetVo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public List<CourseSetVo> selectListByCourse_id(String course_id) {
		List<CourseSetVo> list = new ArrayList<CourseSetVo>();
		CourseSetVo courseSetVo = null;
		String sql = "SELECT * FROM COURSE_SET WHERE COURSE_ID = ? ORDER BY LESSON";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseSetVo = new CourseSetVo();
				courseSetVo.setSet_id(rs.getString(1));
				courseSetVo.setCourse_id(rs.getString(2));
				courseSetVo.setLesson(rs.getInt(3));
				courseSetVo.setPrice(rs.getInt(4));
				list.add(courseSetVo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public CourseSetVo selectOneById(String set_id) {
		CourseSetVo courseSetVo = null;
		String sql = "SELECT * FROM COURSE_SET WHERE SET_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseSetVo = new CourseSetVo();
				courseSetVo.setSet_id(rs.getString(1));
				courseSetVo.setCourse_id(rs.getString(2));
				courseSetVo.setLesson(rs.getInt(3));
				courseSetVo.setPrice(rs.getInt(4));
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return courseSetVo;
	}

}
