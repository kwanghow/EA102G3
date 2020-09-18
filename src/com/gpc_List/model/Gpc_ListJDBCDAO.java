package com.gpc_List.model;

import java.util.*;

import com.gpc.model.GpcVO;

import java.sql.*;

public class Gpc_ListJDBCDAO implements Gpc_ListDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String GET_ALL_STMT = 
		"SELECT gpc_Id, member_Id, pay_Init, pay_Exp, init_Stamp, upl_Stamp, mem_State FROM gpc_List order by init_Stamp desc";
	
	private static final String SELECT_GPC_STMT = 
		"SELECT gpc_Id, member_Id, pay_Init, pay_Exp, init_Stamp, upl_Stamp, mem_State FROM gpc_List where gpc_Id = ?";	
	
	private static final String SELECT_MEM_STMT = 
		"SELECT gpc_Id, member_Id, pay_Init, pay_Exp, init_Stamp, upl_Stamp, mem_State FROM gpc_List where member_Id = ?";
	
	private static final String SELECT_CPK_STMT = 
			"SELECT gpc_Id, member_Id, pay_Init, pay_Exp, init_Stamp, upl_Stamp, mem_State FROM gpc_List where gpc_Id = ? and member_Id = ?";
		
	private static final String INSERT_STMT =  
		"INSERT INTO GPC_LIST(gpc_Id, member_Id, pay_Init, pay_Exp, mem_State) VALUES(?, ?, ?, ?, ?)";
	
	private static final String UPDATE= 
		"UPDATE GPC_LIST set pay_Init=?, pay_Exp=?, upl_Stamp=?, mem_State=?"
		+ "WHERE gpc_Id=? and member_Id=?";
	
	private static final String CHANGE_STATE_STMT =
			"UPDATE GPC_LIST set upl_Stamp=?, mem_State=? WHERE gpc_Id=? and member_Id=?";
	
			
	@Override
    public List<Gpc_ListVO> getAll(){
		List<Gpc_ListVO> gpc_List_list = new ArrayList<>();
		Gpc_ListVO gpc_ListVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_ListVO = new Gpc_ListVO();

				gpc_ListVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_ListVO.setMember_Id(rs.getString("member_Id"));
				gpc_ListVO.setPay_Init(rs.getDate("pay_Init"));
				gpc_ListVO.setPay_Exp(rs.getDate("pay_Exp"));
				gpc_ListVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpc_ListVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpc_ListVO.setMem_State(rs.getInt("mem_State"));
				
				gpc_List_list.add(gpc_ListVO);
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
		return gpc_List_list;
	
    }
	
	@Override
    public List<Gpc_ListVO> findById(String Id){
    	List<Gpc_ListVO> gpc_List_list = new ArrayList<>();
		Gpc_ListVO gpc_ListVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			String prefix = Id.substring(0, 1);		
			pstmt = (prefix.equals("M"))? conn.prepareStatement(SELECT_MEM_STMT): conn.prepareStatement(SELECT_GPC_STMT);
			pstmt.setString(1, Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_ListVO = new Gpc_ListVO();
				gpc_ListVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_ListVO.setMember_Id(rs.getString("member_Id"));
				gpc_ListVO.setPay_Init(rs.getDate("pay_Init"));
				gpc_ListVO.setPay_Exp(rs.getDate("pay_Exp"));
				gpc_ListVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpc_ListVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpc_ListVO.setMem_State(rs.getInt("mem_State"));
				
				gpc_List_list.add(gpc_ListVO);
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

		return gpc_List_list;
    }
	

	@Override
	public void insert(Gpc_ListVO gpc_ListVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gpc_ListVO.getGpc_Id());
			pstmt.setString(2, gpc_ListVO.getMember_Id());
			pstmt.setDate(3, gpc_ListVO.getPay_Init());
			pstmt.setDate(4, gpc_ListVO.getPay_Exp());
			pstmt.setInt(5, 1);
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
    public void update(Gpc_ListVO gpc_ListVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE);
			
			pstmt.setDate(1, gpc_ListVO.getPay_Init());
			pstmt.setDate(2, gpc_ListVO.getPay_Exp());
			pstmt.setTimestamp(3, gpc_ListVO.getUpl_Stamp());
			pstmt.setInt(4, gpc_ListVO.getMem_State());
			pstmt.setString(5, gpc_ListVO.getGpc_Id());
			pstmt.setString(6, gpc_ListVO.getMember_Id());
			
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
	
	//更改狀態用
	@Override
    public void changeState(Gpc_ListVO gpc_ListVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(CHANGE_STATE_STMT);
			
			//更新時間戳記
	
			pstmt.setTimestamp(1, gpc_ListVO.getUpl_Stamp());
			pstmt.setInt(2, gpc_ListVO.getMem_State());
			pstmt.setString(3, gpc_ListVO.getGpc_Id()); // 複合主鍵
			pstmt.setString(4, gpc_ListVO.getMember_Id()); // 複合主鍵
			
			pstmt.executeUpdate();
			System.out.println("DAO執行成功");
			
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
    public Gpc_ListVO findByCPK(String gpc_Id, String member_Id){
		Gpc_ListVO gpc_ListVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt.setString(1, gpc_Id);
			pstmt.setString(2, member_Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpc_ListVO = new Gpc_ListVO();
				gpc_ListVO.setGpc_Id(rs.getString("gpc_Id"));
				gpc_ListVO.setMember_Id(rs.getString("member_Id"));
				gpc_ListVO.setPay_Init(rs.getDate("pay_Init"));
				gpc_ListVO.setPay_Exp(rs.getDate("pay_Exp"));
				gpc_ListVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpc_ListVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpc_ListVO.setMem_State(rs.getInt("mem_State"));
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
		
		return gpc_ListVO;

	}


    
}
