package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.vo.Account;

public class AccountManage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String action_flag = request.getParameter("action_flag");

		if ("createAccount".equals(action_flag)) {
			createAccount(request, response);
		}
		if ("removeAccount".equals(action_flag)) {
			removeAccount(request, response);
		}
	}

	private void removeAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		int accountid = Integer.parseInt(request.getParameter("accountid"));
		System.out.println(accountid);
		try {
			flag = DAOFactory.getAccountManageDAOInstance().RemoveAccountById(accountid);
			if(flag){
				response.setStatus(HttpServletResponse.SC_CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			request.getRequestDispatcher("OperatorLogin").forward(request,
					response);
		}
	}

	private void createAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		Account account = new Account();
		String accountname = request.getParameter("accountname");
		if("".equalsIgnoreCase(accountname)){
			accountname = null;
		}
		account.setAccountname(accountname);
		System.out.println(accountname);
		account.setParentid(Integer.parseInt(request.getParameter("parentid")));
		try {
			flag = DAOFactory.getAccountManageDAOInstance().IsExistAccount(account);
			if (flag) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				flag = DAOFactory.getAccountManageDAOInstance().CreateAccount(
						account);
				if(flag){
					response.setStatus(HttpServletResponse.SC_CREATED);
				}else{
					response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher("OperatorLogin").forward(request,
					response);
		}
	}
}
