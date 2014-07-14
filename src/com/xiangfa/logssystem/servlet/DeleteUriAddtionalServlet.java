package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.util.RecordsAddtionalUtil;

/**
 * Servlet implementation class DeleteUriAddtionalServlet
 */
public class DeleteUriAddtionalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUriAddtionalServlet() {
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

		request.setCharacterEncoding("UTF-8");
		Integer pid = request.getParameter("pid")==null?null:Integer.valueOf(request.getParameter("pid"));
		String logDate = request.getParameter("logDate")==null?null:request.getParameter("logDate");
		if(null==pid||null==logDate||"".equals(logDate)){
			return;
		}
		String out = "true";
		String delete = request.getParameter("delete")==null?null:request.getParameter("delete");
		if(null!=delete&&!"".equals(delete)){
			RecordsAddtionalUtil rutil = new RecordsAddtionalUtil();
			Map<String,String> map = new HashMap<String,String>();
			map.put("pid", pid+"");
			map.put("logDate", logDate);
			map.put("delete", delete);
			try {
				rutil.delete(map);
			} catch (Exception e) {
				e.printStackTrace();
				out = "false";
			}
		}
		response.getWriter().write(out);
	}

}
