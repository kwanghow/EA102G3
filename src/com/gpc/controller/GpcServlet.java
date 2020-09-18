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
		
		//查一個
		if("getOneGPC".equals(action)){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id");
				String gpc_IdReg = "[G][P][C][0-9]{3}";
	
				GpcVO gpcVO;
				
//這裡的巢狀結構還沒優化，先不管了
///////////////////////////////////////////////////////////////
				if(gpc_Id != null && gpc_Id.trim().length() != 0) {
					if(gpc_Id.trim().matches(gpc_IdReg)) {
						gpcSvc = new GpcService();
						gpcVO = gpcSvc.getOneGpc(gpc_Id);
						if(gpcVO != null) {
							req.setAttribute("gpcVO", gpcVO);
							RequestDispatcher successView = req.getRequestDispatcher(
								req.getServletPath().contains("front-end")?
									"": // 前台未完成
									"/back-end/KaiPing/gpc/b_listOneGpc.jsp"
							); 
							successView.forward(req, res);
							return;
						}else {
							errorMsgs.put("gpc_Id","查無資料");
						}				
					}else {
						errorMsgs.put("gpc_Id","應為GPCXXX");
					}					
				} else {
					errorMsgs.put("gpc_Id","請勿空白");
				}
				
				
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")?
							"": // 前台未完成
							"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
					); 
				failureView.forward(req, res);
////////////////////////////////////////////////////				
								
				/*******其他可能的錯誤處理******/
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")?
							"": // 前台未完成
							"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
					); 
				failureView.forward(req, res);
			}		
 		}

		//教練點擊新增課程所啟動的action
		if("findCoachTime".contentEquals(action)) {
			CoachVO coachVO = (CoachVO) req.getSession().getAttribute("coachLogIn");
			RequestDispatcher resultView = null;
			try {
				//防呆用的if
				if(coachVO==null) {
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/newGpc.jsp");
				}else {
					String coachId= coachVO.getCoach_Id();
					Gpc_CoachViewService cViewSvc = new Gpc_CoachViewService();
					// 收集Gpc_CoachViewVO
					List<Gpc_CoachViewVO> coachViewList = cViewSvc.findPeriod_3MM(coachId); 
					// 用來裝整理後的Key-Value
					Map<String, String> coachViewMap = new TreeMap(); 
					BtoF_TimeStrMerger timeMerger = new BtoF_TimeStrMerger();
					// 將不同天的資料整合成一天
					for(int i =0; i < coachViewList.size(); i++) {
						String keyStr = coachViewList.get(i).getView_Date().toString();
						String timeStr = coachViewList.get(i).getView_Time();
						
						//若Map內有已有key,整合時間
						if(coachViewMap.containsKey(keyStr)) {
							String newStr = timeMerger.getMergeStr(timeStr, coachViewMap.get(keyStr));
							if(!timeMerger.validate(newStr)) {
								System.out.println("資料庫內時間發生衝突，請檢查");
								throw new Exception();
							} else {
								coachViewMap.put(keyStr, newStr);
							}
							
						//沒有的話新增
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
		
		//點擊進入單一團課頁啟動的action
		if("getGpcTime".contentEquals(action)) {
			String gpc_Id = req.getParameter("gpc_Id"); // 這裡是null,不過為了跟update比照所以還是這樣寫
			
			String member_Id = req.getParameter("member_Id");
			RequestDispatcher resultView = null;
			try {
				//防呆用的if
				if(member_Id==null) {
					resultView = req.getRequestDispatcher("/front-end/KaiPing/gpc/SingleGpc.jsp");
				}else {
										
					Gpc_ScheduleService gpcSchSvc = new Gpc_ScheduleService();
					// List裝Gpc_ScheduleVO
					List<Gpc_ScheduleVO> gpcSchList = gpcSchSvc.getOneGpc_Schedule(gpc_Id); 
					// Map裝整理後的Key-Value
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
		
		
		//教練新增課程
		if("insert".equals(action)) { // 來自newGpc.jsp & b_newGpc.jsp
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id"); // 這裡是null,不過為了跟update比照所以還是這樣寫
				
				String coach_Id = req.getParameter("coach_Id").trim(); //trim只是防呆
				String exp_Id = req.getParameter("exp_Id").trim();//trim只是防呆
					
				//揪團課程名稱 gpc_Name & 驗證
				String gpc_Name = req.getParameter("gpc_Name");
				verifyGpc_Name(errorMsgs, gpc_Name);
				
				//縣市
				String county = null;
				if(req.getParameter("county") == null) {
					errorMsgs.put("county","  --請選擇縣市--");
				}else {
					county = req.getParameter("county");
					System.out.println(county);
				}
				
				//區域 & 郵遞區號 (郵遞區號會跟著區域產生)
				String district = null;
				String zipcode = null;
				if(req.getParameter("district") == null) {
					errorMsgs.put("district","  --請選擇區域--");
				}else {
					district = req.getParameter("district");
					zipcode = req.getParameter("zipcode");
					System.out.println(district);
					System.out.println(zipcode);
				}
											
				//地點  address & 驗證
				String address = req.getParameter("address");
				verifyAddress(errorMsgs, address);
				
				//內容介紹intro & 驗證
				String intro = req.getParameter("intro");
				verifyIntro(errorMsgs, intro);

				//圖片,錯誤驗證用JS在前台頁面裡做(已完成)
				Part part1 = req.getPart("pic1");
				Part part2 = req.getPart("pic2");
				Part part3 = req.getPart("pic3");
				byte[] pic1 = getPicByteArr(gpc_Id, part1, "pic1");
				byte[] pic2 = getPicByteArr(gpc_Id, part2, "pic2");
				byte[] pic3 = getPicByteArr(gpc_Id, part3, "pic3");
			
				
				//費用price 				 
				String priceParam = req.getParameter("price");
				Integer price = verifyPrice(errorMsgs, priceParam);
						
				//報名截止日期(原繳費開始日期) pay_Start				
				String pay_StartParam = req.getParameter("pay_Start");
				java.sql.Date pay_Start =  verifyDate(errorMsgs, pay_StartParam);
				
/**************************************************************************************/
				//首次上課日期 gpc_Start(要由一群時間們拿出)	
				java.sql.Date gpc_Start = null;
				
				
				
				//先取得要放入gpc_Schedule裡面的前端值
				String[] schFrontArr = null;
				FtoB_TimeFormator formator = null;
				if(req.getParameterValues("gpc_sch_front") == null) {
					errorMsgs.put("gpc_Schedule", "請選擇課程時間");
				} else {
					schFrontArr = req.getParameterValues("gpc_sch_front");
					//除錯用
//					for(int i= 0; i <schFrontArr.length; i ++) {
//						System.out.println(schFrontArr[i]);
//					}
					
					//轉換格式
					formator = new FtoB_TimeFormator(schFrontArr);
					
					//由一群日期字串們拿出上課第一天字串			
					String gpc_StartStr = formator.getFirstDateStr();
					//轉回日期
					gpc_Start = verifyDate(errorMsgs, gpc_StartStr);
					
				}
												
/**************************************************************************************/
								
				//人數下限				 
				String mem_MinParam = req.getParameter("mem_Min");
				Flag isMinValid = new Flag();
				Integer mem_Min = verifyNum(errorMsgs, isMinValid, mem_MinParam, "mem_Min");
							
				//人數上限				 
				String mem_MaxParam = req.getParameter("mem_Max");
				Flag isMaxValid = new Flag();
				Integer mem_Max = verifyNum(errorMsgs, isMaxValid, mem_MaxParam, "mem_Max");
				
				//人數上下限判斷，當輸入格式無誤，再去判斷人數上限是否大於下限
				verifyTwoFlag(errorMsgs, isMinValid, isMaxValid, mem_Min, mem_Max);
							
				//送回前端塞值
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
					errorMsgs.put("rePick","  --請重新選擇圖片--"); // 為了提醒使用者圖片要重新選
					errorMsgs.put("county","  --請選擇縣市--");
					errorMsgs.put("district","  --請選擇區域--");
					errorMsgs.put("gpc_Schedule", "請選擇課程時間");
					req.setAttribute("gpcVO", gpcVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
							req.getServletPath().contains("front-end")? 
								"/front-end/KaiPing/gpc/newGpc.jsp": 
								"/back-end/KaiPing/gpc/b_newGpc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
								
				/*****2.開始新增資料*****/
				//把縣市區域郵遞區號地址串接成完整地址
				String integratedAddr = county + district + zipcode + address;
				//走到這裡表示無錯誤發生，送入完整地址
				gpcVO.setAddress(integratedAddr);
				TreeMap<String, String> resultMap = formator.getResultMap();

				gpcVO = gpcSvc.insertWithGpcSch(gpcVO, resultMap);	
										
				/******3.新增完成,準備轉交(Send the Success view)******/
				Map<String,String> myGpcStates = new LinkedHashMap<>();
								
				//查詢此教練有開立多少筆GPC
				getCoachGpcs(coach_Id , myGpcStates);

				/******4.準備轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/KaiPing/gpc/MyGpc_Coach.jsp"); 
				successView.forward(req, res);
								
				/*******其他可能的錯誤處理******/
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
		
		//教練選了一筆GPC要修改
		if("getOne_For_Update".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*****1.接收請求參數*****/
				String gpc_Id = new String(req.getParameter("gpc_Id"));
				
				/*****2.開始查詢資料*****/

				GpcVO gpcVO = gpcSvc.getOneGpc(gpc_Id);
								
				/*****3.查詢完成,準備轉交(Send the Success view)*****/
				req.setAttribute("gpcVO", gpcVO);  
				RequestDispatcher failureView = req.getRequestDispatcher(
						req.getServletPath().contains("front-end")? 
							"/front-end/KaiPing/gpc/editGpc.jsp":
							"/back-end/KaiPing/gpc/b_editGpc.jsp"
				);
				failureView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					req.getServletPath()
				);				
				failureView.forward(req, res);
			}
		}
			
		
		//修改GPC後上傳
		if("update".equals(action) || "update_front".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id");// PK，不能修改但是要顯示
				String exp_Id =req.getParameter("exp_Id");//這門課的類別，不能修改但是要顯示

							
				//揪團課程名稱 gpc_Name & 驗證
				String gpc_Name = req.getParameter("gpc_Name");
				verifyGpc_Name(errorMsgs, gpc_Name);
				
				//地點  address & 驗證
				String address = req.getParameter("address");
				verifyAddress(errorMsgs, address);
				
				//內容介紹intro & 驗證
				String intro = req.getParameter("intro");
				verifyIntro(errorMsgs, intro);
				
				//圖片,錯誤驗證用JS在前台頁面裡做(已完成)
				Part part1 = req.getPart("pic1");
				Part part2 = req.getPart("pic2");
				Part part3 = req.getPart("pic3");
				byte[] pic1 = getPicByteArr(gpc_Id, part1, "pic1");
				byte[] pic2 = getPicByteArr(gpc_Id, part2, "pic2");
				byte[] pic3 = getPicByteArr(gpc_Id, part3, "pic3");
				
				//費用price
				String priceParam = req.getParameter("price");
				Integer price = verifyPrice(errorMsgs, priceParam);
				
				//報名截止日期(原繳費開始日期) pay_Start
				String pay_StartParam = req.getParameter("pay_Start");
				java.sql.Date pay_Start =  verifyDate(errorMsgs, pay_StartParam);
				
//				//首次上課日期 gpc_Start,這是舊的不能用
//				String gpc_StartParam = req.getParameter("gpc_Start");
//				java.sql.Date gpc_Start = verifyDate(errorMsgs, gpc_StartParam);
				
				//人數下限
				String mem_MinParam = req.getParameter("mem_Min");
				Flag isMinValid = new Flag();
				Integer mem_Min = verifyNum(errorMsgs, isMinValid, mem_MinParam, "mem_Min");
				
				//人數上限
				String mem_MaxParam = req.getParameter("mem_Max");
				Flag isMaxValid = new Flag();
				Integer mem_Max = verifyNum(errorMsgs, isMaxValid, mem_MaxParam, "mem_Max");
				
				//人數上下限判斷，當輸入格式無誤，再去判斷人數上限是否大於下限
				verifyTwoFlag(errorMsgs, isMinValid, isMaxValid, mem_Min, mem_Max);

				GpcVO gpcVO = new GpcVO();
				
				gpcVO.setGpc_Id(gpc_Id);
				gpcVO.setExp_Id(exp_Id);//為了發生錯誤時返回頁面要顯示，所以這裡要有值
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
					errorMsgs.put("rePick","  --請重新選擇圖片--");
					req.setAttribute("gpcVO", gpcVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
							req.getServletPath().contains("front-end")? 
								"/front-end/KaiPing/gpc/editGpc.jsp": 
								"/back-end/KaiPing/gpc/b_editGpc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
				
				
				
				/*****2.開始修改資料*****/
	
				gpcVO = gpcSvc.updateGpc(gpc_Id, //PK //exp_ID是為了發生錯誤時返回頁面要顯示，但是不用進入DB修改
					gpc_Name, address, intro, pic1, pic2, pic3, price, pay_Start, mem_Min, mem_Max);
				
				
				
				/******3.修改完成,準備轉交(Send the Success view)******/
				//後臺轉交流程
				if("update".equals(action)) {
					req.setAttribute("gpcVO",gpcVO);
					RequestDispatcher successView = req.getRequestDispatcher("/back-end/KaiPing/gpc/b_listAllGpc.jsp"); 
					successView.forward(req, res);
				} 
				// 前台轉交流程
				else {
					Map<String,String> myGpcStates = new LinkedHashMap<>();
					String coach_Id = req.getParameter("coach_Id").trim(); // 前湍進入要修改GPC的教練之ID，trim只是防呆
					
					//查詢此教練有開立多少筆GPC
					getCoachGpcs(coach_Id , myGpcStates);

					/******4.準備轉交(Send the Success view)******/
					req.setAttribute("myGpcStates",myGpcStates);
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/KaiPing/gpc/MyGpc_Coach.jsp"); 
					successView.forward(req, res);
					
				}
				
				
				/*******其他可能的錯誤處理******/
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
		
		//後台更改該團狀態
		if("update_GpcState".equals(action)) {
			
			//只是改狀態沒有錯誤驗證需要做,這只是顯示其他錯誤訊息用
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id");// PK
				String gpc_StateParam =req.getParameter("gpc_State");//該門課狀態
				Integer gpc_State = new Integer(gpc_StateParam.trim());//這裡trim只是防呆
				
				/*****2.開始修改資料*****/
				GpcVO gpcVO = gpcSvc.updateGpcState(gpc_Id, gpc_State);
								
				/******3.修改完成,準備轉交(Send the Success view)******/
				req.setAttribute("gpcVO",gpcVO);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/listAllGpc.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		//會員查詢自己的揪團
		if("getMyGpcs_Mem".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String member_Id = req.getParameter("member_Id").trim(); // 當下查看頁面的會員ID，trim只是防呆
							
				/*****2.開始查詢資料*****/
				getMemGpcs(member_Id, myGpcStates);
											
				/******3.修改完成,準備轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		
		//會員取消該筆揪團
		if("cancelThisGpc_Mem".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id_Canceled = req.getParameter("gpc_Id_Canceled").trim();  // 當下要取消的gpc_ID，trim只是防呆
				String member_Id = req.getParameter("member_Id").trim();     // 當下查看頁面的會員ID，trim只是防呆
				
				
				/*****2.開始修改資料*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.updateGpc_LsitState(gpc_Id_Canceled, member_Id, -4);   // 這裡先預設為-4
				
				
				/*****3.修改完成後查詢資料*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.完成後轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		//會員參加該次揪團
		if("joinGpc".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id").trim();  // 要加入的gpc_ID，trim只是防呆
				String member_Id = req.getParameter("member_Id").trim();     // 要加入的會員ID，trim只是防呆
				
				
				/*****2.開始修改資料*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.addGpc_List(gpc_Id, member_Id);   // 
				
				
				/*****3.修改完成後查詢資料*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.完成後轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				req.setAttribute("errorMsgs", errorMsgs);
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		//會員取消該筆揪團
		if("payGpc".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Id = req.getParameter("gpc_Id").trim();  // 當下要取消的gpc_ID，trim只是防呆
				String member_Id = req.getParameter("member_Id").trim();     // 當下查看頁面的會員ID，trim只是防呆
				
				
				/*****2.開始修改資料*****/
				Gpc_ListService gpc_ListSvc = new Gpc_ListService();
				gpc_ListSvc.updateGpc_LsitState(gpc_Id, member_Id, 2);   // 付款完成的狀態是2
				
				
				/*****3.修改完成後查詢資料*****/
				getMemGpcs(member_Id, myGpcStates);
				
							
				/******4.完成後轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Mem.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		
		//教練查詢自己的揪團列表
		if("getMyGpcs_Coach".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();
			
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String coach_Id = req.getParameter("coach_Id").trim(); // 當下查看頁面的教練ID，trim只是防呆
				
				
				/*****2.開始查詢資料*****/
				
				//查詢此教練有開立多少筆GPC
				getCoachGpcs(coach_Id , myGpcStates);

				
				/******3.修改完成,準備轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Coach.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
		
		//教練取消該筆揪團
		if("cancelThisGpc_Coach".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			Map<String,String> myGpcStates = new LinkedHashMap<>();

			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String coach_Id = req.getParameter("coach_Id").trim(); // 當下查看頁面的教練ID，trim只是防呆
				String gpc_Id_Canceled = req.getParameter("gpc_Id_Canceled").trim();  // 當下要取消的gpc_ID，trim只是防呆
				
				/*****2.修改一筆GPC*****/		
				gpcSvc.updateGpcState(gpc_Id_Canceled, /*PK*/ -2); //目前先塞-2 (詳見Table_Spec)
		
				/*****3.查詢修改後的所有資料*****/				
				//查詢此教練有開立多少筆GPC
				getCoachGpcs(coach_Id , myGpcStates);
				
							
				/******4.準備轉交(Send the Success view)******/
				req.setAttribute("myGpcStates",myGpcStates);
				RequestDispatcher successView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")? 
						"/front-end/KaiPing/gpc/MyGpc_Coach.jsp": 
						"/back-end/KaiPing/gpc/back-gpc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
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
	

	
	//給上傳圖片用的
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
	
	//課程名稱驗證
	public static void verifyGpc_Name(Map<String,String> errorMsgs, String gpc_Name){
		if(gpc_Name == null || gpc_Name.trim().length() == 0) {
			errorMsgs.put("gpc_Name","  --揪團課程名稱請勿空白!--");
		} else if(gpc_Name.trim().length() > 100) {
			errorMsgs.put("gpc_Name","  --揪團課程名稱請超過100字!--");
		}

	}
	
	//上課地點驗證
	public static void verifyAddress(Map<String,String> errorMsgs, String address){
		if(address == null || address.trim().length() == 0) {
			errorMsgs.put("address","  --地點請勿空白!--");
		} else if(address.trim().length() > 100) {
			errorMsgs.put("address","  --地點請勿超過100字!--");
		}

	}
	
	//內容驗證
	public static void verifyIntro(Map<String,String> errorMsgs, String intro){
		if(intro == null || intro.trim().length() == 0) {
			errorMsgs.put("intro","  --內容介紹請勿空白!--");
		} else if(intro.trim().length() > 500) {
			errorMsgs.put("intro","  --地點請勿超過500字!--");
		}
		
	}
	
	//費用驗證
	public static Integer verifyPrice(Map<String,String> errorMsgs, String priceParam){
		
		Integer price = null;
		
		try {
			if(priceParam.trim().length() !=0) {
				price = new Integer(priceParam.trim());
				if(price <= 0){errorMsgs.put("price", "  --請輸入正整數!--");}
				else if(price > 9999999) {errorMsgs.put("price", "  --太貴啦~--");}	
			}else {
				errorMsgs.put("price","  --費用請勿空白!--");
			}

		} catch (NumberFormatException e) {
			errorMsgs.put("price","  --費用請填數字!--");
		}	
		return price;
	}
	
	//日期驗證
	public static java.sql.Date verifyDate(Map<String,String> errorMsgs, String dateParam){
		
		java.sql.Date date = null;
		
		try {
			date = java.sql.Date.valueOf(dateParam.trim());
		} catch (IllegalArgumentException e) {
			date =new java.sql.Date(System.currentTimeMillis());
			errorMsgs.put("pay_Start", "  --請輸入日期!--");
		}
		
		return date;
	}
	
	//人數驗證
	public static Integer verifyNum(
		Map<String,String> errorMsgs, Flag flag, String numParam, String type){
		
		Integer num = null;
		
		try {
			if(numParam.trim().length() !=0) {
				num = new Integer(numParam.trim());
				if(num <= 0){
					errorMsgs.put(type,"  --請輸入正整數!--");
				} else if(num > 9999999) {
					errorMsgs.put(type, "  --人數太多啦--");
				} else {
					flag.isValid = true;
				}	
			}else {
				errorMsgs.put(type,"  --人數請勿空白!--");
			}	
		} catch (NumberFormatException e) {
			errorMsgs.put(type,"  --請填數字!--");
		}
		
		return num;
	}
	
	//驗證上下限是否合理
	public static void verifyTwoFlag(
			Map<String,String> errorMsgs, Flag minFlag, Flag maxFlag ,Integer min, Integer max){
		if(minFlag.isValid && maxFlag.isValid) {
			if(!(max > min)) {
				errorMsgs.put("mem_Min","  --請確認數值，下限應小於上限--");
				errorMsgs.put("mem_Max","  --請確認數值，上限應大於下限--");
			}
		}	
	}
	
	//查詢且計算教練的課程們狀態
	public void getCoachGpcs(String coach_Id , Map<String,String> myGpcStates) {
		
		GpcService gpcSvc = new GpcService();
		Gpc_ListService gpc_ListSvc = new Gpc_ListService();
		List<GpcVO> gpcs = gpcSvc.getAll();  
		for(GpcVO gpc : gpcs) {				
			if (coach_Id.equals(gpc.getCoach_Id())) { // 該教練開立的GPC
				int paid = 0;
				String gpc_Id = gpc.getGpc_Id();
				
				switch(gpc.getGpc_State()) {
					case 0:
						myGpcStates.put(gpc_Id,"審核中");
						break;
					case 1:
						myGpcStates.put(gpc_Id,"揪團中");
						break;
					case 2:
						myGpcStates.put(gpc_Id,"審核未通過");
						break;
					default:
						myGpcStates.put(gpc_Id,"您已取消該課程");
				}
				
				
				//審核通過後才計算人數
				if(gpc.getGpc_State() == 1) {
					//計算該筆揪團，繳費完成的有幾人
					List<Gpc_ListVO> people = gpc_ListSvc.getGpc_Lists(gpc_Id);
					for(Gpc_ListVO person : people) {
						if(person.getMem_State()==2) { // 繳費完成的人狀態 == 2
							paid++;
						}						
					}
					
					//參加人數 >= 人數下限才開團
					if (paid >= gpcSvc.getOneGpc(gpc_Id).getMem_Min()) {
						myGpcStates.put(gpc_Id,"已成團");
					}
					
				}		
			} // if-end							
		} // for-end		
	}
	
	
	//查詢且計算會員參加的課程們狀態
	public void getMemGpcs(String member_Id, Map<String,String> myGpcStates) {
		GpcService gpcSvc = new GpcService();
		Gpc_ListService gpc_ListSvc = new Gpc_ListService();
		
		//先查詢此會員有參加多少筆GPC
		List<Gpc_ListVO> gpcs = gpc_ListSvc.getGpc_Lists(member_Id);
		for(Gpc_ListVO gpc : gpcs) {
			int paid = 0;
			String gpc_Id = gpc.getGpc_Id();
			
			//計算該筆揪團，繳費完成的有幾人
			List<Gpc_ListVO> people = gpc_ListSvc.getGpc_Lists(gpc_Id);
			for(Gpc_ListVO person : people) {
				if(person.getMem_State()==2) { // 繳費完成的人狀態 == 2
					paid++;
				}						
			}
			//System.out.println(paid);
			
			//參加人數 >= 人數下限才開團
			if (paid >= gpcSvc.getOneGpc(gpc_Id).getMem_Min()) {
				myGpcStates.put(gpc_Id,"已成團");
			}else {
				myGpcStates.put(gpc_Id,"尚未成團");
			}
								
		}
		
	}
	

	
}

class Flag{
	public boolean isValid = false;
}