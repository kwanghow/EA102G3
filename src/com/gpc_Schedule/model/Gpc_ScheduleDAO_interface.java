package com.gpc_Schedule.model;

import java.sql.Connection;
import java.util.*;

public interface Gpc_ScheduleDAO_interface {
    public void insert(Gpc_ScheduleVO gpc_ScheduleVO);
    public void update(Gpc_ScheduleVO gpc_ScheduleVO);
    public List<Gpc_ScheduleVO> findById(String gpc_Gpc_Id);
    public List<Gpc_ScheduleVO> getAll();
    
    //新增團課時同時加入
    public void insertWithGpc_Id(String gpc_Id, Map.Entry singleMap, Connection conn);
    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}