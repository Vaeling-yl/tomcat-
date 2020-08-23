package com.yl.tomcat.core;

import java.io.IOException;
import java.io.PrintWriter;

public interface ServletResponse {
	/**
	 * 回送的方法
	 * @return
	 * @throws IOException 
	 */
	public PrintWriter getWrite() throws IOException;
	
	/**
	 * 重定向方法
	 * @param url
	 */
	public void sendRediret(String url);
}
