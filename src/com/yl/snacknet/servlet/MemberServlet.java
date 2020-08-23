package com.yl.snacknet.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.yl.tomcat.core.HttpServlet;
import com.yl.tomcat.core.ServletRequest;
import com.yl.tomcat.core.ServletResponse;

public class MemberServlet extends HttpServlet{
	
	@Override
	public void doGet(ServletRequest request, ServletResponse response) throws IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) throws IOException {
		String name=request.getParameter("name");
		String	pwd=request.getParameter("pwd");
		
		PrintWriter out = null;
		
		out = response.getWrite();
		out.print("name=" + name +"pwd="+pwd);
		out.flush();
	}
}