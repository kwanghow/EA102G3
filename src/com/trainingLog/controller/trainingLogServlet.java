package com.trainingLog.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.trainingLog.model.trainingLogService;
import com.trainingLog.model.trainingLogVO;

@WebServlet("/trainingLog.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class trainingLogServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String memno = new String(req.getParameter("memno"));
				String trainLogNo = new String(req.getParameter("trainLogNo"));
				
				String trainLogCat = new String(req.getParameter("trainingCat"));
				
//				System.out.println(trainLogCat);
				Integer weight = new Integer(req.getParameter("weight"));
				String trainingItem = new String(req.getParameter("training_item"));
//				String photo = new String(req.getParameter("photo"));
				byte[] mem_Img = null;
				Part part = req.getPart("upfile1");
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				mem_Img = buf;

				in.close();
				
				trainingLogService svc = new trainingLogService();
				
				svc.update(trainLogCat, weight, mem_Img, trainingItem, trainLogNo);
				Set<trainingLogVO> set = svc.getLogByMemno(memno);

//				*****************查詢完成，開始轉交*****************

				session.setAttribute("listLogByMemno", set);
				session.setAttribute("memno", memno);
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/guochi/testCal2.jsp");
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("無法更新資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/trainingLog/listLogByMember.jsp");
				failureView.forward(req, res);
			}
		}

		if ("get_One_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String trainLogNo = new String(req.getParameter("trainLogNo"));
				String trainLogCat = new String(req.getParameter("trainLogCat"));
				Integer weight = new Integer(req.getParameter("weight"));
				String trainingItem = new String(req.getParameter("trainingItem"));
				String photo = new String(req.getParameter("photo"));
				
				req.setAttribute("trainLogNo",trainLogNo);
				req.setAttribute("trainLogCat",trainLogCat);
				req.setAttribute("weight",weight);
				req.setAttribute("trainingItem",trainingItem);
				req.setAttribute("photo",photo);
				
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/guochi/trainingLog/updateLog.jsp");
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add("無法提取資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/trainingLog/listLogByMember.jsp");
				failureView.forward(req, res);
			}
		}

//		****************************刪除***********************
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String trainLogNo = new String(req.getParameter("trainLogNo"));
				String memno = new String(req.getParameter("memno"));

				trainingLogService svc = new trainingLogService();
				svc.del(trainLogNo);
				Set<trainingLogVO> set = svc.getLogByMemno(memno);

//				*****************查詢完成，開始轉交*****************

				session.setAttribute("listLogByMemno", set);
				session.setAttribute("memno", memno);

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/guochi/testCal2.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法刪除資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/testCal2.jsp");
				failureView.forward(req, res);
			}
		}
//		*********************顯示的部分*************************
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			String requestURL = new String(req.getParameter("requestURL"));
			try {

				String memno = new String(req.getParameter("memno"));

				trainingLogService trainingLogSvc = new trainingLogService();
				Set<trainingLogVO> set = trainingLogSvc.getLogByMemno(memno);

//				*****************查詢完成，開始轉交*****************

				session.setAttribute("listLogByMemno", set);
				session.setAttribute("memno", memno);

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/guochi/trainingLog/listLogByMember.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/traningLog.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("calendar2".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//			String requestURL = new String(req.getParameter("requestURL"));
			try {

				String memno = new String(req.getParameter("memno"));

				trainingLogService trainingLogSvc = new trainingLogService();
				Set<trainingLogVO> set = trainingLogSvc.getLogByMemno(memno);

//				*****************查詢完成，開始轉交*****************

				session.setAttribute("listLogByMemno", set);
				session.setAttribute("memno", memno);

				RequestDispatcher successView = req.getRequestDispatcher("/front-end/guochi/testCal2.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/traningLog.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				**********************取值******************************
				String trainingCat = new String(req.getParameter("trainingCat"));
				int weight = new Integer(req.getParameter("weight"));
				String training_item = new String(req.getParameter("training_item"));
				String memno = new String(req.getParameter("memno"));
				
//				**********************判斷是否為NOW***********************
				String date = new String(req.getParameter("timestamp"));
//				Timestamp trainingLog_date =  Timestamp.valueOf(date);
//				System.out.println(trainingLog_date);
				String nowbool = new String(req.getParameter("nowbool"));
				Timestamp trainingLog_date = Timestamp.valueOf(date);
				if("1".equals(nowbool)) {
					
					trainingLog_date = new Timestamp(System.currentTimeMillis());
				} 
				

//				*********************新增照片****************************
				byte[] mem_Img = null;
				Part part = req.getPart("upfile1");
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				mem_Img = buf;
				in.close();
//				*********************存取*******************************
				trainingLogVO trainingLogVO = new trainingLogVO();

				trainingLogVO.setMember_id(memno);
				trainingLogVO.setTraining_item(training_item);
				trainingLogVO.setTrainingCat_no(trainingCat);
				trainingLogVO.setTrainingLog_date(trainingLog_date);
				trainingLogVO.setWeight(weight);
				trainingLogVO.setPhotos(mem_Img);

				trainingLogService trainingLogSvc = new trainingLogService();
				trainingLogVO = trainingLogSvc.add(memno, trainingCat, weight, mem_Img, training_item,
						trainingLog_date);

				Set<trainingLogVO> set = trainingLogSvc.getLogByMemno(memno);
				session.setAttribute("listLogByMemno", set);
				session.setAttribute("memno", memno);
//				RequestDispatcher successView = req.getRequestDispatcher("/trainingLog/listLogByMember.jsp");
//				successView.forward(req, res);
				
				String url = "/front-end/guochi/testCal2.jsp";
				
				res.sendRedirect(req.getContextPath()+url);
//				RequestDispatcher successView = req.getRequestDispatcher("/testCal2.jsp");
//				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("新增資料失敗" + e.getMessage());
				req.setAttribute("errorMsgs", errorMsgs);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/trainingLog/addLog.jsp");
				failureView.forward(req, res);
			}
		}
	}

	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

}
