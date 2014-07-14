<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="js/index.js"></script>
<link href="../css/base.css" rel="stylesheet" type="text/css" />
<link href="css/daily.css" rel="stylesheet" type="text/css" />
<title>日志详细信息</title>
</head>
<body>
<div class="body_right">
    <div class="body_right_content">
        <form id="f_daily" name="f_daily" action="f.html" method="post">
            <div class="body_right_title">
            	<input type="hidden" id="rid" value="${requestScope.record.rid }" />
                <span class="f_span">当前日志时间为：</span><span id="recordDate">${requestScope.record.logDate }</span>
                <span class="title_w">天气：</span>
                <span>
                    上午
                    <input maxlength="10" style="width:64px;" type="text" id="morningWeather" class="txt" value="${requestScope.record.weather.amWeatherDesc }" />
                    <select style="width:80px;" class="st" id="st1" onchange="javascript:st_change(1);">
                        <option value="晴">晴</option>
                        <option value="阴" >阴</option>
                        <option value="多云">多云</option>
                        <option value="雨">雨</option>
                        <option value="小雨">小雨</option>
                        <option value="中雨">中雨</option>
                        <option value="大雨">大雨</option>
                        <option value="阵雨">阵雨</option>
                        <option value="暴雨">暴雨</option>
                        <option value="雨夹雪">雨夹雪</option>
                        <option value="雪">雪</option>
                        <option value="小雪">小雪</option>
                        <option value="中雪">中雪</option>
                        <option value="大雪">大雪</option>
                        <option value="沙尘暴">沙尘暴</option>
                        <option value="冰雹">冰雹</option>
                        <option value="雾">雾</option>
                        <option value="大雾">大雾</option>
                    </select>
                </span>
                <span class="afternoon">
                    下午
                    <input maxlength="10" style="width:64px;" type="text" id="afternoonWeather" class="txt" value="${requestScope.record.weather.pmWeatherDesc }" />
                    <select style="width:80px;" class="st" id="st2" onchange="javascript:st_change(2);">
                        <option value="晴">晴</option>
                        <option value="阴" >阴</option>
                        <option value="多云">多云</option>
                        <option value="雨">雨</option>
                        <option value="小雨">小雨</option>
                        <option value="中雨">中雨</option>
                        <option value="大雨">大雨</option>
                        <option value="阵雨">阵雨</option>
                        <option value="暴雨">暴雨</option>
                        <option value="雨夹雪">雨夹雪</option>
                        <option value="雪">雪</option>
                        <option value="小雪">小雪</option>
                        <option value="中雪">中雪</option>
                        <option value="大雪">大雪</option>
                        <option value="沙尘暴">沙尘暴</option>
                        <option value="冰雹">冰雹</option>
                        <option value="雾">雾</option>
                        <option value="大雾">大雾</option>
                    </select>
                </span>
                <span class="title_t">温度(℃):&nbsp;&nbsp;</span>
                <span>
                    最高&nbsp;&nbsp;<input maxlength="4" id="topTemper" style="width:40px;" onkeyup="javascript:getDouble(this);" onblur="javascript:getDouble(this);" value="${requestScope.record.weather.hCentigrade}" />
                </span>
                <span>
                    &nbsp;&nbsp;&nbsp;&nbsp;最低&nbsp;&nbsp;<input maxlength="4" id="lowTemper" style="width:40px;" onkeyup="javascript:getDouble(this);" onblur="javascript:getDouble(this);" type="text" name="txtTopTem" class="txtTem" value="${requestScope.record.weather.lCentigrade}" />
                </span>
            </div>
            
            <div class="right_main">
                <div id="config">
                    <div id="config_items">
                    	<c:forEach items="${requestScope.record.itemRecords }" var="item" varStatus="i">
                    		<c:if test="${i.index < 7 }">
                    			<div class="config_show" id="t${i.index + 1 }" onmousemove="javascript:tagMouseOver(this);" onmouseout="javascript:tagMouseOut(this);" onclick="javascript:configShow(this);"><a href="javascript:void(0);" title="${item.key.ritemName }">${f:substring(item.key.ritemName, 0 , 6) }</a></div>
                    		</c:if>
                    		<c:if test="${i.index >= 7 }">
                    			<div style="display:none;" onmousemove="javascript:tagMouseOver(this);" onmouseout="javascript:tagMouseOut(this);" class="config_show" id="t${i.index + 1 }" onclick="javascript:configShow(this);"><a href="javascript:void(0);" title="${item.key.ritemName }">${f:substring(item.key.ritemName, 0 , 6) }</a></div>
                    		</c:if>
                    	</c:forEach>
                    	<c:if test="${f:length(requestScope.record.itemRecords) > 6}">
                    		<div style="display:none;" onmousemove="javascript:tagMouseOver(this);" onmouseout="javascript:tagMouseOut(this);" class="config_show" id="t${f:length(requestScope.record.itemRecords) + 1}" onclick="javascript:configShow(this);"><a href="javascript:void(0);" title="附件">附件</a></div>
                    	</c:if>
                    	<c:if test="${f:length(requestScope.record.itemRecords) <= 6}">
                    		<div class="config_show" onmousemove="javascript:tagMouseOver(this);" onmouseout="javascript:tagMouseOut(this);" id="t${f:length(requestScope.record.itemRecords) + 1}" onclick="javascript:configShow(this);"><a href="javascript:void(0);" title="附件">附件</a></div>
                    	</c:if>
                    </div>
                    <div id="left_position_black" class="position" onclick="javascript:move_left();"></div>
                    <div id="right_position_black" class="position" onclick="javascript:move_right();"></div>
                    <script type="text/javascript">
                    	//tag
                        var all_items = $('config_items').childNodes;
                        var items = 0;
                        //第一个为显示状态的tag的编号
                        var show_index = 1;
                        for(var i = 0; i < all_items.length; i++){
                            if(all_items[i].nodeType == 1){
                                items++;
                            }
                        }
                        if(items > 7){
                            $('right_position_black').id = 'right_position_red';
                        }
                        $('t1').className = 'config_show2';
                    </script>
                    <div id="rItems">
	                    <c:forEach items="${requestScope.record.itemRecords }" var="item" varStatus="i">
	                    	<c:if test="${i.index == 0 }">
	                    		<div id="tag${i.index + 1 }" class="c_tb" style="display:block;">
			                        <textarea id="${item.key.ritemId }" onchange="javascript:ritemsSaving();" name="txttag${i.index + 1 }" class="txta">${item.value }</textarea>
			                    </div>
	                    	</c:if>
		                    <c:if test="${i.index != 0 }">
	                    		<div id="tag${i.index + 1 }" class="c_tb" style="display:none;">
			                        <textarea id="${item.key.ritemId }" onchange="javascript:ritemsSaving();" name="txttag${i.index + 1 }" class="txta">${item.value }</textarea>
			                    </div>
	                    	</c:if>
		                </c:forEach>
	                   		<div id="tag${f:length(requestScope.record.itemRecords) + 1}" class="c_tb" <c:if test="${requestScope.record.itemRecords != null && f:length(requestScope.record.itemRecords) > 0 }">style="display:none;"</c:if>>
		                        <div id="appendix" class="txta">
		                        	<div id="fileLeft">
		                        		<div>
	                        				<ul class="fileHead">
	                        					<li class="fileName">文件名</li>
	                        					<li class="fileOp">操作</li>
	                        				</ul>
	                        			</div>
	                        			<div id="fileContent" class="fileContent"></div>
		                        	</div>
		                        	<div id="fileRight" onclick="javascript:requestUpload();"></div>
		                        </div>
		                    </div>
	                </div>
                </div>
            </div>
            
            <div class="right_classes">
                <p>本日施工班组情况：<span id="add_class" onclick="javascript:addRecordClassesWindow();">添加班组情况</span></p>
                <table border="0" cellpadding="0" cellspacing="0">
                    <thead>
                        <tr>
                            <td width="0"></td>
                            <th width="72px">班组名称</th>
                            <th width="42px">总人数</th>
                            <th width="52px">参加人数</th>
                            <th width="42px">负责人</th>
                            <th width="182px">项目名称</th>
                            <th width="142px">施工部位</th>
                            <th width="72px">施工情况</th>
                            <th width="42px">操作</th>
                        </tr>
                    </thead>
                </table>
                <div id="classes">
                	<c:forEach items="${requestScope.record.constructionGroup }" var="grop" varStatus="i">
	                	<ul>
	                		<li class="classId"><input type="hidden" id="rcgid" name="classId" value="${grop.rcgid }" /></li>
							<li id="cgname" class="className">${grop.cgroup.cgname }</li>
							<li id="totalWorker" class="totalNum">${grop.cgroup.totalWorker }</li>
							<li id="arrivedNumber" class="arrivedNum">${grop.arrivedNumber }</li>
							<li id="bosshead" class="bosshead">${grop.cgroup.bosshead }</li>
							<li id="constructionProjectName" class="pname">${grop.constructionProjectName }</li>
							<li id="constructPart" class="cpart">${grop.constructPart }</li>
							<li id="constructStatus" class="status">${grop.constructStatus }</li>
							<li class="op"><a href="javascript:void(0);" onclick="javascript:delClassItem(this);">删除</a></li>
	                	</ul>
	                </c:forEach>
                 </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
updateDownLoadList();
</script>
</body>
</html>
