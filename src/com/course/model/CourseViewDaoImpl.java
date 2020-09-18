package com.course.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jessica.utils.ConnectionUtils;
import com.jessica.utils.JdbcUtils;

public class CourseViewDaoImpl implements CourseViewDaoI{

	@Override
	public List<CourseViewVo> selectAll() {
		List<CourseViewVo> list = new ArrayList<CourseViewVo>();
		CourseViewVo vo = null;
		String sql = "SELECT * FROM COURSE_VIEW ORDER BY COURSE_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CourseViewVo();
				vo.setCourse_id(rs.getString(1));
				vo.setCoach_id(rs.getString(2));
				vo.setCoach_name(rs.getString(3));
				vo.setCoach_sex(rs.getString(4));
				vo.setCname(rs.getString(5));
				vo.setExp_id(rs.getString(6));
				vo.setExp_name(rs.getString(7));
				vo.setLoc(rs.getString(8));
				vo.setInitstamp(rs.getTimestamp(9));
				vo.setState(rs.getInt(10));
				vo.setPrice_max(rs.getInt(11));
				vo.setPrice_min(rs.getInt(12));
				vo.setPrice_avg(rs.getInt(13));
				vo.setIntro(rs.getString(14));;
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
	public List<CourseViewVo> selectAllOnShelf() {
		List<CourseViewVo> list = new ArrayList<CourseViewVo>();
		CourseViewVo vo = null;
		String sql = "SELECT * FROM COURSE_VIEW WHERE STATE = 1 ORDER BY COURSE_ID DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CourseViewVo();
				vo.setCourse_id(rs.getString(1));
				vo.setCoach_id(rs.getString(2));
				vo.setCoach_name(rs.getString(3));
				vo.setCoach_sex(rs.getString(4));
				vo.setCname(rs.getString(5));
				vo.setExp_id(rs.getString(6));
				vo.setExp_name(rs.getString(7));
				vo.setLoc(rs.getString(8));
				vo.setInitstamp(rs.getTimestamp(9));
				vo.setState(rs.getInt(10));
				vo.setPrice_max(rs.getInt(11));
				vo.setPrice_min(rs.getInt(12));
				vo.setPrice_avg(rs.getInt(13));
				vo.setIntro(rs.getString(14));;
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
	public List<CourseViewVo> selectAll(Map<String, String[]> conditionMap) {
		List<CourseViewVo> list = new ArrayList<CourseViewVo>();
		CourseViewVo vo = null;
		String sql = "SELECT * FROM COURSE" + JdbcUtils.condMapToSqlForOracle(conditionMap) + " ORDER BY COURSE_ID DESC";
		System.out.println("SQL«ü¥O(by CourseDaoImpl): " + sql);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CourseViewVo();
				vo.setCourse_id(rs.getString(1));
				vo.setCoach_id(rs.getString(2));
				vo.setCoach_name(rs.getString(3));
				vo.setCoach_sex(rs.getString(4));
				vo.setCname(rs.getString(5));
				vo.setExp_id(rs.getString(6));
				vo.setExp_name(rs.getString(7));
				vo.setLoc(rs.getString(8));
				vo.setInitstamp(rs.getTimestamp(9));
				vo.setState(rs.getInt(10));
				vo.setPrice_max(rs.getInt(11));
				vo.setPrice_min(rs.getInt(12));
				vo.setPrice_avg(rs.getInt(13));
				vo.setIntro(rs.getString(14));;
				list.add(vo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	public CourseViewVo selectOneById(String course_id) {
		CourseViewVo vo = null;
		String sql = "SELECT * FROM COURSE_VIEW WHERE COURSE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vo = new CourseViewVo();
				vo.setCourse_id(rs.getString(1));
				vo.setCoach_id(rs.getString(2));
				vo.setCoach_name(rs.getString(3));
				vo.setCoach_sex(rs.getString(4));
				vo.setCname(rs.getString(5));
				vo.setExp_id(rs.getString(6));
				vo.setExp_name(rs.getString(7));
				vo.setLoc(rs.getString(8));
				vo.setInitstamp(rs.getTimestamp(9));
				vo.setState(rs.getInt(10));
				vo.setPrice_max(rs.getInt(11));
				vo.setPrice_min(rs.getInt(12));
				vo.setPrice_avg(rs.getInt(13));
				vo.setIntro(rs.getString(14));;
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return vo;
	}
}
