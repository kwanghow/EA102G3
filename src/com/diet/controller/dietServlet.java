package com.diet.controller;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.diet.model.DietService;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.diet.model.dietVO;
import com.dietCon.model.dietConService;
import com.dietCon.model.dietConVO;
import com.trainingLog.model.trainingLogService;
import com.trainingLog.model.trainingLogVO;

public class dietServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");

		if ("addDiet".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = new String(req.getParameter("requestURL"));

			try {
				if ("/diet/listAllDietContext.jsp".equals(requestURL)) {

					String dietno = new String(req.getParameter("dietno"));
					req.setAttribute("dietno", dietno);
				}
				String memno = new String(req.getParameter("memno"));

				System.out.println("from addDiet:");
				System.out.println("memno:" + memno);
//				System.out.println("dietno:"+dietno);

//				DietService DietSvc = new DietService();
//				
//				dietVO dietVO = DietSvc.

				req.setAttribute("memno", memno);
				req.setAttribute("requestURL", requestURL);

				RequestDispatcher send = req.getRequestDispatcher("/front-end/guochi/diet/addDiet.jsp");
				send.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/diet/addDiet.jsp");
				failureView.forward(req, res);
			}

		}

		if ("delete".equals(action)) {

			System.out.println("�i�J�R��");
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/*************************** 1.�����ШD�Ѽ� ***************************************/
				String dietno = new String(req.getParameter("dietno"));
				String memno = new String(req.getParameter("memno"));

				/*************************** 2.�}�l�R����� ***************************************/
				DietService dietSvc = new DietService();
				MemService memSvc = new MemService();

				dietSvc.deldiet(dietno);
				dietVO dietVO = dietSvc.getOneDiet(memno);
				MemVO memVO = memSvc.getOneMem(memno);

				Set<dietVO> set = dietSvc.getDietByMemno(memno);
//				********************3.�d�ߧ����A�}�l���***********
				req.setAttribute("listDiet_ByMemberID", set);
				req.setAttribute("memVO", memVO);
				req.setAttribute("dietVO", dietVO);
				req.setAttribute("memno", memno);

//				String url = requestURL;
				String url = "/front-end/guochi/testCal2.jsp";// ��ƾ�
				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}

		}

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			System.out.println("�s�W�M��modal");
			System.out.println(requestURL);

			try {

				String memno = new String(req.getParameter("memno"));
				String dietIng = new String(req.getParameter("dietIng"));
				String diet_content = new String(req.getParameter("diet_content"));

//				************************9/10�s�W*******************************
//				String nowbool = new String(req.getParameter("nowbool"));
//				String date = new String(req.getParameter("timestamp"));

				Timestamp diet_date = new Timestamp(System.currentTimeMillis());// ����t�η�e�ɶ�
//				Timestamp diet_date = Timestamp.valueOf(date);
//				if (!("1".equals(nowbool))) {
//
//					diet_date = Timestamp.valueOf(date);
//
//				} else {
//					diet_date = new Timestamp(System.currentTimeMillis());// ����t�η�e�ɶ�
//				}
//				************************9/10�s�W*******************************

				dietVO dietVO = new dietVO();
				dietVO.setDiet_date(diet_date);
				dietVO.setMember_id(memno);

				dietConVO dietConVO = new dietConVO();
				dietConVO.setDietIng_no(dietIng);
				dietConVO.setDiet_content(diet_content);

//				String diet_no = dietConVO.getDiet_no();
//				String dietIng_no = dietConVO.getDietIng_no();

				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memno);
				// �|����������

				DietService dietSvc = new DietService();

				List<dietConVO> List = new ArrayList<dietConVO>();
				List.add(dietConVO);
//				�p�G�O�Ӧ�listDietByMember..
				if ("/front-end/guochi/member/listDietByMember.jsp".equals(requestURL)) {
//				****************�@���W�[diet&diet_content**********************
					dietSvc.insertWithDietCon(dietVO, List);
//				1.58
					dietVO = dietSvc.getOneDiet(memno);

					Set<dietVO> set = dietSvc.getDietByMemno(memno);
//				***********************�}�l���********************************
					session.setAttribute("listDiet_ByMemberID", set);
					req.setAttribute("memVO", memVO);
					req.setAttribute("dietVO", dietVO);
					session.setAttribute("memno", memno);

					String url = "/front-end/guochi/member/listDietByMember.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				} else if ("/front-end/guochi/testCal2.jsp".equals(requestURL)) {
//					****************�@���W�[diet&diet_content**********************
					System.out.println("�i�JtestCal2");
					String nowbool = new String(req.getParameter("nowbool"));
					String date = new String(req.getParameter("timestamp"));
					if (!("1".equals(nowbool))) {

						diet_date = Timestamp.valueOf(date);
						System.out.println(diet_date);
						System.out.println("�i�JtestCal2-1");
					} else {
						diet_date = new Timestamp(System.currentTimeMillis());// ����t�η�e�ɶ�
						System.out.println("�i�JtestCal2-2");
					}

					dietVO.setDiet_date(diet_date);
					dietSvc.insertWithDietCon(dietVO, List);

					dietVO = dietSvc.getOneDiet(memno);

					Set<dietVO> set = dietSvc.getDietByMemno(memno);
//				***********************�}�l���********************************
					session.setAttribute("listDiet_ByMemberID", set);
					req.setAttribute("memVO", memVO);
					req.setAttribute("dietVO", dietVO);
					req.setAttribute("memno", memno);

					String url = requestURL;

					res.sendRedirect(req.getContextPath() + url);
//					RequestDispatcher successView = req.getRequestDispatcher(url);
//					successView.forward(req, res);
				} else if ("/front-end/guochi/diet/listAllDietContext.jsp".equals(requestURL)) {
					System.out.println("�i�J11111");
					String dietno = new String(req.getParameter("dietno"));

					dietVO = new dietVO();
					dietVO.setDiet_date(diet_date);
					dietVO.setMember_id(memno);

					dietConVO = new dietConVO();
					dietConVO.setDietIng_no(dietIng);
					dietConVO.setDiet_content(diet_content);

					System.out.println("�q/diet/listAllDietContext.jsp");
//					******************�[�J�����������e*****************************
					dietConService dietConSvc = new dietConService();
					dietConSvc.addDietContent(dietno, dietIng, diet_content);
					Set<dietConVO> set = dietConSvc.getOnediet(dietno);
//					***********************���*********************************

					session.setAttribute("listAlldietContent", set);
					req.setAttribute("dietno", dietno);
					req.setAttribute("memno", memno);

					String url = "/front-end/guochi/diet/listAllDietContext.jsp";

//					res.sendRedirect(req.getContextPath()+url);
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);

				}

			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/guochi/diet/addDiet.jsp");
				failureView.forward(req, res);
			}
		}
		if ("listEmps_ByDeptno_A".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			System.out.println("action=" + action);

			try {

//				********************1. �����ШD�Ѽ�**************
				String memno = new String(req.getParameter("memno"));
				System.out.println(memno);
//				********************2.�}�l�d�߸��***************

				// memSvc
				MemService memSvc = new MemService();
				// DietSvc
				DietService DietSvc = new DietService();

				dietVO dietVO = DietSvc.getOneDiet(memno);

				System.out.println(dietVO.getDiet_date());// print date

				System.out.println("12");
				MemVO memVO = memSvc.getOneMem(memno);

				System.out.println("13");
				// �|����������
				Set<dietVO> set = DietSvc.getDietByMemno(memno);

//				********************3.�d�ߧ����A�}�l���***********
				req.setAttribute("memVO", memVO);
				req.setAttribute("dietVO", dietVO);
				req.setAttribute("memno", memno);
				req.setAttribute("listDiet_ByMemberID", set);

				String url = null;

				url = "/front-end/guochi/member/listDietByMember.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("calendar".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {

				String memno = new String(req.getParameter("memno"));

				DietService DietSvc = new DietService();
//				�s�W
				trainingLogService trainingLogSvc = new trainingLogService();
				Set<trainingLogVO> set2 = trainingLogSvc.getLogByMemno(memno);
//				
				System.out.println(set2);
				dietVO dietVO = DietSvc.getOneDiet(memno);

				Set<dietVO> set = DietSvc.getDietByMemno(memno);

				session.setAttribute("listLogByMemno", set2);

				session.setAttribute("dietVO", dietVO);
				session.setAttribute("listDiet_ByMemberID", set);
				session.setAttribute("memno", memno);

				String url = null;

				url = "/front-end/guochi/testCal2.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				throw new ServletException(e);
			}
		}

		if ("update".equals(action)) {

			try {

				String dietno = new String(req.getParameter("dietno"));
				String diet_date = new String(req.getParameter("diet_date"));
				String memno = new String(req.getParameter("memno"));

				Timestamp date = Timestamp.valueOf(diet_date);

				DietService dietSvc = new DietService();

				dietVO dietVO = dietSvc.update(dietno, date);

				Set<dietVO> set = dietSvc.getDietByMemno(memno);

				trainingLogService trainingLogSvc = new trainingLogService();
				Set<trainingLogVO> set2 = trainingLogSvc.getLogByMemno(memno);

				session.setAttribute("listLogByMemno", set2);

				session.setAttribute("dietVO", dietVO);
				session.setAttribute("listDiet_ByMemberID", set);
				session.setAttribute("memno", memno);

				String url = null;

				url = "/front-end/guochi/testCal2.jsp";
				System.out.println("�e�X��TESTCSL");
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
}
