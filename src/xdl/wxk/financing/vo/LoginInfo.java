package xdl.wxk.financing.vo;


public class LoginInfo {
	private int operatorid;
	private String operatorname;
	private int accountid;
	private String accountname;
	private int level;
	private String peroid;
	public LoginInfo() {
		
	}
	public int getOperatorid() {
		return operatorid;
	}
	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPeroid() {
		return peroid;
	}
	public void setPeroid(String peroid) {
		this.peroid = peroid;
	}
	@Override
	public String toString() {
		return "LoginInfo [operatorid=" + operatorid + ", operatorname="
				+ operatorname + ", accountid=" + accountid + ", accountname="
				+ accountname + ", level=" + level + ", peroid=" + peroid + "]";
	}
	
}
