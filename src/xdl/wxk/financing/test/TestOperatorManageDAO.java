package xdl.wxk.financing.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;


public class TestOperatorManageDAO {

	public static void main(String[] args) {
		try {
			List<Map<String, Object>> list = DAOFactory.getOperatorManageDAOInstance().findAllOperator();
			JSONArray array = JsonDAOFactory.getJsonOperatorManageDAOInstance().getOperatorForEasyTree(list);
			System.out.println(array);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
