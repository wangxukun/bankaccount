package xdl.wxk.financing.vo;

import java.util.Date;

public class AccountDetail {
	int accountdetailid;	//帐户祥情ID
	int accountid;	//帐户ID
	int number;	//凭证编号
	int direction;	//借贷方向
	double amount;	//金额
	Date occurdate;	//发生日期
	String summary;	//摘要
	double balance;	//余额，数据库中没有这个字段
	int groupid;	//分类账户ID
	int freeze;	//是否已冻结(0表示未冻结，1表示冻结)
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int getFreeze() {
		return freeze;
	}
	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}
	public AccountDetail() {
		
	}
	public int getAccountdetailid() {
		return accountdetailid;
	}
	public void setAccountdetailid(int accountdetailid) {
		this.accountdetailid = accountdetailid;
	}
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getOccurdate() {
		return occurdate;
	}
	public void setOccurdate(Date occurdate) {
		this.occurdate = occurdate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "AccountDetail [accountdetailid=" + accountdetailid
				+ ", accountid=" + accountid + ", number=" + number
				+ ", direction=" + direction + ", amount=" + amount
				+ ", occurdate=" + occurdate + ", summary=" + summary
				+ ", balance=" + balance + ", groupid=" + groupid + ", freeze="
				+ freeze + "]";
	}
}
