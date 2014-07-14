// JavaScript Document

function $(id){
	return document.getElementById(id);
}

//uri编码
function $c(values){
	return encodeURIComponent(values);
}

var checkvalue = /^([\u4e00-\u9fa5]|[A-Za-z0-9]){0,}$/;
var xmlhttp;
var xmlhttpResult;
//编辑类型
var editType = '';
var editProjectId = 0;
//选中的tag的id
var currentTagId = 't1';

function getDouble(ob){
	ob.value=ob.value.replace(/[^\.\d]/g,'');
	if(ob.value.split('.').length>2){
		ob.value=ob.value.split('.')[0]+'.'+ob.value.split('.')[1];
	}
	ritemsSaving();
}

function getInt(ob){
	ob.value=ob.value.replace(/\D/g,'');
}

function getEnableValue(ob){
	ob.value=ob.value.replace(/([^\u4e00-\u9fa5A-Za-z0-9])/g,'');
}

function getEnableString(ob){
	ob.value=ob.value.replace(/(=>|!|\?|>|<){1}/g,'');
}
//系统退出
function getout(){
	if(window.confirm("你确定退出么？")){
		window.location = 'loginOut';
	}
}

var today = new Date(); 
var year = today.getFullYear();
var month = today.getMonth() + 1;
var date = today.getDate();
if (month < 10){
	month = '0' + month;
}
if(date < 10){
	date = '0' + date;
}

//css:hover
var csshoverReg = /(^|\s)(([^a]([^ ]+)?)|(a([^#.][^ ]+)+)):(hover|active)/i,
currentSheet, doc = window.document, hoverEvents = [], activators = {
    onhover:{on:'onmouseover', off:'onmouseout'},
    onactive:{on:'onmousedown', off:'onmouseup'}
};

function parseStylesheets() {
    if(!/MSIE (5|6)/.test(navigator.userAgent)) return;
    window.attachEvent('onunload', unhookHoverEvents);
    var sheets = doc.styleSheets, l = sheets.length;
    for(var i=0; i<l; i++)
        parseStylesheet(sheets[i]);
}
function parseStylesheet(sheet) {
    if(sheet.imports) {
        try {
            var imports = sheet.imports, l = imports.length;
            for(var i=0; i<l; i++) parseStylesheet(sheet.imports[i]);
        } catch(securityException){}
    }

    try {
        var rules = (currentSheet = sheet).rules, l = rules.length;
        for(var j=0; j<l; j++) parseCSSRule(rules[j]);
    } catch(securityException){}
}

function parseCSSRule(rule) {
    var select = rule.selectorText, style = rule.style.cssText;
    if(!csshoverReg.test(select) || !style) return;

    var pseudo = select.replace(/[^:]+:([a-z-]+).*/i, 'on$1');
    var newSelect = select.replace(/(\.([a-z0-9_-]+):[a-z]+)|(:[a-z]+)/gi, '.$2' + pseudo);
    var className = (/\.([a-z0-9_-]*on(hover|active))/i).exec(newSelect)[1];
    var affected = select.replace(/:(hover|active).*$/, '');
    var elements = getElementsBySelect(affected);
    if(elements.length == 0) return;

    currentSheet.addRule(newSelect, style);
    for(var i=0; i<elements.length; i++)
        new HoverElement(elements[i], className, activators[pseudo]);
}

function HoverElement(node, className, events) {
    if(!node.hovers) node.hovers = {};
    if(node.hovers[className]) return;
    node.hovers[className] = true;
    hookHoverEvent(node, events.on, function() { node.className += ' ' + className; });
    hookHoverEvent(node, events.off, function() { node.className = node.className.replace(new RegExp('\\s+'+className, 'g'),''); });
}
function hookHoverEvent(node, type, handler) {
    node.attachEvent(type, handler);
    hoverEvents[hoverEvents.length] = {
        node:node, type:type, handler:handler
    };
}

function unhookHoverEvents() {
    for(var e,i=0; i<hoverEvents.length; i++) {
        e = hoverEvents[i];
        e.node.detachEvent(e.type, e.handler);
    }
}

function getElementsBySelect(rule) {
    var parts, nodes = [doc];
    parts = rule.split(' ');
    for(var i=0; i<parts.length; i++) {
        nodes = getSelectedNodes(parts[i], nodes);
    }    return nodes;
}
function getSelectedNodes(select, elements) {
    var result, node, nodes = [];
    var identify = (/\#([a-z0-9_-]+)/i).exec(select);
    if(identify) {
        var element = doc.getElementById(identify[1]);
        return element? [element]:nodes;
    }
   
    var classname = (/\.([a-z0-9_-]+)/i).exec(select);
    var tagName = select.replace(/(\.|\#|\:)[a-z0-9_-]+/i, '');
    var classReg = classname? new RegExp('\\b' + classname[1] + '\\b'):false;
    for(var i=0; i<elements.length; i++) {
        result = tagName? elements[i].all.tags(tagName):elements[i].all;
        for(var j=0; j<result.length; j++) {
            node = result[j];
            if(classReg && !classReg.test(node.className)) continue;
            nodes[nodes.length] = node;
        }
    }   
   
    return nodes;
}

//天气
function st_change(num){
	if(num == 1){
		$('morningWeather').value = $('st' + num).value;
	} else {
		$('afternoonWeather').value = $('st' + num).value;
	}
	ritemsSaving();
}    

//左侧treeview单选效果
function tree_select(ob){
	if(window.parent.$('ritemsState').value == 1){
		alert('当前日志已被修改，并还未保存！\r\n请先保存日志后再进行操作...');
		return;
	}
	var dates = document.getElementsByName('tree_date');
	var yearNode = document.getElementsByName('tree_year');
	var monthNode = document.getElementsByName('tree_month');
	for(var i = 0; i < yearNode.length; i++){
		yearNode[i].style.color = '#666';
		yearNode[i].style.fontWeight = 'normal';
	}
	for(var i = 0; i < monthNode.length; i++){
		monthNode[i].style.color = '#666';
		monthNode[i].style.fontWeight = 'normal';
	}
	for(var i = 0; i < dates.length; i++){
		dates[i].style.color = '#666';
		dates[i].style.fontWeight = 'normal';
	}
	ob.style.color = '#F00';
	ob.style.fontWeight = 'bold';
	ob.parentNode.parentNode.parentNode.previousSibling.previousSibling.firstChild.style.color = '#F00';
	ob.parentNode.parentNode.parentNode.previousSibling.previousSibling.firstChild.style.fontWeight = 'bold';
	ob.parentNode.parentNode.parentNode.parentNode.parentNode.previousSibling.previousSibling.firstChild.style.color = '#F00';
	ob.parentNode.parentNode.parentNode.parentNode.parentNode.previousSibling.previousSibling.firstChild.style.fontWeight = 'bold';
	window.parent.document.getElementById("trid").value = ob.title;
    window.parent.document.getElementById("daily").src = 'getdaily.do?rid=' + ob.title + '&' + guid();
}


//左侧单选结果
function result_select(ob){
	var obs = ob.parentNode.childNodes;
	for(var i = 0; i < obs.length; i++){
		if(obs[i].nodeType == 1){
			obs[i].style.fontWeight='normal';
			obs[i].style.backgroundColor='#FFF';
			obs[i].style.color='#000';
			obs[i].style.border='0px';
		}
	}
	ob.style.backgroundColor = '#D2D2D2';
	ob.style.border = '1px solid #AAA';
	ob.style.color = '#F00';
	ob.style.fontWeight = 'bold';
}

//左侧'施工日期'与'日记查询'切换
function leftShow(ob){
	var index = ob.id.substring(3,4);
	if(index == 1){
		$('c1').style.display='block';
		$('div1').style.border = '1px solid #AAA';
		$('div1').style.backgroundColor = '#AAA';
		$('div1').style.fontWeight = 'bold';
		$('c2').style.display='none';
		$('div2').style.border = '1px solid #D2D2D2';
		$('div2').style.backgroundColor = '#D2D2D2';
		$('div2').style.fontWeight = 'normal';
	} else if(index == 2){
		$('c1').style.display='none';
		$('div1').style.border = '1px solid #D2D2D2';
		$('div1').style.backgroundColor = '#D2D2D2';
		$('div1').style.fontWeight = 'normal';
		$('c2').style.display='block';
		$('div2').style.border = '1px solid #AAA';
		$('div2').style.backgroundColor = '#AAA';
		$('div2').style.fontWeight = 'bold';
	}
}

//右侧各项记录切换
function configShow(ob){
	var obs = ob.parentNode.childNodes;
	var index = 1;
	for(var i = 0; i < obs.length; i++){
		if(obs[i].nodeType == 1){
			obs[i].className = 'config_show';
			var name = 'tag'+ index;
			$(name).style.display='none';
			index++;
		}
	}
	ob.className = 'config_show2';
	currentTagId = ob.id;
	$('tag'+ob.id.substring(1)).style.display='block';
}

//tagMouseOver效果
function tagMouseOver(ob){
	ob.className = 'config_show2';
}

//tagMouseOut效果
function tagMouseOut(ob){
	if(ob.id == currentTagId){
		return;
	}
	ob.className = 'config_show';
}

//右侧各项记录左右移动
function move_left(){
	if(show_index > 1){
		show_index--;
		var name = 't' + (show_index + 7);
		$(name).style.display = 'none';
		name = 't' + show_index;
		$(name).style.display = 'block';
	}
	changeMoveColor();
}

function move_right(){
	if(items - show_index >= 7){
		var name = 't' + show_index;
		$(name).style.display = 'none';
		name = 't' + (show_index + 7);
		$(name).style.display = 'block';
		show_index++;
	} 
	changeMoveColor();
}

//右侧各项记录左右移动图标样式
function changeMoveColor(){
	if(show_index > 1){
		if($('left_position_black')){
			$('left_position_black').id = 'left_position_red';
		}
	} else {
		if($('left_position_red')){
			$('left_position_red').id = 'left_position_black';
		}
	}
	if(show_index + 7 > items){
		if($('right_position_red')){
			$('right_position_red').id = 'right_position_black';
		}
	} else {
		if($('right_position_black')){
			$('right_position_black').id = 'right_position_red';
		}
	}
}

//左侧treeview
function addEvent(el,name,fn){//绑定事件
	if(el.addEventListener) return el.addEventListener(name,fn,false);
	return el.attachEvent('on'+name,fn);
}

function nextnode(node){//寻找下一个兄弟并剔除空的文本节点
	if(!node)return ;
	if(node.nodeType == 1)
		return node;
	if(node.nextSibling)
		return nextnode(node.nextSibling);
}

function prevnode(node){//寻找上一个兄弟并剔除空的文本节点
	if(!node)return ;
	if(node.nodeType == 1)
		return node;
	if(node.previousSibling)
		return prevnode(node.previousSibling);
}

//按照时间范围查询开启或关闭
function checkTimeInput(ob){
	if(ob.checked){
		$('bDate').removeAttribute('disabled');
		$('eDate').removeAttribute('disabled');
	} else {
		$('bDate').disabled = 'disabled';
		$('eDate').disabled = 'disabled';
	}
}

var dailyForm;
//保存日志
function saveDaily(){
	if($('ritemsState').value == 0){
		alert('当前日志没有任何修改...无需保存！');
		return;
	}
	var ofrm1 = document.getElementById("daily").document;
	if (ofrm1==undefined)
    {
    	dailyForm = document.getElementById("daily").contentWindow;
    }
    else
    {
    	dailyForm = document.frames["daily"];
    }
	var currentDate = '';
    currentDate = returnRecordElement('recordDate').innerHTML;
    if(currentDate == ''){
    	alert('保存失败！\r\n当前未选中任何日志...');
    	return;
    }
    var rid = returnRecordElement('rid').value;
    var morningWeather = returnRecordElement('morningWeather').value;
    var topTemper = returnRecordElement('topTemper').value;
    var afternoonWeather = returnRecordElement('afternoonWeather').value;
    var lowTemper = returnRecordElement('lowTemper').value;
    var ritems = returnRecordElement('rItems').childNodes;
    var items = '';
    for(var i = 0; i < ritems.length; i++){
    	if(ritems[i].nodeType == 1){
    		var temTxtarea = ritems[i].firstChild;
    		if(temTxtarea.nodeType != 1){
    			temTxtarea = temTxtarea.nextSibling;
    		}
    		if(temTxtarea.id == 'appendix'){
    			continue;
    		}
    		if(items != ''){
    			items += '@$^';
    		}
    		items = items + temTxtarea.id + ',' + temTxtarea.innerHTML;
    	}
    }
    items = 'rid=' + rid + '&amWeatherDesc=' + $c(morningWeather) + '&pmWeatherDesc=' + $c(afternoonWeather) + '&hCentigrade=' + topTemper + '&lCentigrade=' + lowTemper + '&items=' + $c(items);
    sendAjax('addRecords', items, 'resultSaveRecord');
}

function returnRecordElement(id){
	return dailyForm.document.getElementById(id);
}

//ajax保存日志
function resultSaveRecord(result){
	if(result == 'true'){
		alert('保存成功！');
		ritemsSaved();
		document.date_result.document.location.reload();
		setTimeout('treeviewSeleteById(' + window.parent.$('trid').value + ')', 300);
	} else {
		alert('保存失败！\r\n请检查输入是否包含非法字符...');
	}
}

//打印日志
function printDaily(){
	var trid = $('trid').value;
	if(trid == ''){
		alert('当前位选中任何日志...\r\n请选择您要打印的日志后再进行打印...');
		return;
	}
	window.print();
}

//删除日志
function delDaily(){
	var ofrm1 = document.getElementById("daily").document;
	if (ofrm1==undefined)
    {
    	dailyForm = document.getElementById("daily").contentWindow;
    }
    else
    {
    	dailyForm = document.frames["daily"];
    }
	if(dailyForm.$('rid').value == ''){
		alert('删除失败！\r\n请选择您要删除的日志...');
		return;
	}
	if(!confirm('你确定要删除该日志('+dailyForm.$('recordDate').innerHTML+')么？')){
		return;
	}
	var trid = dailyForm.$('rid').value;
	sendAjax('addRecords', 'rid=' + trid, 'resultDelDaily');
}

function resultDelDaily(result){
	if(result == 'true'){
		alert('删除成功！');
		//删除成功后清空index的rid，然后刷新daily页面
		$('trid').value = '';
		ritemsSaved();
		document.date_result.document.location.reload();
	} else {
		alert('删除失败！');
	}
}


//新建日志
function newDaily(){
	if($('ritemsState').value == 1){
		alert('当前日志已被修改，并还未保存！\r\n请先保存日志后再进行操作...');
		return;
	}
	if($('pro').value == 0){
		alert('新建失败！\r\n请选择工程项目后再新建日志...');
		return;
	}
   var nowDate = year + "-" + month + "-" + date;
   var contentText ='<form name="fRecord" action="addRecord" method="post">新建日志日期：&nbsp;&nbsp;' +
   '<input class="Wdate" id="newDate" value="' + nowDate + '" name="newDate" type="text" onClick="WdatePicker()" /><span class="red">*</span>&nbsp;&nbsp;&nbsp;&nbsp;' +
   '<a class="mybtn" href="javascript:addRecord();">保存</a></form><p style="color:#F00;" id="tip"></p>'; 
   showWindow(400, 100, contentText, false);
}

//根据日期新建日志
function addRecord(){
	var newDate = $('newDate').value;
	var proId = $('pro').value;
	sendAjax('addRecords', 'date='+ newDate +'&projectId=' + proId + '&rid=-1', 'resultAddNewRecord');
}

function resultAddNewRecord(result){
	if(!isNaN(result)){
		$('date_result').src = 'getDateTree?projectId=' + $('indexPid').value + '&' + guid();
		alert('新建日志成功！');
		closeWindow();
		ritemsSaved();
	   //新建成功后更改index的rid，然后刷新date页面
	   $('trid').value = result;
	   //document.date_result.document.location.reload();
	   setTimeout('treeviewSeleteById(' + $('trid').value + ')', 500);
	} else {
		$('tip').innerHTML = '新建日志出错！请检查该日期是否已存在！';
	}
}

//根据日志id选中treeview
function treeviewSeleteById(id){
	if(id == '' || id <= 0){
		return;
	}
	var dateForm;
	var ofrm1 = document.getElementById("date_result").document;
	if (ofrm1==undefined)
    {
		dateForm = document.getElementById("date_result").contentWindow;
    }
    else
    {
    	dateForm = document.frames["date_result"];
    }
	var tYears = dateForm.document.getElementById('menu_zzjs_net').childNodes;
	for(var i = 0; i < tYears.length; i++){
		var tMonths = tYears[i].lastChild.childNodes;
		for(var j = 0; j < tMonths.length; j++){
			var tDays = tMonths[j].lastChild.childNodes;
			for(var k = 0; k < tDays.length; k++){
				var recordId = tDays[k].firstChild.firstChild.title;
				if(recordId == id){
					tDays[k].parentNode.parentNode.parentNode.parentNode.firstChild.click();
					tDays[k].parentNode.parentNode.firstChild.click();
					tDays[k].firstChild.firstChild.onclick();
					return;
				}
			}
		}
	}
}

//将日志分类状态改为1（已编辑、未保存）
function ritemsSaving(){
	window.parent.$('ritemsState').value = 1;
}

//将日志分类状态改为0（已保存、已删除等）
function ritemsSaved(){
	$('ritemsState').value = 0;
}

//下拉列表切换工程名称，加载工程日志列表
function changeProject(ob){
	if($('ritemsState').value == 1){
		alert('当前日志已被修改，并还未保存！\r\n请先保存日志后再进行操作...');
		$('pro').value = $('indexPid').value;
		return;
	}
	pid = $('pro').value;
	$('pro').title = $('pro').options[$('pro').selectedIndex].innerHTML;
	$('date_result').src = 'getDateTree?projectId=' + ob.value + '&' + guid();
	$('daily').src = 'daily.jsp';
	$('indexPid').value = pid;
}

//左侧日志查询
function searchDate(){
	var keyWord = $('txtKeyWord').value;
	if(keyWord == ''){
		alert('查询内容不能为空...');
		$('txtKeyWord').focus();
		return;
	}
	var itemsId = $('searchItemsId').value;
	var values = 'projectId=' + window.parent.$('indexPid').value + '&ritemId=' + itemsId + '&content=' + $c(keyWord);
	if($('ckDate').checked){
		var bDate = $('bDate').value;
		var eDate = $('eDate').value;
		values = values + '&minDate=' + bDate + '&maxDate=' + eDate;
	}
	sendAjax('getdatetreebydatescopeorcontent', values, 'resultSearchDate');
}

var temValues = '';
//查询结果返回json
function resultSearchDate(result){
	$('search_result').innerHTML = '查询中...';
	if(result != '' && eval(result).length != 0){
		var results = eval(result);
		for(var i = 0; i < results.length; i++){
			temValues = temValues + '<p onclick="javascript:searchResultToDaily(this);" id="' + results[i].rid + '">' + results[i].date + '</p>';
		}
	} else {
		temValues = '<p class="simpleP" style="color:#F00;">没有符合条件的结果</p>';
	}
	setTimeout('showSearch()',500);
}

//根据左侧点击右侧显示日志信息
function searchResultToDaily(ob){
	if(window.parent.$('ritemsState').value == 1){
		alert('当前日志已被修改，并还未保存！\r\n请先保存日志后再进行操作...');
		return;
	}
	window.parent.document.getElementById("daily").src = 'getdaily.do?rid=' + ob.id + '&' + guid();
}

function showSearch(){
	$('search_result').innerHTML = temValues;
	temValues = '';
}

//班组管理-显示窗体
function classesManage(){
	if(pid == 0){
		alert('请选择项目后再添加班组...');
		return;
	}
	sendAjax('listClasses', 'pid=' + pid, 'resultListClasses');
}

//ajax获取班组列表
function resultListClasses(result){
	var classes = eval(result);
	var tr = '';
	for(var i = 0; i < classes.length; i++){
		tr = tr + '<tr bgcolor="#ffffff" onclick="javascript:checkClass(this);" id="class'+ i +'">'+
		'<td><input id="class_id'+ i +'" value="'+ classes[i].cgid +'" type="hidden" />'+
		'</td><td>'+ classes[i].cgname +'</td>'+
		'<td>'+ classes[i].totalWorker +'</td>'+
		'<td>'+ classes[i].bosshead +'</td></tr>';	
	}
	var contentText = '<input value="-1" id="manage_class_id" type="hidden" /><input value="-1" id="manage_class_name" type="hidden" /><input value="-1" id="manage_class_number" type="hidden" /><input value="-1" id="manage_class_people" type="hidden" />'+
	'<p style="font-weight:bold; text-align:center; font-size:16px;">班组管理</p><div id="manage_div">' + 
	'<table id="manage_table" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">'+ 
	'<thead><tr><th></th><th>班组名称</th><th>总人数</th><th>负责人</th></tr></thead><tbody>'+
	tr +
	'</tbody></table></div><p class="bts"><span onclick="javascript:addOrAlterClassWindow(\'add\');">添加班组</span>'+
	'<span onclick="javascript:addOrAlterClassWindow(\'alter\');">修改班组</span>'+
	'<span onclick="javascript:editClass(\'del\', \'\', \'\', \'\', \'\');">删除班组</span></p>';
	showWindow(400, 400, contentText, false);
}

// 班组管理-选择班组
function checkClass(ob){
	var trs = ob.parentNode.childNodes;
	if(ob.bgColor == '#bbbbbb'){
		ob.bgColor = '#ffffff';
		$('manage_class_id').value = '-1';
	} else {
		for(var i = 0; i < trs.length; i++){
			if(trs[i].nodeType == 1 && trs[i].bgColor == '#bbbbbb'){
				trs[i].bgColor = '#ffffff';
			}
		}
		ob.bgColor = '#bbbbbb';
		$('manage_class_id').value = $('class_id' + ob.id.substring(5,6)).value;
		var rowindex = 0;
		var tds = ob.childNodes;
		for(var i = 0; i < tds.length; i++){
			if(tds[i].nodeType == 1){
				rowindex++;
				if(rowindex == 2){
					$('manage_class_name').value = tds[i].innerHTML;
				}
				if(rowindex == 3){
					$('manage_class_number').value = tds[i].innerHTML;
				}
				if(rowindex == 4){
					$('manage_class_people').value = tds[i].innerHTML;
				}
			}
		}
	}
}


//关闭窗体
function closeWindow(){
	document.body.removeChild($('bgDiv'));
	document.getElementById("msgDiv").removeChild($('msgTitle'));
	document.body.removeChild($('msgDiv'));
}


//添加修改班组
function addOrAlterClassWindow(type){
	if(type == 'alter' && $('manage_class_id').value == '-1'){
		alert('请选择要修改的班级...');
		return;
	}
	var id = $('manage_class_id').value;
	var className = $('manage_class_name').value;
	var number = $('manage_class_number').value;
	var people = $('manage_class_people').value;
	if(type == 'add'){
		id = '-1';
		className = '';
		number = '';
		people = '';
	}
	closeWindow();
	var contentText ='<p style="font-weight:bold; text-align:center; font-size:16px;">班组管理</p>' + 
   '<form name="ff" action="addoralterclasses.do" method="post"><table id="manage_table" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">' + 
   '<tr><td>班组名称：</td><td><input maxlength="50" id="className" style="width:200px;" name="cgname" value="'+ className +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>总人数&nbsp;：</td><td><input style="width:200px;" onkeyup="javascript:getInt(this);" onblur="javascript:getInt(this);"name="totalWorker" id="number" value="'+ number +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>负责人&nbsp;：</td><td><input maxlength="20" id="people" style="width:200px;" name="bosshead" value="'+ people +'" /><span class="red">*</span></td></tr>' +
   '</table><input type="hidden" id="cid" name="cgid" value="'+ id +'" /><p style="margin-left:100px;" class="bts"><span onclick="javascript:editClass(\'add\');">保存</span><span onclick="javascript:closeWindow();classesManage();">取消</span></p></form>';
   showWindow(400, 200, contentText, false);
}

//班组添加修改删除
function editClass(type){
	var id;
	if(type == 'del'){
		id = $('manage_class_id').value;
		if(id == '-1'){
			alert('请选择要删除的班组...');
			return;
		}
		if(!confirm('你确定要删除该班组么？')){
			return;
		}
		sendAjax('editClasses', 'cgid='+ id, 'resultEditClassesDel');
	} else {
		id = $('cid').value;
		var className = $('className').value;
		if(className == ''){
			alert('班组名称不能为空...');
			$('className').focus();
			return;
		}
		var totalWorker = $('number').value;
		if(totalWorker == ''){
			alert('总人数不能为空...');
			$('number').focus();
			return;
		}
		var bosshead = $('people').value;
		if(bosshead == ''){
			alert('负责人不能为空...');
			$('people').focus();
			return;
		}
		sendAjax('editClasses', 'pid='+ pid +'&cgid='+ id +'&cgname='+ $c(className) +'&totalWorker='+ totalWorker +'&bosshead='+ $c(bosshead), 'resultEditClasses');
	}
}

//删除班组
function resultEditClassesDel(result){
	if(result == 'true'){
		alert('删除成功！');
	} else {
		alert('删除失败,请检查该班组是否存在...');
	}
	closeWindow();
	classesManage();
}

//班组编辑结果
function resultEditClasses(result){
	if(result == 'true'){
		alert('保存成功！');
		closeWindow();
		classesManage();
	} else if(result == 'false') {
		alert('保存失败,请检查输入是否正确...');
	} else {
		alert('保存失败！' + result);
	}
}

//项目管理-选择项目
function checkPro(ob){
	var trs = ob.parentNode.childNodes;
	if(ob.bgColor == '#bbbbbb'){
		ob.bgColor = '#ffffff';
		$('manage_project_id').value = '-1';
	} else {
		for(var i = 0; i < trs.length; i++){
			if(trs[i].nodeType == 1 && trs[i].bgColor == '#bbbbbb'){
				trs[i].bgColor = '#ffffff';
			}
		}
		ob.bgColor = '#bbbbbb';
		$('manage_project_id').value = $('project_id' + ob.id.substring(7,8)).value;
	}
}

//工程项目管理
function projectManage(){
	if($('ritemsState').value == 1){
		alert('当前日志已被修改，并还未保存！\r\n请先保存日志后再进行操作...');
		return;
	}
	sendAjax('getProjectList', null, 'resultListProject');
}

//点击工程列表加载工程
function loadProjects(){
	sendAjax('getProjectList', null, 'resultLoadProject');
}

function resultLoadProject(result){
	
	var projects = eval(result);
	$('pro').options.length = 0;
	var o = new Option('------------------请选择------------------', '0');
	o.setAttribute('title', '请选择');
	$('pro').options.add(o);
	for(var i = 0; i < projects.length; i++){
		var o = new Option(projects[i].projectName, projects[i].pid);
		o.setAttribute('title', projects[i].projectName);
		$('pro').options.add(o);
	}
	if($('indexPid').value != ''){
		$('pro').value = $('indexPid').value;
	}
	if($('pro').value == ''){
		$('pro').value = 0;
	}
}

function resultListProject(result){
	var trs = '';
	var projects = eval(result);
	for(var i = 0; i < projects.length; i++){
		trs = trs + '<tr title="'+projects[i].projectName+'" bgcolor="#ffffff" onclick="javascript:checkPro(this);" id="project'+i+'"><td><input id="project_id'+i+'" value="'+projects[i].pid+'" type="hidden" /></td><td>'+projects[i].projectName+'</td></tr>';
	}
	var contentText = '<input value="-1" id="manage_project_id" type="hidden" />'+
	'<p style="font-weight:bold; text-align:center; font-size:16px;">工程项目管理</p><div id="manage_div">' + 
	'<table id="manage_table" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">'+ 
	'<thead><tr><th></th><th>工程项目名称</th></tr></thead><tbody>'+
	trs +
	'</tbody></table></div><p class="bts"><span onclick="javascript:editProject(\'add\');">添加项目</span>'+
	'<span onclick="javascript:editProject(\'alter\');">修改项目</span>'+
	'<span onclick="javascript:editProject(\'del\');">删除项目</span></p>';
	showWindow(400, 400, contentText, false);
	loadProjects();
}

//添加或修改工程项目
function editProject(type){
	if(type == 'alter'){
		if($('manage_project_id').value == '-1'){
			alert('请选择要修改的项目名...');
			return;
		} else {
			editType = 'alter';
			//ajax获取项目参数
			sendAjax('getProject', 'pid=' + $('manage_project_id').value, 'resultAlterProject');
		}
		return;
	}
	else if(type == 'del'){
		if($('manage_project_id').value == '-1'){
			alert('请选择要删除的项目名...');
			return;
		} else {
			if(!confirm('你确定要删除该项目么？\r\n删除前请确定该工程下不包含任何日志，否则将失败！')){
				return;
			}
			sendAjax('editProject', 'pid=' + $('manage_project_id').value, 'resultProjectDel');
		}
		return;
	}
	else if(type == 'add'){
		editType = 'add';
		addOrAlterProjectWindow($('manage_project_id').value, '', '', '', '', '', '', '', year + "-" + month + "-" + date);
	}
}

function resultProjectDel(result){
	if(result == 'true'){
		loadProjects();
		alert('删除成功！');
	} else {
		alert('删除失败...\r\n请检查该工程是否包含日志...');
	}
	closeWindow();
	projectManage();
}

//修改工程获取工程信息
function resultAlterProject(result){
	var id = $('manage_project_id').value;
	var p = eval(result);
	addOrAlterProjectWindow(id, p[0].projectName, p[0].buildingUnits, p[0].workingSpace, p[0].designUnits, p[0].constructionUnits, p[0].supervisingUnits, p[0].superviserName, p[0].createDate);
}

//添加和修改工程窗体
function addOrAlterProjectWindow(id, proName, proUnit, proAddress, devUnit, buildUnit, supUnit, supPeople, proDate){
	closeWindow();
	var contentText ='<p style="font-weight:bold; text-align:center; font-size:16px;">工程项目管理</p>' + 
   '<form name="ff" action="addoralterproject.do" method="post"><table id="manage_table" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">' + 
   '<tr><td>工程名称：</td><td><input id="proName" style="width:200px;" name="proName" value="'+ proName +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>建设单位：</td><td><input id="proUnit" style="width:200px;" name="proUnit" value="'+ proUnit +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>工程地点：</td><td><input id="proAddress" style="width:200px;" name="proAddress" value="'+ proAddress +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>设计单位：</td><td><input id="devUnit" style="width:200px;" name="devUnit" value="'+ devUnit +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>施工单位：</td><td><input id="buildUnit" style="width:200px;" name="buildUnit" value="'+ buildUnit +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>监理单位：</td><td><input id="supUnit" style="width:200px;" name="supUnit" value="'+ supUnit +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>监制人：</td><td><input maxlength="20" id="supPeople" style="width:200px;" name="supPeople" value="'+ supPeople +'" /><span class="red">*</span></td></tr>' +
   '<tr><td>编制日期：</td><td><input class="Wdate" id="projectDate" value="'+ proDate +'" name="beginDate" type="text" onclick="WdatePicker()" /><span class="red">*</span></td></tr>' +
   '</table><input id="projectId" type="hidden" name="projectId" value="'+ id +'" /><p style="margin-left:0px;" class="bts">'+
   '<span onclick="javascript:saveProject(\'out\');">保存并退出</span>'+
   '<span style="margin-left:30px;" onclick="javascript:saveProject(\'next\');">保存并进入日志分类</span>'+
   '<span style="margin-left:30px;" onclick="javascript:closeWindow();projectManage();">取消</span></form>';
   showWindow(400, 330, contentText, false);
}

//保存工程并进入日志分类
function saveProject(t){
	editType += t;
	if($('proName').value == ''){
		alert('工程名称不能为空...');
		$('proName').focus();
		return;
	}
	if($('projectDate').value == ''){
		alert('编制日期不能为空...');
		$('projectDate').focus();
		return;
	}
	if($('proUnit').value == ''){
		alert('建设单位不能为空...');
		$('proUnit').focus();
		return;
	}
	if($('proAddress').value == ''){
		alert('工程地点不能为空...');
		$('proAddress').focus();
		return;
	}
	if($('devUnit').value == ''){
		alert('设计单位不能为空...');
		$('devUnit').focus();
		return;
	}
	if($('buildUnit').value == ''){
		alert('施工单位不能为空...');
		$('buildUnit').focus();
		return;
	}
	if($('supUnit').value == ''){
		alert('监理单位不能为空...');
		$('supUnit').focus();
		return;
	}
	if($('supPeople').value == ''){
		alert('监制人不能为空...');
		$('supPeople').focus();
		return;
	}
	var pid = $('projectId').value;
	var proName = $('proName').value;
	var proUnit = $('proUnit').value;
	var proAddress = $('proAddress').value;
	var devUnit = $('devUnit').value;
	var buildUnit = $('buildUnit').value;
	var supUnit = $('supUnit').value;
	var supPeople = $('supPeople').value;
	var projectDate = $('projectDate').value;
	//ajax保存工程
	sendAjax('editProject', 'pid=' + pid + '&projectName=' + $c(proName) + '&buildingUnits=' + $c(proUnit) + '&workingSpace=' + $c(proAddress) + '&designUnits=' + $c(devUnit) + '&constructionUnits=' + $c(buildUnit) + '&supervisingUnits=' + $c(supUnit) + '&superviserName=' + $c(supPeople) + '&createDate=' + projectDate, 'resultEditProject');
}

//编辑工程项目
function resultEditProject(result){
	var pid = $('projectId').value;
	if(result == 'true' || result > 0){
		alert('保存成功...');
		if(editType == 'addnext'){
			$('projectId').value = result;
			editProjectId = result;
			resultloadKindWindow('');
		} else if(editType == 'alternext'){
			editProjectId = pid;
			sendAjax('recordItem', 'pid='+pid, 'resultloadKindWindow');
		} else {
			closeWindow();
			projectManage();
		}
	} else {
		alert('保存失败！' + result);
	}
	editType = '';
}

function resultloadKindWindow(result){
	loadProjects();
	var trs = '';
	var id = $('projectId').value;
	closeWindow();
	if(result != ''){
		var kinds = eval(result);
		for(var i = 0; i < kinds.length; i++){
			trs = trs + '<tr><td><input value="'+kinds[i].ritemId+'" type="hidden" /></td><td><input disabled="disabled" id="kindValue" onchange="javascript:getEnableValue(this);" maxlength="50" onblur="javascript:blur_input(this);" type="text" id="pro'+kinds[i].ritemId+'" name="pro'+kinds[i].ritemId+'" value="'+kinds[i].ritemName+'" /></td><td><a onclick="javascript:click_input(this);" href="javascript:void(0);">修改</a><a style="margin-left:10px;" onclick="javascript:delKindByJS(this);" href="javascript:void(0);">删除</a></td></tr>';
		}
	}
	var contentText = '<input value="'+id+'" id="manage_project_id" type="hidden" />'+
	'<p style="font-weight:bold; text-align:center; font-size:16px;">日志分类管理</p><div style="height:240px; overflow:auto;" id="manage_div1">' + 
	'<table id="manage_kind" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">'+ 
	'<thead><tr><th></th><th colspan="2">日志分类名称</th></tr></thead><tbody id="kindTBody">'+
	trs +
	'</tbody></table></div><p style="margin-top:16px;">分类名称：<input type="text" id="newtype" maxlength="50" value="" /><span id="kind_btn" onclick="javascript:addKindsByJS();">添加分类</span></p>'+
	'<p style="margin-left:40px;" class="bts"><span onclick="javascript:saveKind();">保存退出</span><span onclick="javascript:backProject();">取消并返回工程列表</span></p><input id="toServerValue" value="" type="hidden" />';
	showWindow(400, 410, contentText, false);
}

function delKindByJS(ob){
	if(!confirm('你确定要删除该日志分类么？')){
		return;
	}
	var tid = ob.parentNode.parentNode.firstChild.firstChild.value;
	if(tid != '?'){
		$('toServerValue').value = $('toServerValue').value + ',' + tid + '=>!';
	}
	ob.parentNode.parentNode.parentNode.removeChild(ob.parentNode.parentNode);
}
//保存分类
function saveKind(){
	var kindElements = $('kindTBody').childNodes;
	var st = '';
	for(var i = 0; i < kindElements.length; i++){
		if(kindElements[i].nodeType == 1){
			var kid = kindElements[i].firstChild.firstChild.value;
			var kvalue = kindElements[i].firstChild.nextSibling.firstChild.value;
			st = st + kid + '=>' + kvalue + ',';
		}
	}
	st = st.substring(0, st.length - 1) + $('toServerValue').value;
	sendAjax('savekinds', 'ritemName=' + $c(st) + '&pid=' + editProjectId, 'resultSaveKinds');
}

function resultSaveKinds(result){
	if(result == 'true'){
		alert('保存成功！');
		closeWindow();
		document.date_result.document.location.reload();
		setTimeout('treeviewSeleteById(' + $('trid').value + ')', 300);
	} else {
		alert('保存失败...\r\n\r\n原因：\r\n1.新增失败：请检查分类名是否合法,分类名是否重复,或检查项目是否已被删除!\r\n2.删除失败：请检查该分类是否已包含数据.只有在删除该分类所有数据后才能删除该分类！\r\n3.修改失败：请检查分类名是否合法，分类是否存在!');
		closeWindow();
	}
}

function addKindsByJS(){
	if($('newtype').value == ''){
		alert('添加出错!\r\n分类名不能为空...');
		$('newtype').focus();
		return;
	}
	if(!checkvalue.test($('newtype').value)){
		alert('添加出错!\r\n分类名只能是中文、数字、或者英文字母!');
		return;
	}
	var mytr = document.createElement('tr');
	var mytdId = document.createElement('td');
	var mytdValue = document.createElement('td');
	var mytdA = document.createElement('td');
	var myinputId = document.createElement('input');
	var myinputValue = document.createElement('input');
	var myAAlert = document.createElement('a');
	var myA = document.createElement('a');
	myinputId.setAttribute('value', '?');
	myinputId.setAttribute('type', 'hidden');
	myinputId.setAttribute('id', 'kindId');
	mytdId.appendChild(myinputId);
	myinputValue.setAttribute('value', $('newtype').value);
	myinputValue.setAttribute('type', 'text');
	myinputValue.setAttribute('maxlength', '25');
	myinputValue.setAttribute('id', '');
	myinputValue.setAttribute('disabled', 'disabled');
	myinputValue.onblur=function(){blur_input(this);};
	myinputValue.onchange=function(){getEnableValue(this);};
	mytdValue.appendChild(myinputValue);
	myAAlert.setAttribute('href', 'javascript:void(0);');
	myAAlert.innerHTML = '修改';
	myAAlert.onclick = function(){click_input(this); };
	myA.setAttribute('href', 'javascript:void(0);');
	myA.setAttribute('style', 'margin-left:10px;');
	myA.innerHTML = '删除';
	myA.onclick = function(){delKindByJS(this); };
	mytdA.appendChild(myAAlert);
	mytdA.appendChild(myA);
	mytr.appendChild(mytdId);
	mytr.appendChild(mytdValue);
	mytr.appendChild(mytdA);
	$('kindTBody').appendChild(mytr);
	$('newtype').value = '';
	$('newtype').focus();
}

//激活input
function click_input(ob){
	ob = ob.parentNode.parentNode.getElementsByTagName('td')[1].getElementsByTagName('input')[0];
	ob.removeAttribute('disabled');
	//ob.style.color='#000';
}

//反激活input
function blur_input(ob){
	getEnableValue(ob);
	ob.disabled='disabled';
	//ob.style.color='#AAA';
}

//返回工程列表
function backProject(){
	closeWindow();
	projectManage();
}


//日志中添加班组记录窗体
function addRecordClassesWindow(){
	if($('recordDate').innerHTML == ''){
		alert('请先选择日志后再进行班组记录添加...');
		return;
	}
	//ajax加载所有班组信息
	sendAjax('listClasses', 'pid=' + window.parent.$('pro').value, 'resultAddRecordClassesWindow');
}

function resultAddRecordClassesWindow(result){
	var classes = eval(result);
	var ops = '<option title="请选择" value="0">请选择班组</option>';
	for(var i = 0; i < classes.length; i++){
		var temClass = JSON.stringify(classes[i]).replace(/"/g, '\'');
		ops = ops + '<option title="'+classes[i].cgname+'" value="'+temClass+'">'+classes[i].cgname+'</option>';
	}
	var contentText ='<p style="font-weight:bold; text-align:center; font-size:16px;">添加班组情况</p>' + 
   '<table id="manage_table" border="0" cellpadding="0" cellspacing="0" style=" width:320px; margin:auto;">' + 
   '<tr><td>班组名称：</td><td><select id="classItemsClass" onchange="javascript:changeClassItems();" style="width:200px; overflow:hidden;">'+
   ops +
   '</select><span class="red">*</span></td>'+
   '</tr><tr><td>参加人数：</td><td><input id="classItemsIdBuildNumber" style="width:200px;" onkeyup="javascript:getInt(this);" onblur="javascript:getInt(this);" name="total" value="" /><span class="red">*</span></td></tr>' +
   '<tr><td>施工项目：</td><td><input id="classItemsIdBuildProject" onkeyup="javascript:getEnableString(this);" onblur="javascript:getEnableString(this);" style="width:200px;" value="" /><span class="red">*</span></td></tr>' +
   '<tr><td>施工部位：</td><td><input id="classItemsIdBuildPart" onkeyup="javascript:getEnableString(this);" onblur="javascript:getEnableString(this);" style="width:200px;" value="" /><span class="red">*</span></td></tr>' +
   '<tr><td>施工情况：</td><td><input id="classItemsBuildSituation" onkeyup="javascript:getEnableString(this);" onblur="javascript:getEnableString(this);" style="width:200px;" value="" /><span class="red">*</span></td></tr>' +
   '</table><input id="classItemsClassId" type="hidden" value="" />'+
   '</table><input id="classItemsClassName" type="hidden" value="" />'+
   '<input id="classItemsClassPeople" type="hidden" value="" />'+
   '<input id="classItemsClassNumber" type="hidden" value="" />'+
   '<p align="center" style="color:#F00;" id="errMessageClassItem"></p>'+
   '<p style="margin-left:100px;" class="bts"><span onclick="javascript:addRecordClasses();">添加班组情况</span><span onclick="javascript:closeWindow();">取消</span></p>'; 
   showWindow(400, 270, contentText, true);
   changeClassItems(window.parent.$('classItemsClass').options[window.parent.$('classItemsClass').selectedIndex].title.cgname);
}

//选择班组
function changeClassItems(){
	var currentClass = eval('('+window.parent.$('classItemsClass').value+')');
	window.parent.$('classItemsClassId').value = currentClass.cgid;
	window.parent.$('classItemsClassName').value = currentClass.cgname;
	window.parent.$('classItemsClassPeople').value = currentClass.bosshead;
	window.parent.$('classItemsClassNumber').value = currentClass.totalWorker;
	 
}

//日志中添加班组记录
function addRecordClasses(){
	if($('classItemsClass').value == '0'){
		alert('请选择班组名称...');
		$('classItemsClass').focus();
		return;
	}
	if($('classItemsIdBuildNumber').value == ''){
		alert('请输入参加人数...');
		$('classItemsIdBuildNumber').focus();
		return;
	}
	if($('classItemsIdBuildProject').value == ''){
		alert('请填写施工项目...');
		$('classItemsIdBuildProject').focus();
		return;
	}
	if($('classItemsIdBuildPart').value == ''){
		alert('请填写施工部位...');
		$('classItemsIdBuildPart').focus();
		return;
	}
	if($('classItemsBuildSituation').value == ''){
		alert('请填写施工情况...');
		$('classItemsBuildSituation').focus();
		return;
	}
	//添加班组ul情况
	var classId = $('classItemsClassId').value;
	var attendNumber = $('classItemsIdBuildNumber').value;
	var classBuildProject = $('classItemsIdBuildProject').value;
	var classBuildPart = $('classItemsIdBuildPart').value;
	var classBuildSituation = $('classItemsBuildSituation').value;
	sendAjax('recordconstructiongroup', 'rid=' + $('trid').value + '&arrivedNumber=' + attendNumber + '&cgid=' + classId + '&constructionProjectName=' + $c(classBuildProject) + '&constructPart=' + $c(classBuildPart) + '&constructStatus=' + $c(classBuildSituation), 'resultAddClassItem');
}

function resultAddClassItem(result){
	if(!isNaN(result)){
		var classId = result;
		var className = $('classItemsClassName').value;
		var classBuildNumber = $('classItemsIdBuildNumber').value;
		var attendNumber = $('classItemsIdBuildNumber').value;
		var bossHead = $('classItemsClassPeople').value;
		var classBuildProject = $('classItemsIdBuildProject').value;
		var classBuildPart = $('classItemsIdBuildPart').value;
		var classBuildSituation = $('classItemsBuildSituation').value;
		
		var group = document.createElement('ul');
		group.innerHTML = '<li class="classId"><input type="hidden" id="rcgid" name="classId" value="'+classId+'" /></li>'
						+ '<li title="' + className + '" id="cgname" class="className">'+className+'</li>'
						+ '<li title="' + classBuildNumber + '" id="totalWorker" class="totalNum">'+classBuildNumber+'</li>'
						+'<li title="' + attendNumber + '" id="arrivedNumber" class="arrivedNum">'+attendNumber+'</li>'
						+'<li title="' + bossHead + '" id="bosshead" class="bosshead">'+bossHead+'</li>'
						+'<li title="' + classBuildProject + '" id="constructionProjectName" class="pname">'+classBuildProject+'</li>'
						+'<li title="' + classBuildPart + '" id="constructPart" class="cpart">'+classBuildPart+'</li>'
						+'<li title="' + classBuildSituation + '" id="constructStatus" class="status">'+classBuildSituation+'</li>'
						+'<li class="op"><a href="javascript:void(0);" onclick="javascript:delClassItem(this);">删除</a></li>';
		var temForm;
		var ofrm1 = document.getElementById("daily").document;
		if (ofrm1==undefined)
	    {
			temForm = document.getElementById("daily").contentWindow;
	    }
	    else
	    {
	    	temForm = document.frames["daily"];
	    }
		temForm.$('classes').appendChild(group);
		alert('添加成功！');
		closeWindow();
	} else {
		$('errMessageClassItem').innerHTML = '添加失败，请检查当前输入是否合法...';
	}
	
}

var tempOb;
//删除班组情况
function delClassItem(ob){
	if(!confirm('你确定要删除该班组情况么？')){
		return;
	}
	tempOb = ob;
	var classItemId = ob.parentNode.parentNode.firstChild.firstChild.value;
	sendAjax('recordconstructiongroup', 'rcgid=' + classItemId, 'resultDelClassItem');
}

function resultDelClassItem(result){
	if(result == 'true'){
		alert('删除成功！');
		tempOb.parentNode.parentNode.parentNode.removeChild(tempOb.parentNode.parentNode);
		tempOb = null;
	} else {
		alert('删除失败...');
	}
}

//根据宽度，高度，显示内容显示窗体
function showWindow(msgw, msgh, contentText, isParent){
	//msgw提示窗口的宽度，msgh提示窗口的高度，contentText窗体中显示的内容
	titleheight = 25; // 提示窗口标题高度
	bordercolor = "#AAA";// 提示窗口的边框颜色
	titlecolor = "#D2D2D2";// 提示窗口的标题颜色

	var sWidth, sHeight;
	sWidth = window.parent.document.body.offsetWidth;
	sHeight = window.parent.screen.height;
	var bgObj = document.createElement("div");
	bgObj.setAttribute('id', 'bgDiv');
	bgObj.style.position = "absolute";
	bgObj.style.top = "0";
	bgObj.style.background = "#777";
	bgObj.style.filter = "progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
	bgObj.style.opacity = "0.6";
	bgObj.style.left = "0";
	bgObj.style.width = sWidth + "px";
	bgObj.style.height = sHeight + "px";
	bgObj.style.zIndex = "10000";
	if(isParent){
		window.parent.document.body.appendChild(bgObj);
	} else {
		document.body.appendChild(bgObj);
	}

	var msgObj = document.createElement("div");
	msgObj.setAttribute("id", "msgDiv");
	msgObj.setAttribute("align", "center");
	msgObj.style.background = "white";
	msgObj.style.border = "1px solid " + bordercolor;
	msgObj.style.position = "absolute";
	msgObj.style.left = "50%";
	msgObj.style.top = "30%";
	msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
	msgObj.style.marginLeft = "-225px";
	msgObj.style.marginTop = -75 + document.documentElement.scrollTop + "px";
	msgObj.style.width = msgw + "px";
	msgObj.style.height = msgh + "px";
	msgObj.style.textAlign = "center";
	msgObj.style.lineHeight = "25px";
	msgObj.style.zIndex = "10001";

	var title = document.createElement("h4");
	title.setAttribute("id", "msgTitle");
	title.setAttribute("align", "right");
	title.style.margin = "0";
	title.style.padding = "3px";
	title.style.background = bordercolor;
	title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
	title.style.opacity = "0.75";
	title.style.border = "1px solid " + bordercolor;
	title.style.height = "18px";
	title.style.font = "12px Verdana, Geneva, Arial, Helvetica, sans-serif";
	title.style.color = "white";
	title.style.cursor = "pointer";
	title.innerHTML = "关闭";
	title.onclick = function() {
		if(isParent){
			window.parent.document.body.removeChild(bgObj);
			window.parent.document.getElementById("msgDiv").removeChild(title);
			window.parent.document.body.removeChild(msgObj);
		} else {
			document.body.removeChild(bgObj);
			document.getElementById("msgDiv").removeChild(title);
			document.body.removeChild(msgObj);
		}
	};
	if(isParent){
		window.parent.document.body.appendChild(msgObj);
		window.parent.document.getElementById("msgDiv").appendChild(title);
	} else {
		document.body.appendChild(msgObj);
		document.getElementById("msgDiv").appendChild(title);
	}
	var txt = document.createElement("div");
	txt.style.margin = "auto";
	txt.style.padding = "20px 0 0 0";
	txt.setAttribute("id", "msgTxt");
	nowDate = year + "-" + month + "-" + date;
	txt.innerHTML = contentText;
	if(isParent){
		window.parent.document.getElementById("msgDiv").appendChild(txt); 
	} else {
		document.getElementById("msgDiv").appendChild(txt); 
	}
}

//模式窗体请求上传
function requestUpload(){
	if($('recordDate').innerHTML == ''){
		alert('请先选择日志后再上传附件...');
		return;
	}
	window.showModalDialog('fileuploadwindow?pid=' + window.parent.$('pro').value + '&logDate='+ $('recordDate').innerHTML, window, "dialogWidth:620px;dialogHeight:280px;center:yes;status:no;scroll:yes;help:no;");
	updateDownLoadList();
}

//刷新下载列表
function updateDownLoadList(){
	if($('recordDate').innerHTML == ''){
		return;
	}
	sendAjax('filedownload', 'rid=' + $('rid').value, 'resultUpdateDownLoadList');
}

function resultUpdateDownLoadList(result){
	if(result == ''){
		return;
	}
	var files = eval(result);
	var values = '';
	for(var i = 0; i < files.length; i++){
		values = values + '<li class="fileName">'+files[i].name+'</li><li class="fileOp"><a target="_blank" href="file/download?filename='+files[i].name+'">下载</a><a class="fileDel" onclick="javascript:delFile(this);" href="javascript:void(0);">删除</a></li>';
	}
	$('fileContent').innerHTML = values;
}

//删除附件
function delFile(tob){
	if(!confirm('确定要删除该附件？')){
		return;
	}
	sendAjax('filedelete', 'pid='+window.parent.$('pro').value+'&logDate='+$('recordDate').innerHTML+'&delete=' + tob.parentNode.parentNode.getElementsByTagName('li')[0].innerHTML, 'resultDelFile');
}

function resultDelFile(result){
	if(result == 'true'){
		alert('删除成功！');
	} else {
		alert('删除失败！');
	}
	updateDownLoadList();
}

//ajax
function sendAjax(target, values, methodName) {
	xmlhttpResult = '';
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
	}
	xmlhttp.open('POST', target, true);
	xmlhttp.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
	xmlhttp.send(values);
	xmlhttp.onreadystatechange = function result() {
		if (xmlhttp.readyState == 4){
			if(xmlhttp.status == 200) {
				xmlhttpResult = xmlhttp.responseText;
				setTimeout(methodName+'(xmlhttpResult)', 0);
			} else{
				alert('访问出错！请刷新后重试！');
			}
		} 
	};
}

//guid
function G() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
}
function guid(){
	return (G() + G() + "-" + G() + "-" + G() + "-" + G() + "-" + G() + G() + G()).toUpperCase();
}



