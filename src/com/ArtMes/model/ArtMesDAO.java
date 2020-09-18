package com.ArtMes.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.article.model.ArtVO_interface;

public class ArtMesDAO implements ArtMes_interface{
	
		// 銝���蝔�葉嚗������澈嚗�銝��atasource��
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
			"INSERT INTO TB_MESSAGE (Msg_no,Article_no,Mem_no,Msg_cont) "
			+ "VALUES ( LPAD(Mes_seq1.NEXTVAL,12,'0' ), ?,  ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT Msg_no,Article_no,Mem_no,Msg_time,Msg_cont FROM tb_message order by msg_no";
		private static final String GET_ALL_BY_ARTICLENO_STMT = 
				"SELECT Msg_no,Article_no,Mem_no,Msg_time,Msg_cont, mem.mem_name member_name FROM "
				+ " tb_message mes join member mem on mes.mem_no = mem.member_id  where Article_no=? order by msg_no";
		private static final String GET_ONE_STMT = 
				"SELECT Msg_no,Article_no,Mem_no,Msg_time,Msg_cont"
				+ "FROM tb_message where Article_no = ?";
		private static final String DELETE = 
			"DELETE FROM tb_message where msg_no = ?";
		private static final String UPDATE = 
			"UPDATE tb_message set Msg_no=?,Article_no=?,Mem_no=?,Msg_time=toDate(?,'yyyy-MM-dd HH;mm:ss'),Msg_cont=? FROM = ?";

		@Override
		public void insert(ArtMesVO ArtMesVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
System.out.println("into insert");
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, ArtMesVO.getArticleNo());
				pstmt.setString(2, ArtMesVO.getMemNo());
				//pstmt.setTimestamp(3, new Timestamp(ArtMesVO.getMsgTime().getTime()));
				pstmt.setString(3, ArtMesVO.getMsgCont());
		
				int n =pstmt.executeUpdate();
				System.out.println("into insert"+n);

				// Handle any SQL errors
			} catch (SQLException se) {
				se.printStackTrace();
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
		public void update(ArtMesVO ArtMesVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, ArtMesVO.getMsgNo());
				pstmt.setString(2, ArtMesVO.getArticleNo());
				pstmt.setString(3, ArtMesVO.getMemNo());
				pstmt.setTimestamp(4, new Timestamp(ArtMesVO.getMsgTime().getTime()));
				pstmt.setString(5, ArtMesVO.getMsgCont());
				

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

		public void delete(String article_no) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setString(1, article_no);

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

		public ArtMesVO findByPrimaryKey(String article_no) {

			ArtMesVO ArtMesVO = null;
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
				System.out.println(rs);

				while (rs.next()) {
					// empVo ����蕭蹌� Domain objects
					ArtMesVO = new ArtMesVO();
					pstmt.setString(1, ArtMesVO.getMsgNo());
					pstmt.setString(2, ArtMesVO.getArticleNo());
					pstmt.setString(3, ArtMesVO.getMemNo());
					pstmt.setTimestamp(4, new Timestamp(ArtMesVO.getMsgTime().getTime()));
					pstmt.setString(5, ArtMesVO.getMsgCont());
					
				}
				
				System.out.println(ArtMesVO);

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
			return ArtMesVO;
		}

		@Override
		public List<ArtMesVO> getAll() {
			List<ArtMesVO> list = new ArrayList<ArtMesVO>();
			ArtMesVO ArtMesVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("撠������");
			

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				System.out.println("into Getall ============================================================+");
				while (rs.next()) {
					System.out.println(rs.getString("article_no"));
				
					ArtMesVO = new ArtMesVO();
					ArtMesVO.setMsgNo(rs.getString("msg_no"));
					ArtMesVO.setArticleNo(rs.getString("article_no"));
					ArtMesVO.setMemNo(rs.getString("mem_no"));
					ArtMesVO.setMsgTime(rs.getTimestamp("msg_time"));
					
					ArtMesVO.setMsgCont(rs.getString("msg_cont"));
					
					
					
					list.add(ArtMesVO); // Store the row in the list
					System.out.println(rs.getRow());
				}

				System.out.println(list.size());
				System.out.println("撠������");

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

		
		@Override
		public List<ArtMesVO> getAll(String articleNo) {
			List<ArtMesVO> list = new ArrayList<ArtMesVO>();
			ArtMesVO ArtMesVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("you are a bad bad boy");

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_BY_ARTICLENO_STMT);
				pstmt.setString(1, articleNo);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					System.out.println(rs.getString("msg_no"));
					// empVO ����蕭蹌� Domain objects
					ArtMesVO = new ArtMesVO();
					ArtMesVO.setMsgNo(rs.getString("msg_no"));
					ArtMesVO.setArticleNo(rs.getString("article_no"));
					ArtMesVO.setMemNo(rs.getString("mem_no"));
					ArtMesVO.setMsgTime(rs.getTimestamp("msg_time"));
					ArtMesVO.setMsgCont(rs.getString("msg_cont"));
					ArtMesVO.setMemberName(rs.getString("member_name"));

					list.add(ArtMesVO); // Store the row in the list
					System.out.println(rs.getRow());
				}

				System.out.println(list.size());
				System.out.println("撠�");

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

		public void delete(ArtMesVO ArtMesVO) {
			// TODO Auto-generated method stub
			
		}

		public ArtMesVO findByPrimaryKey(Integer article_no) {
			// TODO Auto-generated method stub
			return null;
		}


	
	}
	
	
	
	




