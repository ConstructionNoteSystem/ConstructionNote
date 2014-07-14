package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IConstructionGroupDao;
import com.xiangfa.logssystem.dao.mysqlimpl.ConstructionGroupDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;
import com.xiangfa.logssystem.util.JSONUtil;

/**
 *获取班组信息
 */
public class GetConstructionGroupListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		Integer projectId = request.getParameter("pid")==null?null:Integer.valueOf(request.getParameter("pid").toString());
		IConstructionGroupDao cgdao = new ConstructionGroupDao();
		if(projectId==null){
			return;
		}
		response.setContentType("text/plain;charset=utf-8");
		List<ConstructionGroup> cgs = cgdao.listGroupByProject(projectId);
		StringBuilder sb = new StringBuilder();
		int index = 0;
		int len = cgs.size();
		for(ConstructionGroup c : cgs){
			index++;
			sb.append(JSONUtil.fromObject(c));
			if(index<len){
				sb.append(",");
			}
		}
		response.getWriter().write("[" + sb.toString() + "]");
		response.flushBuffer();
	}

	
}
