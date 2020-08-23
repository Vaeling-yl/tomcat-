package com.yl.tomcat.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yl.web.core.TomcatConstants;
/**
 * 
 * @author Administrator
 *
 */
public class HttpServletRequest implements ServletRequest{
	private String url;//请求的资源地址
	private String method;//请求方式
	private String protoalVersion;//协议版本
	private InputStream is ;
	private Map<String,String> parameters = new HashMap<String,String>();
	private BufferedReader read;
	
	private HttpSession session = new HttpSession();
	private boolean checkJSessionId = false;
	private String jsessionid;
	private Cookie[] cookies;
	
	

	public HttpServletRequest(InputStream is) {
		this.is = is;
	}

	/**
	 * 解析请求头
	 */
	@Override
	public void parse() {
		try {
			read = new BufferedReader(new InputStreamReader(is));
			String line =null;
			List<String> headStrs =new ArrayList<String>();
			while((line = read.readLine()) != null&&!"".equals(line)) {
				headStrs.add(line);
			}
			parseFristLine(headStrs.get(0));//解析起始行
			parseParameter(headStrs);		//解析获取参数
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/**
 * 解析起始行
 * @param str
 */
	private void parseFristLine(String str) {
		String[] strs = str.split(" ");
		this.method = strs[0];
		if (strs[1].contains("?")) {//说明没有参数
			this.url = strs[1].substring(0,strs[1].indexOf("?"));
		}else {
			this.url = strs[1];
		}
		this.protoalVersion = strs[2];
		
		//System.out.println(this.method +"\t"+this.url+"\t"+this.method);
	}
	
/**
 * 解析获取参数
 * @param headStrs
 */
	private void parseParameter(List<String> headStrs) {
		//处理请求地址中的参数
		String str = headStrs.get(0).split(" ")[1];
		if (str.contains("?")) {
			
			str = str.substring(str.indexOf("?") + 1);
			String[] params = str.split("&");
			String[] temp;
			
			for (String param : params) {
				temp = param.split("=");
				this.parameters.put(temp[0], temp[1]);
			}
		}
		
		if (TomcatConstants.REQUEST_METHOD_POST.equals(this.method)) {//说明是post请求
			//System.out.println(headStrs);
			int len = 0;
			for (String head : headStrs) {
				if (head.contains("Content-Length:")) {
					//System.out.println(head);
					len = Integer.parseInt(head.substring(head.indexOf(":")+1).trim());
					break;
				}
			}
			
			if (len <= 0) {//
				return;
			}
			//
			
			try {
				char[] ch = new char[1024*10];
				int count = 0,total = 0;//
				StringBuffer sbf = new StringBuffer(1024 * 10);
				while ((count = read.read(ch)) > 0 ) {
					sbf.append(ch,0,count);
					total += count;
					if (total >= len) {
						break;
					}
				}
				
				str = URLDecoder.decode(sbf.toString(),"utf-8");
				str = str.substring(str.indexOf("?") + 1);
				String[] params = str.split("&");
				String[] temp;
				for (String param : params) {
					temp = param.split("=");
					this.parameters.put(temp[0], temp[1]);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(this.parameters);
	}

	

	@Override
	public String getParameter(String key) {
		return this.parameters.getOrDefault(key, null);
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public HttpSession getSession() {
		return this.session;
	}

	@Override
	public Cookie[] getCookies() {
		return this.cookies;
	}

	@Override
	public boolean checkJSessionId() {
		return this.checkJSessionId;
	}

	@Override
	public String getJSessionId() {
		return this.jsessionid;
	}

	public String getProtoalVersion() {
		return protoalVersion;
	}

	public void setProtoalVersion(String protoalVersion) {
		this.protoalVersion = protoalVersion;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public HttpSession getsession() {
		return session;
	}

	public void setsession(HttpSession session) {
		this.session = session;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	
}
