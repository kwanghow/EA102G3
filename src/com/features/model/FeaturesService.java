package com.features.model;

import java.util.List;

public class FeaturesService {
	private FeaturesDAO_interface dao;

	public FeaturesService() {
		dao = new FeaturesDAO();
	}

	public FeaturesVO addFeatures(String features_name) {
		FeaturesVO featuresVO = new FeaturesVO();
		featuresVO.setFeatures_name(features_name);
		dao.insert(featuresVO);

		return featuresVO;

	}

	public FeaturesVO updateFeatures(String features_id, String features_name) {
		FeaturesVO featuresVO = new FeaturesVO();
		featuresVO.setFeatures_id(features_id);
		featuresVO.setFeatures_name(features_name);
		dao.update(featuresVO);

		return featuresVO;

	}
	public void deleteFeatures(String features_id) {
		dao.delete(features_id);
	}
	public FeaturesVO getOneFeatures(String features_id) {
		return dao.findByParimaryKey(features_id);
		
	}
	public List<FeaturesVO>getAll(){
		return dao.getAll();
	}

}