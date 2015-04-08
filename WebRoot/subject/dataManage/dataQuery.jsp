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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/easyui/themes/metro/datagrid.css">
<style type="text/css">
html{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	font-size: 12px;
	margin:0 auto;
	padding:0 auto;
}
table#dg{
	margin:0 auto;
	padding:0 auto;
	width:900px;
	height:400px;
	background: red;
}
</style>
<script type="text/javascript">
$(function(){
	//---------------------------处理日期---------------------------
	var date = new Date();	//取得当前日期
	var y = date.getFullYear();
	var year = y+"年";
	var theYear = year;	//用于保存查询开始日期
	var m = date.getMonth()+1;
	var d = date.getDate();
	var today = m+'/'+d+'/'+y;
	
		/* 设置当前年的第一天 */
	var firstday = new Date(y,1,1);
	var firstday_y = firstday.getFullYear();
	var firstday_m = firstday.getMonth();
	var firstday_d = firstday.getDate();
	var thatDay = firstday_y+'-'+firstday_m+'-'+firstday_d;
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//---------------------------查询结果表格-----------------------------
	$('#dg').datagrid({
		url:'${pageContext.request.contextPath }/servlet/JsonDataAccountDetail',
		fit: false,
		fitColumns: false,
		singleSelect: true,
		title:'<center>陆良县三岔河镇农业综合服务中心</center>',
		width:1050,
		height:798,
		toolbar:'#tb',
		footer:'#ft',
		columns:[[
			         {field:'year',title:year,halign:'center',colspan:2,width:60,align:'center'},
			         {field:'voucherNum',title:'编号',halign:'center',rowspan:2,width:50,align:'center'},
			         {field:'village',title:'单位名称',halign:'center',rowspan:2,width:140},
			         {field:'summary',title:'摘要',halign:'center',rowspan:2,width:300,
			        	styler:function(value,row,index){
			        		if(value=='本月合计' || value=='累计'){
			        			return 'text-align:center;color:#f00;font-weight:bold;';
			        		}
			        	}	 
			         },
			         {field:'debit',title:'借方',halign:'center',rowspan:2,width:130},
			         {field:'credit',title:'贷方',halign:'center',rowspan:2,width:130},
			         {field:'balanceCredit',title:'借\\贷',halign:'center',rowspan:2,width:50,align:'center'},
			         {field:'balance',title:'余额',halign:'center',rowspan:2,width:130}
		         ],
		         [
					 {field:'month',title:'月',halign:'center',width:30,align:'center'},
					 {field:'day',title:'日',halign:'center',width:30,align:'center'}
		         ]],
		         queryParams:{
		        	 accountid:'${info.accountid}',
		        	 startDate:"",
		        	 endDate:"",
		        	 groupid:""
		        }
	});
	$('#dg').datagrid({
		onLoadSuccess:function(data){
			$('#dg').datagrid('getPanel').panel('setTitle','<center>'+g_selectd_accountname+'</center>');
			/* var o = $('#dg').datagrid('getColumnOption','year');
			o.title = '2015年';	 */		
			if(g_selectd_groupid!="" && '${info.accountid}'!=g_selectd_groupid){
				$('#dg').datagrid('hideColumn','village');
			}else{
				$('#dg').datagrid('showColumn','village');
			}
			$("#dg").datagrid("setColumnTitle",{field:'year',text:theYear});//重设日期字段标题，默认为当前年
		},
		onLoadError:function(){
			$.messager.show({
				title:'错误提示',
				msg:'输入的查询条件不正确',
				timeout:5000,
				showType:'show',
				style:{
					right:'',
					top:document.body.scrollTop+document.documentElement.scrollTop,
					bottom:''
				}
			});
		},
		onBeforeLoad:function(){
			
		}
	});
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	//-----------所属单位组合框树--------------------------
	var g_selectd_groupid = "";
	var g_selectd_accountname = "三岔河镇农业综合服务中心";
	$('#comboTree').combotree({
		url:'${pageContext.request.contextPath }/servlet/JsonDataAccountTree',
		method:'post',
		animate:true,
		lines:true,
		queryParams:{accountTree:'${accountTree}'},
		required:false,
		onSelect:function(node){
			g_selectd_groupid = node.id;
			g_selectd_accountname = node.text;
		}
	}); 
	/* var t = $('#comboTree').combotree('tree');
	var n = t.tree('getRoot');
	t.tree('select',n); */
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	$('#searchBtn').bind('click',function(){
		
		var startdate = $('#startDate').datebox('getValue');
		theYear = (startdate.trim()==""?theYear:startdate) ;//保存到全局变量中
		theYear = theYear.slice(0,4)+'年';
		var enddate = $('#endDate').datebox('getValue');
		var groupid = g_selectd_groupid;
		$('#dg').datagrid('load',{
			accountid:'${info.accountid}',
	       	startDate:startdate,
	       	endDate:enddate,
	       	groupid:groupid
		});
	});
	
	//----------------------------开始日期&结束日期-------------------------------
	$('#startDate').datebox('setValue',thatDay);
	$('#endDate').datebox('setValue',today);
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	
	//扩展datagrid列标题设置方法
	$.extend($.fn.datagrid.methods, {  
		setColumnTitle: function(jq, option){  
			if(option.field){
				return jq.each(function(){  
					var $panel = $(this).datagrid("getPanel");
					var $field = $('td[field='+option.field+']',$panel);
					if($field.length){
						var $span = $("span",$field).eq(0);
						$span.html(option.text);
					}
				});
			}
			return jq;		
		}  
	}); 
});
</script>

</head>
<body>
	<div id="tb" style="padding:2px 5px;">
        	单位:	 <input id="comboTree" name="groupid" value='${form.groupid}' style="width:190px;">
        	开始日期: <input id="startDate" class="easyui-datebox" style="width:110px">
      		  结束日期: <input id="endDate" class="easyui-datebox" style="width:110px">
       
        <a href="javascript:void(0)" id="searchBtn" class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </div>
   	<table id="dg"></table>
   	
    <div id="ft" style="padding:2px 5px;">
        <span>记帐员：${info.operatorname}</span>
    </div>
</body>
</html>
