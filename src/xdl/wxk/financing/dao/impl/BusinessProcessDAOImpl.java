package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.tools.Calculator;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public class BusinessProcessDAOImpl implements BusinessProcessDAO {
	private JdbcUtils jdbc;
	
	public BusinessProcessDAOImpl(JdbcUtils jdbc) {
		this.jdbc = jdbc;
	}

	public boolean insertAccountDetail(AccountDetail accountDetail)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into accountdetail(occurdate,summary,direction,amount,accountid,groupid) values(?,?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(accountDetail.getOccurdate());
		params.add(accountDetail.getSummary());
		params.add(accountDetail.getDirection());
		params.add(accountDetail.getAmount());
		params.add(accountDetail.getAccountid());
		params.add(accountDetail.getGroupid());
		flag = jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	public boolean insertInitAccount(InitAccount initAccount)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into initaccount(accountid,initdate,direction,amount,summary) value(?,?,?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(initAccount.getAccountid());
		params.add(initAccount.getInitdate());
		params.add(initAccount.getDirection());
		params.add(initAccount.getAmount());
		params.add(initAccount.getSummary());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}

	public boolean isInit(int accountId) throws SQLException {
		boolean flag = false;
		String sql = "select accountid from initaccount where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(accountId);
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		flag = !map.isEmpty();
		return flag;
	}

	@Override
	public List<InitAccount> getAllInitaccount() throws SQLException {
		List<InitAccount> list = new ArrayList<InitAccount>();
		String sql = "select accountid,parentid,accountname,initdate,direction,amount,summary from v_initaccount";
		List<Map<String,Object>> listMap = this.jdbc.findMoreByPreparedStatement(sql, null);
		Iterator<Map<String,Object>> iter = listMap.iterator();
		while(iter.hasNext()){
			Map<String,Object> map = iter.next();
			InitAccount init = new InitAccount();
			if(!map.get("accountid").toString().equals("")){
				int accountid = Integer.valueOf(map.get("accountid").toString());
				init.setAccountid(accountid);
			}
			if(!map.get("parentid").toString().equals("")){
				int parentid = Integer.valueOf(map.get("parentid").toString());
				init.setParentid(parentid);
			}
			{
				String accountname = map.get("accountname").toString();
				init.setAccountname(accountname);
			}
			if(!map.get("initdate").toString().equals("")){
				Date initdate = (Date)map.get("initdate");
				init.setInitdate(initdate);
			}
			if(!map.get("direction").toString().equals("")){
				int direction = Integer.valueOf(map.get("direction").toString());
				init.setDirection(direction);
			}else{
				init.setDirection(-1);
			}
			if(!map.get("amount").toString().equals("")){
				String amount = map.get("amount").toString();
				init.setAmount(amount);
			}else{
				init.setAmount("0.0000");
			}
			{
				String summary = map.get("summary").toString();
				init.setSummary(summary);
			}
			
			list.add(init);
		}
		return list;
	}

	@Override
	public List<InitAccount> getAllInitaccount(List<InitAccount> initaccounts,
			int accountid){
		List<InitAccount> result = new ArrayList<InitAccount>();
		return recursionGetInitaccount(result,initaccounts,accountid);
	}

	private List<InitAccount> recursionGetInitaccount(List<InitAccount> result,List<InitAccount> initaccounts,int accountid){
		Iterator<InitAccount> iter = initaccounts.iterator();
		while(iter.hasNext()){
			InitAccount temp = iter.next();
			if(temp.getAccountid()==accountid && !result.contains(temp)){
				result.add(temp);
			}
			if(temp.getParentid()==accountid && !result.contains(temp)){
				result.add(temp);
				recursionGetInitaccount(result,initaccounts,temp.getAccountid());
			}
		}
		return result;
	}

	@Override
	public InitAccount getRootInitaccount(List<InitAccount> initAccounts) {
		InitAccount initdata = new InitAccount();
		initdata.setAccountid(initAccounts.get(0).getAccountid());
		initdata.setAccountname(initAccounts.get(0).getAccountname());
		initdata.setAmount("0.0000");
		initdata.setDirection(0);
		initdata.setInitdate(initAccounts.get(0).getInitdate());
		initdata.setParentid(initAccounts.get(0).getParentid());
		initdata.setSummary("期初余额");
		Iterator<InitAccount> iter = initAccounts.iterator();
		while(iter.hasNext()){
			InitAccount temp = iter.next();
			if(temp.getDirection() == -1)
				continue;
			if(initdata.getDirection()==temp.getDirection()){
				initdata.setAmount(Calculator.add(initdata.getAmount(),temp.getAmount()).toString());
				System.out.println("加->"+initdata.getAmount()+"方向->"+initdata.getDirection());
			}else{
				initdata.setAmount(Calculator.subtract(initdata.getAmount(),temp.getAmount()).toString());
				System.out.println("减->"+initdata.getAmount()+"方向->"+initdata.getDirection());
			}
			if(Double.valueOf(initdata.getAmount())<0){
				if(initdata.getDirection() == 0){
					initdata.setDirection(1);
				}else{
					initdata.setDirection(0);
				}
				initdata.setAmount(Calculator.abs(initdata.getAmount()).toString());
				System.out.println("反->"+initdata.getAmount()+"方向->"+initdata.getDirection());
			}
		}
		return initdata;
	}
	
	@Override
	public boolean updateInitAccount(InitAccount initAccount)
			throws SQLException {
		boolean flag = false;
		String sql = "update initaccount set initdate=?,direction=?,amount=?,summary=? where accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(initAccount.getInitdate());
		params.add(initAccount.getDirection());
		params.add(initAccount.getAmount());
		params.add(initAccount.getSummary());
		params.add(initAccount.getAccountid());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	/*
	 * 验证查询的九种情况
	 * 1、开始日期为空，结束日期不为空，不能查询
	 * 2、开始日期不为空，结束日期为空，不能查询
	 * 3、日期格式不对，不能查询
	 * 4、开始日期后于结束日期不能查询
	 * 5、开始日期与结束日期跨年度不能查询
	 * 6、开始日期、结束日期、所属单位全为空或所属单位ID等于账户ID：显示当前月份整个账户明细
	 * 7、开始日期、结束日期为空，所属单位ID不等于账户ID：显示当前月份特定账户明细
	 * 8、开始日期、结束日期不为空，所属单位全为空或所属单位ID等于账户ID：显示指定月份整个账户明细
	 * 9、完整查询
	 */
	@Override
	public List<DataInfo> getAccountDetails(DataSearchForm formDate)
			throws SQLException {
		List<DataInfo> dataInfo = new ArrayList<DataInfo>();
		switch(formDate.validate()){
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
	/*		Calendar calendar = Calendar.getInstance(Locale.CHINA);
			Date date = calendar.getTime();
			String sql = "select accountdetailid,occurdate,number,summary,direction,amount,accountid,groupid,freeze from accountdetail where accountid=? and occurdate between ? and ? order by occurdate ASC";
			*/
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar calendar = Calendar.getInstance();
	        Date theDate = calendar.getTime();
	        
	        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
	        gcLast.setTime(theDate);
	        gcLast.set(Calendar.DAY_OF_MONTH, 1);
	        String day_first = df.format(gcLast.getTime());
	        /*StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");*/
	        System.out.println(day_first);
			break;
		case 7:
			System.out.println(7);
			break;
		case 8:
			System.out.println(8);		
			break;
		case 9:
			System.out.println(9);
			break;
		}
		return dataInfo;
	}
}
