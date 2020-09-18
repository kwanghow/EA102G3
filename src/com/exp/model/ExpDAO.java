package com.exp.model;

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
import javax.sql.*;

public class ExpDAO implements ExpDAO_interface{
	
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
			"INSERT INTO EXP (coach_Id, exp_Id, isExp) VALUES (?, ?, '0')";
	private static final String GET_ALL_STMT = 
			"SELECT coach_Id, exp_Id, license, isExp FROM EXP order by COACH_ID";
	private static final String ONECOACH_HOWMANY_SKILL = 
			"SELECT coach_Id, exp_Id, license, isExp FROM EXP where coach_Id=?";
	private static final String ONESKILL_HOWMANY_COACH = 
			"SELECT coach_Id, exp_Id, license, isExp FROM EXP where exp_Id=?";
	private static final String UPDATE = 
			"UPDATE EXP set license=?, isExp=? where coach_Id=? and exp_Id=?";
	private static final String DELETE =
			"DELETE FROM EXP where COACH_ID=? and EXP_ID=?";
	private static final String UPDATE_FORMBACK = 
			"UPDATE EXP set isExp=? where coach_Id=? and exp_Id=?";

	
	@Override
	public void insert(ExpVO expVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, expVO.getCoach_Id());
			pstmt.setString(2, expVO.getExp_Id());
//			pstmt.setBytes(3, expVO.getLicense());
//			pstmt.setInt(4, 0);
			
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
	public void update(ExpVO expVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, expVO.getLicense());
			pstmt.setInt(2, expVO.getIsExp());
			pstmt.setString(3, expVO.getCoach_Id());
			pstmt.setString(4, expVO.getExp_Id());
			
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
				}catch (Exception se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void delete(String coach_Id, String exp_Id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
		
			pstmt.setString(1, coach_Id);
			pstmt.setString(2, exp_Id);
			
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
	public List<ExpVO> oneCoachHowManySkill(String coach_Id) {
		List<ExpVO> list2 = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(ONECOACH_HOWMANY_SKILL);
			
			pstmt.setString(1, coach_Id);
			
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				expVO = new ExpVO();
				expVO.setCoach_Id(rs.getString("coach_Id"));
				expVO.setExp_Id(rs.getString("exp_Id"));
				expVO.setLicense(rs.getBytes("license"));
				expVO.setIsExp(rs.getInt("isExp"));
				list2.add(expVO);
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
		return list2;
	}

	@Override
	public List<ExpVO> oneSkillHowManyCoach(String exp_Id) {
		List<ExpVO> list3 = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(ONESKILL_HOWMANY_COACH);
			
			pstmt.setString(1, exp_Id);
			
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				
				expVO = new ExpVO();
				expVO.setCoach_Id(rs.getString("coach_Id"));
//				expVO.setExp_Id(rs.getString("exp_Id"));
//				expVO.setLicense(rs.getBytes("license"));
//				expVO.setIsExp(rs.getInt("isExp"));
				list3.add(expVO);
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
		return list3;
	}

	@Override
	public List<ExpVO> oneCoachHowManySkillIsExp(String coach_Id) {
		List<ExpVO> list2 = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement("SELECT * FROM EXP WHERE COACH_ID = ? AND ISEXP = 1 ORDER BY EXP_ID");
			
			pstmt.setString(1, coach_Id);
			
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				expVO = new ExpVO();
				expVO.setCoach_Id(rs.getString("coach_Id"));
				expVO.setExp_Id(rs.getString("exp_Id"));
				expVO.setLicense(rs.getBytes("license"));
				expVO.setIsExp(rs.getInt("isExp"));
				list2.add(expVO);
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
		return list2;
	}
	
	@Override
	public List<ExpVO> getAll() {
		List<ExpVO> list = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				expVO = new ExpVO();
				expVO.setCoach_Id(rs.getString("coach_Id"));
				expVO.setExp_Id(rs.getString("exp_Id"));
				expVO.setLicense(rs.getBytes("license"));
				expVO.setIsExp(rs.getInt("isExp"));
				list.add(expVO);
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
	
	@Override
	public void insert2(ExpVO expVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, expVO.getCoach_Id());
			pstmt.setString(2, expVO.getExp_Id());
//			pstmt.setBytes(3, expVO.getLicense());
//			pstmt.setInt(4, 0);
			
			pstmt.executeUpdate();
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-EXP");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
		}		
	}
	
	@Override
	public void updateIsExp(List<ExpVO> expList) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FORMBACK);
			
			for(ExpVO expVO : expList) {
			pstmt.setInt(1, expVO.getIsExp());
			pstmt.setString(2, expVO.getCoach_Id());
			pstmt.setString(3, expVO.getExp_Id());
			pstmt.executeUpdate() ;
			}
		
			
			
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
	
}
