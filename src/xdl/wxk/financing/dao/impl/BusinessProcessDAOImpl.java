package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.tools.Calculator;
import xdl.wxk.financing.tools.DataUtils;
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
		Map<String, Object> map = this.jdbc.findSingleByPreparedStatement(sql,
				params);
		flag = !map.isEmpty();
		return flag;
	}

	@Override
	public List<InitAccount> getAllInitaccount() throws SQLException {
		List<InitAccount> list = new ArrayList<InitAccount>();
		String sql = "select accountid,parentid,accountname,initdate,direction,amount,summary from v_initaccount";
		List<Map<String, Object>> listMap = this.jdbc
				.findMoreByPreparedStatement(sql, null);
		Iterator<Map<String, Object>> iter = listMap.iterator();
		while (iter.hasNext()) {
			Map<String, Object> map = iter.next();
			InitAccount init = new InitAccount();
			if (!map.get("accountid").toString().equals("")) {
				int accountid = Integer
						.valueOf(map.get("accountid").toString());
				init.setAccountid(accountid);
			}
			if (!map.get("parentid").toString().equals("")) {
				int parentid = Integer.valueOf(map.get("parentid").toString());
				init.setParentid(parentid);
			}
			{
				String accountname = map.get("accountname").toString();
				init.setAccountname(accountname);
			}
			if (!map.get("initdate").toString().equals("")) {
				Date initdate = (Date) map.get("initdate");
				init.setInitdate(initdate);
			}
			if (!map.get("direction").toString().equals("")) {
				int direction = Integer
						.valueOf(map.get("direction").toString());
				init.setDirection(direction);
			} else {
				init.setDirection(-1);
			}
			if (!map.get("amount").toString().equals("")) {
				String amount = map.get("amount").toString();
				init.setAmount(amount);
			} else {
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
			int accountid) {
		List<InitAccount> result = new ArrayList<InitAccount>();
		return recursionGetInitaccount(result, initaccounts, accountid);
	}
	/**
	 * 递归从帐户初始化数据中获取指定帐户及其子帐户的初始化数据
	 * @param result	获取的初始化数据
	 * @param initaccounts	要从中获取的列表集
	 * @param accountid	指定的ID
	 * @return	获取的初始化数据
	 */
	private List<InitAccount> recursionGetInitaccount(List<InitAccount> result,
			List<InitAccount> initaccounts, int accountid) {
		Iterator<InitAccount> iter = initaccounts.iterator();
		while (iter.hasNext()) {
			InitAccount temp = iter.next();
			if (temp.getAccountid() == accountid && !result.contains(temp)) {
				result.add(temp);
			}
			if (temp.getParentid() == accountid && !result.contains(temp)) {
				result.add(temp);
				recursionGetInitaccount(result, initaccounts,
						temp.getAccountid());
			}
		}
		return result;
	}

	@Override
	public InitAccount getRootInitaccount(List<InitAccount> initAccounts,Date date) {
		InitAccount initdata = new InitAccount();
		initdata.setAccountid(initAccounts.get(0).getAccountid());
		initdata.setAccountname(initAccounts.get(0).getAccountname());
		initdata.setAmount("0.0000");
		initdata.setDirection(0);
		initdata.setInitdate(initAccounts.get(0).getInitdate());
		initdata.setParentid(initAccounts.get(0).getParentid());
		initdata.setSummary("期初余额");
		Iterator<InitAccount> iter = initAccounts.iterator();
		while (iter.hasNext()) {
			InitAccount temp = iter.next();
			if (temp.getDirection() == -1 || temp.getInitdate().compareTo(date)>0)
				continue;
			if (initdata.getDirection() == temp.getDirection()) {
				initdata.setAmount(Calculator.add(initdata.getAmount(),
						temp.getAmount()).toString());
				System.out.println("加->" + initdata.getAmount() + "方向->"
						+ initdata.getDirection());
			} else {
				initdata.setAmount(Calculator.subtract(initdata.getAmount(),
						temp.getAmount()).toString());
				System.out.println("减->" + initdata.getAmount() + "方向->"
						+ initdata.getDirection());
			}
			if (Double.valueOf(initdata.getAmount()) < 0) {
				if (initdata.getDirection() == 0) {
					initdata.setDirection(1);
				} else {
					initdata.setDirection(0);
				}
				initdata.setAmount(Calculator.abs(initdata.getAmount())
						.toString());
				System.out.println("反->" + initdata.getAmount() + "方向->"
						+ initdata.getDirection());
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
	 * 6、开始日期、结束日期、所属单位全为空或所属单位ID等于账户ID：显示当前月份所属年度整个账户明细
	 * 7、开始日期、结束日期为空，所属单位ID不等于账户ID：显示当前月份所属年度特定账户明细
	 * 8、开始日期、结束日期不为空，所属单位全为空或所属单位ID等于账户ID：显示指定月份整个账户明细 
	 * 9、完整查询
	 */
	@Override
	public List<DataInfo> getAccountDetails(DataSearchForm formDate)
			throws SQLException {
		List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
		String sql = "select d.accountdetailid,d.occurdate,d.number,d.summary,d.direction,d.amount,d.accountid,d.groupid,d.freeze,a.accountname from accountdetail as d,account as a where d.groupid=a.accountid";
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		switch(formDate.validate()){
		case 6:
			String t6 = " and d.accountid=? and d.occurdate like ?";
			buffer.append(t6);
			params.add(formDate.getAccountid());
			params.add(DataUtils.getYear(new Date())+"%");
			break;
		case 7:
			String t7 = " and d.accountid=? and d.groupid=? and d.occurdate like ?";
			buffer.append(t7);
			params.add(formDate.getAccountid());
			params.add(formDate.getGroupid());
			params.add(DataUtils.getYear(new Date())+"%");
			break;
		case 8:
			String t8 = " and d.accountid=? and d.occurdate between ? and ?";
			buffer.append(t8);
			params.add(formDate.getAccountid());
			params.add(formDate.getStartDate());
			params.add(formDate.getEndDate());
			break;
		case 9:
			String t9 = " and d.accountid=? and d.groupid=? and d.occurdate between ? and ?";
			buffer.append(t9);
			params.add(formDate.getAccountid());
			params.add(formDate.getGroupid());
			params.add(formDate.getStartDate());
			params.add(formDate.getEndDate());
			break;
		default:
			dataInfoList = null;
			return dataInfoList;
		}
		buffer.append(" order by occurdate ASC");
		List<Map<String, Object>> list = this.jdbc.findMoreByPreparedStatement(buffer.toString(), params);
		Iterator<Map<String,Object>> iter = list.iterator();
		while(iter.hasNext()){
			DataInfo temp = new DataInfo();
			Map<String,Object> map = iter.next();
			temp.setAccountdetailid(Integer.valueOf(map.get("accountdetailid").toString()));
			temp.setOccurdate((Date)map.get("occurdate"));
			temp.setNumber(Integer.valueOf(map.get("number").toString()));
			temp.setSummary(map.get("summary").toString());
			temp.setDirection(Integer.valueOf(map.get("direction").toString()));
			temp.setAmount(map.get("amount").toString());
			temp.setAccountid(Integer.valueOf(map.get("accountid").toString()));
			temp.setGroupid(Integer.valueOf(map.get("groupid").toString()));
			temp.setFreeze(Integer.valueOf(map.get("freeze").toString()));
			temp.setAccountname(map.get("accountname").toString());
			dataInfoList.add(temp);
		}
		return dataInfoList;
	}
}
