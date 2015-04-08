package xdl.wxk.financing.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcUtils {

	// 数据库的用户名
	private final String USERNAME = "root";
	// 数据库的密码
	private final String PASSWORD = "wxlwxkwzl-2015";
	// 数据库的驱动信息
	private final String DRIVER = "com.mysql.jdbc.Driver";
	// 访问数据库的地址
	private final String URL = "jdbc:mysql://localhost:3306/bankAccounts";

	// 数据库的链接
	private Connection connection;
	// sql语句的执行对象
	private PreparedStatement pstmt;
	// 查询返回的结果集合
	private ResultSet resultSet;

	public JdbcUtils() {
		// TODO Auto-generated constructor stub
		try {
			Class.forName(DRIVER);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("注册驱动失败！");
			e.printStackTrace();
		}
	}

	// 获得数据库的链接
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return connection;
	}

	/**
	 * 完成对数据库的表的添加、删除和修改的操作
	 * 
	 * @param sql SQL语句
	 * @param params 填充占位符
	 * @return 操作成功返回true，失败返回false
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params)
			throws SQLException {
		boolean flag = false;
		int result = -1; // 表示当用户执行添加、删除和修改的时候所影响数据库的行数
		pstmt = connection.prepareStatement(sql);
		int index = 1;//点位符的第一个位置
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;
		return flag;
	}

	/**
	 * 完成返回单条记录的数据库查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSingleByPreparedStatement(String sql,
			List<Object> params) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery(); // 获取查询结果

		ResultSetMetaData rsmd = resultSet.getMetaData(); // 获取查询结果的细节
		int colmun_len = rsmd.getColumnCount(); // 返回列数
		while (resultSet.next()) {
			for (int i = 0; i < colmun_len; i++) {
				String colmun_name = rsmd.getColumnName(i + 1); // 返回列名称
				Object colmun_value = resultSet.getObject(colmun_name); // 在当前行中根据列名称返回值
				if (colmun_value == null) {
					colmun_value = "";
				}
				result.put(colmun_name, colmun_value);
			}
		}
		return result;
	}

	/**
	 * 完成返回多条记录的数据库查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMoreByPreparedStatement(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery(); // 获取查询结果

		ResultSetMetaData rsmd = resultSet.getMetaData(); // 获取查询结果的细节
		int colmun_len = rsmd.getColumnCount(); // 返回列数
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < colmun_len; i++) {
				String colmun_name = rsmd.getColumnName(i + 1); // 返回列名称
				Object colmun_value = resultSet.getObject(colmun_name); // 在当前行中根据列名称返回值
				if (colmun_value == null) {
					colmun_value = "";
				}
				map.put(colmun_name, colmun_value);
			}
			result.add(map);
		}
		return result;
	}

	/**
	 * 关闭数据库连接
	 */
	public void releaseConnection() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}