package xdl.wxk.financing.dao.proxy;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import xdl.wxk.financing.abstraction.OperatorRelation;
import xdl.wxk.financing.dao.OperatorManageDAO;
import xdl.wxk.financing.dao.impl.OperatorManageDAOImpl;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorManageDAOProxy implements OperatorManageDAO{
	private JdbcUtils jdbc;
	private OperatorManageDAO dao;
	public OperatorManageDAOProxy() {
		this.jdbc = new JdbcUtils();
		this.jdbc.getConnection();
		this.dao = new OperatorManageDAOImpl(this.jdbc);
	}
	@Override
	public boolean checkOperatorLogin(Operator operator) throws SQLException {
		boolean flag = false;
		try {
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
			list = this.dao.findLimitOperator(offset,rowCount);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> findLimitOperatorInfo(int offset,
			int rowCount) throws SQLException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list;
		try {
			list = this.dao.findLimitOperatorInfo(offset, rowCount);
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
			flag = this.dao.isExist(operator);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
	}
	@Override
	public OperatorRelation Relation(Operator operator) throws SQLException {
		OperatorRelation relation;
		try {
			relation = this.dao.Relation(operator);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return relation;
	}
	@Override
	public LoginInfo getLoginInfo(OperatorRelation relation)
			throws SQLException {
		LoginInfo info;
		try {
			info = this.dao.getLoginInfo(relation);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return info;
	}
	@Override
	public boolean checkOperatorLevel(Operator operator, int level)
			throws SQLException {
		boolean flag = false;
		try {
			flag = this.dao.checkOperatorLevel(operator, level);
		} catch (Exception e) {
			throw e;
		}finally{
			this.jdbc.releaseConnection();
		}
		return flag;
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
	public boolean addPrivilege(int operatorid, int accountid)
			throws SQLException {
		boolean flag = false;
			try{
				this.jdbc.getConnection();
				flag = this.dao.addPrivilege(operatorid, accountid);
			}catch(Exception e){
				throw e;
			}finally{
				this.jdbc.releaseConnection();
			}
		return flag;
	}
}
