package xdl.wxk.financing.vo;

public class DataInfo extends AccountDetail {
	private String accountname;

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	@Override
	public String toString() {
		return "DataInfo [accountname=" + accountname + ", 基类="
				+ super.toString() + "]\n";
	}
	
}
