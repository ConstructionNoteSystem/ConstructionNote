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
public class Users implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 6966235375151735521L;

	private Integer uid;
    
    private String username;
    
    private String password;
    
    private String remark;
    
    private Integer rightLevel;

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRightLevel() {
		return rightLevel;
	}

	public void setRightLevel(Integer rightLevel) {
		this.rightLevel = rightLevel;
	}

	/**
     * @return the uid
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * @param uid the uid to set
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
