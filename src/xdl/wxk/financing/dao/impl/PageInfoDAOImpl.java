package xdl.wxk.financing.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import xdl.wxk.financing.dao.PageInfoDAO;
import xdl.wxk.financing.jdbc.JdbcUtils;
import xdl.wxk.financing.tools.PageInfo;

public class PageInfoDAOImpl implements PageInfoDAO {
	private JdbcUtils jdbc;
	public PageInfoDAOImpl(JdbcUtils jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public PageInfo getOperatorPageInfo(int offset, int rowCount) throws SQLException {
		PageInfo info = new PageInfo();
		String sql = "select count(*) as totalRecord from operator";
		Map<String, Object> map = new HashMap<String,Object>();
		map = jdbc.findSingleByPreparedStatement(sql, null);
		info.setCurrentPage(offset+1);
		int records =Integer.valueOf(map.get("totalRecord").toString()) ;
		info.setTotalRecord(records);
		int pages =new Double(Math.ceil(records*1.0/rowCount)).intValue();
		info.setTotalPage(pages);
		return info;
	}
}
