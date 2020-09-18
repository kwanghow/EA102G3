package com.trainingLog.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class trainingLogService {
	
	private trainingLogDAO_interface dao;
	
	public trainingLogService() {
		dao = new trainingLogDAO();
	}
	
	public trainingLogVO add(String member_id,String trainingCat_no,
			int weight,byte[] photos,String training_item,
			Timestamp trainingLog_date) {
		trainingLogVO trainingLogVO = new trainingLogVO();
		
		trainingLogVO.setMember_id(member_id);
		trainingLogVO.setPhotos(photos);
		trainingLogVO.setTraining_item(training_item);
		trainingLogVO.setTrainingCat_no(trainingCat_no);
		trainingLogVO.setTrainingLog_date(trainingLog_date);
		trainingLogVO.setWeight(weight);
		dao.insert(trainingLogVO);
		
		return trainingLogVO;
	}
	
	public void del(String trainingLog_no) {
		dao.delete(trainingLog_no);
	}
	
	public Set<trainingLogVO> getLogByMemno(String memno) {
		return dao.findByPrimaryKey(memno);
	}
	
	public void update(String trainingCat_no,
			int weight,byte[] photos,String training_item ,String trainingLog_No) {
		
		trainingLogVO trainingLogVO = new trainingLogVO();
		trainingLogVO.setTraining_item(training_item);
		trainingLogVO.setTrainingCat_no(trainingCat_no);
		trainingLogVO.setWeight(weight);
		trainingLogVO.setPhotos(photos);
		trainingLogVO.setTrainingLog_no(trainingLog_No);
		
		dao.update(trainingLogVO);
	}
	
	public List<trainingLogVO> getAll(){
		return dao.getAll();
	}

}
