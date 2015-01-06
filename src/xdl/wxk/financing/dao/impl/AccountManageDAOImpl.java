package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.dao.AccountManageDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.AccountDetail;

public class AccountManageDAOImpl implements AccountManageDAO {
	private JdbcUtils jdbc;
	private double balance;	//帐户余额

	public AccountManageDAOImpl(JdbcUtils jdbc) {
		// TODO Auto-generated constructor stub
		this.jdbc = jdbc;
		this.balance = 0.00;
	}

	@Override
	public boolean CreateAccount(Account account) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		List<Object> params = new ArrayList<Object>();
		params.add(account.getAccountname());
		params.add(account.getParentid());
		String sql = "insert into account(accountname,parentid) value(?,?)";
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	@Override
	public Account getParent(Account account) throws SQLException {
		Account a = new Account();
		String sql = "select accountid,parentid,accountname from account where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getAccountid());
		Map<String, Object> map;
		map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if (!map.isEmpty()) {
			a.setAccountid(Integer.parseInt((map.get("accountid")).toString()));
			a.setParentid(Integer.parseInt((map.get("parentid")).toString()));
			a.setAccountname(map.get("accountname").toString());
		}
		return a;
	}

	@Override
	public boolean isLeaf(Account account) throws SQLException {
		boolean flag = false;
		String sql = "select accountid from account where parentid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getAccountid());
		Map<String, Object> map;
		map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if (map.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isHasParent(Account account) throws SQLException {
		boolean flag = false;
		String sql = "select parentid from account where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getParentid());
		Map<String, Object> map;
		map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if (map.isEmpty()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public List<Account> findChildren(Account account) throws SQLException {
		List<Account> accounts = new ArrayList<Account>();
		List<Map<String,Object>> list;
		String sql = "select accountid,parentid,accountname from account where parentid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getAccountid());
		list = this.jdbc.findMoreByPreparedStatement(sql, params);
		Iterator<Map<String,Object>> iter = list.iterator();
		while(iter.hasNext()){
			Map<String,Object> temp = iter.next();
			Account accountTemp = new Account();
			accountTemp.setAccountid(Integer.parseInt(temp.get("accountid").toString()));
			accountTemp.setParentid(Integer.parseInt(temp.get("parentid").toString()));
			accountTemp.setAccountname(temp.get("accountname").toString());
			accounts.add(accountTemp);
		}
		return accounts;
	}

	@Override
	public List<Account> findAllAccounts() throws SQLException {
		List<Account> accounts = new ArrayList<Account>();
		List<Map<String,Object>> list;
		String sql = "select accountid,parentid,accountname from account";
		list = this.jdbc.findMoreByPreparedStatement(sql, null);
		Iterator<Map<String,Object>> iter = list.iterator();
		while(iter.hasNext()){
			Map<String,Object> temp = iter.next();
			Account accountTemp = new Account();
			accountTemp.setAccountid(Integer.parseInt(temp.get("accountid").toString()));
			accountTemp.setParentid(Integer.parseInt(temp.get("parentid").toString()));
			accountTemp.setAccountname(temp.get("accountname").toString());
			accounts.add(accountTemp);
		}
		return accounts;
	}

	@Override
	public boolean RemoveAccountById(int accountid) throws SQLException {
		boolean flag = false;
		String sql = "delete from account where accountid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(accountid);
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	@Override
	public boolean IsExistAccount(Account account) throws SQLException {
		boolean flag = false;
		Map<String, Object> map;
		String sql = "select accountid from account where parentid=? and accountname=?";
		List<Object> params = new ArrayList<Object>();
		params.add(account.getParentid());
		params.add(account.getAccountname());
		map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			flag = true;
		}
		return flag;
	}

	@Override
	public Account findAccountById(int accountid) throws SQLException {
		Account account = new Account();
		String sql = "select accountid,accountname,parentid from account where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(accountid);
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			account.setAccountid(Integer.valueOf(map.get("accountid").toString()));
			account.setAccountname(map.get("accountname").toString());
			account.setParentid(Integer.valueOf(map.get("parentid").toString()));
		}
		return account;
	}

	@Override
	public List<AccountDetail> findAccountDetailsByAccountid(int accountid)
			throws SQLException {
		List<AccountDetail> list = new ArrayList<AccountDetail>();
		String sql = "select accountdetailid,occurdate,number,summary,direction,amount,accountid from accountdetail where accountid=? order by occurdate asc";
		List<Object> params = new ArrayList<Object>();
		params.add(accountid);
		List<Map<String,Object>> temp = this.jdbc.findMoreByPreparedStatement(sql, params);
		Iterator<Map<String,Object>> iter = temp.iterator();
		while(iter.hasNext()){
			Map<String,Object> m = iter.next();
			AccountDetail detail = new AccountDetail();
			detail.setAccountdetailid(Integer.valueOf(m.get("accountdetailid").toString()));
			detail.setAccountid(Integer.valueOf(m.get("accountid").toString()));
			detail.setAmount(Double.valueOf(m.get("amount").toString()));
			detail.setNumber(Integer.valueOf(m.get("number").toString()));
			detail.setDirection(Integer.parseInt(m.get("direction").toString()));
			detail.setSummary(m.get("summary").toString());
			detail.setOccurdate((Date)m.get("occurdate"));
			if(detail.getDirection()==0){
				this.balance += detail.getAmount();
			}else{
				this.balance -= detail.getAmount();
			}
			detail.setBalance(this.balance);
			list.add(detail);
		}
		return list;
	}
	@Override
	public boolean addAccountDetail(AccountDetail accountDetail) 
			throws SQLException{
		boolean flag = false;
		String sql = "insert into accountdetail(occurdate,number,summary,direction,amount,accountid) value(?,?,?,?,?,?)" ;
		List<Object> params = new ArrayList<Object>();
		params.add(accountDetail.getOccurdate());
		params.add(accountDetail.getNumber());
		params.add(accountDetail.getSummary());
		params.add(accountDetail.getDirection());
		params.add(accountDetail.getAmount());
		params.add(accountDetail.getAccountid());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
}
