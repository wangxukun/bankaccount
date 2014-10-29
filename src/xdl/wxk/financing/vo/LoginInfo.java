package xdl.wxk.financing.vo;

public class LoginInfo {
	private String operatorname;
	private String accountname;
	private String logintime;
	private boolean isManager;
	
	public LoginInfo() {
	}
	
	public LoginInfo(String operatorname, String accountname, String logintime,boolean isManager) {
		super();
		this.operatorname = operatorname;
		this.accountname = accountname;
		this.logintime = logintime;
		this.isManager = isManager;
	}
	public String getOperatorname() {
		return operatorname;
	}
	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	
	public boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	@Override
	public String toString() {
		return "LoginInfo [operatorname=" + operatorname + ", accountname="
				+ accountname + ", logintime=" + logintime + ", isManager="
				+ isManager + "]";
	}
	
}
