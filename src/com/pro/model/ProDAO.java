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
			System.out.println("DAOINS開始");
			System.out.println(ProVO==null);
//			ProVO ProSelect = ProSelect(ProVO.getProduct_Name());
//			System.out.println("是否重複名稱DAO:"+!(ProSelect==null));
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
				System.out.println("新增成功");
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
			System.out.println("到達DAO UPDATE 開始" + ProVO.getProduct_Id());
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
			System.out.println("DAO UPDATE更新結束");

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
			System.out.println("到達DAO PROSELECT");
			
			
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
		System.out.println("daoProSelect結束");
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
			// empVO 也稱為 Domain objects
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
														// 如果商品狀態為已下架不顯示於前台
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
			// empVO 也稱為 Domain objects
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
			
														// 如果商品狀態為已下架不顯示於前台
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
			// empVO 也稱為 Domain objects
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
			
			// 如果商品狀態為已下架不顯示於前台
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

	// 新增
	ProVO ProVO = new ProVO();
	ProVO.setCategory_Id("C002");
	ProVO.setProduct_Name("哀鳳11");
	ProVO.setProduct_Price(12888);
	ProVO.setProduct_Detail("手G");	
	ProVO.setProduct_Status(0);
	ProVO.setProduct_Stock(0);
	ProVO.setProduct_Brand("欸剖");
	ProVO.setProduct_Hot(0);
	dao.ProInsert(ProVO);
//
//////	顯示新增商品的編號`
//	List<ProVO> list = dao.getAll();// 跟查詢全部共用
//	String newestProductNum = null;
//	Iterator<ProVO> iterator = list.iterator();
//	while(iterator.hasNext()) {
//		newestProductNum = iterator.next().getProduct_Id();
//	}
//	System.out.println("新增成功!!商品編號為 : " + newestProductNum);
//	顯示新增商品的編號

	// 修改全部
//	Timestamp time = new Timestamp(System.currentTimeMillis());
//	ProVO ProVO2 = new ProVO();
//	ProVO2.setProduct_Id("P004");
//	ProVO2.setCategory_Id("C001");
//	ProVO2.setProduct_Name("哀鳳2000");
//	ProVO2.setProduct_Price(13888);
//	ProVO2.setProduct_Detail("手G8");	
//	ProVO2.setProduct_Status(0);
//	ProVO2.setProduct_Stock(0);
//	ProVO2.setProduct_Brand("欸剖12");
//	ProVO2.setProduct_Hot(1);
//	dao.ProUpdate(ProVO2);
//	System.out.println("修改成功");
//
	// 刪除(修改狀態)
//	ProVO productVO3 = new ProVO();
//	productVO3.setProduct_Status(1);
//	productVO3.setProduct_Id("P005");
//	System.out.println(productVO3.getProduct_Id() + "已下架");
//	dao.ProDelete(productVO3);
//	System.out.println("下架成功");
//	// 查詢
	ProVO ProductVO4 = dao.ProSelect("P001");
//	Timestamp time= ProductVO4.getP_upload_time();
//	SimpleDateFormat df = new SimpleDat+eFormat("yyyy-MM-dd hh:mm:ss");
//	String timeStr = df.format(time); 
	System.out.println("--------------------------------");
	System.out.println("商品編號 : " + "\t" + ProductVO4.getProduct_Id());
	System.out.println("商品名稱 : " + "\t" + ProductVO4.getProduct_Name());
	System.out.println("類別編號 : " + "\t" + ProductVO4.getCategory_Id());
	System.out.println("單價 : " + "\t" + ProductVO4.getProduct_Price());
	System.out.println("庫存 : " +"\t" + ProductVO4.getProduct_Stock());
	System.out.println("商品詳情 : " + "\t" + ProductVO4.getProduct_Detail());
	System.out.println("圖片 : " + "\t" + ProductVO4.getProduct_Lpic());
	System.out.println("圖片一 : " + "\t" + ProductVO4.getProduct_Lpic1());
	System.out.println("縮圖 : " + "\t" + ProductVO4.getProduct_Spic());
//	System.out.println("總評分 : " +"\t" + ProductVO4.getP_rating());
//	System.out.println("評價人數 : " + "\t" + ProductVO4.getNumber_of_rating());
	System.out.println("狀態 : " + "\t" + ProductVO4.getProduct_Status());


//	// 查詢全部

//		System.out.println("List建立成功");

//	for (ProVO aProduct : list) {
////		Timestamp time = aProduct.getpUploadTime();
////		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
////		String timeStr = df.format(time);
//		System.out.println("--------------------------------");
//		System.out.println("商品編號 : " + "\t" + aProduct.getProduct_Id());
//		System.out.println("商品名稱 : " + "\t" + aProduct.getProduct_Name());
//		System.out.println("類別編號 : " + "\t" + aProduct.getCategory_Id());
//		System.out.println("單價 : " + "\t" + aProduct.getProduct_Price());
//		System.out.println("庫存 : " +"\t" + aProduct.getProduct_Stock());
//		System.out.println("商品詳情 : " + "\t" + aProduct.getProduct_Detail());
//		System.out.println("圖片 : " + "\t" + aProduct.getProduct_Lpic());
//		System.out.println("圖片一 : " + "\t" + aProduct.getProduct_Lpic1());
//		System.out.println("縮圖 : " + "\t" + aProduct.getProduct_Spic());
////		System.out.println("總評分 : " +"\t" + aProduct.getP_rating());
////		System.out.println("評價人數 : " + "\t" + aProduct.getNumber_of_rating());
//		System.out.println("狀態 : " + "\t" + aProduct.getProduct_Status());
//
//	}
//	System.out.println("--------------------------------");
//	System.out.println("顯示成功");
//}
}



};

