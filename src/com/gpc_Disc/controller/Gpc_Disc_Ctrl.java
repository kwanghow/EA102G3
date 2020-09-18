package com.gpc_Disc.controller;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gpc_Disc.model.*;


public class Gpc_Disc_Ctrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		//�d�@��
		if("getOneGpc_Disc".equals(action)){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Disc_Id = req.getParameter("gpc_Disc_Id");
				String gpc_Disc_IdReg = "[D][I][S][C][0-9]{3}";
				Gpc_DiscService gpc_DiscSvc;
				Gpc_DiscVO gpc_DiscVO;
				
//�o�̪��_�����c�٨S�u�ơA�����ޤF
///////////////////////////////////////////////////////////////
				if(gpc_Disc_Id != null && gpc_Disc_Id.trim().length() != 0) {
					if(gpc_Disc_Id.trim().matches(gpc_Disc_IdReg)) {
						gpc_DiscSvc = new Gpc_DiscService();
						gpc_DiscVO = gpc_DiscSvc.getOneGpc_Disc(gpc_Disc_Id);
						if(gpc_DiscVO != null) {
							req.setAttribute("gpc_DiscVO", gpc_DiscVO);
							RequestDispatcher successView = req.getRequestDispatcher(
								(req.getServletPath().contains("front-end"))? 
									"": // �o�̫e�ݭn��檺�٨S���ͥX��
									"/back-end/KaiPing/gpc/b_listOneGpc_Disc.jsp"
							); 
							successView.forward(req, res);
							return;
						}else {
							errorMsgs.put("gpc_Disc_Id","�d�L���");
						}				
					}else {
						errorMsgs.put("gpc_Disc_Id","����DISCXXX");
					}					
				} else {
					errorMsgs.put("gpc_Disc_Id","�ФŪť�");
				}
								
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
						"": // �o�̫e�ݭn��檺�٨S���ͥX��
						"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
				);
				failureView.forward(req, res);
////////////////////////////////////////////////////

				
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC_Disc", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
					"": // �o�̫e�ݭn��檺�٨S���ͥX��	
					"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
				);
				failureView.forward(req, res);
			}		
 		}
		
		//�s�W���
		if("insert".equals(action)) { 
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				
				String gpc_Id = req.getParameter("gpc_Id"); //�e�x�۰ʧ�A��x�U�Ԧ����
				
				String member_Id = req.getParameter("member_Id"); //�e�x�۰ʧ�A��x�U�Ԧ����
				
				//���ݤ��e question & ����
				String question = req.getParameter("question");
				if(question == null || question.trim().length() == 0) {
					errorMsgs.put("question","  --���ݽФŪť�!--");
				} else if(question.trim().length() > 100) {
					errorMsgs.put("question","  --�жW�L100�r!--");
				}
				
				//�^�����e  answer & ����
				String answer = req.getParameter("answer");
				if(answer == null || answer.trim().length() == 0) {
					errorMsgs.put("answer","  --�^���ФŪť�!--");
				} else if(answer.trim().length() > 100) {
					errorMsgs.put("answer","  --�a�I�ФŶW�L100�r!--");
				}
				
				Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
				gpc_DiscVO.setGpc_Id(gpc_Id);
				gpc_DiscVO.setMember_Id(member_Id);
				gpc_DiscVO.setQuestion(question);
				gpc_DiscVO.setAnswer( answer);

				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gpc_DiscVO", gpc_DiscVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))? // �e�x�٦������нm�s�W�άO�ǭ��s�W
							"": //�e�x������
							"/back-end/KaiPing/gpc/b_newGpc_Disc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
								
				/*****2.�}�l�s�W���*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				gpc_DiscVO = gpc_DiscSvc.addGpc_Disc( gpc_Id, member_Id, question, answer );				
				
				/******3.�s�W����,�ǳ����(Send the Success view)******/
				RequestDispatcher successView = req.getRequestDispatcher( 
					(req.getServletPath().contains("front-end"))?  // �e�x�٦������нm�s�W�άO�ǭ��s�W
						""://�e�x������
						"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					((req.getServletPath().contains("front-end"))?
						""://�e�x������ 
						"/back-end/KaiPing/gpc/b_newGpc_Disc.jsp")
				);
				failureView.forward(req, res);
			}					
		}
		
		//���@���ק�n��J
		if("getOne_For_Update".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*****1.�����ШD�Ѽ�*****/
				String gpc_Disc_Id = new String(req.getParameter("gpc_Disc_Id"));
				
				/*****2.�}�l�d�߸��*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				Gpc_DiscVO gpc_DiscVO = gpc_DiscSvc.getOneGpc_Disc(gpc_Disc_Id);
								
				/*****3.�d�ߧ���,�ǳ����(Send the Success view)*****/
				req.setAttribute("gpc_DiscVO", gpc_DiscVO);
				RequestDispatcher successView = req.getRequestDispatcher( 
					req.getServletPath().contains("front-end")? 
						""://�e�x������
						"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
				);
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")?
						""://�e�x������
						"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				);
				failureView.forward(req, res);
			}
		}
			
		
		//�ק��W��
		if("update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				//��d��ID
				String gpc_Disc_Id = req.getParameter("gpc_Disc_Id");
				
				//���ݤ��e question & ����
				String question = req.getParameter("question");
				if(question == null || question.trim().length() == 0) {
					errorMsgs.put("question","  --���ݽФŪť�!--");
				} else if(question.trim().length() > 100) {
					errorMsgs.put("question","  --�жW�L100�r!--");
				}
				
				//�^�����e  answer & ����
				String answer = req.getParameter("answer");
				if(answer == null || answer.trim().length() == 0) {
					errorMsgs.put("answer","  --�^���ФŪť�!--");
				} else if(answer.trim().length() > 100) {
					errorMsgs.put("answer","  --�a�I�ФŶW�L100�r!--");
				}
				
				//�d�����ê��A mute & ����
				Integer mute = new Integer(req.getParameter("mute").trim()); 
					// ���O�q���Ӫ��A����trim()�A�M�ᤣ������
				
				Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
				
				gpc_DiscVO.setGpc_Disc_Id(gpc_Disc_Id);
				gpc_DiscVO.setQuestion(question);
				gpc_DiscVO.setAnswer(answer);
				gpc_DiscVO.setMute(mute);
			

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gpc_DiscVO", gpc_DiscVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))? 
							"": //�e�x������
							"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
					);
					failureView.forward(req, res);
					return;
				}
				
				/*****2.�}�l�ק���*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				//�ק�ɭn��supl_Stamp
				gpc_DiscVO = gpc_DiscSvc.updateGpc_Disc(gpc_Disc_Id, question, answer, mute);
				
				
				/******3.�ק粒��,�ǳ����(Send the Success view)******/
				RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))?
							"": // �o�̫e�ݭn��檺�٨S���ͥX��
							"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				);
				failureView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC_Disc", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
						"": // �o�̫e�ݭn��檺�٨S���ͥX��	
						"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
				);
				failureView.forward(req, res);
			}					
				
		}

					
	}
	

}