package xdl.wxk.financing.service.impl;

import java.sql.SQLException;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.service.IInsertBill;
import xdl.wxk.financing.vo.AccountDetail;

public class InsertBillImpl implements IInsertBill {
	@Override
	public boolean insertBill(AccountDetail detail) throws SQLException {
		boolean flag = false;
		flag = DAOFactory.getBusinessProcessDAOInstance().isInit(detail.getGroupid(),detail.getOccurdate());
		if(flag){
			flag = DAOFactory.getBusinessProcessDAOInstance().insertAccountDetail(detail);
		}
		return flag;
	}
}
