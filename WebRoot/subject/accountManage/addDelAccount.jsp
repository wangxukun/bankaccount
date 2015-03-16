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
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">
<style type="text/css">
html,body{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	padding: 0;
	margin: 0;
}
h6{
	text-align: center;
	color: #777;
	border-bottom: 1px solid;
}
div#left{
	width: 30%;
	height: 100%;
	float:left;
}
div#right{
	width: 70%;
	height: 100%;
	float:left;
}
div#right a#dd{
	text-decoration: underline;
	color: #000000;
}
div#right a#dd:HOVER{
	color: red;
}
div#dlg input{
	width: 200px;
	height: 20px;
	line-height: 20px;
}
</style>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/financing/js/util.js"></script>
<script type="text/javascript">
	//保存选择的帐户名和帐户ID
	var accountname,accountid;
	//保存初始化树函数
	$(function() {
		$('#tt_account').tree({
			url : 'servlet/JsonDataAccountTree',
			method : 'POST',
			animate : true,
			formatter:function(node){
                var s = node.text;
                if (node.children){
                    s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
                }
                return s;
            },
			queryParams : {
				accountTree : '${accountTree}'
			},
			onContextMenu:function(e,node){
				e.preventDefault();
				//选择节点
				$('#tt_account').tree('select',node.target);
				//取得父节点
				var pNode = $('#tt_account').tree('getParent',node.target);
				//帐户名称是pName+subName
				if(pNode != null){
					accountname = pNode.text+node.text;
				}else{
					accountname = node.text;
				}
				accountid = node.id;
				//显示右键菜单
				$('#mm').menu('show',{
					left:e.pageX,
					top:e.pageY
				});
			}
		});
		
		//操作提示
		$('#dd').tooltip({
		    position: 'right',
		    content: '<span style="color:#fff">1、选择左边需要操作的帐户节点.</span><br /><br /><span style="color:#fff">2、在选择的节点上点击鼠标右键.</span>',
		    onShow: function(){
		        $(this).tooltip('tip').css({
		            backgroundColor: '#666',
		            borderColor: '#666'
		        });
		    }
		});
	});
	//增加帐户对话框
	function addAccountBox(){
		//清空元素中内容
		$('.an').empty();
		$('#acc').append("<span class='an' style='color:red'>"+accountname+"</span>");
		$('#dlg').dialog('open');
		$('#dlg').dialog('center');
	}
	//增加帐户
	function addAccount(){
		var name = $('#accountname').val();
		$.ajax(
			'servlet/AccountManage',
			{
				//发送前调用
				beforeSend:function(jqXHR,settings){
				},
				//发送完成后
				complete:function(jqXHR,textStatus){
					//全局刷新页面
//					window.location.reload();
				},
				//应答状态提示
				statusCode:{
					201: function(){
						$('#dlg').dialog('close');
						$.messager.show({
							title:'提示',
							msg:'创建成功.',
							timeout:3000,
							showType:'slide'
						});
					},
					403: function(){
						$('#dlg').dialog('close');
						$.messager.show({
							title:'提示',
							msg:'帐户已存在.',
							timeout:3000,
							showType:'slide'
						});
					},
					405: function(){
						$('#dlg').dialog('close');
						$.messager.show({
							title:'提示',
							msg:'非法输入.',
							timeout:3000,
							showType:'slide'
						});
					}
				},
				dataType:'html',
				//不设置type，中文会出现乱码
				type:'POST',
				//发送的数据
				data:{
					'action_flag':'createAccount',
					'accountname':name,
					'parentid':accountid
				}
			}
		);
	}
	//删除帐户
	function delAccount(){
		//判断是不是叶子节点，不是就不能删除
		var node = $('#tt_account').tree('getSelected');
		var flag = $('#tt_account').tree('isLeaf',node.target);
		if(!flag){
			$.messager.alert("错误","此帐户有下级帐户，不能被删除！","error");
			return;
		}
		//确认是否要删除帐户
		$.messager.confirm('提示','请确认是否要删除<span style="color:red">'+accountname+'</span>帐户？',function(r){
		    if (r){
		    	$.ajax(
						'servlet/AccountManage',
						{
							//发送完成后
							complete:function(jqXHR,textStatus){
								var node = $('#tt_account').tree('getSelected');
								$('#tt_account').tree('remove',node.target);
							},
							//应答状态提示
							statusCode:{
								201: function(){
									$.messager.show({
										title:'提示',
										msg:'删除成功.',
										timeout:3000,
										showType:'slide'
									});
								}
							},
							dataType:'html',
							//不设置type，中文会出现乱码
							type:'POST',
							//发送的数据
							data:{
								'action_flag':'removeAccount',
								'accountid':accountid
							}
						}
					);
		    }
		});
	}
</script>
</head>

<body>
	<h6>增加或删除帐户</h6>
	<div id="left">
		<ul id="tt_account">

		</ul>
	</div>
	<div id="right">
		<a id="dd" href="javascript:void(0)">操作提示</a>
	</div>
	<div id="dlg" class="easyui-dialog" title="增加帐户" style="width:300px;height:200px;padding:10px" 
		data-options="iconCls:'icon-add',
						closed:'true',
						buttons:[
									{
										text:'增加',handler:addAccount
									},
									{
										text:'取消',handler:function(){
													$('#dlg').dialog('close');
												}
									}
								]">
		<p id="acc">你选择的帐户是</p>
		<p>请输入此帐户的下级帐户名称：</p>
		<input type="text" id="accountname" name="accountname" />
    </div>
    
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="addAccountBox()" data-options="iconCls:'icon-add'">增加下级帐户</div>
		<div onclick="delAccount()" data-options="iconCls:'icon-remove'">删除此帐户</div>
	</div>
</body>
</html>
