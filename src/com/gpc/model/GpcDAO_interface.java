package com.gpc.model;

import java.util.*;

public interface GpcDAO_interface {
    public void insert(GpcVO gpcVO);
    
    //�ۼW�D��ȸj�w
    public GpcVO insertWithGpcSch(GpcVO gpcVO, TreeMap<String, String> resultMap);
 
    public void update(GpcVO gpcVO);
    public void delete(String gpc_Id); // �ȨѴ��ե�
    public GpcVO findByPK(String gpc_Id);
    public List<GpcVO> getAll();
    public void changeState(GpcVO gpcVO);
    
    
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}