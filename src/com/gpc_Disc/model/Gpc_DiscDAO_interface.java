package com.gpc_Disc.model;

import java.util.*;

public interface Gpc_DiscDAO_interface {
    public void insert(Gpc_DiscVO gpc_DiscVO);
    public void update(Gpc_DiscVO gpc_DiscVO);
    public void delete(String gpc_Disc_Id); // �ȨѴ��ե�
    public Gpc_DiscVO findByPK(String gpc_Disc_Id);
    public List<Gpc_DiscVO> getAll();
    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//  public List<GpcVO> getAll(Map<String, String[]> map); 
}