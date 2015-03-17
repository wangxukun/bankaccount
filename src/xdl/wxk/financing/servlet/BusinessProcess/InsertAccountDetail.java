package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import xdl.wxk.financing.service.IInsertBill;
import xdl.wxk.financing.service.impl.InsertBillImpl;
import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.web.UI.FeedbackInformation;
import xdl.wxk.financing.web.formbean.DataEntryForm;

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
		/*private int accountdetailid;	//帐户祥情ID
		private int accountid;	//帐户ID
		private int number;	//凭证编号
		private int direction;	//借贷方向
		private double amount;	//金额
		private Date occurdate;	//发生日期
		private String summary;	//摘要
		private double balance;	//余额，数据库中没有这个字段
		private int groupid;	//分类账户ID
		private int freeze;	//是否已冻结(0表示未冻结，1表示冻结)
		*/AccountDetail detail = WebUtils.requestToBean(request, AccountDetail.class);
		DataEntryForm form = WebUtils.requestToBean(request, DataEntryForm.class);
/*		private String accountid;	//帐户ID
		private String direction;	//借贷方向
		private String amount;	//金额
		private String occurdate;	//发生日期
		private String summary;	//摘要
		private String groupid;	//分类账户ID
	*/	if(!form.validate()){
			request.setAttribute("form", form);
			request.getRequestDispatcher("../subject/dataManage/dataInput.jsp").forward(request, response);
			return ;
		}
		IInsertBill insertBill = new InsertBillImpl();
		FeedbackInformation feedback = null;
		try {
			if(insertBill.insertBill(detail)){
				feedback = new FeedbackInformation("数据录入结果提示","数据录入成功","/subject/dataManage/dataInput.jsp");
			}else{
				feedback = new FeedbackInformation("数据录入结果提示","此单位账户未初始化,初始化后才能录入数据","/subject/dataManage/dataInput.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			request.setAttribute("feedback",feedback);
			request.getRequestDispatcher("../subject/dataManage/feedback.jsp").forward(request, response);
		}
	}

}
