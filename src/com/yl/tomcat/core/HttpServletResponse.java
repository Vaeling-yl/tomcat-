package com.yl.tomcat.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yl.web.core.ParseXml;
import com.yl.web.core.ReadConfig;
import com.yl.web.core.TomcatConstants;


public class HttpServletResponse implements ServletResponse {

	private OutputStream os;
	private String basePath = TomcatConstants.BASE_PATH;//基址路径
	private String projectName;
	
	

	public HttpServletResponse(OutputStream os, String projectName) {
		this.os = os;
		this.projectName = projectName;
	}

	public HttpServletResponse(OutputStream os) {
		this.os = os;
	}

	@Override
	public PrintWriter getWrite() throws IOException {
		String msg = "HTTP/1.1 200 OK\r\nContent-Type:text/html;charset=utf-8\r\n\r\n";
		os.write(msg.getBytes());
		os.flush();
		return new PrintWriter(os);
	}

	@Override
	public void sendRediret(String url) {
		if (url == null || "".equals(url)) {
			error404(url);
			return;
		}
		if (!url.startsWith(projectName)) {//
			url = projectName +"/" + url;
		}
		
		if (url.indexOf("/") == url.lastIndexOf("/") && url.indexOf("/") < url.length()) {
			send302(url);
		}else {
			if (url.endsWith("/")) {
				String defaultPath = ReadConfig.getInstance().getProperty("default");
			
				File f1 = new File(basePath,url.substring(1).replace("/", "\\")+defaultPath);
				if (!f1.exists()) {
					error404(url);
					return;
				}
				send200(readFile(f1), defaultPath.substring(defaultPath.lastIndexOf(".") + 1).toLowerCase());
			}else {
				File f1 = new File(basePath,url.substring(1).replace("/", "\\"));
				if (!f1.exists()) {
					error404(url);
					return;
				}
				send200(readFile(f1), url.substring(url.lastIndexOf(".") + 1).toLowerCase());
			}
		}
		
	}
	/**
	 * 
	 * @param f1
	 * @return
	 */
	private byte[] readFile(File f1) {
		try(FileInputStream fis = new FileInputStream(f1)){
			byte[] bt = new byte[fis.available()];
			fis.read(bt);
			return bt;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void send302(String url) {
		try {
			String msg = "HTTP/1.1 302 OK\r\nContent-Type:text/html;charset=utf-8\r\nLocation" + url 
					+ "\r\n\r\n";
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void send200(byte[] bt,String extension) {
		try {
			String contentType = ParseXml.getContentType(extension);
			System.out.println(ParseXml.getMap());
			String msg = "HTTP/1.1 200 OK\r\nContent-Type:"+contentType+"\r\nContent-Length"+bt.length+"\r\n\r\n";
			os.write(msg.getBytes());
			os.write(bt);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void error404(String url) {
		try {
			String contentType = "text/html;charset=utf-8";
			String errInfo = "<h1>HTTP Status 404 -"+ url +"</h1>";
			String msg = "HTTP/1.1 404 File Not Fount\r\nContent-Type:"+contentType+"\r\nContent-Length"+errInfo.length()+"\r\n\r\n"+errInfo;
			os.write(msg.getBytes());
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
