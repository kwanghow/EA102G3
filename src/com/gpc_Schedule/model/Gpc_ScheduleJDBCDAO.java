package com.gpc_Schedule.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class Gpc_ScheduleJDBCDAO implements Gpc_ScheduleDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String GET_ALL_STMT = 
		"SELECT gpc_Id, gpc_Date, gpc_Time FROM gpc_Schedule";
	
	private static final String SELECT_GPC_STMT = 
		"SELECT gpc_Id, gpc_Date, gpc_Time FROM gpc_Schedule where gpc_Id = ? ORDER BY gpc_Date";	
	
	//這裡要用自增主鍵值綁定
	private static final String INSERT_STMT =  
		"INSERT INTO gpc_Schedule(gpc_Id, gpc_Date, gpc_Time) VALUES(?, ?, ?)";
	
	//應該用不到，先放著
	private static final String UPDATE= 
		"UPDATE gpc_Schedule set gpc_Date=?, gpc_Time=? WHERE gpc_Id=?";
	
			
	@Override
    public List<Gpc_ScheduleVO> getAll(){
		List<Gpc_ScheduleVO> gpc_Schedule_list = new ArrayList<>();
		Gpc_ScheduleVO gpc_ScheduleVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_ScheduleVO = new Gpc_ScheduleVO();

				gpc_ScheduleVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_ScheduleVO.setGpc_Date(rs.getDate("gpc_Date"));
				gpc_ScheduleVO.setGpc_Time(rs.getString("gpc_Time"));
				
				gpc_Schedule_list.add(gpc_ScheduleVO);
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
		return gpc_Schedule_list;
	
    }
	
	@Override
    public List<Gpc_ScheduleVO> findById(String Id){
		List<Gpc_ScheduleVO> gpc_Schedule_list = new ArrayList<>();
		Gpc_ScheduleVO gpc_ScheduleVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(SELECT_GPC_STMT);
			pstmt.setString(1, Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_ScheduleVO = new Gpc_ScheduleVO();
				gpc_ScheduleVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_ScheduleVO.setGpc_Date(rs.getDate("gpc_Date"));
				gpc_ScheduleVO.setGpc_Time(rs.getString("gpc_Time"));
				gpc_Schedule_list.add(gpc_ScheduleVO);
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

		return gpc_Schedule_list;
    }
	

	@Override
	public void insert(Gpc_ScheduleVO gpc_ScheduleVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gpc_ScheduleVO.getGpc_Id());
			pstmt.setDate(2, gpc_ScheduleVO.getGpc_Date());
			pstmt.setString(3, gpc_ScheduleVO.getGpc_Time());

			pstmt.executeUpdate();
			
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
		
	}
	
	
	@Override
    public void update(Gpc_ScheduleVO gpc_ScheduleVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE);
			
			
			pstmt.setDate(1, gpc_ScheduleVO.getGpc_Date());
			pstmt.setString(2, gpc_ScheduleVO.getGpc_Time());
			pstmt.setString(3,gpc_ScheduleVO.getGpc_Id());
			
			pstmt.executeUpdate();
			
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
    }
	
	//自增主鍵值綁定
	@Override
	public void insertWithGpc_Id(String gpc_Id, Map.Entry singleMap, Connection conn) {
		PreparedStatement pstmt =null;
		try {
			Class.forName(driver);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			//把key (String) 轉成Date
			java.sql.Date gpc_Date = java.sql.Date.valueOf((String)singleMap.getKey());
			String gpc_Time = (String) singleMap.getValue();
			
			pstmt.setString(1, gpc_Id);
			pstmt.setDate(2, gpc_Date);
			pstmt.setString(3, gpc_Time);

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

			// Handle any SQL errors
		} catch (SQLException se) {
			if (conn != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-OrderMaster");
					conn.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}




    
}
