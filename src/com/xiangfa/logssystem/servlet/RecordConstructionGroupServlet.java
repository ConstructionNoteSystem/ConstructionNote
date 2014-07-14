package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IRecordConstructionGroupDao;
import com.xiangfa.logssystem.dao.mysqlimpl.RecordConstructionGroupDao;
import com.xiangfa.logssystem.entity.ConstructionGroup;
import com.xiangfa.logssystem.entity.RecordConstructionGroup;

/**
 * Servlet implementation class RecordConstructionGroupServlet
 */
public class RecordConstructionGroupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordConstructionGroupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public  void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plaim;charset=utf-8");
		PrintWriter writer = response.getWriter();
		try{
			Integer rcgid = request.getParameter("rcgid")==null?null:Integer.valueOf(request.getParameter("rcgid"));
			IRecordConstructionGroupDao rcdao = new RecordConstructionGroupDao();
			if(rcgid==null){
				Integer rid = request.getParameter("rid")==null?null:Integer.valueOf(request.getParameter("rid"));
				Integer arrivedNumber = request.getParameter("arrivedNumber")==null?null:Integer.valueOf(request.getParameter("arrivedNumber"));
				Integer cgid = request.getParameter("cgid")==null?null:Integer.valueOf(request.getParameter("cgid"));
				String constructionProjectName = request.getParameter("constructionProjectName")==null?null:request.getParameter("constructionProjectName");
				String constructPart  = request.getParameter("constructPart")==null?null:request.getParameter("constructPart");
				String constructStatus = request.getParameter("constructStatus")==null?null:request.getParameter("constructStatus");
				ConstructionGroup cg = new ConstructionGroup();
				cg.setCgid(cgid);
				RecordConstructionGroup rcg = new RecordConstructionGroup(cg, rid,URLDecoder.decode(constructionProjectName,"UTF-8"),arrivedNumber,
						URLDecoder.decode(constructPart,"UTF-8"),URLDecoder.decode(constructStatus,"UTF-8"));
				writer.write(rcdao.add((Serializable)rcg).toString());
				return;
			}
			if(rcdao.delete(rcgid));
			{
				writer.write("true");
				return;
			}	
		}catch(Exception e){
		
		}
		writer.write("false");
		writer.flush();
	}

}
