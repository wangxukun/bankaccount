<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/datagrid.css">
<style type="text/css">
html{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	margin:0 auto;
	padding:0 auto;
}
table#dg{
	margin:0 auto;
	padding:0 auto;
	width:900px;
	height:400px;
	background: red;
}
</style>
<script type="text/javascript">
$(function(){
	$('#dg').datagrid({
		url:'${pageContext.request.contextPath }/servlet/JsonDataAccountDetail',
		fit: false,
		fitColumns: false,
		singleSelect: true,
		title:'<center>单位：水阁村委会</center>',
		width:1050,
		height:700,
		toolbar:'#tb',
		footer:'#ft',
		columns:[[
			         {field:'year',title:'2014年',halign:'center',colspan:2,width:60,align:'center'},
			         {field:'voucherNum',title:'凭证',halign:'center',rowspan:2,width:50,align:'center'},
			         {field:'village',title:'单位名称',halign:'center',rowspan:2,width:140},
			         {field:'summary',title:'摘要',halign:'center',rowspan:2,width:300},
			         {field:'debit',title:'借方',halign:'center',rowspan:2,width:130},
			         {field:'credit',title:'贷方',halign:'center',rowspan:2,width:130},
			         {field:'balanceCredit',title:'借\\贷',halign:'center',rowspan:2,width:50,align:'center'},
			         {field:'balance',title:'余额',halign:'center',rowspan:2,width:130}
		         ],
		         [
					 {field:'month',title:'月',halign:'center',width:30,align:'center'},
					 {field:'day',title:'日',halign:'center',width:30,align:'center'}
		         ]],
		         queryParams:{
		        	 accountid:'${info.accountid}',
		        	 startDate:"",
		        	 endDate:"",
		        	 groupid:""
		        }
	});
	//-----------所属单位组合框树--------------------------
	var g_selectd_groupid = "";
	$('#comboTree').combotree({
		url:'${pageContext.request.contextPath }/servlet/JsonDataAccountTree',
		method:'post',
		animate:true,
		lines:true,
		queryParams:{accountTree:'${accountTree}'},
		required:false,
		onSelect:function(node){
			g_selectd_groupid = node.id;
		}
	}); 
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$('#searchBtn').bind('click',function(){
		var startdate = $('#startDate').datebox('getValue');
		var enddate = $('#endDate').datebox('getValue');
		var groupid = g_selectd_groupid;
		$('#dg').datagrid('load',{
			accountid:'${info.accountid}',
	       	startDate:startdate,
	       	endDate:enddate,
	       	groupid:groupid
		});
	});
});

</script>

</head>
<body>
   	<table id="dg"></table>
   	<div id="tb" style="padding:2px 5px;">
        开始日期: <input id="startDate" class="easyui-datebox" style="width:110px">
        结束日期: <input id="endDate" class="easyui-datebox" style="width:110px">
        单位:	 <input id="comboTree" name="groupid" value='${form.groupid}' style="width:170px;">
        <a href="javascript:void(0)" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
    <div id="ft" style="padding:2px 5px;">
        <span>操作员：王旭昆</span>
    </div>
</body>
</html>
