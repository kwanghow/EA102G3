package com.pro.detail.model;

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

import com.pro.cart.model.Cart;

import ExceptionHandle.CloseHandle;

public class DetailDAO implements DetailDAO_interface {
	
private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERID = "EA102G3";
	private static final String PASSWD = "123456";
	

private static final String INSERT_STMT = "INSERT INTO ORDER_DETAIL("
			+ "ORDER_ID, "
			+ "PRODUCT_ID,"			
			+ "PRODUCT_NAME,"			
			+ "ORDER_BUYMOUNT,"
			+ "PRODUCT_PRICE"					
			+ ")VALUES"
			+ "(?,"
			+ "?,?,?,?"
			+ ")";

private static final String DELETE = 
"DELETE FROM ORDER_DETAIL WHERE ORDER_id=?";	

private static final String UPDATE = 
"UPDATE ORDER_DETAIL SET ORDER_BUYMOUNT=?, PRODUCT_PRICE=? WHERE ORDER_ID = ? AND PRODUCT_ID = ?";

	private static final String GET_ALL_STMT = 
			"SELECT * FROM ORDER_DETAIL ORDER BY ORDER_ID";
		
	private static final String GET_ALL_BY_ORDER_NO = 
			"SELECT * FROM ORDER_DETAIL WHERE ORDER_ID = ?";

	CloseHandle closeHandle = new CloseHandle();

	
	@Override
	public void insert(DetailVO DetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
	con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

		
			
			pstmt.setString(1, DetailVO.getOrder_id());
			pstmt.setString(2, DetailVO.getProduct_id());
			pstmt.setInt(3, DetailVO.getOrder_buymount());
			pstmt.setInt(4, DetailVO.getProduct_price());		
			

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}

	}

	@Override
	public void update(DetailVO DetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, DetailVO.getOrder_buymount());
			pstmt.setInt(2, DetailVO.getProduct_price());
			pstmt.setString(3, DetailVO.getOrder_id());			
			pstmt.setString(4, DetailVO.getProduct_id());
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}

	}
	
	@Override
	public void delete(String order_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, order_id);
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}

	}
	
	@Override
	public List<DetailVO> getAll() {
		
		List<DetailVO> list = new ArrayList<DetailVO>();

		DetailVO DetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				DetailVO = new DetailVO();				
				DetailVO.setOrder_id(rs.getString("ORDER_ID"));
				DetailVO.setProduct_id(rs.getString("PRODUCT_ID"));
				DetailVO.setOrder_buymount(rs.getInt("ORDER_BUYMOUNT"));
				DetailVO.setProduct_price(rs.getInt("PRODUCT_PRICE"));
				

				list.add(DetailVO); // Store the row in the list
			}

			System.out.println("Detail_getall_off");
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			closeHandle.close(rs, pstmt, con);
		}

		return list;
	}
	

	@Override
	public List<DetailVO> getAllByOrderNo(String order_id) {
		System.out.println("order_id" + order_id);
		
		List<DetailVO> list = new ArrayList<DetailVO>();

		DetailVO DetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_ORDER_NO);
			pstmt.setString(1, order_id);		
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				DetailVO = new DetailVO();				
				DetailVO.setOrder_id(rs.getString("ORDER_ID"));
				DetailVO.setProduct_id(rs.getString("PRODUCT_ID"));
				DetailVO.setProduct_name(rs.getString("PRODUCT_NAME"));
				DetailVO.setOrder_buymount(rs.getInt("ORDER_BUYMOUNT"));
				DetailVO.setProduct_price(rs.getInt("PRODUCT_PRICE"));

				list.add(DetailVO);
				
			}

			System.out.println("Detail_getall_by_order_id_off");
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
//		catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		finally {
			closeHandle.close(rs, pstmt, con);
			
		}
		

		return list;
	}
	

	
//LIST FOREACH SET進去
	@Override
	public void addWithPro_Order(Cart Cart, Connection con) {
		PreparedStatement pstmt =null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, Cart.getOrder_Id());
			pstmt.setString(2, Cart.getProduct_Id());
			pstmt.setString(3, Cart.getProduct_Name());
			pstmt.setInt(4, Cart.getOrder_buymount());
			pstmt.setInt(5, Cart.getProduct_Price());	

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3.設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-OrderMaster");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}
		
	}	


	public static void main(String[] args) {
	
	DetailDAO dao = new DetailDAO();

	// 新增
//	DetailVO DetailVO = new DetailVO();	
//	
//
//	DetailVO.setOrder_id("O001");
//	DetailVO.setProduct_id("P001");
//	DetailVO.setOrder_buymount(10);
//	DetailVO.setProduct_price(1888);	
//	dao.insert(DetailVO);
//	System.out.println("新增成功!!");

////	顯示新增商品的編號
//	List<DetailVO> list = dao.getAll();// 跟查詢全部共用
	
		
	
	
	// 修改
//	DetailVO DetailVO2 = new DetailVO();
//	DetailVO2.setOrder_id("O001");
//	DetailVO2.setProduct_id("P002");
//	DetailVO2.setOrder_buymount(11);
//	DetailVO2.setProduct_price(1999);	
//////	
//	dao.update(DetailVO2);
//	
//	System.out.println("修改成功");


//	// 刪除(修改狀態)
//	dao.delete("O001", "P003");		
//	System.out.println("下架成功");

	
//	DetailVO.setOrder_id(rs.getString("ORDER_ID"));
//	DetailVO.setMember_id(rs.getString("MEMBER_ID"));
//	DetailVO.setCoupon_id(rs.getString("COUPON_ID"));
//	DetailVO.setOrder_pay(rs.getInt("ORDER_PAY"));
//	DetailVO.setDelivery(rs.getInt("DELIVERY"));
//	DetailVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
//	DetailVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
//	DetailVO.setOrder_fee(rs.getInt("ORDER_FEE"));
//	DetailVO.setOrder_status(rs.getInt("ORDER_STATUS"));
//	DetailVO.setCreditCard_Num(rs.getString("CREADITCARD_NUM"));
//	DetailVO.setLast_Three_Num(rs.getString("LAST_THREE_NUM"));				
	
	
	
	// 查詢
//	List<DetailVO> list = dao.getAllByOrderNo("O002");
//	for (DetailVO DetailVO4 : list) {
//	System.out.println("--------------------------------");
//	System.out.println("訂單編號 : " + "\t" + DetailVO4.getOrder_id());
//	System.out.println("商品編號 : " + "\t" + DetailVO4.getProduct_id());
//	System.out.println("優惠券 : " + "\t" + DetailVO4.getOrder_buymount());
//	System.out.println("付款方式: " + "\t" + DetailVO4.getProduct_price());
//	}
	

//	// 查詢全部

	List<DetailVO> list = dao.getAll();
	for (DetailVO DetailVO4 : list) {
	System.out.println("--------------------------------");
	System.out.println("訂單編號 : " + "\t" + DetailVO4.getOrder_id());
	System.out.println("商品編號 : " + "\t" + DetailVO4.getProduct_id());
	System.out.println("優惠券 : " + "\t" + DetailVO4.getOrder_buymount());
	System.out.println("付款方式: " + "\t" + DetailVO4.getProduct_price());
	}
//	System.out.println("--------------------------------");
	System.out.println("顯示成功");
}
}

