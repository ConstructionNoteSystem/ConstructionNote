package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiangfa.logssystem.dao.IConstructionGroupDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;

public class ConstructionGroupDao extends BaseDao<ConstructionGroup> implements
		IConstructionGroupDao {

	/**
	 * 按施工组编号查询施工组信息
	 */
	public ConstructionGroup get(Serializable cgid) {
		String sql = "select cgid,cgname,totalWorker,bosshead,pid "
				+ "from ConstructionGroup where cgid=?";
		ConstructionGroup cg = null;
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) cgid);
			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				cg = loadConstructionGroup(rs);
			}
		} catch (Exception e) {

			//e.printStackTrace();
		} finally {
			super.close(psmt);
		}
		return cg;
	}


	public boolean delete(Serializable oid) {
		String sql = "delete from ConstructionGroup where cgid=?";
		if (!super.delete(sql, oid)) {
			ConstructionGroup cg = new ConstructionGroup();
			cg.setCgid((Integer) oid);
			cg.setVisible(0);
			sql = "update ConstructionGroup set visible=? where cgid=?";
			try {
				return super.update(sql, cg);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean add(ConstructionGroup c) throws Exception {
		String sql = "insert into ConstructionGroup (cgname,bosshead,totalWorker,pid) values" +
				"(?,?,?,?)";
		return super.add(sql, c);
	}

	@Override
	public boolean update(ConstructionGroup c) throws Exception {
		String sql = "update ConstructionGroup set cgname=?,bosshead=?,totalWorker=? where cgid=?";
		return super.update(sql, c);
	}

	

	@Override
	public List<ConstructionGroup> listGroupByProject(Serializable projectId) {
		String sql = "select cgid,cgname,totalWorker,bosshead,pid "
				+ "from ConstructionGroup where pid=? and visible=?";
		PreparedStatement psmt = null;
		List<ConstructionGroup> cgs = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) projectId);
			psmt.setInt(2, 1);
			ResultSet rs = psmt.executeQuery();
			cgs = new ArrayList<ConstructionGroup>();
			while(rs.next()){
				cgs.add(loadConstructionGroup(rs));
			}
		} catch (Exception e) {
		
		}
		
		return cgs;
	}

	private ConstructionGroup loadConstructionGroup(ResultSet rs)
			throws SQLException {
		ConstructionGroup cg;
		cg = new ConstructionGroup();
		cg.setCgid(rs.getInt(1));
		cg.setCgname(rs.getString(2));
		cg.setTotalWorker(rs.getInt(3));
		cg.setBosshead(rs.getString(4));
		cg.setPid(rs.getInt(5));
		return cg;
	}

}
