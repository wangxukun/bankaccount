package xdl.wxk.financing.service;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import xdl.wxk.financing.service.impl.UpdateInitBill;
import xdl.wxk.financing.vo.InitAccount;


public class IUpdateInitBillTest {
	InitAccount initAccount = new InitAccount();
	@Before
	public void setUp(){
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = date.parse("2013-5-7");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		initAccount.setAccountid(5);
		initAccount.setDirection(1);
		initAccount.setInitdate(d);
		initAccount.setAmount("782");
		initAccount.setSummary("用JUnit测试修改初始化数据");
	}
	@Test
	public void testUpdateInitBill(){
		IUpdateInitBill update = new UpdateInitBill();
		try {
			assertEquals("修改失败",update.updateInitBill(initAccount),true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
