package com.gpc.model;

import java.util.*;
import java.util.Map.Entry;

import com.gpc_Schedule.model.Gpc_ScheduleJDBCDAO;

import java.sql.*;

public class GpcJDBCDAO implements GpcDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	

	private static final String GET_ALL_STMT = 
		"SELECT gpc_Id, coach_Id, exp_Id, gpc_Name, address, intro, pic1, pic2, pic3, price, "
		+ "to_char(pay_Start,'yyyy-mm-dd') pay_Start, to_char(gpc_Start,'yyyy-mm-dd') gpc_Start, "
		+ "mem_Min, mem_Max, init_Stamp, upl_Stamp, gpc_State FROM gpc order by upl_Stamp desc";
	
	private static final String GET_ONE_STMT = 
		"SELECT gpc_Id, coach_Id, exp_Id, gpc_Name, address, intro, pic1, pic2, pic3, price, "
		+ "to_char(pay_Start,'yyyy-mm-dd') pay_Start, to_char(gpc_Start,'yyyy-mm-dd') gpc_Start, "
		+ "mem_Min, mem_Max, init_Stamp, upl_Stamp, gpc_State FROM gpc where gpc_Id = ?";	
	
	private static final String INSERT_STMT =  
		"INSERT INTO GPC(GPC_ID, COACH_ID, EXP_ID, GPC_NAME, ADDRESS, INTRO, PIC1, PIC2, PIC3, PRICE, PAY_START, GPC_START, MEM_MIN, MEM_MAX)"
		+ "VALUES('GPC' || LPAD (GPC_SEQ.NEXTVAL, 3, '0'),?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?)";
	
	private static final String UPDATE =  /*update設計成不能改gpc_Start,因為是一群時間的第一天*/
		"UPDATE GPC set gpc_Name=?, address=?, intro=?, pic1=?, pic2=?, pic3=?, price=?, pay_Start=?, mem_Min=?, mem_Max=?, upl_Stamp=?"
		+ "WHERE gpc_Id=?";

	private static final String CHANGE_STATE_STMT =
		"UPDATE GPC set upl_Stamp=?, gpc_State=? WHERE gpc_Id=?";
    
			

	
	@Override
    public List<GpcVO> getAll(){
		List<GpcVO> gpc_list = new ArrayList<>();
		GpcVO gpcVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpcVO = new GpcVO();
				gpcVO.setGpc_Id(rs.getString("gpc_Id"));
				gpcVO.setCoach_Id(rs.getString("coach_Id"));
				gpcVO.setExp_Id(rs.getString("exp_Id"));
				gpcVO.setGpc_Name(rs.getString("gpc_Name"));
				gpcVO.setAddress(rs.getString("address"));
				gpcVO.setIntro(rs.getString("intro"));
				gpcVO.setPic1(rs.getBytes("pic1"));
				gpcVO.setPic2(rs.getBytes("pic2"));
				gpcVO.setPic2(rs.getBytes("pic2"));
				gpcVO.setPrice(rs.getInt("price"));
				gpcVO.setPay_Start(rs.getDate("pay_Start"));
				gpcVO.setGpc_Start(rs.getDate("gpc_Start"));
				gpcVO.setMem_Min(rs.getInt("mem_Min"));
				gpcVO.setMem_Max(rs.getInt("mem_Max"));
				gpcVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpcVO.setUpl_Stamp(rs.getTimestamp("upl_Stamp"));
				gpcVO.setGpc_State(rs.getInt("gpc_State"));
				gpc_list.add(gpcVO);
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
		return gpc_list;
	
    }
	
	@Override
    public GpcVO findByPK(String gpc_Id) {
		GpcVO gpcVO = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, gpc_Id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				gpcVO = new GpcVO();
				gpcVO.setGpc_Id(rs.getString("gpc_Id"));
				gpcVO.setCoach_Id(rs.getString("coach_Id"));
				gpcVO.setExp_Id(rs.getString("exp_Id"));
				gpcVO.setGpc_Name(rs.getString("gpc_Name"));
				gpcVO.setAddress(rs.getString("address"));
				gpcVO.setIntro(rs.getString("intro"));
				gpcVO.setPic1(rs.getBytes("pic1"));
				gpcVO.setPic2(rs.getBytes("pic2"));
				gpcVO.setPic3(rs.getBytes("pic3"));
				gpcVO.setPrice(rs.getInt("price"));
				gpcVO.setPay_Start(rs.getDate("pay_Start"));
				gpcVO.setGpc_Start(rs.getDate("gpc_Start"));
				gpcVO.setMem_Min(rs.getInt("mem_Min"));
				gpcVO.setMem_Max(rs.getInt("mem_Max"));
				gpcVO.setInit_Stamp(rs.getTimestamp("init_Stamp"));
				gpcVO.setInit_Stamp(rs.getTimestamp("upl_Stamp"));
				gpcVO.setGpc_State(rs.getInt("gpc_State"));
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

		return gpcVO;
    }
	
	
	@Override
	public void insert(GpcVO gpcVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, gpcVO.getCoach_Id());
			pstmt.setString(2, gpcVO.getExp_Id());
			pstmt.setString(3, gpcVO.getGpc_Name());
			pstmt.setString(4, gpcVO.getAddress());
			pstmt.setString(5, gpcVO.getIntro());
			
			//圖片
			pstmt.setBytes(6, gpcVO.getPic1());
			pstmt.setBytes(7, gpcVO.getPic2());
			pstmt.setBytes(8, gpcVO.getPic3());	
			pstmt.setInt(9, gpcVO.getPrice());
			pstmt.setDate(10, gpcVO.getPay_Start());
			pstmt.setDate(11, gpcVO.getGpc_Start());
			pstmt.setInt(12, gpcVO.getMem_Min());
			pstmt.setInt(13, gpcVO.getMem_Max());
			
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
    public void update(GpcVO gpcVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE);
			
			pstmt.setString(1, gpcVO.getGpc_Name());
			pstmt.setString(2, gpcVO.getAddress());
			pstmt.setString(3, gpcVO.getIntro());
			pstmt.setBytes(4, gpcVO.getPic1());
			pstmt.setBytes(5, gpcVO.getPic2());
			pstmt.setBytes(6, gpcVO.getPic3());	
			pstmt.setInt(7, gpcVO.getPrice());
			pstmt.setDate(8, gpcVO.getPay_Start());
			pstmt.setInt(9, gpcVO.getMem_Min());
			pstmt.setInt(10, gpcVO.getMem_Max());
			//更新時間戳記
			pstmt.setTimestamp(11, gpcVO.getUpl_Stamp());
			pstmt.setString(12, gpcVO.getGpc_Id()); // PK
			
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
    public void changeState(GpcVO gpcVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(CHANGE_STATE_STMT);
			
			//更新時間戳記
	
			pstmt.setTimestamp(1, gpcVO.getUpl_Stamp());
			pstmt.setInt(2, gpcVO.getGpc_State());
			pstmt.setString(3, gpcVO.getGpc_Id()); // PK
			
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
	public GpcVO insertWithGpcSch(GpcVO gpcVO, TreeMap<String, String> resultMap) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			conn.setAutoCommit(false);		
			
			//先新增GPC
			String cols[] = {"gpc_Id"};
			pstmt = conn.prepareStatement(INSERT_STMT,cols);
			
			pstmt.setString(1, gpcVO.getCoach_Id());			
			pstmt.setString(2, gpcVO.getExp_Id());
			pstmt.setString(3, gpcVO.getGpc_Name());
			pstmt.setString(4, gpcVO.getAddress());
			pstmt.setString(5, gpcVO.getIntro());
			
			//圖片
			pstmt.setBytes(6, gpcVO.getPic1());
			pstmt.setBytes(7, gpcVO.getPic2());
			pstmt.setBytes(8, gpcVO.getPic3());	
			pstmt.setInt(9, gpcVO.getPrice());
			pstmt.setDate(10, gpcVO.getPay_Start());
			pstmt.setDate(11, gpcVO.getGpc_Start());
			pstmt.setInt(12, gpcVO.getMem_Min());
			pstmt.setInt(13, gpcVO.getMem_Max());
			
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String generatedGpcId = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				generatedGpcId = rs.getString(1);
				System.out.println("自增主鍵值(gpc_Id)= " + generatedGpcId );
			} else {
				System.out.println("未取得自增主鍵值");
			}
			
			gpcVO.setGpc_Id(generatedGpcId);//為了讓取得新的自增主鍵值
			
			rs.close();
			// 再同時新增gpc_Schedule
			String gpc_Id = generatedGpcId;
			Gpc_ScheduleJDBCDAO dao =new Gpc_ScheduleJDBCDAO();
			Set<Map.Entry<String, String>> resultEntrySet = resultMap.entrySet();
			for(Map.Entry singleMap :resultEntrySet) {
				dao.insertWithGpc_Id(gpc_Id, singleMap, conn);
			}
			
			conn.commit();
			conn.setAutoCommit(true);
			
			
			// Handle any driver errors
		} catch (ClassNotFoundException cnfe) {
			throw new RuntimeException("Couldn't load database driver. "
					+ cnfe.getMessage());
		} catch (SQLException se) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return gpcVO;
	}
	
	
	@Override
    public void delete(String gpc_Id) { // 僅供測試用
    }


    
}
