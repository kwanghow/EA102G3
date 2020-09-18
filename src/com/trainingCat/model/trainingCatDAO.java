package com.trainingCat.model;

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

import com.diet.model.dietVO;

public class trainingCatDAO implements trainingCatDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ALL_STMT = 
			"SELECT TRAININGCAT_NO,TRAININGCAT_NAME,TRAININGCAT_CLASS FROM TRAININGCATAGORY order by TRAININGCAT_NO";

	@Override
	public List<trainingCatVO> getAll() {
		
		List<trainingCatVO> list = new ArrayList<trainingCatVO>();
		trainingCatVO trainingCatVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				trainingCatVO = new trainingCatVO();

				trainingCatVO.setTrainingCat_no(rs.getString("TRAININGCAT_NO"));
				trainingCatVO.setTrainingCat_name(rs.getString("TRAININGCAT_NAME"));
				trainingCatVO.setTrainingCat_class(rs.getString("TRAININGCAT_CLASS"));
				list.add(trainingCatVO);
			}
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

}
