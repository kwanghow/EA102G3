package com.employee.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.employee.model.EmployeeService;
import com.employee.model.EmployeeVO;

import java.util.stream.Collectors;

public class EmployeeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String ephoneReg = "(09)[0-9]{8}$";
		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("emp_id");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("��J���u�s��");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				String emp_id = new String(str);
				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getOneEmp(emp_id);
				if (employeeVO == null) {
					errorMsgs.add("�d�L�����u");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("employeeVO", employeeVO);
				String url = "/front-end/kevin/emp/listOneEmp.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String emp_id = req.getParameter("emp_id");

				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getOneEmp(emp_id);
				req.setAttribute("employeeVO", employeeVO);
				String url = "/front-end/kevin/emp/update_emp_input.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String emp_id = req.getParameter("emp_id").trim();

				String emp_name = req.getParameter("emp_name").trim();
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("�W�l�ФŪť�");
				}
				String emp_account = req.getParameter("emp_account").trim();
				if (emp_account == null || emp_account.trim().length() == 0) {
					errorMsgs.add("�b���ФŪť�");
				}
				String emp_psw = req.getParameter("emp_psw").trim();
				if (emp_psw == null || emp_psw.trim().length() == 0) {
					errorMsgs.add("�K�X�ФŪť�");
				}
				String emp_email = req.getParameter("emp_email").trim();
				if (emp_email == null || emp_email.trim().length() == 0) {
					errorMsgs.add("�H�c�ФŪť�");
				}
				String emp_phone = req.getParameter("emp_phone").trim();
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("����ФŪť�");
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
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return;
				}

				EmployeeService empSvc = new EmployeeService();
				employeeVO = empSvc.updateEmp(emp_id, emp_name, emp_account, emp_psw, emp_email, emp_phone);
				req.setAttribute("employeeVO", employeeVO);
				String url = "/front-end/kevin/emp/listOneEmp.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String emp_name = req.getParameter("emp_name");
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("���u�m�W�ФŪť�");
				}
				String emp_account = req.getParameter("emp_account");
				if (emp_account == null || emp_account.trim().length() == 0) {
					errorMsgs.add("���u�b���ФŪť�");
				}
				String emp_psw = req.getParameter("emp_psw");
				if (emp_psw == null || emp_psw.trim().length() == 0) {
					errorMsgs.add("�K�X�ФŪť�");
				}
				String emp_email = req.getParameter("emp_email");
				if (emp_email == null || emp_email.trim().length() == 0) {
					errorMsgs.add("�H�c�ФŪť�");
				}
				String emp_phone = req.getParameter("emp_phone");
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("����ФŪť�");
				}
				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setEmp_name(emp_name);
				employeeVO.setEmp_account(emp_account);
				employeeVO.setEmp_psw(emp_psw);
				employeeVO.setEmp_email(emp_email);
				employeeVO.setEmp_phone(emp_phone);
				if (!errorMsgs.isEmpty()) {
					String url = "/front-end/kevin/emp/update_emp_input.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;

				}

				EmployeeService empSvc = new EmployeeService();
				employeeVO = empSvc.addEmp(emp_name, emp_account, emp_psw, emp_email, emp_phone);

				String url = "/front-end/kevin/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/addEmp.jsp");
				failureView.forward(req, res);

			}

		}
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String emp_id = req.getParameter("emp_id");
				EmployeeService empSvc = new EmployeeService();
				empSvc.deleteEmp(emp_id);

				String url = "/front-end/kevin/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�R�����Ѱ�" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}

		}

		if ("Login".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String eaccountReg = "[^(\u4e00-\u9fa5)]{4,8}$";
			try {
				String emp_account = req.getParameter("emp_account");
				if (emp_account == null || (emp_account.trim().length() == 0)) {
					errorMsgs.add("��J���u�b��");
				}
				String emp_psw = req.getParameter("emp_psw");
				if (emp_psw == null || (emp_psw.trim().length() == 0)) {
					errorMsgs.add("��J���u�K�X");
				} else if (!emp_account.trim().matches(eaccountReg)) {
					errorMsgs.add("���u�b��:�^��r���B�Ʀr ,�B���ץ��ݦb4��8����");
				} else if (!emp_psw.trim().matches(eaccountReg)) {
					errorMsgs.add("���u�K�X:�^��r���B�Ʀr,�B���ץ��ݦb4��8����");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/login.jsp");
					failureView.forward(req, res);
					return;
				}
				EmployeeService empSvc = new EmployeeService();
				EmployeeVO employeeVO = empSvc.getLogin(emp_account);
				if (employeeVO == null) {
					errorMsgs.add("�b�����~");
				}
				if (!employeeVO.getEmp_psw().equals(emp_psw)) {
					errorMsgs.add("�K�X���~");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/login.jsp");
					failureView.forward(req, res);
					return;
				}
				HttpSession session = req.getSession();
				String location = (String) session.getAttribute("location");
				session.setAttribute("employeeVO", employeeVO);
				if (location != null) {
					res.sendRedirect(location);
					return;
				} else {
					String url = "/back-end/kevin/index/index.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}

			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/login.jsp");
				failureView.forward(req, res);
			}

		}
		if ("Register".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String eaccountReg = "[^(\u4e00-\u9fa5)]{4,8}$";
			String enameReg = "[^0-9]{2,5}$";
			try {
				String emp_account = req.getParameter("emp_account");
				String emp_name = req.getParameter("emp_name");
				String emp_email = req.getParameter("emp_email");
				String emp_phone = req.getParameter("emp_phone");

				EmployeeService empSvc = new EmployeeService();
				List<EmployeeVO> list = empSvc.getAll();
				List<EmployeeVO> password = new ArrayList<EmployeeVO>();
				password = list.stream().filter(email -> email.getEmp_email().equals(emp_email))
						.collect(Collectors.toList());
				List<EmployeeVO> doubleacc = new ArrayList<EmployeeVO>();
				doubleacc = list.stream().filter(account -> account.getEmp_account().equals(emp_account))
						.collect(Collectors.toList());

				if (doubleacc.size() != 0) {
					errorMsgs.add("�b���w�Q���U");
				}

				if (password.size() != 0) {
					errorMsgs.add("�H�c�w�Q���U");
				}

				if (emp_account == null || (emp_account.trim().length() == 0)) {
					errorMsgs.add("��J���u�b��");
				}
				if (emp_name == null || (emp_name.trim().length() == 0)) {
					errorMsgs.add("��J���u�m�W");
				}
				if (emp_email == null || (emp_email.trim().length() == 0)) {
					errorMsgs.add("��J���u�H�c");
				}
				if (emp_phone == null || (emp_phone.trim().length() == 0)) {
					errorMsgs.add("��J���u�q��");
				} else if (!emp_account.trim().matches(eaccountReg)) {
					errorMsgs.add("���u�b��:�^��r���B�Ʀr ,�B���ץ��ݦb4��8����");
				} else if (!emp_name.trim().matches(enameReg)) {
					errorMsgs.add("���u�m�W:���o�]�t�Ʀr�B������2-5����");
				} else if (!emp_phone.trim().matches(ephoneReg)) {
					System.out.println(emp_phone);
					errorMsgs.add("���u�q��:��J���X����");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/register.jsp");
					failureView.forward(req, res);
					return;
				}

				Code code = new Code();
				String emp_psw = code.getRandomPassword();
				EmployeeVO employeeVO = empSvc.addEmp(emp_name, emp_account, emp_psw, emp_email, emp_phone);
				MailService send = new MailService();
				send.sendMail(emp_email, "�˷R��" + emp_name + "�z�n", "�z���b����" + emp_account + "\n" + "�z���K�X��" + emp_psw);
				HttpSession session = req.getSession();
				session.setAttribute("employeeVO", employeeVO);
				String url = "/back-end/kevin/index/login.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/login.jsp");
				failureView.forward(req, res);
			}

		}
		if ("Forgot".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String emp_email = req.getParameter("emp_email");
				if (emp_email == null || (emp_email.trim().length() == 0)) {
					errorMsgs.add("�п�J�q�l�H�c");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/forgot-password.jsp");
					failureView.forward(req, res);
					return;
				}
				EmployeeService empSvc = new EmployeeService();
				List<EmployeeVO> list = empSvc.getAll();
				List<EmployeeVO> findemail = new ArrayList<EmployeeVO>();
				findemail = list.stream().filter(email -> email.getEmp_email().equals(emp_email))
						.collect(Collectors.toList());
				if (findemail.size() != 0) {
					String emp_psw = findemail.get(0).getEmp_psw();
					String emp_name = findemail.get(0).getEmp_name();
					String emp_account = findemail.get(0).getEmp_account();
					MailService send = new MailService();
					send.sendMail(emp_email, "�˷R��" + emp_name + "�z�n", "�z���b����" + emp_account + "\n" + "�z���K�X��" + emp_psw);
					errorMsgs.add("�Ц��H�d��K�X");
				} else {
					errorMsgs.add("�d�L���H�c");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/forgot-password.jsp");
					failureView.forward(req, res);
					return;
				}

				String url = "/back-end/kevin/index/forgot-password.jsp";

				RequestDispatcher successView = req.getRequestDispatcher(url);

				successView.forward(req, res);
			} catch (Exception e) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/kevin/index/forgot-password.jsp");
				failureView.forward(req, res);
			}

		}
		if ("Logout".equals(action)) {
			EmployeeVO employeeVO = null;
			HttpSession session = req.getSession();
			session.setAttribute("employeeVO", employeeVO);
			session.invalidate();

			String url = "/back-end/kevin/index/login.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("ProfileEdit".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			JSONObject obj = new JSONObject();

			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String emp_id = req.getParameter("emp_id").trim();
				String emp_name = req.getParameter("emp_name").trim();
				if (emp_name == null || emp_name.trim().length() == 0) {
					errorMsgs.add("�W�l�ФŪť�");
				}
				String emp_account = req.getParameter("emp_account").trim();
				if (emp_account == null || emp_account.trim().length() == 0) {
					errorMsgs.add("�b���ФŪť�");
				}
				String emp_psw = req.getParameter("emp_psw").trim();
				if (emp_psw == null || emp_psw.trim().length() == 0) {
					errorMsgs.add("�K�X�ФŪť�");
				}
				String emp_email = req.getParameter("emp_email").trim();
				if (emp_email == null || emp_email.trim().length() == 0) {
					errorMsgs.add("�H�c�ФŪť�");
				}
				String emp_phone = req.getParameter("emp_phone").trim();
				if (emp_phone == null || emp_phone.trim().length() == 0) {
					errorMsgs.add("����ФŪť�");
				}
				EmployeeVO employeeVO = new EmployeeVO();
				employeeVO.setEmp_id(emp_id);
				employeeVO.setEmp_name(emp_name);
				employeeVO.setEmp_account(emp_account);
				employeeVO.setEmp_psw(emp_psw);
				employeeVO.setEmp_email(emp_email);
				employeeVO.setEmp_phone(emp_phone);

				if (!errorMsgs.isEmpty()) {
					obj.put("errorMsgs", errorMsgs);
					res.setContentType("text/plain");
					res.setCharacterEncoding("UTF-8");
					PrintWriter out = res.getWriter();
					out.write(obj.toString());
					out.flush();
					out.close();
					return;
				}

				EmployeeService empSvc = new EmployeeService();
				employeeVO = empSvc.updateEmp(emp_id, emp_name, emp_account, emp_psw, emp_email, emp_phone);
				req.setAttribute("employeeVO", employeeVO);
				obj.put("emp_id", employeeVO.getEmp_id());
				obj.put("emp_name", employeeVO.getEmp_name());
				obj.put("emp_account", employeeVO.getEmp_account());
				obj.put("emp_psw", employeeVO.getEmp_psw());
				obj.put("emp_email", employeeVO.getEmp_email());
				obj.put("emp_phone", employeeVO.getEmp_phone());
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
			}
		}

		if ("MyProfile".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			JSONObject obj = new JSONObject();
			req.setAttribute("errorMsgs", errorMsgs);

			String emp_id = req.getParameter("emp_id").trim();

			EmployeeService empSvc = new EmployeeService();
			EmployeeVO employeeVO = new EmployeeVO();
			employeeVO = empSvc.getOneEmp(emp_id);
			req.setAttribute("employeeVO", employeeVO);
			obj.put("emp_id", employeeVO.getEmp_id());
			obj.put("emp_name", employeeVO.getEmp_name());
			obj.put("emp_account", employeeVO.getEmp_account());
			obj.put("emp_psw", employeeVO.getEmp_psw());
			obj.put("emp_email", employeeVO.getEmp_email());
			obj.put("emp_phone", employeeVO.getEmp_phone());
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}
	}

}
