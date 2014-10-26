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
form{
    margin:0;
    padding:0;
}
.dv-table td{
    border:0;
}
.dv-table input{
    border:1px solid #ccc;
}
</style>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/financing/js/util.js"></script>
<title>权限设置</title>
<style type="text/css">
	div#left{
		background-color: red;
		width: 30%;
		height: 100%;
		float:left;
	}
	
	div#right{
		background-color: blue;
		width: 70%;
		height: 100%;
		float:left;
	}
</style>
<script type="text/javascript">
jQuery(document).ready(function(){ 
	
});
</script>
</head>
<body>
<div id="left">left</div>
<div id="right">right</div>
</body>
</html>
