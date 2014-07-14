/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Think
 */
public  class BaseDao<T>{
	
	private static final String URL = "jdbc:mysql://localhost:3306/cns?useUnicode=true&CcharacterEncoding=utf8";
	
	private static final String DRIVE = "org.gjt.mm.mysql.Driver";
    
	private Connection getConnection() throws SQLException, ClassNotFoundException{
			Class.forName(DRIVE);
			return DriverManager.getConnection(URL,"myadmin","mysql");
	}
	
	/**
	 * 获取一个PreparedStatement对象
	 * @param sql
	 * @return PreparedStatement对象
	 * @throws Exception
	 */
	public PreparedStatement getStatement(String sql) throws Exception{
		try {
			Connection conn = getConnection();
			return conn.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			throw new Exception("驱动不存在。。。。");
		} catch (SQLException e) {
			throw new Exception("联接参数错误");
		}
	}
	
	private Serializable generateKey = -1;
	
	 public  Serializable getGenerateKey() {

         return generateKey;
	    }
	
	 
	/**
	 * 获取一个PreparedStatement对象
	 * @param sql
	 * @param obj 要设定的参数
	 * @return PreparedStatement对象
	 * @throws Exception
	 */
	public PreparedStatement getStatementAndSetParams(String sql,Object obj) throws Exception{
	
		PreparedStatement psmt = getStatement(sql);
		
		//要设定的参数有多少个
		String test = "(?<key>[a-zA-Z_$]+)\\s*(=|like)\\s*[?]"; //Update用
		String regex = sql.indexOf("insert")==-1?test:"(?<key>[a-zA-Z_$]+)\\s*([,]|[)])";//insert用
		
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(sql);
		List<String> keys = new ArrayList<String>();
		
		while(matcher.find()){
			String key = matcher.group("key");
			keys.add(key);			
		}
		//参数设定
		if(obj instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String,Object> values = (Map<String, Object>)obj;
			
			for(int i=0;i<keys.size();i++){
				String key = keys.get(i);
				Object value = values.get(key);
				if(null==value){
					throw new Exception("传入参数不正确");
				}
				psmt.setObject(i+1, value);
			}
		}else{
			Object[] objects = getParams(keys,obj);
			for(int i=0;i<objects.length;i++){
				psmt.setObject(i+1, objects[i]);
			}
		}
		
		return psmt;
	}
	
	/**
	 * 获取要设定参数的值
	 * @param 要设定的参数的集合
	 * @param 传入的参数携带对象
	 * @return 要设定的参数值的数组
	 * @throws Exception
	 */
	private Object[] getParams(List<String> keys,Object obj) throws Exception{
		int size = keys.size();

		Object[] objs = new Object[size];
		Object[] args = null;
		for(int i=0;i<size;i++){
			String key = keys.get(i);
			String methodName = "get"+(char)(key.charAt(0)+("A".charAt(0)-"a".charAt(0)))+key.substring(1);
			try {
				Object value = obj.getClass().getMethod(methodName, (Class<?>[]) args).invoke(obj, args);
				if(null==value){
					throw new Exception("传入参数为空....");
				}
				objs[i] = value;
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return objs;
	}
	
	/**
	 * 插入一个对象的数据
	 * @param sql
	 * @param obj
	 * @return true,成功;否则插入失败
	 * @throws Exception 
	 */
	public boolean add(String sql,T obj) throws Exception{
		if(null==sql||null==obj)
			return false;
		Savepoint sp = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			psmt = getStatementAndSetParams(sql, obj);
			conn = psmt.getConnection();
			conn.setAutoCommit(false);
			sp = psmt.getConnection().setSavepoint();
			int rowCount = psmt.executeUpdate();
			if(rowCount>0){
				ResultSet rs = psmt.getGeneratedKeys();
				if(rs.next()){
					this.generateKey = (Serializable) rs.getObject(1);
					
				}
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			if(null!=sp){
				try {
					if(e.getMessage().contains("Duplicate"))
						conn.rollback(sp);
						//System.out.println("=========================");
						throw new Exception("exists");
					
				} catch (SQLException e1) {
					
				}
				
			}
		}finally{
			close(psmt);
		}
		return false;
	}
	
	/**
	 * 更新目标对象
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public boolean update(String sql, T obj) throws Exception{
		
		return add(sql,obj);
		
	}

	/**
	 * 释放数据库资源
	 * @param psmt
	 */
	public void close(Statement psmt){
		
		if(null!=psmt){
			try {
				Connection conn = psmt.getConnection();
				conn.close();
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
	}

	/**
	 * @param 删除用的SQL语句
	 * @param 删除对象的标识符,ID值
	 */
	public boolean delete(String sql,Serializable oid) {
		if(null==oid)
			return false;
		Savepoint sp = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			psmt = getStatement(sql);
			conn = psmt.getConnection();
			conn.setAutoCommit(false);
			sp = psmt.getConnection().setSavepoint();
			psmt.setObject(1, oid);
			int rowCount = psmt.executeUpdate();
			if(rowCount>0){
				conn.commit();
				return true;
			}
		} catch (Exception e) {
			if(null!=sp){
				try {
					conn.rollback(sp);
				} catch (SQLException e1) {
					
				}
			}
		}finally{
			close(psmt);
		}
		return false;
	}
}
