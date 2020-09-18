package com.features.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeaturesJDBCDAO implements FeaturesDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	public static final String INSERT_STMT = "INSERT INTO FEATURES (features_id,features_name) VALUES  ('F' || LPAD (features_seq.NEXTVAL, 2, '0'), ?)";
	public static final String GET_ALL_STMT = "SELECT * FROM FEATURES order by features_id";
	public static final String GET_ONE_STMT = "SELECT * FROM FEATURES where features_id=?";
	public static final String DELETE = "DELETE FROM FEATURES where features_id=?";
	public static final String UPDATE = "UPDATE features set features_name=? where features_id=?";

	@Override
	public void insert(FeaturesVO featuresVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, featuresVO.getFeatures_name());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void delete(String features_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, features_id);
			pstmt.executeUpdate();

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
	public void update(FeaturesVO featuresVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, featuresVO.getFeatures_name());
			pstmt.setString(2, featuresVO.getFeatures_id());
			pstmt.executeUpdate();

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
	public FeaturesVO findByParimaryKey(String features_id) {
		FeaturesVO featuresVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, features_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				featuresVO = new FeaturesVO();
				featuresVO.setFeatures_id(rs.getString("features_id"));
				featuresVO.setFeatures_name(rs.getString("features_name"));

			}
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
		return featuresVO;
	}

	@Override
	public List<FeaturesVO> getAll() {
		List<FeaturesVO> list = new ArrayList<FeaturesVO>();
		FeaturesVO featuresVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				featuresVO = new FeaturesVO();
				featuresVO.setFeatures_id(rs.getString("features_id"));
				featuresVO.setFeatures_name(rs.getString("features_name"));
				list.add(featuresVO);
			}

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
		FeaturesJDBCDAO dao = new FeaturesJDBCDAO();
		
		FeaturesVO featuresVO1 = new FeaturesVO();
		featuresVO1.setFeatures_name("會員管理");
		dao.insert(featuresVO1);
		
		System.out.println("====================新增成功==================");
		
		
		FeaturesVO featuresVO2 =new FeaturesVO();
		featuresVO2.setFeatures_id("F11");
		featuresVO2.setFeatures_name("商品管理2");
		
		dao.update(featuresVO2);
		
		System.out.println("===================修改成功==================");
		
		dao.delete("F15");
		
		System.out.println("==================刪除成功=================");
		
		
		FeaturesVO featuresVO3 = dao.findByParimaryKey("F01");
		
		System.out.print(featuresVO3.getFeatures_id()+",");
		System.out.println(featuresVO3.getFeatures_name());
		
		System.out.println("===================查詢成功============");
		
		List<FeaturesVO>list = dao.getAll();
		for(FeaturesVO aFeatures: list) {
			System.out.print(aFeatures.getFeatures_id()+",");
			System.out.println(aFeatures.getFeatures_name());
			
			System.out.println("===================查詢成功============");
		}
		
		
		
		

	}
}
