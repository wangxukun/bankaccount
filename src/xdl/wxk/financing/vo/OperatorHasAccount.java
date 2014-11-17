package xdl.wxk.financing.vo;

public class OperatorHasAccount {
	private int operatorid;
	private int accountid;
	private int level;
	public int getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "OperatorHasAccount [operatorid=" + operatorid + ", accountid="
				+ accountid + ", level=" + level + "]";
	}
}
