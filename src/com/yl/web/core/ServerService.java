package com.yl.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import com.yl.tomcat.core.HttpServletRequest;
import com.yl.tomcat.core.HttpServletResponse;
import com.yl.tomcat.core.Servlet;
import com.yl.tomcat.core.ServletRequest;
import com.yl.tomcat.core.ServletResponse;


public class ServerService implements Runnable {
	private Socket sk;
	private OutputStream os;
	private InputStream is;
	
	public ServerService(Socket sk) {
		this.sk = sk;
	}

	@Override
	public void run() {
		try{
			this.is = sk.getInputStream();
			this.os = sk.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//处理请求
		ServletRequest request = new HttpServletRequest(is);
		//解析请求
		request.parse();
		//处理请求
		//请求的servlet是动态还是静态资源
		//如果是要映射到动态资源，则肯定会配置到项目的web.xml文件中
		//所以，我们必须在服务器启动时扫描每个项目下的web.xml
		String url = request.getUrl();
		
		String urlStr = url.substring(1);
		System.out.println(url + "----------------url");
		String projectName = urlStr.substring(0,url.indexOf("/"));
		
		ServletResponse response = new HttpServletResponse(os);
		
		//是不是动态资源地址
		String clazz = ParseUrlPattern.getClass(url);
		System.out.println(clazz +"-----------clazz");
		if (clazz == null || "".equals(clazz) ) {
			response.sendRediret(url);
			return;
		}
		
		/**
		 * 
		 */
		URLClassLoader loader = null;//
		URL classPath = null;//
		//
		System.out.println(projectName + "----------------projectName");
		
		try {
			classPath = new URL("file",null,TomcatConstants.BASE_PATH+"\\"+ projectName +"\\bin");
		
			//
			loader = new URLClassLoader(new URL[] {classPath});
			
			//
			Class<?> cls = loader.loadClass(clazz);
			
			Servlet servlet = (Servlet) cls.newInstance();//
			
			//
			servlet.service(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
