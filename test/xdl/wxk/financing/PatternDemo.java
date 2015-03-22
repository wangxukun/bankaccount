package xdl.wxk.financing;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.service.IGetFullData;
import xdl.wxk.financing.service.impl.GetFullDataImpl;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;
public class PatternDemo {
	public static void main(String[] args) throws SQLException {
		String startDate = "";
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
	}
}
