package xdl.wxk.financing.abstraction;

import java.util.List;

import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.Lasttimestatus;
import xdl.wxk.financing.vo.Operator;
import xdl.wxk.financing.vo.Privilege;

public class OperatorRelation {
	private Operator operator;
	private List<Account> accounts;
	private Lasttimestatus status;
	private Privilege privilege;
	public OperatorRelation() {
		
	}
	public OperatorRelation(Operator operator, List<Account> accounts,
			Lasttimestatus status) {
		super();
		this.operator = operator;
		this.accounts = accounts;
		this.status = status;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public Lasttimestatus getStatus() {
		return status;
	}
	public void setStatus(Lasttimestatus status) {
		this.status = status;
	}
	public Privilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	@Override
	public String toString() {
		return "OperatorRelation [operator=" + operator + ", accounts="
				+ accounts + ", status=" + status + ", privilege=" + privilege
				+ "]";
	}
}
