package xdl.wxk.financing.json;

import java.util.List;

import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.InitAccount;

import net.sf.json.JSONArray;

public interface JsonAccountManage {
	//获取帐户树json对象
	public JSONArray getAccountsForEasyTree(List<Account> accounts) ;
	//下面这两个函数为上一个函数服务
	public void recursionForGetAccountsEasyTree(JSONArray array,List<Account>accounts,Account account);
	public List<Account> getChildrenAccount(List<Account> accounts,Account account);
	
	//获取帐户详情json对象
	public JSONArray getAccountDetail(List<AccountDetail> accountDetail);
	
	//猎取账户期初初始化数据
	public JSONArray getJsonOfInitdata(List<InitAccount> init);
}
