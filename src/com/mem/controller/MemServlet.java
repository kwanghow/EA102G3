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

/* 查詢功能 */

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {

				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String memId = req.getParameter("member_Id");
				String idTrue = "^[M]{1}\\d{3}$";

				if (memId == null || (memId.trim()).length() == 0) {
					errorMsgs.add("請輸入會員編號");

				} else if (!memId.trim().matches(idTrue)) {
					errorMsgs.add("會員編號: 只能是　M 開頭, 且編號介於001到999之間 ");
				}
				// 如果輸入錯誤, 則回到原本登入畫面
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(memId);
				if (memVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("memVO", memVO);
				String url = "/front-end/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/select_page.jsp");
				failureView.forward(req, res);
			}
		}

/* 修改功能 */

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String member_Id = new String(req.getParameter("member_Id"));

				/*************************** 2.開始查詢資料 ****************************************/
				MemService memSvc = new MemService();
				MemVO memVO = memSvc.getOneMem(member_Id);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("memVO", memVO); // 資料庫取出的memVO物件,存入req
				String url = "/front-end/update_mem_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_mem_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
/* 進入欄位修改驗證 */
		if ("update_Front".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				// 會員編號驗證
				String member_Id = new String(req.getParameter("member_Id").trim());
				
				// 會員姓名驗證
				String mem_Name = req.getParameter("mem_Name").trim();
				String nameTrue = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_Name == null || mem_Name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mem_Name.trim().matches(nameTrue)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}
				
				// 會員性別驗證
				String mem_Sex = req.getParameter("mem_Sex").trim();
				if (mem_Sex == null) {
					errorMsgs.add("會員性別請勿空白, 請輸入 <男> 或 <女> ");
				}
				
				// 會員帳號驗證
				String mem_Account = req.getParameter("mem_Account").trim();
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("會員帳號請勿空白, 且長度最長為16");
				}
				
				// 會員密碼驗證
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("會員密碼請勿空白, 且長度最長為16");
				}
				
				// 會員照片
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
				
				// 會員信箱驗證
				String mem_Email = req.getParameter("mem_Email");
				String emailTrue = "^([a-zA-Z0-9_]+@[a-zA-Z0-9.]+.[a-zA-Z]{2,4})*$";
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("會員信箱請勿空白, 且長度最長為50");
				}else if (!mem_Email.trim().matches(emailTrue)) {
					errorMsgs.add("電子信箱格式錯誤!!");
				}
				
				// 會員手機驗證
				String mem_Phone = req.getParameter("mem_Phone").trim();
				String phoneTrue = "^[0]{1}[9]{1}\\d{8}$";
				if (mem_Phone == null || mem_Phone.trim().length() == 0) {
					errorMsgs.add("會員手機請勿空白");
				} else if (!mem_Phone.trim().matches(phoneTrue)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員手機只能是數字, 開頭為09, 且長度剛好為10  例:0987654321");
				}
				
				// 會員生日驗證
				java.sql.Date mem_Birth = null;
				try {
					mem_Birth = java.sql.Date.valueOf(req.getParameter("mem_Birth").trim());
				} catch (IllegalArgumentException e) {
					mem_Birth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入您的生日日期!");
				}
				
				// 會員地址驗證
				String mem_Addr = req.getParameter("mem_Addr").trim();
				if (mem_Addr == null || mem_Addr.trim().length() == 0) {
					errorMsgs.add("會員地址請勿空白");
				}
				
				// 會員權限驗證
				Integer mem_Close = new Integer(req.getParameter("mem_Close"));

				// 會員信用卡驗證
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
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "修改失敗!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				
				memVO = memSvc.updateMem(member_Id, mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone,
						mem_Birth, mem_Addr, mem_Close, mem_Card);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				session.setAttribute("memLogIn", memVO); // 資料庫update成功後,正確的的memVO物件,存入req
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "修改成功!");
				String url = "/front-end/updateMember.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneMem.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "修改失敗!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/updateMember.jsp");
				failureView.forward(req, res);
			}
		}
		
		
/*新增功能*/		
		if("insert".equals(action)) {
					
			List<String> errorMsgs = new LinkedList<String>();
					
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
//				// 會員編號驗證
//				String member_Id = new String(req.getParameter("member_Id").trim());
				
				// 新增會員姓名
				String mem_Name = req.getParameter("mem_Name").trim();
				String nameTrue = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (mem_Name == null || mem_Name.trim().length() == 0) {
					errorMsgs.add("會員姓名: 請勿空白");
				} else if (!mem_Name.trim().matches(nameTrue)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}
				
				// 新增會員性別
				String mem_Sex = req.getParameter("mem_Sex");
				if (mem_Sex == null) {
					errorMsgs.add("會員性別請勿空白, 請輸入 <男> 或 <女> ");
				}
				
				// 新增會員帳號
				String mem_Account = req.getParameter("mem_Account").trim();
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("會員帳號請勿空白, 且長度最長為16");
				}
				
				// 新增會員密碼
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("會員密碼請勿空白, 且長度最長為16");
				}
				
				// 新增會員照片
				byte[] mem_Img = null;
				Part part = req.getPart("mem_Img");
				InputStream in = part.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				mem_Img = buf;
	
				in.close();
				
				// 新增會員信箱
				String mem_Email = req.getParameter("mem_Email");
				String emailTrue = "^([a-zA-Z0-9_]+@[a-zA-Z0-9.]+.[a-zA-Z]{2,4})*$";
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("會員信箱請勿空白, 且長度最長為50");
				}else if (!mem_Email.trim().matches(emailTrue)) {
					errorMsgs.add("電子信箱格式錯誤!!");
				}
				// 驗證信箱是否重複
				MemService email = new MemService();
				List<String> allEmail = new ArrayList<String>();
				List<MemVO> emailList = email.getAll();
				for (MemVO a : emailList) {
					allEmail.add(a.getMem_Email());
				}
				if (allEmail.contains(mem_Email)) {
					errorMsgs.add("此電子信箱已被別人使用!");
				}
				
				// 新增會員手機
				String mem_Phone = req.getParameter("mem_Phone").trim();
				String phoneTrue = "^[0]{1}[9]{1}\\d{8}$";
				if (mem_Phone == null || mem_Phone.trim().length() == 0) {
					errorMsgs.add("會員手機請勿空白");
				} else if (!mem_Phone.trim().matches(phoneTrue)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("會員手機只能是數字, 開頭為09, 且長度剛好為10  例:0987654321");
				}
				
				// 新增會員生日
				java.sql.Date mem_Birth = null;
				try {
					mem_Birth = java.sql.Date.valueOf(req.getParameter("mem_Birth").trim());
				} catch (IllegalArgumentException e) {
					mem_Birth = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入您的生日日期!");
				}
				
				// 新增會員地址
				String mem_Addr = req.getParameter("mem_Addr").trim();
				if (mem_Addr == null || mem_Addr.trim().length() == 0) {
					errorMsgs.add("會員地址請勿空白");
				}
				// 新增會員權限
//				Integer mem_Close = new Integer(req.getParameter("mem_Close"));

				// 新增會員信用卡
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
					req.setAttribute("memVO", memVO); // 含有輸入格式錯誤的memVO物件,也存入req
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "申請失敗!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeMember.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 *****************************************/
				MemService memSvc = new MemService();
				memVO = memSvc.addMem(mem_Name, mem_Sex, mem_Account, mem_Psw, mem_Img, mem_Email, mem_Phone,
						mem_Birth, mem_Addr, mem_Card);
				
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "申請成功!");			
				String url = "/front-end/kevin/index.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "申請失敗!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/becomeMember.jsp");
				failureView.forward(req, res);
			}		
		}
		
		
/*刪除功能*/		
		if("delete".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				String member_Id = new String(req.getParameter("member_Id").trim());
				
				/***************************2.開始刪除資料***************************************/
				MemService memSvc = new MemService();
				memSvc.deleteMem(member_Id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/listAllMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/listAllMem.jsp");
				failureView.forward(req, res);
			}
		}
		
/*******************************************會員登入************************************************/		
		
		if("getMember_Login".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {				
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_Account = req.getParameter("mem_Account");
				if (mem_Account == null || mem_Account.trim().length() == 0) {
					errorMsgs.add("會員帳號請勿空白, 且長度最長為16");
				}
				
				String mem_Psw = req.getParameter("mem_Psw").trim();
				if (mem_Psw == null || mem_Psw.trim().length() == 0) {
					errorMsgs.add("會員密碼請勿空白, 且長度最長為16");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}	
				/***************************2.開始查詢資料*****************************************/
				MemService memSvc = new MemService();
				MemVO memLogIn = memSvc.logIn(mem_Account);
				
				if (memLogIn == null || !memLogIn.getMem_Account().equals(mem_Account)) {
					errorMsgs.add("查無此帳號!");
				}
				
				if (memLogIn == null || !memLogIn.getMem_Psw().equals(mem_Psw)) {
					errorMsgs.add("密碼錯誤!");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}
				
				CoachService coachSvc = new CoachService();
				CoachVO coachLogIn = coachSvc.getOneByMem(memLogIn.getMember_Id());
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
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
				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				errorMsgs.add("沒有此帳號!!!");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
			}
		}
		
		/*******************************************會員登出************************************************/		
		
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
		
		/*******************************************忘記密碼************************************************/	
		if ("forgot_Password".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String mem_Email = req.getParameter("mem_Email");
				if (mem_Email == null || mem_Email.trim().length() == 0) {
					errorMsgs.add("會員信箱請勿空白, 且長度最長為50");
				}
				
				if (!errorMsgs.isEmpty()) {
					session.setAttribute("isOk", "No");
					session.setAttribute("okVal", "信箱格式錯誤!");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/logIn2.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始查詢資料*****************************************/
				MemService memSvc = new MemService();
				List<MemVO> list = memSvc.getAll();
				List<MemVO> findEmail = new ArrayList<MemVO>();
				findEmail = list.stream().filter(email -> email.getMem_Email().equals(mem_Email)).collect(Collectors.toList());
				
				if (findEmail.size() != 0) {
					String name = findEmail.get(0).getMem_Name();
					String account = findEmail.get(0).getMem_Account();
					String password = findEmail.get(0).getMem_Psw();
					// 送出驗證信到信箱
					MailService sendEmail = new MailService();
					sendEmail.sendMail(mem_Email, "[我就健] 會員通知: " + name + "您好", "您的帳號為: " + account + "\n" + "密碼為: " + password);

					System.out.println(mem_Email);
					System.out.println(name);
					System.out.println(account);
					System.out.println(password);
				}
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				session.setAttribute("isOk", "Yes");
				session.setAttribute("okVal", "已發送驗證信!");			
				String url = "/front-end/logIn2.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add(e.getMessage());
				session.setAttribute("isOk", "No");
				session.setAttribute("okVal", "信箱格式錯誤!");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/logIn2.jsp");
				failureView.forward(req, res);
			}
		}
		/*******************************************後台修改會員資料************************************************/	
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
		System.out.println("header=" + header); // 測試用
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		System.out.println("filename=" + filename); // 測試用
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}
}
