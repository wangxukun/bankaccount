package xdl.wxk.financing.vo;

public class Account {
	private int accountid;
	private int parentid;
	private String accountname;
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	@Override
	public String toString() {
		return "Account [accountid=" + accountid + ", parentid=" + parentid
				+ ", accountname=" + accountname + "]";
	}
}
