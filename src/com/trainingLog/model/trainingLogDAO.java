package com.trainingLog.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.dietCon.model.dietConVO;

public class trainingLogDAO implements trainingLogDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private final String INSERT = 
			"INSERT INTO TRAININGLOG (TRAININGLOG_NO,MEMBER_ID,TRAININGCAT_NO,WEIGHT,PHOTOS,TRAINING_ITEM,TRAININGLOG_DATE) VALUES('TL10' || LPAD(TRAININGLOG_SEQ.NEXTVAL,3,'0'),?,?,?,?,?,?)";
	private final String GET_ONE = 
			"SELECT TRAININGLOG_NO,MEMBER_ID,TRAININGCAT_NO,WEIGHT,PHOTOS,TRAINING_ITEM,TRAININGLOG_DATE FROM TRAININGLOG where MEMBER_ID = ? ORDER BY TRAININGLOG_DATE";
	private final String DELETE = 
			"DELETE FROM TRAININGLOG WHERE TRAININGLOG_NO = ?";
	private final String UPDATE = 
			"UPDATE TRAININGLOG SET TRAININGCAT_NO = ?,WEIGHT = ?,TRAINING_ITEM = ?,PHOTOS = ? WHERE TRAININGLOG_NO = ?";
	private final String GET_ALL =
			"SELECT TRAININGLOG_NO,MEMBER_ID,TRAININGCAT_NO,WEIGHT,PHOTOS,TRAINING_ITEM,TRAININGLOG_DATE FROM TRAININGLOG";

	@Override
	public void insert(trainingLogVO trainingLogVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1,trainingLogVO.getMember_id());
			pstmt.setString(2,trainingLogVO.getTrainingCat_no());
			pstmt.setInt(3, trainingLogVO.getWeight());
			pstmt.setBytes(4, trainingLogVO.getPhotos());
			pstmt.setString(5, trainingLogVO.getTraining_item());
			pstmt.setTimestamp(6, trainingLogVO.getTrainingLog_date());
			
			pstmt.executeUpdate();
			
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error Occurred. " + se.getMessage());

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
	public void update(trainingLogVO trainingLogVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1,trainingLogVO.getTrainingCat_no());
			pstmt.setInt(2,trainingLogVO.getWeight());
			pstmt.setString(3,trainingLogVO.getTraining_item());
			pstmt.setBytes(4,trainingLogVO.getPhotos());
			pstmt.setString(5,trainingLogVO.getTrainingLog_no());
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		}finally {
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
	public void delete(String trainingLog_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1,trainingLog_no);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public Set<trainingLogVO> findByPrimaryKey(String memno) {
		
		Set<trainingLogVO> set = new LinkedHashSet<trainingLogVO>();
		trainingLogVO trainingLogVO = null; 
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);

			pstmt.setString(1, memno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				trainingLogVO= new trainingLogVO();

				trainingLogVO.setMember_id(rs.getString("MEMBER_ID"));
				trainingLogVO.setPhotos(rs.getBytes("PHOTOS"));
				trainingLogVO.setTraining_item(rs.getString("TRAINING_ITEM"));
				trainingLogVO.setTrainingCat_no(rs.getString("TRAININGCAT_NO"));
				trainingLogVO.setTrainingLog_date(rs.getTimestamp("TRAININGLOG_DATE"));
				trainingLogVO.setTrainingLog_no(rs.getString("TRAININGLOG_NO"));
				trainingLogVO.setWeight(rs.getInt("WEIGHT"));
				
				set.add(trainingLogVO);

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
		
		return set;
	}

	@Override
	public List<trainingLogVO> getAll() {
		
		List<trainingLogVO> List = new ArrayList<trainingLogVO>();
		trainingLogVO trainingLogVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				trainingLogVO = new trainingLogVO();

				trainingLogVO.setMember_id(rs.getString("MEMBER_ID"));
				trainingLogVO.setPhotos(rs.getBytes("PHOTOS"));
				trainingLogVO.setTraining_item(rs.getString("TRAINING_ITEM"));
				trainingLogVO.setTrainingCat_no(rs.getString("TRAININGCAT_NO"));
				trainingLogVO.setTrainingLog_date(rs.getTimestamp("TRAININGLOG_DATE"));
				trainingLogVO.setTrainingLog_no(rs.getString("TRAININGLOG_NO"));
				trainingLogVO.setWeight(rs.getInt("WEIGHT"));

				List.add(trainingLogVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occuured." + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
				if (rs != null) {
					try {
						rs.close();
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

		return List;
	}

}
