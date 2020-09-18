package com.exptype.model;

import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class ExptypeJDBCDAO implements ExptypeDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO EXPTYPE (exp_Id, exp_Name, exp_Img, exp_Words) VALUES ('S' || LPAD (exptype_seq.NEXTVAL, 2, '0'), ?, ?, ?)";
	private static final String	GET_ALL_STMT = 
			"SELECT exp_Id, exp_Name, exp_Img, exp_Words FROM EXPTYPE order by EXP_ID";
	private static final String GET_ONE_STMT = 
			"SELECT exp_Id, exp_Name, exp_Img, exp_Words FROM EXPTYPE where EXP_ID = ?";
	private static final String DELETE = 
			"DELETE FROM EXPTYPE where EXP_ID = ?";
	private static final String UPDATE =
			"UPDATE EXPTYPE set exp_Img=? where exp_Id=?";


	@Override
	public void insert(ExptypeVO exptypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, exptypeVO.getExp_Name());
			pstmt.setBytes(2, exptypeVO.getExp_Img());
			pstmt.setString(3, exptypeVO.getExp_Words());
			
			pstmt.executeUpdate();
			
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("could't load database driver. "
					+ e.getMessage());
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void update(ExptypeVO exptypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, exptypeVO.getExp_Img());
			pstmt.setString(2, exptypeVO.getExp_Id());
			
			pstmt.executeUpdate();
		
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}	
	}

	@Override
	public void delete(String exp_Id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, exp_Id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public ExptypeVO findByPrimaryKey(String exp_Id) {

		ExptypeVO exptypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, exp_Id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				exptypeVO = new ExptypeVO();
				exptypeVO.setExp_Id(rs.getString("exp_Id"));
				exptypeVO.setExp_Name(rs.getString("exp_Name"));
				exptypeVO.setExp_Img(rs.getBytes("exp_Img"));
				exptypeVO.setExp_Words(rs.getString("exp_Words"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

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
		return exptypeVO;
	}

	@Override
	public List<ExptypeVO> getAll() {
		List<ExptypeVO> list = new ArrayList<ExptypeVO>();
		ExptypeVO exptypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				exptypeVO = new ExptypeVO();
				exptypeVO.setExp_Id(rs.getString("exp_Id"));
				exptypeVO.setExp_Name(rs.getString("exp_Name"));
				exptypeVO.setExp_Img(rs.getBytes("exp_Img"));
				exptypeVO.setExp_Words(rs.getString("exp_Words"));
				list.add(exptypeVO);

			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());

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
	
	public static void main(String[] agrs) throws IOException {
		
		ExptypeJDBCDAO dao = new ExptypeJDBCDAO();
		
		//新增專長項目
//		ExptypeVO exptypeVO1 = new ExptypeVO();
//		exptypeVO1.setExp_Name("格鬥有氧");
//		dao.insert(exptypeVO1);
		
		ExptypeVO exptypeVO3 = new ExptypeVO();
		exptypeVO3.setExp_Id("S01");
		byte[] pic = getPictureByteArray("WebContent/front-end/assets/images/e01.jpeg");
		exptypeVO3.setExp_Img(pic);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S02");
		byte[] pic1 = getPictureByteArray("WebContent/front-end/assets/images/e02.jpeg");
		exptypeVO3.setExp_Img(pic1);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S04");
		byte[] pic2 = getPictureByteArray("WebContent/front-end/assets/images/e04.jpeg");
		exptypeVO3.setExp_Img(pic2);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S03");
		byte[] pic3 = getPictureByteArray("WebContent/front-end/assets/images/e03.jpg");
		exptypeVO3.setExp_Img(pic3);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S05");
		byte[] pic4 = getPictureByteArray("WebContent/front-end/assets/images/e05.jpg");
		exptypeVO3.setExp_Img(pic4);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S06");
		byte[] pic5 = getPictureByteArray("WebContent/front-end/assets/images/e06.jpg");
		exptypeVO3.setExp_Img(pic5);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S07");
		byte[] pic6 = getPictureByteArray("WebContent/front-end/assets/images/e07.jpg");
		exptypeVO3.setExp_Img(pic6);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S08");
		byte[] pic7 = getPictureByteArray("WebContent/front-end/assets/images/e08.jpg");
		exptypeVO3.setExp_Img(pic7);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S09");
		byte[] pic8 = getPictureByteArray("WebContent/front-end/assets/images/e09.jpg");
		exptypeVO3.setExp_Img(pic8);
		dao.update(exptypeVO3);
		
		exptypeVO3.setExp_Id("S10");
		byte[] pic9 = getPictureByteArray("WebContent/front-end/assets/images/e10.jpg");
		exptypeVO3.setExp_Img(pic9);
		dao.update(exptypeVO3);
		
		
		//刪除專長項目
//		dao.delete("S12");
		
		//查詢單筆專長
//		ExptypeVO exptypeVO2 = dao.findByPrimaryKey("S05");
//		System.out.println(exptypeVO2.getExp_Id() + ",");
//		System.out.println(exptypeVO2.getExp_Name() + ",");
//		System.out.println("---------------------");
		
		//查詢全部專長項目
//		List<ExptypeVO> list = dao.getAll();
//		for (ExptypeVO aExptype : list) {
//			System.out.println(aExptype.getExp_Id() + ",");
//			System.out.println(aExptype.getExp_Name() + ",");
//			System.out.println("---------------------");
//		}
	}
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		// ByteArray 也是一個低階管, 管子裡面已經有一個預設的Byte的空陣列
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

}
