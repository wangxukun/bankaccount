package xdl.wxk.financing.servlet.BusinessProcess;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import xdl.wxk.financing.service.IUpdateInitBill;
import xdl.wxk.financing.service.impl.UpdateInitBill;
import xdl.wxk.financing.tools.WebUtils;
import xdl.wxk.financing.web.UI.FeedbackInformation;
import xdl.wxk.financing.web.formbean.DataInitForm;

public class ModifyInitData extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		//从客户端收到数据并封装到InitAccount对象中，用于更新数据
		xdl.wxk.financing.vo.InitAccount init = WebUtils.requestToBean(request, xdl.wxk.financing.vo.InitAccount.class);
		//从客户端收到数据并封装到DataInitForm对象中，用于验证数据合法性
		DataInitForm form = WebUtils.requestToBean(request, DataInitForm.class);
		if(!form.validate()){
			request.setAttribute("initData", form);
			request.setAttribute("form", form);
			request.getRequestDispatcher("../subject/dataManage/modifyInitdata.jsp").forward(request, response);
			return ;
		}
		IUpdateInitBill updateInitBill = new UpdateInitBill();
		FeedbackInformation feedback = null;
		try {
			boolean flag = updateInitBill.updateInitBill(init);
			if(flag){
				feedback = new FeedbackInformation("修改结果提示","初始化数据修改成功","/subject/dataManage/dataInit.jsp");
				
			}else{
				feedback = new FeedbackInformation("修改结果提示","初始化数据修改失败","/subject/dataManage/dataInit.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			request.setAttribute("feedback", feedback);
			request.getRequestDispatcher("../subject/dataManage/feedback.jsp").forward(request, response);
		}
	}

}
