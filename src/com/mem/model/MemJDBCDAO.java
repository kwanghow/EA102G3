package com.mem.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MemJDBCDAO implements MemDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	private static final String INSERT_STMT =
			"INSERT INTO MEMBER (member_Id, mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone, mem_Birth, mem_Addr, mem_Close, mem_Card) VALUES ('M' || LPAD (member_seq.NEXTVAL, 3, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT =
			"SELECT member_Id, mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone, to_char(mem_Birth,'yyyy-mm-dd') mem_Birth, mem_Addr, mem_Close, mem_Card FROM MEMBER order by MEMBER_ID";
	private static final String GET_ONE_STMT =
			"SELECT member_Id, mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone, to_char(mem_Birth,'yyyy-mm-dd') mem_Birth, mem_Addr, mem_Close, mem_Card FROM MEMBER where MEMBER_ID = ?";
	private static final String DELETE =                                                               
			"DELETE FROM MEMBER where MEMBER_ID = ?";
	private static final String UPDATE = 
			"UPDATE MEMBER set mem_Name=?, mem_Sex=?, mem_Account=?, mem_Psw=?, mem_Img=?, mem_Email=?, mem_Phone=?, mem_Birth=?, mem_Addr=?, mem_Close=?, mem_Card=? where member_id = ?";
	private static final String LOGIN =
			"SELECT * from MEMBER where mem_Account=?";
	private static final String UPDATE_FROM_BACK = 
			"UPDATE MEMBER set mem_Close=? where member_Id=?";
	private static final String UPDATE_IMG = 
			"UPDATE MEMBER set mem_Img=? where member_Id=?";
	
	
	
	@Override
	public void insert(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, memVO.getMem_Name());
			pstmt.setString(2, memVO.getMem_Sex());
			pstmt.setString(3, memVO.getMem_Account());
			pstmt.setString(4, memVO.getMem_Psw());
			pstmt.setBytes(5, memVO.getMem_Img());
			pstmt.setString(6, memVO.getMem_Email());
			pstmt.setString(7, memVO.getMem_Phone());
			pstmt.setDate(8, memVO.getMem_Birth());
			pstmt.setString(9, memVO.getMem_Addr());
			pstmt.setInt(10, 0);
			pstmt.setString(11, memVO.getMem_Card());
			
			pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Could't load database driver. "
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
	public void update(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			
			pstmt.setString(1, memVO.getMem_Name());
			pstmt.setString(2, memVO.getMem_Sex());
			pstmt.setString(3, memVO.getMem_Account());
			pstmt.setString(4, memVO.getMem_Psw());
			pstmt.setBytes(5, memVO.getMem_Img());
			pstmt.setString(6, memVO.getMem_Email());
			pstmt.setString(7, memVO.getMem_Phone());
			pstmt.setDate(8, memVO.getMem_Birth());
			pstmt.setString(9, memVO.getMem_Addr());
			pstmt.setInt(10, memVO.getMem_Close());
			pstmt.setString(11, memVO.getMem_Card());
			pstmt.setString(12, memVO.getMember_Id());
			
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
	public void delete(String member_Id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, member_Id);

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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public MemVO findByPrimaryKey(String member_Id) {
		
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, member_Id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				memVO = new MemVO();
				memVO.setMember_Id(rs.getString("member_Id"));
				memVO.setMem_Name(rs.getString("mem_Name"));
				memVO.setMem_Sex(rs.getString("mem_Sex"));
				memVO.setMem_Account(rs.getString("mem_Account"));
				memVO.setMem_Psw(rs.getString("mem_Psw"));
				memVO.setMem_Img(rs.getBytes("mem_Img"));
				memVO.setMem_Email(rs.getString("mem_Email"));
				memVO.setMem_Phone(rs.getString("mem_Phone"));
				memVO.setMem_Birth(rs.getDate("mem_Birth"));
				memVO.setMem_Addr(rs.getString("mem_Addr"));
				memVO.setMem_Close(rs.getInt("mem_Close"));
				memVO.setMem_Card(rs.getString("mem_Card"));
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
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return memVO;
	}
	
	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs =pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMember_Id(rs.getString("member_Id"));
				memVO.setMem_Name(rs.getString("mem_Name"));
				memVO.setMem_Sex(rs.getString("mem_Sex"));
				memVO.setMem_Account(rs.getString("mem_Account"));
				memVO.setMem_Psw(rs.getString("mem_Psw"));
				memVO.setMem_Img(rs.getBytes("mem_Img"));
				memVO.setMem_Email(rs.getString("mem_Email"));
				memVO.setMem_Phone(rs.getString("mem_Phone"));
				memVO.setMem_Birth(rs.getDate("mem_Birth"));
				memVO.setMem_Addr(rs.getString("mem_Addr"));
				memVO.setMem_Close(rs.getInt("mem_Close"));
				memVO.setMem_Card(rs.getString("mem_Card"));
				list.add(memVO);
			}
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
			
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
	public MemVO logIn(String mem_Account) {
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt =con.prepareStatement(LOGIN);
			
			pstmt.setString(1, mem_Account);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMember_Id(rs.getString("member_Id"));
				memVO.setMem_Name(rs.getString("mem_Name"));
				memVO.setMem_Sex(rs.getString("mem_Sex"));
				memVO.setMem_Account(rs.getString("mem_Account"));
				memVO.setMem_Psw(rs.getString("mem_Psw"));
				memVO.setMem_Img(rs.getBytes("mem_Img"));
				memVO.setMem_Email(rs.getString("mem_Email"));
				memVO.setMem_Phone(rs.getString("mem_Phone"));
				memVO.setMem_Birth(rs.getDate("mem_Birth"));
				memVO.setMem_Addr(rs.getString("mem_Addr"));
				memVO.setMem_Close(rs.getInt("mem_Close"));
				memVO.setMem_Card(rs.getString("mem_Card"));
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
		return memVO;
	}
	
	@Override
	public void updateFromBack(String member_Id, Integer mem_Close) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_FROM_BACK);

			pstmt.setInt(1, mem_Close);
			pstmt.setString(2, member_Id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

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
	}
	
	@Override
	public void updateImg(MemVO memVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_IMG);

			pstmt.setBytes(1, memVO.getMem_Img());
			pstmt.setString(2, memVO.getMember_Id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());

		} finally {

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
	}
	
	public static void main(String[] args) throws IOException {
		MemJDBCDAO dao = new MemJDBCDAO();
		// 新增資料
//		MemVO memVO1 = new MemVO();
//		memVO1.setMem_Name("武則天");
//		memVO1.setMem_Sex("女");
//		memVO1.setMem_Account("king");
//		memVO1.setMem_Psw("mama07");
//		byte[] pic = getPictureByteArray("WebContent/pic/1.jpg");
//		memVO1.setMem_Img(pic);
//		memVO1.setMem_Email("abc111@gmail.com");
//		memVO1.setMem_Phone("789456123");
//		memVO1.setMem_Birth(java.sql.Date.valueOf("1930-01-01"));
//		memVO1.setMem_Addr("天母聖殿");
//		memVO1.setMem_Close(1);
//		memVO1.setMem_Card("4444555566667777");
//		dao.insert(memVO1);
					
		
		// 修改資料
//		MemVO memVO2 = new MemVO();
//		memVO2.setMember_Id("M028");
//		memVO2.setMem_Name("武則天");
//		memVO2.setMem_Sex("女");
//		memVO2.setMem_Account("king");
//		memVO2.setMem_Psw("mama07");
//		byte[] pic = getPictureByteArray("WebContent/pic/10.jpg");
//		memVO2.setMem_Img(pic);
//		memVO2.setMem_Email("abc111@gmail.com");
//		memVO2.setMem_Phone("7894561230");
//		memVO2.setMem_Birth(java.sql.Date.valueOf("1930-01-01"));
//		memVO2.setMem_Addr("天母聖殿");
//		memVO2.setMem_Close(1);
//		memVO2.setMem_Card("2222111133335555");
//		dao.update(memVO2);
		
		// 刪除資料
		dao.delete("M028");
		
		// 查詢單筆
//		MemVO memVO3 = dao.findByPrimaryKey("M028");		
//		System.out.println(memVO3.getMember_Id() + ",");
//		System.out.println(memVO3.getMem_Name() + ",");
//		System.out.println(memVO3.getMem_Sex() + ",");
//		System.out.println(memVO3.getMem_Account() + ",");
//		System.out.println(memVO3.getMem_Psw() + ",");
//		System.out.println(memVO3.getMem_Img() + ",");
//		System.out.println(memVO3.getMem_Email() + ",");
//		System.out.println(memVO3.getMem_Phone() + ",");
//		System.out.println(memVO3.getMem_Birth() + ",");
//		System.out.println(memVO3.getMem_Addr() + ",");
//		System.out.println(memVO3.getMem_Close() + ",");
//		System.out.println(memVO3.getMem_Card());
//		System.out.println("--------------------");
		
		// 查詢全部
//		List<MemVO> list = dao.getAll();
//		for (MemVO aMem : list) {
//			System.out.println(aMem.getMember_Id() + ",");
//			System.out.println(aMem.getMem_Name() + ",");
//			System.out.println(aMem.getMem_Sex() + ",");
//			System.out.println(aMem.getMem_Account() + ",");
//			System.out.println(aMem.getMem_Psw() + ",");
//			System.out.println(aMem.getMem_Img() + ",");
//			System.out.println(aMem.getMem_Email() + ",");
//			System.out.println(aMem.getMem_Phone() + ",");
//			System.out.println(aMem.getMem_Birth() + ",");
//			System.out.println(aMem.getMem_Addr() + ",");
//			System.out.println(aMem.getMem_Close() + ",");
//			System.out.println(aMem.getMem_Card());
//			System.out.println("--------------------");
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
