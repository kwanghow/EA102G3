package com.gpc_List.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import com.gpc.model.GpcVO;


public class Gpc_ListService {
	
	private Gpc_ListDAO_interface dao;
	
	public Gpc_ListService() {
		dao = new Gpc_ListJDBCDAO();
	}
	
	public List<Gpc_ListVO> getAll(){
		return dao.getAll();
	}
	
	public List<Gpc_ListVO> getGpc_Lists(String Id) {
		return dao.findById(Id);
	}
	
	public Gpc_ListVO addGpc_List(String gpc_Id, String member_Id) {
		
		Gpc_ListVO gpc_ListVO = new Gpc_ListVO();
		
		gpc_ListVO.setGpc_Id(gpc_Id);
		gpc_ListVO.setMember_Id(member_Id);
		
		java.sql.Date pay_Init =new java.sql.Date(System.currentTimeMillis());
		java.util.Date date = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 3); //三天後
		java.util.Date pay_Exp_UtilDate = cal.getTime();
		java.sql.Date pay_Exp = new java.sql.Date(pay_Exp_UtilDate.getTime());
		
		gpc_ListVO.setPay_Init(pay_Init);
		gpc_ListVO.setPay_Exp(pay_Exp);
	
		dao.insert(gpc_ListVO);
	
		return gpc_ListVO;
	}
	
	
	public List<Gpc_ListVO> updateGpc_List(String member_Id, //複合主鍵 
			Date pay_Init, Date pay_Exp, Timestamp upl_Stamp, Integer mem_State) {
		
		Gpc_ListVO gpc_ListVO = new Gpc_ListVO();
		
		gpc_ListVO.setMember_Id(member_Id); //複合主鍵 
		gpc_ListVO.setPay_Init(pay_Init);
		gpc_ListVO.setPay_Exp(pay_Exp);
		gpc_ListVO.setUpl_Stamp(upl_Stamp);
		gpc_ListVO.setMem_State(mem_State);
		
		dao.update(gpc_ListVO);
	
		return dao.findById(member_Id);		
	}
	
	//更改狀態用
	public void updateGpc_LsitState(String gpc_Id, String member_Id, /*複合主鍵*/ Integer mem_State) {
		Gpc_ListVO gpc_ListVO = new Gpc_ListVO();
		
		gpc_ListVO.setGpc_Id(gpc_Id); 		/*複合主鍵*/
		System.out.println("sevice裡取到的:" + gpc_Id);
		gpc_ListVO.setMember_Id(member_Id); /*複合主鍵*/
		System.out.println("sevice裡取到的:" + member_Id);
		
		//修改後要更新時間戳記		
		gpc_ListVO.setUpl_Stamp(new Timestamp(System.currentTimeMillis()));
		
		gpc_ListVO.setMem_State(mem_State);
		
		dao.changeState(gpc_ListVO);
		System.out.println("有執行到這一行");
//		return dao.findByCPK(gpc_Id, member_Id);		
	}
	

}
