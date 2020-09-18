package com.articlecat.model;

import java.util.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ArtCatDAO implements ArtCatVO_interface{

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
		"INSERT INTO tb_article_cat(article_cat_no,article_cat_name) "
		+ "VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT article_cat_no,article_cat_name FROM tb_article_cat";
	private static final String GET_ONE_STMT = 
			"SELECT article_cat_no,article_cat_name "
			+ "FROM tb_article_cat where article_cat_no = ?";
	private static final String DELETE = 
		"DELETE FROM tb_article_cat where article_cat_no = ?";
	private static final String UPDATE = 
		"UPDATE tb_article_cat set article_cat_no=? article_cat_name=? where tb_article_cat = ?";

	@Override
	public void insert(ArtCatVO artCatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, artCatVO.getArticleCatNo());
			pstmt.setString(2, artCatVO.getArticleCatName());	
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
	public void update(ArtCatVO artCatVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, artCatVO.getArticleCatNo());
			pstmt.setString(2, artCatVO.getArticleCatName());
			pstmt.executeUpdate();

			// Handle any driver errors
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

	public void delete(String article_cat_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, article_cat_no);

			pstmt.executeUpdate();

			// Handle any driver errors
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

	public ArtCatVO findByPrimaryKey(String article_cat_no) {

		ArtCatVO artCatVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			System.out.println(" getone -------------");
			System.out.println(article_cat_no);


			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, article_cat_no);

			rs = pstmt.executeQuery();
			System.out.println(rs);

			while (rs.next()) {
				artCatVO = new ArtCatVO();
				pstmt.setString(1, artCatVO.getArticleCatNo());
				pstmt.setString(2, artCatVO.getArticleCatName());
			}
			
			System.out.println(artCatVO);

			// Handle any driver errors
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
		return artCatVO;
	}

	@Override
	public List<ArtCatVO> getAll() {
		List<ArtCatVO> list = new ArrayList<ArtCatVO>();
		ArtCatVO artCatVO1 = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			System.out.println("into Getall ============================================================+");
			while (rs.next()) {
				System.out.println(rs.getString("article_cat_no"));
				ArtCatVO artCatVO = new ArtCatVO();
				artCatVO.setArticleCatNo(rs.getString("article_cat_no"));
				artCatVO.setArticleCatName(rs.getString("article_cat_name"));
				
				
				list.add(artCatVO); // Store the row in the list
			}

			System.out.println(list.size());

			// Handle any driver errors
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
		return list;
	}



}






	

