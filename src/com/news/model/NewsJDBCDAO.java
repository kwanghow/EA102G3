package com.news.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.news.model.NewsJDBCDAO;
import com.news.model.NewsVO;

public class NewsJDBCDAO implements NewsDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO News (news_id,news_spec,emp_id,news_content,news_header) VALUES (news_seq.NEXTVAL, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM news order by news_id";
	private static final String GET_ONE_STMT = "SELECT * FROM news where news_id = ?";
	private static final String DELETE = "DELETE FROM news where news_id = ?";
	private static final String UPDATE = "UPDATE news set news_spec=?, emp_id=?,news_content=?,news_header=? where news_id=?";
	private static final String GET_CONTENT = "select * from news where upper(NEWS_CONTENT) like upper (?) ";

	@Override
	public void insert(NewsVO newsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, newsVO.getNews_spec());
			pstmt.setString(2, newsVO.getEmp_id());
			pstmt.setString(3, newsVO.getNews_content());
			pstmt.setString(4, newsVO.getNews_header());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void update(NewsVO newsVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(5, newsVO.getNews_id());
			pstmt.setString(1, newsVO.getNews_spec());
			pstmt.setString(2, newsVO.getEmp_id());
			pstmt.setString(3, newsVO.getNews_content());
			pstmt.setString(4, newsVO.getNews_header());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void delete(String news_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, news_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

	public NewsVO findByPrimaryKey(String news_id) {
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, news_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setNews_spec(rs.getString("news_spec"));
				newsVO.setEmp_id(rs.getString("emp_id"));
				newsVO.setNews_content(rs.getString("news_content"));
				newsVO.setNews_header(rs.getString("news_header"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// streamVO 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setNews_spec(rs.getString("news_spec"));
				newsVO.setEmp_id(rs.getString("emp_id"));
				newsVO.setNews_content(rs.getString("news_content"));
				newsVO.setNews_header(rs.getString("news_header"));

				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public List<NewsVO> getContent(String news_content) {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_CONTENT);
			rs = pstmt.executeQuery();
			pstmt.setString(1, news_content);

			while (rs.next()) {
				// streamVO 也稱為 Domain objects
				newsVO = new NewsVO();
				newsVO.setNews_id(rs.getString("news_id"));
				newsVO.setNews_spec(rs.getString("news_spec"));
				newsVO.setEmp_id(rs.getString("emp_id"));
				newsVO.setNews_content(rs.getString("news_content"));
				newsVO.setNews_header(rs.getString("news_header"));

				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

		NewsJDBCDAO dao = new NewsJDBCDAO();

		NewsVO newsVO1 = new NewsVO();
//		newsVO1.setNews_id("1010");
		newsVO1.setNews_spec("健身文章");
		newsVO1.setEmp_id("123");
		newsVO1.setNews_content("00一條狗");
		newsVO1.setNews_header("00狗吃漢堡");
		dao.insert(newsVO1);

		System.out.println("====================新增成功==================");

		NewsVO newsVO2 = new NewsVO();
		newsVO1.setNews_id("1003");
		newsVO1.setNews_spec("系統消息");
		newsVO1.setEmp_id("7009");
		newsVO1.setNews_content("霖霖實況秀");
		newsVO1.setNews_header("台北收容所");
		dao.update(newsVO2);

		System.out.println("===================修改成功==================");

//		dao.delete("4");

		System.out.println("==================刪除成功=================");

		NewsVO newsVO3 = dao.findByPrimaryKey("1001");

		System.out.print(newsVO3.getNews_id() + ",");
		System.out.print(newsVO3.getNews_spec() + ",");
		System.out.print(newsVO3.getEmp_id() + ",");
		System.out.print(newsVO3.getNews_content() + ",");
		System.out.println(newsVO3.getNews_header());

		List<NewsVO> list = dao.getAll();
		for (NewsVO aNews : list) {
			System.out.print(aNews.getNews_id() + ",");
			System.out.print(aNews.getNews_spec() + ",");
			System.out.print(aNews.getEmp_id() + ",");
			System.out.print(aNews.getNews_content() + ",");
			System.out.print(aNews.getNews_header());
			System.out.println("===================查詢成功============");
		}

	}
}
