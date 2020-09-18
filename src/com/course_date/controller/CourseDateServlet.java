package com.course_date.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.calendar.model.CalMemberViewService;
import com.calendar.model.CalMemberViewVo;
import com.calendar.model.CalPojo;
import com.coach.model.CoachVO;
import com.course_date.model.CourseDateService;
import com.course_date.model.CourseDateVo;
import com.course_order.model.CourseOrderService;
import com.course_order.model.CourseOrderVo;
import com.dayoff.model.DayoffService;
import com.dayoff.model.DayoffVO;
import com.google.gson.Gson;
import com.jessica.utils.ParamUtils;
import com.mem.model.MemVO;

public class CourseDateServlet extends HttpServlet {
	CalMemberViewService calSvc = new CalMemberViewService();
	DayoffService dayoffSvc = new DayoffService();
	CourseOrderService orderSvc = new CourseOrderService();
	CourseDateService cDatesvc = new CourseDateService();
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		if("showCal".equals(action)) {
			showCal(req, res);
		}
		if("addCDate".equals(action)) {
			addCDate(req, res);
		}
		if("showAllCoachCal".equals(action)) {
			showAllCoachCal(req, res);
		}
		if("showSelfCoachCal".equals(action)) {
			showSelfCoachCal(req, res);
		}
		
	}
	
	protected void showCal(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		
		MemVO memberVo = (MemVO) req.getSession().getAttribute("memLogIn");
		String member_id = memberVo.getMember_Id();
		
		List<CalMemberViewVo> list = calSvc.getAllCalMemberViewByMember_id(member_id);
		Set<CalPojo> set = new HashSet();
		CalPojo event;
		
		for(CalMemberViewVo viewVo: list) {
			char[] time = viewVo.getView_time().toCharArray();	
			for(int i=0; i<time.length; i++) {
				if( time[i] == '1') {
					event = new CalPojo();
					event.setTitle(viewVo.getView_desc());
					event.setStart(sqlDateToString(viewVo.getView_date(), i, false));
					event.setEnd(sqlDateToString(viewVo.getView_date(), i, true));
					if(viewVo.getView_event().startsWith("G"))
						event.setClassNames(new String[] {"gpc", viewVo.getView_event()});
					else if(viewVo.getView_event().startsWith("C"))
						event.setClassNames(new String[] {"course", viewVo.getView_event()});
					event.setEditable(false);
					set.add(event);
				}
			}
		}
		String json = new Gson().toJson(set);
		res.getWriter().write(json);
	}
	
	protected void showAllCoachCal(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		
		List<DayoffVO> list = dayoffSvc.getAll();
		Set<CalPojo> set = new HashSet();
		CalPojo event;
		
		for(DayoffVO vo: list) {
			event = new CalPojo();
			event.setTitle(vo.getCoach_Id() + "排休");
			event.setClassNames(new String[] {"coach", "coachHide", vo.getCoach_Id()});
			event.setStart(vo.getDayoff_Date().toString());
			event.setEditable(false);
			event.setAllDay(true);
			set.add(event);
		}
		String json = new Gson().toJson(set);
		res.getWriter().write(json);
	}
	
	protected void showSelfCoachCal(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		
		CoachVO coachVo = (CoachVO) req.getSession().getAttribute("coachLogIn");
		if(coachVo == null) {
			res.getWriter().write("");
			return;
		}
		String coach_id = coachVo.getCoach_Id();
		
		List<DayoffVO> list = dayoffSvc.oneCoachAllTime(coach_id);
		Set<CalPojo> set = new HashSet();
		CalPojo event;
		
		for(DayoffVO vo: list) {
			event = new CalPojo();
			event.setTitle("我的排休");
			event.setClassNames(new String[] {"selfCoach", vo.getDayoff_Id()});
			event.setStart(vo.getDayoff_Date().toString());
			event.setEditable(false);
			event.setAllDay(true);
			set.add(event);
		}
		String json = new Gson().toJson(set);
		res.getWriter().write(json);
	}
	
	protected void addCDate(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain; charset=UTF-8");
		Gson gson = new Gson();
		Map<String, String> msgs = new HashMap<String, String>();

		try {
			String msg = "";
			// 1. 包裝資料
			CourseDateVo cDateVo = ParamUtils.copyParamToBean(new CourseDateVo(), req.getParameterMap());
			// 2. 錯誤驗證
			if(cDateVo.getOrder_id() == null || cDateVo.getOrder_id().length() == 0) {
				msg="請選擇課程名稱,";
			}
			if(cDateVo.getCtime() == null || cDateVo.getCtime().length() == 0) {
				msg+="請選擇約課時間,";
			}
			if(!msg.isEmpty()) {
				msgs.put("errorMsg", msg);
				res.getWriter().write(gson.toJson(msgs));
				return;
			}
			
			// 3. 查詢訂單狀態是否已完課未完課
			CourseOrderVo orderVo = orderSvc.getOneCourseOrderById(cDateVo.getOrder_id());
			Integer lesson = orderVo.getLesson();
			Integer book_lesson = (orderVo.getBook_lesson() == null) ? new Integer(0) : orderVo.getBook_lesson();
			if(lesson <= book_lesson) {
				msgs.put("errorMsg", "已經完課無法約課");
				res.getWriter().write(gson.toJson(msgs));
				return;
			}
			
			// 4. 把dateVo的time整理成DB格式，新增cdate
			char[] time = new char[24];
			for(int i=0; i<time.length; i++) {
				if(i == Integer.valueOf(cDateVo.getCtime())) {
					time[i] = '1';
				}else {
					time[i] = '0';
				}
			}
			cDateVo.setCtime(String.valueOf(time));
			
			cDatesvc.addCourseDate(cDateVo); // 更新cdate
			orderSvc.updateCOrderBookLesson(orderVo.getOrder_id(), book_lesson + 1); // 更新order
										
			msgs.put("msg", "恭喜約課成功!");
			res.getWriter().write(gson.toJson(msgs));
			return;
			
		}catch(Exception e) {
			
		}

	}	
	
	public static String sqlDateToString(java.sql.Date view_date, int view_time_index, boolean isEnd) {
		java.sql.Date date = view_date;
		int h = view_time_index;		
		if(isEnd) {
			if(view_time_index != 23) {
				++h;
			}else if(view_time_index == 23){
				Calendar calendar = new GregorianCalendar();		
				calendar.setTime(view_date); // 把input的date轉換成Calendar		
				calendar.add(calendar.DATE, 1); // 加一天	
				java.util.Date utilDate = (java.util.Date) calendar.getTime(); // Calendar變成util.Date	
				date = new java.sql.Date(utilDate.getTime()); //java.util.Date日期轉換成轉成java.sql.Date格式
				h = 0;
			}			
		}
		return date.toString() + "T" + String.format("%02d", h) + ":00:00";
	}

}
