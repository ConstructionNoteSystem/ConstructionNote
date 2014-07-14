package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordsDao;
import com.xiangfa.logssystem.entity.Records;

public class GetRecordServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 249648982706043594L;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/palin;charset=utf-8");
		PrintWriter writer = null;
		try{
			writer = resp.getWriter();
			Integer rid = req.getParameter("rid")==null?null:Integer.valueOf(req.getParameter("rid"));
			if(rid==null){
				return;
			}
			Records item = null;
			IRecordsDao rdao = new RecordsDao();
			item = rdao.get(rid);
			req.setAttribute("record",item);
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("daily.jsp");
			requestDispatcher.forward(req, resp);
		}catch(Exception e){
			writer.write("");
		}
		resp.flushBuffer();
	}
}
