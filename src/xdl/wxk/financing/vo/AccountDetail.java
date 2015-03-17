package xdl.wxk.financing.vo;

import java.util.Date;


public class AccountDetail {
	private int accountdetailid;	//帐户祥情ID
	private int accountid;	//帐户ID
	private int number;	//凭证编号
	private int direction;	//借贷方向
	private double amount;	//金额
	private Date occurdate;	//发生日期
	private String summary;	//摘要
	private double balance;	//余额，数据库中没有这个字段
	private int groupid;	//分类账户ID
	private int freeze;	//是否已冻结(0表示未冻结，1表示冻结)
	
	public AccountDetail() {
		
	}
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
