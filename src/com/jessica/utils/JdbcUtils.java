package com.jessica.utils;

import java.util.Map;
import java.util.Set;

public class JdbcUtils {

	/**
	 * 將參數以Map類型轉化成SQL搜尋條件(只限Course使用)
	 * @param req.getParameterMap()
	 * @return SQL搜尋條件字串
	 */
	public static String condMapToSqlForOracle(Map<String, String[]> conditionMap) {
		StringBuffer sqlCond = new StringBuffer();

		Set<String> keys = conditionMap.keySet();
		for(String key: keys) {
			for(int i=0; i<conditionMap.get(key).length; i++) {
				String value = conditionMap.get(key)[i];
				if(value != null && value.trim().length() != 0) {					
					String cond = null;
					
					String colName = key.toUpperCase();
					String colValue = value.toUpperCase().trim();					
					if("COURSE_ID".equals(colName) || "COACH_ID".equals(colName) || "EXP_ID".equals(colName) || "STATE".equals(colName)) {
						cond = colName + " = '" + colValue + "'";
					}else if("CNAME".equals(colName) || "INTRO".equals(colName) || "LOC".equals(colName) ) {
						cond = colName + " like '%" + colValue + "%'";
					}else {
						throw new RuntimeException("出錯拉!!(by ParamUtils)");
					}
					
					sqlCond.append( ((sqlCond.length() == 0)? " where " : " and ") + cond);
				}					
			}

		}
		return sqlCond.toString();
	}
	
//	public static void main(String[] args) {
//		Map map = new HashMap<String, String[]>();
//		map.put("key1", new String[] {"value1"});
//		map.put("course_id", new String[] {"value2"});
//		map.put("cname", new String[] {"value3_1", "value3_2"});
//		map.put("loc", new String[] {null});
//		
//		Map newMap = copyParamToCondMap(new CourseVo(), map);
//		System.out.println(newMap);
//		
//		System.out.println(condMapToSqlForOracle(newMap));
//	}
	
}
