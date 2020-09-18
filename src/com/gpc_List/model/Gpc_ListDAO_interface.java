package com.gpc_List.model;

import java.util.*;

import com.gpc.model.GpcVO;

public interface Gpc_ListDAO_interface {
    public void insert(Gpc_ListVO gpc_ListVO);
    public void update(Gpc_ListVO gpc_ListVO);
    public List<Gpc_ListVO> findById(String Id);
   
    public List<Gpc_ListVO> getAll();
    
    public Gpc_ListVO findByCPK(String gpc_Id, String member_Id); // 複合主鍵
    
    public void changeState(Gpc_ListVO gpc_ListVO);
    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}