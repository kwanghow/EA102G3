package com.stream.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.stream.model.StreamService;
import com.stream.model.StreamVO;

public class StreamServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("stream_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入直播編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String stream_id = null;
				try {
					stream_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				StreamService streamSvc = new StreamService();
				StreamVO streamVO = streamSvc.getOneStream(stream_id);
				if (streamVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("streamVO", streamVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/stream/listOneStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String stream_id = new String(req.getParameter("stream_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				String coach_id = new String(req.getParameter("coach_id"));
				String stream_header = new String(req.getParameter("stream_header"));
				Date stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				StreamService streamSvc = new StreamService();
				StreamVO streamVO = streamSvc.getOneStream(stream_id);

				streamVO.setCoach_id(coach_id);
				streamVO.setStream_header(stream_header);
				streamVO.setStream_notice(stream_notice);

				streamSvc.update(streamVO);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("streamVO", streamVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/stream/update_stream_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/listAllStream.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String stream_id = new String(req.getParameter("stream_id").trim());

				String coach_id = req.getParameter("coach_id");
				String streamReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (coach_id == null || coach_id.trim().length() == 0) {
					errorMsgs.add("教練ID: 請勿空白");
				}

				String stream_header = req.getParameter("stream_header").trim();
				if (stream_header == null || stream_header.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				java.sql.Date stream_notice = null;
				try {
					stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				} catch (IllegalArgumentException e) {
					stream_notice = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String stream_vod = null;
				try {
					stream_vod = new String(req.getParameter("stream_vod").trim());
					if (stream_vod == null || stream_vod.trim().length() == 0) {
						errorMsgs.add("VOD請勿空白");
					}
				} catch (NumberFormatException e) {
					stream_vod = "";
					errorMsgs.add("VOD請填網址.");
				}

//				Integer deptno = new Integer(req.getParameter("deptno").trim());
//
				StreamVO streamVO = new StreamVO();
				streamVO.setStream_id(stream_id);
				streamVO.setCoach_id(coach_id);
//				streamVO.setStream_vod(stream_vod);
				streamVO.setStream_header(stream_header);
				streamVO.setStream_notice(stream_notice);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("streamVO", streamVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/stream/update_stream_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				StreamService streamSvc = new StreamService();
				streamVO = streamSvc.updateStream(stream_id, coach_id, stream_header, stream_notice);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("streamVO", streamVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/stream/listOneStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/update_stream_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//				String stream_id = new String(req.getParameter("stream_id").trim());

				String coach_id = req.getParameter("coach_id");
//				String streamReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (coach_id == null || coach_id.trim().length() == 0) {
					errorMsgs.add("教練ID: 請勿空白");
//				} else if(!coach_id.trim().matches(streamReg)) { //以下練習正則(規)表示式(regular-expression)
//					errorMsgs.add("教練ID: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String stream_header = req.getParameter("stream_header").trim();
				if (stream_header == null || stream_header.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				java.sql.Date stream_notice = null;
				try {
					stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				} catch (IllegalArgumentException e) {
					stream_notice = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				byte[] stream_vod = null;

				StreamVO streamVO = new StreamVO();

				streamVO.setCoach_id(coach_id);
				streamVO.setStream_vod(stream_vod);
				streamVO.setStream_header(stream_header);
				streamVO.setStream_notice(stream_notice);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("streamVO", streamVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/addStream.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				StreamService streamSvc = new StreamService();
				streamVO = streamSvc.addStream(streamVO);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/stream/listAllStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/addStream.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			String stream_id = new String(req.getParameter("stream_id"));

			/*************************** 2.開始刪除資料 ***************************************/
			StreamService streamSvc = new StreamService();
			streamSvc.deleteStream(stream_id);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/front-end/kevin/stream/streamManager.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);

		}

		if ("FindByCoach".equals(action)) {

			String coach_id = req.getParameter("coach_id");

			StreamService streamSvc = new StreamService();
			List<StreamVO> listAll = streamSvc.getAll();
			List<StreamVO> coachVod = new ArrayList<StreamVO>();
			coachVod = listAll.stream().filter(streamVod -> streamVod.getStream_vod()!=null)
					.collect(Collectors.toList());
			req.setAttribute("coachVod", coachVod);
			String url = "/front-end/kevin/stream/listOneStream.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);

		}
		if ("insertAJAX".equals(action)) {

			HttpSession session = req.getSession();

			String coach_id = req.getParameter("coach_id");
			String stream_header = req.getParameter("stream_header").trim();

			java.sql.Date stream_notice = null;
			stream_notice = new java.sql.Date(System.currentTimeMillis());

			byte[] stream_vod = null;

			StreamVO streamVO = new StreamVO();

			streamVO.setCoach_id(coach_id);
			streamVO.setStream_vod(stream_vod);
			streamVO.setStream_header(stream_header);
			streamVO.setStream_notice(stream_notice);
			streamVO.setStream_status(1);
			/*************************** 2.開始新增資料 ***************************************/
			StreamService streamSvc = new StreamService();
			streamVO = streamSvc.addStream(streamVO);
			List<StreamVO> listAll = streamSvc.getAll();
			Optional<StreamVO> latestStream;
			latestStream = listAll.stream().filter(streamVod -> streamVod.getCoach_id().equals(coach_id))
					.reduce((first, second) -> second);
			if (latestStream.isPresent()) {
				streamVO = latestStream.get();
			}
			JSONObject obj=new JSONObject();
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			obj.put("stream_id", streamVO.getStream_id());
			out.write(obj.toString());
			out.flush();
			out.close();
			
			session.setAttribute("streamVO", streamVO);
			System.out.println("新增直播成功等待上傳影片");

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//			String url = "/front-end/kevin/stream/Stream.jsp?stream_id=" + streamVO.getStream_id();
//			res.sendRedirect(req.getContextPath()+url);
//			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//			successView.forward(req, res);

		}

		if ("statusAJAX".equals(action)) {

			HttpSession session = req.getSession();
			StreamVO streamVO = (StreamVO) session.getAttribute("streamVO");
			streamVO.setStream_status(0);
			/*************************** 2.開始新增資料 ***************************************/
			StreamService streamSvc = new StreamService();
			streamSvc.update(streamVO);
			session.setAttribute("streamVO", streamVO);
			System.out.println("直播已關閉");
		}

	}
}
