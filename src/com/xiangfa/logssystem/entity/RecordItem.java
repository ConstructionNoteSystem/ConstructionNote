package com.xiangfa.logssystem.entity;

import java.io.Serializable;

public class RecordItem implements Serializable,Comparable<RecordItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6005607831103046293L;

	private Integer ritemId;
	
	private Integer pid;
	
	private String ritemName;

	public Integer getRitemId() {
		return ritemId;
	}

	public void setRitemId(Integer ritemId) {
		this.ritemId = ritemId;
	}

	public String getRitemName() {
		return ritemName;
	}

	public void setRitemName(String ritemName) {
		this.ritemName = ritemName;
	}

	@Override
	public int compareTo(RecordItem o) {
		if(null==o.ritemId){
			if(o.ritemName.compareTo(this.ritemName)>0){
				return 1;
			}else if(o.ritemName.compareTo(this.ritemName)==0){
				return 0;
			}else{
				return -1;
			}
		}
		if(o.ritemId>this.ritemId)
			return 1;
		else if(o.ritemId==this.ritemId){
			return 0;
		}else{
			return -1;
		}
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	@Override
	public int hashCode() {
		if(null!=this.ritemId)
			return this.ritemId.hashCode();
		
		return this.ritemName.hashCode();
		
	}

}
