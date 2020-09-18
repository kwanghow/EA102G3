package com.exptype.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ExptypeDAO implements ExptypeDAO_interface{
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, exptypeVO.getExp_Name());
			pstmt.setBytes(2, exptypeVO.getExp_Img());
			pstmt.setString(3, exptypeVO.getExp_Words());
			
			pstmt.executeUpdate();
			
			
			
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setBytes(1, exptypeVO.getExp_Img());
			pstmt.setString(2, exptypeVO.getExp_Id());
			
			pstmt.executeUpdate();
		

		
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, exp_Id);

			pstmt.executeUpdate();


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

			con = ds.getConnection();
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



		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " 
					+ se.getMessage());

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

			con = ds.getConnection();
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

}
