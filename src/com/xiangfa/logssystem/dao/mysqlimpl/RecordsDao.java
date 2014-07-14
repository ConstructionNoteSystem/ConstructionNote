package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.xiangfa.logssystem.dao.IRecordItemDao;
import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;
import com.xiangfa.logssystem.entity.RecordConstructionGroup;
import com.xiangfa.logssystem.entity.RecordItem;
import com.xiangfa.logssystem.entity.Records;
import com.xiangfa.logssystem.entity.Weather;

public class RecordsDao extends BaseDao<Records> implements IRecordsDao {

	@Override
	public Records get(Serializable recordsId) {
		if(null==recordsId){
			return new Records();
		}
		String sql = "select amWeatherDesc,pmWeatherDesc,hCentigrade,lCentigrade,pid,logDate "
				+ "from Records where rid = ?";

		PreparedStatement psmt = null;
		Records r = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) recordsId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				r = new Records();
				Weather w = new Weather();
				w.setAmWeatherDesc(rs.getString(1));
				w.setPmWeatherDesc(rs.getString(2));
				w.sethCentigrade(rs.getDouble(3));
				w.setlCentigrade(rs.getDouble(4));
				r.setWeather(w);
				r.setPid(5);
				r.setLogDate(rs.getDate(6));
			}
			Connection conn = psmt.getConnection();
			// 完善Map<RecordItem,String>；数据
			// Step 1 获取所有的RecordItem
			IRecordItemDao ridao = new RecordItemDao();
			List<RecordItem> items = ridao.listItemByRecords(recordsId);
			// Step 2获取对应RecordItem的内容
			if (items.size() > 0) {
				String sql1 = "select content from ItemContent where ritemId=? and rid=?";
				for (RecordItem item : items) {
					psmt = null;
					rs = null;
					psmt = conn.prepareStatement(sql1);
					psmt.setInt(1, item.getRitemId());
					psmt.setInt(2, (Integer) recordsId);
					rs = psmt.executeQuery();
					String result = "";
					if (rs.next()) {
						result = rs.getString(1);
					}
					r.getItemRecords().put(item, result);
				}
			}

			// 完善 Set<RecordConstructionGroup>数据;
			psmt = null;
			rs = null;
			String sql3 = "select cg.cgid,cg.cgname,cg.bosshead,cg.totalWorker,"
					+ "rc.rcgid,rc.rid,rc.constructPart,rc.constructStatus"
					+ ",rc.constructionProjectName,rc.arrivedNumber from constructiongroup cg right join"
					+ " recordconstructiongroup rc on cg.cgid=rc.cgid where rc.rid=?";
			psmt = conn.prepareStatement(sql3);
			psmt.setInt(1, (Integer) recordsId);
			rs = psmt.executeQuery();
			ConstructionGroup cg = null;
			RecordConstructionGroup rcg = null;
			while (rs.next()) {
				cg = new ConstructionGroup();
				cg.setCgid(rs.getInt(1));
				cg.setCgname(rs.getString(2));
				cg.setBosshead(rs.getString(3));
				cg.setTotalWorker(rs.getInt(4));
				rcg = new RecordConstructionGroup();
				rcg.setRcgid(rs.getInt(5));
				rcg.setRid(rs.getInt(6));
				rcg.setConstructPart(rs.getString(7));
				rcg.setConstructStatus(rs.getString(8));
				rcg.setConstructionProjectName(rs.getString(9));
				rcg.setArrivedNumber(rs.getInt(10));
				rcg.setCgroup(cg);
				r.getConstructionGroup().add(rcg);
			}
			if(null!=recordsId){
				r.setRid((Integer) recordsId);
			}
			rs.close();
		} catch (Exception e) {
		
		} finally {
			super.close(psmt);
		}
		return r;
	}

	private Map<String, Integer> getRecord(Serializable projectId,
			Date maxDate, Date minDate) {
		String sql = "select rid,logDate from Records where pid=? ";
		if (null != maxDate || null != minDate) {
			if (null != maxDate && null != minDate) {
				sql += " and (createDate between ? and ?) ";
			} else if (null != minDate && null == maxDate) {
				sql += " and createDate>? ";
			} else {
				sql += " and createDate<? ";
			}
		}
		sql += " order by logDate";
		PreparedStatement psmt = null;
		Map<String, Integer> date = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) projectId);
			if (null != maxDate || null != minDate) {
				if (null != maxDate && null != minDate) {
					psmt.setDate(2, minDate);
					psmt.setDate(3, maxDate);
				} else if (null != minDate && null == maxDate) {
					psmt.setDate(2, minDate);
				} else {
					psmt.setDate(2, maxDate);
				}
			}
			ResultSet rs = psmt.executeQuery();
			date = new TreeMap<String, Integer>();
			while (rs.next()) {
				Integer value = rs.getInt(1);
				String key = rs.getDate(2).toString();
				date.put(key, value);
			}
		} catch (Exception e) {

		} finally {
			super.close(psmt);
		}
		return date;
	}

	@Override
	public boolean add(Records r) throws Exception {
		String sql = "insert into Records (pid,logDate,amWeatherDesc,pmWeatherDesc,hCentigrade"
				+ "lCentigrade) values (?,?,?,?,?,?,?)";
		return super.add(sql, r);
	}

	@Override
	public Integer add(Serializable projectId, java.sql.Date logDate)
			throws Exception {
		String sql = "insert into Records(pid,logDate) values (?,?)";
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) projectId);
			psmt.setDate(2, logDate);
			if (psmt.executeUpdate() > 0) {
				ResultSet rs = psmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Duplicate")) {
				throw new Exception("exists");
			}
		}
		return -1;
	}

	@Override
	public boolean update(Records r) {
		
		Weather weather = r.getWeather();
		if(null!=weather){
			weather.setAmWeatherDesc(weather.getAmWeatherDesc()==null?"":weather.getAmWeatherDesc());
			weather.setPmWeatherDesc(weather.getPmWeatherDesc()==null?"":weather.getPmWeatherDesc());
			weather.setlCentigrade(weather.getlCentigrade()==null?0:weather.getlCentigrade());
			weather.sethCentigrade(weather.gethCentigrade()==null?0:weather.gethCentigrade());
			r.setWeather(weather);
		}
		if (r.getItemRecords().size() == 0&&r.getConstructionGroup().size()==0) {
			String sql = "update Records set amWeatherDesc=?,pmWeatherDesc=?"
					+ ",hCentigrade=?,lCentigrade=? where rid=?";
			Map<String, String> map = new HashMap<String, String>();
			map.put("amWeatherDesc", weather.getAmWeatherDesc());
			map.put("pmWeatherDesc", weather.getPmWeatherDesc());
			map.put("hCentigrade", weather.gethCentigrade()+"");
			map.put("lCentigrade", weather.getlCentigrade()+"");
			map.put("rid",r.getRid()+"");
			
			PreparedStatement psmt = null;
			try {
				psmt = super.getStatementAndSetParams(sql, map);
				psmt.executeUpdate();
				return true;
			} catch (Exception e) {
				
			}finally{
				super.close(psmt);
			}
			return false;
		}
		// 如果有分类信息
		PreparedStatement psmt = null;
		Connection conn = null;
		Savepoint sp = null;

		Map<RecordItem, String> map = r.getItemRecords();
		Iterator<RecordItem> iter = map.keySet().iterator();

		try {
			conn = super.getStatement("").getConnection();
			conn.setAutoCommit(false);
			sp = conn.setSavepoint();
			String insert = "insert into itemcontent (rid,ritemId,content) values (?,?,?)";
			String update = "update itemcontent set content=? where ritemId=? and rid=?";
			while (iter.hasNext()) {
				psmt = null;
				RecordItem item = iter.next();
				String content = map.get(item);
				if ((item.getRitemId() == null || null == r.getRid())
						&& (null == content || "".equals(content))) {
					continue;
				}

				psmt = conn.prepareStatement(update);
				// update
				psmt = conn.prepareStatement(update);
				psmt.setString(1, content);
				psmt.setInt(2, item.getRitemId());
				psmt.setInt(3, r.getRid());
				int row = psmt.executeUpdate();
				psmt = null;
				if (row == 0) {
					psmt = conn.prepareStatement(insert);
					psmt = conn.prepareStatement(insert);
					psmt.setInt(1, r.getRid());
					psmt.setInt(2, item.getRitemId());
					psmt.setString(3, content);
					psmt.executeUpdate();
				}
			}
			//
			if (null != weather) {
			
				String sql = "update Records set amWeatherDesc=?,pmWeatherDesc=?"
						+ ",hCentigrade=?,lCentigrade=? where rid=?";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, weather.getAmWeatherDesc());
				psmt.setString(2, weather.getPmWeatherDesc());
				psmt.setString(3, weather.gethCentigrade()+"");
				psmt.setString(4, weather.getlCentigrade()+"");
				psmt.setInt(5, r.getRid());
				psmt.executeUpdate();
			}
			conn.commit();
			return true;
		} catch (Exception e) {
		
			if (null != sp) {
				try {
					conn.rollback(sp);
				} catch (SQLException e1) {

				}
			}
			return false;
		} finally {
			super.close(psmt);
		}
	}

	@Override
	public boolean delete(Serializable oid) {
		String sql = "delete from Records where rid = ?";
		if (super.delete(sql, oid)) {
			return true;
		}
		Records r = get(oid);
		if (null == r) {
			return true;
		}
		PreparedStatement psmt = null;
		Connection conn = null;
		Savepoint sp = null;
		try {
			psmt = super.getStatement("");
			conn = psmt.getConnection();
			conn.setAutoCommit(false);
			sp = conn.setSavepoint();

			Set<RecordConstructionGroup> cgs = r.getConstructionGroup();
			// 是否级联RecordConstructionGroup,删除级联
			if (cgs.size() > 0) {
				String sql1 = "delete from RecordConstructionGroup where rcgid=?";
				for (RecordConstructionGroup cg : cgs) {
					psmt = null;
					psmt = conn.prepareStatement(sql1);
					psmt.setInt(1, cg.getRcgid());
					psmt.executeUpdate();
				}
			}
			// 是否级级联ItemContent，删除级联
			List<Integer> items = new ArrayList<Integer>();
			String sqlitem = "select ic.icid from ItemContent ic inner join RecordItem ri on ic.ritemId = ri.ritemId "
					+ "where rid = ?";
			psmt = conn.prepareStatement(sqlitem);
			psmt.setInt(1, (Integer) oid);
			ResultSet rs = psmt.executeQuery();
			while (rs.next()) {
				items.add(rs.getInt(1));
			}
			if (items.size() > 0) {
				String sql2 = "delete from ItemContent where icid=?";
				for (Integer item : items) {
					psmt = null;
					psmt = conn.prepareStatement(sql2);
					psmt.setInt(1, item);
					psmt.executeUpdate();
				}
			}

			// 删除日志

			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, (Integer) oid);
			psmt.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			if (null != sp) {
				try {
					conn.rollback(sp);
				} catch (SQLException e1) {

				}
			}
			return false;
		} finally {
			super.close(psmt);
		}
	}

	@Override
	public Map<String, Integer> getRecordTree(Serializable projectId) {

		return getRecord(projectId, null, null);
	}

	public Map<String, Integer> getRecordTree(Integer ritemId, String content,
			Date maxDate, Date minDate) {
		String sql = "select r.logDate,r.rid from itemcontent ic "
				+ "inner join records r on r.rid = ic.rid "
				+ " where  ic.ritemId=? ";
		if (null != maxDate && null != minDate) {
			sql += "and r.logDate between ? and ?";
		}
		sql += " and ic.content like ? order by r.logDate";
		Map<String, Integer> map = null;
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, ritemId);
			if (null != maxDate && null != minDate) {
				psmt.setDate(2, minDate);
				psmt.setDate(3, maxDate);
				psmt.setString(4, "%" + content + "%");
			} else {
				psmt.setString(2, "%" + content + "%");
			}

			ResultSet rs = psmt.executeQuery();
			map = new TreeMap<String, Integer>();
			while (rs.next()) {
				map.put(rs.getDate(1).toString(), rs.getInt(2));
			}
		} catch (Exception e) {

		}

		return map;
	}

	@Override
	public Map<String, Integer> getRecordTree(Integer ritemId, String content) {
		return getRecordTree(ritemId, content, null, null);
	}

	@Override
	public Integer getItemContent(Serializable recordId, Serializable ritemId) {
		String sql = "select icid from ItemContent where rid=? and ritemId=?";
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) recordId);
			psmt.setInt(2, (Integer) ritemId);
			ResultSet rs = psmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

		} finally {
			super.close(psmt);
		}
		return null;
	}

	@Override
	public String getUriAddtional(Serializable recordsId) {
		String sql = "select uriAddtional from records where rid=?";
		PreparedStatement psmt = null;
		String files = "";
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) recordsId);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				files = rs.getString(1);
			}
		} catch (Exception e) {
			
		}
		
		return files;
	}
}
