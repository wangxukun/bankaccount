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
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="/financing/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript"
	src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/icon.css">

<script type="text/javascript">
	$(function() {
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
			 precision:2,
			 groupSeparator:','
		});
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	});
	//-----------------提交表单---------------------------------
	function submitForm(){
		$.ajax({
			type:'POST',
			url:'/financing/servlet/InsertAccountDetail',
			data:{
				number:$('#number').numberbox('getValue'),
				occurdate:$('#dateBox').datebox('getValue'),
				summary:$('#smy').val(),
				direction:$('#direction').combo('getValue'),
				amount:$('#amount').numberbox('getValue'),
				accountid:'处理中...',
				groupid:$('#comboTree').combo('getValue')
			}
		});
	}
	//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
</script>
<style type="text/css">
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
	<div class="easyui-panel" title="发生额数据录入" style="width:600px;padding:10px 60px 20px 60px">
		<form id="fAccountData" method="post">
			<table border="0">
				<tr>
					<td>日期：</td>
					<td><input id="dateBox"></input></td>
				</tr>
				<tr>
					<td>所属单位：</td>
					<td><input id="comboTree" style="width:200px;"></td>
				</tr>
				<tr>
					<td>摘要：</td>
					<td><input id="smy" class="easyui-validatebox textbox" data-options="required:true,validType:'length[3,10]'"></td>
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
					<td><input id="amount"></td>
				</tr>
				<tr class="trCenter">
					<td  colspan="2"><a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="submitForm()">提交</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="clearForm()">清除</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
