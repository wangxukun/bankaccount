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
<style type="text/css">
html,body{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	padding: 0;
	margin: 0;
}
.box{
	width: 1000px;
	height: 280px;
	margin: 50px auto;
}

.box .link{
	width: 205px;
	height: 280px;
	background: none;
	margin: 0 20px;
	float: left;
}
.link .icon{
	display: inline-block;
	width: 100%;
	height: 190px;
	transition: 0.2s linear;
	-webkit-transition: 0.2s linear;
}
.link-one .icon{
	background:url("image/one.png") no-repeat center center;
}
.link-two .icon{
	background:url("image/two.png") no-repeat center center;
}
.link-three .icon{
	background:url("image/three.png") no-repeat center center;
}
.link-four .icon{
	background:url("image/four.png") no-repeat center center;
}
.link .icon:hover{
	transform: rotate(360deg) scale(1.2);
	-ms-transform: rotate(360deg) scale(1.2);
	-webkit-transform: rotate(360deg) scale(1.2);
	-o-transform: rotate(360deg) scale(1.2);
	-moz-transform: rotate(360deg) scale(1.2);
}
.button{
	display: block;
	width: 180px;
	height: 50px;
	text-decoration: none;
	line-height: 50px;
	color: #2DCB70;
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	font-weight: bolder;
	border: 2px solid rgba(0,0,0,0.5);
	padding-left: 20px;
	margin: 0 auto;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	background: url("image/allow.png") no-repeat 110px center;
	position: relative;
	transition: 0.4s ease;
	-webkit-transition: 0.4s ease;
}
.button:hover{
	border: 2px solid rgba(0,0,0,1);
	background-position: 120px center;
}
.button:hover .line{
	background: #000;
}
.button .line{
	display: block;
	position: absolute;
	background: none;
	transition: 0.4s ease;
	-webkit-transition: 0.4s ease;
}
/*
 top:
  1.高度不变
  2.宽度变（0-盒子的宽度）
  3.位置：左-右
*/
.button .line-top{
	height: 2px;
	width: 0px;
	left: -110%;
	top: -2px;
}
.button:hover .line-top{
	height: 2px;
	width: 180px;
	left: -2px;
}
.button .line-bottom{
	height: 2px;
	width: 0px;
	right: -110%;
	bottom: -2px;
}
.button:hover .line-bottom{
	width: 100%;
	right: -2px;
}
.button .line-left{
	height: 0px;
	width: 2px;
	left: -2px;
	bottom: -110%;
}
.button:hover .line-left{
	height: 50px;
	width: 2px;
	bottom: -2px;
}
.button .line-right{
	height: 0px;
	width: 2px;
	right: -2px;
	top: -110%;
}
.button:hover .line-right{
	height: 50px;
	width: 2px;
	top: -2px;
}
</style>
</head>

<body>
<div class="box">
	<div class="link link-one">
		<span class="icon"></span>
		<a href="#" class="button">
			<span class="line line-top"></span>
			<span class="line line-left"></span>
			<span class="line line-right"></span>
			<span class="line line-bottom"></span>
			数据初始化
		</a>
	</div>
	<div class="link link-two">
		<span class="icon"></span>
		<a href="#" class="button">
			<span class="line line-top"></span>
			<span class="line line-left"></span>
			<span class="line line-right"></span>
			<span class="line line-bottom"></span>
			数据录入
		</a>
	</div>
	<div class="link link-three">
		<span class="icon"></span>
		<a href="#" class="button">
			<span class="line line-top"></span>
			<span class="line line-left"></span>
			<span class="line line-right"></span>
			<span class="line line-bottom"></span>
			数据修改
		</a>
	</div>
	<div class="link link-four">
		<span class="icon"></span>
		<a href="#" class="button">
			<span class="line line-top"></span>
			<span class="line line-left"></span>
			<span class="line line-right"></span>
			<span class="line line-bottom"></span>
			数据查询
		</a>
	</div>
</div>
</body>
</html>
