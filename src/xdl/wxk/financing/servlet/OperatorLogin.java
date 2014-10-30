package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import xdl.wxk.financing.abstraction.OperatorRelation;
import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.json.factory.JsonDAOFactory;
import xdl.wxk.financing.vo.LoginInfo;
import xdl.wxk.financing.vo.Operator;

public class OperatorLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			login(request,response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		Operator operator = new Operator();
		String name = request.getParameter("operator");
		String pswd = request.getParameter("pswd");
		String remember = request.getParameter("remember");
		operator.setOperatorname(name);
		operator.setOperatorpassword(pswd);
		String path = "../login.jsp";
		
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
				OperatorRelation relation = DAOFactory.getOperatorManageDAOInstance().Relation(operator);
				
				LoginInfo info = DAOFactory.getOperatorManageDAOInstance().getLoginInfo(relation);
				
				//判断操作员是否是管理员
				JSONArray accountTree;
				if(DAOFactory.getOperatorManageDAOInstance().checkOperatorLevel(operator, 100)){
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(DAOFactory.getAccountManageDAOInstance().findAllAccounts());
				}else{
					accountTree = JsonDAOFactory.getJsonAccountManageDAOInstance().getAccountsForEasyTree(relation.getAccounts());
				}
				List<Map<String, Object>> list = DAOFactory.getOperatorManageDAOInstance().findAllOperator();
				JSONArray operatorTree = JsonDAOFactory.getJsonOperatorManageDAOInstance().getOperatorForEasyTree(list);
				request.getSession().setAttribute("info", info);
				request.getSession().setAttribute("accountTree", accountTree);
				request.getSession().setAttribute("operatorTree", operatorTree);
				
				//如果钩选了记住按钮，设置cookie记住用户登陆
				if(remember != null){
					//解决中文编码出错问题
					name = URLEncoder.encode(name,"utf-8");
						
					Cookie n = new Cookie("name",name);
					Cookie p = new Cookie("pswd",pswd);
					//保存一个月(2592000秒)
					n.setMaxAge(2592000);
					p.setMaxAge(2592000);
					response.addCookie(n);
					response.addCookie(p);
				}
				
				path = "../subject/main.jsp";
				
			} else {
				if (name != null) {
					request.setAttribute("fail", "操作员名称或密码不正确");
				}else if(request.getSession().getAttribute("info") != null){//已经登陆
					path = "../subject/main.jsp";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.getRequestDispatcher(path).forward(request, response);
		}
	}
}
