package com.xiangfa.logssystem.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiangfa.logssystem.dao.IUserDao;
import com.xiangfa.logssystem.dao.mysqlimpl.UserDao;
import com.xiangfa.logssystem.entity.Users;

public class LoginServlet extends HttpServlet {

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
	
		String name = request.getParameter("txtName");
		String password = request.getParameter("txtPassword");
		IUserDao uDao = new UserDao();
		
		response.setContentType("text/html;charset=utf-8");
		Users user = uDao.login(name, password);
		if(null == user){
			PrintWriter out = response.getWriter();
			out.print("<script type=\"text/javascript\">alert('用户名或密码不正确！');history.go(-1);</script>");
			return;
		}
		request.getSession().setAttribute("user", user);
		response.sendRedirect("admin/index.jsp");
	}
}
