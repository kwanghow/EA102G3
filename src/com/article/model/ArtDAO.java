package com.article.model;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

	public class ArtDAO implements ArtVO_interface {
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
			"INSERT INTO tb_article (article_no,mem_id,article_cat_no,article_title,article_title_sub,article_author,article_content,post_time,article_report) "
			+ "VALUES (LPAD(arte_seq.NEXTVAL,10,'0' ), ?, ?, ?, ?, ?, ?, to_date(?, 'yyyy-mm-dd hh:mi:ss'),?)";
		private static final String GET_ALL_STMT = 
			"SELECT article_no,mem_id,article_cat_no,article_title,article_title_sub,"
			+ "article_author,article_content,to_char(post_time,'yyyy-mm-dd hh:mm:ss') post_time,"
			+ "article_report FROM tb_article where article_report in (0,1) order by post_time";
		
		private static final String GET_ALL_REPORTED_STMT = 
				"SELECT article_no,mem_id,article_cat_no,article_title,article_title_sub,article_author,article_content,to_char(post_time,'yyyy-mm-dd hh:mm:ss') post_time,article_report FROM tb_article "
				+ "where article_report!=0  order by article_no";
		
		private static final String GET_ALL_LOVE_STMT = 
				"SELECT article_no,mem_id,article_cat_no,article_title"
				+ ",article_title_sub,article_author,article_content,to_char(post_time,'yyyy-mm-dd hh:mm:ss') post_time"
				+ ",article_report FROM tb_article art join tb_ilove ilv on art.mem_id = ilv.mem_no"
				+ "where art.mem_id order by article_no";

		private static final String GET_ONE_STMT = 
				"SELECT article_no,mem_id,article_cat_no,article_title,article_title_sub,article_author,article_content, post_time,article_report "
				+ "FROM tb_article where article_no = ?";
		private static final String DELETE =      // �����炎������
			"UPDATE tb_article set article_report=? where article_no=? ";
		private static final String UPDATE = 
			"UPDATE tb_article set   article_cat_no=?, article_title=?, article_title_sub=?, article_content=?, article_report=? where article_no = ?";
		private static final String UPDATE_REPORT = 
				"UPDATE tb_article set article_report=? where article_no = ?";

		
		//to_date(?, 'yyyy-mm-dd')
		@Override
		public void insert(ArtVO artVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, artVO.getMemId());
				pstmt.setString(2, artVO.getArticleCatNo());
				pstmt.setString(3, artVO.getArticleTitle());
				pstmt.setString(4, artVO.getArticleTitleSub());
				pstmt.setString(5, artVO.getArticleAuthor());
				pstmt.setString(6, artVO.getArticleContent());
				pstmt.setString(7, getString(artVO.getPostTime()));
			    pstmt.setInt(8,artVO.getArticleReport());

				pstmt.executeUpdate();

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
		public void update(ArtVO artVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);
				System.out.println("dao");

				System.out.println("artVO.getArticleReport=" + artVO.getArticleReport());
				System.out.println("artVO.getArticleNo1()=" + artVO.getArticleNo());
				
				pstmt.setString(1, artVO.getArticleCatNo());
				pstmt.setString(2, artVO.getArticleTitle());
				pstmt.setString(3, artVO.getArticleTitleSub());
				pstmt.setString(4, artVO.getArticleContent());
//				pstmt.setDate(5, new java.sql.Date(artVO.getPostTime().getTime()));
				pstmt.setInt(5, artVO.getArticleReport());
				pstmt.setString(6, artVO.getArticleNo());
				System.out.println("artVO.getArticleNo()=" + artVO.getArticleNo());
				

				pstmt.executeUpdate();
				System.out.println("DAO UPDATE��蝯��");
				// Handle any driver errors
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

		public void delete(ArtVO ArtVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1,ArtVO.getArticleReport());
				pstmt.setString(2,ArtVO.getArticleNo());
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

		public ArtVO findByPrimaryKey(String article_no) {

			ArtVO artVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				
				System.out.println(" getone -------------");
				System.out.println(article_no);
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);
				System.out.println(" getone -------------");

				pstmt.setString(1, article_no);

				rs = pstmt.executeQuery();
				System.out.println("DAO");

				while (rs.next()) {
					// empVo Domain objects
					artVO = new ArtVO();
					artVO.setArticleNo(rs.getString("article_no"));
					artVO.setMemId(rs.getString("mem_id"));
					artVO.setArticleCatNo(rs.getString("article_cat_no"));
					artVO.setArticleTitle(rs.getString("article_Title"));
					artVO.setArticleTitleSub(rs.getString("article_Title_Sub"));
					artVO.setArticleAuthor(rs.getString("article_Author"));
					artVO.setArticleContent(rs.getString("article_Content"));
					artVO.setPostTime(rs.getTimestamp("post_Time"));
					artVO.setArticleReport(rs.getInt("article_Report"));
				};

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
			return artVO;
		}

		@Override
		public List<ArtVO> getAll() {
			List<ArtVO> list = new ArrayList<ArtVO>();
			ArtVO artVO1 = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("AA");
		
			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				System.out.println("into Getall ============================================================+");
				while (rs.next()) {
					System.out.println(rs.getString("article_no"));
					// empVO ??嚙踝蕭?嚙踝蕭?嚙踝蕭嚙�?? Domain objects
					ArtVO artVO = new ArtVO();
					artVO.setArticleNo(rs.getString("article_no"));
					artVO.setMemId(rs.getString("mem_id"));
					artVO.setArticleCatNo(rs.getString("article_cat_no"));
					artVO.setArticleTitle(rs.getString("article_title"));
					artVO.setArticleTitleSub(rs.getString("article_title_sub"));
					artVO.setArticleAuthor(rs.getString("article_author"));
					artVO.setArticleContent(rs.getString("article_content"));	
					SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					artVO.setPostTime(df.parse(rs.getString("post_time")));
					artVO.setArticleReport(rs.getInt("article_report"));
					
					list.add(artVO); // Store the row in the list
					System.out.println(rs.getString("article_no"));
				}

				System.out.println(list.size());
				System.out.println("BB");

				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. 51561561561"
						+ se.getMessage());
				// Clean up JDBC resources
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		public List<ArtVO> getAllLoved() {
			List<ArtVO> list = new ArrayList<ArtVO>();
			ArtVO artVO1 = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("AAAAAA");
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_LOVE_STMT);
				rs = pstmt.executeQuery();
				
				System.out.println("into Getall ============================================================+");
				while (rs.next()) {
					System.out.println(rs.getString("article_no"));
					// empVO ??���?���?�����蕭?? Domain objects
					ArtVO artVO = new ArtVO();
					artVO.setArticleNo(rs.getString("article_no"));
					artVO.setMemId(rs.getString("mem_id"));
					artVO.setArticleCatNo(rs.getString("article_cat_no"));
					artVO.setArticleTitle(rs.getString("article_title"));
					artVO.setArticleTitleSub(rs.getString("article_title_sub"));
					artVO.setArticleAuthor(rs.getString("article_author"));
					artVO.setArticleContent(rs.getString("article_content"));	
					SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					artVO.setPostTime(df.parse(rs.getString("post_time")));
					artVO.setArticleReport(rs.getInt("article_report"));
					
					list.add(artVO); // Store the row in the list
					System.out.println(rs.getString("article_no"));
				}

				System.out.println(list.size());
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		public List<ArtVO> getAllReported() {
			List<ArtVO> list = new ArrayList<ArtVO>();
			ArtVO artVO1 = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			System.out.println("AAAAAA");
			

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_REPORTED_STMT);
				rs = pstmt.executeQuery();
				
				System.out.println("into Getall ============================================================+");
				while (rs.next()) {
					System.out.println(rs.getString("article_no"));
					// empVO ??嚙踝蕭?嚙踝蕭?嚙踝蕭嚙�?? Domain objects
					ArtVO artVO = new ArtVO();
					artVO.setArticleNo(rs.getString("article_no"));
					artVO.setMemId(rs.getString("mem_id"));
					artVO.setArticleCatNo(rs.getString("article_cat_no"));
					artVO.setArticleTitle(rs.getString("article_title"));
					artVO.setArticleTitleSub(rs.getString("article_title_sub"));
					artVO.setArticleAuthor(rs.getString("article_author"));
					artVO.setArticleContent(rs.getString("article_content"));	
					SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        artVO.setPostTime(df.parse(rs.getString("post_time")));

					artVO.setArticleReport(rs.getInt("article_report"));
					
					list.add(artVO); // Store the row in the list
					System.out.println(rs.getString("article_no"));
				}

				System.out.println(list.size());
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

				// Handle any driver errors
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. "
						+ se.getMessage());
				// Clean up JDBC resources
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		

		public static String getString(Date d) {
			   return new SimpleDateFormat("yyyy-MM-dd").format(d);
			}
		
		public void update_report(ArtVO artVO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_REPORT);
				System.out.println("dao_UP_REPORT");

				System.out.println("artVO.getArticleReport=" + artVO.getArticleReport());
				System.out.println("artVO.getArticleNo1()=" + artVO.getArticleNo());
				
				pstmt.setInt(1, artVO.getArticleReport());
				pstmt.setString(2, artVO.getArticleNo());
				System.out.println("artVO.getArticleNo()=" + artVO.getArticleNo());
				

				pstmt.executeUpdate();
				System.out.println("DAO UPDATE��蝯��");
				// Handle any driver errors
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
	
	}
	
	
	
	

