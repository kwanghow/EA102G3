package com.features.model;

import java.util.List;

public interface FeaturesDAO_interface {

	
	public void insert(FeaturesVO featuresVO);
	public void delete(String features_id);
	public void update(FeaturesVO featuresVO);
	public FeaturesVO findByParimaryKey(String features_id);
	public List<FeaturesVO> getAll();
	
	
	
	
}
