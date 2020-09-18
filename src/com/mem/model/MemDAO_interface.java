package com.mem.model;
import java.util.*;

import com.coach.model.CoachVO;

public interface MemDAO_interface {
	public void insert(MemVO memVO);
	public void update(MemVO memVO);
	public void delete(String member_Id);
	public MemVO findByPrimaryKey(String member_Id);
	public List<MemVO> getAll();
	public MemVO logIn(String mem_Account);
	public void updateFromBack(String member_Id, Integer mem_Close);
	
	public void updateImg(MemVO memVO);
}
