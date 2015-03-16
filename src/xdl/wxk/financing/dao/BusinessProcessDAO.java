package xdl.wxk.financing.dao;

import java.sql.SQLException;
import java.util.List;

import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;
/**
 * 业务处理核心接口.
 * <p>  这个接口提供了插入一条业务记录、插入初始化账户数据、判断特定账户是否已初始化、取得所有账户的初始化数据、合并Root账户初始化数据等功能。</p>
 * @author 王旭昆
 * @version 1.0
 */
public interface BusinessProcessDAO {
	/**
	 * 插入一条发生额业务记录
	 * @param accountDetail AccountDetail实例对象，代表一条完整的业务记录
	 * @return 插入成功返回true，否则返回false 
	 * @throws SQLException
	 */
	public boolean insertAccountDetail(AccountDetail accountDetail) throws SQLException;
	
	/**
	 * 插入初始化帐户数据
	 * @param initAccount InitAccount实例对象，代表一条完整的账户初始化数据
	 * @return 插入成功返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean insertInitAccount(InitAccount initAccount) throws SQLException;
	
	/**
	 * 修改账户初始化数据
	 * @param initAccount InitAccount实例对象，代表一条完整的账户初始化数据
	 * @return 修改成功返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean updateInitAccount(InitAccount initAccount) throws SQLException;
	/**
	 * 判断一个账户是否已初始化
	 * @param accountId 指定的账户ID
	 * @return　账户已初始化返回true，否则返回false
	 * @throws SQLException
	 */
	public boolean isInit(int accountId) throws SQLException;
	
	//取得所有帐户的初始化情况
	/**
	 * 取得所有账户的初始化数据
	 * @see #getAllInitaccount(List, int)
	 * @return 所有账户初始数据列表
	 * @throws SQLException
	 */
	public List<InitAccount> getAllInitaccount() throws SQLException;
	
	//根据accountid取得帐户及子帐户的初始化情况
	/**
	 * 根据账户ID取得对应账户及子账户的初始化数据
	 * @see #getAllInitaccount()
	 * @param initaccounts 所有账户初始数据列表
	 * @param accountid 指定的账户ID
	 * @return　返回指定的账户ID对应的账户及子账户的初始化数据列表list，指定账户初始化数据存储在list[0]，子账户数据在后面
	 */
	public List<InitAccount> getAllInitaccount(List<InitAccount> initaccounts,int accountid);
	
	/**
	 * 根据各子账户合并为一条Root账户初始余额，root在这里指子账户和子账户所属的父账户的合并
	 * @param initAccounts 初始化数据列表，不一定是所有的账户，参数数据使用{@link #getAllInitaccount(List, int)}函数获得
	 * @return 合并的Root账户初始化数据
	 */
	public InitAccount getRootInitaccount(List<InitAccount> initAccounts);
	
	/**
	 * 根据提交的表单数据，查询账户详细数据
	 * @param formDate	表单数据对象，对象中的属性可设置零个多处或全部
	 * @return 返回账户详细数据列表
	 * @throws SQLException
	 */
	public List<DataInfo> getAccountDetails(DataSearchForm formDate) throws SQLException;
}
