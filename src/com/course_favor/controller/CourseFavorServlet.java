package com.course_favor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.course_favor.model.CourseFavorService;
import com.course_favor.model.CourseFavorVo;
import com.mem.model.MemVO;

public class CourseFavorServlet extends HttpServlet {
	
	CourseFavorService courseFavorSvc = new CourseFavorService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("�g�L" + req.getRequestURI());
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);			
			method.invoke(this, req, res);
		} catch(Exception e) {
			throw new RuntimeException("action�ѼƦ��~");
		}
	}
	
	protected void ajaxEditFavor(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {	
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		try {
			// 1. �P�_�O�_���n�J�A�Y�L�h�^��NoLogIn�F***���B�bsession�O���F�ӷ����}***
			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");			
			if(memberVo == null) {
				req.getSession().setAttribute("location", req.getParameter("location"));
				System.out.println(req.getParameter("location"));
				out.write("NoLogIn");
				return;
			}
			
			// 2. �w�n�J�A�⦬��Ѽƥ]�˦�VO
			String course_id = req.getParameter("course_id");
			String member_id = memberVo.getMember_Id();
			CourseFavorVo courseFavorVo = new CourseFavorVo(member_id, course_id);
		
			// 3. �P�_�O�_�w�[�J�L�̷R			
			if(courseFavorSvc.getOneCourseFavor(courseFavorVo.getMember_id(), courseFavorVo.getCourse_id()) != null) {
				// 3.1 �O�A�q�̷R���R��
				courseFavorSvc.deleteCourseFavor(courseFavorVo);
				out.write("deleteSuccess");				
			} else {
				// 3.2 �_�A�s�W�̷R
				courseFavorSvc.addCourseFavor(courseFavorVo);	
				out.write("addSuccess");
			}
		} catch(Exception e) {
			throw new RuntimeException("�ҵ{�̷R�X���F!" + e.getMessage());
		}
	}

}
