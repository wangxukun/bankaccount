package xdl.wxk.financing.json.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.JsonAccountManage;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.InitAccount;

public class JsonAccountManageImpl implements JsonAccountManage {
	private int initMonth ;//记录初始化后账户的初始月份
	private double debitTotal ;//初始化借方本月合计
	private double debitAccumulative ;//初始化借方累计
	private double creditTotal ;//初始化贷方本月合计
	private double creditAccumulative ;//初始化贷方累计
	private double balance;//初始化结余
	private int number ;//初始化每月的凭证编号
	
	public JsonAccountManageImpl() {
		this.initMonth = -1;//记录初始化后账户的初始月份
		this.debitTotal = 0.0 ;//初始化借方本月合计
		this.debitAccumulative = 0.0 ;//初始化借方累计
		this.creditTotal = 0.0;//初始化贷方本月合计
		this.creditAccumulative = 0.0 ;//初始化贷方累计
		this.balance = 0.0;//初始化结余
		this.number = 0;//初始化每月的凭证编号
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
	
	/*
	 * [
		{"month":"10","day":"15","voucherNum":"1","summary":"上年结转","debit":"5210.50","credit":"","balance":"5210.50"},
		{"month":"10","day":"15","voucherNum":"2","summary":"支出","debit":"","credit":"5000.00","balance":"210.50"}
	   ]
	 */
	@Override
	public JSONArray getAccountDetail(List<AccountDetail> accountDetail) {
		Calendar calendarTemp = Calendar.getInstance(Locale.CHINA);
		calendarTemp.setTime(accountDetail.get(0).getOccurdate());
		this.initMonth = calendarTemp.get(calendarTemp.MONTH);
		
		JSONArray jArray = new JSONArray();
		Iterator<AccountDetail> iter = accountDetail.iterator();
		while(iter.hasNext()){
			JSONObject jObject = new JSONObject();
			AccountDetail detail = iter.next();
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setTime(detail.getOccurdate());
			int month = calendar.get(Calendar.MONTH);
			if(this.initMonth != month){
				summarizing(jArray,month);
			}
			jObject.accumulate("detailId",detail.getAccountdetailid());
			jObject.accumulate("month", month+1);
			jObject.accumulate("day", calendar.get(Calendar.DATE));
			jObject.accumulate("voucherNum",++this.number);
			jObject.accumulate("summary",detail.getSummary());
			double amount;
			if(detail.getDirection()==0){
				amount = detail.getAmount();
				jObject.accumulate("debit",amount);
				jObject.accumulate("credit","");
				this.debitTotal += amount;
			}else{
				amount = detail.getAmount();
				jObject.accumulate("credit",amount);
				jObject.accumulate("debit","");
				
				this.creditTotal += amount;
			}
			this.balance = detail.getBalance();
			if(this.balance >= 0){
				jObject.accumulate("direction", "借");
			}else{
				jObject.accumulate("direction", "贷");
			}
			jObject.accumulate("balance",this.balance);
			jArray.add(jObject);
		}
		summarizing(jArray,this.initMonth);
		return jArray;
	}
	
	//汇总本月合计、累计
	private void summarizing(JSONArray jArray,int month){
		JSONObject jObject1 = new JSONObject();
		jObject1.accumulate("detailId",-1);
		jObject1.accumulate("month", "");
		jObject1.accumulate("day", "");
		jObject1.accumulate("voucherNum","");
		jObject1.accumulate("summary","本月合计");
		jObject1.accumulate("debit",this.debitTotal==0.0?"":this.debitTotal);
		jObject1.accumulate("credit",this.creditTotal==0.0?"":this.creditTotal);
		jObject1.accumulate("balance",this.balance);
		if(this.balance >= 0){
			jObject1.accumulate("direction", "借");
		}else{
			jObject1.accumulate("direction", "贷");
		}
		jArray.add(jObject1);
		
		JSONObject jObject2 = new JSONObject();
		jObject2.accumulate("detailId",-1);
		jObject2.accumulate("month", "");
		jObject2.accumulate("day", "");
		jObject2.accumulate("voucherNum","");
		jObject2.accumulate("summary","累计");
		
		this.debitAccumulative += this.debitTotal;
		this.creditAccumulative += this.creditTotal;
		
		jObject2.accumulate("debit",this.debitAccumulative==0.0?"":this.debitAccumulative);
		jObject2.accumulate("credit",this.creditAccumulative==0.0?"":this.creditAccumulative);
		jObject2.accumulate("balance",this.balance);
		if(this.balance >= 0){
			jObject2.accumulate("direction", "借");
		}else{
			jObject2.accumulate("direction", "贷");
		}
		jArray.add(jObject2);
		this.initMonth = month;
		this.creditTotal = 0.0;
		this.debitTotal = 0.0;
		this.number = 0;
	}
	@Override
	public JSONArray getJsonOfInitdata(List<InitAccount> init) {
		String rootSummary = "汇总各单位数据";
		double rootAmount = 0.0;
		String rootDirection = null;
		JSONArray jArray = new JSONArray();
		Iterator<InitAccount> iter = init.iterator();
		while(iter.hasNext()){
			JSONObject jObject = new JSONObject();
			InitAccount tempInit = iter.next();
			boolean isRoot = false;
			try {
				isRoot = DAOFactory.getAccountManageDAOInstance().isRootAccount(tempInit.getAccountid());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(!isRoot){
				int flag = tempInit.getDirection();
				if(flag == -1){
					jObject.accumulate("unit",tempInit.getAccountname());
					jObject.accumulate("initDate", "-");
					jObject.accumulate("summary", "-");
					jObject.accumulate("direction","-");
					jObject.accumulate("amount","-");
				}else{
					Calendar calendar = Calendar.getInstance();
					Date date = calendar.getTime();
					DateFormat d = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.CHINA);
					String initdate = d.format(date);
					calendar.setTime(tempInit.getInitdate());
					jObject.accumulate("unit",tempInit.getAccountname());
					jObject.accumulate("initDate", initdate);
					jObject.accumulate("summary", tempInit.getSummary());
					if(tempInit.getDirection()==0){
						jObject.accumulate("direction","借");
					}else{
						jObject.accumulate("direction","贷");
					}
					
					jObject.accumulate("amount",tempInit.getAmount());
				}
				jArray.add(jObject);
			}
		}
		return jArray;
	}
}
