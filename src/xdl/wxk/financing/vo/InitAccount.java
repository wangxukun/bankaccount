package xdl.wxk.financing.vo;

import java.util.Date;

public class InitAccount {
	int accountid;
	int parentid;
	String accountname;
	Date initdate;
	int direction;
	double amount;
	String summary;

	public InitAccount() {

	}

	public InitAccount(int accountid, Date initdate, int direction,
			double amount, String summary) {
		super();
		this.accountid = accountid;
		this.initdate = initdate;
		this.direction = direction;
		this.amount = amount;
		this.summary = summary;
	}

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

	public Date getInitdate() {
		return initdate;
	}

	public void setInitdate(Date initdate) {
		this.initdate = initdate;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public int hashCode() {
		return new Integer(this.accountid).hashCode()
				+ new Integer(this.parentid).hashCode()
				+ this.accountname.hashCode()
				+ this.initdate.hashCode()
				+ new Integer(this.direction).hashCode()
				+ new Double(this.amount).hashCode()
				+ this.summary.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof InitAccount)) {
			return false;
		}
		InitAccount o = (InitAccount) obj;
		if (o.accountid == this.accountid && o.parentid == this.parentid
				&& o.accountname.equals(this.accountname)
				&& o.initdate.equals(this.initdate)
				&& o.direction == this.direction && o.amount == this.amount
				&& o.summary.equals(this.summary)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "InitAccount [accountid=" + accountid + ",parentid=" + parentid
				+ ",accountname=" + accountname + ", initdate=" + initdate
				+ ", direction=" + direction + ", amount=" + amount
				+ ", summary=" + summary + "]";
	}
}
