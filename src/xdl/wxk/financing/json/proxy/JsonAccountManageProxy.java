package xdl.wxk.financing.json.proxy;

import java.util.List;

import net.sf.json.JSONArray;
import xdl.wxk.financing.json.JsonAccountManage;
import xdl.wxk.financing.json.impl.JsonAccountManageImpl;
import xdl.wxk.financing.vo.Account;

public class JsonAccountManageProxy implements JsonAccountManage {
	private JsonAccountManage dao;
	public JsonAccountManageProxy() {
		this.dao = new JsonAccountManageImpl();
	}

	@Override
	public JSONArray getAccountsForEasyTree(List<Account> accounts) {
		return this.dao.getAccountsForEasyTree(accounts);
	}

	@Override
	public void recursionForGetAccountsEasyTree(JSONArray array,
			List<Account> accounts, Account account) {
		this.dao.recursionForGetAccountsEasyTree(array, accounts, account);
		
	}

	@Override
	public List<Account> getChildrenAccount(List<Account> accounts,
			Account account) {
		return this.dao.getChildrenAccount(accounts, account);
	}

}
