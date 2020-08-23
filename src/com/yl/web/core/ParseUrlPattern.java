package com.yl.web.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseUrlPattern {
	private String basePath = TomcatConstants.BASE_PATH;
	private static Map<String,String> urlPattern = new HashMap<String,String>();
	
	public ParseUrlPattern() {
		parse();
	}

	private void parse() {
		//获取服务中部署的所有项目
		File[] files = new File(basePath).listFiles();
		if (files== null || files.length <= 0) {//说明服务区中没有项目
			return;
		}
		String projectName = null;
		File childFile = null;
		
		for (File f1 : files) {//循环所有项目目录
			projectName = f1.getName();
			
			if (!f1.isDirectory()) {//如果不是目录说明他不是一个工程，则不管
				continue;
			}
			
			//获取这个项目下的web.xml文件
			childFile = new File(f1,"web.xml");
			if (!childFile.exists()) {//说明没有这个web.xml，可能是静态网站服务器，则不管
				continue;
			}
			//System.out.println(childFile);
			parseXml(projectName,childFile);
		}
	}

	@SuppressWarnings("unchecked")
	private void parseXml(String projectName, File childFile) {
		SAXReader read = new SAXReader();
		Document doc = null;
		
		try {
			doc = read.read(childFile);
			
			List<Element> mimes = doc.selectNodes("//servlet");
			
			//循环解析
			for (Element el : mimes) {
				urlPattern.put("/"+ projectName +el.selectSingleNode("url-pattern").getText().trim(), el.selectSingleNode("servlet-class").getText().trim());
				
			}
			System.out.println(this.urlPattern);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public static String getClass(String url) {
		return urlPattern.getOrDefault(url, null);
	}
	
	public Map<String,String> getUrlPattern(){
		return urlPattern;
		
	}
	

}
