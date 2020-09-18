package com.pro.model;

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

import com.pro.model.ProVO;


import ExceptionHandle.CloseHandle;

public class ProDAO implements ProDAO_interface {
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}

	

	private static final String INSERT_STMT = "INSERT INTO PRODUCT(PRODUCT_ID,CATEGORY_ID , PRODUCT_NAME , PRODUCT_PRICE ,PRODUCT_DETAIL , PRODUCT_STATUS ,PRODUCT_STOCK , PRODUCT_BRAND, PRODUCT_HOT,PRODUCT_Lpic,PRODUCT_Lpic1,PRODUCT_Spic)VALUES('P'||lpad(SEQ_PRODUCT_ID.nextval,3,0),?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_BY_CAT_STMT = "SELECT PRODUCT_ID,CATEGORY_ID , PRODUCT_NAME , PRODUCT_PRICE ,PRODUCT_DETAIL , PRODUCT_STATUS ,PRODUCT_STOCK , PRODUCT_BRAND, PRODUCT_HOT FROM PRODUCT  WHERE CATEGORY_ID=?";
	private static final String SELECT_STMT = "SELECT * FROM PRODUCT WHERE PRODUCT_ID=?";	
	private static final String UPDATE = "UPDATE PRODUCT set CATEGORY_ID=?, PRODUCT_NAME=?, PRODUCT_PRICE=?, PRODUCT_DETAIL=?, PRODUCT_STATUS=?, PRODUCT_STOCK=?, PRODUCT_BRAND=?, PRODUCT_HOT=?, PRODUCT_Lpic=?, PRODUCT_Lpic1=?, PRODUCT_Spic=? where PRODUCT_ID=?";
	private static final String DELETEPRO = "UPDATE PRODUCT set PRODUCT_STATUS=? where PRODUCT_ID=? ";
	private static final String GET_ALL_STMT = "SELECT PRODUCT_ID,CATEGORY_ID , PRODUCT_NAME , PRODUCT_PRICE ,PRODUCT_DETAIL , PRODUCT_STATUS ,PRODUCT_STOCK , PRODUCT_BRAND, PRODUCT_HOT FROM PRODUCT";
	private static final String GET_ALL_FRONT_STMT = "SELECT PRODUCT_ID,CATEGORY_ID , PRODUCT_NAME , PRODUCT_PRICE ,PRODUCT_DETAIL , PRODUCT_STATUS ,PRODUCT_STOCK , PRODUCT_BRAND, PRODUCT_HOT FROM PRODUCT WHERE PRODUCT_HOT=1 AND ROWNUM < 5";
	CloseHandle close = new CloseHandle();
	@Override
	
	
	public void ProInsert(ProVO ProVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			System.out.println("DAOINS�}�l");
			System.out.println(ProVO==null);
//			ProVO ProSelect = ProSelect(ProVO.getProduct_Name());
//			System.out.println("�O�_���ƦW��DAO:"+!(ProSelect==null));
//			System.out.println("ProNameDAO" + ProSelect);
			
				
				
//				pstmt.setString(1, ProVO.getProduct_Id());
				pstmt.setString(1, ProVO.getCategory_Id());
				pstmt.setString(2, ProVO.getProduct_Name());
				pstmt.setInt(3, ProVO.getProduct_Price());
				pstmt.setString(4, ProVO.getProduct_Detail());
				pstmt.setInt(5, ProVO.getProduct_Status());
				pstmt.setInt(6, ProVO.getProduct_Stock());
				pstmt.setString(7, ProVO.getProduct_Brand());
				pstmt.setInt(8,ProVO.getProduct_Hot());
				pstmt.setBytes(9,ProVO.getProduct_Lpic());
				pstmt.setBytes(10,ProVO.getProduct_Lpic1());
				pstmt.setBytes(11,ProVO.getProduct_Spic());
				
				pstmt.executeUpdate();
				System.out.println("�s�W���\");
//				System.out.println("getProduct_Id" + ProVO.getProduct_Id());
				System.out.println(ProVO.getCategory_Id());
				System.out.println(ProVO.getProduct_Name());
				System.out.println(ProVO.getProduct_Price());
				System.out.println(ProVO.getProduct_Detail());				
				System.out.println(ProVO.getProduct_Status());
				System.out.println(ProVO.getProduct_Stock());
				System.out.println(ProVO.getProduct_Brand());
				System.out.println(ProVO.getProduct_Hot());
				System.out.println(ProVO.getProduct_Lpic());
				System.out.println(ProVO.getProduct_Lpic1());
				System.out.println(ProVO.getProduct_Spic());
			
		}catch (SQLException se) {
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
	public void ProUpdate(ProVO ProVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			System.out.println("��FDAO UPDATE �}�l" + ProVO.getProduct_Id());
			pstmt.setString(1, ProVO.getCategory_Id());
			pstmt.setString(2, ProVO.getProduct_Name());
			pstmt.setInt(3, ProVO.getProduct_Price());
			pstmt.setString(4, ProVO.getProduct_Detail());
			pstmt.setInt(5, ProVO.getProduct_Status());
			pstmt.setInt(6, ProVO.getProduct_Stock());
			pstmt.setString(7, ProVO.getProduct_Brand());
			pstmt.setInt(8,ProVO.getProduct_Hot());			
			pstmt.setBytes(9, ProVO.getProduct_Lpic());
			pstmt.setBytes(10, ProVO.getProduct_Lpic1());
			pstmt.setBytes(11, ProVO.getProduct_Spic());
			pstmt.setString(12, ProVO.getProduct_Id());
			
			

			pstmt.executeUpdate();
			System.out.println("DAO UPDATE��s����");

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			close.close(pstmt);
			close.close(con);
		}

	}

	@Override
	public void ProDelete(ProVO ProVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETEPRO);

			System.out.println("DAO GET STATUS" + ProVO.getProduct_Status());
			pstmt.setInt(1,ProVO.getProduct_Status());
			pstmt.setString(2,ProVO.getProduct_Id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			close.close(pstmt);
			close.close(con);
		}
	}

	@Override
	public ProVO ProSelect(String Product_Id){
		ProVO ProVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_STMT);

			pstmt.setString(1, Product_Id);

			rs = pstmt.executeQuery();
			System.out.println("��FDAO PROSELECT");
			
			
			while (rs.next()) {
				ProVO = new ProVO();
				ProVO.setProduct_Id(rs.getString("Product_ID"));
				ProVO.setCategory_Id(rs.getString("Category_ID"));
				ProVO.setProduct_Name(rs.getString("Product_NAME"));
				ProVO.setProduct_Price(rs.getInt("Product_PRICE"));
				ProVO.setProduct_Lpic(rs.getBytes("Product_LPIC"));
				ProVO.setProduct_Lpic1(rs.getBytes("Product_LPIC1"));
				ProVO.setProduct_Spic(rs.getBytes("Product_SPIC"));
				ProVO.setProduct_Detail(rs.getString("Product_DETAIL"));				
				ProVO.setProduct_Status(rs.getInt("Product_STATUS"));
				ProVO.setProduct_Stock(rs.getInt("Product_STOCK"));
				ProVO.setProduct_Brand(rs.getString("Product_BRAND"));
				ProVO.setProduct_Hot(rs.getInt("Product_HOT"));	
				System.out.println("selectID" + rs.getString("Product_ID"));
				System.out.println("selectCATID" + rs.getString("Category_ID"));
				
			}
//			

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
		System.out.println("daoProSelect����");
		return ProVO;
		
	}

	
public List<ProVO> getAll() {
	List<ProVO> list = new ArrayList<ProVO>();
	ProVO ProVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_STMT);
		rs = pstmt.executeQuery();
		System.out.println("into_DAO_getAll");
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
			if(!(rs.getInt("Product_Status")==1)) {
				list.add(ProVO);
			}
														// �p�G�ӫ~���A���w�U�[����ܩ�e�x
				 // Store the row in the list
			
		}
		
		// Handle any driver errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	System.out.println("return_getall");
	return list;
};

public List<ProVO> getAllback() {
	List<ProVO> list = new ArrayList<ProVO>();
	ProVO ProVO = null;

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {

		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_STMT);
		rs = pstmt.executeQuery();
		System.out.println("into_DAO_getAll");
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
				list.add(ProVO);
			
														// �p�G�ӫ~���A���w�U�[����ܩ�e�x
				 // Store the row in the list
			
		}
		
		// Handle any driver errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	System.out.println("return_getall");
	return list;
};

public List<ProVO> getAllfront() {
	List<ProVO> list = new ArrayList<ProVO>();
	ProVO ProVO = null;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	try {
		
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_FRONT_STMT);
		rs = pstmt.executeQuery();
		System.out.println("into_DAO_getAll");
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
			list.add(ProVO);
			
			// �p�G�ӫ~���A���w�U�[����ܩ�e�x
			// Store the row in the list
			
		}
		
		// Handle any driver errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	System.out.println("return_getall");
	return list;
};

@Override
public List<ProVO> getAllByCat(String CateGory_Id) {
	List<ProVO> list = new ArrayList<ProVO>();
	ProVO ProVO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(GET_ALL_BY_CAT_STMT);
		pstmt.setString(1, CateGory_Id);

		rs = pstmt.executeQuery();
		System.out.println("into_DAO_getAllByCat");
		
		
		while (rs.next()) {
			ProVO = new ProVO();
			ProVO.setProduct_Id(rs.getString("Product_ID"));
			ProVO.setCategory_Id(rs.getString("Category_ID"));
			ProVO.setProduct_Name(rs.getString("Product_NAME"));
			ProVO.setProduct_Price(rs.getInt("Product_PRICE"));
//			ProVO.setProduct_Lpic(rs.getBytes("Product_LPIC"));
//			ProVO.setProduct_Lpic1(rs.getBytes("Product_LPIC1"));
//			ProVO.setProduct_Spic(rs.getBytes("Product_SPIC"));
			ProVO.setProduct_Detail(rs.getString("Product_DETAIL"));				
			ProVO.setProduct_Status(rs.getInt("Product_STATUS"));
			ProVO.setProduct_Stock(rs.getInt("Product_STOCK"));
			ProVO.setProduct_Brand(rs.getString("Product_BRAND"));
			ProVO.setProduct_Hot(rs.getInt("Product_HOT"));	
//			System.out.println("getAllBYCAT" + rs.getString("Category_ID"));
			if(!(rs.getInt("Product_Status")==1)) {
				list.add(ProVO);
			}
			
		}
		
		// Handle any driver errors
	} catch (SQLException se) {
		throw new RuntimeException("A database error occured. " + se.getMessage());
		// Clean up JDBC resources
	} finally {
		close.close(rs);
		close.close(pstmt);
		close.close(con);
	}
	System.out.println("return_cat_list");
	return list;
};
public static void main(String[] args) {
	ProDAO dao = new ProDAO();

	// �s�W
	ProVO ProVO = new ProVO();
	ProVO.setCategory_Id("C002");
	ProVO.setProduct_Name("�s��11");
	ProVO.setProduct_Price(12888);
	ProVO.setProduct_Detail("��G");	
	ProVO.setProduct_Status(0);
	ProVO.setProduct_Stock(0);
	ProVO.setProduct_Brand("�٭�");
	ProVO.setProduct_Hot(0);
	dao.ProInsert(ProVO);
//
//////	��ܷs�W�ӫ~���s��`
//	List<ProVO> list = dao.getAll();// ��d�ߥ����@��
//	String newestProductNum = null;
//	Iterator<ProVO> iterator = list.iterator();
//	while(iterator.hasNext()) {
//		newestProductNum = iterator.next().getProduct_Id();
//	}
//	System.out.println("�s�W���\!!�ӫ~�s���� : " + newestProductNum);
//	��ܷs�W�ӫ~���s��

	// �ק����
//	Timestamp time = new Timestamp(System.currentTimeMillis());
//	ProVO ProVO2 = new ProVO();
//	ProVO2.setProduct_Id("P004");
//	ProVO2.setCategory_Id("C001");
//	ProVO2.setProduct_Name("�s��2000");
//	ProVO2.setProduct_Price(13888);
//	ProVO2.setProduct_Detail("��G8");	
//	ProVO2.setProduct_Status(0);
//	ProVO2.setProduct_Stock(0);
//	ProVO2.setProduct_Brand("�٭�12");
//	ProVO2.setProduct_Hot(1);
//	dao.ProUpdate(ProVO2);
//	System.out.println("�ק令�\");
//
	// �R��(�ק窱�A)
//	ProVO productVO3 = new ProVO();
//	productVO3.setProduct_Status(1);
//	productVO3.setProduct_Id("P005");
//	System.out.println(productVO3.getProduct_Id() + "�w�U�[");
//	dao.ProDelete(productVO3);
//	System.out.println("�U�[���\");
//	// �d��
	ProVO ProductVO4 = dao.ProSelect("P001");
//	Timestamp time= ProductVO4.getP_upload_time();
//	SimpleDateFormat df = new SimpleDat+eFormat("yyyy-MM-dd hh:mm:ss");
//	String timeStr = df.format(time); 
	System.out.println("--------------------------------");
	System.out.println("�ӫ~�s�� : " + "\t" + ProductVO4.getProduct_Id());
	System.out.println("�ӫ~�W�� : " + "\t" + ProductVO4.getProduct_Name());
	System.out.println("���O�s�� : " + "\t" + ProductVO4.getCategory_Id());
	System.out.println("��� : " + "\t" + ProductVO4.getProduct_Price());
	System.out.println("�w�s : " +"\t" + ProductVO4.getProduct_Stock());
	System.out.println("�ӫ~�Ա� : " + "\t" + ProductVO4.getProduct_Detail());
	System.out.println("�Ϥ� : " + "\t" + ProductVO4.getProduct_Lpic());
	System.out.println("�Ϥ��@ : " + "\t" + ProductVO4.getProduct_Lpic1());
	System.out.println("�Y�� : " + "\t" + ProductVO4.getProduct_Spic());
//	System.out.println("�`���� : " +"\t" + ProductVO4.getP_rating());
//	System.out.println("�����H�� : " + "\t" + ProductVO4.getNumber_of_rating());
	System.out.println("���A : " + "\t" + ProductVO4.getProduct_Status());


//	// �d�ߥ���

//		System.out.println("List�إߦ��\");

//	for (ProVO aProduct : list) {
////		Timestamp time = aProduct.getpUploadTime();
////		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////		String timeStr = df.format(time);
//		System.out.println("--------------------------------");
//		System.out.println("�ӫ~�s�� : " + "\t" + aProduct.getProduct_Id());
//		System.out.println("�ӫ~�W�� : " + "\t" + aProduct.getProduct_Name());
//		System.out.println("���O�s�� : " + "\t" + aProduct.getCategory_Id());
//		System.out.println("��� : " + "\t" + aProduct.getProduct_Price());
//		System.out.println("�w�s : " +"\t" + aProduct.getProduct_Stock());
//		System.out.println("�ӫ~�Ա� : " + "\t" + aProduct.getProduct_Detail());
//		System.out.println("�Ϥ� : " + "\t" + aProduct.getProduct_Lpic());
//		System.out.println("�Ϥ��@ : " + "\t" + aProduct.getProduct_Lpic1());
//		System.out.println("�Y�� : " + "\t" + aProduct.getProduct_Spic());
////		System.out.println("�`���� : " +"\t" + aProduct.getP_rating());
////		System.out.println("�����H�� : " + "\t" + aProduct.getNumber_of_rating());
//		System.out.println("���A : " + "\t" + aProduct.getProduct_Status());
//
//	}
//	System.out.println("--------------------------------");
//	System.out.println("��ܦ��\");
//}
}



};

