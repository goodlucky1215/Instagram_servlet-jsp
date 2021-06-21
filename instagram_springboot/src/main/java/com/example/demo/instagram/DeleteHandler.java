package com.example.demo.instagram;


import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class DeleteHandler{
	private static final String FORM_VIEW = "articleRead";
	@Autowired(required=true)
	private ArticleReadDao articlereaddao = null;

	@GetMapping("delete")
	private String processForm() { //get으로 받으면 다시 회원가입 창으로 이동
		return FORM_VIEW;
	}
	@PostMapping("delete")
	private String processSubmit(HttpServletRequest req) throws SQLException, UnsupportedEncodingException { //post로 받으면 정상으로 폼 전송 처리
		HashMapBinder hmb = new HashMapBinder(req);
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