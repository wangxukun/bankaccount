package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.dao.impl.BusinessProcessDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public class BusinessProcessDAOProxy implements BusinessProcessDAO {
	private BusinessProcessDAO dao;
	private JdbcUtils jdbc;
	public BusinessProcessDAOProxy() {
		this.jdbc = new JdbcUtils();
		this.dao = new BusinessProcessDAOImpl(this.jdbc);
	}

	public boolean insertAccountDetail(AccountDetail accountDetail)
			throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.insertAccountDetail(accountDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	public boolean insertInitAccount(InitAccount initAccount)
			throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.insertInitAccount(initAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	public boolean isInit(int accountId,Date date) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.isInit(accountId,date);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public List<InitAccount> getAllInitaccount() throws SQLException {
		List<InitAccount> list = null;
		try {
			this.jdbc.getConnection();
			list = this.dao.getAllInitaccount();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}

	@Override
	public List<InitAccount> getAllInitaccount(List<InitAccount> initaccounts,
			int accountid) {
		 return this.dao.getAllInitaccount(initaccounts,accountid);
	}

	@Override
	public InitAccount getRootInitaccount(List<InitAccount> initAccounts,Date date) {
		return this.dao.getRootInitaccount(initAccounts,date);
	}
	@Override
	public boolean updateInitAccount(InitAccount initAccount)
			throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.updateInitAccount(initAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public List<DataInfo> getAccountDetails(DataSearchForm formDate)
			throws SQLException {
		List<DataInfo> list = null;
		try {
			this.jdbc.getConnection();
			list = this.dao.getAccountDetails(formDate);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}

	@Override
	public InitAccount getInitAccount(int accountId) throws SQLException {
		InitAccount initInfo = null;
		try {
			this.jdbc.getConnection();
			initInfo = this.dao.getInitAccount(accountId);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return initInfo;
	}

	@Override
	public String getBalanceBeforeStartDate(String accountid,String groupid, Date date)
			throws SQLException {
		String balance="";
		try {
			this.jdbc.getConnection();
			balance = this.dao.getBalanceBeforeStartDate(accountid,groupid,date);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.jdbc.releaseConnection();
		}
		return balance;
	}

	@Override
	public InitAccount getCurrentInitaccount(InitAccount origin,
			String beforeStartBalance) {
		return this.dao.getCurrentInitaccount(origin, beforeStartBalance);
	}
}
