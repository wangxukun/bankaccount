package xdl.wxk.financing.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

public class DataResivesForm {
	private String accountdetailid;
	private String accountid;
	private String number;
	private String direction;
	private String amount;
	private String occurdate;
	private String enterdate;
	private String summary;
	private String balance;
	private String groupid;
	private String freeze;
	private Map<String,String> errors = new HashMap<String,String>();	//保存校验失败信息
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public String getAccountdetailid() {
		return accountdetailid;
	}
	public void setAccountdetailid(String accountdetailid) {
		this.accountdetailid = accountdetailid;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOccurdate() {
		return occurdate;
	}
	public void setOccurdate(String occurdate) {
		this.occurdate = occurdate;
	}
	public String getEnterdate() {
		return enterdate;
	}
	public void setEnterdate(String enterdate) {
		this.enterdate = enterdate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	@Override
	public String toString() {
		return "DataResivesForm [accountdetailid=" + accountdetailid
				+ ", accountid=" + accountid + ", number=" + number
				+ ", direction=" + direction + ", amount=" + amount
				+ ", occurdate=" + occurdate + ", enterdate=" + enterdate
				+ ", summary=" + summary + ", balance=" + balance
				+ ", groupid=" + groupid + ", freeze=" + freeze + "]";
	}
	
	
	//对表单进行校验
		//日期不能为空，并且要是合法的日期
		//所属单位不能为空
		//摘要不能为空，并且不能多于30个汉字或数字
		//金额不能为空，并且必须是大于或等于0的数字
		public boolean validate(){
			boolean isOK = true;
			//日期不能为空，并且要是合法的日期
			if(this.occurdate==null || this.occurdate.trim().equals("")){
				isOK = false;
				this.errors.put("occurdate", "业务发生日期不能为空");
			}else{
				try{
					DateLocaleConverter dlc = new DateLocaleConverter();
					dlc.convert(this.occurdate, "yyyy-MM-dd");
				}catch(Exception e){
					isOK = false;
					System.out.println(this.occurdate);
					this.errors.put("occurdate", "日期格式不合法");
				}
			}
			//所属单位不能为空
			if(this.groupid == null || this.groupid.trim().equals("")){
				isOK = false;
				this.errors.put("groupid", "所属单位必须填写");
			}
			//摘要不能为空，并且不能多于30个汉字或数字
			if(this.summary == null ||this.summary.trim().equals("")){
				isOK = false;
				this.errors.put("summary", "摘要不能为空");
			}else{
				String pattern = "[\\w+||\\S+]{1,30}";
				if(!this.summary.trim().matches(pattern)){
					isOK = false;
					this.errors.put("summary", "摘要只能少于30个字符");
				}
			}
			//借贷方向不能为空
			if(this.direction == null || this.direction.trim().equals("")){
				isOK = false;
				this.errors.put("direction", "借贷方向必须填写");
			}
			//金额不能为空，并且必须是大于或等于0的数字
			if(this.amount == null || this.amount.trim().equals("")){
				isOK = false;
				this.errors.put("amount", "金额必须填写");
			}else{
				String pattern2 = "\\d+.?\\d{0,4}";
				if(!this.amount.trim().matches(pattern2)){
					isOK = false;
					this.errors.put("amount", "金额必须是大于或等于0的数字，小数最多保留四位");
				}
			}
			return isOK;
		}
}
