package com.gpc_Schedule.model;

import java.sql.Connection;
import java.util.*;

public interface Gpc_ScheduleDAO_interface {
    public void insert(Gpc_ScheduleVO gpc_ScheduleVO);
    public void update(Gpc_ScheduleVO gpc_ScheduleVO);
    public List<Gpc_ScheduleVO> findById(String gpc_Gpc_Id);
    public List<Gpc_ScheduleVO> getAll();
    
    //�s�W�νҮɦP�ɥ[�J
    public void insertWithGpc_Id(String gpc_Id, Map.Entry singleMap, Connection conn);
    
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}