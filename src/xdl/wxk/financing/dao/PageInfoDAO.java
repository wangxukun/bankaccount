package xdl.wxk.financing.dao;

import java.sql.SQLException;

import xdl.wxk.financing.tools.PageInfo;

public interface PageInfoDAO {
	PageInfo getOperatorPageInfo(int offset, int rowCount) throws SQLException;
	PageInfo getOperatorInfoPageInfo(int offset, int rowCount) throws SQLException;
}
