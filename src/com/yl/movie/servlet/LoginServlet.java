package com.yl.movie.servlet;
import com.yl.tomcat.core.HttpServlet;
import com.yl.tomcat.core.ServletRequest;
import com.yl.tomcat.core.ServletResponse;

public class LoginServlet extends HttpServlet{
	
	@Override
	public void doGet(ServletRequest request, ServletResponse response) {
		doPost(request, response);
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) {
		System.out.println("name="+request.getParameter("name"));
		System.out.println("pwd="+request.getParameter("pwd"));
		
		response.sendRediret("WebContent/index.html");

	}
}
