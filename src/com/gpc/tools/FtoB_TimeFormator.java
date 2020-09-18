package com.gpc.tools;

import java.util.*;

public class FtoB_TimeFormator {
	private String[] frontArr;
	private String ruler = "ABCDEFGHIJKLMNOPQRSTUVWX";
	private TreeMap<String, String> resultMap = new TreeMap();
	
	
	public FtoB_TimeFormator(String[] frontArr) {
		this.frontArr = frontArr;
		Formate(); // 建立後直接轉換格式
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
		//整理陣列順序		
		java.util.Arrays.sort(frontArr);
		
		//用來裝切出來的key
		TreeSet <String> keySet = new TreeSet<>();
		
		//起始Map，把同一天的資料裝在一起
		HashMap <String, ArrayList<String>> frontMap =new HashMap();
		
		//轉換資料用的processMap
		HashMap <String, char[]> processMap =new HashMap();
		
		//取出每一筆資料，彙整成KeySet,frontMap跟processMap都能用
		for(int i = 0; i < frontArr.length; i++) {
			String dateKey = frontArr[i].substring(0, 10);
			keySet.add(dateKey);			
		}
		
		//建立frontMap跟processMap的keySet,並初始化兩者
		Iterator it = keySet.iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			frontMap.put(key, new ArrayList<String>());
			//長度要是24
			char[] tempArr = new char[24];
			//初始化先塞'0'
			for(int i = 0; i <tempArr.length; i++) {
				tempArr[i] = '0';
			}
			processMap.put(key, tempArr);
		}
		
		//把前端時間代碼塞入frontMap
		for(int i = 0; i < frontArr.length; i++) {
			String dateKey = frontArr[i].substring(0, 10);
			String timeCode = frontArr[i].substring(11, 12);
			frontMap.get(dateKey).add(timeCode);
		}
		
		//轉換資料，並放入processMap
		Iterator itForProcess = keySet.iterator();
		while(itForProcess.hasNext()) {	
			//key就是當天的日期
			String key = (String)itForProcess.next();
			ArrayList timeCodeList = frontMap.get(key);
			char[] timeCharArr =  processMap.get(key);
			for(int i = 0; i < timeCodeList.size(); i++) {
				//將時間代碼轉換為index
				int eventIndex = ruler.indexOf((String)timeCodeList.get(i));
				//將對應的位置改成'1'
				timeCharArr[eventIndex] = '1';
				
			}
			//把char[]轉成String
			String timeStr = new String(timeCharArr);
			//放入MAP中
			resultMap.put(key, timeStr);
			
			//除錯確認用
//			System.out.print(key);
//			System.out.print("****");
//			System.out.print(timeCharArr);
//			System.out.println();
			
		}
		//除錯確認用
		
//		System.out.print("firstKey() = ");
//		System.out.println(resultMap.firstKey());
//		System.out.print("lastKey() = ");
//		System.out.println(resultMap.lastKey());
		

	}

}
