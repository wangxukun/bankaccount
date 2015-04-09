package xdl.wxk.financing.service;

import java.sql.SQLException;

import xdl.wxk.financing.vo.AccountDetail;

public interface IUpdateDetail {
	public boolean resivesDetail(AccountDetail detailData) throws SQLException;
}
