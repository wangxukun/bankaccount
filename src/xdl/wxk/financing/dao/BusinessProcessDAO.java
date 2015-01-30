package xdl.wxk.financing.dao;

import java.sql.SQLException;

import xdl.wxk.financing.vo.AccountDetail;

public interface BusinessProcessDAO {
//	public List<AccountDetail> findAccountDetailsByAccountid(int accountid) throws SQLException;
	
	//新增或插入一条银行业务记录
	public boolean insertAccountDetail(AccountDetail accountDetail) throws SQLException;
}
