package com.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coach.model.CoachService;
import com.exptype.model.ExptypeService;
import com.mem.model.MemService;


@WebServlet("/ShowPhotos")
public class ShowPhotos extends HttpServlet {

	Connection con;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

		
		try {
			//初始化
			byte[] imgBuf = null;
			

			//取出照片PK
			String img_no = req.getParameter("img_no");
			String type= req.getParameter("type");
			
			switch(type) {
				case "memberPic":
					MemService memberSvc = new MemService();
					imgBuf = memberSvc.getOneMem(img_no).getMem_Img();
					break;
				case "coachPic":
					CoachService  coachSvc = new CoachService();
					imgBuf = coachSvc.getOneByCoach(img_no).getCoach_Img();
					break;
				case "exptypePic":
					ExptypeService exptypeSvc = new ExptypeService();
					imgBuf = exptypeSvc.getOneExptype(img_no).getExp_Img(); 
					break;
			}
			
			out.write(imgBuf);
	
		}catch(Exception e) {
			//無圖時顯示的部分
			InputStream in = getServletContext().getResourceAsStream("/front-end/assets/images/NoImage.jpg");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}
		
	}

}
