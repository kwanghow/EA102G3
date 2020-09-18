package com.gpc.controller;

import java.io.*;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.coach.model.CoachVO;
import com.exp.model.ExpService;
import com.exp.model.ExpVO;
import com.exptype.model.ExptypeService;
import com.exptype.model.ExptypeVO;
import com.gpc.model.*;
import com.gpc_List.model.*;
import com.gpc_Schedule.model.Gpc_ScheduleService;
import com.gpc_Schedule.model.Gpc_ScheduleVO;
import com.gpc.tools.*;
import com.gpc_CoachView.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class GpcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		GpcService gpcSvc = new GpcService();
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//�d�@��
		if("getOneGPC".equals(action)){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id");
				String gpc_IdReg = "[G][P][C][0-9]{3}";
	
				GpcVO gpcVO;
				
//�o�̪��_�����c�٨S�u�ơA�����ޤF
///////////////////////////////////////////////////////////////
				if(gpc_Id != null && gpc_Id.trim().length() != 0) {
					if(gpc_Id.trim().matches(gpc_IdReg)) {
						gpcSvc = new GpcService();
						gpcVO = gpcSvc.getOneGpc(gpc_Id);
						if(gpcVO != null) {
							req.setAttribute("gpcVO", gpcVO);
							RequestDispatcher successView = req.getRequestDispatcher(
								req.getServletPath().contains("front-end")?
									"": // �e�x������
									"/back-end/KaiPing/gpc/b_listOneGpc.jsp"
							); 
							successView.forward(req, res);
							return;
						}else {
							errorMsgs.put("gpc_Id","�d�L���");
						}				
					}else {
						errorMsgs.put("gpc_Id","����GPCXXX");
					}					
				} else {
					errorMsgs.put("gpc_Id","�ФŪť�");
				}
				
				
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")?
							"": // �e�x������
							"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
					); 
				failureView.forward(req, res);
////////////////////////////////////////////////////				
								
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")?
							"": // �e�x������
							"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
					); 
				failureView.forward(req, res);
			}		
 		}

		//�нm�I���s�W�ҵ{�ұҰʪ�action
		if("findCoachTime".contentEquals(action)) {
			CoachVO coachVO = (CoachVO) req.getSession().getAttribute("coachLogIn");
			RequestDispatcher resultView = null;
			try {
				//���b�Ϊ�if
				if(coachVO==null) {
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/newGpc.jsp");
				}else {
					String coachId= coachVO.getCoach_Id();
					Gpc_CoachViewService cViewSvc = new Gpc_CoachViewService();
					// ����Gpc_CoachViewVO
					List<Gpc_CoachViewVO> coachViewList = cViewSvc.findPeriod_3MM(coachId); 
					// �ΨӸ˾�z�᪺Key-Value
					Map<String, String> coachViewMap = new TreeMap(); 
					BtoF_TimeStrMerger timeMerger = new BtoF_TimeStrMerger();
					// �N���P�Ѫ���ƾ�X���@��
					for(int i =0; i < coachViewList.size(); i++) {
						String keyStr = coachViewList.get(i).getView_Date().toString();
						String timeStr = coachViewList.get(i).getView_Time();
						
						//�YMap�����w��key,��X�ɶ�
						if(coachViewMap.containsKey(keyStr)) {
							String newStr = timeMerger.getMergeStr(timeStr, coachViewMap.get(keyStr));
							if(!timeMerger.validate(newStr)) {
								System.out.println("��Ʈw���ɶ��o�ͽĬ�A���ˬd");
								throw new Exception();
							} else {
								coachViewMap.put(keyStr, newStr);
							}
							
						//�S�����ܷs�W
						} else {
							coachViewMap.put(keyStr, timeStr);
						}					
					}
				

					Gson gson = new Gson();
					String jsonStrCoachView = gson.toJson(coachViewMap);
					HttpSession session = req.getSession();
					session.setAttribute("jsonStrCoachView", jsonStrCoachView);
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/newGpc.jsp");
				}
			
				resultView.forward(req, res);
			
			}  catch(Exception e) {
				
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					"/front-end/KaiPing/gpc/listAllGpc.jsp"
				);
				failureView.forward(req, res);
			}	
			
			
		}
		
		//�I���i�J��@�νҭ��Ұʪ�action
		if("getGpcTime".contentEquals(action)) {
			String gpc_Id = req.getParameter("gpc_Id"); // �o�̬Onull,���L���F��update��өҥH�٬O�o�˼g
			
			String member_Id = req.getParameter("member_Id");
			RequestDispatcher resultView = null;
			try {
				//���b�Ϊ�if
				if(member_Id==null) {
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/SingleGpc.jsp");
				}else {
										
					Gpc_ScheduleService gpcSchSvc = new Gpc_ScheduleService();
					// List��Gpc_ScheduleVO
					List<Gpc_ScheduleVO> gpcSchList = gpcSchSvc.getOneGpc_Schedule(gpc_Id); 
					// Map�˾�z�᪺Key-Value
					Map<String, String> gpcSchMap = new TreeMap();
					
					// List -> Map
					for(int i =0; i < gpcSchList.size(); i++) {
						String keyStr = gpcSchList.get(i).getGpc_Date().toString();
						String timeStr = gpcSchList.get(i).getGpc_Time();
						gpcSchMap.put(keyStr, timeStr);				
					}
					
					Gson gson = new Gson();
					String jsonStrGpcSch = gson.toJson(gpcSchMap);
					
					req.setAttribute("jsonStrGpcSch", jsonStrGpcSch);
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/SingleGpc.jsp");
				}
				Gpc_ListService gpcListSvc = new Gpc_ListService();
				List<Gpc_ListVO> list = gpcListSvc.getGpc_Lists(gpc_Id);
				int count = 0; 
				for(int i = 0; i < list.size(); i++) {
					count++;
					
				}
				int gpcFull = 0;
				int gpcPass = 0;
				int gpc_State = gpcSvc.getOneGpc(gpc_Id).getGpc_State();
				int mem_Max = gpcSvc.getOneGpc(gpc_Id).getMem_Max();
				int mem_Min = gpcSvc.getOneGpc(gpc_Id).getMem_Min();
				if(count >= mem_Max) {
					gpcFull = 1;
				}
				if(count >= mem_Min && gpc_State >0) {
					gpcPass = 1;
				}
				int gpcRemain = mem_Max - count;

				req.setAttribute("gpcFull", gpcFull);
				req.setAttribute("gpcPass", gpcPass);
				req.setAttribute("gpcRemain", gpcRemain);
				
				resultView.forward(req, res);
			
			}  catch(Exception e) {
				
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					"/front-end/KaiPing/gpc/listAllGpc.jsp"
				);
				failureView.forward(req, res);
			}	
			
			
		}
		
		
		//�нm�s�W�ҵ{
		if("insert".equals(action)) { // �Ӧ�newGpc.jsp & b_newGpc.jsp
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id"); // �o�̬Onull,���L���F��update��өҥH�٬O�o�˼g
				
				String coach_Id = req.getParameter("coach_Id").trim(); //trim�u�O���b
				String exp_Id = req.getParameter("exp_Id").trim();//trim�u�O���b
					
				//���νҵ{�W�� gpc_Name & ����
				String gpc_Name = req.getParameter("gpc_Name");
				verifyGpc_Name(errorMsgs, gpc_Name);
				
				//����
				String county = null;
				if(req.getParameter("county") == null) {
					errorMsgs.put("county","  --�п�ܿ���--");
				}else {
					county = req.getParameter("county");
					System.out.println(county);
				}
				
				//�ϰ� & �l���ϸ� (�l���ϸ��|��۰ϰ첣��)
				String district = null;
				String zipcode = null;
				if(req.getParameter("district") == null) {
					errorMsgs.put("district","  --�п�ܰϰ�--");
				}else {
					district = req.getParameter("district");
					zipcode = req.getParameter("zipcode");
					System.out.println(district);
					System.out.println(zipcode);
				}
											
				//�a�I  address & ����
				String address = req.getParameter("address");
				verifyAddress(errorMsgs, address);
				
				//���e����intro & ����
				String intro = req.getParameter("intro");
				verifyIntro(errorMsgs, intro);

				//�Ϥ�,���~���ҥ�JS�b�e�x�����̰�(�w����)
				Part part1 = req.getPart("pic1");
				Part part2 = req.getPart("pic2");
				Part part3 = req.getPart("pic3");
				byte[] pic1 = getPicByteArr(gpc_Id, part1, "pic1");
				byte[] pic2 = getPicByteArr(gpc_Id, part2, "pic2");
				byte[] pic3 = getPicByteArr(gpc_Id, part3, "pic3");
			
				
				//�O��price 				 
				String priceParam = req.getParameter("price");
				Integer price = verifyPrice(errorMsgs, priceParam);
						
				//���W�I����(��ú�O�}�l���) pay_Start				
				String pay_StartParam = req.getParameter("pay_Start");
				java.sql.Date pay_Start =  verifyDate(errorMsgs, pay_StartParam);
				
/**************************************************************************************/
				//�����W�Ҥ�� gpc_Start(�n�Ѥ@�s�ɶ��̮��X)	
				java.sql.Date gpc_Start = null;
				
				
				
				//�����o�n��Jgpc_Schedule�̭����e�ݭ�
				String[] schFrontArr = null;
				FtoB_TimeFormator formator = null;
				if(req.getParameterValues("gpc_sch_front") == null) {
					errorMsgs.put("gpc_Schedule", "�п�ܽҵ{�ɶ�");
				} else {
					schFrontArr = req.getParameterValues("gpc_sch_front");
					//������
//					for(int i= 0; i <schFrontArr.length; i ++) {
//						System.out.println(schFrontArr[i]);
//					}
					
					//�ഫ�榡
					formator = new FtoB_TimeFormator(schFrontArr);
					
					//�Ѥ@�s����r��̮��X�W�ҲĤ@�Ѧr��			
					String gpc_StartStr = formator.getFirstDateStr();
					//��^���
					gpc_Start = verifyDate(errorMsgs, gpc_StartStr);
					
				}
												
/**************************************************************************************/
								
				//�H�ƤU��				 
				String mem_MinParam = req.getParameter("mem_Min");
				Flag isMinValid = new Flag();
				Integer mem_Min = verifyNum(errorMsgs, isMinValid, mem_MinParam, "mem_Min");
							
				//�H�ƤW��				 
				String mem_MaxParam = req.getParameter("mem_Max");
				Flag isMaxValid = new Flag();
				Integer mem_Max = verifyNum(errorMsgs, isMaxValid, mem_MaxParam, "mem_Max");
				
				//�H�ƤW�U���P�_�A���J�榡�L�~�A�A�h�P�_�H�ƤW���O�_�j��U��
				verifyTwoFlag(errorMsgs, isMinValid, isMaxValid, mem_Min, mem_Max);
							
				//�e�^�e�ݶ��
				GpcVO gpcVO = new GpcVO();
				gpcVO.setCoach_Id(coach_Id);
				gpcVO.setExp_Id(exp_Id);
				gpcVO.setGpc_Name(gpc_Name);
				gpcVO.setAddress(address);
				gpcVO.setIntro(intro);
				gpcVO.setPic1(pic1);
				gpcVO.setPic2(pic2);
				gpcVO.setPic3(pic3);
				gpcVO.setPrice(price);
				gpcVO.setPay_Start(pay_Start);
				gpcVO.setGpc_Start(gpc_Start);
				gpcVO.setMem_Min(mem_Min);
				gpcVO.setMem_Max(mem_Max);
							
				if (!errorMsgs.isEmpty()) {
					errorMsgs.put("rePick","  --�Э��s��ܹϤ�--"); // ���F�����ϥΪ̹Ϥ��n���s��
					errorMsgs.put("county","  --�п�ܿ���--");
					errorMsgs.put("district","  --�п�ܰϰ�--");
					errorMsgs.put("gpc_Schedule", "�п�ܽҵ{�ɶ�");
					req.setAttribute("gpcVO", gpcVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
							req.getServletPath().contains("front-end")? 
								"/front-end/KaiPing/gpc/newGpc.jsp": 
								"/back-end/KaiPing/gpc/b_newGpc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
								
				/*****2.�}�l�s�W���*****/
				//�⿤���ϰ�l���ϸ��a�}�걵������a�}
				String integratedAddr = county + district + zipcode + address;
				//����o�̪�ܵL���~�o�͡A�e�J����a�}
				gpcVO.setAddress(integratedAddr);
				TreeMap<String, String> resultMap = formator.getResultMap();

				gpcVO = gpcSvc.insertWithGpcSch(gpcVO, resultMap);	
										
				/******3.�s�W����,�ǳ����(Send the Success view)******/
				Map<String,String> myGpcStates = new LinkedHashMap<>();
								
				//�d�ߦ��нm���}�ߦh�ֵ�GPC
				getCoachGpcs(coach_Id , myGpcStates);

				/******4.�ǳ����(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/KaiPing/gpc/MyGpc_Coach.jsp"); 
				successView.forward(req, res);
								
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/newGpc.jsp": 
							"/back-end/KaiPing/gpc/b_newGpc.jsp"
				);
				failureView.forward(req, res);
			}					
		}
		
		//�нm��F�@��GPC�n�ק�
		if("getOne_For_Update".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*****1.�����ШD�Ѽ�*****/
				String gpc_Id = new String(req.getParameter("gpc_Id"));
				
				/*****2.�}�l�d�߸��*****/

				GpcVO gpcVO = gpcSvc.getOneGpc(gpc_Id);
								
				/*****3.�d�ߧ���,�ǳ����(Send the Success view)*****/
				req.setAttribute("gpcVO", gpcVO);  
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/editGpc.jsp":
							"/back-end/KaiPing/gpc/b_editGpc.jsp"
				);
				failureView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					req.getServletPath()
				);				
				failureView.forward(req, res);
			}
		}
			
		
		//�ק�GPC��W��
		if("update".equals(action) || "update_front".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id");// PK�A����ק���O�n���
				String exp_Id =req.getParameter("exp_Id");//�o���Ҫ����O�A����ק���O�n���

							
				//���νҵ{�W�� gpc_Name & ����
				String gpc_Name = req.getParameter("gpc_Name");
				verifyGpc_Name(errorMsgs, gpc_Name);
				
				//�a�I  address & ����
				String address = req.getParameter("address");
				verifyAddress(errorMsgs, address);
				
				//���e����intro & ����
				String intro = req.getParameter("intro");
				verifyIntro(errorMsgs, intro);
				
				//�Ϥ�,���~���ҥ�JS�b�e�x�����̰�(�w����)
				Part part1 = req.getPart("pic1");
				Part part2 = req.getPart("pic2");
				Part part3 = req.getPart("pic3");
				byte[] pic1 = getPicByteArr(gpc_Id, part1, "pic1");
				byte[] pic2 = getPicByteArr(gpc_Id, part2, "pic2");
				byte[] pic3 = getPicByteArr(gpc_Id, part3, "pic3");
				
				//�O��price
				String priceParam = req.getParameter("price");
				Integer price = verifyPrice(errorMsgs, priceParam);
				
				//���W�I����(��ú�O�}�l���) pay_Start
				String pay_StartParam = req.getParameter("pay_Start");
				java.sql.Date pay_Start =  verifyDate(errorMsgs, pay_StartParam);
				
//				//�����W�Ҥ�� gpc_Start,�o�O�ª������
//				String gpc_StartParam = req.getParameter("gpc_Start");
//				java.sql.Date gpc_Start = verifyDate(errorMsgs, gpc_StartParam);
				
				//�H�ƤU��
				String mem_MinParam = req.getParameter("mem_Min");
				Flag isMinValid = new Flag();
				Integer mem_Min = verifyNum(errorMsgs, isMinValid, mem_MinParam, "mem_Min");
				
				//�H�ƤW��
				String mem_MaxParam = req.getParameter("mem_Max");
				Flag isMaxValid = new Flag();
				Integer mem_Max = verifyNum(errorMsgs, isMaxValid, mem_MaxParam, "mem_Max");
				
				//�H�ƤW�U���P�_�A���J�榡�L�~�A�A�h�P�_�H�ƤW���O�_�j��U��
				verifyTwoFlag(errorMsgs, isMinValid, isMaxValid, mem_Min, mem_Max);

				GpcVO gpcVO = new GpcVO();
				
				gpcVO.setGpc_Id(gpc_Id);
				gpcVO.setExp_Id(exp_Id);//���F�o�Ϳ��~�ɪ�^�����n��ܡA�ҥH�o�̭n����
				gpcVO.setGpc_Name(gpc_Name);
				gpcVO.setAddress(address);
				gpcVO.setIntro(intro);
				gpcVO.setPic1(pic1);
				gpcVO.setPic2(pic2);
				gpcVO.setPic3(pic3);
				gpcVO.setPrice(price);
				gpcVO.setPay_Start(pay_Start);
				gpcVO.setMem_Min(mem_Min);
				gpcVO.setMem_Max(mem_Max);
			
				
				if (!errorMsgs.isEmpty()) {
					errorMsgs.put("rePick","  --�Э��s��ܹϤ�--");
					req.setAttribute("gpcVO", gpcVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
							req.getServletPath().contains("front-end")? 
								"/front-end/KaiPing/gpc/editGpc.jsp": 
								"/back-end/KaiPing/gpc/b_editGpc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
				
				
				
				/*****2.�}�l�ק���*****/
	
				gpcVO = gpcSvc.updateGpc(gpc_Id, //PK //exp_ID�O���F�o�Ϳ��~�ɪ�^�����n��ܡA���O���ζi�JDB�ק�
					gpc_Name, address, intro, pic1, pic2, pic3, price, pay_Start, mem_Min, mem_Max);
				
				
				
				/******3.�ק粒��,�ǳ����(Send the Success view)******/
				//��O���y�{
				if("update".equals(action)) {
					req.setAttribute("gpcVO",gpcVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/KaiPing/gpc/b_listAllGpc.jsp"); 
					successView.forward(req, res);
				} 
				// �e�x���y�{
				else {
					Map<String,String> myGpcStates = new LinkedHashMap<>();
					String coach_Id = req.getParameter("coach_Id").trim(); // �e���i�J�n�ק�GPC���нm��ID�Atrim�u�O���b
					
					//�d�ߦ��нm���}�ߦh�ֵ�GPC
					getCoachGpcs(coach_Id , myGpcStates);

					/******4.�ǳ����(Send the Success view)******/
					req.setAttribute("myGpcStates",myGpcStates);
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/KaiPing/gpc/MyGpc_Coach.jsp"); 
					successView.forward(req, res);
					
				}
				
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/editGpc.jsp": 
							"/back-end/KaiPing/gpc/b_editGpc.jsp"
				);
				failureView.forward(req, res);
				System.out.println("BBBBB");
			}					
				
		}
		
		//��x���ӹΪ��A
		if("update_GpcState".equals(action)) {
			
			//�u�O�窱�A�S�����~���һݭn��,�o�u�O��ܨ�L���~�T����
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id");// PK
				String gpc_StateParam =req.getParameter("gpc_State");//�Ӫ��Ҫ��A
				Integer gpc_State = new Integer(gpc_StateParam.trim());//�o��trim�u�O���b
				
				/*****2.�}�l�ק���*****/
				GpcVO gpcVO = gpcSvc.updateGpcState(gpc_Id, gpc_State);
								
				/******3.�ק粒��,�ǳ����(Send the Success view)******/
				req.setAttribute("gpcVO",gpcVO);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/listAllGpc.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}					
				
		}
		
		//�|���d�ߦۤv������
		if("getMyGpcs_Mem".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String member_Id = req.getParameter("member_Id").trim(); // ��U�d�ݭ������|��ID�Atrim�u�O���b
							
				/*****2.�}�l�d�߸��*****/
				getMemGpcs(member_Id, myGpcStates);
											
				/******3.�ק粒��,�ǳ����(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		}
		
		
		//�|�������ӵ�����
		if("cancelThisGpc_Mem".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id_Canceled = req.getParameter("gpc_Id_Canceled").trim();  // ��U�n������gpc_ID�Atrim�u�O���b
				String member_Id = req.getParameter("member_Id").trim();     // ��U�d�ݭ������|��ID�Atrim�u�O���b
				
				
				/*****2.�}�l�ק���*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.updateGpc_LsitState(gpc_Id_Canceled, member_Id, -4);   // �o�̥��w�]��-4
				
				
				/*****3.�ק粒����d�߸��*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.���������(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		}
		
		//�|���ѥ[�Ӧ�����
		if("joinGpc".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id").trim();  // �n�[�J��gpc_ID�Atrim�u�O���b
				String member_Id = req.getParameter("member_Id").trim();     // �n�[�J���|��ID�Atrim�u�O���b
				
				
				/*****2.�}�l�ק���*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.addGpc_List(gpc_Id, member_Id);   // 
				
				
				/*****3.�ק粒����d�߸��*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.���������(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				req.setAttribute("errorMsgs", errorMsgs);
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		}
		
		//�|�������ӵ�����
		if("payGpc".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String gpc_Id = req.getParameter("gpc_Id").trim();  // ��U�n������gpc_ID�Atrim�u�O���b
				String member_Id = req.getParameter("member_Id").trim();     // ��U�d�ݭ������|��ID�Atrim�u�O���b
				
				
				/*****2.�}�l�ק���*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.updateGpc_LsitState(gpc_Id, member_Id, 2);   // �I�ڧ��������A�O2
				
				
				/*****3.�ק粒����d�߸��*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.���������(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		}
		
		
		//�нm�d�ߦۤv�����ΦC��
		if("getMyGpcs_Coach".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();
			
			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String coach_Id = req.getParameter("coach_Id").trim(); // ��U�d�ݭ������нmID�Atrim�u�O���b
				
				
				/*****2.�}�l�d�߸��*****/
				
				//�d�ߦ��нm���}�ߦh�ֵ�GPC
				getCoachGpcs(coach_Id , myGpcStates);

				
				/******3.�ק粒��,�ǳ����(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Coach.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		}
		
		//�нm�����ӵ�����
		if("cancelThisGpc_Coach".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.�����ШD�Ѽ� - ��J�榡�����~�B�z*****/
				String coach_Id = req.getParameter("coach_Id").trim(); // ��U�d�ݭ������нmID�Atrim�u�O���b
				String gpc_Id_Canceled = req.getParameter("gpc_Id_Canceled").trim();  // ��U�n������gpc_ID�Atrim�u�O���b
				
				/*****2.�ק�@��GPC*****/		
				gpcSvc.updateGpcState(gpc_Id_Canceled, /*PK*/ -2); //�ثe����-2 (�Ԩ�Table_Spec)
		
				/*****3.�d�߭ק�᪺�Ҧ����*****/				
				//�d�ߦ��нm���}�ߦh�ֵ�GPC
				getCoachGpcs(coach_Id , myGpcStates);
				
							
				/******4.�ǳ����(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Coach.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******��L�i�઺���~�B�z******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/listAllGpc.jsp": 
							"/back-end/KaiPing/gpc/back-gpc.jsp"
				);
				failureView.forward(req, res);
			}
			
		} //if-cancelThisGpc_Coach-end
		
						
	} // doPost-end
	

	
	//���W�ǹϤ��Ϊ�
	public static byte[] getPicByteArr(String gpc_Id, Part part, String param) throws IOException, ServletException {
		GpcService gpcSvc = new GpcService();
		byte[] picArr = null;
		InputStream in = part.getInputStream();		
		if(in.available()!=0) {
			picArr = new byte[in.available()];
			in.read(picArr);
			in.close();
			return picArr;
			
		} else {
			switch(param) {
				case "pic1":				
					picArr = gpc_Id == null? null: gpcSvc.getOneGpc(gpc_Id).getPic1();
					break;
				case "pic2":
					picArr = gpc_Id == null? null: gpcSvc.getOneGpc(gpc_Id).getPic2();
					break;
				case "pic3":
					picArr = gpc_Id == null? null: gpcSvc.getOneGpc(gpc_Id).getPic3();
					break;
				default:
					break;
			}
			return picArr;
		}		
	}
	
	//�ҵ{�W������
	public static void verifyGpc_Name(Map<String,String> errorMsgs, String gpc_Name){
		if(gpc_Name == null || gpc_Name.trim().length() == 0) {
			errorMsgs.put("gpc_Name","  --���νҵ{�W�ٽФŪť�!--");
		} else if(gpc_Name.trim().length() > 100) {
			errorMsgs.put("gpc_Name","  --���νҵ{�W�ٽжW�L100�r!--");
		}

	}
	
	//�W�Ҧa�I����
	public static void verifyAddress(Map<String,String> errorMsgs, String address){
		if(address == null || address.trim().length() == 0) {
			errorMsgs.put("address","  --�a�I�ФŪť�!--");
		} else if(address.trim().length() > 100) {
			errorMsgs.put("address","  --�a�I�ФŶW�L100�r!--");
		}

	}
	
	//���e����
	public static void verifyIntro(Map<String,String> errorMsgs, String intro){
		if(intro == null || intro.trim().length() == 0) {
			errorMsgs.put("intro","  --���e���нФŪť�!--");
		} else if(intro.trim().length() > 500) {
			errorMsgs.put("intro","  --�a�I�ФŶW�L500�r!--");
		}
		
	}
	
	//�O������
	public static Integer verifyPrice(Map<String,String> errorMsgs, String priceParam){
		
		Integer price = null;
		
		try {
			if(priceParam.trim().length() !=0) {
				price = new Integer(priceParam.trim());
				if(price <= 0){errorMsgs.put("price", "  --�п�J�����!--");}
				else if(price > 9999999) {errorMsgs.put("price", "  --�ӶQ��~--");}	
			}else {
				errorMsgs.put("price","  --�O�νФŪť�!--");
			}

		} catch (NumberFormatException e) {
			errorMsgs.put("price","  --�O�νж�Ʀr!--");
		}	
		return price;
	}
	
	//�������
	public static java.sql.Date verifyDate(Map<String,String> errorMsgs, String dateParam){
		
		java.sql.Date date = null;
		
		try {
			date = java.sql.Date.valueOf(dateParam.trim());
		} catch (IllegalArgumentException e) {
			date =new java.sql.Date(System.currentTimeMillis());
			errorMsgs.put("pay_Start", "  --�п�J���!--");
		}
		
		return date;
	}
	
	//�H������
	public static Integer verifyNum(
		Map<String,String> errorMsgs, Flag flag, String numParam, String type){
		
		Integer num = null;
		
		try {
			if(numParam.trim().length() !=0) {
				num = new Integer(numParam.trim());
				if(num <= 0){
					errorMsgs.put(type,"  --�п�J�����!--");
				} else if(num > 9999999) {
					errorMsgs.put(type, "  --�H�ƤӦh��--");
				} else {
					flag.isValid = true;
				}	
			}else {
				errorMsgs.put(type,"  --�H�ƽФŪť�!--");
			}	
		} catch (NumberFormatException e) {
			errorMsgs.put(type,"  --�ж�Ʀr!--");
		}
		
		return num;
	}
	
	//���ҤW�U���O�_�X�z
	public static void verifyTwoFlag(
			Map<String,String> errorMsgs, Flag minFlag, Flag maxFlag ,Integer min, Integer max){
		if(minFlag.isValid && maxFlag.isValid) {
			if(!(max > min)) {
				errorMsgs.put("mem_Min","  --�нT�{�ƭȡA�U�����p��W��--");
				errorMsgs.put("mem_Max","  --�нT�{�ƭȡA�W�����j��U��--");
			}
		}	
	}
	
	//�d�ߥB�p��нm���ҵ{�̪��A
	public void getCoachGpcs(String coach_Id , Map<String,String> myGpcStates) {
		
		GpcService gpcSvc = new GpcService();
		Gpc_ListService gpc_ListSvc = new Gpc_ListService();
		List<GpcVO> gpcs = gpcSvc.getAll();  
		for(GpcVO gpc : gpcs) {				
			if (coach_Id.equals(gpc.getCoach_Id())) { // �ӱнm�}�ߪ�GPC
				int paid = 0;
				String gpc_Id = gpc.getGpc_Id();
				
				switch(gpc.getGpc_State()) {
					case 0:
						myGpcStates.put(gpc_Id,"�f�֤�");
						break;
					case 1:
						myGpcStates.put(gpc_Id,"���Τ�");
						break;
					case 2:
						myGpcStates.put(gpc_Id,"�f�֥��q�L");
						break;
					default:
						myGpcStates.put(gpc_Id,"�z�w�����ӽҵ{");
				}
				
				
				//�f�ֳq�L��~�p��H��
				if(gpc.getGpc_State() == 1) {
					//�p��ӵ����ΡAú�O���������X�H
					List<Gpc_ListVO> people = gpc_ListSvc.getGpc_Lists(gpc_Id);
					for(Gpc_ListVO person : people) {
						if(person.getMem_State()==2) { // ú�O�������H���A == 2
							paid++;
						}						
					}
					
					//�ѥ[�H�� >= �H�ƤU���~�}��
					if (paid >= gpcSvc.getOneGpc(gpc_Id).getMem_Min()) {
						myGpcStates.put(gpc_Id,"�w����");
					}
					
				}		
			} // if-end							
		} // for-end		
	}
	
	
	//�d�ߥB�p��|���ѥ[���ҵ{�̪��A
	public void getMemGpcs(String member_Id, Map<String,String> myGpcStates) {
		GpcService gpcSvc = new GpcService();
		Gpc_ListService gpc_ListSvc = new Gpc_ListService();
		
		//���d�ߦ��|�����ѥ[�h�ֵ�GPC
		List<Gpc_ListVO> gpcs = gpc_ListSvc.getGpc_Lists(member_Id);
		for(Gpc_ListVO gpc : gpcs) {
			int paid = 0;
			String gpc_Id = gpc.getGpc_Id();
			
			//�p��ӵ����ΡAú�O���������X�H
			List<Gpc_ListVO> people = gpc_ListSvc.getGpc_Lists(gpc_Id);
			for(Gpc_ListVO person : people) {
				if(person.getMem_State()==2) { // ú�O�������H���A == 2
					paid++;
				}						
			}
			//System.out.println(paid);
			
			//�ѥ[�H�� >= �H�ƤU���~�}��
			if (paid >= gpcSvc.getOneGpc(gpc_Id).getMem_Min()) {
				myGpcStates.put(gpc_Id,"�w����");
			}else {
				myGpcStates.put(gpc_Id,"�|������");
			}
								
		}
		
	}
	

	
}

class Flag{
	public boolean isValid = false;
}