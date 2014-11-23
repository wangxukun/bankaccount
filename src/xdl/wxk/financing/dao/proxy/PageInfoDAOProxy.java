package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;

import xdl.wxk.financing.dao.PageInfoDAO;
import xdl.wxk.financing.dao.impl.PageInfoDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.tools.PageInfo;

public class PageInfoDAOProxy implements PageInfoDAO {
	private JdbcUtils jdbc;
	private PageInfoDAOImpl dao;
	public PageInfoDAOProxy() {
		this.jdbc= new JdbcUtils();
		this.jdbc.getConnection();
		this.dao  = new PageInfoDAOImpl(this.jdbc);
	}

	@Override
	public PageInfo getOperatorPageInfo(int offset, int rowCount) throws SQLException {
		PageInfo info;
		try {
			info = this.dao.getOperatorPageInfo(offset, rowCount);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return info;
	}
}
