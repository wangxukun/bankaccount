package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.InitAccount;

public class BusinessProcessDAOImpl implements BusinessProcessDAO {
	private JdbcUtils jdbc;
	
	public BusinessProcessDAOImpl(JdbcUtils jdbc) {
		this.jdbc = jdbc;
	}

	public boolean insertAccountDetail(AccountDetail accountDetail)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into accountdetail(occurdate,summary,direction,amount,accountid,freeze) values(?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(accountDetail.getOccurdate());
		params.add(accountDetail.getSummary());
		params.add(accountDetail.getDirection());
		params.add(accountDetail.getAmount());
		params.add(accountDetail.getAccountid());
		params.add(accountDetail.getGroupid());
		flag = jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	public boolean insertInitAccount(InitAccount initAccount)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into initaccount(accountid,initdate,direction,amount,summary) value(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(initAccount.getAccountid());
		params.add(initAccount.getInitdate());
		params.add(initAccount.getDirection());
		params.add(initAccount.getAmount());
		params.add(initAccount.getSummary());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	public boolean isInit(int accountId) throws SQLException {
		boolean flag = false;
		String sql = "select accountid from initaccount where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(accountId);
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		flag = !map.isEmpty();
		return flag;
	}

	@Override
	public List<InitAccount> getAllInitaccount() throws SQLException {
		List<InitAccount> list = new ArrayList<InitAccount>();
		String sql = "select accountid,parentid,accountname,initdate,direction,amount,summary from v_initaccount";
		List<Map<String,Object>> listMap = this.jdbc.findMoreByPreparedStatement(sql, null);
		Iterator<Map<String,Object>> iter = listMap.iterator();
		while(iter.hasNext()){
			Map<String,Object> map = iter.next();
			InitAccount init = new InitAccount();
			if(!map.get("accountid").toString().equals("")){
				int accountid = Integer.valueOf(map.get("accountid").toString());
				init.setAccountid(accountid);
			}
			if(!map.get("parentid").toString().equals("")){
				int parentid = Integer.valueOf(map.get("parentid").toString());
				init.setParentid(parentid);
			}
			{
				String accountname = map.get("accountname").toString();
				init.setAccountname(accountname);
			}
			if(!map.get("initdate").toString().equals("")){
				Date initdate = (Date)map.get("initdate");
				init.setInitdate(initdate);
			}
			if(!map.get("direction").toString().equals("")){
				int direction = Integer.valueOf(map.get("direction").toString());
				init.setDirection(direction);
			}else{
				init.setDirection(-1);
			}
			if(!map.get("amount").toString().equals("")){
				double amount = Double.valueOf(map.get("amount").toString());
				init.setAmount(amount);
			}
			{
				String summary = map.get("summary").toString();
				init.setSummary(summary);
			}
			
			list.add(init);
		}
		return list;
	}

	@Override
	public List<InitAccount> getAllInitaccount(List<InitAccount> initaccounts,
			int accountid){
		List<InitAccount> result = new ArrayList<InitAccount>();
		return recursionGetInitaccount(result,initaccounts,accountid);
	}

	private List<InitAccount> recursionGetInitaccount(List<InitAccount> result,List<InitAccount> initaccounts,int accountid){
		Iterator<InitAccount> iter = initaccounts.iterator();
		while(iter.hasNext()){
			InitAccount temp = iter.next();
			if(temp.getAccountid()==accountid && !result.contains(temp)){
				result.add(temp);
			}
			if(temp.getParentid()==accountid && !result.contains(temp)){
				result.add(temp);
				recursionGetInitaccount(result,initaccounts,temp.getAccountid());
			}
		}
		return result;
	}
}
