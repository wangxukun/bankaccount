<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>银行存款日记帐-登陆</title>
<base href="<%=basePath%>">
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="/financing/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/easyui.css">
<style type="text/css">
body{
	margin: 0 ;
	padding: 0;
}
header{
	width:100%;
	height:100%;
	margin:120px auto;
	margin:20px 0;
	text-align: center;
}
div#form1{
	width:400px;
	height:100%;
	margin:20px auto;
	background:red;
	text-align: center;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		//点击“登陆”提交表单
		$("#btnSubmit").click(function(){
			
			//验证表单
			var name = $.trim($("#operator").val());
			var password = $.trim($("#pswd").val());
			if(name == null || name ==""){
				$.messager.alert("提示","操作员名称不能为空");
				return false;
			}
			if(password == null  || password==""){
				$.messager.alert("提示","密码名称不能为空");
				return false;
			}
			
			$("#form_login").submit();
		});
		
		
	});
</script>

<!-- 如果已经登陆，则跳转到主页 -->
<c:if test="${!empty info}">
	<c:redirect url="servlet/OperatorLogin"></c:redirect>
</c:if>
</head>

<body>
	<header>
		<h3>银行存款日记帐</h3>
	</header>
<div id="form1">
	<div class="easyui-panel" title="系统登陆"
		style="width:400px;padding:30px 70px 20px 70px" id="login-panel">
		<form id="form_login" action="servlet/OperatorLogin" method="post">
			<div style="margin-bottom:10px">
				<input class="easyui-textbox"
					style="width:100%;height:40px;padding:12px"
					data-options="prompt:'操作员名称',iconCls:'icon-man',iconWidth:38"
					name="operator" id="operator">
			</div>
			<div style="margin-bottom:20px">
				<input class="easyui-textbox" type="password"
					style="width:100%;height:40px;padding:12px"
					data-options="prompt:'操作员密码',iconCls:'icon-lock',iconWidth:38"
					name="pswd" id="pswd">
			</div>
			<div style="margin-bottom:20px">
				<label><input type="checkbox" name="remember" id="remember"><span>记住 </span></label>
			</div>
			<div>
				<a href="javascript:void(0)" id="btnSubmit" class="easyui-linkbutton"
					data-options="iconCls:'icon-ok'"
					style="padding:5px 0px;width:100%;"> <span
					style="font-size:14px;">登陆</span>
				</a>
			</div>
		</form>
	</div>
</div>
	<c:if test="${!empty fail}">
		<script type="text/javascript">
			$(function(){
				//当用户名或密码错误时提示
				function alertPrivilege(){
					$.messager.alert("提示","操作员名称或密码不正确");
				}
				
				alertPrivilege();
			});
		</script>
	</c:if>
</body>
</html>
