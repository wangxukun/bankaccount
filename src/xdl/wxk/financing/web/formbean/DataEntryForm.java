package xdl.wxk.financing.web.formbean;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;


/**
 * 数据录入表单
 * @author wangxukun
 *
 */
public class DataEntryForm {
	private String accountid;	//帐户ID
	private String direction;	//借贷方向
	private String amount;	//金额
	private String occurdate;	//发生日期
	private String summary;	//摘要
	private String groupid;	//分类账户ID
	
	private Map<String,String> errors = new HashMap<String,String>();	//保存校验失败信息
	
	public Map<String,String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String,String> errors) {
		this.errors = errors;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
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
