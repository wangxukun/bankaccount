package xdl.wxk.financing;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.service.IGetFullData;
import xdl.wxk.financing.service.IGetReviseData;
import xdl.wxk.financing.service.impl.GetFullDataImpl;
import xdl.wxk.financing.service.impl.GetReviseDataImpl;
import xdl.wxk.financing.tools.DateUtils;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;
public class PatternDemo {
	public static void main(String[] args) throws SQLException {
		/*String startDate = "";
		String endDate = "";
		String accountid = "1";
		String groupid = "1";
		DataSearchForm form = new DataSearchForm(accountid,groupid,startDate,endDate);
		System.out.println("验证结果编码："+form.validate()+"\n反馈信息："+form.getError());
		
		List<DataInfo> listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
		
		
		List<InitAccount> list = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount();
		List<InitAccount> list1 = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount(list, 1);
		InitAccount initAccount = DAOFactory.getBusinessProcessDAOInstance().getRootInitaccount(list1,new Date());
		IGetFullData getFullData = new GetFullDataImpl();
		List<DataInfo> full = getFullData.GetFullData(initAccount, listData);
		JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);
		System.out.println(full);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sd.parse("2014-5-1");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String b = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate(1, date);
		System.out.println(b);
		
		Date day = DateUtils.getYearFirstDayOfDate(new Date());
		System.out.println(day);*/
		
//		String beforeStartBalance = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate("1","21",DateUtils.getYearFirstDayOfDate(new Date()));
		
		/*boolean f = DAOFactory.getBusinessProcessDAOInstance().isInit(2, DateUtils.getYearFirstDayOfDate(new Date()));
		System.out.println(DateUtils.getYearFirstDayOfDate(new Date()));
		System.out.println(f);*/
		//[accountid=1, groupid=, startDate=2015-03-01, endDate=2015-03-30, error=0]
		String startDate = "2015-03-01";
		String endDate = "2015-03-30";
		String accountid = "1";
		String groupid = "";
		String error = "0";
		DataSearchForm form = new DataSearchForm(accountid,groupid,startDate,endDate,error);
		
		IGetReviseData revisedata = new GetReviseDataImpl();
		
		/*System.out.println("验证结果编码："+form.validate()+"\n反馈信息："+form.getError());
		System.out.println("============================================================================");
		List<DataInfo> list1 = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
		System.out.println("按发生日期"+list1);
		System.out.println("============================================================================");
		List<DataInfo> list2 = DAOFactory.getBusinessProcessDAOInstance().getDateDetailsByEnterdate(form);
		System.out.println("按录入日期"+list2);
		System.out.println("============================================================================");
		JSONArray jarray = JsonDAOFactory.getJsonAccountManageDAOInstance().getReviseDataForEasyGrid(list2);
		System.out.println("JSON数据"+jarray);*/
		System.out.println(revisedata.GetReviseData(form));
	}
}
