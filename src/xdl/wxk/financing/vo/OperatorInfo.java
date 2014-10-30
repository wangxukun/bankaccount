package xdl.wxk.financing.vo;

import java.util.Date;

public class OperatorInfo {
	private int operatorid;
	private String operatorname;
	private String operatorpassword;
	private Date registerdate;
	private Date updatedate;
	private Date logintime;
	private String accountname;
	public OperatorInfo() {
		// TODO Auto-generated constructor stub
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
	public String getOperatorpassword() {
		return operatorpassword;
	}
	public void setOperatorpassword(String operatorpassword) {
		this.operatorpassword = operatorpassword;
	}
	public Date getRegisterdate() {
		return registerdate;
	}
	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	@Override
	public String toString() {
		return "OperatorInfo [operatorid=" + operatorid + ", operatorname="
				+ operatorname + ", operatorpassword=" + operatorpassword
				+ ", registerdate=" + registerdate + ", updatedate="
				+ updatedate + ", logintime=" + logintime + ", accountname="
				+ accountname + "]";
	}
}
