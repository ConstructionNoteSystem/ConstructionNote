/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Think
 */
public class Records implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1441815639275385211L;

	private Integer rid;
    
    private Integer pid;
    
    private java.sql.Date logDate;
    
    private Weather weather;
    
    private Map<RecordItem,String> itemRecords = new HashMap<RecordItem,String>();

    private String uriAddtional;
    
    private Set<RecordConstructionGroup> constructionGroup = new HashSet<RecordConstructionGroup>();

    /**
     * @return the rid
     */
    public Integer getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(Integer rid) {
        this.rid = rid;
    }

    /**
     * @return the pid
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * @return the logDate
     */
    public java.sql.Date getLogDate() {
        return logDate;
    }

    /**
     * @param logDate the logDate to set
     */
    public void setLogDate(java.sql.Date logDate) {
        this.logDate = logDate;
    }

    /**
     * @return the weather
     */
    public Weather getWeather() {
        return weather;
    }

    /**
     * @param weather the weather to set
     */
    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    /**
     * @return the itemRecords
     */
    public Map<RecordItem,String> getItemRecords() {
        return itemRecords;
    }

    /**
     * @param itemRecords the itemRecords to set
     */
    public void setItemRecords(Map<RecordItem,String> itemRecords) {
    	this.itemRecords = itemRecords;
    }

    /**
     * @return the uriAddtional
     */
    public String getUriAddtional() {
        return uriAddtional;
    }

    /**
     * @param uriAddtional the uriAddtional to set
     */
    public void setUriAddtional(String uriAddtional) {
        this.uriAddtional = uriAddtional;
    }

	public Set<RecordConstructionGroup> getConstructionGroup() {
		return constructionGroup;
	}

	public void setConstructionGroup(Set<RecordConstructionGroup> constructionGroup) {
		this.constructionGroup = constructionGroup;
	}

}
