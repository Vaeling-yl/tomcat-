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
		System.out.println(urlStr + "----------------urlStr");
		String projectName = urlStr.substring(0,urlStr.indexOf("/"));
		System.out.println(projectName + "----------------projectName");
		
		ServletResponse response = new HttpServletResponse("/"+ projectName,os);
		
		//是不是动态资源地址
		String clazz = ParseUrlPattern.getClass(url);//如果能取到处理类，则说明是动态资源
		System.out.println(clazz +"-----------clazz");
		if (clazz == null || "".equals(clazz) ) {//当静态资源访问
			response.sendRediret(url);
			return;
		}
		
		/**
		 * 处理动态资源
		 * 我的规则：所有的动态资源处理代码-》servlet代码必须放到当前项目下的bin目录
		 */
		URLClassLoader loader = null;//类加载器
		URL classPath = null;//要加载的这个类的地址
		
		try {
			classPath = new URL("file",null,TomcatConstants.BASE_PATH+"\\"+ projectName +"\\bin");
		
			//创建一个类加载器，告诉它到这个路径下加载类
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
