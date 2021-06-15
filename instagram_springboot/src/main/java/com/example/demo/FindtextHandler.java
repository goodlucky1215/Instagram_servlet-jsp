package com.example.demo;

import java.util.List;
import java.util.Map;

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
public class FindtextHandler{
	
	@Autowired(required=true)
	private LoginService loginservice = null;
	
	@RequestMapping("findtext")
	@ResponseBody
	public List<Map<String,Object>> process(HttpServletRequest req, HttpServletResponse res) throws Exception{
		System.out.println("검색한 파일 찾기");
		res.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("utf-8");
		String text = req.getParameter("findtext");
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		List<Map<String,Object>> articles = loginservice.articleList(user.getId(),text);

        //DTO 타입의 어레이리스트를 json 형태로 바꿔주는 구문(라이브러리 필수, zip->jar 확장자명 꼭 확인)
        //String gson = new Gson().toJson(articles);
		//둘 다 입력했다면 이제 찾기 시작
        //ajax로 리턴해주는 부분
        //res.getWriter().write(gson);
		return articles;
	}

}
