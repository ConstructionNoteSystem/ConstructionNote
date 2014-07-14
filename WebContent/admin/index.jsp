<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>祥发工程施工日志管理软件</title>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/WdatePicker.js"></script>
<style type="text/css">
body{
    behavior:url(css/iehoverfix.htc);  
}
</style>
</head>
<body>
	<div id="index">
    	<div class="top">
        	<img class="logo" src="../img/logo.gif" alt="苏州祥发工程logo" />
            <span class="manage" onclick="javascript:projectManage();">工程项目管理</span>
            <span class="manage" onclick="javascript:classesManage();">班组管理</span>
            <span class="welcome">欢迎您：</span>
            <span class="managerName">${sessionScope.user.username }</span>
            <span class="getOut" onclick="javascript:getout();">退出系统</span>
        </div>
        <div class="head">
        	<div class="head_left">
            	<p>工程项目列表:</p>
            	<p>
                	<select id="pro" style="width:200px; overflow:hidden;" onchange="javascript:changeProject(this);">
                    	<option title="请选择" value="0">------------------请选择------------------</option>
                    	<c:forEach items="${requestScope.projects}" var="p">
                    		<option title="${p.projectName}" value="${p.pid}">${p.projectName}</option>
                    	</c:forEach>
                   	</select>
                </p>
            </div>
            <div class="head_right">
            	<input type="hidden" id="indexPid" value="" />
            	<input type="hidden" id="trid" value="" />
            	<input type="hidden" id="ritemsState" value="0" />
            	<span class="head_btn" onclick="javascript:newDaily();">新建日志</span>
            	<span class="head_btn" onclick="javascript:saveDaily();">保存日志</span>
            	<span class="head_btn" onclick="javascript:delDaily();">删除日志</span>
            	<span class="head_btn" onclick="javascript:printDaily();">打印日志</span>
                
            </div>
        </div>
        
        <div class="body">
        	<div class="body_left">
            	<iframe id="date_result" frameborder="0" width="180" height="480" scrolling="no" name="date_result" src="date.jsp"></iframe>
            </div>
            <div class="body_right">
        		<iframe id="daily" frameborder="0" width="780" height="480" scrolling="no" name="daily" src="daily.jsp"></iframe>
            </div>
        </div>
        
        <div class="foot">
        	<p>版权所有: © 祥发智能，苏州祥发智能工程有限公司</p>
			<p>电话: 0512-63093966 邮件:szxfzn@163.com</p>
        </div>
        
    </div>
    <script type="text/javascript">
	    var pid = $('pro').value;
	    loadProjects();
    </script>
</body>
</html>