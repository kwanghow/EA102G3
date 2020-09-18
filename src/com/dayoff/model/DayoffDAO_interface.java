package com.dayoff.model;

import java.util.List;

public interface DayoffDAO_interface {
	public void insert(DayoffVO dayoffVO);
	public void update(DayoffVO dayoffVO);	
	public List<DayoffVO> oneCoachAllTime(String coach_Id);
	public List<DayoffVO> getAll();
}
