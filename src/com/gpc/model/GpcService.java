package com.gpc.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.exptype.model.ExptypeVO;


public class GpcService {
	
	private GpcDAO_interface dao;
	
	public GpcService() {
		dao = new GpcJDBCDAO();
	}
	
	public List<GpcVO> getAll(){
		return dao.getAll();
	}
	
	public GpcVO getOneGpc(String gpc_Id) {
		return dao.findByPK(gpc_Id);
	}
	
	public GpcVO addGpc(String coach_Id, String exp_Id, String gpc_Name, String address, String intro, 
			byte[] pic1, byte[] pic2, byte[] pic3 ,
			Integer price, java.sql.Date pay_Start, java.sql.Date gpc_Start, Integer mem_Min, Integer mem_Max) {
		
		GpcVO gpcVO = new GpcVO();
		

		gpcVO.setCoach_Id(coach_Id);
		gpcVO.setExp_Id(exp_Id);
		gpcVO.setGpc_Name(gpc_Name);
		gpcVO.setAddress(address);
		gpcVO.setIntro(intro);
		//�Ϥ�
		gpcVO.setPic1(pic1);
		gpcVO.setPic2(pic2);
		gpcVO.setPic3(pic3);
		//
		gpcVO.setPrice(price);
		gpcVO.setPay_Start(pay_Start);
		gpcVO.setGpc_Start(gpc_Start);
		gpcVO.setMem_Min(mem_Min);
		gpcVO.setMem_Max(mem_Max);
	
		dao.insert(gpcVO);
	
		return gpcVO;
	}
	
	public GpcVO updateGpc(String gpc_Id, //PK 
			String gpc_Name, String address, String intro, 
			byte[] pic1, byte[] pic2, byte[] pic3 ,
			Integer price, java.sql.Date pay_Start, Integer mem_Min, Integer mem_Max) {
		
		GpcVO gpcVO = new GpcVO();
		
		gpcVO.setGpc_Id(gpc_Id); // PK
		gpcVO.setGpc_Name(gpc_Name);
		gpcVO.setAddress(address);
		gpcVO.setIntro(intro);
		//�Ϥ�
		gpcVO.setPic1(pic1);
		gpcVO.setPic2(pic2);
		gpcVO.setPic3(pic3);
		//
		gpcVO.setPrice(price);
		gpcVO.setPay_Start(pay_Start);
		/*update�]�p�������gpc_Start,�]���O�@�s�ɶ����Ĥ@��*/
		gpcVO.setMem_Min(mem_Min);
		gpcVO.setMem_Max(mem_Max);
		//�ק��n��s�ɶ��W�O		
		gpcVO.setUpl_Stamp(new Timestamp(System.currentTimeMillis()));
	
		dao.update(gpcVO);
		
		return dao.findByPK(gpc_Id);		
	}
	
	//��窱�A��
	public GpcVO updateGpcState(String gpc_Id, /*PK*/ Integer gpc_State) {
		
		GpcVO gpcVO = new GpcVO();
		
		gpcVO.setGpc_Id(gpc_Id); // PK
		gpcVO.setGpc_State(gpc_State);
		//�ק��n��s�ɶ��W�O		
		gpcVO.setUpl_Stamp(new Timestamp(System.currentTimeMillis()));	
		
		dao.changeState(gpcVO);
		
		return dao.findByPK(gpc_Id);		
	}
	
	//�ۼW�D��ȸj�w
	public GpcVO insertWithGpcSch(GpcVO gpcVO, TreeMap<String, String> resultMap) {
		dao.insertWithGpcSch(gpcVO, resultMap);
		return gpcVO;
	}
	
	
	

}
