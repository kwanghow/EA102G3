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

		//查一個
		if("getOneGpc_Disc".equals(action)){
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				String gpc_Disc_Id = req.getParameter("gpc_Disc_Id");
				String gpc_Disc_IdReg = "[D][I][S][C][0-9]{3}";
				Gpc_DiscService gpc_DiscSvc;
				Gpc_DiscVO gpc_DiscVO;
				
//這裡的巢狀結構還沒優化，先不管了
///////////////////////////////////////////////////////////////
				if(gpc_Disc_Id != null && gpc_Disc_Id.trim().length() != 0) {
					if(gpc_Disc_Id.trim().matches(gpc_Disc_IdReg)) {
						gpc_DiscSvc = new Gpc_DiscService();
						gpc_DiscVO = gpc_DiscSvc.getOneGpc_Disc(gpc_Disc_Id);
						if(gpc_DiscVO != null) {
							req.setAttribute("gpc_DiscVO", gpc_DiscVO);
							RequestDispatcher successView = req.getRequestDispatcher(
								(req.getServletPath().contains("front-end"))? 
									"": // 這裡前端要轉交的還沒有生出來
									"/back-end/KaiPing/gpc/b_listOneGpc_Disc.jsp"
							); 
							successView.forward(req, res);
							return;
						}else {
							errorMsgs.put("gpc_Disc_Id","查無資料");
						}				
					}else {
						errorMsgs.put("gpc_Disc_Id","應為DISCXXX");
					}					
				} else {
					errorMsgs.put("gpc_Disc_Id","請勿空白");
				}
								
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
						"": // 這裡前端要轉交的還沒有生出來
						"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
				);
				failureView.forward(req, res);
////////////////////////////////////////////////////

				
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC_Disc", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
					"": // 這裡前端要轉交的還沒有生出來	
					"/back-end/KaiPing/gpc/b_gpc_select_page.jsp"
				);
				failureView.forward(req, res);
			}		
 		}
		
		//新增資料
		if("insert".equals(action)) { 
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				
				String gpc_Id = req.getParameter("gpc_Id"); //前台自動抓，後台下拉式選單
				
				String member_Id = req.getParameter("member_Id"); //前台自動抓，後台下拉式選單
				
				//提問內容 question & 驗證
				String question = req.getParameter("question");
				if(question == null || question.trim().length() == 0) {
					errorMsgs.put("question","  --提問請勿空白!--");
				} else if(question.trim().length() > 100) {
					errorMsgs.put("question","  --請超過100字!--");
				}
				
				//回應內容  answer & 驗證
				String answer = req.getParameter("answer");
				if(answer == null || answer.trim().length() == 0) {
					errorMsgs.put("answer","  --回答請勿空白!--");
				} else if(answer.trim().length() > 100) {
					errorMsgs.put("answer","  --地點請勿超過100字!--");
				}
				
				Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
				gpc_DiscVO.setGpc_Id(gpc_Id);
				gpc_DiscVO.setMember_Id(member_Id);
				gpc_DiscVO.setQuestion(question);
				gpc_DiscVO.setAnswer( answer);

				
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gpc_DiscVO", gpc_DiscVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))? // 前台還有分為教練新增或是學員新增
							"": //前台未完成
							"/back-end/KaiPing/gpc/b_newGpc_Disc.jsp"
					);
					failureView.forward(req, res);
					return;
				}
								
				/*****2.開始新增資料*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				gpc_DiscVO = gpc_DiscSvc.addGpc_Disc( gpc_Id, member_Id, question, answer );				
				
				/******3.新增完成,準備轉交(Send the Success view)******/
				RequestDispatcher successView = req.getRequestDispatcher( 
					(req.getServletPath().contains("front-end"))?  // 前台還有分為教練新增或是學員新增
						""://前台未完成
						"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				); 
				successView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
			} catch(Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					((req.getServletPath().contains("front-end"))?
						""://前台未完成 
						"/back-end/KaiPing/gpc/b_newGpc_Disc.jsp")
				);
				failureView.forward(req, res);
			}					
		}
		
		//有一筆修改要輸入
		if("getOne_For_Update".equals(action)) {

			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/*****1.接收請求參數*****/
				String gpc_Disc_Id = new String(req.getParameter("gpc_Disc_Id"));
				
				/*****2.開始查詢資料*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				Gpc_DiscVO gpc_DiscVO = gpc_DiscSvc.getOneGpc_Disc(gpc_Disc_Id);
								
				/*****3.查詢完成,準備轉交(Send the Success view)*****/
				req.setAttribute("gpc_DiscVO", gpc_DiscVO);
				RequestDispatcher successView = req.getRequestDispatcher( 
					req.getServletPath().contains("front-end")? 
						""://前台未完成
						"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
				);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("exception", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					req.getServletPath().contains("front-end")?
						""://前台未完成
						"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				);
				failureView.forward(req, res);
			}
		}
			
		
		//修改後上傳
		if("update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*****1.接收請求參數 - 輸入格式的錯誤處理*****/
				//抓留言ID
				String gpc_Disc_Id = req.getParameter("gpc_Disc_Id");
				
				//提問內容 question & 驗證
				String question = req.getParameter("question");
				if(question == null || question.trim().length() == 0) {
					errorMsgs.put("question","  --提問請勿空白!--");
				} else if(question.trim().length() > 100) {
					errorMsgs.put("question","  --請超過100字!--");
				}
				
				//回應內容  answer & 驗證
				String answer = req.getParameter("answer");
				if(answer == null || answer.trim().length() == 0) {
					errorMsgs.put("answer","  --回答請勿空白!--");
				} else if(answer.trim().length() > 100) {
					errorMsgs.put("answer","  --地點請勿超過100字!--");
				}
				
				//留言隱藏狀態 mute & 驗證
				Integer mute = new Integer(req.getParameter("mute").trim()); 
					// 其實是從選單來的，不用trim()，然後不用驗證
				
				Gpc_DiscVO gpc_DiscVO = new Gpc_DiscVO();
				
				gpc_DiscVO.setGpc_Disc_Id(gpc_Disc_Id);
				gpc_DiscVO.setQuestion(question);
				gpc_DiscVO.setAnswer(answer);
				gpc_DiscVO.setMute(mute);
			

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("gpc_DiscVO", gpc_DiscVO); 
					RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))? 
							"": //前台未完成
							"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
					);
					failureView.forward(req, res);
					return;
				}
				
				/*****2.開始修改資料*****/
				Gpc_DiscService gpc_DiscSvc = new Gpc_DiscService();
				//修改時要更新upl_Stamp
				gpc_DiscVO = gpc_DiscSvc.updateGpc_Disc(gpc_Disc_Id, question, answer, mute);
				
				
				/******3.修改完成,準備轉交(Send the Success view)******/
				RequestDispatcher failureView = req.getRequestDispatcher(
						(req.getServletPath().contains("front-end"))?
							"": // 這裡前端要轉交的還沒有生出來
							"/back-end/KaiPing/gpc/b_listAllGpc_Disc.jsp"
				);
				failureView.forward(req, res);
				
				/*******其他可能的錯誤處理******/
			} catch(Exception e) {
				errorMsgs.put("exception_getOneGPC_Disc", e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher(
					(req.getServletPath().contains("front-end"))?
						"": // 這裡前端要轉交的還沒有生出來	
						"/back-end/KaiPing/gpc/b_updateGpc_Disc_input.jsp"
				);
				failureView.forward(req, res);
			}					
				
		}

					
	}
	

}