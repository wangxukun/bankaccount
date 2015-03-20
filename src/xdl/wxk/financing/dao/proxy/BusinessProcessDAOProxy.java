package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;
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

	public boolean isInit(int accountId) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.isInit(accountId);
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
	public InitAccount getRootInitaccount(List<InitAccount> initAccounts) {
		return this.dao.getRootInitaccount(initAccounts);
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
}
