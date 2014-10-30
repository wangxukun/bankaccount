package xdl.wxk.financing.json.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xdl.wxk.financing.json.JsonAccountManage;
import xdl.wxk.financing.vo.Account;

public class JsonAccountManageImpl implements JsonAccountManage {
	public JsonAccountManageImpl() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public JSONArray getAccountsForEasyTree(List<Account> accounts) {
		JSONArray array = new JSONArray();
		Iterator<Account> iter = accounts.iterator();
		while(iter.hasNext()){
			Account account = new Account();
			account = iter.next();
			if(account.getParentid() == 0){
				recursionForGetAccountsEasyTree(array,accounts,account);
			}
		}
		return array;
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
	public void recursionForGetAccountsEasyTree(JSONArray array,List<Account>accounts,Account account){
		List<Account> a= getChildrenAccount(accounts,account);
		JSONObject o = new JSONObject();
		if(a!=null && !a.isEmpty()){
			o.accumulate("id", account.getAccountid());
			o.accumulate("text", account.getAccountname());
			o.accumulate("state", "closed");
			Iterator<Account> iter = a.iterator();
			JSONArray arrayTemp = new JSONArray();
			while(iter.hasNext()){
				Account temp = new Account();
				
				temp = iter.next();
				recursionForGetAccountsEasyTree(arrayTemp,accounts,temp);
			}
			o.accumulate("children", arrayTemp);
			array.add(o);
			
		}else{
			o.accumulate("id", account.getAccountid());
			o.accumulate("text", account.getAccountname());
			array.add(o);
		}
	}

	@Override
	public List<Account> getChildrenAccount(List<Account> accounts,
			Account account) {
		Iterator<Account> iter = accounts.iterator();
		List<Account> list = new ArrayList<Account>();
		while(iter.hasNext()){
			Account a = iter.next();
			if(a.getParentid()==account.getAccountid()){
				list.add(a);
			}
		}
		return list;
	}
}
