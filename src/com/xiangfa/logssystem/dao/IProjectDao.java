/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;

import com.xiangfa.logssystem.entity.Project;

/**
 *
 * @author Think
 */
public interface IProjectDao extends IAddUpdateAndDelete<Project> {
    
	/**
	 * 获取工程列表
	 * @param 用户编号
	 * @return
	 */
    public List<Project> listProjects(Serializable uid);
    
    /**
     * 按日期范围查询工程列表
     * @param 用户编号
     * @param 最近日期
     * @param 较远的日期
     * @return
     */
    public List<Project> listProjectsByDateScope(Serializable uid,java.sql.Date maxDate,java.sql.Date minDate);
    
    public Project get(Serializable pid);
    
    public Serializable add(Serializable p) throws Exception;
}
