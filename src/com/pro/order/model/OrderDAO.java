package com.pro.order.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.pro.cart.model.Cart;
import com.pro.detail.model.DetailDAO;
import com.pro.detail.model.DetailVO;

import ExceptionHandle.CloseHandle;

public class OrderDAO implements OrderDAO_interface {
private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}
	

private static final String INSERT_STMT = "INSERT INTO PRO_ORDER("
			+ "ORDER_ID, "
			+ "MEMBER_ID,"			
			+ "ORDER_PAY,"
			+ "DELIVERY,"
			+ "ORDER_ADDRESS,"
			+ "ORDER_FEE,"
			+ "ORDER_STATUS"					
			+ ")"
			+ "VALUES(('O'||LPAD(SEQ_ORDER_ID.NEXTVAL,3,'0')),"
			+ "?,?,?,?,?,?"
			+ ")";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM PRO_ORDER ORDER BY ORDER_ID";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM PRO_ORDER WHERE ORDER_ID = ?";
	private static final String DELETE_ORDER = 
			"DELETE FROM PRO_ORDER WHERE ORDER_ID = ?";
	
	private static final String UPDATE_STATUS_STMT = 
			"UPDATE PRO_ORDER SET ORDER_STATUS=? WHERE ORDER_ID=?";
	private static final String COMPLETE_STMT = 
			"UPDATE PRO_ORDER SET ORDER_STATUS=2 WHERE ORDER_ID=?";
	private static final String CANCEL_STMT = 
			"UPDATE PRO_ORDER SET ORDER_STATUS=3 WHERE ORDER_ID=?";

	
	private static final String UPDATE = 
			"UPDATE PRO_ORDER SET ORDER_PAY=?, DELIVERY=?, ORDER_ADDRESS=?, ORDER_FEE=? WHERE ORDER_ID = ?";

//	get_order_in_front_end
	private static final String GET_ALL_BY_MEMBERID_STMT = 
			"SELECT * FROM PRO_ORDER WHERE MEMBER_ID = ? ORDER BY ORDER_STATUS";
	private static final String GET_ORDER_PAY_STMT = 
	"SELECT * FROM PRO_ORDER WHERE MEMBER_ID = ? AND ORDER_STATUS = '1'  ORDER BY ORDER_ID";
	private static final String GET_ORDER_COMPLETED_STMT = 
			"SELECT * FROM PRO_ORDER WHERE MEMBER_ID = ? AND ORDER_STATUS = '2' ORDER BY ORDER_ID";
	private static final String GET_ORDER_CANCELED_STMT = 
			"SELECT * FROM PRO_ORDER WHERE MEMBER_ID = ? AND ORDER_STATUS = '3' ORDER BY ORDER_ID";
	
	CloseHandle closeHandle = new CloseHandle();

	@Override
	public void insert(OrderVO OrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
    		con.setAutoCommit(false);
    		
    		// ���s�W�q��
    		String cols[] = {"Order_id"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);		
			
			pstmt.setString(1, OrderVO.getMember_id());
			pstmt.setInt(2, OrderVO.getOrder_pay());
			pstmt.setInt(3, OrderVO.getDelivery());
			pstmt.setString(4, OrderVO.getOrder_address());
			pstmt.setInt(5, OrderVO.getOrder_fee());
			pstmt.setInt(6, OrderVO.getOrder_status());			
		
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}

	}

	@Override
	public void update(OrderVO OrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, OrderVO.getOrder_pay());
			pstmt.setInt(2, OrderVO.getDelivery());
			pstmt.setString(3, OrderVO.getOrder_address());
			pstmt.setInt(4, OrderVO.getOrder_fee());
			pstmt.setString(5, OrderVO.getOrder_id());			
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
	public void deleteFromFront(OrderVO OrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS_STMT);

			pstmt.setInt(1, OrderVO.getOrder_status());
			pstmt.setString(2, OrderVO.getOrder_id());

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
	public OrderVO findByPrimaryKey(String order_id) {

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, order_id);

			rs = pstmt.executeQuery();
		
			
	
			
			while (rs.next()) {
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("Order_Id"));
				OrderVO.setMember_id(rs.getString("Member_Id"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));
				System.out.println("dao get order_id" + rs.getString("Order_Id"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(rs, pstmt, con);
		}
		System.out.println("dao_up_off");
		return OrderVO;
	}

	@Override
	public void updatestatus(OrderVO OrderVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS_STMT);

			pstmt.setInt(1, OrderVO.getOrder_status());
			pstmt.setString(2, OrderVO.getOrder_id());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}

	}

	@SuppressWarnings("null")
	public List<OrderVO> getAll() {
		List<OrderVO> list = new ArrayList<OrderVO>();

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("ORDER_ID"));
				OrderVO.setMember_id(rs.getString("MEMBER_ID"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));

				list.add(OrderVO); // Store the row in the list
			}

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
	public OrderVO insertWithOrderLists(OrderVO OrderVO, List<Cart> list) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
    		con.setAutoCommit(false);
    		
    		// ���s�W�q��
    		String cols[] = {"Order_id"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);		
			
			pstmt.setString(1, OrderVO.getMember_id());
			pstmt.setInt(2, OrderVO.getOrder_pay());
			pstmt.setInt(3, OrderVO.getDelivery());
			pstmt.setString(4, OrderVO.getOrder_address());
			pstmt.setInt(5, OrderVO.getOrder_fee());
			pstmt.setInt(6, OrderVO.getOrder_status());			
		
			
			pstmt.executeUpdate();
			//�����������ۼW�D���
			String next_Order_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_Order_id = rs.getString(1);
				System.out.println("�ۼW�D���= " + next_Order_id );
			} else {
				System.out.println("�����o�ۼW�D���");
			}
			
							
			OrderVO.setOrder_id(next_Order_id);
			
			
			rs.close();
			
			
			// �A�P�ɷs�W�q�����
			String Order_Id = next_Order_id;
			DetailDAO dao = new DetailDAO();
			for (Cart Cart : list) {				
				Cart.setOrder_Id(new String(Order_Id));
					dao.addWithPro_Order(Cart, con);
						}
//			�M���ʪ������e
			int size = list.size();
			for(int i = size - 1; i >= 0; i--){				
					list.remove(i);
				};
				
				con.commit();
				con.setAutoCommit(true);
			

			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-order");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			closeHandle.close(pstmt, con);
		}
		return OrderVO;
		
	}

	@Override
	public List<OrderVO> getAllOrderByMemNo_All(String member_id) {
		List<OrderVO> list = new ArrayList<OrderVO>();

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ALL_BY_MEMBERID_STMT);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("ORDER_ID"));
				OrderVO.setMember_id(rs.getString("MEMBER_ID"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));

				list.add(OrderVO); // Store the row in the list
			}

			System.out.println("���J���\");
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
	public List<OrderVO> getAllOrderByMemNo_Pay(String member_id) {
		List<OrderVO> list = new ArrayList<OrderVO>();

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_PAY_STMT);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("ORDER_ID"));
				OrderVO.setMember_id(rs.getString("MEMBER_ID"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));

				list.add(OrderVO); // Store the row in the list
			}

			System.out.println("���J���\");
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}
//		catch (IOException e) {
//			e.printStackTrace();
//		} 
		finally {
			closeHandle.close(rs, pstmt, con);
		}

		return list;
	}

	@Override
	public List<OrderVO> getAllOrderByMemNo_Com(String member_id) {
		List<OrderVO> list = new ArrayList<OrderVO>();

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_COMPLETED_STMT);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("ORDER_ID"));
				OrderVO.setMember_id(rs.getString("MEMBER_ID"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));

				list.add(OrderVO); // Store the row in the list
			}

			System.out.println("���J���\");
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
	public List<OrderVO> getAllOrderByMemNo_Can(String member_id) {
		List<OrderVO> list = new ArrayList<OrderVO>();

		OrderVO OrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(GET_ORDER_CANCELED_STMT);
			pstmt.setString(1, member_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				OrderVO = new OrderVO();				
				OrderVO.setOrder_id(rs.getString("ORDER_ID"));
				OrderVO.setMember_id(rs.getString("MEMBER_ID"));
				OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
				OrderVO.setDelivery(rs.getInt("DELIVERY"));
				OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
				OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
				OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
				OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));

				list.add(OrderVO); // Store the row in the list
			}

			System.out.println("���J���\");
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
	
	
	public void complete_order(String order_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(COMPLETE_STMT);
			
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
	
	
	public void cancel_order(String order_id) {
 

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(CANCEL_STMT);
			
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
	
	
	public static void main(String[] args) {
		OrderDAO dao = new OrderDAO();

		// �s�W
//		OrderVO OrderVO = new OrderVO();	
//		
//
//		OrderVO.setMember_id("M001");
//		OrderVO.setOrder_pay(1);
//		OrderVO.setDelivery(1);
//		OrderVO.setOrder_address("��饫���c�ϥ_�I��1515151��");
//		OrderVO.setOrder_fee(4);		
//		OrderVO.setOrder_status(1);//		
//		dao.insert(OrderVO);
//		System.out.println("�s�W���\!!");

////		��ܷs�W�ӫ~���s��
//		List<OrderVO> list = dao.getAll();// ��d�ߥ����@��
		
			
		
		
		// �ק�
//		OrderVO OrderVO2 = new OrderVO();
//		OrderVO2.setOrder_pay(1);
//		OrderVO2.setDelivery(1);
//		OrderVO2.setOrder_address("���a�N�O�A�a");
//		OrderVO2.setOrder_fee(200);
//		OrderVO2.setOrder_id("O001");		
//////		
//		dao.update(OrderVO2);
//		
//		System.out.println("�ק令�\");

//		pstmt.setDouble(1, OrderVO.getQuantity());
//		pstmt.setString(2, OrderVO.getPoStatus());
//		pstmt.setString(3, OrderVO.getPoNo());

		// �R��(�ק窱�A)
//		OrderVO OrderVO3 = new OrderVO();
//		OrderVO3.setOrder_status(4);
//		OrderVO3.setOrder_id("O002");
//		System.out.println(OrderVO3.getOrder_id() + "�w�U�[");
//		dao.deleteFromFront(OrderVO3);
//		System.out.println("�U�[���\");

		
//		OrderVO.setOrder_id(rs.getString("ORDER_ID"));
//		OrderVO.setMember_id(rs.getString("MEMBER_ID"));
//		OrderVO.setCoupon_id(rs.getString("COUPON_ID"));
//		OrderVO.setOrder_pay(rs.getInt("ORDER_PAY"));
//		OrderVO.setDelivery(rs.getInt("DELIVERY"));
//		OrderVO.setOrder_address(rs.getString("ORDER_ADDRESS"));
//		OrderVO.setOrder_date(rs.getTimestamp("ORDER_DATE"));
//		OrderVO.setOrder_fee(rs.getInt("ORDER_FEE"));
//		OrderVO.setOrder_status(rs.getInt("ORDER_STATUS"));
//				
		
		
		
//		// �d��
//		OrderVO OrderVO4 = dao.findByPrimaryKey("O001");
//		Timestamp time = OrderVO4.getOrder_date();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		String timeStr = df.format(time);
//		System.out.println("--------------------------------");
//		System.out.println("�q��s�� : " + "\t" + OrderVO4.getOrder_id());
//		System.out.println("�R�a�s�� : " + "\t" + OrderVO4.getMember_id());
//		System.out.println("�I�ڤ覡: " + "\t" + OrderVO4.getOrder_pay());
//		System.out.println("�H�e�覡: " + "\t" + OrderVO4.getDelivery());
//		System.out.println("�H�e�a�} : " + "\t" + OrderVO4.getOrder_address());
//		System.out.println("�U���� : " + "\t" + timeStr);
//		System.out.println("�B�O : " + "\t" + OrderVO4.getOrder_fee());
//		System.out.println("�q�檬�A : " + "\t" + OrderVO4.getOrder_status());
//		
		

//		// �d�ߥ���

//			System.out.println("List�إߦ��\");
//
//		for (OrderVO aOrder : list) {
//			Timestamp time = aOrder.getOrder_date();
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			String timeStr = df.format(time);
////			
//			System.out.println("--------------------------------");
//		System.out.println("�q��s�� : " + "\t" + aOrder.getOrder_id());
//		System.out.println("�R�a�s�� : " + "\t" + aOrder.getMember_id());
//		System.out.println("�u�f�� : " + "\t" + aOrder.getCoupon_id());
//		System.out.println("�I�ڤ覡: " + "\t" + aOrder.getOrder_pay());
//		System.out.println("�H�e�覡: " + "\t" + aOrder.getDelivery());
//		System.out.println("�H�e�a�} : " + "\t" + aOrder.getOrder_address());
//		System.out.println("�U���� : " + "\t" + timeStr);
//		System.out.println("�B�O : " + "\t" + aOrder.getOrder_fee());
//		System.out.println("�q�檬�A : " + "\t" + aOrder.getOrder_status());
//		System.out.println("�H�Υd�� : " + "\t" + aOrder.getCreditCard_Num());
//		System.out.println("��T�X: " + "\t" + aOrder.getLast_Three_Num());
//		}
////		System.out.println("--------------------------------");
//		System.out.println("��ܦ��\");
	}

	

	
}



