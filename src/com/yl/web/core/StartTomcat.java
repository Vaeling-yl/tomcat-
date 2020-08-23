package com.yl.web.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartTomcat {
	public static void main(String[] args) {
		try {
			new StartTomcat().start();//启动服务器
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private void start() throws IOException {
			int port = Integer.parseInt(ReadConfig.getInstance().getProperty("port"));//
			
			ServerSocket ssk = new ServerSocket(port);
			
			System.out.println("服务器启动成功 端口为："+port);
			
			//扫描解析每个部署到服务器下的项目中的web.xml文件   之前放在项目目录下
			new ParseUrlPattern();
			
			new ParseXml();
			//创建一个线程池
			ExecutorService serviceTread = Executors.newFixedThreadPool(20);
			
			//监听客户端的请求
			Socket sk = null;
			while(true) {
				sk = ssk.accept();
				//交给线程池去处理
				serviceTread.submit(new ServerService(sk));
			}
	}
}
