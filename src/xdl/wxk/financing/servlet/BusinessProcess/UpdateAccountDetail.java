package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xdl.wxk.financing.service.IUpdateDetail;
import xdl.wxk.financing.service.impl.UpdateDetail;
import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.vo.AccountDetail;
import xdl.wxk.financing.web.UI.FeedbackInformation;
import xdl.wxk.financing.web.formbean.DataEntryForm;
import xdl.wxk.financing.web.formbean.DataResivesForm;

public class UpdateAccountDetail extends HttpServlet{

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
		private Date updatetime; //录入日期
		private String summary;	//摘要
		private String balance;	//余额，数据库中没有这个字段
		private int groupid;	//分类账户ID
		private int freeze;	//是否已冻结(0表示未冻结，1表示冻结)
		*/AccountDetail detail = WebUtils.requestToBean(request, AccountDetail.class);
		DataResivesForm form = WebUtils.requestToBean(request, DataResivesForm.class);
/*		private String accountid;	//帐户ID
		private String direction;	//借贷方向
		private String amount;	//金额
		private String occurdate;	//发生日期
		private String summary;	//摘要
		private String groupid;	//分类账户ID
	*/	
		
		if(!form.validate()){
			request.setAttribute("form", form);
			request.getRequestDispatcher("/servlet/ModifyDetailUI").forward(request, response);
			return ;
		}
		IUpdateDetail update = new UpdateDetail();
		FeedbackInformation feedback = null;
		try {
			if(update.resivesDetail(detail)){
				feedback = new FeedbackInformation("数据修改结果提示","数据修改成功","/subject/dataManage/dataRevise.jsp");
			}else{
				feedback = new FeedbackInformation("数据修改结果提示","数据修改失败","/subject/dataManage/dataRevise.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			request.setAttribute("feedback",feedback);
			request.getRequestDispatcher("../subject/dataManage/feedback.jsp").forward(request, response);
		}
		
		System.out.println(detail);
	}

}
