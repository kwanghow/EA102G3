package com.diet.model;

import java.util.*;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dietCon.model.dietConDAO;
import com.dietCon.model.dietConVO;

public class dietDAO implements dietDAO_interface {

	// Database共用
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO DIET (DIET_NO,MEMBER_ID,DIET_DATE) VALUES ('D1' || LPAD (diet_seq.NEXTVAL,4, '0'),?,?)";
//	private static final String GET_ALL_STMT = "SELECT DIET_NO,MEMBER_ID,to_char(DIET_DATE,'yyyy-MM-dd HH:mm:ss.f') DIET_DATE FROM DIET order by DIET_NO";
//	private static final String GET_ONE_STMT = "SELECT DIET_NO,MEMBER_ID ,to_date(DIET_DATE,'YYYY-MM-DD HH24:MI:SS')DIET_DATE FROM DIET where MEMBER_ID = ?";
	private static final String GET_ALL_STMT = "SELECT DIET_NO,MEMBER_ID,DIET_DATE FROM DIET order by DIET_NO";
	private static final String GET_ONE_STMT = "SELECT DIET_NO,MEMBER_ID,DIET_DATE FROM DIET where MEMBER_ID = ?";
	
	private static final String DELETE = "DELETE FROM DIET where DIET_NO = ?";
	private static final String DELETE_DIETCONTENT = "DELETE FROM DIET_CONTENT where DIET_NO = ?";
	private static final String GET_Diet_ByMemberID_STMT = "SELECT DIET_NO,MEMBER_ID,DIET_DATE FROM DIET where MEMBER_ID = ?";
	

	private static final String UPDATE = 
			"UPDATE DIET set DIET_DATE = ? WHERE DIET_NO = ?";
	@Override
	public void insert(dietVO dietVO) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dietVO.getMember_id());
			pstmt.setTimestamp(2, dietVO.getDiet_date());

			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error Occurred. " + se.getMessage());

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
	public void update(dietVO dietVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, dietVO.getDiet_date());
			pstmt.setString(2, dietVO.getDietno());

			pstmt.execute();
		} catch (SQLException se) {
			throw new RuntimeException("A database error Occurred. " + se.getMessage());

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
	public void delete(String dietno) {
		// TODO Auto-generated method stub

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			pstmt = con.prepareStatement(DELETE_DIETCONTENT);
			pstmt.setString(1, dietno);
			pstmt.executeUpdate();

			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, dietno);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public dietVO findByPrimaryKey(String memno) {
		// TODO Auto-generated method stub
		dietVO dietVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, memno);

			rs = pstmt.executeQuery();
			
			

			while (rs.next()) {

				dietVO = new dietVO();
				dietVO.setDietno(rs.getString("DIET_NO"));
				dietVO.setMember_id(rs.getString("MEMBER_ID"));
				
//				********************************************************
//				java.sql.Timestamp ts = rs.getTimestamp("DIET_DATE");
//				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
//				java.util.Date date = format1.parse(rs.getTimestamp("DIET_DATE")); 
				dietVO.setDiet_date(rs.getTimestamp("DIET_DATE"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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

		return dietVO;
	}

	@Override
	public List<dietVO> getAll() {
		// TODO Auto-generated method stub
		List<dietVO> list = new ArrayList<dietVO>();
		dietVO dietVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietVO = new dietVO();

				dietVO.setDietno(rs.getString("DIET_NO"));
				dietVO.setMember_id(rs.getString("MEMBER_ID"));
				dietVO.setDiet_date(rs.getTimestamp("DIET_DATE"));
				list.add(dietVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return list;
	}

	@Override
	public void insertWithDietCon(dietVO dietVO, List<dietConVO> list) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			con.setAutoCommit(false);

			String cols[] = { "diet_no" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setString(1, dietVO.getMember_id());
			pstmt.setTimestamp(2, dietVO.getDiet_date());

			pstmt.executeUpdate();
			String next_dietno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_dietno = rs.getString(1);
				System.out.println("自增主鍵值=" + next_dietno + "(剛新增成功的飲食清單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			dietConDAO dao = new dietConDAO();
			System.out.println("list.size(-A=" + list.size());
			for (dietConVO aDiet : list) {
				aDiet.setDiet_no(new String(next_dietno));
				dao.insert2(aDiet, con);
			}

			con.commit();
			con.setAutoCommit(true);

			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增飲食清單編號" + next_dietno + "時，共有明細" + list.size() + "同時被新增");

		} catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being");
					System.err.println("reload back 由 diet");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occurred." + excep.getMessage());

				}
			}
			throw new RuntimeException("A database error occurred." + se.getMessage());
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
	public Set<dietVO> getDietByMemno(String memno) {

		Set<dietVO> set = new LinkedHashSet<dietVO>();
		dietVO dietVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Diet_ByMemberID_STMT);

			pstmt.setString(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietVO = new dietVO();

				dietVO.setDietno(rs.getString("diet_no"));
				dietVO.setMember_id(rs.getString("member_id"));
				dietVO.setDiet_date(rs.getTimestamp("diet_date"));

				set.add(dietVO);

			}
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
		return set;

	}

}
