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
	 * ��ȡһ��PreparedStatement����
	 * @param sql
	 * @return PreparedStatement����
	 * @throws Exception
	 */
	public PreparedStatement getStatement(String sql) throws Exception{
		try {
			Connection conn = getConnection();
			return conn.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			throw new Exception("���������ڡ�������");
		} catch (SQLException e) {
			throw new Exception("���Ӳ�������");
		}
	}
	
	private Serializable generateKey = -1;
	
	 public  Serializable getGenerateKey() {

         return generateKey;
	    }
	
	 
	/**
	 * ��ȡһ��PreparedStatement����
	 * @param sql
	 * @param obj Ҫ�趨�Ĳ���
	 * @return PreparedStatement����
	 * @throws Exception
	 */
	public PreparedStatement getStatementAndSetParams(String sql,Object obj) throws Exception{
	
		PreparedStatement psmt = getStatement(sql);
		
		//Ҫ�趨�Ĳ����ж��ٸ�
		String test = "(?<key>[a-zA-Z_$]+)\\s*(=|like)\\s*[?]"; //Update��
		String regex = sql.indexOf("insert")==-1?test:"(?<key>[a-zA-Z_$]+)\\s*([,]|[)])";//insert��
		
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(sql);
		List<String> keys = new ArrayList<String>();
		
		while(matcher.find()){
			String key = matcher.group("key");
			keys.add(key);			
		}
		//�����趨
		if(obj instanceof Map){
			@SuppressWarnings("unchecked")
			Map<String,Object> values = (Map<String, Object>)obj;
			
			for(int i=0;i<keys.size();i++){
				String key = keys.get(i);
				Object value = values.get(key);
				if(null==value){
					throw new Exception("�����������ȷ");
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
	 * ��ȡҪ�趨������ֵ
	 * @param Ҫ�趨�Ĳ����ļ���
	 * @param ����Ĳ���Я������
	 * @return Ҫ�趨�Ĳ���ֵ������
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
					throw new Exception("�������Ϊ��....");
				}
				objs[i] = value;
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return objs;
	}
	
	/**
	 * ����һ�����������
	 * @param sql
	 * @param obj
	 * @return true,�ɹ�;�������ʧ��
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
	 * ����Ŀ�����
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception 
	 */
	public boolean update(String sql, T obj) throws Exception{
		
		return add(sql,obj);
		
	}

	/**
	 * �ͷ����ݿ���Դ
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
	 * @param ɾ���õ�SQL���
	 * @param ɾ������ı�ʶ��,IDֵ
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
