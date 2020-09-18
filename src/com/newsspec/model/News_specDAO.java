package com.newsspec.model;



	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.LinkedHashSet;
	import java.util.List;
	import java.util.Set;

	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import javax.sql.DataSource;

	import com.news.model.NewsVO;
	import com.newsspec.model.News_specVO;


	public class News_specDAO implements News_specDAO_interface{
		
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		private static final String INSERT_STMT = "INSERT INTO  news_Spec VALUES (?,news_spec_seq.NEXTVAL)";
		private static final String GET_ALL_STMT = "SELECT * FROM news_spec order by News_spec";
		private static final String GET_ONE_STMT = "SELECT * FROM news_spec where News_spec = ?";
		private static final String DELETE = "DELETE FROM news_spec where news_specno = ?";
		private static final String GET_NEWS_Bynews_specno_STMT = "SELECT * FROM news where news_spec = ? order by news_id";

		private static final String DELETE_NEWS = "DELETE FROM news where news_spec = ?";
		private static final String DELETE_NEWS_SPEC = "DELETE FROM news_spec where news_spec = ?";

		private static final String UPDATE = "UPDATE news_spec set news_spec=? where news_specno = ?";
		
		
		@Override
		public void insert(News_specVO news_specVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				pstmt.setString(1, news_specVO.getNews_spec());

				pstmt.executeUpdate();

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
		public void update(News_specVO news_specVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(2, news_specVO.getNews_specheader());
				pstmt.setString(1, news_specVO.getNews_spec());

				pstmt.executeUpdate();
	//"UPDATE news_spec set news_spec=? where news_specno = ?";
				// Handle any driver errors
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
		public void delete(String news_spec) {
			int updateCount_EMPs = 0;

			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();

				// 1●設定於 pstm.executeUpdate()之前
				con.setAutoCommit(false);

				// 先刪除員工
				pstmt = con.prepareStatement(DELETE_NEWS);
				pstmt.setString(1, news_spec);
				updateCount_EMPs = pstmt.executeUpdate();
				// 再刪除部門
				pstmt = con.prepareStatement(DELETE_NEWS_SPEC);
				pstmt.setString(1, news_spec);
				pstmt.executeUpdate();

				// 2●設定於 pstm.executeUpdate()之後
				con.commit();
				con.setAutoCommit(true);
				System.out.println("刪除部門編號" + news_spec + "時,共有員工" + updateCount_EMPs + "人同時被刪除");

				// Handle any driver errors
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
		public News_specVO findByPrimaryKey(String news_spec) {
			News_specVO news_specVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, news_spec);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// deptVO 也稱為 Domain objects
					news_specVO = new News_specVO();
					news_specVO.setNews_specheader(rs.getString("news_specheader"));
					news_specVO.setNews_spec(rs.getString("news_spec"));
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
			return news_specVO;
		}

		@Override
		public List<News_specVO> getAll() {
			List<News_specVO> list = new ArrayList<News_specVO>();
			News_specVO news_specVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					news_specVO = new News_specVO();
					news_specVO.setNews_spec(rs.getString("news_spec"));
					news_specVO.setNews_specheader(rs.getString("news_specheader"));
					list.add(news_specVO); // Store the row in the list
				}

				// Handle any driver errors
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
			return list;
		}

		@Override
		public Set<NewsVO> getNewsByNews_spec(String news_spec) {
			Set<NewsVO> set = new LinkedHashSet<NewsVO>();
			NewsVO newsVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_NEWS_Bynews_specno_STMT);
				pstmt.setString(1, news_spec);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					newsVO = new NewsVO();
					newsVO.setNews_id(rs.getString("news_id"));
					newsVO.setNews_spec(rs.getString("news_spec"));
					newsVO.setEmp_id(rs.getString("emp_id"));
					newsVO.setNews_content(rs.getString("news_content"));
					newsVO.setNews_header(rs.getString("news_header"));
					set.add(newsVO); // Store the row in the vector
				}

				// Handle any driver errors
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