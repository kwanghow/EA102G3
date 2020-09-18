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
		cal.add(Calendar.DATE, 3); //�T�ѫ�
		java.util.Date pay_Exp_UtilDate = cal.getTime();
		java.sql.Date pay_Exp = new java.sql.Date(pay_Exp_UtilDate.getTime());
		
		gpc_ListVO.setPay_Init(pay_Init);
		gpc_ListVO.setPay_Exp(pay_Exp);
	
		dao.insert(gpc_ListVO);
	
		return gpc_ListVO;
	}
	
	
	public List<Gpc_ListVO> updateGpc_List(String member_Id, //�ƦX�D�� 
			Date pay_Init, Date pay_Exp, Timestamp upl_Stamp, Integer mem_State) {
		
		Gpc_ListVO gpc_ListVO = new Gpc_ListVO();
		
		gpc_ListVO.setMember_Id(member_Id); //�ƦX�D�� 
		gpc_ListVO.setPay_Init(pay_Init);
		gpc_ListVO.setPay_Exp(pay_Exp);
		gpc_ListVO.setUpl_Stamp(upl_Stamp);
		gpc_ListVO.setMem_State(mem_State);
		
		dao.update(gpc_ListVO);
	
		return dao.findById(member_Id);		
	}
	
	//��窱�A��
	public void updateGpc_LsitState(String gpc_Id, String member_Id, /*�ƦX�D��*/ Integer mem_State) {
		Gpc_ListVO gpc_ListVO = new Gpc_ListVO();
		
		gpc_ListVO.setGpc_Id(gpc_Id); 		/*�ƦX�D��*/
		System.out.println("sevice�̨��쪺:" + gpc_Id);
		gpc_ListVO.setMember_Id(member_Id); /*�ƦX�D��*/
		System.out.println("sevice�̨��쪺:" + member_Id);
		
		//�ק��n��s�ɶ��W�O		
		gpc_ListVO.setUpl_Stamp(new Timestamp(System.currentTimeMillis()));
		
		gpc_ListVO.setMem_State(mem_State);
		
		dao.changeState(gpc_ListVO);
		System.out.println("�������o�@��");
//		return dao.findByCPK(gpc_Id, member_Id);		
	}
	

}
