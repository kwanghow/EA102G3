package jdbcUtil_CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_member {
	
	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("member_id".equals(columnName) || "mem_name".equals(columnName)||"mem_sex".equals(columnName)||"mem_account".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("hiredate".equals(columnName))                          // 用於Oracle的date
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("member_id", new String[] { "M001" });
		map.put("mem_name", new String[] { "玲顏" });
		map.put("mem_sex", new String[] { "女" });
		map.put("mem_account", new String[] { "kallie" });
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from member "
				          + jdbcUtil_CompositeQuery_member.get_WhereCondition(map)
				          + "order by member_id";
		System.out.println("●●finalSQL = " + finalSQL);

	}

}
