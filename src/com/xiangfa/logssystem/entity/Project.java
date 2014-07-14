/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;
import java.util.TreeSet;


/**
 *
 * @author Think
 */
public class Project implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8983357942786702526L;

	public Project(String projectName, String buildingUnits,
			String workingSpace, String designUnits, String constructionUnits,
			String supervisingUnits, String superviserName, Date createDate) throws Exception {
		
		if(null==projectName||null==buildingUnits||null==workingSpace
				||null==designUnits||null==constructionUnits||null==supervisingUnits
				||null==superviserName||null==createDate)
			throw new Exception("传入的参数不能为空");
		this.projectName = projectName;
		this.buildingUnits = buildingUnits;
		this.workingSpace = workingSpace;
		this.designUnits = designUnits;
		this.constructionUnits = constructionUnits;
		this.supervisingUnits = supervisingUnits;
		this.superviserName = superviserName;
		this.createDate = createDate;
	}
	
	

	public Project() {
	
	}



	private Integer pid;
	
	private Integer userId;
    
    private String projectName;
    
    private String buildingUnits;
    
    private String workingSpace;
    
    private String designUnits;
    
    private String constructionUnits;
    
    private String supervisingUnits;
    
    private String superviserName;
   
    private java.sql.Date createDate;

    private Set<RecordItem> recordItems = new TreeSet<RecordItem>();
    
	public Set<RecordItem> getRecordItems() {
		return recordItems;
	}

	public void setRecordItems(Set<RecordItem> recordItems) {
		this.recordItems = recordItems;
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
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the buildingUnits
     */
    public String getBuildingUnits() {
        return buildingUnits;
    }

    /**
     * @param buildingUnits the buildingUnits to set
     */
    public void setBuildingUnits(String buildingUnits) {
        this.buildingUnits = buildingUnits;
    }

    /**
     * @return the workingSpace
     */
    public String getWorkingSpace() {
        return workingSpace;
    }

    /**
     * @param workingSpace the workingSpace to set
     */
    public void setWorkingSpace(String workingSpace) {
        this.workingSpace = workingSpace;
    }

    /**
     * @return the designUnits
     */
    public String getDesignUnits() {
        return designUnits;
    }

    /**
     * @param designUnits the designUnits to set
     */
    public void setDesignUnits(String designUnits) {
        this.designUnits = designUnits;
    }

    /**
     * @return the constructingUnits
     */
    public String getConstructionUnits() {
        return constructionUnits;
    }

    /**
     * @param constructingUnits the constructingUnits to set
     */
    public void setConstructionUnits(String constructionUnits) {
        this.constructionUnits = constructionUnits;
    }

    /**
     * @return the supervisingUnits
     */
    public String getSupervisingUnits() {
        return supervisingUnits;
    }

    /**
     * @param supervisingUnits the supervisingUnits to set
     */
    public void setSupervisingUnits(String supervisingUnits) {
        this.supervisingUnits = supervisingUnits;
    }

    /**
     * @return the superviserName
     */
    public String getSuperviserName() {
        return superviserName;
    }

    /**
     * @param superviserName the superviserName to set
     */
    public void setSuperviserName(String superviserName) {
        this.superviserName = superviserName;
    }

    /**
     * @return the createDate
     */
    public java.sql.Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(java.sql.Date createDate) {
        this.createDate = createDate;
    }



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
