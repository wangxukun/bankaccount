package xdl.wxk.financing.abstraction;

import java.util.List;

import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.Operator;

public class AccountRelation {
	private Account account;
	private List<Operator> operators;
	
	public AccountRelation() {
		
	}

	public AccountRelation(Account account, List<Operator> operators) {
		this.account = account;
		this.operators = operators;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Operator> getOperators() {
		return operators;
	}

	public void setOperators(List<Operator> operators) {
		this.operators = operators;
	}

	@Override
	public String toString() {
		return "AccountRelation [account=" + account + ", operators="
				+ operators + "]";
	}
	
}
