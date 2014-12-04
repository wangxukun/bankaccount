<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<form method="post">
	<table class="dv-table" style="width:800px;border:1px solid #ccc;padding:5px;margin-top:5px;">
		<tr>
			<td>摘要</td>
			<td><input name="summary" class="easyui-validatebox" required="true" style="width:250px;height:18px"></input></td>
			<td>金额</td>
			<td><input name="amount" class="easyui-validatebox" required="true" style="width:150px;height:18px"></input></td>
			<td>方向</td>
			<td><select id="cc" class="easyui-combobox" name="direction"
					style="width:50px;">
						<option value="aa">借</option>
						<option>贷</option>
				</select></td>
			<td>日期</td>
			<td><input id="dd" type="text" class="easyui-datebox"
					required="required"></td>
		</tr>
	</table>
	<div style="padding:5px 0;text-align:right;padding-right:30px;width:760px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="saveItem()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="cancelItem()">取消</a>
	</div>
</form>
