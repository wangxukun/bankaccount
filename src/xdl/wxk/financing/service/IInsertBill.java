/**
 * 
 */
package xdl.wxk.financing.service;

import java.sql.SQLException;

import xdl.wxk.financing.vo.AccountDetail;

/**
 * 插入一笔账单业务服务接口.
 * <p>业务逻辑层</p>
 * @author 王旭昆
 */
public interface IInsertBill {
	/**
	 * 插入一条业务记录
	 * @param detail 一条业务记录对象实体
	 * @return 成功返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean insertBill(AccountDetail detail) throws SQLException;
}
