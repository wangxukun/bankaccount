package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;
import java.util.List;

import xdl.wxk.financing.dao.AccountManageDAO;
import xdl.wxk.financing.dao.impl.AccountManageDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.AccountDetail;

public class AccountManageDAOProxy implements AccountManageDAO {
	private JdbcUtils jdbc;
	AccountManageDAO dao;
	public AccountManageDAOProxy() {
		// TODO Auto-generated constructor stub
		this.jdbc = new JdbcUtils();
		this.dao = new AccountManageDAOImpl(this.jdbc);
	}

	@Override
	public boolean CreateAccount(Account account) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.CreateAccount(account);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public Account getParent(Account account) throws SQLException {
		Account a;
		try {
			this.jdbc.getConnection();
			a = this.dao.getParent(account);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return a;
	}

	@Override
	public boolean isLeaf(Account account) throws SQLException {
		
		return this.dao.isLeaf(account);
	}

	@Override
	public boolean isHasParent(Account account) throws SQLException {
		// TODO Auto-generated method stub
		return this.dao.isHasParent(account);
	}

	@Override
	public List<Account> findChildren(Account account) throws SQLException {
		List<Account> accounts;
		try {
			this.jdbc.getConnection();
			accounts = this.dao.findChildren(account);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return accounts;
	}

	@Override
	public List<Account> findAllAccounts() throws SQLException {
		List<Account> accounts;
		try {
			this.jdbc.getConnection();
			accounts = this.dao.findAllAccounts();
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return accounts;
	}

	@Override
	public boolean RemoveAccountById(int accountid) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.RemoveAccountById(accountid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public boolean IsExistAccount(Account account) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.IsExistAccount(account);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public Account findAccountById(int accountid) throws SQLException {
		Account account = null;
		try {
			this.jdbc.getConnection();
			account = this.dao.findAccountById(accountid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return account;
	}

	@Override
	public List<AccountDetail> findAccountDetailsByAccountid(int accountid)
			throws SQLException {
		List<AccountDetail> list = null;
		try {
			this.jdbc.getConnection();
			list = this.dao.findAccountDetailsByAccountid(accountid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}
	
	@Override
	public boolean addAccountDetail(AccountDetail accountDetail) throws SQLException {
		boolean flag = false;
		try{
			this.jdbc.getConnection();
			flag = this.dao.addAccountDetail(accountDetail);
		} catch(Exception e){
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
}
