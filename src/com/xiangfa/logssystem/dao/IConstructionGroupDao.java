/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.dao;


import java.io.Serializable;
import java.util.List;

import com.xiangfa.logssystem.entity.ConstructionGroup;

/**
 *
 * @author Think
 */
public interface IConstructionGroupDao extends IAddUpdateAndDelete<ConstructionGroup> {
    
	public ConstructionGroup get(Serializable cgid);

	
	public List<ConstructionGroup> listGroupByProject(Serializable projectId);
}
