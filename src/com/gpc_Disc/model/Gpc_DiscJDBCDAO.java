package com.gpc_Disc.model;

import java.util.*;
import java.sql.*;

public class Gpc_DiscJDBCDAO implements Gpc_DiscDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String GET_ALL_STMT = 
		"SELECT gpc_Disc_Id, gpc_Id, member_Id, question, answer, init_Stamp, upl_Stamp, mute FROM gpc_Disc";
	
	private static final String GET_ONE_STMT = 
		"SELECT gpc_Disc_Id, gpc_Id, member_Id, question, answer, init_Stamp, upl_Stamp, mute FROM gpc_Disc where gpc_Disc_Id = ?";	
	
	private static final String INSERT_STMT =  
		"INSERT INTO GPC_DISC(GPC_DISC_ID, GPC_ID, MEMBER_ID, QUESTION, ANSWER)"
		+ "VALUES('DISC' || LPAD (GPC_DISC_SEQ.NEXTVAL, 3, '0'), ?, ?, ?, ?)";
	
	private static final String UPDATE = 
		"UPDATE GPC_DISC set QUESTION=?, ANSWER=?, UPL_STAMP=?, MUTE=?"
		+ "WHERE GPC_DISC_ID=?";
			

	@Override
    public List<Gpc_DiscVO> getAll(){
		List<Gpc_DiscVO> gpc_Disc_list = new ArrayList<>();
		Gpc_DiscVO gpc_DiscVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_DiscVO = new Gpc_DiscVO();
				gpc_DiscVO.setGpc_Disc_Id(rs.getString("gpc_Disc_Id"));
				gpc_DiscVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_DiscVO.setMember_Id(rs.getString("member_Id"));
				gpc_DiscVO.setQuestion(rs.getString("question"));
				gpc_DiscVO.setAnswer(rs.getString("answer"));
				gpc_DiscVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpc_DiscVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpc_DiscVO.setMute(rs.getInt("mute"));
				
				gpc_Disc_list.add(gpc_DiscVO);
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
		return gpc_Disc_list;
	
    }
	
	@Override
    public Gpc_DiscVO findByPK(String gpc_Disc_Id) {
		Gpc_DiscVO gpc_DiscVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, gpc_Disc_Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_DiscVO = new Gpc_DiscVO();
				gpc_DiscVO.setGpc_Disc_Id(rs.getString("gpc_Disc_Id"));
				gpc_DiscVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_DiscVO.setMember_Id(rs.getString("member_Id"));
				gpc_DiscVO.setQuestion(rs.getString("question"));
				gpc_DiscVO.setAnswer(rs.getString("answer"));
				gpc_DiscVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpc_DiscVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpc_DiscVO.setMute(rs.getInt("mute"));
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

		return gpc_DiscVO;
    }
	
	
	@Override
	public void insert(Gpc_DiscVO gpc_DiscVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gpc_DiscVO.getGpc_Id());
			pstmt.setString(2, gpc_DiscVO.getMember_Id());
			pstmt.setString(3, gpc_DiscVO.getQuestion());
			pstmt.setString(4, gpc_DiscVO.getAnswer());

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
    public void update(Gpc_DiscVO gpc_DiscVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE);
			
			pstmt.setString(1, gpc_DiscVO.getQuestion());
			pstmt.setString(2, gpc_DiscVO.getAnswer());
			//更新時間戳記
			pstmt.setTimestamp(3, gpc_DiscVO.getUpl_Stamp());
			pstmt.setInt(4, gpc_DiscVO.getMute());
			pstmt.setString(5, gpc_DiscVO.getGpc_Disc_Id());
			
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
    public void delete(String Gpc_Dis_Id) { // 僅供測試用
    }


    
}
