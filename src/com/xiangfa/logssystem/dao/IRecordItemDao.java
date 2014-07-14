package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xiangfa.logssystem.entity.RecordItem;

public interface IRecordItemDao  extends IAddUpdateAndDelete<RecordItem>{

	public List<RecordItem> listItem(Serializable projectId);

	/**
	 * 对RecordItem进行批量操作时通过一个Map分别进行增、删、改操作
	 * @param data
	 * @return
	 */
	public boolean update(Map<Integer, List<RecordItem>> data);


	public List<RecordItem> listItemByRecords(Serializable recordId);
	
}
