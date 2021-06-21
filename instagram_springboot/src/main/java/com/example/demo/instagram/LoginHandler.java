package com.example.demo.instagram;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class LoginHandler{
	private static final String FORM_VIEW = "loginForm";
	
	@Autowired(required=true)
	private LoginService loginservice = null;
	@GetMapping("loginForm")
	private String processForm() {
		return FORM_VIEW;
	}
	@PostMapping("loginForm")
	private String processSubmit(MemberVO memverVO,HttpServletRequest req) throws Exception {
		//  사용자가 임의의 name 값을 개발자도구에서 변경시 에러가 날 수 있기 때문이다.
		String id = memverVO.getId()!=null ? trim(memverVO.getId()) : null;
		String password = memverVO.getPassword()!=null ? trim(memverVO.getPassword()) : null;
		//아이디나 비밀번호 입력 확인
		if(id==null||password==null) {
			//사용자 임의로 id이름을 변경시
			req.setAttribute("message","아이디나 비밀번호를 입력해주세요!");
			return FORM_VIEW;
		}
		//둘 다 입력했다면 이제 찾기 시작
		try {
			User user = loginservice.login(id, password);
			req.getSession().setAttribute("authUser", user); //유저 아이디와 이름 저장
			//res.sendRedirect("mainview.do");
			return"redirect:mainview";
		} catch (LoginFailException e) {
			req.setAttribute("message","아이디나 비밀번호가 틀렸습니다.");			
			return  FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str.trim().length() == 0 ? null:str.trim(); //문자열의 첫 시작의 공백을 제거해줌.
	}

}
