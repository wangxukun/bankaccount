<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
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
$(function(){
	//----------日期处理------------
	var date = new Date();
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	var today = m+'/'+d+'/'+y;
	var t_date = new Date('${initData.initdate}');
	var ty = t_date.getFullYear();
	var tm = t_date.getMonth()+1;
	var td = t_date.getDate();
	var t_day = ty+'-'+tm+'-'+td;	//'2015-2-2'
	$('#dateBox').datebox({
		required:true,
		currentText:'Today'
	}).datebox('setValue',t_day==""?today:t_day);
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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
	var s2 = $("#sp input[value='${initData.direction}']").next('span').text();
	$('#direction').combobox('setValue', '${initData.direction}').combobox('setText', s2).combobox('hidePanel');
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
});
function submitForm(){
	modifyInitDataForm.action='${pageContext.request.contextPath }/servlet/ModifyInitData';
	modifyInitDataForm.submit();
}
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
h4{
	height: 80px;
	line-height: 80px;
	
	font-weight: bolder;
	text-align: center;
}
</style>
</head>
  
<body>
<!-- 
	${initData.accountname}等等实际上是ModifyInitDataUI.java或ModifyInitData.java传递过来的数据，
	因为ModifyInitDataUI.java传来的initData对象没有errors属性，为了避免发生el表达式错误，
	ModifyInitData.java又传来了一个form对象，其实form对象与ModifyInitData.java传来initData对象内容相同。
 -->
<h4>修改${initData.accountname}初始化数据</h4>
<form id="modifyInitDataForm" name="modifyInitDataForm" method="post">
	<input type="hidden" name="accountid" value='${initData.accountid}' />
	<input type="hidden" name="accountname" value='${initData.accountname}' />
	<table>
		<tr>
			<td>日期：</td>
			<td><input id="dateBox" name="initdate"></input><span>${form.errors.initdate }</span></td>
		</tr>
		<tr>
			<td>摘要：</td>
			<td><input id="smy" name="summary" class="easyui-validatebox textbox" value='${initData.summary}' data-options="required:true,validType:'length[1,30]'"><span>${form.errors.summary }</span></td>
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
				<span>${form.errors.direction }</span>
			</td>
		</tr>
		<tr>
			<td>金额：</td>
			<td><input id="amount" name="amount" type="text" class="easyui-numberbox" value='${initData.amount}' data-options="required:true,min:0,precision:4,groupSeparator:','"><span>${form.errors.amount }</span></td>
		</tr>
		<tr class="trCenter">
			<td  colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="submitForm()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="clearForm()">返回</a></td>
		</tr>
	</table>
</form>
</body>
</html>
