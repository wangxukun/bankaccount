package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.service.IGetFullData;
import xdl.wxk.financing.service.impl.GetFullDataImpl;
import xdl.wxk.financing.vo.DataInfo;
import xdl.wxk.financing.vo.InitAccount;
import xdl.wxk.financing.web.formbean.DataSearchForm;

public class JsonDataAccountDetail extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/x-json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
//		int accountid = Integer.parseInt(request.getParameter("accountid"));
		PrintWriter out = response.getWriter();
		
		/*try {
			List<AccountDetail> details = DAOFactory.getAccountManageDAOInstance().findAccountDetailsByAccountid(accountid);
			JSONArray jsonAccountDetail = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountDetail(details);
			out.println(jsonAccountDetail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		String startDate = "";
		String endDate = "";
		String accountid = "1";
		String groupid = "1";
		DataSearchForm form = new DataSearchForm(accountid,groupid,startDate,endDate);
		System.out.println("验证结果编码："+form.validate()+"\n反馈信息："+form.getError());
		
		List<DataInfo> listData;
		try {
			listData = DAOFactory.getBusinessProcessDAOInstance().getAccountDetails(form);
			
			List<InitAccount> list = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount();
			List<InitAccount> list1 = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount(list, 1);
			InitAccount initAccount = DAOFactory.getBusinessProcessDAOInstance().getRootInitaccount(list1,new Date());
			IGetFullData getFullData = new GetFullDataImpl();
			List<DataInfo> full = getFullData.GetFullData(initAccount, listData);
			JSONArray data = JsonDAOFactory.getJsonAccountManageDAOInstance().getFullDataForEasyGrid(full);
			
			out.println(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		out.flush();
		out.close();
	}

}
