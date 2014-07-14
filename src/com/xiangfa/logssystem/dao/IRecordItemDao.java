package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xiangfa.logssystem.entity.RecordItem;

public interface IRecordItemDao  extends IAddUpdateAndDelete<RecordItem>{

	public List<RecordItem> listItem(Serializable projectId);

	/**
	 * ��RecordItem������������ʱͨ��һ��Map�ֱ��������ɾ���Ĳ���
	 * @param data
	 * @return
	 */
	public boolean update(Map<Integer, List<RecordItem>> data);


	public List<RecordItem> listItemByRecords(Serializable recordId);
	
}
