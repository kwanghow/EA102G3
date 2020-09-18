package com.dayoff.model;

import java.util.List;
import java.util.*;
import java.sql.*;

public class DayoffJDBCDAO implements DayoffDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
		
			pstmt.setString(1, dayoffVO.getCoach_Id());
			pstmt.setDate(2, dayoffVO.getDayoff_Date());
			pstmt.setString(3, dayoffVO.getDayoff_Time());
			
			pstmt.executeUpdate();
		
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, dayoffVO.getDayoff_Time());
			pstmt.setString(2, dayoffVO.getCoach_Id());
			pstmt.setDate(3, dayoffVO.getDayoff_Date());
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
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
	
	public static void main(String[] agrs) {
		
		DayoffJDBCDAO dao = new DayoffJDBCDAO();
		
		//新增教練休息時間
//		DayoffVO dayoff1 = new DayoffVO();
//		dayoff1.setCoach_Id("C02");
//		dayoff1.setDayoff_Date(java.sql.Date.valueOf("2020-08-31"));
//		dayoff1.setDayoff_Time("111111111111111111111111");
//		dao.insert(dayoff1);
		
		//修改教練休息時間
//		DayoffVO dayoff2 = new DayoffVO();
//		dayoff2.setCoach_Id("C02");
//		dayoff2.setDayoff_Date(java.sql.Date.valueOf("2020-08-31"));
//		dayoff2.setDayoff_Time("110000000000000000000000");
//		dao.update(dayoff2);
		
		//查詢一個教練的所有排休時間	
		List<DayoffVO> list1 = dao.oneCoachAllTime("C01");
		for (DayoffVO oneCoach : list1) {
			System.out.println(oneCoach.getDayoff_Id() + ",");
			System.out.println(oneCoach.getCoach_Id() + ",");
			System.out.println(oneCoach.getDayoff_Date() + ",");
			System.out.println(oneCoach.getDayoff_Time() + ",");
			System.out.println("------------------------------");
		}
		
		//查詢所有教練排休時間
//		List<DayoffVO> list = dao.getAll();
//		for (DayoffVO aDayoff : list) {
//			System.out.println(aDayoff.getDayoff_Id() + ",");
//			System.out.println(aDayoff.getCoach_Id() + ",");
//			System.out.println(aDayoff.getDayoff_Date() + ",");
//			System.out.println(aDayoff.getDayoff_Time() + ",");
//			System.out.println("------------------------------");
//		}
	}
}
