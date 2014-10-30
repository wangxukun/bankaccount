package xdl.wxk.financing.dao;

import java.sql.SQLException;
import java.util.List;

import xdl.wxk.financing.vo.Account;

public interface AccountManageDAO {
	public boolean CreateAccount(Account account) throws SQLException;
	public boolean IsExistAccount(Account account) throws SQLException;
	public boolean RemoveAccountById(int accountid) throws SQLException;
	public Account getParent(Account account) throws SQLException;
	boolean isLeaf(Account account) throws SQLException;
	boolean isHasParent(Account account) throws SQLException;
	public List<Account> findChildren(Account account) throws SQLException;
	public List<Account> findAllAccounts() throws SQLException;
}
