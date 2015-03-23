<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>

<!DOCTYPE HTML>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/">
<title>银行存款日记帐</title>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/metro/easyui.css">


<style type="text/css">
html,body {
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	padding: 0 auto;
	margin: 0 auto;
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
#aa ul li span:HOVER {
	color: orange;
	cursor:pointer;
}
#aa ul li a:HOVER {
	color: orange;
}
iframe{
	width: 100%;
	height: 100%;
	border:0;
	margin-bottom: -5px;
}
#switchOperator p{
	height: 40px;
	line-height:40px;
	color: red;
	text-indent: 20px;
}
#switchOperator input{
	width: 180px;
	height: 20px;
	margin-left: 40px;
}
/*
	主体面板中内容
*/
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


<script type="text/javascript">
$(document).ready(function() {
	//显示登录操作员有权限的帐户树
	$('#tt_account').tree({
		url:'servlet/JsonDataAccountTree',
		method:'POST',
		animate:true,
		queryParams:{accountTree:'${accountTree}'}
	});
	//显示管理员及操作员树
	$('#tt_operator').tree({
		url:'servlet/JsonDataOperatorTree',
		method:'POST',
		animate:true,
		queryParams:{operatorTree:'${operatorTree}'}
	});
	//切换帐户
	$('#tt_account').tree({
		'onClick':function(node){
			//如果是点击了当前帐户，则不做任何处理
			if(node.id == '${info.accountid}')
				return false;
			$.post(
					'servlet/OperatorLogin',
					{
						action_flag:'switch_account',
						isManager:'${isManager }',
						operatorid:'${info.operatorid}',
						accountid:node.id
					},
					function(data,textStatus,jqXHR){
						parent.location.reload();
					}
			);
		}
	});
	//切换操作员
	$('#tt_operator').tree({
		'onClick':function(node){
			//如果是点击了【管理员】或【操作员】这两个父节点，则不做任何处理
			if(node.id == -1)
				return false;
			//如果是点击了当前操作员，则不做任何处理
			if(node.id == '${info.operatorid}')
				return false;
			if('true' == '${isManager}'){
				$.post(
						'servlet/OperatorLogin',
						{
							action_flag:'switch_operator',
							isManager:'${isManager }',
							operatorid:node.id
						},
						function(data,textStatus,jqXHR){
							parent.location.reload();
						}
				);
			}else{
				//切换操作员密码输入对话框（默认关闭）
				$('#switchOperator').dialog({
					title:'切换操作员--'+node.text,
					width: 300,
					height: 200,
					cache: false,
					closed: true,
					buttons:[{
						text:'确定',
			        	handler:function(){
			        		var pswd = $("#switchOperator input").val();
			        		$.post(
			        			'servlet/OperatorLogin',
			        			{
			        				action_flag:'switch_operator',
									isManager:'${isManager }',
			        				operatorname : node.text,
			        				operatorpassword:pswd
			        			},
			        			function(data,textStatus,jqXHR){
			        				parent.location.reload();
			        			}
			        		);
			        	}
							},{
						text:'取消',
						handler:function(){
							$('#switchOperator').dialog('close');	
						}
							}],
					modal: true
				});
				//打开切换操作员密码输入对话框
				$('#switchOperator').dialog('open');	
			}
		}
	});
	
	//初始化文档加载后#switchOperator为切换操作员密码输入对话框（默认关闭）
	$('#switchOperator').dialog({
		closed:true
	});
	
	//数据管理器对象
	var DataManager = new function(){
		//数据初始化
		this.dataInitialization = function(){
			if($("#myTab").tabs('getTab','数据初始化') == null){
				$("#myTab").tabs('add',{
					id:'two',
					title:'数据初始化',
					closable:true,
					content:'<iframe id=\"main\" name=\"main\" src=\"subject/dataManage/dataInit.jsp\" ></iframe>'
				});
			}else{
				$("#myTab").tabs('select','数据初始化');
			}
		};
		//数据录入
		this.dataInput = function(){
			if($("#myTab").tabs('getTab','数据录入') == null){
				$("#myTab").tabs('add',{
					id:'three',
					title:'数据录入',
					closable:true,
					content:'<iframe id=\"main\" name=\"main\" src=\"subject/dataManage/dataInput.jsp\" ></iframe>'
				});
			}else{
				$("#myTab").tabs('select','数据录入');
			}
		};
		
		//数据查询
		this.dataQuery = function(){
			if($("#myTab").tabs('getTab','数据查询') == null){
				$("#myTab").tabs('add',{
					id:'three',
					title:'数据查询',
					closable:true,
					content:'<iframe id=\"main\" name=\"main\" src=\"subject/dataManage/dataQuery.jsp\" ></iframe>'
				});
			}else{
				$("#myTab").tabs('select','数据查询');
			}
		};
	};	
	
	
	//easyui-tab
	$("#myTab").tabs({
		border:false,
		cache:true,
		tabPosition:'top',
		fit:true
	});
	//数据初始化
	$("#dataInit").click(function(){
		DataManager.dataInitialization();
	});
	$(".link-one a").click(function(event){
		event.preventDefault();	//禁止超链接的默认事件
		DataManager.dataInitialization();
	});
	
	//数据录入
	$("#dataInput").click(function(){
		DataManager.dataInput();
	});
	$(".link-two a").click(function(event){
		event.preventDefault();	//禁止超链接的默认事件
		DataManager.dataInput();
	});
	
	//数据查询
	$("#dataQuery").click(function(){
		DataManager.dataQuery();
	});
	$(".link-four a").click(function(event){
		event.preventDefault();	//禁止超链接的默认事件
		DataManager.dataQuery();
	});
});
</script>

</head>

<body class="easyui-layout" id="layout">
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
					<li>年月：${info.peroid }
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
    <div id="o_has_a" data-options="region:'east',title:'切换操作',split:true" style="width:150px;">
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
		  
		  <!--+++++切换操作员密码输入对话框 +++++-->
		  <div id="switchOperator">
		  	<p>请输入密码</p>
		  	<input type="password" id="password" name="password" />
		  </div>
		  <!-------切换操作员密码输入对话框----- -->
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
		    
		    <c:if test="${isManager }">
			    <div title="账户管理" data-options="iconCls:'icon-man'" style="padding:10px;">
			        <div id="accordionLeft-2">
						<ul>
							<li id="addAccount"><a href="${pageContext.request.contextPath }/subject/accountManage/addDelAccount.jsp" target="main">增删帐户</a>
						</ul>
					</div>
			    </div>
		    </c:if>
		    
		    <div title="业务处理" data-options="iconCls:'icon-man',selected:true" style="padding:10px;">
		        <div id="accordion-3">
					<ul>
						<c:if test="${isManager }">
							<li id="dataInit"><span>数据初始化</span></li>
							<li id="dataInput"><span>数据录入</span></li>
							<li id="dataModify">数据修改
						</c:if>
						<li id="dataQuery"><span>数据查询</span></li>
						<li>数据汇总
					</ul>
				</div>
		    </div>
		    
		    <c:if test="${isManager }">
			    <div title="用户管理" data-options="iconCls:'icon-man'" style="padding:10px;">
			        <div id="accordion-4">
						<ul>
							<li id="deleteOperator"><a href="${pageContext.request.contextPath }/subject/operatorManage/operatorManage.jsp" target="main">操作员管理</a>
						</ul>
					</div>
			    </div>
		    </c:if>
		</div>
	<!-- C左面板->可折叠面板 -->
    </div>
<!---------------------------左面板--------------------------->    
    
    
<!--+++++++++++++++++++++++++主体面板+++++++++++++++++++++++++-->    
    <div data-options="region:'center'" style="padding:5px;">
<!-- <iframe id="main" name="main" src="subject/dataInit.jsp" ></iframe> -->		
    	<div id="myTab" class="easyui-tabs">
    		<div class="box" title="首页">
				<div class="link link-one" id="one">
					<span class="icon"></span>
					<a href="javasrcipt:void(0)" class="button">
						<span class="line line-top"></span>
						<span class="line line-left"></span>
						<span class="line line-right"></span>
						<span class="line line-bottom"></span>
						数据初始化
					</a>
				</div>
				<div class="link link-two">
					<span class="icon"></span>
					<a href="javasrcipt:void(0)" class="button">
						<span class="line line-top"></span>
						<span class="line line-left"></span>
						<span class="line line-right"></span>
						<span class="line line-bottom"></span>
						数据录入
					</a>
				</div>
				<div class="link link-three">
					<span class="icon"></span>
					<a href="javasrcipt:void(0)" class="button">
						<span class="line line-top"></span>
						<span class="line line-left"></span>
						<span class="line line-right"></span>
						<span class="line line-bottom"></span>
						数据修改
					</a>
				</div>
				<div class="link link-four">
					<span class="icon"></span>
					<a href="javasrcipt:void(0)" class="button">
						<span class="line line-top"></span>
						<span class="line line-left"></span>
						<span class="line line-right"></span>
						<span class="line line-bottom"></span>
						数据查询
					</a>
				</div>
			</div>

    	</div>	
    </div>
<!---------------------------主体面板--------------------------->
</body>
</html>
