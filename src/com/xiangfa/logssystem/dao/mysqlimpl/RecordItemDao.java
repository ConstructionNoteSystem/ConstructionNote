package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xiangfa.logssystem.entity.RecordItem;
import com.xiangfa.logssystem.util.ModifyAction;

public class RecordItemDao extends BaseDao<RecordItem> implements
		com.xiangfa.logssystem.dao.IRecordItemDao {

	@Override
	public boolean add(RecordItem c) throws Exception {
		String sql = "insert into RecordItem (pid,ritemName) values (?,?)";
		return super.add(sql, c);
	}
	
	

	@Override
	public List<RecordItem> listItem(Serializable projectId) {
		String sql = "select ritemId,pid,ritemName from RecordItem where pid=?";
		PreparedStatement psmt = null;
		List<RecordItem> items = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) projectId);
			ResultSet rs = psmt.executeQuery();
			items = new ArrayList<RecordItem>();
			RecordItem item = null;
			while(rs.next()){
				item = new RecordItem();
				item.setRitemId(rs.getInt(1));
				item.setPid(rs.getInt(2));
				item.setRitemName(rs.getString(3));
				items.add(item);
			}
		} catch (Exception e) {
			
		}
		return items;
	}

	@Override
	public boolean update(RecordItem ritem) throws Exception {
		String sql = "update RecordItem set ritemName=? where ritemId=?";
		return super.update(sql, ritem);
	}

	@Override
	public boolean delete(Serializable ritem) {
		String sql = "delete from RecordItem where ritemId=?";
		return super.delete(sql, ritem);
	}

	@Override
	public boolean update(Map<Integer, List<RecordItem>> data) {
		PreparedStatement psmt = null;
		Connection conn = null;
		Savepoint sp = null;
		try {
			psmt = super.getStatement("");
			conn = psmt.getConnection();
			conn.setAutoCommit(false);
			sp = conn.setSavepoint();
			//ÐÂÔö
			List<RecordItem> items = data.get(ModifyAction.INSERT);
			if(null!=items&&items.size()>0){
				String sql = "insert into RecordItem (pid,ritemName) values (?,?)";
				for(RecordItem item : items){
					psmt = null;
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, item.getPid());
					psmt.setString(2, item.getRitemName());
					if(psmt.executeUpdate()==0){
						conn.rollback(sp);
						return false;
					}
				}
			}
			//É¾³ý
			items = data.get(ModifyAction.DELETE);
			if(null!=items&&items.size()>0){
				String sql = "delete from RecordItem where ritemId=?";
				for(RecordItem item : items){
					psmt = null;
					psmt = conn.prepareStatement(sql);
					psmt.setInt(1, item.getRitemId());
					if(psmt.executeUpdate()==0){
						conn.rollback(sp);
						return false;
					}
				}
			}
			//ÐÞ¸Ä
			items = data.get(ModifyAction.MODIFY);
			
			if(null!=items&&items.size()>0){
				String sql = "update RecordItem set ritemName=? where ritemId=?";
				for(RecordItem item : items){
					psmt = null;
					psmt = conn.prepareStatement(sql);
					psmt.setString(1, item.getRitemName());
					psmt.setInt(2, item.getRitemId());
					if(psmt.executeUpdate()==0){
						conn.rollback(sp);
						return false;
					}
				}
			}
			conn.commit();
			return true;
			
		} catch (Exception e) {
			if(null!=sp){
				try {
					conn.rollback(sp);
				} catch (SQLException e1) {
					
				}
			}
			return false;
		}finally{
			super.close(psmt);
		}
	}



	@Override
	public List<RecordItem> listItemByRecords(Serializable recordsId) {
		PreparedStatement psmt = null;
		List<RecordItem> items = null;
		String sql = "select ritemId,ritemName,pid from RecordItem where pid = (select pid from records where rid=?)";
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) recordsId);
			
			ResultSet rs = psmt.executeQuery();
			items = new ArrayList<RecordItem>();
			RecordItem item = null;
			while(rs.next()){
				item = new RecordItem();
				item.setRitemId(rs.getInt(1));
				item.setRitemName(rs.getString(2));
				item.setPid(rs.getInt(3));
				items.add(item);
			}
		} catch (Exception e) {
			
		}finally{
			super.close(psmt);
		}
		
		return items;
	}

}
