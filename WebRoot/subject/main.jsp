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
<base href="<%=basePath%>">

<title>银行存款日记帐</title>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">


<style type="text/css">
html,body {
	background-color: white;
	padding: 0;
	margin: 0;
}
#north{
	background-color:white;
}
#north div#left {
	margin: 0;
	padding: 0;
	width: 60%;
	float: left;
}

#north div#left h1 {
	margin: 0;
	width: 100%;
	height: 100%;
	line-height: 90px;
	text-indent: 20px;
	font-size: 30px;
}

#north div#right {
	margin: 0;
	width: 40%;
	height: 100%;
	float: right;
}

#north div#right ul {
	margin: 0 10px 0 0;
	width: 250px;
	height: 100%;
	float: right;
}

#north div#right ul li {
	width: 240px;
	height: 20px;
	line-height: 20px;
	padding-top: 8px;
}


#aa ul li{
	margin-bottom: 20px;
}
#aa ul li a{
	text-decoration: none;
	color: #000000;
}
#aa ul li a:HOVER {
	color: orange;
}

iframe{
	width: 100%;
	height: 100%;
	border:0;
	margin-bottom: -3px;
}
</style>


<script type="text/javascript">
$(document).ready(function() {
	$('#tt_account').tree({
		url:'servlet/JsonDataAccountTree',
		method:'POST',
		animate:true,
		queryParams:{accountTree:'${accountTree}'}
	});
	
	$('#tt_operator').tree({
		url:'servlet/JsonDataOperatorTree',
		method:'POST',
		animate:true,
		queryParams:{operatorTree:'${operatorTree}'}
	});
});
</script>

</head>

<body class="easyui-layout">
<!--+++++++++++++++++++++++++头面板+++++++++++++++++++++++++-->
    <div id="north" data-options="region:'north',title:null,split:false" style="height:100px;">
    	<div id="left">
			<h1>银行存款日记帐</h1>
		</div>
		<div id="right">
			<c:if test="${!empty info}">
				<ul>
					<li>操作员：${info.operatorname }
					<li>帐户：${info.accountname }
					<li>年月：${info.logintime }
				</ul>
			</c:if>
		</div>
    </div>
<!---------------------------头面板--------------------------->   
    
    
    
    
<!--+++++++++++++++++++++++++脚面板+++++++++++++++++++++++++-->    
    <div data-options="region:'south',title:'系统信息',split:true" style="height:100px;">
    </div>
<!---------------------------脚面板--------------------------->  
    
    
    
    
    
<!--+++++++++++++++++++++++++右面板+++++++++++++++++++++++++-->    
    <div data-options="region:'east',title:'切换操作',split:true" style="width:150px;">
    	<!-- O右面板->可折叠面板  -->
	    <div id="bb" class="easyui-accordion" style="width:100%;height:auto;">
	    	<div title="帐户切换" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
		    	<div id="accordionRight-1">
				    <ul id="tt_account">
					    
					</ul>
				</div>
		    </div>
		    
		    <div title="操作员切换" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
		        <div id="accordionRight-2">
					<ul id="tt_operator">
					    
					</ul>
				</div>
		    </div>
		  </div>
		  <!-- C右面板->可折叠面板 -->
    </div>
<!---------------------------右面板--------------------------->    
    
    
    
    
<!--+++++++++++++++++++++++++左面板+++++++++++++++++++++++++-->    
    <div data-options="region:'west',title:'控制面板',split:true" style="width:200px;">
    
    <!-- O左面板->可折叠面板  -->
	    <div id="aa" class="easyui-accordion" style="width:100%;height:auto;">
		    <div title="系统管理" data-options="iconCls:'icon-man'" style="overflow:auto;padding:10px;">
		        <div id="accordionLeft-1">
					<ul>
						<li id="exit"><a href="servlet/OperatorExit">退出系统</a>
					</ul>
				</div> 
		    </div>
		    
		    <c:if test="${info.isManager == true }">
			    <div title="账户管理" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
			        <div id="accordionLeft-2">
						<ul>
							<li id="addAccount"><a href="/financing/subject/accountManage/addDelAccount.jsp" target="main">增删帐户</a>
							<li id="initAccount"><a href="javascript:onInitAccount();">初始化帐户</a>
						</ul>
					</div>
			    </div>
		    </c:if>
		    
		    <div title="业务处理" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
		        <div id="accordion-3">
					<ul>
						<c:if test="${info.isManager == true }">
							<li>数据录入
							<li>数据修改
						</c:if>
						<li id="dataSearch"><a href="/financing/subject/dataManage/dataSearch.jsp" target="main">数据查询</a>
						<li>结余汇总
					</ul>
				</div>
		    </div>
		    
		    <c:if test="${info.isManager == true }">
			    <div title="用户管理" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
			        <div id="accordion-4">
						<ul>
							<li id="deleteOperator"><a href="/financing/subject/operatorManage/operatorManage.jsp" target="main">操作员管理</a>
							<li id="privilegeconfig"><a href="/financing/subject/operatorManage/privilegeConfig.jsp" target="main">权限设置</a>
							<li id="expandRowForm"><a href="/financing/subject/operatorManage/expandRowForm.jsp" target="main">ExpandRowForm</a>
						</ul>
					</div>
			    </div>
		    </c:if>
		</div>
	<!-- C左面板->可折叠面板 -->
    </div>
<!---------------------------左面板--------------------------->    





    
    
<!--+++++++++++++++++++++++++主体面板+++++++++++++++++++++++++-->    
    <div data-options="region:'center',title:'业务操作区'" style="padding:5px;background:#eee;">
    	<iframe id="main" name="main" src="subject/welcome.jsp" ></iframe>
    </div>
<!---------------------------主体面板--------------------------->
</body>
</html>
