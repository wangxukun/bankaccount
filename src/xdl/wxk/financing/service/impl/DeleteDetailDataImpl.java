package xdl.wxk.financing.service.impl;

import java.sql.SQLException;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.service.IDeleteDetailData;

public class DeleteDetailDataImpl implements IDeleteDetailData {

	@Override
	public boolean delete(String id) throws SQLException {
		return DAOFactory.getBusinessProcessDAOInstance().deleteAccountDetail(id);
	}
}
