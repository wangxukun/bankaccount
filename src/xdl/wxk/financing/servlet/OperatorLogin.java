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
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String name =null;
	private String pswd = null;
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
		//操作员登录
		
		if("switch".equals(action_flag)){	//切换操作员
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
					LoginInfo info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator);
					boolean isAdmin = DAOFactory.getOperatorManageDAOInstance().isAdmin(operator);
					request.getSession().setAttribute("info", info);
					request.getSession().setAttribute("isManager", isAdmin);
					this.path = "../subject/main.jsp";
					System.out.println(info);
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					System.out.println(isManager);
					this.path = "../subject/main.jsp";
					request.getRequestDispatcher(this.path).forward(request, response);
				}
			}else{	//不是管理员
				
			}
		}else{
			Operator operator = new Operator();
			this.name = request.getParameter("operator");
			this.pswd = request.getParameter("pswd");
			operator.setOperatorname(this.name);
			operator.setOperatorpassword(this.pswd);
			login(operator,request,response);
		}
	}

	private void login(Operator operator,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String remember = request.getParameter("remember");
		
//		this.path = "../login.jsp";
		
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
				System.out.println(operator);
				LoginInfo info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(operator);
				System.out.println(info);
				//判断操作员是否是管理员
				JSONArray accountTree;
				boolean isManager = false;
				isManager = DAOFactory.getOperatorManageDAOInstance().isAdmin(operator);
				if(isManager){
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(DAOFactory.getAccountManageDAOInstance().findAllAccounts());
					System.out.println("是管理员");
				}else{
					
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(DAOFactory.getOperatorManageDAOInstance().getAuthorizedAccounts(operator));
				}
				List<Map<String, Object>> list = DAOFactory.getOperatorManageDAOInstance().findAllOperator();
				JSONArray operatorTree = JsonDAOFactory.getJsonOperatorManageDAOInstance().getOperatorForEasyTree(list);
				request.getSession().setAttribute("info", info);
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
