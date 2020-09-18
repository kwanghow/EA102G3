package com.authority.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.authority.model.AuthorityService;
import com.authority.model.AuthorityVO;
import com.employee.model.EmployeeService;
import com.employee.model.EmployeeVO;


public class AuthorityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		List<String> errorMsgs = new LinkedList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		if ("insert".equals(action)) {
			String emp_id = req.getParameter("emp_id");
			JSONArray array = new JSONArray();
			try {

				String emp_name = req.getParameter("emp_name").trim();
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("名子請勿空白");
				}
				String emp_account = req.getParameter("emp_account").trim();
				if (emp_account == null || emp_account.trim().length() == 0) {
					errorMsgs.add("帳號請勿空白");
				}
				String emp_psw = req.getParameter("emp_psw").trim();
				if (emp_psw == null || emp_psw.trim().length() == 0) {
					errorMsgs.add("密碼請勿空白");
				}
				String emp_email = req.getParameter("emp_email").trim();
				if (emp_email == null || emp_email.trim().length() == 0) {
					errorMsgs.add("信箱請勿空白");
				}
				String emp_phone = req.getParameter("emp_phone").trim();
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("手機請勿空白");
				}
				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(emp_id);
				employeeVO.setEmp_name(emp_name);
				employeeVO.setEmp_account(emp_account);
				employeeVO.setEmp_psw(emp_psw);
				employeeVO.setEmp_email(emp_email);
				employeeVO.setEmp_phone(emp_phone);
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("employeeVO", employeeVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/authority/update_authority_input.jsp");
					failureView.forward(req, res);
					return;
				}

				AuthorityService authoritySvc = new AuthorityService();
				List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
				req.setAttribute("list", list);
				List<String> list1 = new ArrayList<String>();// 原來的權限
				List<String> list2 = new ArrayList<String>();// 修改後的權限
				for (AuthorityVO f : list) {
					list1.add(f.getFeatures_id());
				}
				String[] features = req.getParameterValues("features");
				System.out.println(features);
				if (!(features == null)) {
					for (int i = 0; i < features.length; i++) {
						list2.add(features[i]);
					}
				}
				if (!list1.equals(list2)) {
					for (String del : list1) {
						authoritySvc.deleteAuthority(emp_id, del);
					}
					for (String add : list2) {
						authoritySvc.addAuthority(emp_id, add);
					}
				}
				for (AuthorityVO usb : list) {
					JSONObject obj = new JSONObject();
					obj.put("features_id", usb.getFeatures_id());
					obj.put("emp_id", usb.getEmp_id());
					array.put(obj);
				}

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(array.toString());
				out.flush();
				out.close();

				EmployeeService empSvc = new EmployeeService();
				employeeVO = empSvc.updateEmp(emp_id, emp_name, emp_account, emp_psw, emp_email, emp_phone);
				req.setAttribute("employeeVO", employeeVO);
				String url = "/front-end/authority/select_page.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/authority/update_authority_input.jsp");
				failureView.forward(req, res);
			}

		}

		if ("update".equals(action)) {

			String emp_id = req.getParameter("emp_id");
			req.setAttribute("emp_id", emp_id);
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
			req.setAttribute("list", list);
			List<String> list1 = new ArrayList<String>();// 原來的權限
			List<String> list2 = new ArrayList<String>();// 修改後的權限
			for (AuthorityVO f : list) {
				list1.add(f.getFeatures_id());
			}
			String[] features = req.getParameterValues("features");
			if (!(features == null)) {
				for (int i = 0; i < features.length; i++) {
					list2.add(features[i]);
				}
			}
			if (!list1.equals(list2)) {
				for (String del : list1) {
					authoritySvc.deleteAuthority(emp_id, del);
				}
				for (String add : list2) {
					authoritySvc.addAuthority(emp_id, add);
				}
			}

			String url = "/front-end/authority/listAllAuthority.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("getOne_For_Display".equals(action)) {
			String emp_id = req.getParameter("emp_id");

			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
			req.setAttribute("list", list);
			EmployeeService empSvc = new EmployeeService();
			EmployeeVO employeeVO = empSvc.getOneEmp(emp_id);
			req.setAttribute("employeeVO", employeeVO);

			String url = "/front-end/authority/update_authority_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		if ("delete".equals(action)) {

			String emp_id = req.getParameter("emp_id");
			String[] features = req.getParameterValues("features");
			AuthorityService authoritySvc = new AuthorityService();
			for (int i = 0; i < features.length; i++) {
				String features_id = features[i];
				authoritySvc.deleteAuthority(emp_id, features_id);
			}
			String url = "/front-end/authority/listOneAuthority.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		if ("deleteAuthority".equals(action)) {

			String emp_id = req.getParameter("emp_id");
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
			req.setAttribute("list", list);

			String url = "/front-end/authority/deleteAuthority.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("DisplayAllAJAX".equals(action)) {
			JSONArray array = new JSONArray();
//			取得所有員工資料
			EmployeeService empSvc = new EmployeeService();
			List<EmployeeVO> listAll = empSvc.getAll();
//			取得員工權限
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getAll();
			req.setAttribute("list", list);
//			撈出員工資料放入
			for (EmployeeVO usb : listAll) {
				JSONObject obj = new JSONObject();
				obj.put("emp_id", usb.getEmp_id());
				obj.put("emp_name", usb.getEmp_name());
				obj.put("emp_account", usb.getEmp_account());
				obj.put("emp_psw", usb.getEmp_psw());
				obj.put("emp_email", usb.getEmp_email());
				obj.put("emp_phone", usb.getEmp_phone());
				array.put(obj);
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

		}
		if ("UpdateOneAJAX".equals(action)) {
			String emp_id = req.getParameter("emp_id");
			String emp_name = req.getParameter("emp_name");
			String emp_account = req.getParameter("emp_account");
			String emp_psw = req.getParameter("emp_psw");
			String emp_email = req.getParameter("emp_email");
			String emp_phone = req.getParameter("emp_phone");

			JSONArray array = new JSONArray();
//			修改員工資料
			EmployeeService empSvc = new EmployeeService();
			EmployeeVO empVO = empSvc.updateEmp(emp_id, emp_name, emp_account, emp_psw, emp_email, emp_phone);

//			取得及修改員工權限
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
			req.setAttribute("list", list);
			List<String> list1 = new ArrayList<String>();// 原來的權限
			List<String> list2 = new ArrayList<String>();// 修改後的權限
			
			String[] features = req.getParameterValues("features[]");
			for (AuthorityVO f : list) {
				list1.add(f.getFeatures_id());
			}
			
			if (!(features == null)) {
				for (int i = 0; i < features.length; i++) {
					list2.add(features[i]);
				}
			}
			if (!list1.equals(list2)) {
				for (String del : list1) {
					authoritySvc.deleteAuthority(emp_id, del);
				}
				for (String add : list2) {
					authoritySvc.addAuthority(emp_id, add);
				}
			}
//			撈出員工資料放入
			JSONObject obj = new JSONObject();
			obj.put("emp_id", empVO.getEmp_id());
			obj.put("emp_name", empVO.getEmp_name());
			obj.put("emp_account", empVO.getEmp_account());
			obj.put("emp_psw", empVO.getEmp_psw());
			obj.put("emp_email", empVO.getEmp_email());
			obj.put("emp_phone", empVO.getEmp_phone());

//			撈出員工權限放入
			obj.put("authority", list2);
//			放入array裡面
//			array.put(obj);

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}
		if ("DisplayOneAJAX".equals(action)) {
			JSONArray array = new JSONArray();
			
			String emp_id = req.getParameter("emp_id");
//			取得所有資料
			EmployeeService empSvc = new EmployeeService();
			EmployeeVO emp = empSvc.getOneEmp(emp_id);
//			取得員工權限
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getOneAuthority(emp_id);
			req.setAttribute("list", list);
//			撈出員工資料放入
				JSONObject obj = new JSONObject();
				obj.put("emp_id", emp.getEmp_id());
				obj.put("emp_name", emp.getEmp_name());
				obj.put("emp_account", emp.getEmp_account());
				obj.put("emp_psw", emp.getEmp_psw());
				obj.put("emp_email", emp.getEmp_email());
				obj.put("emp_phone", emp.getEmp_phone());
//			    處理員工權限	
				List<String> authorityList = new ArrayList<String>();
				for(AuthorityVO a:list) {
					authorityList.add(a.getFeatures_id());
				}
				obj.put("authorityList", authorityList);
				array.put(obj);
				
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

		}
		if ("RemoveError".equals(action)) {
			HttpSession session=req.getSession();
			session.removeAttribute("featuresError");
			System.out.println("featuresError被我刪除了");
		}
	}
	
	

}
