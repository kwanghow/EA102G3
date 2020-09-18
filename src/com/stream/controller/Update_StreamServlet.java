package com.stream.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.stream.model.StreamService;
import com.stream.model.StreamVO;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@MultipartConfig()
@WebServlet("/front-end/stream/upload.do") 
public class Update_StreamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
			req.setCharacterEncoding("UTF-8");
			res.setContentType("Content-Type; video/webm");
			byte[] buffer = new byte[1024 * 1024];
			InputStream in = req.getInputStream();  
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1){
			    out.write(buffer, 0, bytesRead);
			}
			out.close();
			in.close();
			byte[] blob = out.toByteArray();
			
			HttpSession session = req.getSession();
			StreamVO streamVO = (StreamVO)session.getAttribute("streamVO");
			streamVO.setStream_vod(blob);
			
			StreamService streamSvc = new StreamService();
			streamSvc.update(streamVO);
			
			session.removeAttribute("StreamVO");
			
		System.out.println("上傳完成");	
		
	}

}
