package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IProjectDao;
import com.xiangfa.logssystem.dao.mysqlimpl.ProjectDao;
import com.xiangfa.logssystem.entity.Project;
import com.xiangfa.logssystem.util.JSONUtil;

public class GetProjectServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1878465314622392666L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plaim;charset=utf-8");
		Integer pid = req.getParameter("pid")==null?null:Integer.valueOf(req.getParameter("pid").toString());
		
		if(null==pid)
			return;
		IProjectDao pdao = new ProjectDao();
		Project p = pdao.get(pid);
		
		PrintWriter writer = resp.getWriter();
		
		writer.write("["+JSONUtil.fromObject(p)+"]");
	
		writer.flush();
	}

}
