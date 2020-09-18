package com.course_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.course_date.model.CourseDateVo;
import com.jessica.utils.ConnectionUtils;

public class CourseOrderDaoImpl implements CourseOrderDaoI{

	@Override
	public int insert(CourseOrderVo courseOrderVo) {
		int updateCount = 0;
		String sql = "INSERT INTO COURSE_ORDER (ORDER_ID, MEMBER_ID, SET_ID, LESSON, ORDER_PRICE, TOTAL_PRICE, BOOK_LESSON, STATE) "
					+ "VALUES ('CO20' || LPAD(COURSE_ORDER_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, 0, 0)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, courseOrderVo.getMember_id());
			pstmt.setString(2, courseOrderVo.getSet_id());
			pstmt.setInt(3, courseOrderVo.getLesson());
			pstmt.setInt(4, courseOrderVo.getOrder_price());
			pstmt.setInt(5, courseOrderVo.getTotal_price());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	public void updatePrice(String order_id, String coupon_id, Integer disc, Integer total_price) {
		String orderSql = "UPDATE COURSE_ORDER SET COUPON_ID = ?, DISC = ?, TOTAL_PRICE = ?, STATE = 3 WHERE ORDER_ID = ?";
		String couponSql = "UPDATE COUPON SET STATE = ? WHERE COUPON_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			con.setAutoCommit(false);
			
			// 先改折價券狀態
			pstmt = con.prepareStatement(couponSql);
			pstmt.setInt(1, 1);
			pstmt.setString(2, coupon_id);
			pstmt.executeUpdate();
			pstmt.clearParameters();
			
			// 再改訂單狀態
			pstmt = con.prepareStatement(orderSql);
			pstmt.setString(1, coupon_id);
			pstmt.setInt(2, disc);
			pstmt.setInt(3, total_price);
			pstmt.setString(4, order_id);
			pstmt.executeUpdate();
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("訂單" + order_id + "結帳與折價券" + coupon_id + "狀態更新成功");
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch(SQLException se) {
					throw new RuntimeException("Rollback失敗!!!" + se.getMessage());
				}
			}
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}
	}

	@Override
	public int updateComment(CourseOrderVo courseOrderVo) {
		int updateCount = 0;
		String sql = "UPDATE COURSE_ORDER SET COM_STAR = ?, COM_CONTENT = ?, COM_DATE = CURRENT_TIMESTAMP WHERE ORDER_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, courseOrderVo.getCom_star());
			pstmt.setString(2, courseOrderVo.getCom_content());
			pstmt.setString(3, courseOrderVo.getOrder_id());
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}
		
	@Override
	public int updateStateById(String order_id, int state) {
		int updateCount = 0;
		String sql = "UPDATE COURSE_ORDER SET STATE = ? WHERE ORDER_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, state);
			pstmt.setString(2, order_id);			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}

	@Override
	public int updateBookLessonById(String order_id, int book_lesson) {
		int updateCount = 0;
		String sql = "UPDATE COURSE_ORDER SET BOOK_LESSON = ? WHERE ORDER_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, book_lesson);
			pstmt.setString(2, order_id);			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return updateCount;
	}
	
	@Override
	public List<CourseOrderVo> selectAll() {
		List<CourseOrderVo> list = new ArrayList<CourseOrderVo>();
		CourseOrderVo orderVO = null;
		String sql = "SELECT * FROM COURSE_ORDER ORDER BY ORDER_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new CourseOrderVo();
				orderVO.setOrder_id(rs.getString(1));
				orderVO.setMember_id(rs.getString(2));
				orderVO.setSet_id(rs.getString(3));
				orderVO.setLesson(rs.getInt(4));
				orderVO.setOrder_price(rs.getInt(5));
				orderVO.setCoupon_id(rs.getString(6));
				orderVO.setDisc(rs.getInt(7));
				orderVO.setTotal_price(rs.getInt(8));
				orderVO.setBook_lesson(rs.getInt(9));
				orderVO.setInitstamp(rs.getTimestamp(10));
				orderVO.setCom_star(rs.getInt(11));
				orderVO.setCom_content(rs.getString(12));
				orderVO.setCom_date(rs.getTimestamp(13));
				orderVO.setState(rs.getInt(14));
				list.add(orderVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public List<CourseOrderVo> selectListByMember_id(String member_id) {
		List<CourseOrderVo> list = new ArrayList<CourseOrderVo>();
		CourseOrderVo orderVO = null;
		String sql = "SELECT * FROM COURSE_ORDER WHERE MEMBER_ID = ? ORDER BY ORDER_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new CourseOrderVo();
				orderVO.setOrder_id(rs.getString(1));
				orderVO.setMember_id(rs.getString(2));
				orderVO.setSet_id(rs.getString(3));
				orderVO.setLesson(rs.getInt(4));
				orderVO.setOrder_price(rs.getInt(5));
				orderVO.setCoupon_id(rs.getString(6));
				orderVO.setDisc(rs.getInt(7));
				orderVO.setTotal_price(rs.getInt(8));
				orderVO.setBook_lesson(rs.getInt(9));
				orderVO.setInitstamp(rs.getTimestamp(10));
				orderVO.setCom_star(rs.getInt(11));
				orderVO.setCom_content(rs.getString(12));
				orderVO.setCom_date(rs.getTimestamp(13));
				orderVO.setState(rs.getInt(14));
				list.add(orderVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}
	
	public List<CourseOrderVo> selectListBySet_id(String set_id) {
		List<CourseOrderVo> list = new ArrayList<CourseOrderVo>();
		CourseOrderVo orderVO = null;
		String sql = "SELECT * FROM COURSE_ORDER WHERE SET_ID = ? ORDER BY ORDER_ID";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, set_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVO = new CourseOrderVo();
				orderVO.setOrder_id(rs.getString(1));
				orderVO.setMember_id(rs.getString(2));
				orderVO.setSet_id(rs.getString(3));
				orderVO.setLesson(rs.getInt(4));
				orderVO.setOrder_price(rs.getInt(5));
				orderVO.setCoupon_id(rs.getString(6));
				orderVO.setDisc(rs.getInt(7));
				orderVO.setTotal_price(rs.getInt(8));
				orderVO.setBook_lesson(rs.getInt(9));
				orderVO.setInitstamp(rs.getTimestamp(10));
				orderVO.setCom_star(rs.getInt(11));
				orderVO.setCom_content(rs.getString(12));
				orderVO.setCom_date(rs.getTimestamp(13));
				orderVO.setState(rs.getInt(14));
				list.add(orderVO);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return list;
	}

	@Override
	public CourseOrderVo selectOneById(String order_id) {
		CourseOrderVo orderVo = null;
		String sql = "SELECT * FROM COURSE_ORDER WHERE ORDER_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVo = new CourseOrderVo();
				orderVo.setOrder_id(rs.getString(1));
				orderVo.setMember_id(rs.getString(2));
				orderVo.setSet_id(rs.getString(3));
				orderVo.setLesson(rs.getInt(4));
				orderVo.setOrder_price(rs.getInt(5));
				orderVo.setCoupon_id(rs.getString(6));
				orderVo.setDisc(rs.getInt(7));
				orderVo.setTotal_price(rs.getInt(8));
				orderVo.setBook_lesson(rs.getInt(9));
				orderVo.setInitstamp(rs.getTimestamp(10));
				orderVo.setCom_star(rs.getInt(11));
				orderVo.setCom_content(rs.getString(12));
				orderVo.setCom_date(rs.getTimestamp(13));
				orderVo.setState(rs.getInt(14));
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return orderVo;
	}

	@Override
	public Set<CourseDateVo> findCourseDatesById(String order_id) {
		Set<CourseDateVo> set = new LinkedHashSet<CourseDateVo>();
		CourseDateVo dateVo = null;
		String sql = "SELECT * FROM COURSE_DATE WHERE ORDER_ID = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionUtils.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, order_id);
			rs = pstmt.executeQuery();
					
			while(rs.next()) {
				dateVo = new CourseDateVo();
				dateVo.setCdate_id(rs.getString(1));
				dateVo.setOrder_id(rs.getString(2));
				dateVo.setCdate(rs.getDate(3));
				dateVo.setCtime(rs.getString(4));
				dateVo.setInitstamp(rs.getTimestamp(5));
				dateVo.setState(rs.getInt(6));				
				set.add(dateVo);
			}						
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} finally {
			ConnectionUtils.close(con);
		}		
		return set;
	}

}
