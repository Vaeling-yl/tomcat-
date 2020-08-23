package com.yl.tomcat.core;

public interface ServletRequest {
	
	
	/**
	 * 获取请求的方法
	 * @param key
	 * @return
	 */
	public String getParameter(String key);
	/**
	 * 解析请求的方法
	 */
	public void parse();
	/**
	 * 获取解析出来的请求地址
	 * @return
	 */
	public String getUrl();
	/**
	 * 获取方式
	 * @return
	 */
	public String getMethod();
	/**
	 * 获取Session
	 * @return
	 */
	public HttpSession getSession();
	/**
	 * 获取Cookie
	 * @return
	 */
	public Cookie[] getCookies();
	
	/**
	 * 检查判断是否有JSession
	 * @return
	 */
	public boolean checkJSessionId();
	/**
	 * 获取用户的JSession
	 * @return
	 */
	public String getJSessionId();

}
