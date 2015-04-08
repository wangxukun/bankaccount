package xdl.wxk.financing.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.dao.factory.DAOFactory;
import xdl.wxk.financing.vo.Account;
import xdl.wxk.financing.vo.Operator;

public class ExistingPermissions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/plain; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		
		int operatorid = Integer.valueOf(request.getParameter("oid"));
		String operatorname = request.getParameter("oname");
		Operator ope = new Operator();
		ope.setOperatorid(operatorid);
		ope.setOperatorname(operatorname);
		try {
			//需要改进
			List<Account> accounts = DAOFactory.getOperatorManageDAOInstance().getAuthorizedAccounts(ope);
			if(accounts!=null && !accounts.isEmpty()){
				StringBuffer strBuf = new StringBuffer();
				Iterator<Account> iter = accounts.iterator();
				while(iter.hasNext()){
					int tempid = iter.next().getAccountid();
					int level = DAOFactory.getOperatorManageDAOInstance().findLevel(operatorid, tempid);
					if(level != -1){
						strBuf.append(tempid);
						strBuf.append(",");
					}
				}
				strBuf.setLength(strBuf.length()-1);
				
				PrintWriter out = response.getWriter();
				out.println(strBuf.toString());
				out.flush();
				out.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
