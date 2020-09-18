package com.dietIng.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class dietIngDAO implements dietIngDAO_interface  {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		}catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO dietIngredient(dietIng_no,dietIng_name,cal) VALUES ('DI10' || LPAD(DIETINGREDIENT_SEQ.NEXTVAL,3,'0'),?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT dietIng_no,dietIng_name,cal FROM dietIngredient order by dietIng_no";
	
	

	@Override
	public dietIngVO findByPrimaryKey(String dietIng_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(dietIngVO dietIngVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con=ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,dietIngVO.getDietIng_no());
			pstmt.setString(2,dietIngVO.getDietIng_name());
			pstmt.setInt(3, dietIngVO.getCal());
			
			pstmt.executeQuery();
			
		}catch (SQLException se) {
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
	public void delete(String dietIng_no) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<dietIngVO> getAll() {
		
		List<dietIngVO> List = new ArrayList<dietIngVO>();
		dietIngVO dietIngVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				dietIngVO = new dietIngVO();
				
				dietIngVO.setDietIng_no(rs.getString("dietIng_no"));
				dietIngVO.setDietIng_name(rs.getString("dietIng_name"));
				dietIngVO.setCal(rs.getInt("cal"));
				
				List.add(dietIngVO);
			}
			
		}
		catch (SQLException se) {
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
		return List;
	}

}
