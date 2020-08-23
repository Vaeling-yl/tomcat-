package com.yl.tomcat.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yl.web.core.TomcatConstants;

public class HttpServlet implements Servlet {
	
	

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	public void service(ServletRequest request,ServletResponse response) throws IOException {
		switch (request.getMethod()) {
		case TomcatConstants.REQUEST_METHOD_GET:doGet(request,response);
			break;
		case TomcatConstants.REQUEST_METHOD_POST:doPost(request,response);
			break;
		}
	}


	@Override
	public void doGet(ServletRequest request, ServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}
