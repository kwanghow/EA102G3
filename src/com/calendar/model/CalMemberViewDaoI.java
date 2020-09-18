package com.calendar.model;

import java.util.List;

public interface CalMemberViewDaoI {
	List<CalMemberViewVo> selectAll();	
	List<CalMemberViewVo> selectListByMember_id(String member_id);
}
