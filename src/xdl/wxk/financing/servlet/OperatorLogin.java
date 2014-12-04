package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String name ="";
	private String pswd = "";
	private String path = "../login.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		String action_flag = request.getParameter("action_flag");
		
		if("switch_operator".equals(action_flag)){	//切换操作员
			switchOperator(request,response);
			
		}else if("switch_account".equals(action_flag)){	//切换帐户
			switchAccount(request,response);
			
		}else{	//从登录界面登录
			Operator operator = new Operator();
			this.name = request.getParameter("operator");
			this.pswd = request.getParameter("pswd");
			operator.setOperatorname(this.name);
			operator.setOperatorpassword(this.pswd);
			login(operator,request,response);
		}
	}

	private void switchAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Account account = null;
		Operator operator = null;
		int accountid = Integer.valueOf(request.getParameter("accountid"));
		int operatorid =Integer.valueOf(request.getParameter("operatorid"));
		try {
			try{
			account = DAOFactory.getAccountManageDAOInstance().findAccountById(accountid);
			}catch(SQLException e){
				e.printStackTrace();
			}
			operator = DAOFactory.getOperatorManageDAOInstance().findOperatorById(operatorid);
			LoginInfo info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator,account);
			request.getSession().setAttribute("info", info);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println("切换帐户");
		}
		
	}

	private void switchOperator(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//删除Session
		request.getSession().removeAttribute("info");
		//删除Cookie
		Cookie c[] = request.getCookies();
		if(c != null){
			for(int i=0;i<c.length;i++){
				if(c[i].getName().compareTo("name") == 0){
					c[i].setMaxAge(0);
					response.addCookie(c[i]);
				}
				if(c[i].getName().compareTo("pswd") == 0){
					c[i].setMaxAge(0);
					response.addCookie(c[i]);
				}
			}
		}
		
		String isManager = request.getParameter("isManager");
		if("true".equals(isManager)){	//是管理员
			int operatorid = Integer.valueOf(request.getParameter("operatorid"));
			try {
				Operator operator = DAOFactory.getOperatorManageDAOInstance().findOperatorById(operatorid);
				List<Account> accounts = DAOFactory.getOperatorManageDAOInstance().getAuthorizedAccounts(operator);
				boolean isAdmin = DAOFactory.getOperatorManageDAOInstance().isAdmin(operator);
				if(accounts != null && !accounts.isEmpty()){
					LoginInfo info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator,accounts.get(0));
					if(info.getPeroid() != null){
						request.getSession().setAttribute("info", info);
					}
				}else{
					LoginInfo tempInfo = new LoginInfo();
					tempInfo.setAccountid(-1);
					tempInfo.setAccountname("未初始化");
					tempInfo.setLevel(-1);
					tempInfo.setOperatorid(operator.getOperatorid());
					tempInfo.setOperatorname(operator.getOperatorname());
					tempInfo.setPeroid("未初始化");
					request.getSession().setAttribute("info", tempInfo);
				}
				//重新获取切换后的帐户树
				JSONArray accountTree;
				if(accounts != null && !accounts.isEmpty()){
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(accounts);
				}else{
					accountTree = null;
				}
				request.getSession().setAttribute("accountTree", accountTree);
				request.getSession().setAttribute("isManager", isAdmin);
				this.path = "../subject/main.jsp";
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				this.path = "../subject/main.jsp";
				request.getRequestDispatcher(this.path).forward(request, response);
			}
		}else{	//不是管理员
			Operator operator = new Operator();
			this.name = request.getParameter("operatorname");
			this.pswd = request.getParameter("operatorpassword");
			operator.setOperatorname(this.name);
			operator.setOperatorpassword(this.pswd);
			login(operator,request,response);
		}
		
	}

	private void login(Operator operator,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String remember = request.getParameter("remember");
		
		Cookie c[] = request.getCookies();
		if(c != null){
			for(int i=0;i<c.length;i++){
				if(c[i].getName().compareTo("name") == 0){
					operator.setOperatorname(URLDecoder.decode(c[i].getValue(),"utf-8"));
				}
				if(c[i].getName().compareTo("pswd") == 0){
					operator.setOperatorpassword(c[i].getValue());
				}
			}
		}
		
		
		boolean flag = false;
		try {
			flag = DAOFactory.getOperatorManageDAOInstance()
					.checkOperatorLogin(operator);
			if (flag) {
				LoginInfo info = null; 
				List<Account> accounts = null;
				//判断操作员是否是管理员
				JSONArray accountTree=null;
				boolean isManager = false;
				isManager = DAOFactory.getOperatorManageDAOInstance().isAdmin(operator);
				if(isManager){
					accounts = DAOFactory.getAccountManageDAOInstance().findAllAccounts();
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(accounts);
					info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator,accounts.get(0));
				}else{
					accounts = DAOFactory.getOperatorManageDAOInstance().getAuthorizedAccounts(operator);
					if(accounts!=null && !accounts.isEmpty()){
						accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(accounts);
						info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator,accounts.get(0));
					}else{
						info = new LoginInfo();
						info.setPeroid(null);
					}
				}
				List<Map<String, Object>> list = DAOFactory.getOperatorManageDAOInstance().findAllOperator();
				JSONArray operatorTree = JsonDAOFactory.getJsonOperatorManageDAOInstance().getOperatorForEasyTree(list);
				if(info.getAccountname() != null){
					request.getSession().setAttribute("info", info);
				}else{
					LoginInfo tempInfo = new LoginInfo();
					tempInfo.setAccountid(-1);
					tempInfo.setAccountname("未初始化");
					tempInfo.setLevel(-1);
					tempInfo.setOperatorid(operator.getOperatorid());
					tempInfo.setOperatorname(operator.getOperatorname());
					tempInfo.setPeroid("未初始化");
					request.getSession().setAttribute("info", tempInfo);
				}
				request.getSession().setAttribute("isManager", isManager);
				request.getSession().setAttribute("accountTree", accountTree);
				request.getSession().setAttribute("operatorTree", operatorTree);
				
				//如果钩选了记住按钮，设置cookie记住用户登陆
				if(remember != null){
					//解决中文编码出错问题
					this.name = URLEncoder.encode(name,"utf-8");
						
					Cookie n = new Cookie("name",this.name);
					Cookie p = new Cookie("pswd",this.pswd);
					//保存一个月(2592000秒)
					n.setMaxAge(2592000);
					p.setMaxAge(2592000);
					response.addCookie(n);
					response.addCookie(p);
				}
				
				this.path = "../subject/main.jsp";
				
			} else {
				if (this.name != null) {
					request.setAttribute("fail", "操作员名称或密码不正确");
				}else if(request.getSession().getAttribute("info") != null){//已经登陆
					{
						//为了刷新时更新切换操作员下的操作员树
						List<Map<String, Object>> list = DAOFactory.getOperatorManageDAOInstance().findAllOperator();
						JSONArray operatorTree = JsonDAOFactory.getJsonOperatorManageDAOInstance().getOperatorForEasyTree(list);
						request.getSession().setAttribute("operatorTree", operatorTree);
					}
					this.path = "../subject/main.jsp";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher(this.path).forward(request, response);
		}
	}
}
