package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.dao.impl.BusinessProcessDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.AccountDetail;

public class BusinessProcessDAOProxy implements BusinessProcessDAO {
	private BusinessProcessDAO dao;
	private JdbcUtils jdbc;
	public BusinessProcessDAOProxy() {
		this.jdbc = new JdbcUtils();
		this.dao = new BusinessProcessDAOImpl(this.jdbc);
	}

	@Override
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

}
