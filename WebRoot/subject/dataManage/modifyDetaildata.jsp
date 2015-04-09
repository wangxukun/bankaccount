<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>

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
<script type="text/javascript">
$(function() {
	//----------日期处理------------
	var date = new Date('${reviseData.occurdate}');
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var theDay = y+'-'+m+'-'+d;
	$('#dateBox').datebox({
		required:true,
		currentText:'Today'
	}).datebox('setValue',theDay);
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//-----------所属单位组合框树--------------------------
	$('#comboTree').combotree({
		url:'${pageContext.request.contextPath }/servlet/JsonDataAccountTree',
		method:'post',
		animate:true,
		lines:true,
		queryParams:{accountTree:'${accountTree}'},
		required:true
	}); 
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	//-------------记账方向-----------------------------------------
	$('#direction').combobox({
		required : true,
		editable : false,
		panelHeight: 100
	});
	$('#sp').appendTo($('#direction').combobox('panel'));
	$('#sp input').click(
			function() {
				var v = $(this).val();
				var s = $(this).next('span').text();
				$('#direction').combobox('setValue', v).combobox('setText', s)
						.combobox('hidePanel');
			});
	var s2 = $("#sp input[value='${reviseData.direction}']").next('span').text();
	$('#direction').combobox('setValue', '${reviseData.direction}').combobox('setText', s2).combobox('hidePanel');
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
});
//-----------------提交表单---------------------------------

/*

	int accountdetailid;	//帐户祥情ID
	int accountid;	//帐户ID
	int number;	//凭证编号
	int direction;	//借贷方向
	double amount;	//金额
	Date occurdate;	//发生日期
	String summary;	//摘要
	double balance;	//余额，数据库中没有这个字段
	int groupid;	//分类账户ID
	int freeze;	//是否已冻结(0表示未冻结，1表示冻结)

*/

function submitForm(){
	dataEntryForm.action='${pageContext.request.contextPath }/servlet/UpdateAccountDetail';
	dataEntryForm.submit();
}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
</script>
<style type="text/css">
html,body{
	font-family: "微软雅黑",Arial,Helvetica,sans-serif;
	padding: 0;
	margin: 0;
}
table{
	margin:0 auto;
	width:500px;
	height:400px;
}
.trCenter{
	text-align: center;
}
input#smy{
	width:300px;
	height:20px;
}
input#dctn{
	width:150px;
	height:20px;
}
</style>
</head>

<body>
<!--  	<div class="easyui-panel" title="发生额数据录入" style="width:600px;padding:10px 60px 20px 60px">		-->
		<form id="dataEntryForm" name="dataEntryForm" method="post">
			<input type="hidden" name="accountid" value='${info.accountid}' />
			<input type="hidden" name="accountdetailid" value='${reviseData.accountdetailid}' />
			<table>
				<tr>
					<td>日期：</td>
					<td><input id="dateBox" name="occurdate"></input><span>${form.errors.occurdate}</span></td>
				</tr>
				<tr>
					<td>所属单位：</td>
					<td><input id="comboTree" name="groupid" value='${reviseData.groupid}' style="width:200px;"><span>${form.errors.groupid}</span></td>
				</tr>
				<tr>
					<td>摘要：</td>
					<td><input id="smy" name="summary" class="easyui-validatebox textbox" value='${reviseData.summary}' data-options="required:true,validType:'length[1,30]'"><span>${form.errors.summary}</span></td>
				</tr>
				<tr>
					<td>方向：</td>
					<td>
						<select id="direction" name="direction" style="width:150px"></select>
						<div id="sp">
							<div style="color:#99BBE8;background:#fafafa;padding:5px;">请选择记账方向</div>
							<div style="padding:10px;height:50px;">
								<input type="radio" name="direc" value="0" id="debit"><span>借</span><br />
								<input type="radio" name="direc" value="1" id="lender"><span>贷</span><br />
							</div>
						</div>
						<span>${form.errors.direction}</span>
					</td>
				</tr>
				<tr>
					<td>金额：</td>
					<%-- <td><input id="amount" name="amount"  value='${form.amount}'><span>${form.errors.amount}</span></td> --%>
					<td><input id="amount" name="amount" type="text" class="easyui-numberbox" value='${reviseData.amount}' data-options="required:true,min:0,precision:4,groupSeparator:','"><span>${form.errors.amount}</span></td>
				</tr>
				<tr class="trCenter">
					<td  colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="submitForm()">提交</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="clearForm()">清除</a></td>
				</tr>
			</table>
		</form>
<!--  	</div>	-->
</body>
</html>
