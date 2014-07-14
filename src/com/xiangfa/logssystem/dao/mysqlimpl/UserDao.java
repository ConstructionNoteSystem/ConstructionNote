package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import com.xiangfa.logssystem.dao.IUserDao;
import com.xiangfa.logssystem.entity.Users;

public class UserDao extends BaseDao<Users> implements IUserDao {

	@Override
	public Users login(String username, String password) {
		String sql = "select userId,userName,rightLevel,remark" +
				" from users where username=? and password=? and rightLevel>=?";
		try {
			PreparedStatement psmt = super.getStatement(sql);
			psmt.setString(1, username);
			psmt.setString(2, password);
			psmt.setInt(3, -1);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				Users u = new Users();
				u.setUid(rs.getInt(1));
				u.setUsername(rs.getString(2));
				u.setRightLevel(rs.getInt(3));
				u.setRemark(rs.getString(4));
			return u;
			}
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public boolean add(Users u) throws Exception {
		 String sql = "insert into users (username,password,rightLevel) values (?,?,?)";
		return super.add(sql, u);
	}

	@Override
	public boolean update(Users u) throws Exception {
		String sql = "update Users set username=?,password=?,rightLevel=?,remark=? where uid=?";
		return super.update(sql, u);
	}

	@Override
	public boolean delete(Serializable oid) {
		String sql =  "delete from Users where userId=?";
		return super.delete(sql, oid);
	}
}
