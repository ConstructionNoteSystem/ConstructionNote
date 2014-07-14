package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordsDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordsDao;
import com.xiangfa.logssystem.util.FileNameSwitch;

/**
 * ÏÂÔØÁÐ±í
 */
public class FileDownLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownLoadServlet() {
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
		response.setContentType("text/pain;charset=utf-8");
		Integer rid = request.getParameter("rid")==null?null:Integer.valueOf(request.getParameter("rid"));
		if(null==rid){
			return;
		}
		String serverPath = request.getRequestURL().toString();
		String context = request.getContextPath();
		int pos = serverPath.indexOf(context);
		String path = serverPath.substring(0,pos+context.length());
	
		IRecordsDao rdao = new RecordsDao();
		String names = rdao.getUriAddtional(rid);
		StringBuilder sb  = new StringBuilder();
		if(!("".equals(names)||null==names)){
			String[] files = FileNameSwitch.toClient(names);
				int index = 0;
				for(String s : files){
					if(index>0)
						sb.append(",");
					sb.append("{'name':'").append(s).append("','uri':'").append(URLEncoder.encode(path+"\\admin\\"+"file"+"\\"+s,"UTF-8")).append("'}");
					index++;
				
				}
		}
		response.getWriter().write("["+sb.toString()+"]");
	}

}
