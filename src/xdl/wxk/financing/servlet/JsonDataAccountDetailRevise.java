package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.service.IGetFullData;
import xdl.wxk.financing.service.IGetReviseData;
import xdl.wxk.financing.service.impl.GetFullDataImpl;
import xdl.wxk.financing.service.impl.GetReviseDataImpl;
import xdl.wxk.financing.tools.DateUtils;
import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public class JsonDataAccountDetailRevise extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/x-json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();
		
		/*String startDate = "";
		String endDate = "";
		String accountid = "1";
		String groupid = "1";
		DataSearchForm form = new DataSearchForm(accountid,groupid,startDate,endDate);*/
		DataSearchForm form = WebUtils.requestToBean(request, DataSearchForm.class);
		System.out.println(form);
//		System.out.println("验证结果编码："+form.validate()+"\n反馈信息："+form.getError());
		
		/**
		 * 验证查询的九种情况
		 * 1、开始日期为空，结束日期不为空，不能查询
		 * 2、开始日期不为空，结束日期为空，不能查询
		 * 3、日期格式不对，不能查询
		 * 4、开始日期后于结束日期，不能查询
		 * 5、开始日期与结束日期跨年度，不能查询
		 * 6、开始日期、结束日期、所属单位全为空或所属单位ID等于账户ID或所属单位ID为空：显示当前月份所对应年度整个账户明细
		 * 7、查询指定月份期间的整个账户数据
		 * 8、开始日期、结束日期为空，所属单位ID不等于账户ID并且所属单位ID不为空：显示当前月份所属年度特定账户明细
		 * 9、查询指定账户指定日期间的明细
		 */
		
		
		
		List<DataInfo> listData;	//保存数据查询结果（不包括期初余额）
		IGetReviseData getrevisedate = new GetReviseDataImpl();
		listData = getrevisedate.GetReviseData(form);
		System.out.println(listData);
		String contextPath = request.getContextPath();
		JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getReviseDataForEasyGrid(listData,contextPath);
		out.println(data);
		
		
	/*	switch(form.validate()){
		case 6:	//显示当前月份所对应年度整个账户明细
			
			try {
				listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
				//合并原始的总账初始化数据
				List<InitAccount> list = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount();	//取得所有的帐户初始信息
				List<InitAccount> list1 = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount(list, Integer.parseInt(form.getAccountid()));//取得指定帐户的所有子帐户初始信息
				InitAccount originInit = DAOFactory.getBusinessProcessDAOInstance().getRootInitaccount(list1,new Date());//合并帐户初始化余额
					
				String beforeStartBalance = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate(form.getAccountid(),null,DateUtils.getYearFirstDayOfDate(new Date()));
				System.out.println("beforeStartBalance="+beforeStartBalance);
				System.out.println("yearfirst="+DateUtils.getYearFirstDayOfDate(new Date()));
				InitAccount currentInit = DAOFactory.getBusinessProcessDAOInstance().getCurrentInitaccount(originInit, beforeStartBalance);
				IGetFullData getFullData = new GetFullDataImpl();
				List<DataInfo> full = getFullData.GetFullData(currentInit, listData); //取得期初余额及发生额组成的帐户信息
				JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);	//转换为JSON数据
				
				out.println(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 7:	//查询指定月份期间的整个账户数据
			try {
				listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
				
				List<InitAccount> list = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount();
				List<InitAccount> list1 = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount(list, Integer.parseInt(form.getAccountid()));
				
				//日期转换
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sd.parse(form.getEndDate());
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				InitAccount originInit = DAOFactory.getBusinessProcessDAOInstance().getRootInitaccount(list1,date);
				
				SimpleDateFormat startdate = new SimpleDateFormat("yyyy-MM-dd");
				Date startd = null;
				try {
					startd = startdate.parse(form.getStartDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				String beforeStartBalance = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate(form.getAccountid(),null,startd);
				InitAccount currentInit = DAOFactory.getBusinessProcessDAOInstance().getCurrentInitaccount(originInit, beforeStartBalance);
				IGetFullData getFullData = new GetFullDataImpl();
				List<DataInfo> full = getFullData.GetFullData(currentInit, listData);
				JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);
				
				out.println(data);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 8:	//显示当前月份所对应年度特定账户明细
			try {
				listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
				//取得特定帐户初始化数据
				InitAccount originInit = DAOFactory.getBusinessProcessDAOInstance().getInitAccount(Integer.valueOf(form.getGroupid()));
				System.out.println("originInit="+originInit);
				String beforeStartBalance = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate(form.getAccountid(),form.getGroupid(),DateUtils.getYearFirstDayOfDate(new Date()));
				System.out.println("beforeStartBalance="+beforeStartBalance);
				System.out.println("yearfirst="+DateUtils.getYearFirstDayOfDate(new Date()));
				InitAccount currentInit = DAOFactory.getBusinessProcessDAOInstance().getCurrentInitaccount(originInit, beforeStartBalance);
				System.out.println("currentInit="+currentInit);
				IGetFullData getFullData = new GetFullDataImpl();
				List<DataInfo> full = getFullData.GetFullData(currentInit, listData);
				System.out.println("full="+full);
				JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);
				out.println(data);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 9:	//查询指定账户指定日期间的明细
			try {
				InitAccount originInit = null;
				listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
				SimpleDateFormat startdate = new SimpleDateFormat("yyyy-MM-dd");
				Date startd = null;
				Date endd = null;
				try {
					startd = startdate.parse(form.getStartDate());
					endd = startdate.parse(form.getEndDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//取得特定帐户初始化数据
				if(DAOFactory.getBusinessProcessDAOInstance().isInit(Integer.valueOf(form.getGroupid()), endd)){
					originInit = DAOFactory.getBusinessProcessDAOInstance().getInitAccount(Integer.valueOf(form.getGroupid()));
				}
				System.out.println("originInit"+originInit);
				String beforeStartBalance = DAOFactory.getBusinessProcessDAOInstance().getBalanceBeforeStartDate(form.getAccountid(),form.getGroupid(),startd);
				InitAccount currentInit = DAOFactory.getBusinessProcessDAOInstance().getCurrentInitaccount(originInit, beforeStartBalance);
				IGetFullData getFullData = new GetFullDataImpl();
				List<DataInfo> full = getFullData.GetFullData(currentInit, listData);
				JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);
				out.println(data);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		default:	//错误查询
			return;
		}*/
		
		out.flush();
		out.close();
	}

}
