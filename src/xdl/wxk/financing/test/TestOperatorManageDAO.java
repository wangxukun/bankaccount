package xdl.wxk.financing.test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Locale;


import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.vo.AccountDetail;


public class TestOperatorManageDAO {

	public static void main(String[] args) {
		try {
			/*List<AccountDetail> detail= DAOFactory.getAccountManageDAOInstance().findAccountDetailsByAccountid(3);
			Iterator<AccountDetail> iter = detail.iterator();
			while(iter.hasNext()){
				AccountDetail d = iter.next();
				Calendar calendar = Calendar.getInstance(Locale.CHINA);
				calendar.setTime(d.getOccurdate());
				System.out.println(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"+calendar.get(Calendar.DATE)+"日");
			}
			System.out.println("----------------------------------");
			JSONArray array = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountDetail(detail);
			System.out.println(array);*/
			AccountDetail accountDetail = new AccountDetail();
			accountDetail.setAccountid(3);
			accountDetail.setAmount(9527);
			accountDetail.setDirection(0);
			accountDetail.setNumber(10);
			accountDetail.setSummary("看电影");
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			accountDetail.setOccurdate(calendar.getTime());
			DAOFactory.getAccountManageDAOInstance().addAccountDetail(accountDetail);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
