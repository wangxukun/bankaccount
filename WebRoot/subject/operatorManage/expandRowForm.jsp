<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/jquery-ui.min.css">
<link rel="stylesheet" type="text/css"
	href="/financing/css/smoothness/theme.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="/financing/easyui/themes/metro/easyui.css">
<style type="text/css">
html,body {
	padding: 0;
	margin: 0;
}
</style>
<script type="text/javascript" src="/financing/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="/financing/js/jquery-ui.js"></script>
<script type="text/javascript" src="/financing/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/financing/easyui/datagrid-detailview.js"></script>
<script type="text/javascript"
	src="/financing/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/financing/js/util.js"></script>
<title>ExpandRowForm</title>
</head>

<body>
	<h2>Edit form in expanded row details</h2>
	<p>Click the expand button to expand a detail form.</p>


	<table id="dg" title="My Users" style="width:700px;height:250px"
		url="get_users.php" toolbar="#toolbar" pagination="true"
		fitColumns="true" singleSelect="true">
		<thead>
			<tr>
				<th field="firstname" width="50">First Name</th>
				<th field="lastname" width="50">Last Name</th>
				<th field="phone" width="50">Phone</th>
				<th field="email" width="50">Email</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="newItem()">New</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyItem()">Destroy</a>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#dg').datagrid(
					{
						view : detailview,
						detailFormatter : function(index, row) {
							return '<div class="ddv"></div>';
						},
						onExpandRow : function(index, row) {
							var ddv = $(this).datagrid('getRowDetail', index)
									.find('div.ddv');
							ddv.panel({
								border : false,
								cache : true,
								href : 'show_form.php?index=' + index,
								onLoad : function() {
									$('#dg').datagrid('fixDetailRowHeight',
											index);
									$('#dg').datagrid('selectRow', index);
									$('#dg').datagrid('getRowDetail', index)
											.find('form').form('load', row);
								}
							});
							$('#dg').datagrid('fixDetailRowHeight', index);
						}
					});
		});
		function saveItem(index) {
			var row = $('#dg').datagrid('getRows')[index];
			var url = row.isNewRecord ? 'save_user.php' : 'update_user.php?id='
					+ row.id;
			$('#dg').datagrid('getRowDetail', index).find('form').form(
					'submit', {
						url : url,
						onSubmit : function() {
							return $(this).form('validate');
						},
						success : function(data) {
							data = eval('(' + data + ')');
							data.isNewRecord = false;
							$('#dg').datagrid('collapseRow', index);
							$('#dg').datagrid('updateRow', {
								index : index,
								row : data
							});
						}
					});
		}
		function cancelItem(index) {
			var row = $('#dg').datagrid('getRows')[index];
			if (row.isNewRecord) {
				$('#dg').datagrid('deleteRow', index);
			} else {
				$('#dg').datagrid('collapseRow', index);
			}
		}
		function destroyItem() {
			var row = $('#dg').datagrid('getSelected');
			if (row) {
				$.messager.confirm('Confirm',
						'Are you sure you want to remove this user?', function(
								r) {
							if (r) {
								var index = $('#dg').datagrid('getRowIndex',
										row);
								$.post('destroy_user.php', {
									id : row.id
								}, function() {
									$('#dg').datagrid('deleteRow', index);
								});
							}
						});
			}
		}
		function newItem() {
			$('#dg').datagrid('appendRow', {
				isNewRecord : true
			});
			var index = $('#dg').datagrid('getRows').length - 1;
			$('#dg').datagrid('expandRow', index);
			$('#dg').datagrid('selectRow', index);
		}
	</script>
	

</body>
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
</style>
</html>
