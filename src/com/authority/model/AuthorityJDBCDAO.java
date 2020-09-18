package com.authority.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.employee.model.EmployeeVO;
import com.news.model.NewsVO;

public class AuthorityJDBCDAO implements AuthorityDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO AUTHORITY (emp_id,FEATURES_ID) VALUES (?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM AUTHORITY order by emp_id";
	private static final String GET_ONE_STMT = "SELECT * FROM AUTHORITY where emp_id=?";
	private static final String DELETE = "DELETE FROM AUTHORITY WHERE emp_id=? and features_id=?";
	private static final String UPDATE = "UPDATE AUTHORITY SET features_id=? where emp_id=?";
	private static final String GET_EMP_ByFEATURES_STMT = "SELECT * FROM employee where emp_id in (select emp_id from authority where features_id=?) ";

	@Override
	public void insert(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, authorityVO.getEmp_id());
			pstmt.setString(2, authorityVO.getFeatures_id());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void update(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, authorityVO.getFeatures_id());
			pstmt.setString(2, authorityVO.getEmp_id());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void delete(String emp_id,String features_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, emp_id);
			pstmt.setString(2, features_id);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public List<AuthorityVO> findByPrimaryKey(String emp_id) {
		List<AuthorityVO>list=new ArrayList<AuthorityVO>();
		AuthorityVO authorityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, emp_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				authorityVO = new AuthorityVO();
				authorityVO.setEmp_id(rs.getString("emp_id"));
				authorityVO.setFeatures_id(rs.getString("features_id"));
				list.add(authorityVO);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	@Override
	public List<AuthorityVO> getAll() {
		List<AuthorityVO> list = new ArrayList<AuthorityVO>();
		AuthorityVO authorityVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				authorityVO = new AuthorityVO();
				authorityVO.setEmp_id(rs.getString("emp_id"));
				authorityVO.setFeatures_id(rs.getString("features_id"));

				list.add(authorityVO);

			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	@Override
	public Set<EmployeeVO> getEmpByFeatures(String features_id) {
		Set<EmployeeVO> set = new LinkedHashSet<EmployeeVO>();
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EMP_ByFEATURES_STMT);
			pstmt.setString(1, features_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(rs.getString("emp_id"));
				employeeVO.setEmp_account(rs.getString("emp_account"));
				employeeVO.setEmp_email(rs.getString("emp_email"));
				employeeVO.setEmp_name(rs.getString("emp_name"));
				employeeVO.setEmp_phone(rs.getString("emp_phone"));
				employeeVO.setEmp_psw(rs.getString("emp_psw"));
				set.add(employeeVO);
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return set;
	}

	public static void main(String[] args) {
		AuthorityJDBCDAO dao = new AuthorityJDBCDAO();

//		Set<EmployeeVO> set = dao.getEmpByFeatures("F03");
//
//		for (EmployeeVO aNews : set) {
//			System.out.print(aNews.getEmp_id() + ",");
//			System.out.print(aNews.getEmp_name() + ",");
//			System.out.print(aNews.getEmp_account() + ",");
//			System.out.print(aNews.getEmp_psw() + ",");
//			System.out.print(aNews.getEmp_email()+",");
//			System.out.println(aNews.getEmp_phone());
//
//		}
//		List<AuthorityVO>list= new ArrayList();
//		AuthorityVO authorityVO = new AuthorityVO();
//		list=dao.findByPrimaryKey("E001");
//		System.out.println(list);
		AuthorityVO authorityVO = new AuthorityVO();
		authorityVO.setEmp_id("E002");
		authorityVO.setFeatures_id("F01");
		dao.insert(authorityVO);
		
		

	}

}
