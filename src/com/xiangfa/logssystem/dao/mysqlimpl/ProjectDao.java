package com.xiangfa.logssystem.dao.mysqlimpl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import com.xiangfa.logssystem.dao.IProjectDao;
import com.xiangfa.logssystem.entity.Project;
import com.xiangfa.logssystem.entity.RecordItem;

public class ProjectDao extends BaseDao<Project> implements IProjectDao {


	/**
	 * 查询工程信息
	 * @param 所属用户编号
	 * @param 分页信息
	 * @return 工程列表
	 */
	private List<Project> listProjects(Serializable uid,Date maxDate,
			Date minDate) {
		String sql = "select p.pid,p.projectName from project p " +
				" where userId=? " ;
		
		sql+=" order by createDate desc ";
		PreparedStatement psmt = null;
		List<Project> projects = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) uid);
			
			ResultSet rs = psmt.executeQuery();
			projects = new ArrayList<Project>();
			Project p = null;	
			while(rs.next()){
				p = new Project();
				p.setPid(rs.getInt(1));
				p.setProjectName(rs.getString(2));
				projects.add(p);
			}
			for(Project ps : projects){
				Connection conn = psmt.getConnection();
				psmt = null;
				rs = null;
				psmt = conn.prepareStatement("select ritemId,ritemName from RecordItem where pid=?");
				psmt.setInt(1, ps.getPid());
				rs = psmt.executeQuery();
				RecordItem item = null;
				while(rs.next()){
					item =  new RecordItem();
					item.setRitemId(rs.getInt(1));
					item.setRitemName(rs.getString(2));
					ps.getRecordItems().add(item);
				}
			}
			//关闭ResultSet
			rs.close();
		} catch (Exception e) {
			
		}finally{
			super.close(psmt);
		}
		return projects;
	}


	@Override
	public List<Project> listProjects(Serializable uid) {
		
		return listProjects(uid,null,null);
	}

	@Override
	public boolean add(Project c) throws Exception {
		String sql = "insert into Project (userId,projectName,buildingUnits,workingSpace,designUnits,constructionUnits" +
				",supervisingUnits,superviserName,createDate) values" +
				"(?,?,?,?,?,?,?,?,?)";
		return super.add(sql, c);
	}

	@Override
	public boolean delete(Serializable oid) {
		String sql = "delete from Project where pid=?";
		//如果工程有分类，但没有日志，直接删除
		if(!super.delete(sql, oid)){
			return deleteCascadeRecordItem(oid);
		}
		return true;
	}
	
	/**
	 * 
	 * @param oid
	 * @param cascadeRecordItem
	 * @return
	 */
	private boolean deleteCascadeRecordItem(Serializable oid){
		PreparedStatement psmt = null;
		Savepoint sp = null;
		Connection conn = null;
		try {
			psmt = super.getStatement("");
			conn = psmt.getConnection();
			conn.setAutoCommit(false);
			psmt = null;
			sp = conn.setSavepoint();
			String sql = "delete from RecordItem where pid=?";
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, (Integer) oid);
			if(psmt.executeUpdate()==0){
				conn.rollback(sp);
				return false;
			}
			psmt = null;
			String sql2 = "delete from Project where pid=?";
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, (Integer) oid);
			if(psmt.executeUpdate()==0){
				conn.rollback(sp);
				return false;
			}
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
	public boolean update(Project c) throws Exception {
		String sql = "update Project set projectName=?,buildingUnits=?,constructionUnits=?" +
				",workingSpace=?,designUnits=?,supervisingUnits=?,superviserName=?" +
				",createDate=? where pid=?";
		return super.update(sql, c);
	}

	@Override
	public Project get(Serializable pid) {
		String sql = "select pid,projectName,buildingUnits,constructionUnits,createDate,designUnits" +
				",superviserName,supervisingUnits,workingSpace from Project where pid=?";
		PreparedStatement psmt = null;
		try {
			psmt = super.getStatement(sql);
			psmt.setInt(1, (Integer) pid);
			ResultSet rs = psmt.executeQuery();
			if(rs.next()){
				Project p = new Project();
				p.setPid(rs.getInt(1));
				p.setProjectName(rs.getString(2));
				p.setBuildingUnits(rs.getString(3));
				p.setConstructionUnits(rs.getString(4));
				p.setCreateDate(rs.getDate(5));
				p.setDesignUnits(rs.getString(6));
				p.setSuperviserName(rs.getString(7));
				p.setSupervisingUnits(rs.getString(8));
				p.setWorkingSpace(rs.getString(9));
				return p;
			}
			rs.close();
		} catch (Exception e) {
			
		}	
		return null;
	}

	@Override
	public Serializable add(Serializable p) throws Exception {
		if(add((Project)p))
			return super.getGenerateKey();
		return -1;
	}


	@Override
	public List<Project> listProjectsByDateScope(Serializable uid, Date maxDate,
			Date minDate) {
		return listProjects(uid,maxDate,minDate);
	}

}
