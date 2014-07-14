package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordsDao;
import com.xiangfa.logssystem.entity.RecordItem;
import com.xiangfa.logssystem.entity.Records;
import com.xiangfa.logssystem.entity.Weather;
import com.xiangfa.logssystem.util.EscapeHTMLElement;

/**
 * 添加或更新一条日志记录
 */
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}


	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		resp.setContentType("text/plain;charset=utf-8");
		PrintWriter writer = resp.getWriter();
		try {	
			IRecordsDao rdao = new RecordsDao();
			Records c = new Records();
		
			Integer rid = req.getParameter("rid") == null ? null : Integer
					.valueOf(req.getParameter("rid").toString());
			if (null == rid)
				return;
			if (rid != -1) {

				String amWD = (String) (req.getParameter("amWeatherDesc") == null ? null
						: req.getParameter("amWeatherDesc"));
				if (null==(amWD)) {
					if (rdao.delete(rid)) {
						writer.write("true");
					} else {
						writer.write("false");
					}
					return;
				}
				String pmWD = req.getParameter("pmWeatherDesc") == null ? null
						: req.getParameter("pmWeatherDesc");
				Double hc = req.getParameter("hCentigrade") == null ? null
						: Double.valueOf(req.getParameter("hCentigrade")
								);
				Double lc = req.getParameter("lCentigrade") == null ? null
						: Double.valueOf(req.getParameter("lCentigrade")
								);
				if(!("".equals(amWD)&&"".equals(pmWD)&&0==hc&&0==lc)){
					Weather w = new Weather(URLDecoder.decode(amWD,"UTF-8"), URLDecoder.decode(pmWD,"UTF-8"), hc, lc);
					c.setWeather(w);
					
				}
				String items = req.getParameter("items")==null?null:req.getParameter("items");
				items = URLDecoder.decode(items,"UTF-8");
				
				Map<RecordItem,String> map = null;
				RecordItem record = null;
				if(items!=null){
					map = new HashMap<RecordItem,String>();
					StringTokenizer st = new StringTokenizer(items, "@$^");
					while(st.hasMoreElements()){
						String item = st.nextElement().toString();
						int split = item.indexOf(",");
						Integer id = Integer.valueOf(item.substring(0,split));
						if(item.length()==split+1&&rdao.getItemContent(rid, id)==null){
							continue;
						}
						String content = item.substring(split+1);
					
						record = new RecordItem();
						record.setRitemId(id);
						map.put(record, EscapeHTMLElement.escape(content));
					}
					c.setItemRecords(map);
				}
				c.setRid(rid);
				if (rdao.update(c)) {
					writer.write("true");
				} else {
					writer.write("false");
				}
				return;
			}
			//
			java.sql.Date date = req.getParameter("date") == null ? null
					: java.sql.Date
							.valueOf(req.getParameter("date"));
			Integer pid = req.getParameter("projectId") == null ? null
					: Integer
							.parseInt(req.getParameter("projectId"));
			c.setPid(pid);
			c.setLogDate(date);
			Map<String, Integer> dateMap = rdao.getRecordTree(pid);
			Iterator<String> iter = dateMap.keySet().iterator();
			while (iter.hasNext()) {
				if (date.equals(iter.next())) {
					writer.write("false");
					return;
				}
			}
			Integer id = rdao.add(pid, date);
			writer.write(id.toString());

		} catch (Exception e) {
		
			if (e.getMessage().contains("exists")) {
				writer.write("该日期的日志已存在");
			} else {
				writer.write("false");
			}
		}
		writer.flush();
	}
}
