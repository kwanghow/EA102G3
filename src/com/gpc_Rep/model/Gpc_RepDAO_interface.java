package com.gpc_Rep.model;

import java.util.*;

public interface Gpc_RepDAO_interface {
    public void insert(Gpc_RepVO gpc_RepVO);
    public void update(Gpc_RepVO gpc_RepVO);
    public void delete(String gpc_Rep_Id); // 度ㄑ代刚ノ
    public Gpc_RepVO findByPK(String gpc_Rep_Id);
    public List<Gpc_RepVO> getAll();
    //窾ノ狡琩高(肚把计篈Map)(肚 List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}