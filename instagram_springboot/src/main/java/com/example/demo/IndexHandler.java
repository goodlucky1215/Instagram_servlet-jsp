package com.example.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class IndexHandler{

	@RequestMapping("mainview")
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HashMapBinder hmb = new HashMapBinder(req);
		return "index";
	}


}