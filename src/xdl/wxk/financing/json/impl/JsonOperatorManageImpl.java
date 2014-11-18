package xdl.wxk.financing.json.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.JsonOperatorManage;
import xdl.wxk.financing.tools.PageInfo;
import xdl.wxk.financing.vo.Operator;

public class JsonOperatorManageImpl implements JsonOperatorManage {
	OperatorManageDAO dao;

	public JsonOperatorManageImpl(OperatorManageDAO dao) {
		this.dao = dao;
	}

	@Override
	public JSONArray getJsonOperator(List<Map<String, Object>> list) {
		JSONArray jArray1 = new JSONArray();
		Iterator<Map<String, Object>> iter = list.iterator();
		while (iter.hasNext()) {
			JSONArray jArray2 = new JSONArray();
			Date date1 = new Date();
			Date date2 = new Date();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap = iter.next();
			jArray2.add((Integer) tempMap.get("operatorid"));
			jArray2.add((String) tempMap.get("operatorname"));
			date1 = (Date) tempMap.get("registerdate");
			jArray2.add(date1.toString());
			date2 = (Date) tempMap.get("updatedate");
			jArray2.add(date2.toString());
			jArray1.element(jArray2);
		}
		return jArray1;
	}

	/*
	 { 
		  "total": "xxx", 
		  "page": "yyy", 
		  "records": "zzz",
		  "rows" : [
		    {"id" :"1", "cell" :["cell11", "cell12", "cell13"]},
		    {"id" :"2", "cell":["cell21", "cell22", "cell23"]},
		      ...
		  ]
	 }
	 */
	
	@Override
	public JSONObject getLimitOperator(List<Map<String, Object>> list,PageInfo pageInfo) {
		JSONArray jArray = new JSONArray();
		Iterator<Map<String, Object>> iter = list.iterator();
		int p = 1;
		while (iter.hasNext()) {
			JSONObject jObject = new JSONObject();
			jObject.element("id", p);
			JSONArray jArray2 = new JSONArray();
			
			Date date1 = new Date();
			Date date2 = new Date();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap = iter.next();
			jArray2.add((Integer) tempMap.get("operatorid"));
			jArray2.add((String) tempMap.get("operatorname"));
			date1 = (Date) tempMap.get("registerdate");
			jArray2.add(date1.toString());
			date2 = (Date) tempMap.get("updatedate");
			jArray2.add(date2.toString());
			jObject.element("cell", JSONArray.toCollection(jArray2));
			jArray.element(jObject);
			p++;
		}
		JSONObject json = new JSONObject();
		json.element("total", pageInfo.getTotalPage());
		json.element("page", pageInfo.getCurrentPage());
		json.element("records", pageInfo.getTotalRecord());
		json.element("rows", JSONArray.toCollection(jArray));
		return json;
	}

	@Override
	public JSONObject getLimitOperatorInfo(List<Map<String, Object>> list,
			PageInfo pageInfo) {
		JSONArray jArray = new JSONArray();
		Iterator<Map<String, Object>> iter = list.iterator();
		int p = 1;
		while (iter.hasNext()) {
			JSONObject jObject = new JSONObject();
			jObject.element("id", p);
			JSONArray jArray2 = new JSONArray();
			
			Date date1 = new Date();
			Date date2 = new Date();
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap = iter.next();
			jArray2.add((Integer) tempMap.get("operatorid"));
			jArray2.add((String) tempMap.get("operatorname"));
			jArray2.add((String) tempMap.get("operatorpassword"));
			date1 = (Date) tempMap.get("registerdate");
			jArray2.add(date1.toString());
			date2 = (Date) tempMap.get("updatedate");
			jArray2.add(date2.toString());
			if(!("".equals(tempMap.get("logintime")))){
				jArray2.add(((Date) tempMap.get("logintime")).toString());
			}else{
				jArray2.add("未登录过");
			}
			jArray2.add(!"".equals((String) tempMap.get("accountname")) ? (String) tempMap.get("accountname") : "未登录过");
			jObject.element("cell", JSONArray.toCollection(jArray2));
			jArray.element(jObject);
			p++;
		}
		JSONObject json = new JSONObject();
		json.element("total", pageInfo.getTotalPage());
		json.element("page", pageInfo.getCurrentPage());
		json.element("records", pageInfo.getTotalRecord());
		json.element("rows", JSONArray.toCollection(jArray));
		return json;
	}
	/*
	 [{
	    "id": 1,
	    "text": "Node 1",
	    "state": "closed",
	    "children": [{
	        "id": 11,
	        "text": "Node 11"
	    },{
	        "id": 12,
	        "text": "Node 12"
	    }]
	},{
	    "id": 2,
	    "text": "Node 2",
	    "state": "closed"
	}]
	 */
	@Override
	public JSONArray getOperatorForEasyTree(List<Map<String,Object>> operators) throws SQLException {
		JSONArray array = new JSONArray(); //root
		JSONObject manager = new JSONObject();
		manager.accumulate("id", 1);
		manager.accumulate("text", "管理员");
		manager.accumulate("state", "closed");
		JSONObject operator = new JSONObject();
		operator.accumulate("id", 2);
		operator.accumulate("text", "操作员");
		operator.accumulate("state", "closed");
		Iterator<Map<String,Object>> iter = operators.iterator();
		boolean hasManager = false;
		boolean hasOperator = false;
		//保存children中的对象
		JSONArray arrayManager = new JSONArray();
		JSONArray arrayOperator = new JSONArray();
		while(iter.hasNext()){
			Map<String,Object> temp = iter.next();
			
			Operator ope = new Operator();
			ope.setOperatorid((Integer)temp.get("operatorid"));
			ope.setOperatorname(temp.get("operatorname").toString());
			
			JSONObject isManager = new JSONObject();
			JSONObject isOperator = new JSONObject();
			
			//参数100表示是管理员
			if(DAOFactory.getOperatorManageDAOInstance().isAdmin(ope)){
				isManager.accumulate("id", ope.getOperatorid());
				isManager.accumulate("text", ope.getOperatorname());
			}else{
				isOperator.accumulate("id", ope.getOperatorid());
				isOperator.accumulate("text", ope.getOperatorname());
			}
			if(!isManager.isEmpty()){
				hasManager = true;
				arrayManager.add(isManager);
				
			}
			if(!isOperator.isEmpty()){
				hasOperator = true;
				arrayOperator.add(isOperator);
			}
		}
		if(hasManager){
			manager.accumulate("children", arrayManager);
			array.add(manager);
		}
		if(hasOperator){
			operator.accumulate("children", arrayOperator);
			array.add(operator);
		}
		return array;
	}
}
