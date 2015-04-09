package xdl.wxk.financing.service.impl;

import java.sql.SQLException;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.service.IUpdateDetail;
import xdl.wxk.financing.vo.AccountDetail;

public class UpdateDetail implements IUpdateDetail {

	@Override
	public boolean resivesDetail(AccountDetail detailData) throws SQLException {
		// TODO Auto-generated method stub
		return DAOFactory.getBusinessProcessDAOInstance().updateAccountDetail(detailData);
	}

}
