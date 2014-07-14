package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordsDao;

/**
 * 查询日志
 */
public class GetDateTreeByDateScopeOrContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDateTreeByDateScopeOrContentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		StringBuilder sb = new StringBuilder(100);
		IRecordsDao rdao = new RecordsDao();
		PrintWriter writer = response.getWriter();
		try{
			Date maxDate = request.getParameter("maxDate")==null?null:java.sql.Date.valueOf(request.getParameter("maxDate"));
			Date minDate = request.getParameter("minDate")==null?null:java.sql.Date.valueOf(request.getParameter("minDate"));
			
			Integer ritemId = request.getParameter("ritemId")==null?null:Integer.valueOf(request.getParameter("ritemId"));
			String content = request.getParameter("content")==null?null:request.getParameter("content");
			Map<String,Integer> result = null;
			if(null!=ritemId&&null!=content&&null==maxDate){
				result = rdao.getRecordTree(ritemId, content);
			}else if(null!=ritemId&&null!=maxDate&&null!=minDate&&null!=content){
				result = rdao.getRecordTree(ritemId, content, maxDate, minDate);
			}else {
				throw new Exception("输入内容不完整");
			}
			if(null==result){
				writer.write("");
				return;
			}
				Iterator<String> iter = result.keySet().iterator();
		
			String regex = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";
			Pattern p = Pattern.compile(regex);
			
			int index = 1;
			while(iter.hasNext()){
				if(index>1){
					sb.append(",");
				}
				String date = (String) iter.next();
				Matcher m = p.matcher(date);
				if(m.find()){
					sb.append("{'date':'"+m.group("year")+"年"+m.group("month")+"月"+m.group("day")+"日"+"','rid':'"+result.get(date).toString()+"'}");
				}
				index++;
			}
		}catch(Exception e){
			
			writer.write("");
		}
	
		writer.write("["+sb.toString()+"]");
		writer.flush();
	}

}
