package com.news.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.authority.model.AuthorityService;
import com.authority.model.AuthorityVO;
import com.employee.model.EmployeeService;
import com.employee.model.EmployeeVO;
import com.news.model.NewsService;
import com.news.model.NewsVO;
import com.newsspec.model.News_specService;
import com.newsspec.model.News_specVO;

public class NewsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("news_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�̷s�����s��");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				String news_id = null;
				try {
					news_id = new String(str);
				} catch (Exception e) {
					errorMsgs.add("�̷s�����s���榡�����T");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_id);
				if (newsVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
					failureView.forward(req, res);
					return;// �{�����_
				}
				req.setAttribute("newsVO", newsVO);
				String url = "/front-end/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

				String news_id = req.getParameter("news_id");

				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_id);

				req.setAttribute("newsVO", newsVO);

				String url = "/front-end/news/update_news_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/listAllNews.jsp");
				failureView.forward(req, res);
			}
		}
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String emp_id = req.getParameter("emp_id").trim();
				if (emp_id == null || emp_id.trim().length() == 0) {
					errorMsgs.add("���uID: �ФŪť�");
				}
				String news_spec = req.getParameter("news_spec");
				if (news_spec == null || news_spec.trim().length() == 0) {
					errorMsgs.add("�������O�ФŪť�");
				}
				String news_content = req.getParameter("news_content");
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("�������e�ФŪť�");
				}
				String news_header = req.getParameter("news_header");
				if (news_header == null | news_header.trim().length() == 0) {
					errorMsgs.add("���D���i����");
				}
				String news_id = req.getParameter("news_id");

				NewsVO newsVO = new NewsVO();
				newsVO.setNews_id(news_id);
				newsVO.setEmp_id(emp_id);
				newsVO.setNews_content(news_content);
				newsVO.setNews_header(news_header);
				newsVO.setNews_spec(news_spec);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/update_news_input.jsp");
					failureView.forward(req, res);
					return; // �{�����_
				}
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(news_spec, emp_id, news_content, news_header, news_id);

				req.setAttribute("newsVO", newsVO);
				String url = "/front-end/kevin/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// ���\��� update_emp_input.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/stream/update_news_input.jsp");
				failureView.forward(req, res);
			}
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
//				String news_spec = req.getParameter("news_spec");
//				if (news_spec == null || news_spec.trim().length() == 0) {
//					errorMsgs.add("���������ФŪť�");
//				}
				String emp_id = req.getParameter("emp_id");
				if (emp_id == null || emp_id.trim().length() == 0) {
					errorMsgs.add("���u�s���ФŪť�");
				}
				String news_content = req.getParameter("news_content");
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("�������e�ФŪť�");
				}
				String news_header = req.getParameter("news_header");
				if (news_header == null || news_header.trim().length() == 0) {
					errorMsgs.add("�п�J���D");
				}
				NewsVO newsVO = new NewsVO();
				newsVO.setEmp_id(emp_id);
				newsVO.setNews_header(news_header);
				newsVO.setNews_content(news_content);
				newsVO.setNews_spec(req.getParameter("news_spec"));
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/addNews.jsp");
					failureView.forward(req, res);
					return;
				}
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.addNews(req.getParameter("news_spec"), emp_id, news_content, news_header);

				String url = "/front-end/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/addNews.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String news_id = new String(req.getParameter("news_id"));

				NewsService newsSvc = new NewsService();
				newsSvc.deleteNews(news_id);

				String url = "/front-end/news/listAllNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�R������:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/listAllNews.jsp");
				failureView.forward(req, res);
			}

		}

		if ("DisplayAllAJAX".equals(action)) {
			JSONArray array = new JSONArray();
//			���o�Ҧ����u���
			NewsService newsSvc = new NewsService();
			List<NewsVO> listAll = newsSvc.getAll();
//			���o���u�v��
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getAll();
			req.setAttribute("list", list);
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
//			���X���u��Ʃ�J
			for (NewsVO usb : listAll) {
				JSONObject obj = new JSONObject();
				obj.put("emp_id", usb.getEmp_id());
				obj.put("news_content", usb.getNews_content());
				obj.put("news_id", usb.getNews_id());
				for (News_specVO b : listA) {
					if (b.getNews_spec().equals(usb.getNews_spec())) {
						obj.put("news_spec", b.getNews_specheader());
					}
				}
				obj.put("news_header", usb.getNews_header());
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
			JSONArray array = new JSONArray();
//			���o�Ҧ����
			String news_id = req.getParameter("news_id");
			String emp_id = req.getParameter("emp_id");
			String news_content = req.getParameter("news_content");
			String news_spec = req.getParameter("news_spec");
			String news_header = req.getParameter("news_header");
			NewsService newsSvc = new NewsService();
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
			for (News_specVO b : listA) {
				if (news_spec.equals(b.getNews_specheader())) {
					news_spec = b.getNews_spec();
				}
			}

			NewsVO newsVO = newsSvc.updateNews(news_spec, emp_id, news_content, news_header, news_id);

//			���X�̷s������Ʃ�J
			JSONObject obj = new JSONObject();
			obj.put("emp_id", newsVO.getEmp_id());
			obj.put("news_content", newsVO.getNews_content());
			obj.put("news_id", newsVO.getNews_id());
			obj.put("news_spec", newsVO.getNews_spec());
			obj.put("news_header", newsVO.getNews_header());
			array.put(obj);

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

		}

		if ("InsertOneAJAX".equals(action)) {
			JSONArray array = new JSONArray();

			String news_id = req.getParameter("news_id");
			String emp_id = req.getParameter("emp_id");
			String news_content = req.getParameter("news_content");
			String news_spec = req.getParameter("news_spec");
			String news_header = req.getParameter("news_header");
//			���o�Ҧ����
			System.out.println("�s�W�������\");
			NewsService newsSvc = new NewsService();
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
			for (News_specVO b : listA) {
				if (news_spec.equals(b.getNews_specheader())) {
					news_spec = b.getNews_spec();
				}
			}

			NewsVO newsVO = newsSvc.addNews(news_spec, emp_id, news_content, news_header);
//			���X�̷s������Ʃ�J
			JSONObject obj = new JSONObject();
			obj.put("emp_id", newsVO.getEmp_id());
			obj.put("news_content", newsVO.getNews_content());
			obj.put("news_id", newsVO.getNews_id());
			obj.put("news_spec", newsVO.getNews_spec());
			obj.put("news_header", newsVO.getNews_header());
			array.put(obj);

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();

		}

		if ("DeleteOneAJAX".equals(action)) {
			String news_id = req.getParameter("news_id");
//			���o�Ҧ����
			System.out.println("�R���������\");
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(news_id);
//			���X�̷s������Ʃ�J
			JSONObject obj = new JSONObject();
			obj.put("Success", news_id);
//			res.setContentType("text/plain");
//			res.setCharacterEncoding("UTF-8");
//			PrintWriter out = res.getWriter();
//			out.write(obj.toString());
//			out.flush();
//			out.close();
			String url = "/back-end/kevin/index/index.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if ("FindOneAJAX".equals(action)) {
			
			String find = req.getParameter("find");
			NewsService newsSvc = new NewsService();
			List<NewsVO> finded = newsSvc.getContent(find);
			JSONObject obj = new JSONObject();
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
			List<String> specheader=new ArrayList<String>();
			for (News_specVO b : listA) {
				for (NewsVO a : finded) {
					if (a.getNews_spec().equals(b.getNews_spec())) {
						specheader.add(b.getNews_specheader());
					}
				}
			}

			obj.put("finded", finded);
			obj.put("specheader", specheader);
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
			

		}
		if ("SpecPlayAJAX".equals(action)) {
			String news_spec = req.getParameter("news_spec");
			NewsService newsSvc = new NewsService();
			List<NewsVO> listAll = newsSvc.getAll();
			List<NewsVO> spec = new ArrayList<NewsVO>();
			spec = listAll.stream().filter(newsspec -> newsspec.getNews_spec().equals(news_spec))
					.collect(Collectors.toList());
			JSONObject obj = new JSONObject();

			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
			List<String> specheader=new ArrayList<String>();
			for (News_specVO b : listA) {
				for (NewsVO a : spec) {
					if (a.getNews_spec().equals(b.getNews_spec())) {
						specheader.add(b.getNews_specheader());
					}
				}
			}

			obj.put("spec", spec);
			obj.put("specheader", specheader);
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}

	}

}
