package com.course.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.coach.model.CoachService;
import com.coach.model.CoachVO;
import com.course.model.CoursePojo;
import com.course.model.CourseService;
import com.course.model.CourseViewService;
import com.course.model.CourseViewVo;
import com.course.model.CourseVo;
import com.course_favor.model.CourseFavorService;
import com.course_set.model.CourseSetService;
import com.course_set.model.CourseSetVo;
import com.exptype.model.ExptypeService;
import com.google.gson.Gson;
import com.jessica.utils.ParamUtils;
import com.mem.model.MemService;
import com.mem.model.MemVO;

@MultipartConfig
public class CourseServlet extends HttpServlet{
	
	// Service
	CourseService courseSvc = new CourseService();
	MemService memberSvc = new MemService();
	CoachService coachSvc = new CoachService();
	ExptypeService exptypeSvc = new ExptypeService();
	CourseSetService courseSetSvc = new CourseSetService();
	CourseFavorService courseFavorSvc = new CourseFavorService();
	CourseViewService courseViewSvc = new CourseViewService();
	
	private static final String INDEX_URL = "/front-end/logIn2.jsp"; // ����url

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("�I�s" + req.getRequestURI() + "/" + action);

		try {
			Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, req, res);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void exploreFwIntro(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<String> msg = new LinkedList<String>();
		req.setAttribute("msg", msg);
		
		try {
			String reqUrl = req.getParameter("reqUrl");	
	        req.getRequestDispatcher(reqUrl).forward(req, res);	        
	        return;
		}catch(Exception e) {
			msg.add("�L�k���o���" + e.getMessage());
			req.getRequestDispatcher(INDEX_URL).forward(req, res);
		}
    }
	
	protected void exploreFilter(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		Gson gson = new Gson();
		try {
			// 1. ���o�ШD�Ѽ�(�j������)
			String[] cname = req.getParameterValues("cname[]");
			String[] sex = req.getParameterValues("sex[]");
			String[] loc = req.getParameterValues("loc[]");
			String[] exp_type = req.getParameterValues("exp_type[]");
			
			// 2. �P�_�O�_�n�J�H��̷ܳR
			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");
			String member_id = "";
			if(memberVo != null) {
				member_id = memberVo.getMember_Id();
			}			
			
			// 3. ���o�����W�[�ҵ{
			List<CourseViewVo> courseVoList = courseViewSvc.getAllCourseOnShelf();

			// 4.1  �S����J�j�M����
			if(cname == null && sex == null && loc == null && exp_type == null) {
				if(member_id.length() != 0) {
					for(CourseViewVo vo: courseVoList) {
						if(courseFavorSvc.getOneCourseFavor(member_id, vo.getCourse_id()) != null)
							vo.setIsFavor("1");
						else				
							vo.setIsFavor("0");
					}
				}				
				out.write(gson.toJson(courseVoList));
				out.flush();
				return;
			}			

			// ���j�M���� (list�R���|���index�A�n�ϥ�Iterator�R��)
			CourseViewVo vo;
			Iterator<CourseViewVo> it = courseVoList.iterator();
			while(it.hasNext()) {
				vo = it.next();								
				cond1:
				if(cname != null) {	
					for(String str: cname) {
						if (vo.getCname().contains(str)) {
							break cond1; // �u�n�@�ŦX����A�ն}cname�j��A���W�i��U�@�ӱ���
						}
					}
					it.remove();
					continue; // ���������ŦX�A���L�H�U�������󰨤W�i��U�@��vo
				}								
				cond2:
				if(sex == null) {
					
				}else if(sex != null) {
					for(String str: sex) {				
						if(vo.getCoach_sex().equals(str)) {
							break cond2;
						}
					}
					it.remove();					
					continue;
				}								
				cond3:
				if(loc != null) {
					for(String str: loc) {				
						if(vo.getLoc().contains(str)) {
							break cond3;
						}
					}
					it.remove();					
					continue;
				}				
				cond4:
				if(exp_type != null) {
					for(String str: exp_type) {				
						if(vo.getExp_id().equals(str)) {
							break cond4;
						}
					}
					it.remove();					
					continue;
				}
			}
			
			out.write(gson.toJson(courseVoList));
			out.flush();
			return;
			
		}catch(Exception e) {
			out.write("CourseServlet�X��: " + e.getMessage());
		}
	}
	
	protected void ajaxFilter(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
//		List<String> msg = new LinkedList<String>();
//		req.setAttribute("msg", msg);
//		try {
//			res.setContentType("text/plain; charset=UTF-8");
//	
//			String[] cname = req.getParameterValues("cname[]");
//			String[] sex = req.getParameterValues("sex[]");
//			String[] loc = req.getParameterValues("loc[]");
//			String[] exp_type = req.getParameterValues("exp_type[]");
//			
//			MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");
//			String member_id = "";
//			if(memberVo != null) {
//				member_id = memberVo.getMember_Id();
//			}
//			String contextPath = req.getContextPath();
//			
//			List<CourseVo> courseVoList = courseSvc.getAllCourseOnShelf();
//			Set<CourseVo> courseVoSet = new LinkedHashSet<CourseVo>();
//			
//			// �Ĥ@�����Jor�S������j�M����
//			if(cname == null && sex == null && loc == null && exp_type == null) {
//				for(CourseVo vo: courseVoList) {
//					courseVoSet.add(vo);
//				}
//				
//				LinkedHashSet<CoursePojo> newSet = transJson(courseVoSet, member_id, contextPath);
//				Gson gson = new Gson();
//				System.out.println(gson.toJson(newSet));
//				res.getWriter().write(gson.toJson(newSet));
//				return;
//			}
//			
//			// ���j�M����
//			for(CourseVo vo: courseVoList) {
//				if(cname != null) {	
//					for(String str: cname) {
//						if(vo.getCname().contains(str)) {
//							courseVoSet.add(vo);
//						}
//					}			
//				}
//				if(sex != null) {
//					for(String str: sex) {				
//						if(memberSvc.getOneMem(coachSvc.getOneByCoach(vo.getCoach_id()).getMember_Id()).getMem_Sex().equals(str)) {
//							courseVoSet.add(vo);
//						}
//					}			
//				}
//				if(loc != null) {
//					for(String str: loc) {				
//						if(vo.getLoc().contains(str)) {
//							courseVoSet.add(vo);
//						}
//					}
//				}
//				if(exp_type != null) {
//					for(String str: exp_type) {				
//						if(vo.getExp_id().equals(str)) {
//							courseVoSet.add(vo);
//						}
//					}			
//				}
//			}
//			
//			LinkedHashSet<CoursePojo> newSet = transJson(courseVoSet, member_id, contextPath);
//			Gson gson = new Gson();
//			System.out.println(gson.toJson(newSet));
//			res.getWriter().write(gson.toJson(newSet));
//		}catch(Exception e) {
//			msg.add("�L�k���X�ҵ{����" + e.getMessage());
//			req.getRequestDispatcher(INDEX_URL).forward(req, res);
//		}
	}
	
	protected void getCourselistByCoach_id(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		List<String> msgs = new LinkedList<String>();
		req.setAttribute("msgs", msgs);
		
		try {
			// 1. ���o�нm�s��			
			CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
			String coach_id = coachVo.getCoach_Id();
			
			// 2. �d�����ҵ{�C��
			List<CourseVo> list = courseSvc.getListCourseByCoachId(coach_id);
			// 2.1 �d�L�ҵ{�C��
			if(list.isEmpty()) { 
				req.setAttribute("courseListByCoachId", "noResult");
				req.getRequestDispatcher("/front-end/Jessica/coach/course_productList.jsp").forward(req, res);
				return;
			}
			// 2.2 ���ҵ{
			req.setAttribute("courseListByCoachId", list);
			req.getRequestDispatcher("/front-end/Jessica/coach/course_productList.jsp").forward(req, res);			
		} catch(Exception e) {
			msgs.add("�L�k�d�ߨp�H�ҵ{�C��" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/coach/course_productList.jsp").forward(req, res);	
			return;
		}
	}
	
	protected void addCourse(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {		
			// 1. ���o�нm�s��			
			CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
			String coach_id = coachVo.getCoach_Id();
			
			// 2. ���oCourse�ШD�Ѽ�
			CourseVo vo = ParamUtils.copyParamToBean(new CourseVo(), req.getParameterMap());
			vo.setCoach_id(coach_id);				
			
			// 2_1. ���~�P�_
			if(vo.getCname() == null || vo.getCname().trim().length() == 0) {
				errorMsgs.put("cname","�ж�g�ҵ{�W�١C");
			}else if(vo.getCname().trim().length() > 100) {
				errorMsgs.put("cname","�ҵ{�W�ٽФŶW�L100�r�C");
			}
			
			if(vo.getExp_id() == null || vo.getExp_id().trim().length() == 0) {
				errorMsgs.put("exp_id","�п�ܽҵ{���O�C");
			}
			
			if(vo.getLoc() == null || vo.getLoc().trim().length() == 0) {
				errorMsgs.put("loc","�ж�g�W�Ҧa�I�C");
			}
			
			if(vo.getIntro().length() > 1000) {
				errorMsgs.put("intro","�ҵ{²���ФŶW�L1000�r�C");
			}
			
			if(vo.getState() == null) {
				errorMsgs.put("state","�п�ܽҵ{�W�U�[���A�C");
			}
			
			// 2_2. �]�˹Ϥ�
			String newPic1 = partToImgSrc(req, req.getPart("file1"));
			String newPic2 = partToImgSrc(req, req.getPart("file2"));
			String newPic3 = partToImgSrc(req, req.getPart("file3"));
			String pic1 = ( (newPic1 == "") ? req.getParameter("oldPic1") : newPic1 );
			String pic2 = ( (newPic2 == "") ? req.getParameter("oldPic2") : newPic2 );
			String pic3 = ( (newPic3 == "") ? req.getParameter("oldPic3") : newPic3 );
			if(pic1 == null || pic1.length() == 0) {
				errorMsgs.put("pic","�ФW�ǫʭ��Ӥ��C");
			}
			
			// 3. ���oCourseSet�ШD�Ѽ� & ���~�P�_
			Set<CourseSetVo> sets = new LinkedHashSet<CourseSetVo>();
			CourseSetVo setVo;
			String[] cset = req.getParameterValues("set");
			if(cset == null || cset.length == 0) {
				errorMsgs.put("set","�Цܤֶ�g�@�ؤ�סC");
			}else {
				String lessonPattern = "[0-9]{1,2}";
				String pricePattern = "[0-9]{1,6}";
				
				for(int i=0; i<cset.length; i++) {
					String lesson = "";
					String price = "";
					String[] tokens = cset[i].split(":");					
					if(tokens.length == 2) {
						lesson = tokens[0];
						price = tokens[1];
					}
					if(lesson == null || price == null || lesson.trim().length() == 0 || price.trim().length() == 0) {
						errorMsgs.put("set","��Ƥ��`���ФŪťաC");
					}else if(!lesson.trim().matches(lessonPattern)){
						errorMsgs.put("lesson","��ƽп�J1~2���");
					}else if(!price.trim().matches(pricePattern)){
						errorMsgs.put("price","����п�J1~6���");
					}else {
						Integer Lesson = Integer.valueOf(lesson.trim());
						Integer Price = Integer.valueOf(price.trim());
						setVo = new CourseSetVo();				
						setVo.setLesson(Lesson);
						setVo.setPrice(Price);
						sets.add(setVo);												
					}
				}
			}
			
			// 4. �p�G�����A��^���~��
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("courseVo", vo);
				req.setAttribute("sets", sets);
				req.setAttribute("pic1", pic1);
				req.setAttribute("pic2", pic2);
				req.setAttribute("pic3", pic3);
				req.getRequestDispatcher("/front-end/Jessica/coach/addCourse.jsp").forward(req, res);
				return;
			}
			
			// 5. �S���}�l�s�W���
			vo.setPic1(imgSrcToByteArray(req, pic1));
			vo.setPic2(imgSrcToByteArray(req, pic2));
			vo.setPic3(imgSrcToByteArray(req, pic3));
			courseSvc.addCourse(vo, sets);
			
			// 6. �s�W���\�A���
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/coach/course_productList.jsp");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			errorMsgs.put("exception", "�N�O�����շF!!!" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/coach/addCourse.jsp").forward(req, res);
		}
	}

	protected void productListFwUpdateCourse(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		List<String> msg = new LinkedList<String>();
		req.setAttribute("msgs", msg);
		try {
			// 1. ���o�нm�s��			
			CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
			String coach_id = coachVo.getCoach_Id();
			
			// 2. ���o�ШD�ҵ{
			String course_id = req.getParameter("course_id");
			
			// 3. �d�ߵ��G
			CourseVo vo = courseSvc.getOneCourseById(course_id);
			Set<CourseSetVo> sets = courseSvc.getCSetsById(course_id);
			String pic1 = byteArrayToImgSrc(req, vo.getPic1(), course_id+"pic1");
			String pic2 = byteArrayToImgSrc(req, vo.getPic2(), course_id+"pic2");
			String pic3 = byteArrayToImgSrc(req, vo.getPic3(), course_id+"pic3");
			
			// ���ӥi��o��...
			if(!vo.getCoach_id().equals(coach_id)) {
				msg.add("�n�J�нm�P�ӽҵ{���ǰt�A�N�h�^�ҵ{�C����");
				req.getRequestDispatcher("/front-end/Jessica/coach/course_productList.jsp").forward(req, res);
				return;
			}

			// 4. �]�˸�ơA��^���G
			req.setAttribute("courseVo", vo);
			req.setAttribute("sets", sets);
			req.setAttribute("pic1", pic1);
			req.setAttribute("pic2", pic2);
			req.setAttribute("pic3", pic3);
			req.getRequestDispatcher("/front-end/Jessica/coach/updateCourse.jsp?course_id="+course_id).forward(req, res);			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			msg.add("�L�k�i�J�s��ҵ{����" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/coach/course_productList.jsp").forward(req, res);
		}
	}
	
	protected void updateCourse(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		try {		
			// 1. ���o�нm�s��			
			CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
			String coach_id = coachVo.getCoach_Id();
			
			// 2. ���oCourse�ШD�Ѽ�
			CourseVo vo = ParamUtils.copyParamToBean(new CourseVo(), req.getParameterMap());
			vo.setCoach_id(coach_id);				
			
			// 2_1. ���~�P�_
			if(vo.getCname() == null || vo.getCname().trim().length() == 0) {
				errorMsgs.put("cname","�ж�g�ҵ{�W�١C");
			}else if(vo.getCname().trim().length() > 100) {
				errorMsgs.put("cname","�ҵ{�W�ٽФŶW�L100�r�C");
			}
			
			if(vo.getExp_id() == null || vo.getExp_id().trim().length() == 0) {
				errorMsgs.put("exp_id","�п�ܽҵ{���O�C");
			}
			
			if(vo.getLoc() == null || vo.getLoc().trim().length() == 0) {
				errorMsgs.put("loc","�ж�g�W�Ҧa�I�C");
			}
			
			if(vo.getIntro().length() > 1000) {
				errorMsgs.put("intro","�ҵ{²���ФŶW�L1000�r�C");
			}
			
			if(vo.getState() == null) {
				errorMsgs.put("state","�п�ܽҵ{�W�U�[���A�C");
			}
			
			// 2_2. �]�˹Ϥ�
			String newPic1 = partToImgSrc(req, req.getPart("file1"));
			String newPic2 = partToImgSrc(req, req.getPart("file2"));
			String newPic3 = partToImgSrc(req, req.getPart("file3"));
			String pic1 = ( (newPic1 == "") ? req.getParameter("oldPic1") : newPic1 );
			String pic2 = ( (newPic2 == "") ? req.getParameter("oldPic2") : newPic2 );
			String pic3 = ( (newPic3 == "") ? req.getParameter("oldPic3") : newPic3 );
			if(pic1 == null || pic1.length() == 0) {
				errorMsgs.put("pic","�ФW�ǫʭ��Ӥ��C");
			}
			
			// 3. ���oCourseSet�ШD�Ѽ� & ���~�P�_
			Set<CourseSetVo> sets = new LinkedHashSet<CourseSetVo>();
			CourseSetVo setVo;
			String[] cset = req.getParameterValues("set");
			if(cset == null || cset.length == 0) {
				errorMsgs.put("set","�Цܤֶ�g�@�ؤ�סC");
			}else {
				String lessonPattern = "[0-9]{1,2}";
				String pricePattern = "[0-9]{1,6}";
				
				for(int i=0; i<cset.length; i++) {
					String lesson = "";
					String price = "";
					String set_id = "";
					String[] tokens = cset[i].split(":");					
					if(tokens.length == 2) {
						lesson = tokens[0];
						price = tokens[1];
					}else if(tokens.length == 3) {
						lesson = tokens[0];
						price = tokens[1];
						set_id = tokens[2];
					}
					if(lesson == null || price == null || lesson.trim().length() == 0 || price.trim().length() == 0) {
						errorMsgs.put("set","��Ƥ��`���ФŪťաC");
					}else if(!lesson.trim().matches(lessonPattern)){
						errorMsgs.put("lesson","��ƽп�J1~2���");
					}else if(!price.trim().matches(pricePattern)){
						errorMsgs.put("price","����п�J1~6���");
					}else {
						Integer Lesson = Integer.valueOf(lesson.trim());
						Integer Price = Integer.valueOf(price.trim());
						setVo = new CourseSetVo();
						setVo.setCourse_id(vo.getCourse_id());
						setVo.setLesson(Lesson);
						setVo.setPrice(Price);
						if(set_id.length() != 0) {
							setVo.setSet_id(set_id);
						}
						sets.add(setVo);												
					}
				}
			}
			
			// 4. �p�G�����A��^���~��
			if(!errorMsgs.isEmpty()) {
				req.setAttribute("courseVo", vo);
				req.setAttribute("sets", sets);
				req.setAttribute("pic1", pic1);
				req.setAttribute("pic2", pic2);
				req.setAttribute("pic3", pic3);
				req.getRequestDispatcher("/front-end/Jessica/coach/updateCourse.jsp").forward(req, res);
				return;
			}
			
			// 5. �S���}�l�s�W���
			vo.setPic1(imgSrcToByteArray(req, pic1));
			vo.setPic2(imgSrcToByteArray(req, pic2));
			vo.setPic3(imgSrcToByteArray(req, pic3));
			courseSvc.updateCourse(vo, sets);
			
			// 6. ��s���\�A���
			res.sendRedirect(req.getContextPath()+"/front-end/Jessica/coach/course_productList.jsp");
		}catch(Exception e) {
			errorMsgs.put("exception", "�N�O�����շF!!!" + e.getMessage());
			req.getRequestDispatcher("/front-end/Jessica/coach/addCourse.jsp").forward(req, res);
		}
	}
	
	private LinkedHashSet<CoursePojo> transJson(Set<CourseVo> set, String member_id, String contextPath) {
		
		LinkedHashSet<CoursePojo> newSet = new LinkedHashSet<CoursePojo>();
		CoursePojo pojo;
		CourseVo courseVo;
		CoachVO coachVo;
		
		for(CourseVo vo : set) {
			pojo = new CoursePojo();
			
			courseVo = courseSvc.getOneCourseById(vo.getCourse_id());		
			pojo.setCourse_id(courseVo.getCourse_id());
			pojo.setCname(courseVo.getCname());
			pojo.setLoc(courseVo.getLoc());
			pojo.setPic1url(contextPath + "/jessica/img.do?picIndex=1&course_id=" + vo.getCourse_id());
			pojo.setCourseUrl(contextPath + "/front-end/Jessica/course/intro.jsp?course_id=" + vo.getCourse_id());
			
			coachVo = coachSvc.getOneByCoach(courseVo.getCoach_id());		
			pojo.setCoach_name(memberSvc.getOneMem(coachVo.getMember_Id()).getMem_Name());
			pojo.setCoach_img(contextPath + "/front-end/ShowPhotos?type=coachPic&img_no=" + courseVo.getCoach_id());
			pojo.setCoachUrl(contextPath + "/front-end/coachInfo.jsp?coach_Id=" + courseVo.getCoach_id());
			
			pojo.setExp_name(exptypeSvc.getOneExptype(courseVo.getExp_id()).getExp_Name());
			
			int[] price = courseSetSvc.getAllCourseSetPrice(vo.getCourse_id());
			if(price.length > 0) {
				pojo.setLowPrice(String.valueOf(price[0]));
				pojo.setHighPrice(String.valueOf(price[price.length-1]));
			}else {
				pojo.setLowPrice("0");
				pojo.setHighPrice("0");
			}
			if(member_id.length() != 0) {
				if(courseFavorSvc.getOneCourseFavor(member_id, vo.getCourse_id()) != null)
					pojo.setIsFavor("1");
				else				
					pojo.setIsFavor("0");
			}else {
				pojo.setIsFavor("0");
			}			

			newSet.add(pojo);			
		}

		return newSet;
	}

	public String partToImgSrc(HttpServletRequest req, Part part) throws ServletException, IOException{
		String src = "";
		
		String saveDir = "/front-end/Jessica/static/img/temp"; // �]�w���|
		File realPathDir = new File(getServletContext().getRealPath(saveDir)); // ���eclipse�u���x�s���|
		if(!realPathDir.exists())
			realPathDir.mkdirs();		   
				
		if(part.getSize() > 0) {
			CoachVO coachVo = (CoachVO)req.getSession().getAttribute("coachLogIn");
			String partName = coachVo.getCoach_Id() + "_temp_" + part.getName() + "." + part.getContentType().substring(part.getContentType().indexOf("/")+1);
			File img = new File(realPathDir, partName);
			part.write(img.toString());
			src = req.getContextPath() + saveDir + "/" + partName; // ���oWeb���|
		}
		return src;
	}
	
	public byte[] imgSrcToByteArray(HttpServletRequest req, String imgSrc) throws ServletException, IOException {
		if(imgSrc.length() == 0) {
			return null;
		}
		byte[] pic = null;
		String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + imgSrc;
		URL picUrl = new URL(url);
		HttpURLConnection huc = (HttpURLConnection) picUrl.openConnection();
		InputStream is = huc.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] b = new byte[4096];
		int len;
		while((len = is.read(b)) != -1) {
			baos.write(b, 0, len);
			baos.flush();
		}
		pic = baos.toByteArray();		
		baos.close();
		is.close();		
		
		return pic;
	}
	
	public String byteArrayToImgSrc(HttpServletRequest req, byte[] byteArray, String filename) throws ServletException, IOException{
		if(byteArray == null) {
			return "";
		}
		String src = "";
		
		String saveDir = "/front-end/Jessica/static/img/temp"; // �]�w���|
		File realPathDir = new File(getServletContext().getRealPath(saveDir)); // ���eclipse�u���x�s���|
		if(!realPathDir.exists())
			realPathDir.mkdirs();   

		File img = new File(realPathDir, filename);
		OutputStream os = new FileOutputStream(img);
		os.write(byteArray);
		os.flush();
		os.close();

		src = req.getContextPath() + saveDir + "/" + filename; // ���oWeb���|		
		return src;
	}

}
