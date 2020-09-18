package com.mem.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemDAO implements MemDAO_interface{
	
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
			
			con =ds.getConnection();
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
			
			con =ds.getConnection();
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

			con =ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, member_Id);

			pstmt.executeUpdate();


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
			
			con =ds.getConnection();
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
			
			con =ds.getConnection();
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
			
			con =ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_FROM_BACK);

			pstmt.setInt(1, mem_Close);
			pstmt.setString(2, member_Id);

			pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_IMG);

			pstmt.setBytes(1, memVO.getMem_Img());
			pstmt.setString(2, memVO.getMember_Id());

			pstmt.executeUpdate();


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

}