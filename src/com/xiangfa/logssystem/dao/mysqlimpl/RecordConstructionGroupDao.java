package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.xiangfa.logssystem.dao.IRecordConstructionGroupDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;
import com.xiangfa.logssystem.entity.RecordConstructionGroup;

public class RecordConstructionGroupDao extends
		BaseDao<RecordConstructionGroup> implements IRecordConstructionGroupDao {

	@Override
	public List<RecordConstructionGroup> list(Serializable rid) {
		String sql = "select c.cgid,c.pid,c.cgname,c.bosshead,c.totalWorker," +
				"r.rcgid,r.constructPart,r.constructStatus,r.arrivedNumber,r.constructionProjectName " +
				" from ConstructionGroup c inner join RecordConstructionGroup r " +
				"on c.cgid=r.cgid where r.rid=?";
		List<RecordConstructionGroup> rcgs = null;
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) rid);
			ResultSet rs = psmt.executeQuery();
			rcgs = new ArrayList<RecordConstructionGroup>();
			RecordConstructionGroup rcg = null;
			ConstructionGroup cgroup = null;
			while (rs.next()) {
				//
				cgroup = new ConstructionGroup();
				cgroup.setCgid(rs.getInt(1));
				cgroup.setPid(rs.getInt(2));
				cgroup.setCgname(rs.getString(3));
				cgroup.setBosshead(rs.getString(4));
				cgroup.setTotalWorker(rs.getInt(5));
				//
				rcg = new RecordConstructionGroup();
				rcg.setRcgid(rs.getInt(6));
				rcg.setConstructPart(rs.getString(7));
				rcg.setConstructStatus(rs.getString(8));
				rcg.setArrivedNumber(rs.getInt(9));
				rcg.setConstructionProjectName(rs.getString(10));
				rcg.setCgroup(cgroup);
				rcg.setRid((Integer) rid);
				rcgs.add(rcg);
			}
		} catch (Exception e) {

		
		} finally {
			super.close(psmt);
		}
		return rcgs;
	}


	public boolean add(RecordConstructionGroup c){
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean delete(Serializable oid) {
		String sql = "delete from RecordConstructionGroup where rcgid=?";

		return super.delete(sql, oid);
	}

	@Override
	public boolean update(RecordConstructionGroup c) throws Exception {
		throw new Exception("Update is not support.....");
	}


	@Override
	public Integer add(Serializable rcg) {
		RecordConstructionGroup r = (RecordConstructionGroup) rcg;
		String sql = "insert into RecordConstructionGroup (cgid,rid,constructPart,constructStatus,constructionProjectName,arrivedNumber) "
				+ "values (?,?,?,?,?,?)";
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1,r.getCgroup().getCgid());
			psmt.setInt(2, r.getRid());
			psmt.setString(3,r.getConstructPart());
			psmt.setString(4, r.getConstructStatus());
			psmt.setString(5, r.getConstructionProjectName());
			psmt.setInt(6, r.getArrivedNumber());
			psmt.executeUpdate();
			ResultSet rs = psmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		} catch (Exception e) {
			
		}finally{
			super.close(psmt);
		}
		
		return -1;
	}

}
