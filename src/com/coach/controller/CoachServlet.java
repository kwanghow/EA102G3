package com.coach.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.coach.model.*;
import com.exp.model.*;
import com.exptype.model.*;
import com.mem.model.*;


@WebServlet("/coach.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class CoachServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		/***************************  ��x���u�ק�нm���    *****************************************/
		/*�ק�нm�M���f��*/
		if("update_isExp".equals(action)) {
			try {
				
				String coach_Id = req.getParameter("coach_Id");
				String[] exp_Id = req.getParameterValues("exp_Id");
				String[] isExps = req.getParameterValues("isExp");
								
				List <ExpVO> expList = new ArrayList<ExpVO>();
				
				for (int i=0; i<exp_Id.length; i++) {
					ExpVO expVO = new ExpVO();

					expVO.setCoach_Id(coach_Id);
					expVO.setExp_Id(exp_Id[i]);
					expVO.setIsExp(new Integer(isExps[i]));
					expList.add(expVO);	
				}
				
				ExpService expSvc = new ExpService();
				expSvc.updateIsExp(expList);
				
				String url = "/back-end/admin-2/back-coach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		/*�ק�нm�v��*/
		if ("update_isCoach".equals(action)) {
			
			try {
				
				String coach_Id = req.getParameter("coach_Id");
				Integer isCoach = new Integer(req.getParameter("isCoach"));
				
				CoachService coachSvc = new CoachService();
				
				coachSvc.updateFromBack(coach_Id, isCoach);
				
				String url = "/back-end/admin-2/back-coach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
		
		if ("become_coach".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				// �|���s��
				String member_Id = req.getParameter("member_Id").trim();
				if (member_Id == null || member_Id.trim().length() == 0) {
					errorMsgs.add("�|���s�����i����, �Х�<�n�J�{���b��>��<���U�|��>�~��ӽЦ����нm!");
				}
				// �����g��
				String experience = req.getParameter("experience").trim();
				if (experience == null || experience.trim().length() == 0) {
					errorMsgs.add("�ӽбнm��楲����g�����g��");
				}
				// �нm�Ӥ�
				byte[] coach_Img = null;
				Part part = req.getPart("coach_Img");
				InputStream in = part.getInputStream();			
				byte[] buf = new byte[in.available()];
				in.read(buf);
				coach_Img = buf;
				in.close();
				// �M��(�h��)	
				String[] exp_Id = req.getParameterValues("exp_Id");
				if(exp_Id == null) {
					errorMsgs.add("�ӽбнm��楲���ܤֿ���@���M��");
				}
				
				CoachVO coachVO = new CoachVO();
				coachVO.setMember_Id(member_Id);
				coachVO.setCoach_Img(coach_Img);
				coachVO.setExperience(experience);	
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO); // �t����J�榡���~��memVO����,�]�s�Jreq
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "�ӽХ���!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeCoach.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.�}�l�s�W��� *****************************************/				
				CoachService coachSvc = new CoachService();
				coachVO = coachSvc.insertWithExps(member_Id, experience, coach_Img, exp_Id);
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "�ӽЦ��\!");
				
				/*************************** 3.�ӽЧ���,�ǳ����(Send the Success view) *************/
				
				String url = "/front-end/kevin/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
				
				/*************************** ��L�i�઺���~�B�z *************************************/
			}catch(Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "�ӽХ���!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeCoach.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*************************** �e�x�нm�ק���   *****************************************/
		if ("update_Coach".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
			try {
				// �нm�s��
				String coach_Id = req.getParameter("coach_Id");
				// �|���s��
				String member_Id = req.getParameter("member_Id");
				// �нm�Ӥ�
				CoachService coachSvc = new CoachService();
				byte[] coach_Img = null;
				Part part = req.getPart("coach_Img");
				InputStream in = part.getInputStream();
				
				if(in.available() != 0) {
					byte[] buf = new byte[in.available()];
					in.read(buf);
					coach_Img = buf;
					in.close();
				}else {
					coach_Img = coachSvc.getOneByCoach(coach_Id).getCoach_Img();
				}
				// �����g��
				String experience = req.getParameter("experience").trim();
				if (experience == null || experience.trim().length() == 0) {
					errorMsgs.add("�нm�g��ФŪť�");
				};

				// �O�_���нm
				Integer isCoach = new Integer(req.getParameter("isCoach"));
		
				
				CoachVO coachVO = new CoachVO();
				coachVO.setCoach_Id(coach_Id);
				coachVO.setMember_Id(member_Id);
				coachVO.setCoach_Img(coach_Img);
				coachVO.setExperience(experience);
				coachVO.setIsCoach(isCoach);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("coachVO", coachVO); // �t����J�榡���~��memVO����,�]�s�Jreq
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "�ק異��!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateCoach.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�ק���*****************************************/
				coachVO = coachSvc.updateCoach(coach_Id, member_Id, experience, isCoach, coach_Img);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				req.setAttribute("coachVO", coachVO);
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "�ק令�\!");
				String url = "/front-end/updateCoach.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "�ק異��!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateCoach.jsp");
				failureView.forward(req, res);		
			}
		}
	}
}
