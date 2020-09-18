package com.course.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.course_order.model.CourseOrderVo;
import com.course_set.model.CourseSetDaoI;
import com.course_set.model.CourseSetDaoImpl;
import com.course_set.model.CourseSetService;
import com.course_set.model.CourseSetVo;

public class CourseService{
	private CourseDaoI dao;
	private CourseSetDaoI setDao;
	
	public CourseService() {
		this.dao = new CourseDaoImpl();
		this.setDao = new CourseSetDaoImpl();
	}

	public void addCourse(CourseVo courseVo, Set<CourseSetVo> sets) {
		int addSetConut = 0;
		if(dao.insert(courseVo) == 1) {
			String course_id = dao.selectOneByCoachIdAndCname(courseVo.getCoach_id(), courseVo.getCname());
			for(CourseSetVo cSetVo : sets) {
				cSetVo.setCourse_id(course_id);
				addSetConut += setDao.insert(cSetVo);
			}
			if(addSetConut != sets.size()) {
				System.err.println("課程方案新增數量與輸入的集合數量不同!");				
			}
		}else {
			System.err.println("課程新增失敗");
		}
	}

	// 這邊沒有處理被刪掉的...e04，不處理了QQQQQQQQ
	public void updateCourse(CourseVo courseVo, Set<CourseSetVo> sets) {
		int addSetConut = 0;
		int updateSetConut = 0;
		if(dao.update(courseVo) == 1) {
			for(CourseSetVo cSetVo : sets) {
				if(cSetVo.getSet_id() == null || cSetVo.getSet_id().length() == 0) {
					addSetConut += setDao.insert(cSetVo);
				}else {
					updateSetConut += setDao.update(cSetVo);
				}
			}
			if((addSetConut + updateSetConut) != sets.size()) {
				System.err.println("課程方案更新數量與輸入的集合數量不同!");
			}else {
				System.out.println("課程代碼"+courseVo.getCourse_id()+"更新成功，課程方案新增"+addSetConut+"筆，更新"+updateSetConut+"筆");
			}
		}else {
			System.err.println("課程更新失敗");
		}
	}

	public List<CourseVo> getAllCourse() {
		return dao.selectAll();
	}
	
	public List<CourseVo> getAllCourseOnShelf() {
		return dao.selectAllOnShelf();
	}
	
	public List<CourseVo> getAllCourse(Map<String, String[]> paramMap) {
		return dao.selectAll(paramMap);
	}

	public List<CourseVo> getListCourseByCoachId(String coach_id) {
		return dao.selectListByCoachId(coach_id);
	}
	
	public CourseVo getOneCourseById(String course_id) {
		return dao.selectOneById(course_id);
	}
	
	public Set<CourseSetVo> getCSetsById(String course_id) {
		return dao.findCourseSetsById(course_id);
	}

	public Set<CourseOrderVo> getCOrderFromCourse(String course_id){
		Set<CourseOrderVo> orderSet = new LinkedHashSet<CourseOrderVo>();		
		// 工頭們
		CourseService cSvc = new CourseService();
		CourseSetService setSvc = new CourseSetService();
		// 做事		
		Set<CourseSetVo> sets = cSvc.getCSetsById(course_id);
		for(CourseSetVo setVo : sets) {
			Set<CourseOrderVo> orders = setSvc.getCOrderFromCSet(setVo.getSet_id());
			for(CourseOrderVo orderVo: orders) {
				orderSet.add(orderVo);
			}
		}
		return orderSet;
	}	

}















