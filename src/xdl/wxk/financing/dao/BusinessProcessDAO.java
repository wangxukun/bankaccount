package xdl.wxk.financing.dao;

import java.sql.SQLException;
import java.util.List;

import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.InitAccount;

public interface BusinessProcessDAO {
//	public List<AccountDetail> findAccountDetailsByAccountid(int accountid) throws SQLException;
	
	//新增或插入一条银行业务记录
	public boolean insertAccountDetail(AccountDetail accountDetail) throws SQLException;
	
	//插入初始化帐户数据
	public boolean insertInitAccount(InitAccount initAccount) throws SQLException;
	
	//判断特定帐户是否已初始化
	public boolean isInit(int accountId) throws SQLException;
	
	//取得所有帐户的初始化情况
	public List<InitAccount> getAllInitaccount() throws SQLException;
	
	//根据accountid取得帐户及子帐户的初始化情况
	public List<InitAccount> getAllInitaccount(List<InitAccount> initaccounts,int accountid);
}
