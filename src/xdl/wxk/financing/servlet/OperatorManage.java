package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.tools.PageInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorManage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action_flag = request.getParameter("action_flag");
		if ("privilegeConfig".equals(action_flag)) {
			privilegeConfig(request,response);
		} else if ("operatorManage".equals(action_flag)) {
			getJsonShowOperators(request, response);
		}else if("editing".equals(action_flag)){
			String oper = request.getParameter("oper");
			if("add".equals(oper)){
				addOperator(request,response);
				System.out.println("add");
			}else if("del".equals(oper)){
				delOperator(request,response);
			}else if("edit".equals(oper)){
				String wxk = request.getParameter("wxk");
				System.out.println("edit "+ "\n"+wxk);
			}
		}
	}

	private void privilegeConfig(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int operatorid = Integer.parseInt(request.getParameter("operatorid"));
		String accountids = request.getParameter("accountids");
		System.out.println("Config---"+operatorid);
		System.out.println("Privilege---"+accountids);
	}

	private void delOperator(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int operatorid = Integer.parseInt(request.getParameter("operId"));
		String operatorname = request.getParameter("operName");
		Operator operator = new Operator();
		operator.setOperatorid(operatorid);
		operator.setOperatorname(operatorname);
		boolean flag = false;
		try {
			flag = DAOFactory.getOperatorManageDAOInstance().delOperator(operator);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println(flag);
		}
	}

	private void getJsonShowOperators(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//传递JSON数据
		response.setContentType("application/x-json");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String pgButton = request.getParameter("page");
		int page = Integer.valueOf(pgButton);
		JSONObject json;
		PageInfo pageInfo;
		List<Map<String, Object>> list;
		try {
			pageInfo = DAOFactory.getOperatorPageInfoDAOInstance().getOperatorInfoPageInfo(page-1, 20);
			list = DAOFactory.getOperatorManageDAOInstance().findLimitOperatorInfo(page-1, 20);
			json = JsonDAOFactory.getJsonOperatorManageDAOInstance().getLimitOperatorInfo(list,pageInfo);
			out.println(json);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally{
			out.flush();
			out.close();
		}
	}

	private void addOperator(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		boolean flag = false;
		Operator operator = new Operator();
		operator.setOperatorname(request.getParameter("operatorname"));
		operator.setOperatorpassword(request.getParameter("operatorpassword"));
		out.println("<h3 style=\"color:red\">");
		try {
			if(!DAOFactory.getOperatorManageDAOInstance().isExist(operator)){
				flag = DAOFactory.getOperatorManageDAOInstance().addOperator(
					operator);
			}
			if (flag) {
				out.println("操作员创建成功。");
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"操作员已存在");
				out.println("操作员创建失败。");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</h3>");
		out.flush();
		out.close();
	}

}
