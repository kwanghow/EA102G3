package com.gpc_CoachView.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Gpc_CoachViewJDBCDAO implements Gpc_CoachViewDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String SELECT_PERIOD_3MM = 
		"select view_date, view_time from coach_view "
		+ "where coach_Id = ? and (view_date BETWEEN ? AND ?)"
		+ "order by view_date";
				
	
	@Override
    public List<Gpc_CoachViewVO> findPeriod_3MM(String coach_Id, java.sql.Date startDate, java.sql.Date endDate){
		List<Gpc_CoachViewVO> gpc_CoachView_list = new ArrayList<>();
		Gpc_CoachViewVO gpc_CoachViewVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(SELECT_PERIOD_3MM);
			pstmt.setString(1, coach_Id);
			pstmt.setDate(2, startDate);
			pstmt.setDate(3, endDate);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_CoachViewVO = new Gpc_CoachViewVO();

				gpc_CoachViewVO.setCoach_Id(coach_Id);
				gpc_CoachViewVO.setView_Date(rs.getDate(1));
				gpc_CoachViewVO.setView_Time(rs.getString(2));
				
				gpc_CoachView_list.add(gpc_CoachViewVO);
				
			
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return gpc_CoachView_list;
	
    }
	    
}
