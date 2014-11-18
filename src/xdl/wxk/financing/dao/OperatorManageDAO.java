package xdl.wxk.financing.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public interface OperatorManageDAO {
	boolean checkOperatorLogin(Operator operator) throws SQLException;
	boolean addOperator(Operator operator) throws SQLException;
	List<Map<String,Object>> findAllOperator() throws SQLException;
	List<Map<String,Object>> findLimitOperator(int offset,int rowCount) throws SQLException;
	List<Map<String,Object>> findLimitOperatorInfo(int offset,int rowCount) throws SQLException;
	//判断操作员是否存在
	boolean isExist(Operator operator) throws SQLException;
	boolean delOperator(Operator operator) throws SQLException;
	LoginInfo getLoginInfo(Operator operator) throws SQLException;
	//判断操作员是不有一定的权限
	boolean isHasPrivilege(int operatorid) throws SQLException;
	//删除指定操作员的所有权限
	boolean delPrivilegeById(int operatorid) throws SQLException;
	//操作员授权
	boolean addPrivilege(int operatorid,int accountid,int level) throws SQLException;
	//查找特点操作员是否是管理员
	boolean isAdmin(Operator operator) throws SQLException;
}
