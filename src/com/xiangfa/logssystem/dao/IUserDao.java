/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xiangfa.logssystem.dao;

import com.xiangfa.logssystem.entity.Users;

/**
 *
 * @author Think
 */
public interface IUserDao extends IAddUpdateAndDelete<Users>{
    
	/**
	 * ��¼
	 * @param username
	 * @param password
	 * @return
	 */
    public Users login(String username,String password);
    
}
