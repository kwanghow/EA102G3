package com.employee.model;

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

public class EmployeeDAO implements EmployeeDAO_interface {
	private static DataSource ds = null;
	static {

		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE(emp_id,emp_name,emp_account,emp_psw,emp_email,emp_phone) VALUES ('E' || LPAD (employee_seq.nextval,3,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMPLOYEE order BY emp_id";
	private static final String GET_ONE_STMT = "SELECT *FROM EMPLOYEE where emp_id=?";
	private static final String DELETE = "DELETE FROM EMPLOYEE WHERE emp_id=?";
	private static final String UPDATE = "UPDATE EMPLOYEE SET emp_name=?,emp_account=?,emp_psw=?,emp_email=?,emp_phone=? where emp_id=?";
	private static final String LOGIN = "SELECT * FROM EMPLOYEE where emp_account=?";

	@Override
	public void insert(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, employeeVO.getEmp_name());
			pstmt.setString(2, employeeVO.getEmp_account());
			pstmt.setString(3, employeeVO.getEmp_psw());
			pstmt.setString(4, employeeVO.getEmp_email());
			pstmt.setString(5, employeeVO.getEmp_phone());

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
	public void update(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, employeeVO.getEmp_name());
			pstmt.setString(2, employeeVO.getEmp_account());
			pstmt.setString(3, employeeVO.getEmp_psw());
			pstmt.setString(4, employeeVO.getEmp_email());
			pstmt.setString(5, employeeVO.getEmp_phone());
			pstmt.setString(6, employeeVO.getEmp_id());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void delete(String emp_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_id);

			pstmt.executeUpdate();

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
	public EmployeeVO findByPrimaryKey(String emp_id) {
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, emp_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(rs.getString("emp_id"));
				employeeVO.setEmp_name(rs.getString("emp_name"));
				employeeVO.setEmp_account(rs.getString("emp_account"));
				employeeVO.setEmp_psw(rs.getString("emp_psw"));
				employeeVO.setEmp_email(rs.getString("emp_email"));
				employeeVO.setEmp_phone(rs.getString("emp_phone"));

			}

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

		return employeeVO;
	}

	@Override
	public List<EmployeeVO> getAll() {
		List<EmployeeVO> list = new ArrayList<EmployeeVO>();
		EmployeeVO employeeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(rs.getString("emp_id"));
				employeeVO.setEmp_name(rs.getString("emp_name"));
				employeeVO.setEmp_account(rs.getString("emp_account"));
				employeeVO.setEmp_psw(rs.getString("emp_psw"));
				employeeVO.setEmp_email(rs.getString("emp_email"));
				employeeVO.setEmp_phone(rs.getString("emp_phone"));

				list.add(employeeVO);

			}
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

	public EmployeeVO findByAccount(String emp_account) {
		EmployeeVO employeeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);
			pstmt.setString(1, emp_account);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(rs.getString("emp_id"));
				employeeVO.setEmp_name(rs.getString("emp_name"));
				employeeVO.setEmp_account(rs.getString("emp_account"));
				employeeVO.setEmp_psw(rs.getString("emp_psw"));
				employeeVO.setEmp_email(rs.getString("emp_email"));
				employeeVO.setEmp_phone(rs.getString("emp_phone"));
			}

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
		return employeeVO;
	}
}
