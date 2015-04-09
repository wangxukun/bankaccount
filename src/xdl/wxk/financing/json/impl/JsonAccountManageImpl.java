package xdl.wxk.financing.json.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import xdl.wxk.financing.tools.Calculator;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;

public class JsonAccountManageImpl implements JsonAccountManage {
	private int initMonth ;//记录初始化后账户的初始月份
	private String debitTotal ;//初始化借方本月合计
	private String debitAccumulative ;//初始化借方累计
	private String creditTotal ;//初始化贷方本月合计
	private String creditAccumulative ;//初始化贷方累计
//	private String balance;//初始化结余
	private int number ;//初始化每月的凭证编号
	public JsonAccountManageImpl() {
		this.initMonth = -1;//记录初始化后账户的初始月份
		this.debitTotal = "0.00";//初始化借方本月合计
		this.debitAccumulative = "0.00" ;//初始化借方累计
		this.creditTotal = "0.00";//初始化贷方本月合计
		this.creditAccumulative = "0.00" ;//初始化贷方累计
//		this.balance = "0.0000";//初始化结余
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
	
	@Override
	public JSONArray getJsonOfInitdata(List<InitAccount> init,String contextPath) {
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
					jObject.accumulate("modify","-");
					jObject.accumulate("delete","-");
				}else{
					jObject.accumulate("unit",tempInit.getAccountname());
					DateFormat df = DateFormat.getDateInstance();
					String initdate = df.format(tempInit.getInitdate());
					jObject.accumulate("initDate", initdate);
					jObject.accumulate("summary", tempInit.getSummary());
					if(tempInit.getDirection()==0){
						jObject.accumulate("direction","借");
					}else{
						jObject.accumulate("direction","贷");
					}
					jObject.accumulate("amount",tempInit.getAmount());
					jObject.accumulate("modify","<a href='"+contextPath+"/servlet/ModifyInitDataUI?accountid="+tempInit.getAccountid()+
																					"&parentid="+tempInit.getParentid()+
																					"&accountname="+tempInit.getAccountname()+
																					"&initdate="+tempInit.getInitdate()+
																					"&direction="+tempInit.getDirection()+
																					"&amount="+tempInit.getAmount()+
																					"&summary="+tempInit.getSummary()+"'>修改</a>");
					jObject.accumulate("delete","<a href='"+tempInit.getAccountid()+"'>删除</a>");
				}
				jArray.add(jObject);
			}
		}
		return jArray;
	}
	/*
	 * [
		  	{month:10,day:5,voucherNum:1,village:'黄家圩村委会第十二组',summary:'收到补助金',debit:50000.00,credit:null,balanceCredit:'借',balance:50000.00},
			{month:10,day:5,voucherNum:2,village:'水阁村委会',summary:'支付工程款',debit:null,credit:10000.00,balanceCredit:'借',balance:40000.00},
			{month:10,day:5,voucherNum:3,village:'水阁村委会',summary:'收到补助金',debit:50000.00,credit:null,balanceCredit:'借',balance:90000.00},
			{month:10,day:6,voucherNum:4,village:'水阁村委会',summary:'支付工程款',debit:null,credit:25000.00,balanceCredit:'借',balance:65000.00}
		]
	 */
	@Override
	/*public JSONArray getFullDataForEasyGrid(List<DataInfo> data) {
		JSONArray jArray = new JSONArray();
		Iterator<DataInfo> iter = data.iterator();
		while(iter.hasNext()){
			JSONObject jObject = new JSONObject();
			DataInfo detail = iter.next();
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			Date date = detail.getOccurdate();
			if(date!=null){
				calendar.setTime(detail.getOccurdate());
				int month = calendar.get(Calendar.MONTH);
				if(this.initMonth != month && !"".equals(month)){
					summarizing(jArray,month);
				}
				jObject.accumulate("month",month+1);
				jObject.accumulate("day", calendar.get(Calendar.DATE));
			}else{
				jObject.accumulate("month","");
				jObject.accumulate("day", "");
			}
			jObject.accumulate("voucherNum",++this.number);
			jObject.accumulate("village", detail.getAccountname());
			jObject.accumulate("summary",detail.getSummary());
			String amount;
			if(detail.getDirection()==0){
				amount = detail.getAmount();
				jObject.accumulate("debit",amount);
				jObject.accumulate("credit","");
				this.debitTotal =  Calculator.add(this.debitTotal, amount).toString();
			}else{
				amount = detail.getAmount();
				jObject.accumulate("credit",amount);
				jObject.accumulate("debit","");
				this.creditTotal =  Calculator.add(this.creditTotal, amount).toString();
			}
			if(detail.getNumber() == 0){
				jObject.accumulate("balanceCredit", "借");
			}else{
				jObject.accumulate("balanceCredit", "贷");
			}
			jObject.accumulate("balance",detail.getBalance());
			jArray.add(jObject);
		}
		summarizing(jArray,this.initMonth);
		return jArray;
	}*/
	public JSONArray getFullDataForEasyGrid(List<DataInfo> data) {
		JSONArray jArray = new JSONArray();
		Iterator<DataInfo> iter = data.iterator();
		while(iter.hasNext()){
			JSONObject jObject = new JSONObject();
			DataInfo detail = iter.next();
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			Date date = detail.getOccurdate();
			
			if(date == null){
				jObject.accumulate("month","");
				jObject.accumulate("day", "");
				jObject.accumulate("voucherNum","");
				jObject.accumulate("village", "");
				jObject.accumulate("summary",detail.getSummary());
				jObject.accumulate("debit","");
				jObject.accumulate("credit","");
				if(detail.getNumber() == 0){
					jObject.accumulate("balanceCredit", "借");
				}else{
					jObject.accumulate("balanceCredit", "贷");
				}
				jObject.accumulate("balance",detail.getBalance());
				jArray.add(jObject);
			}else{
				calendar.setTime(detail.getOccurdate());
				int month = calendar.get(Calendar.MONTH);
				if(this.initMonth != month && !"".equals(month)){
					summarizing(jArray,month);
				}
				jObject.accumulate("month",month+1);
				jObject.accumulate("day", calendar.get(Calendar.DATE));
				jObject.accumulate("voucherNum",++this.number);
				jObject.accumulate("village", detail.getAccountname());
				jObject.accumulate("summary",detail.getSummary());
				String amount;
				if(detail.getDirection()==0){	//本期发生额方向
					amount = detail.getAmount();
					jObject.accumulate("debit",amount);
					jObject.accumulate("credit","");
					this.debitTotal =  Calculator.add(this.debitTotal, amount).toString();
				}else{
					amount = detail.getAmount();
					jObject.accumulate("credit",amount);
					jObject.accumulate("debit","");
					this.creditTotal =  Calculator.add(this.creditTotal, amount).toString();
				}
				if(detail.getNumber() == 0){	//余额方向
					jObject.accumulate("balanceCredit", "借");
				}else{
					jObject.accumulate("balanceCredit", "贷");
				}
				jObject.accumulate("balance",detail.getBalance());
				jArray.add(jObject);
			}
		}
		summarizing(jArray,this.initMonth);
		return jArray;
	}
	
	/*
	 * [
		  	{month:10,day:5,voucherNum:1,village:'黄家圩村委会第十二组',summary:'收到补助金',debit:50000.00,credit:null,balanceCredit:'借',balance:50000.00},
			{month:10,day:5,voucherNum:2,village:'水阁村委会',summary:'支付工程款',debit:null,credit:10000.00,balanceCredit:'借',balance:40000.00},
			{month:10,day:5,voucherNum:3,village:'水阁村委会',summary:'收到补助金',debit:50000.00,credit:null,balanceCredit:'借',balance:90000.00},
			{month:10,day:6,voucherNum:4,village:'水阁村委会',summary:'支付工程款',debit:null,credit:25000.00,balanceCredit:'借',balance:65000.00}
		]
	 */
	//汇总本月合计、累计
	private void summarizing(JSONArray jArray,int month){
		JSONObject jObject1 = new JSONObject();
		
		jObject1.accumulate("month", "");
		jObject1.accumulate("day", "");
		jObject1.accumulate("voucherNum","");
		jObject1.accumulate("village","");
		jObject1.accumulate("summary","本月合计");
		jObject1.accumulate("debit",this.debitTotal.equals("0.00")?"":this.debitTotal);
		jObject1.accumulate("credit",this.creditTotal.equals("0.00")?"":this.creditTotal);
		jObject1.accumulate("balanceCredit","");
		jObject1.accumulate("balance","");
		jArray.add(jObject1);
		
		JSONObject jObject2 = new JSONObject();
		jObject2.accumulate("month", "");
		jObject2.accumulate("day", "");
		jObject2.accumulate("voucherNum","");
		jObject2.accumulate("village","");
		jObject2.accumulate("summary","累计");
		this.debitAccumulative = Calculator.add(this.debitAccumulative, this.debitTotal).toString();
		this.creditAccumulative = Calculator.add(this.creditAccumulative, this.creditTotal).toString();
		
		jObject2.accumulate("debit",this.debitAccumulative.equals("0.00")?"":this.debitAccumulative);
		jObject2.accumulate("credit",this.creditAccumulative.equals("0.00")?"":this.creditAccumulative);
		jObject2.accumulate("balanceCredit","");
		jObject2.accumulate("balance","");
		jArray.add(jObject2);
		this.initMonth = month;
		this.creditTotal = "0.00";
		this.debitTotal = "0.00";
		this.number = 0;
	}
	@Override
	public JSONArray getReviseDataForEasyGrid(List<DataInfo> data,String contextPath) {
		/*
		 * village enterDate occurDate summary debit credit modify delete
		 */
		JSONArray jArray = new JSONArray();
		Iterator<DataInfo> iter = data.iterator();
		while(iter.hasNext()){
			JSONObject jObject = new JSONObject();
			DataInfo detail = iter.next();
			jObject.accumulate("village", detail.getAccountname());
			SimpleDateFormat dfenter = new SimpleDateFormat("yyyy-MM-dd");
			String enterday = dfenter.format(detail.getEnterdate());
			jObject.accumulate("enterDate", enterday);
			SimpleDateFormat dfoccur = new SimpleDateFormat("yyyy-MM-dd");
			String occurday = dfoccur.format(detail.getOccurdate());
			jObject.accumulate("occurDate", occurday);
			jObject.accumulate("summary", detail.getSummary());
			String amount;
			if(detail.getDirection()==0){	//本期发生额方向
				amount = detail.getAmount();
				jObject.accumulate("debit",amount);
				jObject.accumulate("credit","");
			}else{
				amount = detail.getAmount();
				jObject.accumulate("credit",amount);
				jObject.accumulate("debit","");
			}
			/*private int accountdetailid;	//帐户祥情ID
			private int accountid;	//帐户ID
			private int number;	//凭证编号
			private int direction;	//借贷方向
			private double amount;	//金额
			private Date occurdate;	//发生日期
			private Date updatetime; //录入日期
			private String summary;	//摘要
			private String balance;	//余额，数据库中没有这个字段
			private int groupid;	//分类账户ID
			private int freeze;	//是否已冻结(0表示未冻结，1表示冻结)
			*/
			jObject.accumulate("modify","<a href='"+contextPath+"/servlet/ModifyDetailUI?accountdetailid="+detail.getAccountdetailid()+
					"&occurdate="+detail.getOccurdate()+
					"&groupid="+detail.getGroupid()+
					"&summary="+detail.getSummary()+
					"&direction="+detail.getDirection()+
					"&amount="+detail.getAmount()+"'>修改</a>");
			jObject.accumulate("delete","<a href='"+detail.getAccountdetailid()+"'>删除</a>");
			jArray.add(jObject);
		}
		
		return jArray;
	}
}
