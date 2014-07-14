package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordItemDao;
import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordItemDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordsDao;
import com.xiangfa.logssystem.entity.RecordItem;

public class GetDateTreeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int proId = -1;
		if(null != request.getParameter("projectId") && !"".equals(request.getParameter("projectId").trim())){
			proId = Integer.parseInt(request.getParameter("projectId"));
		}
		IRecordItemDao ritem = new RecordItemDao();
		List<RecordItem> items = ritem.listItem(proId);
		request.setAttribute("items", items);
		
		IRecordsDao rdao = new RecordsDao();
		Map<String, Integer> recordTree = rdao.getRecordTree(proId);
		Iterator<String> iter = recordTree.keySet().iterator();
		Map<String, Integer> mapDate = new TreeMap<String, Integer>();
		Map<String, Map<String, Integer>> mapMonth = new TreeMap<String, Map<String, Integer>>();
		Map<String, Map<String, Map<String, Integer>>> mapYear = new TreeMap<String, Map<String, Map<String, Integer>>>();
		String y = null;
		String m = null;
		int index = 0;
		while(iter.hasNext()){
			index++;
			String id = iter.next();
			String year = id.substring(0,4);
			String month = id.substring(5,7);
			String date = id.substring(8,10);
			int cid = recordTree.get(id);
			if(m != null){
				if(!month.equals(m) || !year.equals(y)){
					mapMonth.put(m, mapDate);
					mapDate = new TreeMap<String, Integer>();
				}
				if(!year.equals(y)){
					mapYear.put(y, mapMonth);
					mapMonth = new TreeMap<String, Map<String, Integer>>();
				}
			}
			mapDate.put(date, cid);
			m = month;
			y = year;
			if(index == recordTree.size()){
				mapMonth.put(month, mapDate);
				mapYear.put(year, mapMonth);
			}
		}
		request.setAttribute("mapYear", mapYear);
		request.getRequestDispatcher("date.jsp").forward(request, response);
		
	}
}
