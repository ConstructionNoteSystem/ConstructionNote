/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.dao;

import com.xiangfa.logssystem.entity.Records;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author Think
 */
public interface IRecordsDao  extends IAddUpdateAndDelete<Records>{
    
	/**
	 * ��ѯ��־��¼
	 * @param ��־��¼���
	 * @return null,�����ڣ����ض�Ӧ����־��¼
	 */
    public Records get(Serializable recordsId);
    
    /**
     * ���ݹ��̲�ѯ��־��Ϣ(�������ڣ����)
     * @param projectId
     * @return
     */
    public Map<String,Integer> getRecordTree(Serializable projectId);
    
    /**
     * ��ʱ�䷶Χ�͹������е�һ�������ѯĳ�����̵���־(�������ڣ����)
     * @param ritemId
     * @param content
     * @param maxDate
     * @param minDate
     * @return
     */
    public Map<String,Integer> getRecordTree(Integer ritemId, String content,java.sql.Date maxDate,java.sql.Date minDate);
    
    /**
     * ����һ����־
     * @param projectId
     * @param logDate
     * @return
     * @throws Exception
     */
    public Integer add(Serializable projectId,java.sql.Date logDate) throws Exception;

    /**
     * �����̵�ĳһ�����������ģ����ѯ��־(�������ڣ����)
     * @param ritemId
     * @param content
     * @return
     */
	public Map<String, Integer> getRecordTree(Integer ritemId, String content); 
	
	/**
	 * ��ѯ��־��������
	 * @param recordId
	 * @param ritemId
	 * @return null,û�м�¼;���򷵻���־�������ݵı��
	 */
	public Integer getItemContent(Serializable recordId,Serializable ritemId);
	
	/**
	 * ������־��Ż�ȡ�ļ������б�
	 * @param recordsId
	 * @return
	 */
	public String getUriAddtional(Serializable recordsId);
}
