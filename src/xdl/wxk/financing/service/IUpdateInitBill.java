package xdl.wxk.financing.service;

import java.sql.SQLException;

import xdl.wxk.financing.vo.InitAccount;

/**
 * 修改账户初始化数据服务接口.
 * 业务逻辑层
 * @author 王旭昆
 *
 */
public interface IUpdateInitBill {
	public boolean updateInitBill(InitAccount initAccount) throws SQLException;
}
