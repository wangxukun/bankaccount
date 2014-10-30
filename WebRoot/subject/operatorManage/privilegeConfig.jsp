<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">
<style type="text/css">
html,body{
	padding: 0;
	margin: 0;
}
</style>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/financing/js/util.js"></script>
<title>权限设置</title>
<style type="text/css">

</style>
<script type="text/javascript">
jQuery(document).ready(function(){ 
	$('#dg').datagrid({
	    url:'subject/operatorManage/datagrid_data.json',
	    columns:[[
	        {field:'operatorid',title:'操作员编号',halign:'center',width:100},
	        {field:'operatorname',title:'操作员姓名',halign:'center',width:100}
	    ]],
	    //增加工具栏
	    toolbar: '#tb',
	    //增加分页
	    pagination: true,
	    //显示行号
	    rownumbers: true,
	    //只能单选
	    singleSelect: false,
	    //按下【ctrl】+鼠标点击多选，singleSelect必须为false
	    ctrlSelect: true,
	    //显示行头
	    showHeader: true,
	    //显示行脚
	    showFooter: true,
	    //行风格
	    rowStyler: function(index,row){
	    	if(row.operatorname == 'Wang wu'){
	    		return 'background-color:#6293BB;color:#fff';
	    	}
	    }
	    
	});
});
</script>
</head>
<body>
<table id="dg"></table>
<div id="tb">
<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"></a>
<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true"></a>
</div>
</body>
</html>
