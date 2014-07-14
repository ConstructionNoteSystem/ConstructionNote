package com.xiangfa.logssystem.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateUtil {
	
	
	private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 将字符日期转为yyyy-MM-dd格式的字符串
	 * @param date
	 * @return 日期字符串
	 */
	public final static String fromDate(java.util.Date date){
		try{
			return sdf2.format(date);
		}catch(Exception e){
			return sdf1.format(date);
		}
	}
	
	public final static java.util.Date fromString(String dateStr){
		try{
			return sdf2.parse(dateStr);
		}catch(Exception e){
			try {
				return sdf1.parse(dateStr);
			} catch (ParseException e1) {
				
			}
		}
		return null;
	}
}
