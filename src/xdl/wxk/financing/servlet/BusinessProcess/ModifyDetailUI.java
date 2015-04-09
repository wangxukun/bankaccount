package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.vo.DataInfo;

public class ModifyDetailUI extends HttpServlet {

	/**
	 * 显示修改发生额数据UI
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
	//	http://localhost:8080/financing/servlet/ModifyDetailUI?accountdetailid=369&occurdate=2014-09-26&groupid=30&summary=摘要&direction=0&amount=5000.00
		AccountDetail detail = WebUtils.requestToBean(request, AccountDetail.class);
		//解决<a href>传递过来的中文参数乱码问题
		/*{	
			String name = initAccount.getAccountname();
			String summary = initAccount.getSummary();
			name = new String(name.getBytes("ISO-8859-1"),"utf-8");
			summary = new String(summary.getBytes("ISO-8859-1"),"utf-8");
			initAccount.setAccountname(name);
			initAccount.setSummary(summary);
		}*/
		request.setAttribute("reviseData", detail);
		System.out.println(detail);
		request.getRequestDispatcher("../subject/dataManage/modifyDetaildata.jsp").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
