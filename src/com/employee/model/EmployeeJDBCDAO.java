package com.employee.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeJDBCDAO implements EmployeeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO EMPLOYEE(emp_id,emp_name,emp_account,emp_psw,emp_email,emp_phone) VALUES ('E' || LPAD (member_seq.nextval,3,'0'),?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT * FROM EMPLOYEE order BY emp_id";
	private static final String GET_ONE_STMT = "SELECT * FROM EMPLOYEE where emp_id=?";
	private static final String DELETE = "DELETE FROM EMPLOYEE WHERE emp_id=?";
	private static final String UPDATE = "UPDATE EMPLOYEE SET emp_name=?,emp_account=?,emp_psw=?,emp_email=?,emp_phone=? where emp_id=?";

	@Override
	public void insert(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, employeeVO.getEmp_name());
			pstmt.setString(2, employeeVO.getEmp_account());
			pstmt.setString(3, employeeVO.getEmp_psw());
			pstmt.setString(4, employeeVO.getEmp_email());
			pstmt.setString(5, employeeVO.getEmp_phone());

			pstmt.executeUpdate();

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
	public void update(EmployeeVO employeeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);

			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, employeeVO.getEmp_name());
			pstmt.setString(2, employeeVO.getEmp_account());
			pstmt.setString(3, employeeVO.getEmp_psw());
			pstmt.setString(4, employeeVO.getEmp_email());
			pstmt.setString(5, employeeVO.getEmp_phone());
			pstmt.setString(6, employeeVO.getEmp_id());

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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, emp_id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
            pstmt.setString(1,emp_id);
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
			Class.forName(driver);
			con = DriverManager.getConnection(url,userid,passwd);
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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}if(con!=null) {
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
	public static void main(String[] args) {
		
		EmployeeJDBCDAO dao = new EmployeeJDBCDAO();
		
//		EmployeeVO employeeVO1 = new EmployeeVO();
//		
//		employeeVO1.setEmp_name("歐文");
//		employeeVO1.setEmp_account("sd8f9");
//		employeeVO1.setEmp_psw("5687s4");
//		employeeVO1.setEmp_email("9999@9999.999");
//		employeeVO1.setEmp_phone("0987654321");
//		dao.insert(employeeVO1);
//		
//		System.out.println("===============小子恭喜你新增成功===============");
//		
//		
//		EmployeeVO employeeVO2 = new EmployeeVO();
//		
//		employeeVO2.setEmp_name("寶傑");
//		employeeVO2.setEmp_account("0");
//		employeeVO2.setEmp_psw("0");
//		employeeVO2.setEmp_email("0");
//		employeeVO2.setEmp_phone("0");
//		employeeVO2.setEmp_id("25");
//		dao.update(employeeVO2);
//		
//		
//		System.out.println("===============小子恭喜你修改成功===============");
//		
//		
//		dao.delete("E029");
//		
//		System.out.println("===============小子恭喜你刪除成功===============");
		
		EmployeeVO employeeVO3 = dao.findByPrimaryKey("E001");
        		
		System.out.print(employeeVO3.getEmp_account()+",");
		System.out.print(employeeVO3.getEmp_name()+",");
		System.out.print(employeeVO3.getEmp_psw()+",");
		System.out.print(employeeVO3.getEmp_email()+",");
		System.out.print(employeeVO3.getEmp_phone());
		
		System.out.println("===============小子恭喜你查詢成功===============");
		
		
//		List<EmployeeVO> list = dao.getAll();
//		for(EmployeeVO aEmp:list) {
//			System.out.print(aEmp.getEmp_id()+",");
//			System.out.print(aEmp.getEmp_name()+",");
//			System.out.print(aEmp.getEmp_account()+",");
//			System.out.print(aEmp.getEmp_psw()+",");
//			System.out.print(aEmp.getEmp_email()+",");
//			System.out.println(aEmp.getEmp_phone());
//		}
//		
//		System.out.println("===============小子恭喜你GetAll成功===============");
		
		
		
		
	}

	@Override
	public EmployeeVO findByAccount(String emp_account) {
		// TODO Auto-generated method stub
		return null;
	}


}
