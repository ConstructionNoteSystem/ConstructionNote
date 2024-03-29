package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;

import com.xiangfa.logssystem.entity.RecordConstructionGroup;

public interface IRecordConstructionGroupDao extends IAddUpdateAndDelete<RecordConstructionGroup> {

	/**
	 * 查询施工组情况
	 * @param 日志记录编号
	 * @return 返回该日志记录的所有施工组;null，不存在 
	 */
	public List<RecordConstructionGroup> list(Serializable rid);
	
	/**
	 * 新境一条施组工施工记录
	 * @param rcg
	 * @return
	 */
	public Integer add(Serializable rcg);
}
