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

		if ("getOne_For_Display".equals(action)) { // �Ӧ�select_page.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String str = req.getParameter("stream_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�����s��");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				String stream_id = null;
				try {
					stream_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("���u�s���榡�����T");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				StreamService streamSvc = new StreamService();
				StreamVO streamVO = streamSvc.getOneStream(stream_id);
				if (streamVO == null) {
					errorMsgs.add("�d�L���");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("streamVO", streamVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/front-end/stream/listOneStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\��� listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // �Ӧ�listAllEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String stream_id = new String(req.getParameter("stream_id"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				String coach_id = new String(req.getParameter("coach_id"));
				String stream_header = new String(req.getParameter("stream_header"));
				Date stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				StreamService streamSvc = new StreamService();
				StreamVO streamVO = streamSvc.getOneStream(stream_id);

				streamVO.setCoach_id(coach_id);
				streamVO.setStream_header(stream_header);
				streamVO.setStream_notice(stream_notice);

				streamSvc.update(streamVO);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("streamVO", streamVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/front-end/stream/update_stream_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/listAllStream.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String stream_id = new String(req.getParameter("stream_id").trim());

				String coach_id = req.getParameter("coach_id");
				String streamReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (coach_id == null || coach_id.trim().length() == 0) {
					errorMsgs.add("�нmID: �ФŪť�");
				}

				String stream_header = req.getParameter("stream_header").trim();
				if (stream_header == null || stream_header.trim().length() == 0) {
					errorMsgs.add("¾��ФŪť�");
				}

				java.sql.Date stream_notice = null;
				try {
					stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				} catch (IllegalArgumentException e) {
					stream_notice = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}

				String stream_vod = null;
				try {
					stream_vod = new String(req.getParameter("stream_vod").trim());
					if (stream_vod == null || stream_vod.trim().length() == 0) {
						errorMsgs.add("VOD�ФŪť�");
					}
				} catch (NumberFormatException e) {
					stream_vod = "";
					errorMsgs.add("VOD�ж���}.");
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
					req.setAttribute("streamVO", streamVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/stream/update_stream_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				StreamService streamSvc = new StreamService();
				streamVO = streamSvc.updateStream(stream_id, coach_id, stream_header, stream_notice);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				req.setAttribute("streamVO", streamVO); // ��Ʈwupdate���\��,���T����empVO����,�s�Jreq
				String url = "/front-end/stream/listOneStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/update_stream_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // �Ӧ�addEmp.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z *************************/
//				String stream_id = new String(req.getParameter("stream_id").trim());

				String coach_id = req.getParameter("coach_id");
//				String streamReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (coach_id == null || coach_id.trim().length() == 0) {
					errorMsgs.add("�нmID: �ФŪť�");
//				} else if(!coach_id.trim().matches(streamReg)) { //�H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
//					errorMsgs.add("�нmID: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
				}

				String stream_header = req.getParameter("stream_header").trim();
				if (stream_header == null || stream_header.trim().length() == 0) {
					errorMsgs.add("¾��ФŪť�");
				}

				java.sql.Date stream_notice = null;
				try {
					stream_notice = java.sql.Date.valueOf(req.getParameter("stream_notice").trim());
				} catch (IllegalArgumentException e) {
					stream_notice = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J���!");
				}

				byte[] stream_vod = null;

				StreamVO streamVO = new StreamVO();

				streamVO.setCoach_id(coach_id);
				streamVO.setStream_vod(stream_vod);
				streamVO.setStream_header(stream_header);
				streamVO.setStream_notice(stream_notice);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("streamVO", streamVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/addStream.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� ***************************************/
				StreamService streamSvc = new StreamService();
				streamVO = streamSvc.addStream(streamVO);

				/*************************** 3.�s�W����,�ǳ����(Send the Success view) ***********/
				String url = "/front-end/stream/listAllStream.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/stream/addStream.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.�����ШD�Ѽ� ***************************************/
			String stream_id = new String(req.getParameter("stream_id"));

			/*************************** 2.�}�l�R����� ***************************************/
			StreamService streamSvc = new StreamService();
			streamSvc.deleteStream(stream_id);

			/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
			String url = "/front-end/kevin/stream/streamManager.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
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
			RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
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
			/*************************** 2.�}�l�s�W��� ***************************************/
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
			System.out.println("�s�W�������\���ݤW�Ǽv��");

			/*************************** 3.�R������,�ǳ����(Send the Success view) ***********/
//			String url = "/front-end/kevin/stream/Stream.jsp?stream_id=" + streamVO.getStream_id();
//			res.sendRedirect(req.getContextPath()+url);
//			RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
//			successView.forward(req, res);

		}

		if ("statusAJAX".equals(action)) {

			HttpSession session = req.getSession();
			StreamVO streamVO = (StreamVO) session.getAttribute("streamVO");
			streamVO.setStream_status(0);
			/*************************** 2.�}�l�s�W��� ***************************************/
			StreamService streamSvc = new StreamService();
			streamSvc.update(streamVO);
			session.setAttribute("streamVO", streamVO);
			System.out.println("�����w����");
		}

	}
}
