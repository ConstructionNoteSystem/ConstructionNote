package com.xiangfa.logssystem.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.xiangfa.logssystem.dao.mysqlimpl.BaseDao;

public final class RecordsAddtionalUtil {
	BaseDao<Map<String, String>> bdao = new BaseDao<Map<String, String>>();
	public void upload(String fileNames,Integer pid,String logDate) throws Exception{
		if(null!=fileNames&&(!"".equals(fileNames))&&null!=pid&&null!=logDate){
			logDate = logDate.substring(0,4)+"-"+logDate.substring(4,6)+"-"+logDate.substring(6,8);
			Map<String,String> map = new HashMap<String,String>();
			map.put("uriAddtional", fileNames);
			map.put("pid", pid+"");
			map.put("logDate", logDate);
			String existsUri = getUriAddtional(map);
			
			if(!("".equals(existsUri) || null == existsUri)){
				String[] files = FileNameSwitch.toClient(fileNames);
				for(String file : files){
					if(existsUri.contains(file)){
						continue;
					}
					existsUri +=file+":";
				}
			}else{
				existsUri = fileNames;
			}
			
			String sql = "update Records r set uriAddtional=? where pid=? and logDate=?";
			map.put("uriAddtional", existsUri);
			if(!bdao.update(sql, map)){
				throw new  Exception("更新失败....");
			}
		}
	}
	
	public String getUriAddtional(Map<String,String> args){
		String sql = "select uriAddtional from records where pid=? and logDate=?";
		PreparedStatement psmt = null;
		try {
			psmt = bdao.getStatementAndSetParams(sql, args);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}
		} catch (Exception e) {
		
		}finally{
			bdao.close(psmt);
		}
		return "";
	}
	
	public boolean delete(Map<String,String> args) throws Exception{
		
		String existsUri = getUriAddtional(args);
		String delete = args.get("delete");
		existsUri = existsUri.replace(delete+":", "");
		args.put("uriAddtional", existsUri);
		String sql = "update Records r set uriAddtional=? where pid=? and logDate=?";
		if(!bdao.update(sql, args)){
			throw new  Exception("更新失败....");
		}
		return true;
	}
}
