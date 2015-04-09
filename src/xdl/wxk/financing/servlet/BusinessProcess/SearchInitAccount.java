package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;

public class SearchInitAccount extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/x-json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String strId = request.getParameter("accountid");
		List<xdl.wxk.financing.vo.InitAccount> list = null; 
		List<xdl.wxk.financing.vo.InitAccount> accounts = null;
		String contextPath = request.getContextPath();
		JSONArray json = null;
		int accountid = Integer.parseInt(strId);
		try {
			list = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount();
			accounts = DAOFactory.getBusinessProcessDAOInstance().getAllInitaccount(list, accountid);
			json = JsonDAOFactory.getJsonAccountManageDAOInstance().getJsonOfInitdata(accounts,contextPath);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
