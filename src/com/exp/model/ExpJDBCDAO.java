package com.exp.model;

import java.util.List;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class ExpJDBCDAO implements ExpDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	
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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, expVO.getCoach_Id());
			pstmt.setString(2, expVO.getExp_Id());
//			pstmt.setBytes(3, expVO.getLicense());
//			pstmt.setInt(4, 0);
			
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
	public void update(ExpVO expVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, expVO.getLicense());
			pstmt.setInt(2, expVO.getIsExp());
			pstmt.setString(3, expVO.getCoach_Id());
			pstmt.setString(4, expVO.getExp_Id());
			
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
		
			pstmt.setString(1, coach_Id);
			pstmt.setString(2, exp_Id);
			
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
	public List<ExpVO> oneCoachHowManySkill(String coach_Id) {
		List<ExpVO> list2 = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		return list3;
	}

	@Override
	public List<ExpVO> getAll() {
		List<ExpVO> list = new ArrayList<ExpVO>();
		ExpVO expVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_FORMBACK);
			
			for(ExpVO expVO : expList) {
			pstmt.setInt(1, expVO.getIsExp());
			pstmt.setString(2, expVO.getCoach_Id());
			pstmt.setString(3, expVO.getExp_Id());
			pstmt.executeUpdate() ;
			}
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
	
	public List<ExpVO> oneCoachHowManySkillIsExp(String coach_Id) {
		List<ExpVO> list2 = new ArrayList<ExpVO>();
		ExpVO expVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list2;
	}
	
	public static void main(String[] args) throws IOException {
		
		ExpJDBCDAO dao = new ExpJDBCDAO();
		
		//新增教練專長
//		ExpVO expVO1 = new ExpVO();
//		expVO1.setCoach_Id("C04");
//		expVO1.setExp_Id("S10");
//		expVO1.setLicense(null);
//		expVO1.setIsExp(1);
//		dao.insert(expVO1);
		
		//修改教練專長資料
		ExpVO expVO2 = new ExpVO();
		expVO2.setCoach_Id("C02");
		expVO2.setExp_Id("S09");
		byte[] pic = getPictureByteArray("WebContent/front-end/assets/images/b5.jpg");
		expVO2.setLicense(pic);
		expVO2.setIsExp(1);
		dao.update(expVO2);
		
		//刪除教練專長
//		dao.delete("C04", "S10");
		
		//查詢所有教練與專長
//		List<ExpVO> list = dao.getAll();
//		for (ExpVO aExp : list) {
//			System.out.println(aExp.getCoach_Id() + ",");
//			System.out.println(aExp.getExp_Id() + ",");
//			System.out.println(aExp.getLicense() + ",");
//			System.out.println(aExp.getIsExp() + ",");
//			System.out.println("----------------");
//		}
		
		//查詢一個教練所擁有的專長
//		List<ExpVO> list2 = dao.oneCoachHowManySkill("C01");
//		for (ExpVO coachExp : list2) {
//			System.out.println(coachExp.getCoach_Id() + ",");
//			System.out.println(coachExp.getExp_Id() + ",");
//			System.out.println(coachExp.getLicense() + ",");
//			System.out.println(coachExp.getIsExp() + ",");
//			System.out.println("----------------");
//		}
		
		//查詢一個專長有幾個教練所擁有
//		List<ExpVO> list3 = dao.oneSkillHowManyCoach("S06");
//		for (ExpVO skillExp : list3) {
//			System.out.println(skillExp.getCoach_Id() + ",");
//			System.out.println("----------------");
//		}
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		// ByteArray 也是一個低階管, 管子裡面已經有一個預設的Byte的空陣列
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

}
