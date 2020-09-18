package com.mem.controller;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.coach.model.*;
import com.mem.model.*;
import com.tools.MailService;


@WebServlet("/mem.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class MemServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

/* �d�ߥ\�� */

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.�����ШD�Ѽ� - ��J�榡�����~�B�z **********************/
				String memId = req.getParameter("member_Id");
				String idTrue = "^[M]{1}\\d{3}$";

				if (memId == null || (memId.trim()).length() == 0) {
					errorMsgs.add("�п�J�|���s��");

				} else if (!memId.trim().matches(idTrue)) {
					errorMsgs.add("�|���s��: �u��O�@M �}�Y, �B�s������001��999���� ");
				}
				// �p�G��J���~, �h�^��쥻�n�J�e��
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}

				/*************************** 2.�}�l�d�߸�� *****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memId);
				if (memVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) *************/
				req.setAttribute("memVO", memVO);
				String url = "/front-end/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
				failureView.forward(req, res);
			}
		}

/* �ק�\�� */

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				String member_Id = new String(req.getParameter("member_Id"));

				/*************************** 2.�}�l�d�߸�� ****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(member_Id);

				/*************************** 3.�d�ߧ���,�ǳ����(Send the Success view) ************/
				req.setAttribute("memVO", memVO); // ��Ʈw���X��memVO����,�s�Jreq
				String url = "/front-end/update_mem_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_mem_input.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
/* �i�J���ק����� */
		if ("update_Front".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
				// �|���s������
				String member_Id = new String(req.getParameter("member_Id").trim());
				
				// �|���m�W����
				String mem_Name = req.getParameter("mem_Name").trim();
				String nameTrue = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_Name == null || mem_Name.trim().length() == 0) {
					errorMsgs.add("�|���m�W: �ФŪť�");
				} else if (!mem_Name.trim().matches(nameTrue)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|���m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
				}
				
				// �|���ʧO����
				String mem_Sex = req.getParameter("mem_Sex").trim();
				if (mem_Sex == null) {
					errorMsgs.add("�|���ʧO�ФŪť�, �п�J <�k> �� <�k> ");
				}
				
				// �|���b������
				String mem_Account = req.getParameter("mem_Account").trim();
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("�|���b���ФŪť�, �B���׳̪���16");
				}
				
				// �|���K�X����
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("�|���K�X�ФŪť�, �B���׳̪���16");
				}
				
				// �|���Ӥ�
				MemService memSvc = new MemService();			
				byte[] mem_Img = null;
				Part part = req.getPart("mem_Img");
				InputStream in = part.getInputStream();
				
				if(in.available()!=0) {			
							byte[] buf = new byte[in.available()];
							in.read(buf);
							mem_Img = buf;
							in.close();
				} else {
					mem_Img = memSvc.getOneMem(member_Id).getMem_Img();
				}
				
				// �|���H�c����
				String mem_Email = req.getParameter("mem_Email");
				String emailTrue = "^([a-zA-Z0-9_]+@[a-zA-Z0-9.]+.[a-zA-Z]{2,4})*$";
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("�|���H�c�ФŪť�, �B���׳̪���50");
				}else if (!mem_Email.trim().matches(emailTrue)) {
					errorMsgs.add("�q�l�H�c�榡���~!!");
				}
				
				// �|���������
				String mem_Phone = req.getParameter("mem_Phone").trim();
				String phoneTrue = "^[0]{1}[9]{1}\\d{8}$";
				if (mem_Phone == null || mem_Phone.trim().length() == 0) {
					errorMsgs.add("�|������ФŪť�");
				} else if (!mem_Phone.trim().matches(phoneTrue)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|������u��O�Ʀr, �}�Y��09, �B���׭�n��10  ��:0987654321");
				}
				
				// �|���ͤ�����
				java.sql.Date mem_Birth = null;
				try {
					mem_Birth = java.sql.Date.valueOf(req.getParameter("mem_Birth").trim());
				} catch (IllegalArgumentException e) {
					mem_Birth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�z���ͤ���!");
				}
				
				// �|���a�}����
				String mem_Addr = req.getParameter("mem_Addr").trim();
				if (mem_Addr == null || mem_Addr.trim().length() == 0) {
					errorMsgs.add("�|���a�}�ФŪť�");
				}
				
				// �|���v������
				Integer mem_Close = new Integer(req.getParameter("mem_Close"));

				// �|���H�Υd����
				String mem_Card = req.getParameter("mem_Card").trim();
				
				MemVO memVO = new MemVO();

				memVO.setMember_Id(member_Id);
				memVO.setMem_Name(mem_Name);
				memVO.setMem_Sex(mem_Sex);
				memVO.setMem_Account(mem_Account);
				memVO.setMem_Psw(mem_Psw);
				memVO.setMem_Img(mem_Img);
				memVO.setMem_Email(mem_Email);
				memVO.setMem_Phone(mem_Phone);
				memVO.setMem_Birth(mem_Birth);
				memVO.setMem_Addr(mem_Addr);
				memVO.setMem_Close(mem_Close);
				memVO.setMem_Card(mem_Card);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // �t����J�榡���~��memVO����,�]�s�Jreq
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "�ק異��!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�ק��� *****************************************/
				
				memVO = memSvc.updateMem(member_Id, mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone,
						mem_Birth, mem_Addr, mem_Close, mem_Card);

				/*************************** 3.�ק粒��,�ǳ����(Send the Success view) *************/
				session.setAttribute("memLogIn", memVO); // ��Ʈwupdate���\��,���T����memVO����,�s�Jreq
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "�ק令�\!");
				String url = "/front-end/updateMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneMem.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "�ק異��!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateMember.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/*�s�W�\��*/		
		if("insert".equals(action)) {
					
			List<String> errorMsgs = new LinkedList<String>();
					
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.�����ШD�Ѽ� ****************************************/
//				// �|���s������
//				String member_Id = new String(req.getParameter("member_Id").trim());
				
				// �s�W�|���m�W
				String mem_Name = req.getParameter("mem_Name").trim();
				String nameTrue = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_Name == null || mem_Name.trim().length() == 0) {
					errorMsgs.add("�|���m�W: �ФŪť�");
				} else if (!mem_Name.trim().matches(nameTrue)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|���m�W: �u��O���B�^��r���B�Ʀr�M_ , �B���ץ��ݦb2��10����");
				}
				
				// �s�W�|���ʧO
				String mem_Sex = req.getParameter("mem_Sex");
				if (mem_Sex == null) {
					errorMsgs.add("�|���ʧO�ФŪť�, �п�J <�k> �� <�k> ");
				}
				
				// �s�W�|���b��
				String mem_Account = req.getParameter("mem_Account").trim();
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("�|���b���ФŪť�, �B���׳̪���16");
				}
				
				// �s�W�|���K�X
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("�|���K�X�ФŪť�, �B���׳̪���16");
				}
				
				// �s�W�|���Ӥ�
				byte[] mem_Img = null;
				Part part = req.getPart("mem_Img");
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				mem_Img = buf;
	
				in.close();
				
				// �s�W�|���H�c
				String mem_Email = req.getParameter("mem_Email");
				String emailTrue = "^([a-zA-Z0-9_]+@[a-zA-Z0-9.]+.[a-zA-Z]{2,4})*$";
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("�|���H�c�ФŪť�, �B���׳̪���50");
				}else if (!mem_Email.trim().matches(emailTrue)) {
					errorMsgs.add("�q�l�H�c�榡���~!!");
				}
				// ���ҫH�c�O�_����
				MemService email = new MemService();
				List<String> allEmail = new ArrayList<String>();
				List<MemVO> emailList = email.getAll();
				for (MemVO a : emailList) {
					allEmail.add(a.getMem_Email());
				}
				if (allEmail.contains(mem_Email)) {
					errorMsgs.add("���q�l�H�c�w�Q�O�H�ϥ�!");
				}
				
				// �s�W�|�����
				String mem_Phone = req.getParameter("mem_Phone").trim();
				String phoneTrue = "^[0]{1}[9]{1}\\d{8}$";
				if (mem_Phone == null || mem_Phone.trim().length() == 0) {
					errorMsgs.add("�|������ФŪť�");
				} else if (!mem_Phone.trim().matches(phoneTrue)) { // �H�U�m�ߥ��h(�W)��ܦ�(regular-expression)
					errorMsgs.add("�|������u��O�Ʀr, �}�Y��09, �B���׭�n��10  ��:0987654321");
				}
				
				// �s�W�|���ͤ�
				java.sql.Date mem_Birth = null;
				try {
					mem_Birth = java.sql.Date.valueOf(req.getParameter("mem_Birth").trim());
				} catch (IllegalArgumentException e) {
					mem_Birth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("�п�J�z���ͤ���!");
				}
				
				// �s�W�|���a�}
				String mem_Addr = req.getParameter("mem_Addr").trim();
				if (mem_Addr == null || mem_Addr.trim().length() == 0) {
					errorMsgs.add("�|���a�}�ФŪť�");
				}
				// �s�W�|���v��
//				Integer mem_Close = new Integer(req.getParameter("mem_Close"));

				// �s�W�|���H�Υd
				String mem_Card = req.getParameter("mem_Card").trim();
				
				MemVO memVO = new MemVO();

//				memVO.setMember_Id(member_Id);
				memVO.setMem_Name(mem_Name);
				memVO.setMem_Sex(mem_Sex);
				memVO.setMem_Account(mem_Account);
				memVO.setMem_Psw(mem_Psw);
				memVO.setMem_Img(mem_Img);
				memVO.setMem_Email(mem_Email);
				memVO.setMem_Phone(mem_Phone);
				memVO.setMem_Birth(mem_Birth);
				memVO.setMem_Addr(mem_Addr);
//				memVO.setMem_Close(mem_Close);
				memVO.setMem_Card(mem_Card);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("memVO", memVO); // �t����J�榡���~��memVO����,�]�s�Jreq
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "�ӽХ���!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.�}�l�s�W��� *****************************************/
				MemService memSvc = new MemService();
				memVO = memSvc.addMem(mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone,
						mem_Birth, mem_Addr, mem_Card);
				
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "�ӽЦ��\!");			
				String url = "/front-end/kevin/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z *************************************/
			} catch (Exception e) {
				errorMsgs.add("�s�W��ƥ���:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "�ӽХ���!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeMember.jsp");
				failureView.forward(req, res);
			}		
		}
		
		
/*�R���\��*/		
		if("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				String member_Id = new String(req.getParameter("member_Id").trim());
				
				/***************************2.�}�l�R�����***************************************/
				MemService memSvc = new MemService();
				memSvc.deleteMem(member_Id);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/								
				String url = "/front-end/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
/*******************************************�|���n�J************************************************/		
		
		if("getMember_Login".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {				
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String mem_Account = req.getParameter("mem_Account");
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("�|���b���ФŪť�, �B���׳̪���16");
				}
				
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("�|���K�X�ФŪť�, �B���׳̪���16");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}	
				/***************************2.�}�l�d�߸��*****************************************/
				MemService memSvc = new MemService();
				MemVO memLogIn = memSvc.logIn(mem_Account);
				
				if (memLogIn == null || !memLogIn.getMem_Account().equals(mem_Account)) {
					errorMsgs.add("�d�L���b��!");
				}
				
				if (memLogIn == null || !memLogIn.getMem_Psw().equals(mem_Psw)) {
					errorMsgs.add("�K�X���~!");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}
				
				CoachService coachSvc = new CoachService();
				CoachVO coachLogIn = coachSvc.getOneByMem(memLogIn.getMember_Id());
				
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				session.setAttribute("memLogIn", memLogIn);
				session.setAttribute("coachLogIn", coachLogIn);				

				try {
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location");
						res.sendRedirect(location);
						return;
					}
				}catch (Exception ignored) { }
				
				res.sendRedirect(req.getContextPath()+"/front-end/kevin/index.jsp");
				
//				String url = "/front-end/fakeIndex.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);				
				/***************************��L�i�઺���~�B�z*************************************/
			}catch (Exception e) {
				errorMsgs.add("�S�����b��!!!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*******************************************�|���n�X************************************************/		
		
		if ("logOut".equals(action)) {
			
			try {
			 session.invalidate();
//			 String url1 = req.getParameter("come");
//			 String[] a = url1.split("/");
//			 String url = a[a.length-1];
//			 String url = "/front-end/kevin/index.jsp";
//			 RequestDispatcher logOut = req.getRequestDispatcher(url);
//			 logOut.forward(req, res);
			 res.sendRedirect(req.getContextPath()+"/front-end/kevin/index.jsp");
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
		if ("logIn".equals(action)) {
			
			try {
				String url = "/front-end/logIn2.jsp";
				RequestDispatcher logIn = req.getRequestDispatcher(url);
				logIn.forward(req, res);
			
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
		
		/*******************************************�ѰO�K�X************************************************/	
		if ("forgot_Password".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				String mem_Email = req.getParameter("mem_Email");
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("�|���H�c�ФŪť�, �B���׳̪���50");
				}
				
				if (!errorMsgs.isEmpty()) {
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "�H�c�榡���~!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�d�߸��*****************************************/
				MemService memSvc = new MemService();
				List<MemVO> list = memSvc.getAll();
				List<MemVO> findEmail = new ArrayList<MemVO>();
				findEmail = list.stream().filter(email -> email.getMem_Email().equals(mem_Email)).collect(Collectors.toList());
				
				if (findEmail.size() != 0) {
					String name = findEmail.get(0).getMem_Name();
					String account = findEmail.get(0).getMem_Account();
					String password = findEmail.get(0).getMem_Psw();
					// �e�X���ҫH��H�c
					MailService sendEmail = new MailService();
					sendEmail.sendMail(mem_Email, "[�ڴN��] �|���q��: " + name + "�z�n", "�z���b����: " + account + "\n" + "�K�X��: " + password);

					System.out.println(mem_Email);
					System.out.println(name);
					System.out.println(account);
					System.out.println(password);
				}
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)*************/
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "�w�o�e���ҫH!");			
				String url = "/front-end/logIn2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "�H�c�榡���~!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
			}
		}
		/*******************************************��x�ק�|�����************************************************/	
		if ("update_From_back".equals(action)) {
			
			try {
				
				String member_Id = req.getParameter("member_Id");
				Integer mem_Close = new Integer(req.getParameter("mem_Close"));
				
				MemService memSvc = new MemService();
				
				memSvc.updateFromBack(member_Id, mem_Close);
				
				String url = "/back-end/admin-2/back-member.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

	}
	
	public String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		System.out.println("header=" + header); // ���ե�
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // ���ե�
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
