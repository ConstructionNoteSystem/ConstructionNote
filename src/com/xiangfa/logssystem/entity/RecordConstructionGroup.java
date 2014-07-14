package com.xiangfa.logssystem.entity;

import java.io.Serializable;

public class RecordConstructionGroup implements Serializable, Comparable<RecordConstructionGroup> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 924407981666725637L;

	private Integer rcgid;

	private ConstructionGroup cgroup;

	private Integer rid;
	
	private String constructionProjectName;
	
	private Integer arrivedNumber;

	private String constructPart;

	private String constructStatus;
	
	

	public RecordConstructionGroup() {
	
	}

	public RecordConstructionGroup(ConstructionGroup cgroup, Integer rid,
			String constructionProjectName, Integer arrivedNumber,
			String constructPart, String constructStatus) {
		this.cgroup = cgroup;
		this.rid = rid;
		this.constructionProjectName = constructionProjectName;
		this.arrivedNumber = arrivedNumber;
		this.constructPart = constructPart;
		this.constructStatus = constructStatus;
	}

	public Integer getRcgid() {
		return rcgid;
	}

	public void setRcgid(Integer rcgid) {
		this.rcgid = rcgid;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getConstructPart() {
		return constructPart;
	}

	public void setConstructPart(String constructPart) {
		this.constructPart = constructPart;
	}

	public String getConstructStatus() {
		return constructStatus;
	}

	public void setConstructStatus(String constructStatus) {
		this.constructStatus = constructStatus;
	}

	public ConstructionGroup getCgroup() {
		return cgroup;
	}

	public void setCgroup(ConstructionGroup cgroup) {
		this.cgroup = cgroup;
	}

	public String getConstructionProjectName() {
		return constructionProjectName;
	}

	public void setConstructionProjectName(String constructionProjectName) {
		this.constructionProjectName = constructionProjectName;
	}

	public Integer getArrivedNumber() {
		return arrivedNumber;
	}

	public void setArrivedNumber(Integer arrivedNumber) {
		this.arrivedNumber = arrivedNumber;
	}

	@Override
	public int compareTo(RecordConstructionGroup arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int hashCode() {
		if(null!=this.getCgroup().getCgname()){
			return this.getCgroup().getCgname().hashCode();
		}
		return this.rcgid.hashCode();
	}
}
