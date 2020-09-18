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
				System.err.println("�ҵ{��׷s�W�ƶq�P��J�����X�ƶq���P!");				
			}
		}else {
			System.err.println("�ҵ{�s�W����");
		}
	}

	// �o��S���B�z�Q�R����...e04�A���B�z�FQQQQQQQQ
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
				System.err.println("�ҵ{��ק�s�ƶq�P��J�����X�ƶq���P!");
			}else {
				System.out.println("�ҵ{�N�X"+courseVo.getCourse_id()+"��s���\�A�ҵ{��׷s�W"+addSetConut+"���A��s"+updateSetConut+"��");
			}
		}else {
			System.err.println("�ҵ{��s����");
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
		// �u�Y��
		CourseService cSvc = new CourseService();
		CourseSetService setSvc = new CourseSetService();
		// ����		
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















