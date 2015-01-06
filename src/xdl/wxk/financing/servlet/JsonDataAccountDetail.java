package xdl.wxk.financing.servlet;

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
import xdl.wxk.financing.vo.AccountDetail;

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
		
		int accountid = Integer.parseInt(request.getParameter("accountid"));
		PrintWriter out = response.getWriter();
		
		try {
			List<AccountDetail> details = DAOFactory.getAccountManageDAOInstance().findAccountDetailsByAccountid(accountid);
			JSONArray jsonAccountDetail = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountDetail(details);
			out.println(jsonAccountDetail);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

}
