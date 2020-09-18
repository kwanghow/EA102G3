package com.tools;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ReadPictures extends HttpServlet{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "EA102G3";
	String passwd = "123456";
	Connection con;
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
	

			PreparedStatement pstmt = con.prepareStatement("select PHOTOS from TRAININGLOG where TRAININGLOG_NO =?");

			String id = req.getParameter("TRAININGLOG_NO");
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();

					
			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("PHOTOS"));
				byte[] buf = new byte[4 * 1024];
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			}else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
			rs.close();
			pstmt.close();
			con.close();
		
		}catch (Exception e) {
			//無圖時顯示的部分
			InputStream in = getServletContext().getResourceAsStream("/front-end/guochi/assets/images/NoImage.png");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}
	}

}
