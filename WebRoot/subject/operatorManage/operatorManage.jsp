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
//初始化当前行信息
var currentRowInfo = {
	"rowid":-1,
	"status":false,
	"e":null,
	"info":null
};
$(function(){
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
//	      footerrow:true,
	      height:"100%",
//	      shrinkToFit:false,
	      hoverrows:true,
	      rownumbers:true,
	      rownumWidth:25,
	      toolbar:[true,"both"],
//	      rowList:[10,20,30],
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
						//		checkOnSubmit:true,
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
					//		checkOnSubmit:true,
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
				 //返回当前行的数据
				 var a = jQuery("#grid").getRowData(currentRowInfo.rowid);
				 $(this).delGridRow(currentRowInfo.rowid,{
					 caption:"删除操作员",
					 msg:"确认删除所选择的操作员吗？",
					 delData:{operId:a.operatorid,operName:a.operatorname},
					 bSubmit:"删除",
					 bCancel:"取消"
				 });
			 }
		 }).jqGrid('navButtonAdd','#gridpager',{
			 caption:"权限设置", 
			 buttonicon:"ui-icon-gear", 
			 onClickButton:triggerPrivilegeBtn, 
			 position: "last", 
			 title:"", 
			 cursor: "pointer"
		 });
	$(window).resize(function(){
	  	var div = document.getElementById("jqgrid");
	  	var w = realStyleAttrValue(div,getStyleAttrValue(div,"width"));
		jQuery("#grid").setGridWidth(w-2,true);
	});
	function myAttr(){
		jQuery("#grid").css({fontSize: '12px'});
	}
	
});
function triggerPrivilegeBtn(){
	if(currentRowInfo.rowid == -1){
		$.messager.alert('错误','请选择要设置权限操作员所在的行。','error');
		return;
	}
	//返回当前行的数据
	currentRowInfo.info = jQuery("#grid").getRowData(currentRowInfo.rowid);
	$('#win').window({
		title:'权限设置--'+currentRowInfo.info.operatorname,
		width:500,
		height:400,
		iconCls:'icon-man',
		modal:true,
		closed:true
	});
	$('#win').window('open');
	$('#tt').tree({
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
		checkbox:true
	});
}
</script>
</head>

<body>
<!-- Test jqgrid -->
	<div id="jqgrid">
	    <table id="grid"></table>
	    <div id="gridpager"></div>
	    
	</div>
	
	<div id="win">
        <div id="tt">
        </div>
    </div>
	
</body>
</html>
