package xdl.wxk.financing.web.formbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

/**
 * 数据多条件查询表单.
 * 验证查询的九种情况
 * 1、开始日期为空，结束日期不为空，不能查询
 * 2、开始日期不为空，结束日期为空，不能查询
 * 3、日期格式不对，不能查询
 * 4、开始日期后于结束日期不能查询
 * 5、开始日期与结束日期跨年度不能查询
 * 6、开始日期、结束日期、所属单位全为空或所属单位ID等于账户ID或所属单位ID为空：显示当前月份所对应年度整个账户明细
 * 7、开始日期、结束日期为空，所属单位ID不等于账户ID：显示当前月份特定账户明细
 * 8、开始日期、结束日期不为空，所属单位全为空或所属单位ID等于账户ID：显示指定月份整个账户明细
 * 9、完整查询
 * @author wangxukun
 *
 */
public class DataSearchForm {
	/**
	 * select accountdetailid,occurdate,number,summary,direction,amount,accountid,groupid,freeze 
	 * from accountdetail
     * where accountid=1 and groupid=1 and occurdate between '2015-03-01' and '2015-03-05' order by occurdate ASC;
	 */
	private String accountid;
	private String groupid;
	private String startDate;
	private String endDate;
	private String error;
	
	public DataSearchForm() {
	}
	
	public DataSearchForm(String accountid, String groupid, String startDate,
			String endDate) {
		this.accountid = accountid;
		this.groupid = groupid;
		this.startDate = startDate;
		this.endDate = endDate;
		this.error = "";
	}

	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "DataSearchForm [accountid=" + accountid + ", groupid="
				+ groupid + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", error=" + error + "]";
	}
	/**
	 * 验证查询的九种情况
	 * 1、开始日期为空，结束日期不为空，不能查询
	 * 2、开始日期不为空，结束日期为空，不能查询
	 * 3、日期格式不对，不能查询
	 * 4、开始日期后于结束日期不能查询
	 * 5、开始日期与结束日期跨年度不能查询
	 * 6、开始日期、结束日期、所属单位全为空或所属单位ID等于账户ID或所属单位ID为空：显示当前月份所对应年度整个账户明细
	 * 7、开始日期、结束日期为空，所属单位ID不等于账户ID并且所属单位ID不为空：显示当前月份特定账户明细
	 * 8、开始日期、结束日期不为空，所属单位全为空或所属单位ID等于账户ID：显示指定月份整个账户明细
	 * 9、完整查询
	 */
	public int validate(){
		int flag = 0;
		this.error = "未知错误";
		if(this.startDate==null||"".equals(this.startDate) && this.endDate!=null&&!"".equals(this.endDate)){
			this.error = "填写了结束日期，必须填写开始日期";
			flag = 1;
			return flag;
		}
		if(this.endDate==null||"".equals(this.endDate) && this.startDate!=null&&!"".equals(this.startDate)){
			this.error = "填写了开始日期，必须填写结束日期";
			flag = 2;
			return flag;
		}
		if(this.startDate!=null&&!"".equals(this.startDate)&&this.endDate!=null&&!"".equals(this.endDate)){
			//下面三种情况都是要两个日期都不能为空
			//////////////
			try{
				DateLocaleConverter dlc = new DateLocaleConverter();
				dlc.convert(this.startDate, "yyyy-MM-dd");
				dlc.convert(this.endDate, "yyyy-MM-dd");
			}catch(Exception e){
				flag = 3;
				this.error = "日期格式不合法";
				return flag;
			}
			///////////////////
			SimpleDateFormat formatStart = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatEnd = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date start= formatStart.parse(this.startDate);
				Date end = formatEnd.parse(this.endDate);
				int d = start.compareTo(end);
				if(d > 0){
					flag = 4;
					this.error = "开始日期不能在结束日期之后";
					return flag;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			///////////////////
			if(!this.startDate.substring(0, 4).equals(this.endDate.substring(0,4))){
				System.out.println(this.startDate.substring(0, 4)+"\n"+this.endDate.substring(0,4));
				flag = 5;
				this.error = "不能跨年度查询";
				return flag;
			}
		}
//		if((this.startDate==null||"".equals(this.startDate))&&(this.endDate==null||"".equals(this.endDate))&&((this.groupid==this.accountid)||this.groupid==null||"".equals(this.groupid))){
		if((this.groupid==this.accountid)||this.groupid==null||"".equals(this.groupid)){
			flag = 6;
			this.error = "没有错误，这种情况是查询当前月份所属年度整个账户";
			return flag;
		}
		if((this.startDate==null||"".equals(this.startDate))&&(this.endDate==null||"".equals(this.endDate))&&(this.groupid!=this.accountid)){
			flag = 7;
			this.error = "没有错误，这种情况是查询当前月份所属年度特定账户明细";
			return flag;
		}
		if((this.startDate!=null||!"".equals(this.startDate))&&(this.endDate!=null||!"".equals(this.endDate))&&((this.groupid==this.accountid)||this.groupid==null||"".equals(this.groupid))){
			flag = 8;
			this.error = "没有错误，这种情况是查询指定月份整个账户";
			return flag;
		}
		//最后一种情况，完全条件查询
		flag = 9;
		this.error = "没有错误，这种情况是查询指定账户指定日期间的明细";
		return flag;
	}
}
