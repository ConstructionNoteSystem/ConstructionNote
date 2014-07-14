package com.xiangfa.logssystem.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiangfa.logssystem.util.FileNameSwitch;
import com.xiangfa.logssystem.util.RecordsAddtionalUtil;

/**
 * Servlet implementation class FileUpLoadServlet
 */
public class FileUpLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpLoadServlet() {
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
		
		response.setContentType("text/html;charset=utf-8"); 
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Integer pid = request.getParameter("pid")==null?null:Integer.valueOf(request.getParameter("pid"));
		String logDate = request.getParameter("logDate")==null?null:request.getParameter("logDate");
		
		if(null==pid||null==logDate){
			if(session.getAttribute("pid")==null||session.getAttribute("logDate")==null){
				out.print("<p>请确认上传附件的工程和日志是否存在!</p>");
				return;
			}
			logDate = (String) session.getAttribute("logDate");
			pid = (Integer) session.getAttribute("pid");
		}
		else{
			logDate = logDate.replaceAll("-", "");
			
			session.setAttribute("pid", pid);
			session.setAttribute("logDate", logDate);
			if("GET".equalsIgnoreCase(request.getMethod())){
				response.sendRedirect("fileupload.html");
				return;
			}
		}
		
		char[] hchl={13,10}; 
		String boundary=request.getContentType().substring(30); 
		
		String field_boundary="--"+boundary+new String(hchl); 
	
		String last_boundary="--"+boundary+"--"+new String(hchl); 
		
		String _msg="";
		String saveName = "";
		ServletInputStream getdata=request.getInputStream(); 

		ByteArrayOutputStream temp=new ByteArrayOutputStream(); 
		byte[] data_line=new byte[8192]; 
		int line_byte_count=0; 
		boolean found_boundary=false; 
		while((line_byte_count=getdata.readLine(data_line,0,data_line.length))!=-1){ 
		    if(!found_boundary){ 
		        line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		    } 
		    String temp_str=new String(data_line,0,line_byte_count); 
		    if(temp_str.indexOf("filename")!=-1){ 
		    	
		        if(temp_str.substring(temp_str.indexOf("filename=")+9,temp_str.lastIndexOf("\"")+1).length()>2){ 
		            
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            String file_name = temp_str.substring(temp_str.indexOf("filename=") + 10, temp_str.lastIndexOf("\""));
					
		            file_name = new String(file_name.getBytes(),"UTF-8");
					
		            String upfile_name;
					try {
						upfile_name = FileNameSwitch.fromClient(file_name, pid, logDate);
					} catch (Exception e1) {
						continue;
					}
		          
		            String  path = request.getServletContext().getRealPath("\\admin\\file\\");
		            FileOutputStream myfile=new FileOutputStream(path + "\\" +upfile_name,false); //文件存放目录
		            boolean test=true; 
		            while(test) { 
		                line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		                if(line_byte_count==-1){ 
		                    test=false; 
		                    break; 
		                } 
		                if(temp.size()==0){ 
		                    temp.write(data_line,0,line_byte_count); 
		                }else{ 
		                    if(new String(data_line,0,line_byte_count).equals(field_boundary) || new String(data_line,0,line_byte_count).equals(last_boundary)){ 
		                        myfile.write(temp.toByteArray(),0,temp.toByteArray().length-2); 
		                        temp.reset(); 
		                        myfile.flush();
		                        myfile.close(); 
		                        //out.println(file_name+"上传成功了 <br>");
								_msg=_msg+file_name+"上传成功<br>"; 
								saveName=saveName+upfile_name+":";
		                        test=false; 
		                        found_boundary=true; 
		                    }else{ 
		                        temp.writeTo(myfile); 
		                        temp.reset(); 
		                        temp.write(data_line,0,line_byte_count); 
		                    } 
		                } 
		             
		            } 
		            //更新数据库
		            RecordsAddtionalUtil rutil = new RecordsAddtionalUtil();
		            try {
		            	if(!"".equals(saveName)){
		            		rutil.upload(saveName, pid, logDate);
		            	}
		    		} catch (Exception e) {
		    			
		    		}finally{
		    	       
		    		}
		        }else{ 
		            //String field_name=temp_str.substring(temp_str.indexOf("name")+6,temp_str.lastIndexOf(";")-1); 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            found_boundary=true; 
		            //out.println(field_name+"没有选择上传文件！ <br>"); 
		            //_msg=_msg+field_name+"没有选择上传文件<br>";
		        } 
		    } else{ 
		        String field_name=temp_str.substring(temp_str.indexOf("name")+6,temp_str.lastIndexOf("\"")); 
		        line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		        temp.reset(); 
		        boolean test=true; 
		        while(test) { 
		            line_byte_count=getdata.readLine(data_line,0,data_line.length); 
		            if(line_byte_count==-1){ 
		                test=false; 
		                break; 
		            } 
		            if(new String(data_line,0,line_byte_count).equals(field_boundary) || new String(data_line,0,line_byte_count).equals(last_boundary)){ 
		                test=false; 
		                found_boundary=true; 
		                if(temp.size()>2){ 
		                   // out.println(field_name+":"+new String(temp.toByteArray())+" <br>"); 
						   _msg=_msg+field_name+":"+new String(temp.toByteArray())+"<br>";
		                }else{ 
		                   // out.println(field_name+"没有内容！ <br>"); 
						   _msg=_msg+field_name+"没有内容！<br>";
		                } 
		                temp.reset(); 
		            }else{ 
		                temp.write(data_line,0,line_byte_count); 
		            } 
		        } 
		    } 
		    
		} 
		getdata.close(); 
		session.removeAttribute("pid");
		session.removeAttribute("rid");
		out.println("<script>window.parent.Finish('"+_msg+"');window.parent.close();</script>");
	}

}
