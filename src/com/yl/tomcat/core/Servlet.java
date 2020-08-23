package com.yl.tomcat.core;

import java.io.IOException;

public interface Servlet {
	public void init();
	
	public void service(ServletRequest request, ServletResponse response) throws IOException;

	public void doGet(ServletRequest request, ServletResponse response) throws IOException;
	
	public void doPost(ServletRequest request, ServletResponse response) throws IOException;
}
