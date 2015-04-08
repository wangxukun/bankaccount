package xdl.wxk.financing.service;

import java.util.List;

import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
/**
 * 取得完整的帐户数据接口.
 * 包括期初余额及本期发生额
 * @author 王旭昆
 *
 */
public interface IGetFullData {
	/**
	 * 取得完整的帐户数据.
	 * @param init	此帐户的初始化数据
	 * @param data	本期发生额数据
	 * @return	完整的帐户数据
	 */
	public List<DataInfo> GetFullData(InitAccount init,List<DataInfo> data);
}
