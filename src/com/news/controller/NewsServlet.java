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
					errorMsgs.add("請輸入最新消息編號");
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
					errorMsgs.add("最新消息編號格式不正確");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				NewsService newsSvc = new NewsService();
				NewsVO newsVO = newsSvc.getOneNews(news_id);
				if (newsVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}
				req.setAttribute("newsVO", newsVO);
				String url = "/front-end/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
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
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
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
					errorMsgs.add("員工ID: 請勿空白");
				}
				String news_spec = req.getParameter("news_spec");
				if (news_spec == null || news_spec.trim().length() == 0) {
					errorMsgs.add("消息類別請勿空白");
				}
				String news_content = req.getParameter("news_content");
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("消息內容請勿空白");
				}
				String news_header = req.getParameter("news_header");
				if (news_header == null | news_header.trim().length() == 0) {
					errorMsgs.add("標題不可為空");
				}
				String news_id = req.getParameter("news_id");

				NewsVO newsVO = new NewsVO();
				newsVO.setNews_id(news_id);
				newsVO.setEmp_id(emp_id);
				newsVO.setNews_content(news_content);
				newsVO.setNews_header(news_header);
				newsVO.setNews_spec(news_spec);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/update_news_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				NewsService newsSvc = new NewsService();
				newsVO = newsSvc.updateNews(news_spec, emp_id, news_content, news_header, news_id);

				req.setAttribute("newsVO", newsVO);
				String url = "/front-end/kevin/news/listOneNews.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
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
//					errorMsgs.add("消息分類請勿空白");
//				}
				String emp_id = req.getParameter("emp_id");
				if (emp_id == null || emp_id.trim().length() == 0) {
					errorMsgs.add("員工編號請勿空白");
				}
				String news_content = req.getParameter("news_content");
				if (news_content == null || news_content.trim().length() == 0) {
					errorMsgs.add("消息內容請勿空白");
				}
				String news_header = req.getParameter("news_header");
				if (news_header == null || news_header.trim().length() == 0) {
					errorMsgs.add("請輸入標題");
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
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
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
				errorMsgs.add("刪除失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/kevin/news/listAllNews.jsp");
				failureView.forward(req, res);
			}

		}

		if ("DisplayAllAJAX".equals(action)) {
			JSONArray array = new JSONArray();
//			取得所有員工資料
			NewsService newsSvc = new NewsService();
			List<NewsVO> listAll = newsSvc.getAll();
//			取得員工權限
			AuthorityService authoritySvc = new AuthorityService();
			List<AuthorityVO> list = authoritySvc.getAll();
			req.setAttribute("list", list);
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
//			撈出員工資料放入
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
//			取得所有資料
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

//			撈出最新消息資料放入
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
//			取得所有資料
			System.out.println("新增消息成功");
			NewsService newsSvc = new NewsService();
			News_specService news_specSvc = new News_specService();
			List<News_specVO> listA = news_specSvc.getAll();
			for (News_specVO b : listA) {
				if (news_spec.equals(b.getNews_specheader())) {
					news_spec = b.getNews_spec();
				}
			}

			NewsVO newsVO = newsSvc.addNews(news_spec, emp_id, news_content, news_header);
//			撈出最新消息資料放入
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
//			取得所有資料
			System.out.println("刪除消息成功");
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(news_id);
//			撈出最新消息資料放入
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
