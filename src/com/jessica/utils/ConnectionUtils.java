package com.jessica.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtils {
	
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USERNAME = "EA102G3";
	private static final String PASSWORD = "123456";	
	private static DataSource ds;
	
	/**
	 * 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	 * @return 如果返回null，表示連線失敗
	 */	
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/EA102G3");
//			System.out.println("DataSource建立");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static Connection getConnection() {
//		Connection con = null;
//		try {
//			if(ds != null) {
//				con = ds.getConnection();
//			} else
//				throw new RuntimeException("找不到連線池!!!");
//		}catch(SQLException e) {
//			throw new RuntimeException("SQLException!!!" + e.getMessage());
//		}
//		return con;
//	}
	
	/**
	 * 以驅動器方式取得連線
	 * @return 如果返回null，表示連線失敗
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ClassNotFoundException!!!" + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("SQLException!!!" + e.getMessage());
		} 
		return con;
	}
	
	/**
	 * 關閉連線
	 * @param 欲關閉的Connection
	 */
	public static void close(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
		}
	}	
}
