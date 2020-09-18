package com.profav.model;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DBGifReader2 extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		try {
			Statement stmt = con.createStatement();
			String prod_no = req.getParameter("prod_no");
			String name = req.getParameter("name");
			ResultSet rs = stmt.executeQuery("SELECT "+ name +" FROM product WHERE prod_no ='"+prod_no+"'");

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream(name));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
//			
//				InputStream in = getServletContext().getResourceAsStream("/images/cake007.jpg");
//				byte[] b = new byte[in.available()];
//				in.read(b);
//				out.write(b);
//				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			//System.out.println(e);
			ServletContext context = getServletContext();
//			InputStream in = context.getResourceAsStream("/images/尚無圖片.jpg");
			InputStream in = context.getResourceAsStream("/images/nopic.jpg");
			byte[] pic2 = new byte[in.available()];
			in.read(pic2);
			out.write(pic2);
			in.close();
			
//			InputStream in = getServletContext().getResourceAsStream("/images/尚無圖片.jpg");
//			byte[] b = new byte[in.available()];
//			in.read(b);
//			out.write(b);
//			in.close();
		}
	}

	public void init() throws ServletException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "DA104G3", "654321");
		} catch (ClassNotFoundException e) {
			throw new UnavailableException("Couldn't load JdbcOdbcDriver");
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

}