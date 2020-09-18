package com.gpc.model;

import java.util.*;

public interface GpcDAO_interface {
    public void insert(GpcVO gpcVO);
    
    //糤龄竕﹚
    public GpcVO insertWithGpcSch(GpcVO gpcVO, TreeMap<String, String> resultMap);
 
    public void update(GpcVO gpcVO);
    public void delete(String gpc_Id); // 度ㄑ代刚ノ
    public GpcVO findByPK(String gpc_Id);
    public List<GpcVO> getAll();
    public void changeState(GpcVO gpcVO);
    
    
    //窾ノ狡琩高(肚把计篈Map)(肚 List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}