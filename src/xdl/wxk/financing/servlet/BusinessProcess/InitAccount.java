package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.dao.BusinessProcessDAO;
import xdl.wxk.financing.dao.factory.DAOFactory;

public class InitAccount extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;");
		response.setCharacterEncoding("utf-8");
		String initdate = request.getParameter("initdate");
		String summary = request.getParameter("summary");
		String direction = request.getParameter("direction");
		String amount = request.getParameter("amount");
		String accountid = request.getParameter("accountid");
		DateFormat myDateFormat = DateFormat.getDateInstance();
		Date myDate = null;
		try {
			myDate = myDateFormat.parse(initdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xdl.wxk.financing.vo.InitAccount initAccount = new xdl.wxk.financing.vo.InitAccount(Integer.parseInt(accountid),myDate,Integer.parseInt(direction),amount,summary);
		BusinessProcessDAO dao = DAOFactory.getBusinessProcessDAOInstance();
		try {
			if(!dao.isInit(initAccount.getAccountid(),initAccount.getInitdate())){
				if(dao.insertInitAccount(initAccount)){
					System.out.println("初始化成功！");
					response.setStatus(HttpServletResponse.SC_CREATED);
				}else{
					System.out.println("初始化失败！");
					response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
				}
			}else{
				System.out.println("初始化数据已存在！");
				response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
