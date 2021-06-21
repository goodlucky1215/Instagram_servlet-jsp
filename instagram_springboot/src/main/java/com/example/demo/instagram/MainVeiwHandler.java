package com.example.demo.instagram;

import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class MainVeiwHandler{
	
	@Autowired(required=true)
	private LoginService loginservice = null;
	@RequestMapping("index")
	@ResponseBody
	public List<Map<String,Object>> process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		List<Map<String,Object>> articles = loginservice.articleList(user.getId(),"_");
	    //타입을 json으로 바꿔줘야됨
	    res.setContentType("application/json;charset=utf-8"); //=> 이렇게 한번에 처리해도 됨
		return articles;
	}

}
