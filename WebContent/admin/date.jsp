<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/WdatePicker.js"></script>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="css/date.css" rel="stylesheet" type="text/css" />
<title>日期列表</title>
</head>
<body>
<div class="body_left">
    <div class="left_show" id="div1" onclick="javascript:leftShow(this);">施工日期</div>
    <div class="left_show" id="div2" onclick="javascript:leftShow(this);">日记查询</div>
    <div id="c1" class="c_div">
        <ul id="menu_zzjs_net">
        	<c:forEach items="${requestScope.mapYear }" var="y">
        		<li>
        			<label><a name="tree_year" href="javascript:void(0);">${y.key}年</a></label>
	                <ul class="two">
        				<c:forEach items="${y.value }" var="m">
	                    <li>
	                        <label><a name="tree_month" href="javascript:void(0);">${m.key}月</a></label>
	                        <ul class="two">
	                        	<c:forEach items="${m.value }" var="d">
	                            <li><label><a name="tree_date" title="${d.value }" onclick="javascript:tree_select(this);" href="javascript:void(0);">${d.key}日</a></label></li>
	                            </c:forEach>
	                        </ul>
	                    </li>
	                	</c:forEach>
	                </ul>
	            </li>
	        </c:forEach>
	    </ul>
    </div>
    <div id="c2" class="c_div">
        <p class="result_title">查询结果：</p>
        <div id="search_result">
            <p class="simpleP">暂无查询结果显示</p>
        </div>
        <div id="search">
            <form id="searchForm" name="searchForm">
                <p>查询类别:</p>
                <select id="searchItemsId" class="txtSearch">
                	<c:forEach var="item" items="${requestScope.items }">
                		<option title="${item.ritemName }" value="${item.ritemId }">${item.ritemName }</option>
                	</c:forEach>
                </select><span class="red">*</span>
                <p>查询内容:</p>
                <input type="text" id="txtKeyWord" name="txtKeyWord" class="txtSearch" /><span class="red">*</span>
                <p><input type="checkbox" id="ckDate" name="ckDate" onclick="javascript:checkTimeInput(this);" />指定查询时间范围</p>
                <div>
                    <p>开始日期：</p>
                    <input class="Wdate" disabled="disabled" id="bDate" name="beginDate" type="text" onclick="WdatePicker()" />
                    <p>结束日期：</p>
                    <input class="Wdate" disabled="disabled" id="eDate" name="endDate" type="text" onclick="WdatePicker()" />
                </div>
                
                <!--查询日期默认为当天-->
                <script type="text/javascript">
                    $('bDate').value = year + "-" + month + "-" + date;
                    $('eDate').value = year + "-" + month + "-" + date;
                 </script>
                 
                <p><input type="button" onclick="javascript:searchDate();" id="btnSear" name="btnSearch" value="查询日志" /></p>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
	addEvent(document.getElementById('menu_zzjs_net'),'click',function(e){//绑定点击事件，使用menu_zzjs_net根元素代理
		e = e||window.event;
		var target = e.target||e.srcElement;
		var tp = nextnode(target.parentNode.nextSibling);
		switch(target.nodeName){
		case 'A'://点击A标签展开和收缩树形目录，并改变其样式会选中checkbox
			if(tp&&tp.nodeName == 'UL'){
				if(tp.style.display != 'block' ){
					tp.style.display = 'block';
					prevnode(target.parentNode.previousSibling).className = 'ren';
				}else{
					tp.style.display = 'none';
					prevnode(target.parentNode.previousSibling).className = 'add';
				}
			}
			break;
		case 'SPAN'://点击图标只展开或者收缩
			var ap = nextnode(nextnode(target.nextSibling).nextSibling);
			if(ap.style.display != 'block' ){
				ap.style.display = 'block';
				target.className = 'ren';
			}else{
				ap.style.display = 'none';
				target.className = 'add';
			}
			break;
			}
		});
		window.onload = function(){//页面加载时给有孩子结点的元素动态添加图标
			var labels = document.getElementById('menu_zzjs_net').getElementsByTagName('label');
			for(var i=0;i<labels.length;i++){
				var span = document.createElement('span');
				span.style.cssText ='display:inline-block;height:18px;vertical-align:middle;width:16px;cursor:pointer;';
				span.innerHTML = ' ';
				span.className = 'add';
				if(nextnode(labels[i].nextSibling)&&nextnode(labels[i].nextSibling).nodeName == 'UL')
				labels[i].parentNode.insertBefore(span,labels[i]);
				else
				labels[i].className = 'rem';
			}
		};
	
</script>
</body>
</html>
