package com.dayoff.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DayoffDAO implements DayoffDAO_interface{
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO DAYOFF (dayoff_Id, coach_Id, dayoff_Date, dayoff_Time) VALUES ('CDT' || LPAD (dayoff_seq.NEXTVAL, 6, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT dayoff_Id, coach_Id, to_char(dayoff_Date, 'yyyy-mm-dd') dayoff_Date, dayoff_Time from DAYOFF order by dayoff_Id";
	private static final String ONECOACH_ALLTIME = 
			"SELECT * from DAYOFF where coach_id=?";
	private static final String UPDATE = 
			"UPDATE DAYOFF set dayoff_Time=? where COACH_ID=? and DAYOFF_DATE=?";
	
	
	@Override
	public void insert(DayoffVO dayoffVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
		
			pstmt.setString(1, dayoffVO.getCoach_Id());
			pstmt.setDate(2, dayoffVO.getDayoff_Date());
			pstmt.setString(3, dayoffVO.getDayoff_Time());
			
			pstmt.executeUpdate();
		

		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void update(DayoffVO dayoffVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dayoffVO.getDayoff_Time());
			pstmt.setString(2, dayoffVO.getCoach_Id());
			pstmt.setDate(3, dayoffVO.getDayoff_Date());
			
			pstmt.executeUpdate();
			

		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public List<DayoffVO> oneCoachAllTime(String coach_Id) {
		List<DayoffVO> list1 = new ArrayList<DayoffVO>();
		DayoffVO dayoffVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(ONECOACH_ALLTIME);
					
			pstmt.setString(1, coach_Id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				dayoffVO = new DayoffVO();
				dayoffVO.setDayoff_Id(rs.getString("dayoff_Id"));
				dayoffVO.setCoach_Id(rs.getString("coach_Id"));
				dayoffVO.setDayoff_Date(rs.getDate("dayoff_Date"));
				dayoffVO.setDayoff_Time(rs.getString("dayoff_Time"));
				list1.add(dayoffVO);
			}
			

		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		}finally {
			
			if (rs != null) {
				try {
					rs.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list1;
	}
	
	@Override
	public List<DayoffVO> getAll() {
		List<DayoffVO> list = new ArrayList<DayoffVO>();
		DayoffVO dayoffVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				dayoffVO = new DayoffVO();
				dayoffVO.setDayoff_Id(rs.getString("dayoff_Id"));
				dayoffVO.setCoach_Id(rs.getString("coach_Id"));
				dayoffVO.setDayoff_Date(rs.getDate("dayoff_Date"));
				dayoffVO.setDayoff_Time(rs.getString("dayoff_Time"));
				list.add(dayoffVO);
			}
			

			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		}finally {
			if (rs != null) {
				try {
					rs.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
}
