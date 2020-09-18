package com.dietCon.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbcUtil_CompositeQuery.jdbcUtil_CompositeQuery_member;

public class dietConDAO implements dietConDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO DIET_CONTENT (dietcon_no,diet_no,dieting_no,diet_content) VALUES('DC10' || LPAD(DIET_CONTENT_SEQ.NEXTVAL,4,'0'),?,?,?)";
	private static final String GET_ALL_DIETCON = "SELECT diet_no,dieting_no,diet_content FROM DIET_CONTENT";
	private static final String GET_ONE = "SELECT dietcon_no,diet_no,dietIng_no,diet_content FROM diet_content where diet_no = ?";
	private static final String DELETE = "DELETE FROM diet_content where dietcon_no = ?";
	private static final String UPDATE = "UPDATE diet_content set dietIng_no=?,diet_content=? where dietcon_no=?";
	@Override
	public void insert(dietConVO dietConVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, dietConVO.getDiet_no());
			pstmt.setString(2, dietConVO.getDietIng_no());
			pstmt.setString(3, dietConVO.getDiet_content());

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
	public void update(dietConVO dietConVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,dietConVO.getDietIng_no());
			pstmt.setString(2,dietConVO.getDiet_content());
			pstmt.setString(3,dietConVO.getDietcon_no());
			
			pstmt.executeUpdate();
			
			
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
	public void delete(String dietCon_no) {

		Connection con = null;
		PreparedStatement pstmt = null;
//		
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1,dietCon_no);

			pstmt.executeUpdate();

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
	public List<dietConVO> getAll() {

		List<dietConVO> List = new ArrayList<dietConVO>();
		dietConVO dietConVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_DIETCON);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				dietConVO = new dietConVO();

				dietConVO.setDiet_no(rs.getString("diet_no"));
				dietConVO.setDietIng_no(rs.getString("dieting_no"));
				dietConVO.setDiet_content(rs.getString("diet_content"));

				List.add(dietConVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occuured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				if (rs != null) {
					try {
						rs.close();
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

		return List;
	}

	@Override
	public List<dietConVO> getAll(Map<String, String[]> map) {

		List<dietConVO> list = new ArrayList<dietConVO>();
		dietConVO dietConVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			String finalSQL = "select * from diet_content" + jdbcUtil_CompositeQuery_member.get_WhereCondition(map)
					+ "order by diet_no";
			pstmt = con.prepareStatement(finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dietConVO = new dietConVO();

				dietConVO.setDiet_no(rs.getString("diet_no"));
				dietConVO.setDietIng_no(rs.getString("dietIng_no"));
				dietConVO.setDiet_content(rs.getString("diet_content"));

				list.add(dietConVO);
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occurred." + se.getMessage());
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
	public Set<dietConVO> findByPrimaryKey(String diet_no) {

		Set<dietConVO> set = new LinkedHashSet<dietConVO>();
		dietConVO dietConVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, diet_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				dietConVO = new dietConVO();

				dietConVO.setDietcon_no(rs.getString("dietcon_no"));
				dietConVO.setDiet_no(rs.getString("diet_no"));
				dietConVO.setDietIng_no(rs.getString("dietIng_no"));
				dietConVO.setDiet_content(rs.getString("diet_content"));
				System.out.println(dietConVO);
				set.add(dietConVO);

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
		return set;
	}

	@Override
	public void insert2(dietConVO dietConVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, dietConVO.getDiet_no());
			pstmt.setString(2,dietConVO.getDietIng_no());
			pstmt.setString(3,dietConVO.getDiet_content());
			
			pstmt.executeUpdate();
			
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
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

}
