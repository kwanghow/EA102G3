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
	 * @param <T> ���wVO��Class
	 * @param req.getParameterMap()
	 * @param �n�`�J��VO
	 * @return �^�Ǫ`�J�n��VO
	 */
	public static <T> T copyParamToBean(T vo, Map<String, String[]> paramMap) {
		try {
			BeanUtils.populate(vo, paramMap);
			System.out.println("�Ѽƥ]�˦�VO: " + vo);			
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("�Ѽƪ`�JVO����" + e.getMessage());
		}
		
		return vo;
	}
	
	/**
	 * �Ϥ��ШD�Ѽ�Map�O�_��VO�ݩ�(=����d�W)�A�O�h�]�˦�Map�^�ǡF�_�h��ܦbConsole
	 * @return �^�ǲŦX�d��W��Map<Key-Value>
	 */
	public static <T> Map<String, String[]> copyParamToCondMap(T vo, Map<String, String[]> paramMap) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		
		// ���ovo���ݩʭ�
		Set<String> voFieldName = new HashSet<String>();
		Field[] fields = vo.getClass().getDeclaredFields();
		for(Field field: fields) {
			voFieldName.add(field.getName());
		}

		// ���Map��key�O�_�ŦXvo���ݩ�(=��檺���)��
		Set<String> keys = paramMap.keySet();
		for(String key: keys) {
			if(voFieldName.contains(key)) {
				map.put(key, paramMap.get(key)); // �ŦX����iMap��
			}else
				System.out.println("�Ѽ�" + key + "���ŦXVO���ݩʭ�(=����d��W)");
		}

		return map;
	}

}
