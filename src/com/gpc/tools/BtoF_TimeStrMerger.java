package com.gpc.tools;

import java.util.*;

public class BtoF_TimeStrMerger {
	
	public static boolean validate(String mergeData) {
			
		
		if(mergeData.indexOf('2')!=-1) return false;
		
		return true;
	}

	public static String getMergeStr(String data1, String data2) {

		String returnData = "";

		char[] dataArray1 = data1.toCharArray();
		char[] dataArray2 = data2.toCharArray();
		
		if(data1==null) {
			return new String(data2);
		}else if(data2==null) {
			return new String(data1);
		}
		
		
		char[] result = new char[dataArray1.length];

		for (int i = 0; i < result.length; i++) {
				
			char mergeData = (char) ((int) dataArray1[i] + (int) dataArray2[i]);
			switch (mergeData) {
				case '`':
					result[i] = '0';
					break;
				case 'a':
					result[i] = '1';
					break;
				case 'b':
					result[i] = '2';
					break;
			}
		}

		returnData = new String(result);

		return returnData;
	}	


}