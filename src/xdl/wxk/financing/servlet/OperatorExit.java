package xdl.wxk.financing.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OperatorExit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		
		request.getRequestDispatcher("../login.jsp").forward(request, response);
	}

}
