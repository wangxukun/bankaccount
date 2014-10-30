package xdl.wxk.financing.vo;

import java.util.Date;

public class Lasttimestatus {
	private int operatorid;
	private int accountid;
	private Date logintime;
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
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
	@Override
	public String toString() {
		return "Lasttimestatus [operatorid=" + operatorid + ", accountid="
				+ accountid + ", logintime=" + logintime + "]";
	}
	
}
