package xdl.wxk.financing.json;

import java.util.List;

import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;

import net.sf.json.JSONArray;
/**
 * 数据转换为Json对象接口
 * @author 王旭昆
 *
 */
public interface JsonAccountManage {
	/**
	 * 获取帐户树json对象.
	 * @param accounts 帐户对象列表
	 * @return 匹配JeasyUI的JSON对象
	 */
	public JSONArray getAccountsForEasyTree(List<Account> accounts) ;
	//下面这两个函数为上一个函数服务
	public void recursionForGetAccountsEasyTree(JSONArray array,List<Account>accounts,Account account);
	public List<Account> getChildrenAccount(List<Account> accounts,Account account);
	
	/**
	 * 获取帐户详情json对象.
	 * @param accountDetail 帐户详情对象列表
	 * @return 匹配JeasyUI的JSON对象
	 */
//	public JSONArray getAccountDetail(List<AccountDetail> accountDetail);
	
	/**
	 * 获取账户期初初始化数据组成的JSON对象.
	 * @param init	初始化数据列表
	 * @return	匹配JeasyUI的JSON对象
	 */
	public JSONArray getJsonOfInitdata(List<InitAccount> init);
	
	/**
	 * 获取期初余额及本期发生额组成的JSON对象。
	 * @param data 包含期初余额及本期发生额的DataInfo对象
	 * @return	匹配JeasyUI的JSON对象
	 */
	public JSONArray getFullDataForEasyGrid(List<DataInfo> data);
}
