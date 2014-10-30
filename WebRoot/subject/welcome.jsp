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
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<script type="text/javascript">
	$(function() {
		
	});
</script>
<style type="text/css">
html,body{
	padding: 0;
	margin: 0;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
}
</style>
</head>

<body>
<div>
welcome!
</div>
</body>
</html>
