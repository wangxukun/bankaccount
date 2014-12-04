package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.dao.impl.OperatorManageDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorManageDAOProxy implements OperatorManageDAO{
	private JdbcUtils jdbc;
	private OperatorManageDAO dao;
	public OperatorManageDAOProxy() {
		this.jdbc = new JdbcUtils();
		this.dao = new OperatorManageDAOImpl(this.jdbc);
	}
	@Override
	public boolean checkOperatorLogin(Operator operator) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.checkOperatorLogin(operator);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
	@Override
	public boolean addOperator(Operator operator) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.addOperator(operator);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
	@Override
	public List<Map<String, Object>> findAllOperator() throws SQLException {
		List<Map<String, Object>> list;
		try {
			this.jdbc.getConnection();
			list = this.dao.findAllOperator();
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> findLimitOperator(int offset, int rowCount)
			throws SQLException {
		List<Map<String, Object>> list;
		try {
			this.jdbc.getConnection();
			list = this.dao.findLimitOperator(offset,rowCount);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}
	@Override
	public boolean isExist(Operator operator) throws SQLException {
		boolean flag;
		try {
			this.jdbc.getConnection();
			flag = this.dao.isExist(operator);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}

	@Override
	public LoginInfo getLoginInfo(Operator operator,Account account)
			throws SQLException {
		LoginInfo info;
		try {
			this.jdbc.getConnection();
			info = this.dao.getLoginInfo(operator,account);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return info;
	}
	@Override
	public boolean delOperator(Operator operator) throws SQLException {
		boolean flag = false;
		if(this.isExist(operator)){
			this.jdbc.getConnection();
			try {
				flag = this.dao.delOperator(operator);
			} catch (Exception e) {
				throw e;
			}finally{
				this.jdbc.releaseConnection();
			}
		}
		return flag;
	}
	
	@Override
	public boolean isHasPrivilege(int operatorid) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.isHasPrivilege(operatorid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
	@Override
	public boolean delPrivilegeById(int operatorid) throws SQLException {
		boolean flag =false;
		if(this.isHasPrivilege(operatorid)){
			try{
				this.jdbc.getConnection();
				flag = this.dao.delPrivilegeById(operatorid);
			}catch(Exception e){
				throw e;
			}finally{
				this.jdbc.releaseConnection();
			}
		}
		return flag;
	}
	@Override
	public boolean addPrivilege(int operatorid, int accountid,int level)
			throws SQLException {
		boolean flag = false;
			try{
				this.jdbc.getConnection();
				flag = this.dao.addPrivilege(operatorid, accountid,level);
			}catch(Exception e){
				throw e;
			}finally{
				this.jdbc.releaseConnection();
			}
		return flag;
	}
	@Override
	public int findLevel(int operatorid, int accountid) throws SQLException {
		int level = -1;
		try {
			this.jdbc.getConnection();
			level = this.dao.findLevel(operatorid, accountid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return level;
	}
	@Override
	public boolean isAdmin(Operator operator) throws SQLException {
		boolean flag = false;
		try {
			this.jdbc.getConnection();
			flag = this.dao.isAdmin(operator);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
	@Override
	public List<Account> getAuthorizedAccounts(Operator operator)
			throws SQLException {
		List<Account> list = null;
		if(this.isHasPrivilege(operator.getOperatorid())){
			try {
				this.jdbc.getConnection();
				list = this.dao.getAuthorizedAccounts(operator);
			} catch (Exception e) {
				throw e;
			}finally{
				this.jdbc.releaseConnection();
			}
		}
		return list;
	}
	@Override
	public Operator findOperatorById(int operatorid) throws SQLException {
		Operator operator = null;
		try {
			this.jdbc.getConnection();
			operator = this.dao.findOperatorById(operatorid);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return operator;
	}
}
