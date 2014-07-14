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
	 * 查询日志记录
	 * @param 日志记录编号
	 * @return null,不存在；返回对应的日志记录
	 */
    public Records get(Serializable recordsId);
    
    /**
     * 根据工程查询日志信息(建立日期，编号)
     * @param projectId
     * @return
     */
    public Map<String,Integer> getRecordTree(Serializable projectId);
    
    /**
     * 按时间范围和工程其中的一个分类查询某个工程的日志(建立日期，编号)
     * @param ritemId
     * @param content
     * @param maxDate
     * @param minDate
     * @return
     */
    public Map<String,Integer> getRecordTree(Integer ritemId, String content,java.sql.Date maxDate,java.sql.Date minDate);
    
    /**
     * 新增一条日志
     * @param projectId
     * @param logDate
     * @return
     * @throws Exception
     */
    public Integer add(Serializable projectId,java.sql.Date logDate) throws Exception;

    /**
     * 按工程的某一个分类的内容模糊查询日志(建立日期，编号)
     * @param ritemId
     * @param content
     * @return
     */
	public Map<String, Integer> getRecordTree(Integer ritemId, String content); 
	
	/**
	 * 查询日志分类内容
	 * @param recordId
	 * @param ritemId
	 * @return null,没有记录;否则返回日志分类内容的编号
	 */
	public Integer getItemContent(Serializable recordId,Serializable ritemId);
	
	/**
	 * 根据日志编号获取文件下载列表
	 * @param recordsId
	 * @return
	 */
	public String getUriAddtional(Serializable recordsId);
}
