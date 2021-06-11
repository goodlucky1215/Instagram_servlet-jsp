package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class DeleteHandler{
	private static final String FORM_VIEW = "articleRead";
	@Autowired(required=true)
	private ArticleReadDao articlereaddao = null;

	@RequestMapping("delete")
	public ModelAndView process(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, SQLException{
		HashMapBinder hmb = new HashMapBinder(req);
		ModelAndView mav = new ModelAndView();
		String viewPage = null;
		if(req.getMethod().equalsIgnoreCase("GET")) {//보내는 방식이 get일때,equalsIgnoreCase이것은 대소문자 구분 안함.
			viewPage = processForm(req,res); 
		}else if(req.getMethod().equalsIgnoreCase("POST")) { //보내는 방식이 post일때,equalsIgnoreCase이것은 대소문자 구분 안함.
			viewPage = processSubmit(req,res);
		}else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		if(viewPage != null) {
			mav.setViewName(viewPage);
		}
		return mav;
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) { //get으로 받으면 다시 회원가입 창으로 이동
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws SQLException { //post로 받으면 정상으로 폼 전송 처리
		HttpSession session = req.getSession();  
		User user = (User) session.getAttribute("authUser");
		String noVal = req.getParameter("no");
		int articleNum = Integer.parseInt(noVal);
		System.out.println(articleNum+"  "+articlereaddao.getDeleteUser(articleNum));
		if(!user.getId().equals(articlereaddao.getDeleteUser(articleNum))) {
			System.out.println("삭제 안돼");
			//아니라면 삭제 못함!
			Map<String, Object> articleread = articlereaddao.articleRead(user.getId(), articleNum);
			System.out.println(articleread.get("contentText"));
			req.setAttribute("articleread",articleread);
			req.setAttribute("error", "자신의 게시물만 삭제할 수 있습니다!");
			return FORM_VIEW;
		}else {
			System.out.println("돼");
			articlereaddao.getDelete(articleNum);
			return "redirect:mainview";	
		}
	}
}