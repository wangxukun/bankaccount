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
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="/financing/easyui/themes/metro/datagrid.css">
<style type="text/css">
html{
	margin:0;
	padding:0;
}
table{
	margin:0;
}
</style>
<script type="text/javascript">
$(function(){
	$('#dg').datagrid({
//		url:'easyui.json',
		data:[
		  	{month:10,day:5,voucherType:'A',voucherNum:'001',checkType:'A',checkNum:'00001',summary:'收到补助金',debit:50000.00,credit:null,balance:50000.00},
			{month:10,day:5,voucherType:'A',voucherNum:'005',checkType:'B',checkNum:'00015',summary:'支付工程款',debit:null,credit:10000.00,balance:40000.00},
			{month:10,day:5,voucherType:'B',voucherNum:'0111',checkType:'A',checkNum:'00236',summary:'收到补助金',debit:50000.00,credit:null,balance:90000.00},
			{month:10,day:6,voucherType:'A',voucherNum:'021',checkType:'A',checkNum:'01235',summary:'支付工程款',debit:null,credit:25000.00,balance:65000.00}
		],
		fit: true,
		title:'银行存款日记帐',
		width:1050,
		height:700,
		columns:[[
			         {field:'year',title:'2014年',halign:'center',colspan:2,width:100},
			         {field:'voucher',title:'凭证',halign:'center',colspan:2,width:100},
			         {field:'check',title:'支票',halign:'center',colspan:2,width:100},
			         {field:'summary',title:'摘要',halign:'center',rowspan:2,width:300},
			         {field:'debit',title:'借方',halign:'center',rowspan:2,width:150},
			         {field:'credit',title:'贷方',halign:'center',rowspan:2,width:150},
			         {field:'balance',title:'余额',halign:'center',rowspan:2,width:150}
		         ],
		         [
					 {field:'month',title:'月',halign:'center',width:50},
					 {field:'day',title:'日',halign:'center',width:50},
					 {field:'voucherType',title:'种类',halign:'center',width:50},
					 {field:'voucherNum',title:'号数',halign:'center',width:50},
					 {field:'checkType',title:'类别',halign:'center',width:50},
					 {field:'checkNum',title:'号数',halign:'center',width:50},
		         ]]
		
	});
});
</script>

</head>

<body>
	<table id="dg"></table>
</body>
</html>
