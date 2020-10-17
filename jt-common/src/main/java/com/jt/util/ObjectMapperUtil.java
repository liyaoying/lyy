package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 编辑工具类实现对象与json转化
 */
public class ObjectMapperUtil {
	private static final ObjectMapper MAPPER=new ObjectMapper();
	public static String toJSON(Object target){
		String json=null;
		try {
			json=MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	
	public static <T>T toObject(String json,Class<T> cls){
		T t= (T) new Object();
		try {
			t=(T) MAPPER.readValue(json, cls);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
}
