package com.gpc_CoachView.model;

import java.sql.Connection;
import java.util.*;

public interface Gpc_CoachViewDAO_interface {
 
    public List<Gpc_CoachViewVO> findPeriod_3MM(String coach_Id, java.sql.Date startDate, java.sql.Date endDate);
    
}