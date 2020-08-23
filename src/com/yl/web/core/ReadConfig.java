package com.yl.web.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * 采用单例模式读取配置文件
 *
 */
public class ReadConfig extends Properties {

	private static final long serialVersionUID = -1241313531531511L;
	public static ReadConfig instance = new ReadConfig();
	
	private  ReadConfig() {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("web.properties")){
			load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ReadConfig getInstance() {
		return instance;
	}
}
