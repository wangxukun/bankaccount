<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Refresh"
	content="5;url=${pageContext.request.contextPath }${feedback.forward}" />
<style type="text/css">
body{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	margin: 0 auto;
	padding: 0 auto;
}
div{
	background: mintcream;
	margin: 0 auto;
	padding: 0 auto;
	margin-top: 200px;
	width: 500px;
	height: 180px;
	
	border-radius:10px;
}
h6,p{
	margin: 0;
	padding: 0;
}
div h6{
	background:lightskyblue;
	width: 500px;
	height: 30px;
	line-height: 30px;
	text-indent: 20px;
	color:white;
	float: left;
	
	border-top-left-radius:10px;
	border-top-right-radius:10px;
}
div p{
	width: 500px;
	height: 60px;
	line-height: 60px;
	text-align: center;
	float: left;
}
div h6+p{
	font-size: 24px;
	font-style: oblique;
	color:gray;
}
</style>
</head>
<body>
	<div>
		<h6>${feedback.title }</h6>
		<p>${feedback.message }</p>
		<p>五秒钟后自动跳转，如无跳转请点击<a href="${pageContext.request.contextPath }${feedback.forward}">这里</a>
	</div>	
</body>
</html>
