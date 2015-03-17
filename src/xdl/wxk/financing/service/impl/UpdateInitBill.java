package xdl.wxk.financing.service.impl;

import java.sql.SQLException;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.service.IUpdateInitBill;
import xdl.wxk.financing.vo.InitAccount;

public class UpdateInitBill implements IUpdateInitBill {
	
	public boolean updateInitBill(InitAccount initAccount) throws SQLException{
		boolean flag = false;
		flag = DAOFactory.getBusinessProcessDAOInstance().isInit(initAccount.getAccountid());
		if(flag){
			flag = DAOFactory.getBusinessProcessDAOInstance().updateInitAccount(initAccount);
		}
		return flag;
	}
}
