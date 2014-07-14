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
	 * ��ȡ�����б�
	 * @param �û����
	 * @return
	 */
    public List<Project> listProjects(Serializable uid);
    
    /**
     * �����ڷ�Χ��ѯ�����б�
     * @param �û����
     * @param �������
     * @param ��Զ������
     * @return
     */
    public List<Project> listProjectsByDateScope(Serializable uid,java.sql.Date maxDate,java.sql.Date minDate);
    
    public Project get(Serializable pid);
    
    public Serializable add(Serializable p) throws Exception;
}
