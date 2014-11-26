package xdl.wxk.financing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public interface OperatorManageDAO {
	//检查操作员登录
	boolean checkOperatorLogin(Operator operator) throws SQLException;
	//查找操作员ById
	Operator findOperatorById(int operatorid) throws SQLException;
	//新增操作员
	boolean addOperator(Operator operator) throws SQLException;
	//查找所有操作员
	List<Map<String,Object>> findAllOperator() throws SQLException;
	//分页查找操作员
	List<Map<String,Object>> findLimitOperator(int offset,int rowCount) throws SQLException;
	//判断操作员是否存在
	boolean isExist(Operator operator) throws SQLException;
	//删除操作员
	boolean delOperator(Operator operator) throws SQLException;
	//取得操作员的登录信息
	LoginInfo getLoginInfo(Operator operator,Account account) throws SQLException;
	//判断操作员是不有一定的权限
	boolean isHasPrivilege(int operatorid) throws SQLException;
	//删除指定操作员的所有权限
	boolean delPrivilegeById(int operatorid) throws SQLException;
	//操作员授权
	boolean addPrivilege(int operatorid,int accountid,int level) throws SQLException;
	//查找特定操作员对特定帐户的权限
	public int findLevel(int operatorid, int accountid) throws SQLException;
	//查找特点操作员是否是管理员
	boolean isAdmin(Operator operator) throws SQLException;
	//取得特定操作员已有权限的所有帐户
	List<Account> getAuthorizedAccounts(Operator operator) throws SQLException;
}
