package com.gpc_Rep.model;

import java.util.*;
import java.sql.*;

public class Gpc_RepJDBCDAO implements Gpc_RepDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String GET_ALL_STMT = 
		"SELECT gpc_Rep_Id, gpc_Disc_Id, member_Id, reason, rep_state";
	
	private static final String GET_ONE_STMT = 
		"SELECT gpc_Rep_Id, gpc_Disc_Id, member_Id, reason, rep_state where gpc_Rep_Id = ?";	
	
	private static final String INSERT_STMT =  
		"INSERT INTO GPC_REP(GPC_REP_ID, GPC_DISC_ID, MEMBER_ID, REASON)"
		+ "VALUES('REP' || LPAD (GPC_DISC_SEQ.NEXTVAL, 3, '0'), ?, ?, ?)";
	
	private static final String UPDATE = 
		"UPDATE GPC_REP set reason=?, rep_state=? WHERE GPC_DISC_ID=?";
			           

	@Override
    public List<Gpc_RepVO> getAll(){
		List<Gpc_RepVO> gpc_Rep_list = new ArrayList<>();
		Gpc_RepVO gpc_RepVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_RepVO = new Gpc_RepVO();
				gpc_RepVO.setGpc_Rep_Id(rs.getString("gpc_Rep_Id"));
				gpc_RepVO.setGpc_Disc_Id(rs.getString("gpc_Disc_Id"));
				gpc_RepVO.setMember_Id(rs.getString("member_Id"));
				gpc_RepVO.setReason(rs.getString("reason"));
				gpc_RepVO.setRep_state(rs.getInt("rep_state"));
				
				gpc_Rep_list.add(gpc_RepVO);
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
		return gpc_Rep_list;
	
    }
	
	@Override
    public Gpc_RepVO findByPK(String gpc_Rep_Id) {
		Gpc_RepVO gpc_RepVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, gpc_Rep_Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_RepVO = new Gpc_RepVO();
				gpc_RepVO.setGpc_Rep_Id(rs.getString("gpc_Rep_Id"));
				gpc_RepVO.setGpc_Disc_Id(rs.getString("gpc_Disc_Id"));
				gpc_RepVO.setMember_Id(rs.getString("member_Id"));
				gpc_RepVO.setReason(rs.getString("reason"));
				gpc_RepVO.setRep_state(rs.getInt("rep_state"));
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

		return gpc_RepVO;
    }
	
	
	@Override
	public void insert(Gpc_RepVO gpc_RepVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gpc_RepVO.getGpc_Disc_Id());
			pstmt.setString(2, gpc_RepVO.getMember_Id());
			pstmt.setString(3, gpc_RepVO.getReason());

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
    public void update(Gpc_RepVO gpc_RepVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE);
			
			pstmt.setString(1, gpc_RepVO.getReason());
			pstmt.setInt(2, gpc_RepVO.getRep_state());
		
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
    public void delete(String Gpc_Dis_Id) { // ¶È¨Ñ´ú¸Õ¥Î
    }


    
}
