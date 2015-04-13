package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.service.IDeleteDetailData;
import xdl.wxk.financing.service.impl.DeleteDetailDataImpl;
import xdl.wxk.financing.web.UI.FeedbackInformation;

public class DeleteDetailData extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String accountdetailid = request.getParameter("accountdetailid");
		
		/*PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML>");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>删除记录</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(accountdetailid+"删除成功");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();*/
		FeedbackInformation feedback = null;
		if(accountdetailid != null && !"".equals(accountdetailid)){
			IDeleteDetailData del = new DeleteDetailDataImpl();
			try {
				del.delete(accountdetailid);
				feedback = new FeedbackInformation("数据录入结果提示","数据删除成功","/subject/dataManage/dataRevise.jsp");
				System.out.println("删除成功");
			} catch (SQLException e) {
				System.out.println("异常");
				e.printStackTrace();
			}
		}else{
			feedback = new FeedbackInformation("数据录入结果提示","数据删除失败","/subject/dataManage/dataRevise.jsp");
			System.out.println("删除成功");
		}
		request.setAttribute("feedback",feedback);
		request.getRequestDispatcher("../subject/dataManage/feedback.jsp").forward(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
