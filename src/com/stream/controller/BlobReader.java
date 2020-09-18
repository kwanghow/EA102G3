
package com.stream.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.stream.model.StreamService;
import com.stream.model.StreamVO;

import javax.servlet.annotation.WebServlet;

@WebServlet("/BlobReader")
public class BlobReader extends HttpServlet {

	private static final long serialVersionUID = 1L;

//	src="<%= request.getContextPath()%>/BlobReader?stream_id=${streamVO.stream_id}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		res.setBufferSize(50 * 1024 * 1024); // KeepAlive
		res.setContentType("video/webm");
		ServletOutputStream out = res.getOutputStream();

		try {

			String stream_id = req.getParameter("stream_id");
			StreamService streamSvc = new StreamService();
			StreamVO streamVO = streamSvc.getOneStream(stream_id);
			byte[] buf = streamVO.getStream_vod();
			if (buf != null) {
				out.write(buf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}