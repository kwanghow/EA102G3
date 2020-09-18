package com.profav.model;

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
import javax.sql.DataSource;



public class Product_FavoriteJDBCDAO implements Product_Favorite_interface {
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO LIKE_PRODUCT (MEMBER_ID,PRODUCT_ID) VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT MEMBER_ID ,PRODUCT_ID FROM LIKE_PRODUCT order by member_id";
	private static final String GET_ONE_STMT = "SELECT MEMBER_ID,PRODUCT_ID FROM LIKE_PRODUCT where member_id = ? order by PRODUCT_ID";
	private static final String DELETE = "DELETE LIKE_PRODUCT WHERE MEMBER_ID= ? and PRODUCT_ID= ? ";

	@Override
	public void insert(Product_FavoriteVO Product_FavoriteVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();


			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, Product_FavoriteVO.getMember_id());
			pstmt.setString(2, Product_FavoriteVO.getProduct_id());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String member_id, String product_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();


			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, member_id);
			pstmt.setString(2, product_id);

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public List<Product_FavoriteVO> findByPrimaryKey(String member_id) {
		List<Product_FavoriteVO> list = new ArrayList<Product_FavoriteVO>();
		Product_FavoriteVO FAVO1 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				FAVO1 = new Product_FavoriteVO();
				FAVO1.setMember_id(rs.getString("member_id"));
				FAVO1.setProduct_id(rs.getString("product_id"));

				list.add(FAVO1);

				// Store the row in the list
			}

			// Handle any driver errors
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
	
	public Product_FavoriteVO getOneFavor(String member_id, String product_id) {
		Product_FavoriteVO FAVO1 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement("SELECT MEMBER_ID,PRODUCT_ID FROM LIKE_PRODUCT where member_id = ? and product_id = ?");
			pstmt.setString(1, member_id);
			pstmt.setString(2, product_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				FAVO1 = new Product_FavoriteVO();
				FAVO1.setMember_id(rs.getString("member_id"));
				FAVO1.setProduct_id(rs.getString("product_id"));
			}

			// Handle any driver errors
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
		return FAVO1;

	}

	@Override
	public List<Product_FavoriteVO> getAll() {
		List<Product_FavoriteVO> list = new ArrayList<Product_FavoriteVO>();
		Product_FavoriteVO FAVO1 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				FAVO1 = new Product_FavoriteVO();
				FAVO1.setMember_id(rs.getString("member_id"));
				FAVO1.setProduct_id(rs.getString("product_id"));

				list.add(FAVO1);

				// Store the row in the list
			}

			// Handle any driver errors
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

	public static void main(String[] args) {
//		Product_FavoriteJDBCDAO dao = new Product_FavoriteJDBCDAO();

		// 新增
//		Product_FavoriteVO FAVO2 = new Product_FavoriteVO();
////
//		FAVO2.setMember_id("M004");
//		FAVO2.setProduct_id("P005");
////
//		dao.insert(FAVO2);

		// 刪除

//    	dao.delete("M010", "P001");

		//
//		List<Product_FavoriteVO> list = dao.findByPrimaryKey("M004");
//		for (Product_FavoriteVO aOD : list) {
//			System.out.print(aOD.getMember_id() + ",");
//			System.out.print(aOD.getProduct_id() + ",");
////
//		System.out.println("-----------ok----------");

		// 查詢
//		List<Product_FavoriteVO> list = dao.getAll();
//		for (Product_FavoriteVO aOD : list) {
//			System.out.print(aOD.getMember_id() + ",");
//			System.out.print(aOD.getProduct_id() + ",");
//
//			System.out.println();
//		}
	}

}
