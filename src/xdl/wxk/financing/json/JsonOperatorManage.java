package xdl.wxk.financing.json;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.tools.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface JsonOperatorManage {
	public JSONArray getJsonOperator(List<Map<String,Object>> list);
	public JSONObject getLimitOperator(List<Map<String,Object>> list,PageInfo pageInfo);
	public JSONObject getLimitOperatorInfo(List<Map<String,Object>> list,PageInfo pageInfo);
	
	//根据权限，获取操作员树JSON数组
	public JSONArray getOperatorForEasyTree(List<Map<String,Object>> operators)  throws SQLException;
}
