package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.tools.WebUtils;

public class ModifyInitDataUI extends HttpServlet {

	/**
	 * 显示初始化数据UI
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		xdl.wxk.financing.vo.InitAccount initAccount = WebUtils.requestToBean(request, xdl.wxk.financing.vo.InitAccount.class);
		//解决<a href>传递过来的中文参数乱码问题
		{	
			String name = initAccount.getAccountname();
			String summary = initAccount.getSummary();
			name = new String(name.getBytes("ISO-8859-1"),"utf-8");
			summary = new String(summary.getBytes("ISO-8859-1"),"utf-8");
			initAccount.setAccountname(name);
			initAccount.setSummary(summary);
		}
		request.setAttribute("initData", initAccount);
		request.getRequestDispatcher("../subject/dataManage/modifyInitdata.jsp").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
