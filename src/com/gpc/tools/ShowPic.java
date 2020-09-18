package com.gpc.tools;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.gpc.model.*;

public class ShowPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		GpcService gpcSvc = new GpcService();
		GpcVO gpcVO = gpcSvc.getOneGpc(req.getParameter("gpc_Id"));

		try {
			byte[] picArr = null;
			String picNo = req.getParameter("picNo");
			switch (picNo) {
			case "pic1":
				picArr = gpcVO.getPic1();
				break;
			case "pic2":
				picArr = gpcVO.getPic2();
				break;
			case "pic3":
				picArr = gpcVO.getPic3();
				break;
			default:
				System.out.println("µo¥Í¦bdefault");
				break;
			}

			out.write(picArr);

		} catch (Exception e) {
//			e.printStackTrace();
			InputStream in = getServletContext().getResourceAsStream("/front-end/KaiPing/images/example.png");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}
	}

}