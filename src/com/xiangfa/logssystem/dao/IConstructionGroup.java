package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;

import com.xiangfa.logssystem.entity.ConstructionGroup;

public interface IConstructionGroup extends IAddUpdateAndDelete<ConstructionGroup> {

	public List<ConstructionGroup> listGroup(Serializable recordId);
}
