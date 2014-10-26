package xdl.wxk.financing.vo;

public class Privilege {
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
		return "Privilege [operatorid=" + operatorid + ", accountid="
				+ accountid + ", level=" + level + "]";
	}
}
