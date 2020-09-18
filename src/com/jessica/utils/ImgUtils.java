package com.jessica.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course.model.CourseService;
import com.course.model.CourseVo;

public class ImgUtils extends HttpServlet {
	
	CourseService courseSvc = new CourseService();

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/jpg");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			String course_id = req.getParameter("course_id");
			String picMethod = "getPic" + req.getParameter("picIndex");
			
			CourseVo courseVo = courseSvc.getOneCourseById(course_id);						
			Method method = courseVo.getClass().getDeclaredMethod(picMethod, null);
			byte[] b = (byte[]) method.invoke(courseVo, null);
			
			out.write(b);			
		}catch(Exception e) {
			InputStream in = getServletContext().getResourceAsStream("/front-end/Jessica/static/img/none.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

}
