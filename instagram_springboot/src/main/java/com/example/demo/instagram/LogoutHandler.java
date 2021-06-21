package com.example.demo.instagram;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class LogoutHandler{

	@RequestMapping("logout")
	public String process(HttpServletRequest req) throws IOException{
		HashMapBinder hmb = new HashMapBinder(req);
		HttpSession session = req.getSession(false);

		if(session!=null) {
			session.invalidate();
		}
		return "redirect:loginForm";
	}

}
