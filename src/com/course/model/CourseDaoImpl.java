package com.course.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.course_set.model.CourseSetVo;
import com.jessica.utils.ConnectionUtils;
import com.jessica.utils.JdbcUtils;

public class CourseDaoImpl implements CourseDaoI{

	@Override
	public int insert(CourseVo courseVo) {
		int updateCount = 0;
		String sql = "INSERT INTO COURSE (COURSE_ID, COACH_ID, CNAME, INTRO, EXP_ID, LOC, PIC1, PIC2, PIC3, STATE)"
				+ "VALUES (('C20' || LPAD(COURSE_SEQ.NEXTVAL, 3, '0')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseVo.getCoach_id());
			pstmt.setString(2, courseVo.getCname());
			pstmt.setString(3, courseVo.getIntro());
			pstmt.setString(4, courseVo.getExp_id());
			pstmt.setString(5, courseVo.getLoc());
			pstmt.setBytes(6, courseVo.getPic1());
			pstmt.setBytes(7, courseVo.getPic2());
			pstmt.setBytes(8, courseVo.getPic3());
			pstmt.setInt(9, courseVo.getState());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}
	
	@Override
	public int deleteById(String course_id) {
		int updateCount = 0;
		String sql = "DELETE FROM COURSE where COURSE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_id);
			updateCount = pstmt.executeUpdate();			
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int update(CourseVo courseVo) {
		int updateCount = 0;
		String sql = "UPDATE COURSE SET COACH_ID = ?, CNAME = ?, INTRO = ?, EXP_ID = ?, LOC = ?, PIC1 = ?, PIC2 = ?, PIC3 = ?, STATE = ?  WHERE COURSE_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseVo.getCoach_id());
			pstmt.setString(2, courseVo.getCname());
			pstmt.setString(3, courseVo.getIntro());
			pstmt.setString(4, courseVo.getExp_id());
			pstmt.setString(5, courseVo.getLoc());
			pstmt.setBytes(6, courseVo.getPic1());
			pstmt.setBytes(7, courseVo.getPic2());
			pstmt.setBytes(8, courseVo.getPic3());
			pstmt.setInt(9, courseVo.getState());
			pstmt.setString(10, courseVo.getCourse_id());
			updateCount = pstmt.executeUpdate();		
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public List<CourseVo> selectAll() {
		List<CourseVo> list = new ArrayList<CourseVo>();
		CourseVo courseVO = null;
		String sql = "SELECT * FROM COURSE ORDER BY COURSE_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseVO = new CourseVo();
				courseVO.setCourse_id(rs.getString(1));
				courseVO.setCoach_id(rs.getString(2));
				courseVO.setCname(rs.getString(3));
				courseVO.setIntro(rs.getString(4));
				courseVO.setExp_id(rs.getString(5));
				courseVO.setLoc(rs.getString(6));
				courseVO.setPic1(rs.getBytes(7));
				courseVO.setPic2(rs.getBytes(8));
				courseVO.setPic3(rs.getBytes(9));
				courseVO.setInitstamp(rs.getTimestamp(10));
				courseVO.setState(rs.getInt(11));
				list.add(courseVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	@Override
	public List<CourseVo> selectAllOnShelf() {
		List<CourseVo> list = new ArrayList<CourseVo>();
		CourseVo courseVO = null;
		String sql = "SELECT * FROM COURSE WHERE STATE = 1 ORDER BY COURSE_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseVO = new CourseVo();
				courseVO.setCourse_id(rs.getString(1));
				courseVO.setCoach_id(rs.getString(2));
				courseVO.setCname(rs.getString(3));
				courseVO.setIntro(rs.getString(4));
				courseVO.setExp_id(rs.getString(5));
				courseVO.setLoc(rs.getString(6));
				courseVO.setPic1(rs.getBytes(7));
				courseVO.setPic2(rs.getBytes(8));
				courseVO.setPic3(rs.getBytes(9));
				courseVO.setInitstamp(rs.getTimestamp(10));
				courseVO.setState(rs.getInt(11));
				list.add(courseVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	@Override
	public List<CourseVo> selectAll(Map<String, String[]> conditionMap) {
		List<CourseVo> list = new ArrayList<CourseVo>();
		CourseVo courseVO = null;
		String sql = "SELECT * FROM COURSE" + JdbcUtils.condMapToSqlForOracle(conditionMap) + " ORDER BY COURSE_ID";
		System.out.println("SQL指令(by CourseDaoImpl): " + sql);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseVO = new CourseVo();
				courseVO.setCourse_id(rs.getString(1));
				courseVO.setCoach_id(rs.getString(2));
				courseVO.setCname(rs.getString(3));
				courseVO.setIntro(rs.getString(4));
				courseVO.setExp_id(rs.getString(5));
				courseVO.setLoc(rs.getString(6));
				courseVO.setPic1(rs.getBytes(7));
				courseVO.setPic2(rs.getBytes(8));
				courseVO.setPic3(rs.getBytes(9));
				courseVO.setInitstamp(rs.getTimestamp(10));
				courseVO.setState(rs.getInt(11));
				list.add(courseVO);
			}						
		} catch (Exception e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	@Override
	public List<CourseVo> selectListByCoachId(String coach_id) {
		List<CourseVo> list = new ArrayList<CourseVo>();
		CourseVo courseVO = null;
		String sql = "SELECT * FROM COURSE WHERE COACH_ID = ? ORDER BY COURSE_ID DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coach_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseVO = new CourseVo();
				courseVO.setCourse_id(rs.getString(1));
				courseVO.setCoach_id(rs.getString(2));
				courseVO.setCname(rs.getString(3));
				courseVO.setIntro(rs.getString(4));
				courseVO.setExp_id(rs.getString(5));
				courseVO.setLoc(rs.getString(6));
				courseVO.setPic1(rs.getBytes(7));
				courseVO.setPic2(rs.getBytes(8));
				courseVO.setPic3(rs.getBytes(9));
				courseVO.setInitstamp(rs.getTimestamp(10));
				courseVO.setState(rs.getInt(11));
				list.add(courseVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	@Override
	public CourseVo selectOneById(String course_id) {
		CourseVo courseVO = null;
		String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, course_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				courseVO = new CourseVo();
				courseVO.setCourse_id(rs.getString(1));
				courseVO.setCoach_id(rs.getString(2));
				courseVO.setCname(rs.getString(3));
				courseVO.setIntro(rs.getString(4));
				courseVO.setExp_id(rs.getString(5));
				courseVO.setLoc(rs.getString(6));
				courseVO.setPic1(rs.getBytes(7));
				courseVO.setPic2(rs.getBytes(8));
				courseVO.setPic3(rs.getBytes(9));
				courseVO.setInitstamp(rs.getTimestamp(10));
				courseVO.setState(rs.getInt(11));
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return courseVO;
	}
	
	public String selectOneByCoachIdAndCname(String coach_id, String cname) {
		String course_id = null;
		String sql = "SELECT COURSE_ID FROM COURSE WHERE COACH_ID = ? AND CNAME = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, coach_id);
			pstmt.setString(2, cname);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				course_id = rs.getString(1);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return course_id;
	}
	
	@Override
	public Set<CourseSetVo> findCourseSetsById(String course_id) {
		Set<CourseSetVo> set = new LinkedHashSet<CourseSetVo>();
		CourseSetVo courseSetVo = null;
		String sql = "SELECT * FROM COURSE_SET WHERE COURSE_ID = ?";
		
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
				set.add(courseSetVo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return set;
	}

	public static void main(String[] args) {
		CourseDaoImpl dao = new CourseDaoImpl();
		System.out.println(dao.selectOneByCoachIdAndCname("C01", "10000000000"));
		
//		CourseVo courseVo = dao.selectOneById("C20005");
//		
//		Set<CourseSetVo> sets = new LinkedHashSet<CourseSetVo>();
//		sets.add(new CourseSetVo("", "", 80, 200));
//		sets.add(new CourseSetVo("", "", 90, 300));
//		
//		dao.insert(courseVo, sets);
	}

	
//	public void insert(CourseVo courseVo, Set<CourseSetVo> set) {
//		String cSql = "INSERT INTO COURSE (COURSE_ID, COACH_ID, CNAME, INTRO, EXP_ID, LOC, PIC1, PIC2, PIC3, STATE) "
//					+ "VALUES (('C20' || LPAD(COURSE_SEQ.NEXTVAL, 3, '0')), ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//		String setSql = "INSERT INTO COURSE_SET (SET_ID, COURSE_ID, LESSON, PRICE) "
//					  + "VALUES ('CS20' || LPAD(COURSE_SET_SEQ.NEXTVAL, 3, '0'), ?, ?, ?)";		
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ConnectionUtils.getConnection();
//			con.setAutoCommit(false);
//			
//			// 先新增課程
//			pstmt = con.prepareStatement(cSql);
//			pstmt.setString(1, courseVo.getCoach_id());
//			pstmt.setString(2, courseVo.getCname());
//			pstmt.setString(3, courseVo.getIntro());
//			pstmt.setString(4, courseVo.getExp_id());
//			pstmt.setString(5, courseVo.getLoc());
//			pstmt.setBytes(6, courseVo.getPic1());
//			pstmt.setBytes(7, courseVo.getPic2());
//			pstmt.setBytes(8, courseVo.getPic3());
//			pstmt.setInt(9, courseVo.getState());
//			pstmt.executeUpdate();
//			pstmt.clearParameters();
//			
//			// 取得課程編號
//			String course_id = new CourseDaoImpl().selectOneByCoachIdAndCname(courseVo.getCoach_id(), courseVo.getCname());
//			System.out.println("***course_id="+course_id);
//			
//			int count = 0;
//			// 後新增方案
//			for(CourseSetVo courseSetVo : set) {
//				pstmt = con.prepareStatement(setSql);
//				pstmt.setString(1, course_id);
//				pstmt.setInt(2, courseSetVo.getLesson());
//				pstmt.setInt(3, courseSetVo.getPrice());
//				count += pstmt.executeUpdate();
//				pstmt.clearParameters();
//			}
//
//			con.commit();
//			con.setAutoCommit(true);
//			System.out.println("課程" + course_id + "及其" + count + "個set更新成功");
//		} catch (SQLException e) {
//			if(con != null) {
//				try {
//					con.rollback();
//				} catch(SQLException se) {
//					throw new RuntimeException("Rollback失敗!!!" + se.getMessage());
//				}
//			}
//			throw new RuntimeException("SQLException!!!" + e.getMessage());
//		} finally {
//			ConnectionUtils.close(con);
//		}
//	}
	
}
