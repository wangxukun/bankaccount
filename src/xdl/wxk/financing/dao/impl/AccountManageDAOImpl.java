package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.dao.AccountManageDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.Account;

public class AccountManageDAOImpl implements AccountManageDAO {
	private JdbcUtils jdbc;

	public AccountManageDAOImpl(JdbcUtils jdbc) {
		// TODO Auto-generated constructor stub
		this.jdbc = jdbc;
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
}
