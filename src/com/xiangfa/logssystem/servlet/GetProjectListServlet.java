package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IProjectDao;
import com.xiangfa.logssystem.dao.mysqlimpl.ProjectDao;
import com.xiangfa.logssystem.entity.Project;
import com.xiangfa.logssystem.entity.Users;
import com.xiangfa.logssystem.util.JSONUtil;

public class GetProjectListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		Users user = (Users) request.getSession().getAttribute("user");
		Integer uid = user.getUid();
		IProjectDao pDao = new ProjectDao();
		List<Project> projects = pDao.listProjects(uid);

		StringBuilder sb = new StringBuilder(100);
		int index = 0;
		for (Project p : projects) {
			if (index > 0) {
				sb.append(",");
			}
			sb.append(JSONUtil.fromObject(p));
			index++;
		}
		response.getWriter().write("[" + sb.toString() + "]");
		response.flushBuffer();

	}
}
