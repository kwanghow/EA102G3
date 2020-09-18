package com.coach.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import com.exp.model.ExpJDBCDAO;
import com.exp.model.ExpVO;
import com.mem.model.MemVO;



public class CoachJDBCDAO implements CoachDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	
	private static final String INSERT_STMT =        
			"INSERT INTO COACH (coach_Id, member_Id, experience, isCoach, coach_Img) VALUES ('C' || LPAD (coach_seq.NEXTVAL, 2, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT coach_Id, member_Id, experience, isCoach, coach_Img FROM COACH order by COACH_ID";
	private static final String  GET_ONE_STMT =
			"SELECT coach_Id, member_Id, experience, isCoach, coach_Img FROM COACH where COACH_ID = ?";
	private static final String  GET_ONEBYMEM =
			"SELECT coach_Id, member_Id, experience, isCoach, coach_Img FROM COACH where MEMBER_ID = ?";
	private static final String DELETE =
			"DELETE FROM COACH where COACH_ID = ?";
	private static final String UPDATE = 
			"UPDATE COACH set member_Id=?, experience=?, isCoach=?, coach_Img=? where coach_Id=?";
	private static final String UPDATE_FROM_BACK =
			"UPDATE COACH set isCoach=? where coach_Id=?";
	private static final String UPDATE_CIMG = 
			"UPDATE COACH set coach_Img=? where coach_Id=?";
                                    
	@Override
	public void insert(CoachVO coachVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, coachVO.getMember_Id());
			pstmt.setString(2, coachVO.getExperience());
			pstmt.setInt(3,0);
//			byte[] pic = getPictureByteArray("pic/1.jpg");
			pstmt.setBytes(4, coachVO.getCoach_Img());
			
			pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("could't load database driver. "
					+ e.getMessage());
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(CoachVO coachVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
					
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, coachVO.getMember_Id());
			pstmt.setString(2, coachVO.getExperience());
			pstmt.setInt(3, coachVO.getIsCoach());
			pstmt.setBytes(4, coachVO.getCoach_Img());
			pstmt.setString(5, coachVO.getCoach_Id());
			
			pstmt.executeUpdate();
			//pstmt.clearParameters();
					
					
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
	public void delete(String coach_Id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, coach_Id);

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
	public CoachVO findByPrimaryKey(String coach_Id) {
		
		CoachVO coachVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, coach_Id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoach_Id(rs.getString("coach_Id"));
				coachVO.setMember_Id(rs.getString("member_Id"));
				coachVO.setExperience(rs.getString("experience"));
				coachVO.setIsCoach(rs.getInt("isCoach"));
				coachVO.setCoach_Img(rs.getBytes("coach_Img"));
			}
			
		}catch (ClassNotFoundException e) {
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
		return coachVO;
	}
	
	@Override
	public CoachVO findByMemId(String member_Id) {
		CoachVO coachVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONEBYMEM);
			
			pstmt.setString(1, member_Id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoach_Id(rs.getString("coach_Id"));
				coachVO.setMember_Id(rs.getString("member_Id"));
				coachVO.setExperience(rs.getString("experience"));
				coachVO.setIsCoach(rs.getInt("isCoach"));
				coachVO.setCoach_Img(rs.getBytes("coach_Img"));
			}
			
		}catch (ClassNotFoundException e) {
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
		return coachVO;
	}

	@Override
	public List<CoachVO> getAll() {
		List<CoachVO> list = new ArrayList<CoachVO>();
		CoachVO coachVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				coachVO = new CoachVO();
				coachVO.setCoach_Id(rs.getString("coach_Id"));
				coachVO.setMember_Id(rs.getString("member_Id"));
				coachVO.setExperience(rs.getString("experience"));
				coachVO.setIsCoach(rs.getInt("isCoach"));
				coachVO.setCoach_Img(rs.getBytes("coach_Img"));
				list.add(coachVO);
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
	public void insertWithExps(CoachVO coachVO, String[] exp_Id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			
			// 先新增教練
			String cols[] = {"COACH_ID"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setString(1, coachVO.getMember_Id());
			pstmt.setString(2, coachVO.getExperience());
			pstmt.setInt(3,0);
			pstmt.setBytes(4, coachVO.getCoach_Img());
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_coach_Id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_coach_Id = rs.getString(1);
				System.out.println("自增主鑑值= " + next_coach_Id + "(剛新增成功的教練編號)");
			}else {
				System.out.println("未取得自增主鑑值");
			}
			rs.close();
			//再同時新增專長
			ExpJDBCDAO dao = new ExpJDBCDAO();
			System.out.println("list.size()-A="+exp_Id.length);
			for (int i=0; i<exp_Id.length; i++) {
				ExpVO expVO = new ExpVO();
				expVO.setExp_Id(exp_Id[i]);
				expVO.setCoach_Id(next_coach_Id);
				dao.insert2(expVO, con);
			}					
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+exp_Id.length);
			System.out.println("新增教練編號" + next_coach_Id + "時, 共有" + exp_Id.length
					+ "專長同時被新增");
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.out.println("Transaction is being ");
					System.out.println("rolled back to COACH");
					con.rollback();
				}catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		} finally {
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
	public void updateFromBack(String coach_Id, Integer isCoach) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_FROM_BACK);

			pstmt.setInt(1, isCoach);
			pstmt.setString(2, coach_Id);

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
	public void updateImg(CoachVO coachVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_CIMG);

			pstmt.setBytes(1, coachVO.getCoach_Img());
			pstmt.setString(2, coachVO.getCoach_Id());

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
		CoachJDBCDAO dao = new CoachJDBCDAO();
		
		//新增教練資料
		CoachVO coachVO1 = new CoachVO();
//		coachVO1.setMember_Id("M022");
//		coachVO1.setExperience("AFAA 美國有氧體適能協會個人體適能顧問, " + 
//				"Stroops VITL 3D 功能彈力訓練指導員, " + 
//				"中華民國舉重協會B級教練證, " + 
//				"中華民國紅十字會急救員證(CPR+AED)");
//		coachVO1.setIsCoach(0);
//		byte[] pic = getPictureByteArray("/front-end/assets/images/s2.jpg");
//		coachVO1.setCoach_Img(pic);
//		dao.insert(coachVO1);
		
		//修改教練資料
		CoachVO coachVO2 = new CoachVO();
		coachVO2.setCoach_Id("C03");
		coachVO2.setExperience("AASFP亞洲運動及體適能專業學院認證, " + 
				"TRX懸吊系統訓練指導員, " + 
				"Rumble Roller深層放鬆認證, " + 
				"中華健身運動協會體適能C級指導員認證, " + 
				"ViPR PRO功能訓練系統認證, " + 
				"紅十字會急救員CPR、AED認證" +
				"唬爛證照");
		byte[] pic = getPictureByteArray("WebContent/front-end/assets/images/s4.jpg");
		coachVO2.setCoach_Img(pic);
		dao.update(coachVO2);
		
		//刪除教練資料
//		dao.delete("C05");
		
		//查詢單筆
//		CoachVO coachVO3 = dao.findByPrimaryKey("C03");
//		System.out.println(coachVO3.getCoach_Id() + ",");
//		System.out.println(coachVO3.getMember_Id() + ",");
//		System.out.println(coachVO3.getExperience() + ",");
//		System.out.println(coachVO3.getIsCoach() + ",");
//		System.out.println(coachVO3.getCoach_Img() + ",");
//		System.out.println("--------------------");
		
		//查詢全部
//		List<CoachVO> list = dao.getAll();
//		for (CoachVO aCoach : list) {
//			System.out.println(aCoach.getCoach_Id() + ",");
//			System.out.println(aCoach.getMember_Id() + ",");
//			System.out.println(aCoach.getExperience() + ",");
//			System.out.println(aCoach.getIsCoach() + ",");
//			System.out.println(aCoach.getCoach_Img() + ",");
//			System.out.println("--------------------");
//		}
	}
	
		
		
	
	// 使用byte[]方式
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
