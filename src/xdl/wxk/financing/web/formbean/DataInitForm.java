package xdl.wxk.financing.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
/**
 * 数据初始化表单
 * @author Administrator
 *
 */
public class DataInitForm {
	private String initdate;
	private String accountname;
	private String summary;
	private String direction;
	private String amount;
	private String accountid;
	private Map<String,String> errors = new HashMap<String,String>();	//保存校验失败信息
	public String getInitdate() {
		return initdate;
	}
	public void setInitdate(String initdate) {
		this.initdate = initdate;
	}
	
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "DataInitForm [initdate=" + initdate + ", accountname="
				+ accountname + ", summary=" + summary + ", direction="
				+ direction + ", amount=" + amount + ", accountid=" + accountid
				+ ", errors=" + errors + "]";
	}
	//对表单进行校验
	//日期不能为空，并且要是合法的日期
	//所属单位不能为空
	//摘要不能为空，并且不能多于30个汉字或数字
	//金额不能为空，并且必须是大于或等于0的数字
	public boolean validate(){
		boolean isOK = true;
		//日期不能为空，并且要是合法的日期
		if(this.initdate==null || this.initdate.trim().equals("")){
			isOK = false;
			this.errors.put("initdate", "初始化日期不能为空");
		}else{
			try{
				DateLocaleConverter dlc = new DateLocaleConverter();
				dlc.convert(this.initdate, "yyyy-MM-dd");
			}catch(Exception e){
				isOK = false;
				System.out.println(this.initdate);
				this.errors.put("initdate", "日期格式不合法");
			}
		}
		//所属单位不能为空
		if(this.accountid == null || this.accountid.trim().equals("")){
			isOK = false;
			this.errors.put("accountid", "所属单位必须填写");
		}
		//摘要不能为空，并且不能多于30个汉字或数字
		if(this.summary == null ||this.summary.trim().equals("")){
			isOK = false;
			this.errors.put("summary", "摘要不能为空");
		}else{
			String pattern = "[\\d+||\\W+]{1,30}";
			if(!this.summary.trim().matches(pattern)){
				isOK = false;
				this.errors.put("summary", "摘要只能是中文、数字并且少于30个字符");
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
