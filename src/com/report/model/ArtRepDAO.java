package com.report.model;
import java.util.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.article.model.ArtVO;
import com.report.model.ArtRepVO;
import com.report.model.ArtRepVO_interface;

public class ArtRepDAO implements ArtRepVO_interface{

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
		"INSERT INTO tb_Report(Artrep_no,article_no,mem_no,Article_reprea,MsgRep_result) "
		+ "VALUES (report_seq.NEXTVAL, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT Artrep_no,article_no,mem_no,Article_reprea,MsgRep_result FROM tb_Report by Artrep_no";
	private static final String GET_ONE_STMT = 
			"SELECT Artrep_no,article_no,mem_no,Article_reprea,MsgRep_result FROM tb_Report where article_no = ?";
	private static final String DELETE = 
		"DELETE FROM tb_Report where Artrep_no = ?";
	private static final String UPDATE = 
		"UPDATE tb_Report set MsgRep_result=? where article_no = ?";
	private static final String UPDATE_ART = 
			"UPDATE  tb_Report set MsgRep_result=? where article_no = ?";

	@Override
	public void insert(ArtRepVO ArtRepVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, ArtRepVO.getArticleNo());
			pstmt.setString(2, ArtRepVO.getMemNo());
			pstmt.setString(3, ArtRepVO.getArticleReprea());
			pstmt.setInt(4, ArtRepVO.getMsgRepResult());
			

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
	public void update(ArtRepVO ArtRepVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ArtRepVO.getMsgRepResult());
			pstmt.setString(2, ArtRepVO.getArticleNo());
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

	public void delete(String Artrep_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, Artrep_no);

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
	@Override
	public ArtRepVO findByPrimaryKey(String article_no) {

		ArtRepVO ArtRepVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			
			System.out.println(" getone -------------");
			System.out.println(article_no);


			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, article_no);

			rs = pstmt.executeQuery();
			System.out.println("DAO");
			
			
			

			while (rs.next()) {
//				System.out.println("article_no2=" + article_no);
				ArtRepVO = new ArtRepVO();
				ArtRepVO.setArticleNo(rs.getString("article_no"));
				ArtRepVO.setArtrepNo(rs.getString("artrep_no"));
				ArtRepVO.setMemNo(rs.getString("mem_no"));
				ArtRepVO.setArticleReprea(rs.getString("article_reprea"));
				ArtRepVO.setMsgRepResult(rs.getInt("MsgRep_Result"));
				
				
				System.out.println("article_reprea=" + rs.getString("article_reprea"));
				System.out.println("article_no=" + rs.getString("article_no"));
				
			
			}
			

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new NullPointerException("123");
			// Clean up JDBC resources
		}  finally {
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
		return ArtRepVO;
	}

	@Override
	public List<ArtRepVO> getAll() {
		List<ArtRepVO> list = new ArrayList<ArtRepVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("getall");
		

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			System.out.println("into Getall ============================================================+");
			while (rs.next()) {
				System.out.println(rs.getString("Artrep_no"));
				// empVO ??嚙踝蕭?嚙踝蕭?嚙踝蕭嚙�? Domain objects
				ArtRepVO ArtRepVO = new ArtRepVO();
				ArtRepVO.setArtrepNo(rs.getString("Artrep_no"));
				ArtRepVO.setArticleNo(rs.getString("Article_no"));
				ArtRepVO.setMemNo(rs.getString("mem_no"));
				ArtRepVO.setArticleReprea(rs.getString("Article_reprea"));
				ArtRepVO.setMsgRepResult(rs.getInt("MsgRep_result"));
				list.add(ArtRepVO); // Store the row in the list
				System.out.println(rs.getRow());
			}

			System.out.println(list.size());
			System.out.println("撠蕭?嚙踝蕭?嚙踝蕭?嚙踝蕭?嚙踝蕭??");

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







