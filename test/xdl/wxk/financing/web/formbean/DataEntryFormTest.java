package xdl.wxk.financing.web.formbean;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Administrator
 *
 */

public class DataEntryFormTest {
	DataEntryForm form;
	
	//执行任意一个方法之前都会调用setUp()方法
	@Before
	public void setUp(){
		form = new DataEntryForm();
		form.setOccurdate("2015-2-28");
		form.setSummary("20-15年王旭昆王旭昆王旭昆王旭昆王旭昆王旭");
		form.setGroupid("1");
		form.setAmount("23525.25");
	}
	
	//加了@Test表示是一个单元测试方法
	@Test
	public void testValidate(){
		boolean f = form.validate();
	//	assertEquals(form.getErrors().get("occurdate"), f, true);
	//	assertEquals(form.getErrors().get("summary"), f,true);
		assertEquals(form.getErrors().get("amount"), f,true);
	}
}