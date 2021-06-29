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
}
