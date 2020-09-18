package com.jessica.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

public class ParamUtils {
	
	/**
	 * Apache API BeanUtils
	 * https://commons.apache.org/proper/commons-beanutils/apidocs/org/apache/commons/beanutils/BeanUtils.html
	 * @param <T> 指定VO的Class
	 * @param req.getParameterMap()
	 * @param 要注入的VO
	 * @return 回傳注入好的VO
	 */
	public static <T> T copyParamToBean(T vo, Map<String, String[]> paramMap) {
		try {
			BeanUtils.populate(vo, paramMap);
			System.out.println("參數包裝成VO: " + vo);			
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("參數注入VO失敗" + e.getMessage());
		}
		
		return vo;
	}
	
	/**
	 * 區分請求參數Map是否為VO屬性(=表格攔名)，是則包裝成Map回傳；否則顯示在Console
	 * @return 回傳符合攔位名的Map<Key-Value>
	 */
	public static <T> Map<String, String[]> copyParamToCondMap(T vo, Map<String, String[]> paramMap) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		// 取得vo的屬性值
		Set<String> voFieldName = new HashSet<String>();
		Field[] fields = vo.getClass().getDeclaredFields();
		for(Field field: fields) {
			voFieldName.add(field.getName());
		}

		// 比對Map的key是否符合vo的屬性(=表格的欄位)值
		Set<String> keys = paramMap.keySet();
		for(String key: keys) {
			if(voFieldName.contains(key)) {
				map.put(key, paramMap.get(key)); // 符合的放進Map裡
			}else
				System.out.println("參數" + key + "不符合VO的屬性值(=表格攔位名)");
		}

		return map;
	}

}
