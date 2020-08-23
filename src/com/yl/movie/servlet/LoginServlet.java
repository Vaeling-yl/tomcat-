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
		System.out.println("id="+request.getParameter("id"));
		System.out.println("name="+request.getParameter("name"));
		
		response.sendRediret("/movie/WebContent/index.html");

	}
}
