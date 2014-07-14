package com.xiangfa.logssystem.dao;

import java.io.Serializable;
import java.util.List;

import com.xiangfa.logssystem.entity.RecordConstructionGroup;

public interface IRecordConstructionGroup extends IAddUpdateAndDelete<RecordConstructionGroup> {

	/**
	 * ��ѯʩ�������
	 * @param ��־��¼���
	 * @return ���ظ���־��¼������ʩ����;null�������� 
	 */
	public List<RecordConstructionGroup> list(Serializable rid);
}
