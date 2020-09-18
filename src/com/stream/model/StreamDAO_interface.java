package com.stream.model;

import java.util.List;



public interface StreamDAO_interface {
	public void insert(StreamVO streamVO);
    public void update(StreamVO streamVO);
    public void delete(String Stream_id);
    public StreamVO findByPrimaryKey(String stream_id);
    public List<StreamVO> getAll();
}
