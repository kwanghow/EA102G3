package com.stream.model;

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

public class StreamDAO implements StreamDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Stream (stream_id,coach_id,stream_vod,stream_header,stream_notice,stream_status) VALUES (stream_seq.NEXTVAL, ?, ?, ?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM stream order by stream_id";
	private static final String GET_ONE_STMT = "SELECT * FROM stream where stream_id = ?";
	private static final String DELETE = "DELETE FROM stream where stream_id = ?";
	private static final String UPDATE = "UPDATE stream set coach_id=?, stream_vod=?,stream_header=?,stream_notice=?,stream_status=? where stream_id=?";

	@Override
	public void insert(StreamVO streamVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);


			
			pstmt.setString(1, streamVO.getCoach_id());
			pstmt.setBytes(2, streamVO.getStream_vod());
			pstmt.setString(3, streamVO.getStream_header());
			pstmt.setDate(4, streamVO.getStream_notice());
			pstmt.setInt(5, streamVO.getStream_status());
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
	public void update(StreamVO streamVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, streamVO.getCoach_id());
			pstmt.setBytes(2, streamVO.getStream_vod());
			pstmt.setString(3, streamVO.getStream_header());
			pstmt.setDate(4, streamVO.getStream_notice());
			pstmt.setInt(5, streamVO.getStream_status());
			pstmt.setString(6, streamVO.getStream_id());
			pstmt.executeUpdate();

			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, stream_id);

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
	public StreamVO findByPrimaryKey(String stream_id) {
		StreamVO streamVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
				streamVO.setStream_status(rs.getInt("stream_status"));
				list.add(streamVO); // Store the row in the list
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
		return list;
	}

	
}