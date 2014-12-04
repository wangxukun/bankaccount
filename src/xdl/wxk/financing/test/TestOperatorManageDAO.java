package xdl.wxk.financing.test;

import java.sql.SQLException;


import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.vo.Account;


public class TestOperatorManageDAO {

	public static void main(String[] args) {
		try {
			Account account = DAOFactory.getAccountManageDAOInstance().findAccountById(2);
			System.out.println(account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
