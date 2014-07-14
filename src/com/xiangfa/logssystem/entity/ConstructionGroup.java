/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.entity;

import java.io.Serializable;

/**
 * 
 * @author Think
 */
 public class ConstructionGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8945734663590939763L;

	
	
	public ConstructionGroup() {
		
	}

	public ConstructionGroup(Integer pid, String cgname,
			Integer totalWorker, String bosshead) {
		this.pid = pid;
		this.cgname = cgname;
		this.totalWorker = totalWorker;
		this.bosshead = bosshead;
	}

	private Integer cgid;
	
	private Integer pid;

	private String cgname;

	private Integer totalWorker;

	private Integer visible;

	private String bosshead;

	public String getBosshead() {
		return bosshead;
	}

	public void setBosshead(String bosshead) {
		this.bosshead = bosshead;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	/**
	 * @return the cgid
	 */
	public Integer getCgid() {
		return cgid;
	}

	/**
	 * @param cgid
	 *            the cgid to set
	 */
	public void setCgid(Integer cgid) {
		this.cgid = cgid;
	}

	/**
	 * @return the cgname
	 */
	public String getCgname() {
		return cgname;
	}

	/**
	 * @param cgname
	 *            the cgname to set
	 */
	public void setCgname(String cgname) {
		this.cgname = cgname;
	}

	/**
	 * @return the totalWorker
	 */
	public Integer getTotalWorker() {
		return totalWorker;
	}

	/**
	 * @param totalWorker
	 *            the totalWorker to set
	 */
	public void setTotalWorker(Integer totalWorker) {
		this.totalWorker = totalWorker;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	@Override
	public int hashCode() {
		
		return this.cgid.hashCode();
	}
	
}
