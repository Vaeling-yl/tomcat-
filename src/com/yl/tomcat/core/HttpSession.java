package com.yl.tomcat.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 往Session中存值
 * @author Administrator
 *
 */
public class HttpSession {
	public Map<String, Object> session = new HashMap<String, Object>();
	private String jsessionId = null;

	/**
	 * 往session存值
	 * 
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Object value) {
		this.session.put(key, value);
		//
		
		//更新最后一次访问的时间
	}
	
	/**
	 *从session中根据键获取对应的值
	 * @param key
	 * @return
	 */
	public Object getAttribute(String key) {
		if (!session.containsKey(key)) {
			return null;
		}
		return session.get(key);
	}
	
	public void setJSession(String jsession) {
		this.jsessionId = jsession;
	}
}
