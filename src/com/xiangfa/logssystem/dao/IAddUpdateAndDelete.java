package com.xiangfa.logssystem.dao;

import java.io.Serializable;

public interface IAddUpdateAndDelete<T> {

	public boolean add(T c) throws Exception;
	
	public boolean delete(Serializable oid);
	
	public boolean update(T c) throws Exception;
}
