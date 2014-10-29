package xdl.wxk.financing.json;

import java.util.List;

import xdl.wxk.financing.vo.Account;

import net.sf.json.JSONArray;

public interface JsonAccountManage {
	public JSONArray getAccountsForEasyTree(List<Account> accounts) ;
	//下面这两个函数为上一个函数服务
	public void recursionForGetAccountsEasyTree(JSONArray array,List<Account>accounts,Account account);
	public List<Account> getChildrenAccount(List<Account> accounts,Account account);
}
