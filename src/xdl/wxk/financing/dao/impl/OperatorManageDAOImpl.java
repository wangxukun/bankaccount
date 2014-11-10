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

import xdl.wxk.financing.abstraction.OperatorRelation;
import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.Lasttimestatus;
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
		String sql = "select operatorid,operatorname,registerdate,updatedate from operator limit ?,?";
		 List<Object> params= new ArrayList<Object>();
		 params.add(offset*rowCount);
		 params.add(rowCount);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = this.jdbc.findMoreByPreparedStatement(sql, params);
		return list;
	}
	@Override
	public List<Map<String, Object>> findLimitOperatorInfo(int offset,
			int rowCount) throws SQLException {
		String sql = "select operatorid,operatorname,operatorpassword,registerdate,updatedate,logintime,accountname from operatorinfo limit ?,?";
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
	public OperatorRelation Relation(Operator operator) throws SQLException {
		OperatorRelation relation = new OperatorRelation();
		//查询特定操作员有权操作的帐户ID集合，并获得每个帐户的细节
		String sqlAccountids = "select accountid from privilege where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operator.getOperatorid());
		List<Map<String,Object>> list = this.jdbc.findMoreByPreparedStatement(sqlAccountids, params);
		Iterator<Map<String,Object>> iter = list.iterator();
		List<Account> accounts = new ArrayList<Account>();
		while(iter.hasNext()){
			Map<String,Object> map = iter.next();
			String sqlAccounts = "select accountid,parentid,accountname from account where accountid=?";
			List<Object> paramsTemp = new ArrayList<Object>();
			paramsTemp.add(map.get("accountid"));
			Map<String,Object> accountMap = this.jdbc.findSingleByPreparedStatement(sqlAccounts, paramsTemp);
			Account accountTemp = new Account();
			accountTemp.setAccountid(Integer.parseInt(accountMap.get("accountid").toString()));
			accountTemp.setParentid(Integer.parseInt(accountMap.get("parentid").toString()));
			accountTemp.setAccountname(accountMap.get("accountname").toString());
			accounts.add(accountTemp);
		}
		relation.setAccounts(accounts);
		
		
		//查询并获取特定操作员的细节
		Operator tempOperator = new Operator();
		String sqlOperator = "select operatorid,operatorname,registerdate,updatedate from operator where operatorid=?";
		List<Object> paramsOperator = new ArrayList<Object>();
		paramsOperator.add(operator.getOperatorid());
		Map<String,Object> mapOperator = this.jdbc.findSingleByPreparedStatement(sqlOperator, paramsOperator);
		if(!mapOperator.isEmpty()){
			tempOperator.setOperatorid(Integer.parseInt(mapOperator.get("operatorid").toString()));
			tempOperator.setOperatorname(mapOperator.get("operatorname").toString());
			tempOperator.setRegisterdate((Date)mapOperator.get("registerdate"));
			tempOperator.setUpdatedate((Date)mapOperator.get("updatedate"));
		}
		relation.setOperator(tempOperator);
		
		
		//查询上次登陆信息
		Lasttimestatus status = new Lasttimestatus();
		String sqlLastTimeStatus= "select operatorid,accountid,logintime from lasttimestatus where operatorid=?";
		List<Object> paramsStatus = new ArrayList<Object>();
		paramsStatus.add(tempOperator.getOperatorid());
		Map<String,Object> mapStatus = this.jdbc.findSingleByPreparedStatement(sqlLastTimeStatus, paramsStatus);
		if(!mapStatus.isEmpty()){
			status.setOperatorid(Integer.parseInt(mapStatus.get("operatorid").toString()));
			status.setAccountid(Integer.parseInt(mapStatus.get("accountid").toString()));
			status.setLogintime((Date)mapStatus.get("logintime"));
		}
		relation.setStatus(status);
		
		return relation;
	}
	@Override
	public LoginInfo getLoginInfo(OperatorRelation relation) throws SQLException {
		LoginInfo info = new LoginInfo();
		String sql = "select operatorname,accountname,logintime from logininfo where operatorname=?";
		List<Object> params = new ArrayList<Object>();
		params.add(relation.getOperator().getOperatorname());
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			info.setOperatorname(map.get("operatorname").toString());
			info.setAccountname(map.get("accountname").toString());
			Date dateTemp = (Date)map.get("logintime");
			
			String dateString = DateFormat.getDateInstance(DateFormat.FULL,Locale.CHINA).format(dateTemp);
			String str = dateString.substring(0,dateString.indexOf("月")+1);
			
			info.setLogintime(str);
		}
		boolean isManager = false;
		isManager = this.checkOperatorLevel(relation.getOperator(), 100);
		info.setIsManager(isManager);
		return info;
	}
	@Override
	public boolean checkOperatorLevel(Operator operator, int level)  throws SQLException{
		boolean flag = false;
		String sql="select distinct operatorid from privilege where level=?";
		List<Object> params = new ArrayList<Object>();
		params.add(level);
		List<Map<String, Object>> list = this.jdbc.findMoreByPreparedStatement(sql, params);
		if(list.isEmpty()){
			flag = false;
		}
		else{
			Iterator<Map<String, Object>> iter = list.iterator();
			while(iter.hasNext()){
				Map<String,Object> temp = iter.next();
				if(operator.getOperatorid() == (Integer)temp.get("operatorid")){
					flag = true;
				}
			}
		}
		return flag;
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
		String sql = "select operatorid from privilege where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		List<Map<String, Object>> priv = this.jdbc.findMoreByPreparedStatement(sql, params);
		boolean flag = priv.isEmpty();
		return !flag;
	}
	@Override
	public boolean delPrivilegeById(int operatorid) throws SQLException {
		boolean flag = false;
		String sql="delete from privilege where operatorid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public boolean addPrivilege(int operatorid, int accountid,int level)
			throws SQLException {
		boolean flag = false;
		String sql = "insert into privilege(operatorid,accountid,level) values(?,?,?)";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		params.add(accountid);
		params.add(level);
		flag = this.jdbc.updateByPreparedStatement(sql, params);
		return flag;
	}
	@Override
	public int findLevel(int operatorid, int accountid) throws SQLException {
		int level = 0;
		String sql = "select level from privilege where operatorid=? and accountid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(operatorid);
		params.add(accountid);
		Map<String,Object> map = this.jdbc.findSingleByPreparedStatement(sql, params);
		if(!map.isEmpty()){
			level = Integer.parseInt(map.get("level").toString());
		}
		return level;
	}
}
