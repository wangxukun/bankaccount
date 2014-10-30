package xdl.wxk.financing.vo;

import java.util.Date;

public class Operator {
	private int operatorid;
	private String operatorname;
	private String operatorpassword;
	private Date registerdate;
	private Date updatedate;
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
	@Override
	public String toString() {
		return "Operator [operatorid=" + operatorid + ", operatorname="
				+ operatorname + ", operatorpassword=" + operatorpassword
				+ ", registerdate=" + registerdate + ", updatedate="
				+ updatedate + "]";
	}
}
