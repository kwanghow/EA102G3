package com.stream.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class StreamJDBCDAO implements StreamDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Stream (stream_id,coach_id,stream_vod,stream_header,stream_notice) VALUES (stream_seq.NEXTVAL, ?, ?, ?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM stream order by stream_id";
	private static final String GET_ONE_STMT = "SELECT * FROM stream where stream_id = ?";
	private static final String DELETE = "DELETE FROM stream where stream_id = ?";
	private static final String UPDATE = "UPDATE stream set coach_id=?, stream_vod=?,stream_header=?,stream_notice=? where stream_id=?";

	@Override
	public void insert(StreamVO streamVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);


			
			pstmt.setString(1, streamVO.getCoach_id());
			pstmt.setBytes(2, streamVO.getStream_vod());
			pstmt.setString(3, streamVO.getStream_header());
			pstmt.setDate(4, streamVO.getStream_notice());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void update(StreamVO streamVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, streamVO.getCoach_id());
			pstmt.setBytes(2, streamVO.getStream_vod());
			pstmt.setString(3, streamVO.getStream_header());
			pstmt.setDate(4, streamVO.getStream_notice());
			pstmt.setString(5, streamVO.getStream_id());
			
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public void delete(String stream_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, stream_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public StreamVO findByPrimaryKey(String stream_id) {
		StreamVO streamVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, stream_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				streamVO = new StreamVO();
				streamVO.setStream_id(rs.getString("stream_id"));
				streamVO.setCoach_id(rs.getString("coach_id"));
				streamVO.setStream_vod(rs.getBytes("stream_vod"));
				streamVO.setStream_header(rs.getString("stream_header"));
				streamVO.setStream_notice(rs.getDate("stream_notice"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
		return streamVO;
	}

	@Override
	public List<StreamVO> getAll() {
		List<StreamVO> list = new ArrayList<StreamVO>();
		StreamVO streamVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// streamVO 也稱為 Domain objects
				streamVO = new StreamVO();
				streamVO.setStream_id(rs.getString("stream_id"));
				streamVO.setCoach_id(rs.getString("coach_id"));
				streamVO.setStream_vod(rs.getBytes("stream_vod"));
				streamVO.setStream_header(rs.getString("stream_header"));
				streamVO.setStream_notice(rs.getDate("stream_notice"));

				list.add(streamVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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

	public static void main(String[] args) {

		StreamJDBCDAO dao = new StreamJDBCDAO();

		StreamVO streamVO1 = new StreamVO();
		streamVO1.setStream_id("1010");
		streamVO1.setCoach_id("8");
		streamVO1.setStream_vod(null);
		streamVO1.setStream_header("00一條狗");
		streamVO1.setStream_notice(java.sql.Date.valueOf("1995-08-05"));
		dao.insert(streamVO1);
		
		System.out.println("====================新增成功==================");
		
		

		StreamVO streamVO2 = new StreamVO();
		streamVO2.setStream_id("1001");
		streamVO2.setCoach_id("8");
		streamVO2.setStream_vod(null);
		streamVO2.setStream_header("00一條狗");
		streamVO2.setStream_notice(java.sql.Date.valueOf("1995-08-05"));
		dao.update(streamVO2);
		
		System.out.println("===================修改成功==================");
		
		
//		dao.delete("4");
		
		System.out.println("==================刪除成功=================");
		
		StreamVO streamVO3 = dao.findByPrimaryKey("1001");
		
		System.out.print(streamVO3.getStream_id()+",");
		System.out.print(streamVO3.getCoach_id()+",");
        System.out.print(streamVO3.getStream_vod()+",");
		System.out.print(streamVO3.getStream_header()+",");
		System.out.println(streamVO3.getStream_notice());
        
        
		List<StreamVO> list = dao.getAll();
		for(StreamVO aStream:list) {
			System.out.print(aStream.getStream_id()+",");
			System.out.print(aStream.getCoach_id()+",");
			System.out.print(aStream.getStream_vod()+",");
			System.out.print(aStream.getStream_header()+",");
			System.out.print(aStream.getStream_notice());
			System.out.println("===================查詢成功============");
		}
		
		
		
		
		
		
		
	}
}