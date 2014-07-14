package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IConstructionGroupDao;
import com.xiangfa.logssystem.dao.mysqlimpl.ConstructionGroupDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;

/**
 * 施工组增加，删除，更新
 */
public class ConstructionGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		IConstructionGroupDao cgdao = new ConstructionGroupDao();
		PrintWriter writer = resp.getWriter();
		try{
			Integer cgid = req.getParameter("cgid")==null?null:Integer.valueOf(req.getParameter("cgid").toString());
			if(cgid==null)
				return;
		
			ConstructionGroup cg = null;
				String cgname = req.getParameter("cgname")==null?"":req.getParameter("cgname").toString();
				if("".equals(cgname)){
					//删除
					if(cgdao.delete(cgid)){
						writer.write("true");
					}else{
						writer.write("false");
					}
				}else{
					Integer pid = req.getParameter("pid")==null?null:Integer.valueOf(req.getParameter("pid").toString());
					Integer totalWorker = req.getParameter("totalWorker")==null?null:Integer.valueOf(req.getParameter("totalWorker").toString());
					String bosshead = req.getParameter("bosshead")==null?null:req.getParameter("bosshead").toString();
					cg = new ConstructionGroup(pid,cgname,totalWorker,bosshead);
					if(cgid==-1){
						//新增
						if(cgdao.add(cg)){
							writer.write("true");
						}else{
							writer.write("false");
						}
					}else{
						//更新
						cg.setCgid(cgid);
						if(cgdao.update(cg)){
							writer.write("true");
						}else{
							writer.write("false");
						}
					}
				}
			}catch(Exception e){
				if (e.getMessage().contains("exists")) {
					writer.write("该班组名称已存在");
				} else {
					writer.write("false");
				}
			}
		writer.flush();
	}
}
