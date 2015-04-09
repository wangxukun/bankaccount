package xdl.wxk.financing.service.impl;

import java.sql.SQLException;
import java.util.List;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.service.IGetReviseData;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public class GetReviseDataImpl implements IGetReviseData {

	@Override
	public List<DataInfo> GetReviseData(DataSearchForm form) {
		List<DataInfo> list = null;
		System.out.println("要看的数据:"+form.getError());
		if("0".equals(form.getError().trim())){	//按数据录入日期查询
			System.out.println("到零");
			try {
				list = DAOFactory.getBusinessProcessDAOInstance().getDateDetailsByEnterdate(form);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{	//按数据发生日期
			System.out.println("到壹");
			try {
				list = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

}
