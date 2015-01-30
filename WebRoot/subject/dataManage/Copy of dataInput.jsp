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
	src="/financing/easyui/datagrid-detailview.js"></script>
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
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/datagrid.css">
<style type="text/css">
form {
	margin: 0;
	padding: 0;
}

.dv-table td {
	border: 0;
}

.dv-table input {
	border: 1px solid #ccc;
}

#input_data table tr td {
	padding: 0 auto;
	margin: 0 auto;
}
</style>
<script type="text/javascript">
$(function(){
	$('#dg').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div class="ddv"></div>';
        },
        onExpandRow: function(index,row){
        	if(row.voucherNum == ""){
        		return ;
        	}
            var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
            ddv.panel({
                border:false,
 //               href:'show_form.jsp?index='+index,
		 		content:'<form method="post"><table class="dv-table" style="width:800px;border:1px solid #ccc;padding:5px;margin-top:5px;"><tr><td>摘要</td><td><input name="summary" class="easyui-validatebox" required="true" style="width:250px;height:18px"></input></td><td>金额</td><td><input name="amount" class="easyui-validatebox" required="true" style="width:150px;height:18px"></input></td><td>方向</td><td><select id="cc" class="easyui-combobox" name="direction"style="width:50px;"><option value="aa">借</option><option>贷</option></select></td><td>日期</td><td><input id="dd" type="text" class="easyui-datebox"></td></tr></table><div style="padding:5px 0;text-align:right;padding-right:30px;width:760px"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveItem('+index+')">保存</a><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelItem('+index+')">取消</a></div></form>'
            });
             
            
        	$('#dg').datagrid('fixDetailRowHeight',index);
            $('#dg').datagrid('selectRow',index);
            //加载内容到表单
            var data = {
            		summary:row.summary,
            		//如果借方为空字符，金额输入框中填贷方，否则填借方
            		amount:row.debit==""?row.credit:row.debit
            };
        	$('#dg').datagrid('getRowDetail',index).find('form').form('load',data);
        	
            $('#dg').datagrid('fixDetailRowHeight',index);
            
          	//日期
        	$("#dd").datebox("setValue", "2012-01-01");
		}
    });
	
	
});
function saveItem(index){
    var row = $('#dg').datagrid('getRows')[index];
    var url = row.isNewRecord ? 'dataInput.jsp' : 'update_user.jsp?id='+row.id;
    $('#dg').datagrid('getRowDetail',index).find('form').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(data){
        	alert(data);
            data = eval('('+data+')');
            data.isNewRecord = false;
            $('#dg').datagrid('collapseRow',index);
            $('#dg').datagrid('updateRow',{
                index: index,
                row: data
            });
        }
    });
}
function cancelItem(index){
    var row = $('#dg').datagrid('getRows')[index];
    if (row.isNewRecord){
        $('#dg').datagrid('deleteRow',index);
    } else {
        $('#dg').datagrid('collapseRow',index);
    }
}
function destroyItem(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','Are you sure you want to remove this user?',function(r){
            if (r){
                var index = $('#dg').datagrid('getRowIndex',row);
                $.post('destroy_user.php',{id:row.id},function(){
                    $('#dg').datagrid('deleteRow',index);
                });
            }
        });
    }
}
function newItem(){
    $('#dg').datagrid('appendRow',{isNewRecord:true});
    var index = $('#dg').datagrid('getRows').length - 1;
    $('#dg').datagrid('expandRow', index);
    $('#dg').datagrid('selectRow', index);
}


</script>

</head>

<body>
	<table id="dg" title="业务处理-数据录入" style="width:100%;height:600px"
		url="/financing/servlet/JsonDataAccountDetail?accountid=${info.accountid }"
		toolbar="#toolbar" pagination="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th field="detailId" width="50" rowspan="2" fixed="true"
					halign="center" hidden="hidden">ID</th>
				<th field="year" width="50" colspan="2" fixed="true" align="center">2014年</th>
				<th field="voucherNum" width="50" rowspan="2" fixed="true"
					align="center">编号</th>
				<th field="summary" width="120" rowspan="2" halign="center">摘要</th>
				<th field="debit" width="120" rowspan="2" fixed="true"
					halign="center">借方</th>
				<th field="credit" width="120" rowspan="2" fixed="true"
					halign="center">贷方</th>
				<th field="direction" width="40" rowspan="2" fixed="true"
					halign="center" align="center">方向</th>
				<th field="balance" width="120" rowspan="2" fixed="true"
					halign="center">余额</th>
			</tr>
			<tr>
				<th field="month" width="25" fixed="true" align="center">月</th>
				<th field="day" width="25" fixed="true" align="center">日</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newItem()">录入</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyItem()">删除</a>
	</div>
</body>
</html>
