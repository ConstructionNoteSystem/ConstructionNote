package com.xiangfa.logssystem.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.xiangfa.logssystem.entity.ConstructionGroup;
import com.xiangfa.logssystem.entity.Weather;

public final class JSONUtil {

	public static final String fromObject(Object obj) {

		if(null==obj){
			return "";
		}
		Method[] methods = obj.getClass().getDeclaredMethods();
		int len = methods.length;
		if (len == 0) {
			return "";
		} else {
			Object[] objects = null;
			// 初始化一个StringBuilder
			StringBuilder sb = new StringBuilder(64);
			int index = 0;
			sb.append("{");
			for (Method m : methods) {
				
				String methodName = m.getName();
			
				if (methodName.contains("get")) {
					index++;
					if (index>1) {
						sb.append(",");
					}
					String key = methodName.substring(3);
					
					key = (char) (key.charAt(0) - ((int) 'A' - (int) 'a'))
							+ key.substring(1);
					sb.append("'").append(key).append("':");
					try {
						Object value = m.invoke(obj, objects);
						value = value == null ? "" : value;
						//特殊做法，本项目存在的组合类 ConstructionGroup，Weather
						if (value instanceof ConstructionGroup||value instanceof Weather) {
							sb.append(fromObject(value));
						} else {
							sb.append("'").append(value).append("'");
						}
					
					} catch (IllegalAccessException e) {
						
					} catch (IllegalArgumentException e) {
						
					} catch (InvocationTargetException e) {
						
					}
				}
			}
			sb.append("}");
			return sb.toString();
		}

	}
}
