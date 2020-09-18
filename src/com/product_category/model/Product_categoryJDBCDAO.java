package com.product_category.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.pro.model.ProVO;


public class Product_categoryJDBCDAO implements Product_categoryDAO_interface {
private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO PRODUCT_CAT(CATEGORY_ID,CATEGORY_NAME) VALUES('C'||lpad(SEQ_CATEGORY_ID.nextval,3,0),?)";
	private static final String GET_ALL_STMT = "SELECT * FROM PRODUCT_CAT";
	private static final String GET_ONE_CAT_STMT = "SELECT * FROM PRODUCT_CAT WHERE CATEGORY_ID=?";
	private static final String UPDATE = "UPDATE PRODUCT_CAT SET CATEGORY_NAME=? WHERE CATEGORY_ID = ?";
	private static final String GET_PRO_BY_CATID_STMT = "SELECT * FROM PRODUCT where CATEGORY_ID = ? order by PRODUCT_ID";

	@Override
	public void insert(Product_categoryVO product_categoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(INSERT_STMT);

			
//			pstmt.setString(1, product_categoryVO.getCategory_Id());
            pstmt.setString(1, product_categoryVO.getCategory_Name());
			pstmt.executeUpdate();
			

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
	public void update(Product_categoryVO product_categoryVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE);

			
			pstmt.setString(1, product_categoryVO.getCategory_Name());
			pstmt.setString(2, product_categoryVO.getCategory_Id());
			pstmt.executeUpdate();
			System.out.println("DAO_UPD���J���\");

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
	public  Product_categoryVO findByPrimaryKey(String category_Id) {

		Product_categoryVO product_categoryVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ONE_CAT_STMT);
//			System.out.println("��FBPK");
			

			pstmt.setString(1, category_Id);

			rs = pstmt.executeQuery();	
			product_categoryVO = new Product_categoryVO();
			
			while (rs.next()) {
				product_categoryVO = new Product_categoryVO();
				product_categoryVO.setCategory_Id(rs.getString("CATEGORY_ID"));
				product_categoryVO.setCategory_Name(rs.getString("CATEGORY_NAME"));	
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
		return product_categoryVO;
	}

//		@Override
	public List<Product_categoryVO> getAll() {
		List<Product_categoryVO> list = new ArrayList<Product_categoryVO>();
		Product_categoryVO product_categoryVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				product_categoryVO = new Product_categoryVO();
				product_categoryVO.setCategory_Id(rs.getString("CATEGORY_ID"));
				product_categoryVO.setCategory_Name(rs.getString("CATEGORY_NAME"));
//				System.out.println("------------------------------------------");
//				System.out.println(product_categoryVO.getCategory_Id());
//				System.out.println(product_categoryVO.getCategory_Name());
//				System.out.println("------------------------------------------");

				list.add(product_categoryVO); 
				
//					
			}
			System.out.println("DAO_GETALL���J���\");
			
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

	@Override
	public Set<ProVO> getProductsByCategory(String category_Id) {
		Set<ProVO> set = new LinkedHashSet<ProVO>();
		ProVO ProVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_PRO_BY_CATID_STMT);

			pstmt.setString(1, category_Id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				ProVO = new ProVO();
				ProVO.setProduct_Id(rs.getString("Product_Id"));
				ProVO.setCategory_Id(rs.getString("Category_Id"));
				ProVO.setProduct_Name(rs.getString("Product_Name"));
				ProVO.setProduct_Price(rs.getInt("Product_Price"));
				ProVO.setProduct_Detail(rs.getString("Product_Detail"));
				ProVO.setProduct_Status(rs.getInt("Product_Status"));
				ProVO.setProduct_Stock(rs.getInt("Product_Stock"));
				ProVO.setProduct_Brand(rs.getString("Product_Brand"));
				ProVO.setProduct_Hot(rs.getInt("Product_Hot"));
				set.add(ProVO); // Store the row in the list
			}
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

	public static void main(String[] args) {
		Product_categoryJDBCDAO dao = new Product_categoryJDBCDAO();
//		 �ק�
//			Product_categoryVO product_categoryVO2 = new Product_categoryVO();
//			product_categoryVO2.setCategory_Id("C005");;
//			product_categoryVO2.setCategory_Name("�ק����O");;
////
//			dao.update(product_categoryVO2);
//			System.out.println("�ק令�\");
		
		// �s�W
//		Product_categoryVO productVO1 = new Product_categoryVO();
//		productVO1.setCategory_Name("��������");//
//		dao.insert(productVO1);
		
		
//////			��ܷs�W�ӫ~���s��
		List<Product_categoryVO> list = dao.getAll();// ��d�ߥ����@��
//		String newestProductNum = null;
//		Iterator<Product_categoryVO> iterator = list.iterator();
//		while (iterator.hasNext()) {
//			newestProductNum = iterator.next().getCategory_Id();
//		}
//		System.out.println("�s�W���\!!�����s���� : " + newestProductNum);
////			��ܷs�W�ӫ~���s��

//			// �d��
//		Product_categoryVO product_categoryVO4 = dao.findByPrimaryKey("C003");
//		System.out.println("--------------------------------");
//		System.out.println("���O�s�� : " + "\t" + product_categoryVO4.getCategory_Name());
//		System.out.println("���O�W�� : " + "\t" + product_categoryVO4.getCategory_Id());
//		System.out.println("--------------------------------");
		//
//			// �d�ߥ���

//				System.out.println("List�إߦ��\");

		for (Product_categoryVO aProduct_category : list) {
			System.out.println("--------------------------------");
			System.out.println("���O�s�� : " + "\t" + aProduct_category.getCategory_Id());
			System.out.println("���O�W�� : " + "\t" + aProduct_category.getCategory_Name());
//
		}
		// �d�߬Y���O���ӫ~
		String category = "C001";
		System.out.println("�H�U�����O�s��" + category + "���Ҧ��ӫ~");
		Set<ProVO> set = dao.getProductsByCategory(category);
		for (ProVO aProduct : set) {			
			System.out.println("--------------------------------");
			System.out.println("�ӫ~�s�� : " + "\t" + aProduct.getProduct_Id());
			System.out.println("�ӫ~�W�� : " + "\t" + aProduct.getProduct_Name());
			System.out.println("���O�s�� : " + "\t" + aProduct.getCategory_Id());
			System.out.println("��� : " + "\t" + aProduct.getProduct_Price());
			System.out.println("�w�s : " +"\t" + aProduct.getProduct_Stock());
			System.out.println("�ӫ~�Ա� : " + "\t" + aProduct.getProduct_Detail());
			System.out.println("�Ϥ� : " + "\t" + aProduct.getProduct_Lpic());
			System.out.println("�Ϥ��@ : " + "\t" + aProduct.getProduct_Lpic1());
			System.out.println("�Y�� : " + "\t" + aProduct.getProduct_Spic());
//			System.out.println("�`���� : " +"\t" + aProduct.getP_rating());
//			System.out.println("�����H�� : " + "\t" + aProduct.getNumber_of_rating());
			System.out.println("���A : " + "\t" + aProduct.getProduct_Status());
			System.out.println();
		}
		System.out.println("--------------------------------");
		System.out.println("��ܦ��\");
	}
}
