package xdl.wxk.financing;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.tools.DataUtils;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.web.formbean.DataSearchForm;
public class PatternDemo {
	public static void main(String[] args) throws SQLException {
		String startDate = "";
		String endDate = "";
		String accountid = "1";
		String groupid = "1";
		DataSearchForm form = new DataSearchForm(accountid,groupid,startDate,endDate);
		System.out.println("验证结果编码："+form.validate()+"\n反馈信息："+form.getError());
		
		List<DataInfo> list = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
		
		
		System.out.println(list);
	}
}
