<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<link rel="stylesheet" type="text/css" href="/financing/jpgrid/css/ui.jqgrid.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">
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
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/jpgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="/financing/jpgrid/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/financing/js/util.js"></script>
<script type="text/javascript">
//初始化当前行信息---------start-----------------------------------------------
var currentRowInfo = {
	"rowid":-1,
	"status":false,
	"e":null,
	"data":""
};
//初始化当前行信息---------end-------------------------------------------------




$(function(){
	//操作员管理表格---------------start-----------------------------------------------
	jQuery("#grid").jqGrid({
	      url:"servlet/OperatorManage?action_flag=operatorManage",
	      datatype: "json",
	      mtype: 'GET',
	      colNames:['操作员ID', '操作员姓名','密码','注册时间', '更新时间','上次登录时间','上次登录账套','确认密码'],
	      colModel:[
	                {name:'operatorid',cellattr:myAttr,index:'operatorid',align:'center', width:55, sortable:false, editable:false, editoptions:{readonly:true,size:10}},
	                {name:'operatorname',index:'operatorname',align:'center', width:100,editable:true,edittype:'text',editoptions:{},editrules:{required:true},formoptions:{}},
	                {name:'operatorpassword',index:'operatorpassword',align:'center',hidden:true, width:150,editable:true,edittype:'password',editrules:{edithidden:true,required:true}},
	                {name:'registerdate',index:'registerdate',align:'center', hidden:true,width:150,editable:false,editrules:{edithidden:true}},
	                {name:'updatedate',index:'updatedate',align:'center',hidden:true, width:150,editable:false,editrules:{edithidden:true}},
	                {name:'lastlogindate',index:'lastlogin',align:'center',hidden:true, width:150,editable:false,editrules:{edithidden:true}},
	                {name:'lastloginaccount',index:'lastlogin',align:'center',hidden:true, width:150,editable:false,editrules:{edithidden:true}},
	                {name:'operatorrepassword',index:'operatorrepassword',align:'center',hidden:true,viewable:false, width:150,editable:true,edittype:'password',editrules:{edithidden:true,required:true}}
	                
	           ],
	     jsonReader : {
	    	 root: "rows",
	         page: "page",
	         total: "total",
	         records: "records",
	         repeatitems: true,
	         cell: "cell",
	         id: "id",
	         userdata: "userdata"
	     },
	      hidegrid: false,
	      autowidth:true,
	      height:"100%",
	      hoverrows:true,
	      rownumbers:true,
	      rownumWidth:25,
	      toolbar:[true,"both"],
	      checkOnSubmit: true,
	      pager: "#gridpager",
	      emptyrecords: "Nothing to display",
	      pagerpos: "center",
	      pgbuttons: true,
	      pginput: true,
	      pgtext : "第{0}页  共{1}页",
	      recordtext: "第{0}-{1}条    共{2}条记录",
	      sortname: 'operatorid',
	      altRows: true,
	      viewrecords: true,
	      sortorder: "asc",
	      caption:"操作员管理",
	      editurl:"servlet/OperatorManage?action_flag=editing",
	      onSelectRow:function(rowid,status,e){
	    	  currentRowInfo.rowid = rowid;
	    	  currentRowInfo.status = status;
	    	  currentRowInfo.e = e;
	    	//返回当前行的数据
			currentRowInfo.data = jQuery("#grid").getRowData(rowid);
			//初始化帐户树
			ShowAccounts();
	      }
	 }).navGrid("#gridpager",{
			 refresh:false,
			 search:false,
			 add:true,
			 edit:true,
			 del:true,
			 view:true,
			 addtext:"新增操作员",
			 edittext:"修改操作员",
			 viewtext:"操作员信息",
			 deltext:"删除操作员",
			 closeOnEscape:true,
			 alertcap:"错啦！",
			 alerttext:"请选择一条操作员记录.",
			 addfunc:function(){
					$(this).editGridRow(
							"new",
							{
								top:50,
								left:50,
								closeAfterEdit:true,
								onclickSubmit:function(params,posdata){
									params.closeAfterEdit;
								},
								afterSubmit:function(response,postdata){
									if(response.statusCode == 401){
										return [flag,"操作员已存在",-1];
									}
									var flag = (postdata.operatorpassword == postdata.operatorrepassword);
									return [flag,"两次输入的密码不一至",-1];
								}
							});
				},
			 editfunc:function(){
				$(this).editGridRow(
						currentRowInfo.rowid,
						{
							top:50,
							left:50,
							closeAfterEdit:true,
							onclickSubmit:function(params,postdata){
								params.closeAfterEdit;
							},
							afterSubmit:function(response,postdata){
								var flag = (postdata.operatorpassword == postdata.operatorrepassword);
								return [flag,"两次输入的密码不一至",-1];
							}
						});
			 },
			 delfunc:function(){
				 $(this).delGridRow(currentRowInfo.rowid,{
					 caption:"删除操作员",
					 msg:"确认删除所选择的操作员吗？",
					 delData:{operId:currentRowInfo.data.operatorid,operName:currentRowInfo.data.operatorname},
					 bSubmit:"删除",
					 bCancel:"取消"
				 });
			 }
		 }).jqGrid(
			//分页栏上新建按键
			'navButtonAdd',
			'#gridpager',
			{
				caption:"权限设置", 
				buttonicon:"ui-icon-newwin", 
				onClickButton:privilege, 
				position: "last", 
				title:"", 
				cursor: "pointer"
			}
		 );
	//操作员管理表格---------------end-------------------------------------------------
	
	
	
	
	//表格随窗口大小而改变------------start--------------------------------------------
	$(window).resize(function(){
	  	var div = document.getElementById("jqgrid");
	  	var w = realStyleAttrValue(div,getStyleAttrValue(div,"width"));
		jQuery("#grid").setGridWidth(w-2,true);
	});
	//表格随窗口大小而改变------------end----------------------------------------------
	
	
	//设置表格中字体大小--------------start------------------------------------------
	function myAttr(){
		jQuery("#grid").css({fontSize: '12px'});
	}
	//设置表格中字体大小--------------end--------------------------------------------
});


//权限按键调用函数------start-----------------------------------------
function privilege(){
	if(currentRowInfo.rowid == -1){
		$.messager.alert("错误","请选择要设置权限的操作员所在行.","error");
		return;
	}else{
		
		//权限设置窗口
		$('#privilege').window({
			title:'权限设置--'+currentRowInfo.data.operatorname,
			width:500,
			height:400,
			modal:true,
			closed:true
		});
		//打开权限设置窗口
		$('#privilege').window('open');
	}
}
//权限按键调用函数------end-------------------------------------------




//显示所有帐户----start------------------------------------------------------------
//当执行ShowAccounts函数，显示所有帐户
function ShowAccounts(){
	$('#tt_account').tree({
		url : 'servlet/JsonDataAccountTree',
		checkbox:true,
		method : 'POST',
		animate : true,
		onLoadSuccess:function(){
			//绑定权限  
            $.ajax(  
            	{
            		url: 'servlet/ExistingPermissions?oid='+currentRowInfo.data.operatorid+'&oname='+currentRowInfo.data.operatorname,
	            	cache:false,  
	                dataType:'text',
	                contentType:'text/plain; charset=utf-8',
	                type: 'GET',
	                success:function(data){   
	                   var array = data.split(',');  
	                   for(var i=0;i<array.length;i++)  
	                   {  
	                       var node = $('#tt_account').tree('find',array[i]);  
	                      $('#tt_account').tree('check',node.target);  
	                   }  
	                }  
	           });
		},
		formatter:function(node){
	        var s = node.text;
	        if (node.children){
	            s += '&nbsp;<span style=\'color:blue\'>(' + node.children.length + ')</span>';
	        }
	        return s;
	    },
		queryParams : {
			accountTree : '${accountTree}'
		}
	});
}
//显示所有帐户----end--------------------------------------------------------------


//点击权限设置窗口中【设置】按键的处理函数---start-----------------------------------------------
function privilegeConfig(){
	//取得帐户树中所有被选中的帐户节点
	var nodes = $('#tt_account').tree('getChecked','checked');
	//保存帐户树中的所选节点对应的帐户id
	var accountids = [];
	for( var i in nodes){
		accountids[i]=nodes[i].id;
	}
	
	//保存所要设置权限的操作员id
	var operatorid = currentRowInfo.data.operatorid;
	$.ajax(
		'servlet/OperatorManage',
		{
			//发送前调用
			beforeSend:function(jqXHR,settings){
			},
			//发送完成后
			complete:function(jqXHR,textStatus){
				//全局刷新页面
//				window.location.reload();
			},
			//应答状态提示
			statusCode:{
				201: function(){
					//关闭权限设置窗口
					$('#privilege').window('close');
					$.messager.show({
						title:'提示',
						msg:'创建成功.',
						timeout:3000,
						showType:'slide'
					});
				},
				403: function(){
					//关闭权限设置窗口
					$('#privilege').window('close');
					$.messager.show({
						title:'提示',
						msg:'帐户已存在.',
						timeout:3000,
						showType:'slide'
					});
				},
				405: function(){
					//关闭权限设置窗口
					$('#privilege').window('close');
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
				'action_flag':'privilegeConfig',
				'accountids':accountids.toString(),
				'operatorid':operatorid
			}
		}
	);
	cancelConfig();
}
//点击权限设置窗口中【设置】按键的处理函数---end-------------------------------------------------


//关闭权限设置窗口---------------start--------------------------------------------------
function cancelConfig(){
	//关闭权限设置窗口
	$('#privilege').window('close');
}
//关闭权限设置窗口---------------end----------------------------------------------------
</script>
</head>

<body>
<!-- 操作员管理表格 -->
	<div id="jqgrid">
	    <table id="grid"></table>
	    <div id="gridpager"></div>
	</div>
<!-- 权限设置窗口 -->	
	<div id="privilege">
		<div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'north',split:true" style="height:50px">
            	<p style="text-align:center">在这里设置此操作员所能操作的帐户</p>
            </div>
            <div data-options="region:'center'" style="padding:10px;">
                <ul id="tt_account"></ul>
            </div>
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:privilegeConfig()" style="width:80px">设置</a>
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:cancelConfig()" style="width:80px">取消</a>
            </div>
        </div>
	</div>
</body>
</html>
