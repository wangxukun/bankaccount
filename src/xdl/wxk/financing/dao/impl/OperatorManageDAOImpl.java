package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorManageDAOImpl implements OperatorManageDAO {
	private JdbcUtils jdbc;
	public OperatorManageDAOImpl(JdbcUtils jdbc){
		this.jdbc = jdbc;
	}
	@Override
	public boolean checkOperatorLogin(Operator operator) throws SQLException {
		boolean flag = false;
		String sql = "select operatorid from operator where operatorname=? and operatorpassword=MD5(?)";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorname());
		params.add(operator.getOperatorpassword());
		Map<String,Object> map = new HashMap<String,Object>();
		map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			operator.setOperatorid(Integer.parseInt(map.get("operatorid").toString()));
			flag = true;
		}
		return flag;
	}
	@Override
	public boolean addOperator(Operator operator) throws SQLException {
		boolean flag = false;
		String sql = "insert into operator(operatorname,operatorpassword) values(?,MD5(?))";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorname());
		params.add(operator.getOperatorpassword());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public List<Map<String, Object>> findAllOperator() throws SQLException {
		String sql = "select operatorid,operatorname,registerdate,updatedate from operator";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = this.jdbc.findMoreByPreparedStatement(sql, null);
		return list;
	}
	@Override
	public List<Map<String, Object>> findLimitOperator(int offset, int rowCount)
			throws SQLException {
		String sql = "select operatorid,operatorname,operatorpassword,registerdate,updatedate from operator limit ?,?";
		 List<Object> params= new ArrayList<Object>();
		 params.add(offset*rowCount);
		 params.add(rowCount);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = this.jdbc.findMoreByPreparedStatement(sql, params);
		return list;
	}
	
	@Override
	public boolean isExist(Operator operator) throws SQLException {
		String sql = "select operatorid from operator where operatorname=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorname());
		Map<String,Object> ope = this.jdbc.findSingleByPreparedStatement(sql, params);
		boolean flag = ope.isEmpty();
		return !flag;
	}
	@Override
	public LoginInfo getLoginInfo(Operator operator) throws SQLException {
		LoginInfo info = new LoginInfo();
		String sql = "select operatorid,operatorname,accountid,accountname,level,period from logininfo where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorid());
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			info.setOperatorid(Integer.parseInt(map.get("operatorid").toString()));
			info.setOperatorname(map.get("operatorname").toString());
			info.setAccountid(Integer.parseInt(map.get("accountid").toString()));
			info.setAccountname(map.get("accountname").toString());
			info.setLevel(Integer.parseInt(map.get("level").toString()));
			Date dateTemp = (Date)map.get("period");
			
			String dateString = DateFormat.getDateInstance(DateFormat.FULL,Locale.CHINA).format(dateTemp);
			String str = dateString.substring(0,dateString.indexOf("月")+1);
			
			info.setPeroid(str);
		}
		return info;
	}
	@Override
	public boolean delOperator(Operator operator) throws SQLException {
		boolean flag = false;
		String sql="delete from operator where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorid());
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public boolean isHasPrivilege(int operatorid) throws SQLException {
		String sql = "select operatorid from operator_has_account where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		List<Map<String, Object>> priv = this.jdbc.findMoreByPreparedStatement(sql, params);
		boolean flag = priv.isEmpty();
		return !flag;
	}
	@Override
	public boolean delPrivilegeById(int operatorid) throws SQLException {
		boolean flag = false;
		String sql="delete from operator_has_account where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public boolean addPrivilege(int operatorid, int accountid,int level)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into operator_has_account(operatorid,accountid,level) values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		params.add(accountid);
		params.add(level);
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public int findLevel(int operatorid, int accountid) throws SQLException{
		int level = -1;
		String sql = "select level from operator_has_account where operatorid=? and accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		params.add(accountid);
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			level = Integer.parseInt(map.get("level").toString());
		}
		return level;
	}
	@Override
	public boolean isAdmin(Operator operator) throws SQLException {
		boolean flag = false;
		String sql = "select b.operatorid,b.accountid from (select accountid from account where parentid=0) as a ,operator_has_account as b where a.accountid = b.accountid and b.operatorid=? and b.level<>-1";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorid());
		Map<String,Object> ope = this.jdbc.findSingleByPreparedStatement(sql, params);
		flag = ope.isEmpty();
		return !flag;
	}
	@Override
	public List<Account> getAuthorizedAccounts(Operator operator) throws SQLException {
		List<Account> accounts = new ArrayList<Account>();
		String sql = "select a.accountid,a.accountname,a.parentid from account as a,operator_has_account as o_has_a where a.accountid=o_has_a.accountid and o_has_a.operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorid());
		List<Map<String,Object>> list = this.jdbc.findMoreByPreparedStatement(sql, params);
		Iterator<Map<String,Object>> iter = list.iterator();
		while(iter.hasNext()){
			Map<String,Object> temp = iter.next();
			Account account = new Account();
			account.setAccountid(Integer.valueOf(temp.get("accountid").toString()));
			account.setAccountname(temp.get("accountname").toString());
			account.setParentid(Integer.valueOf(temp.get("parentid").toString()));
			accounts.add(account);
		}
		return accounts;
	}
	@Override
	public Operator findOperatorById(int operatorid) throws SQLException {
		Operator operator = new Operator();
		String sql = "select operatorname from operator where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		Map<String,Object> ope = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!ope.isEmpty()){
			operator.setOperatorid(operatorid);
			operator.setOperatorname(ope.get("operatorname").toString());
		}
		return operator;
	}
}
