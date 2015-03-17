<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/datagrid.css">
<script type="text/javascript" src="print.js"></script>
<script type="text/javascript">
$(function(){
	$('#dg').datagrid({
	    url:'datagrid_data.json',
	    columns:[[
	        {field:'code',title:'Code',width:100},
	        {field:'name',title:'Name',width:100},
	        {field:'price',title:'Price',width:100,align:'right'}
	    ]]
	});
	CreateFormPage("datagrid", $("#dg"));
});
</script>
<head>
<title>Êý¾Ý´òÓ¡</title>

<style type="text/css">
body {
	background: white;
	margin: 0px;
	padding: 0px;
	font-size: 13px;
	text-align: left;
}

.pb {
	font-size: 13px;
	border-collapse: collapse;
}

.pb th {
	font-weight: bold;
	text-align: center;
	border: 1px solid #333333;
	padding: 2px;
}

.pb td {
	border: 1px solid #333333;
	padding: 2px;
}
</style>
</head>
<body>
	<script type="text/javascript">
		document.write(window.dialogArguments);
		window.print();
	</script>
	<table id="dg"></table>
</body>
</html>
