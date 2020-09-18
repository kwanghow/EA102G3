package com.gpc.tools;

import java.util.*;

public class FtoB_TimeFormator {
	private String[] frontArr;
	private String ruler = "ABCDEFGHIJKLMNOPQRSTUVWX";
	private TreeMap<String, String> resultMap = new TreeMap();
	
	
	public FtoB_TimeFormator(String[] frontArr) {
		this.frontArr = frontArr;
		Formate(); // �إ᪽߫���ഫ�榡
	}
	
	public String getFirstDateStr() {
		return resultMap.firstKey();
	}
	
	public String getLastDateStr() {
		return resultMap.lastKey();
	}
	
	
	public TreeMap<String, String> getResultMap(){
		return resultMap;
	}
	
	public void Formate(){
		//��z�}�C����		
		java.util.Arrays.sort(frontArr);
		
		//�ΨӸˤ��X�Ӫ�key
		TreeSet <String> keySet = new TreeSet<>();
		
		//�_�lMap�A��P�@�Ѫ���Ƹ˦b�@�_
		HashMap <String, ArrayList<String>> frontMap =new HashMap();
		
		//�ഫ��ƥΪ�processMap
		HashMap <String, char[]> processMap =new HashMap();
		
		//���X�C�@����ơA�J�㦨KeySet,frontMap��processMap�����
		for(int i = 0; i < frontArr.length; i++) {
			String dateKey = frontArr[i].substring(0, 10);
			keySet.add(dateKey);			
		}
		
		//�إ�frontMap��processMap��keySet,�ê�l�ƨ��
		Iterator it = keySet.iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			frontMap.put(key, new ArrayList<String>());
			//���׭n�O24
			char[] tempArr = new char[24];
			//��l�ƥ���'0'
			for(int i = 0; i <tempArr.length; i++) {
				tempArr[i] = '0';
			}
			processMap.put(key, tempArr);
		}
		
		//��e�ݮɶ��N�X��JfrontMap
		for(int i = 0; i < frontArr.length; i++) {
			String dateKey = frontArr[i].substring(0, 10);
			String timeCode = frontArr[i].substring(11, 12);
			frontMap.get(dateKey).add(timeCode);
		}
		
		//�ഫ��ơA�é�JprocessMap
		Iterator itForProcess = keySet.iterator();
		while(itForProcess.hasNext()) {	
			//key�N�O��Ѫ����
			String key = (String)itForProcess.next();
			ArrayList timeCodeList = frontMap.get(key);
			char[] timeCharArr =  processMap.get(key);
			for(int i = 0; i < timeCodeList.size(); i++) {
				//�N�ɶ��N�X�ഫ��index
				int eventIndex = ruler.indexOf((String)timeCodeList.get(i));
				//�N��������m�令'1'
				timeCharArr[eventIndex] = '1';
				
			}
			//��char[]�নString
			String timeStr = new String(timeCharArr);
			//��JMAP��
			resultMap.put(key, timeStr);
			
			//�����T�{��
//			System.out.print(key);
//			System.out.print("****");
//			System.out.print(timeCharArr);
//			System.out.println();
			
		}
		//�����T�{��
		
//		System.out.print("firstKey() = ");
//		System.out.println(resultMap.firstKey());
//		System.out.print("lastKey() = ");
//		System.out.println(resultMap.lastKey());
		

	}

}
