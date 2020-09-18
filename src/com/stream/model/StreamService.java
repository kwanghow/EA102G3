package com.stream.model;

import java.sql.Date;
import java.util.List;

public class StreamService {
	private StreamDAO_interface dao;
	public StreamService() {
		dao = new StreamDAO();
	}

	public StreamVO addStream(StreamVO streamVO) {
		dao.insert(streamVO);
		
		
		
		
		return streamVO;
					
	}
	public StreamVO updateStream(String stream_id,String coach_id,String stream_header,java.sql.Date stream_notice) {
		StreamVO streamVO = new StreamVO();
		
		streamVO.setStream_id(stream_id);
		streamVO.setCoach_id(coach_id);
//		streamVO.setStream_vod(stream_vod);
		streamVO.setStream_header(stream_header);
		streamVO.setStream_notice(stream_notice);
		dao.update(streamVO);
		
		
		
		
		return streamVO;
					
	}
	public void deleteStream(String stream_id) {
		dao.delete(stream_id);
	}
	public StreamVO getOneStream(String stream_id) {
		return dao.findByPrimaryKey(stream_id);
	}
	public List<StreamVO> getAll(){
		return dao.getAll();
	}

	public void update(StreamVO streamVO) {
		  dao.update(streamVO);
	}
	
	
	
}

