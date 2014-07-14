package com.xiangfa.logssystem.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class CheckUser implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		response.setContentType("text/html;charset=utf-8");
		if(null != req.getSession().getAttribute("user")){
			chain.doFilter(request, response);
		} else {
			response.getWriter().print("<script type=\"text/javascript\">alert('����δ��½��\\n���ȵ�½����ʹ�ñ�ϵͳ��');window.location='" + req.getContextPath() + "/login.html';</script>");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
