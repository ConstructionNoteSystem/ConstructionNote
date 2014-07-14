package com.xiangfa.logssystem.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class FileDownLoadFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		response.setCharacterEncoding("utf-8");
		String filename = request.getParameter("filename");
		String filepath = request.getServletContext().getRealPath("/") + "admin\\file\\"; 
		response.setContentType("APPLICATION/OCTET-STREAM"); 
		((HttpServletResponse)response).setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename,"utf-8")+"");
		FileInputStream fileInputStream = new FileInputStream(filepath + filename);
		OutputStream outputStream = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = fileInputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		fileInputStream.close();
		outputStream = null;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
