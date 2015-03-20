<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
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
div#left{
	width: 30%;
	height: 100%;
	float:left;
}
div#left #tip{
	border-bottom: 1px solid #999;
	margin-bottom: 10px;
}
div#right{
	width: 70%;
	height: 100%;
	float:left;
}
.btnInGrid{
	/*
		用于关联datagrid中链接
	*/
}
.btnInGrid a{
	display: inline-block;
	border: 1px solid #999;
	text-decoration: none;
	width: 40px;
	height: 20px;
	line-height: 20px;
	border-radius:5px;
	background-color: none;
	color: #000;
	font-style: italic;
	font-size-adjust: inherit;
}
.btnInGrid a:HOVER{
	color: #f00;
	font-style:normal;
	font-weight: bolder;
}
p a#dd{
	text-decoration: underline;
	color: #000000;
}
div#right a#dd:HOVER{
	color: red;
}
div#dlg table{
	margin:0 auto;
	width:300px;
	height:300px;
}
div#dlg input#dateBox{
	width: 180px;
	height: 20px;
}
div#dlg input#smy{
	width:180px;
	height:20px;
}
div#dlg input#dctn{
	width:150px;
	height:20px;
}
div#dlg input#amount{
	width: 150px;
	height: 20px;
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
			},
			onBeforeSelect:function(node){
				
			},
			onClick:function(node){
				console.log("点击了");
	//			$('#init').remove();
	//				var element = $("<div id='init'></div>");
					console.log("init div created");
					$('#init').datagrid({
						url:"servlet/SearchInitAccount?accountid="+node.id,
						fit:true,
						rownumbers:true,
						cache: false,
						title:'账户初始化信息-'+node.text,
						width:660,
						height:500,
						singleSelect:true,
						columns:[[
									 {field:'unit',title:'单位',halign:'center',rowspan:2,width:140},
									 {field:'initInfo',title:'初始化信息',halign:'center',colspan:4,width:350},
									 {field:'operate',title:'操作',halign:'center',colspan:2,width:100}
						         ],
						         [
									 {field:'initDate',title:'初始化日期',halign:'center',align:'center',align:'center',width:90},
									 {field:'summary',title:'摘要',halign:'center',width:170},
									 {field:'direction',title:'方向',halign:'center',align:'center',width:50},
									 {field:'amount',title:'金额',halign:'center',width:100},
									 {field:'modify',title:'修改',halign:'center',align:'center',width:70,styler:function(value,row,index){
										 if(value != '-'){
											 return {class:'btnInGrid'};
										 }
									 }},
									 {field:'delete',title:'删除',halign:'center',align:'center',width:70,styler:function(value,row,index){
										 if(value != '-'){
											 return {class:'btnInGrid'};
										 }
									 }}
						         ]],
						rowStyler: function(index,row){
							if (row.direction=="-"){
								return 'color:#f00;';
							}
						}
						
					});
			}
			
		});

		//----------日期处理------------
		var date = new Date();
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		var today = m+'/'+d+'/'+y;
		$('#dateBox').datebox({
			required:true,
			currentText:'Today'
		}).datebox('setValue',today);
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//-----------所属单位组合框树--------------------------
		$('#comboTree').combotree({
			url:'/financing/servlet/JsonDataAccountTree',
			method:'post',
			animate:true,
			lines:true,
			queryParams:{accountTree:'${accountTree}'},
			required:true
		}); 
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//-------------记账方向-----------------------------------------
		$('#direction').combo({
			required : true,
			editable : false,
			panelHeight: 100
		});
		$('#sp').appendTo($('#direction').combo('panel'));
		$('#sp input').click(
				function() {
					var v = $(this).val();
					var s = $(this).next('span').text();
					$('#direction').combo('setValue', v).combo('setText', s)
							.combo('hidePanel');
				});
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		//---------------金额输入框------------------------------
		$('#amount').numberbox({
			 required:true,
			 min:0,
			 precision:4,
			 groupSeparator:','
		});
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		
		
		
	});
	//增加帐户对话框
	function addAccountBox(){
		//清空元素中内容
//		$('.an').empty();
//		$('#acc').append("<span class='an' style='color:red'>"+accountname+"</span>");
		$('#dlg').dialog('open');
		$('#dlg').dialog('center');
	}
	//初始化帐户
	function initAccount(){
		$.ajax(
			'servlet/InitAccount',
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
							msg:'初始化成功.',
							timeout:3000,
							showType:'slide'
						});
					},
					417: function(){
						$('#dlg').dialog('close');
						$.messager.show({
							title:'提示',
							msg:'初始化失败.',
							timeout:3000,
							showType:'slide'
						});
					},
					405: function(){
						$('#dlg').dialog('close');
						$.messager.show({
							title:'提示',
							msg:'初始化数据已存在.',
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
					initdate:$('#dateBox').datebox('getValue'),
					summary:$('#smy').val(),
					direction:$('#direction').combo('getValue'),
					amount:$('#amount').numberbox('getValue'),
					accountid:accountid
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
	
	
	<div id="top" class="easyui-layout" data-options="fit:true">
		
		<div data-options="region:'north',split:true,border:false" style="height:2px;">
		</div>
		<div id="left" data-options="region:'west',title:'单位',split:true" style="width:220px;">
			<div id="tip">
				<h4>初始化数据录入操作提示：</h4>
				<p>1、选择下边需要操作的帐户节点.</p>
				<p>2、在选择的节点上点击鼠标右键.</p>
			</div>
			<ul id="tt_account">
			</ul>
		</div>
		<div id="right" data-options="region:'center',split:true,border:false">
			<div id='init'></div>
		</div>
	</div>
	
	<!-- 新增账户对话框 -->
	<div id="dlg" class="easyui-dialog" title="帐户初始化 " style="width:400px;height:400px;padding:10px" 
		data-options="iconCls:'icon-add',
						closed:'true',
						buttons:[
									{
										text:'提交',handler:initAccount
									},
									{
										text:'取消',handler:function(){
													$('#dlg').dialog('close');
												}
									}
								]">
		
		<table>
			<tr>
					<td>日期：</td>
					<td><input id="dateBox"></input></td>
				</tr>
				<tr>
					<td>摘要：</td>
					<td><input id="smy" class="easyui-validatebox textbox" data-options="required:true,validType:'length[3,20]'"></td>
				</tr>
				<tr>
					<td>方向：</td>
					<td>
						<select id="direction" style="width:150px"></select>
						<div id="sp">
							<div style="color:#99BBE8;background:#fafafa;padding:5px;">请选择记账方向</div>
							<div style="padding:10px;height:50px;">
								<input type="radio" name="direc" value="0" id="debit"><span>借</span><br />
								<input type="radio" name="direc" value="1" id="lender"><span>贷</span><br />
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>金额：</td>
					<td><input id="amount" /></td>
				</tr>
		</table>						
    </div>
    
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="addAccountBox()" data-options="iconCls:'icon-tip'">初始化设置</div>
		<!-- <div onclick="modifyAccount()" data-options="iconCls:'icon-tip'">初始化修改</div> -->
	</div>
</body>
</html>
