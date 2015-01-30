package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertAccountDetail extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;");
		response.setCharacterEncoding("utf-8");
		
		String occurdate = request.getParameter("occurdate");
		String summary = request.getParameter("summary");
		String direction = request.getParameter("direction");
		String amount = request.getParameter("amount");
		String accountid = request.getParameter("accountid");
		String groupid = request.getParameter("groupid");
		System.out.println("occurdate = "+occurdate);
		System.out.println("summary = "+summary);
		System.out.println("direction = "+direction);
		System.out.println("amount = "+amount);
		System.out.println("accountid = "+accountid);
		System.out.println("groupid = "+groupid);
	}

}
