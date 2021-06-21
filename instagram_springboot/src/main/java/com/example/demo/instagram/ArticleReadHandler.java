package com.example.demo.instagram;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class ArticleReadHandler{
	@Autowired(required=true)
	private ArticleReadDao articlereaddao = null;
	@RequestMapping("read.do")
	public String process(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, SQLException {
		HashMapBinder hmb = new HashMapBinder(req);
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		Map<String, Object> articleread = articlereaddao.articleRead(user.getId(), articleNum);
		System.out.println(articleread.get("contentText"));
		req.setAttribute("articleread",articleread);
		return "articleRead";
	}
}
