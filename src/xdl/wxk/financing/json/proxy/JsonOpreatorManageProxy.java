package xdl.wxk.financing.json.proxy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.dao.proxy.OperatorManageDAOProxy;
import xdl.wxk.financing.json.JsonOperatorManage;
import xdl.wxk.financing.json.impl.JsonOperatorManageImpl;
import xdl.wxk.financing.tools.PageInfo;

public class JsonOpreatorManageProxy implements JsonOperatorManage {
	OperatorManageDAO dao;
	public JsonOpreatorManageProxy() {
		this.dao = new OperatorManageDAOProxy();
	}

	@Override
	public JSONArray getJsonOperator(List<Map<String, Object>> list) {
		JsonOperatorManage jOperator = new JsonOperatorManageImpl(this.dao);
		return jOperator.getJsonOperator(list);
	}

	@Override
	public JSONObject getLimitOperator(List<Map<String, Object>> list,PageInfo pageInfo) {
		JsonOperatorManage jOperator = new JsonOperatorManageImpl(this.dao);
		return jOperator.getLimitOperator(list,pageInfo);
	}

	@Override
	public JSONArray getOperatorForEasyTree(List<Map<String, Object>> operators)
			throws SQLException {
		JsonOperatorManage jOperatorLevel = new JsonOperatorManageImpl(this.dao);
		
		return jOperatorLevel.getOperatorForEasyTree(operators);
	}

}
