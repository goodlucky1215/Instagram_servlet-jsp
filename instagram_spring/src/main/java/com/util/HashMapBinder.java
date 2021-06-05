package com.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


public class HashMapBinder {
	HttpServletRequest request = null;
	public HashMapBinder() {}
	public HashMapBinder(HttpServletRequest request) throws UnsupportedEncodingException {
		this.request = request;
		request.setCharacterEncoding("UTF-8");
	}
	public void bind(Map<String,Object> target) {
		Enumeration en = request.getParameterNames();
		//<input type="text" name="mem_id" 
	      while(en.hasMoreElements()) {
	          String key = (String)en.nextElement();
	          target.put(key, HangulConversion.toUTF(request.getParameter(key)));//※주의!! 상수가 아닌 변수를 사용!!!
	       }
	    }//////end of bind
}
