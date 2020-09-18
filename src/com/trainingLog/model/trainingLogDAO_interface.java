package com.trainingLog.model;

import java.util.List;
import java.util.Set;

public interface trainingLogDAO_interface {
	
	public void insert(trainingLogVO trainingLogVO);
	public void update(trainingLogVO trainingLogVO);
	public void delete(String trainingLog_no);
	public Set<trainingLogVO> findByPrimaryKey(String memno);
	public List<trainingLogVO> getAll();
}
