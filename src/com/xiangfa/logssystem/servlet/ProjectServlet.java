package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IProjectDao;
import com.xiangfa.logssystem.dao.mysqlimpl.ProjectDao;
import com.xiangfa.logssystem.entity.Project;
import com.xiangfa.logssystem.entity.Users;

/**
 * 工程 新增，删除和修改
 */
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjectServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		IProjectDao pdao = new ProjectDao();
		PrintWriter writer = resp.getWriter();
		try {
			Integer pid = req.getParameter("pid") == null ? null : Integer
					.valueOf(req.getParameter("pid").toString());
			if (pid == null)
				return;

			Project p = null;
			String projectName = req.getParameter("projectName") == null ? ""
					: req.getParameter("projectName").toString();
			if ("".equals(projectName)) {
				// 删除
				if (pdao.delete(pid)) {
					writer.write("true");
				} else {
					writer.write("false");
				}
			} else {
				String buildingUnits = req.getParameter("buildingUnits") == null ? null
						: req.getParameter("buildingUnits").toString();
				String constructionUnits = req
						.getParameter("constructionUnits") == null ? null : req
						.getParameter("constructionUnits").toString();
				java.sql.Date createDate = req.getParameter("createDate") == null ? null
						: java.sql.Date.valueOf(req.getParameter("createDate")
								.toString());
				String designUnits = req.getParameter("designUnits") == null ? null
						: req.getParameter("designUnits").toString();
				String supervisingUnits = req.getParameter("supervisingUnits") == null ? null
						: req.getParameter("supervisingUnits").toString();
				String superviserName = req.getParameter("superviserName") == null ? null
						: req.getParameter("superviserName").toString();
				String workingSpace = req.getParameter("workingSpace") == null ? null
						: req.getParameter("workingSpace").toString();

				p = new Project(URLDecoder.decode(projectName,"UTF-8"), URLDecoder.decode(buildingUnits,"UTF-8"), URLDecoder.decode(workingSpace,"UTF-8"),
						URLDecoder.decode(designUnits,"UTF-8"), URLDecoder.decode(constructionUnits,"UTF-8"), URLDecoder.decode(supervisingUnits,"UTF-8"),
						URLDecoder.decode(superviserName,"UTF-8"), createDate);

				if (pid == -1) {
					// 新增
					Users u = (Users) req.getSession().getAttribute("user");
					p.setUserId(u.getUid());
					Serializable id = pdao.add((Serializable) p);
					writer.write(id.toString());
				} else {
					// 更新
					p.setPid(pid);
					if (pdao.update(p)) {
						writer.write("true");
					} else {
						writer.write("false");
					}
				}

			}
		} catch (Exception e) {
			if (e.getMessage().contains("exists")) {
				writer.write("该工程名已存在");
			} else {
				writer.write(-1);
			}
		}
		writer.flush();
	}

}
