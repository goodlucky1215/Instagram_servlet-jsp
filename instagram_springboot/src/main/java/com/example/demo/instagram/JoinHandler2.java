package com.example.demo.instagram;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.util.HashMapBinder;

@Controller
@RequestMapping("/mystudy/instagram/*")
public class JoinHandler2{
	private static final String FORM_VIEW = "joinForm";
	
	@Autowired(required=true)
	private JoinService joinService = null;

	@GetMapping("join")
	private String processForm() { //get으로 받으면 다시 회원가입 창으로 이동
		return FORM_VIEW;
	}
	
	@PostMapping("join")
	private String processSubmit(MemberVO memverVO,HttpServletRequest req) throws SQLException, UnsupportedEncodingException { //post로 받으면 정상으로 폼 전송 처리
		HashMapBinder hmb = new HashMapBinder(req);
		String viewPage = null;
		
		Map<String, Boolean> errors = new HashMap<>(); //errors를 담을 map을 만들어줌.
		req.setAttribute("errors", errors); //req에다가 errors라는 이름으로 객체 errors를 저장해두는 것임
		
		memverVO.validate(errors); //값이 빈값인지 아닌지 확인하는 메소드

		if(!errors.isEmpty()) { //만약에 errors안에 값이 존재하면 -빈 값이 있다는 것(혹은 비번!=확인비번)이므로 다시 회원가입 폼으로 간다.
			return FORM_VIEW;
		}
		
		//errors가 비어있다면, 에러가 없다는 것이므로 로그인 화면으로 넘어 갈 준비를 한다.
		try {
			joinService.join(memverVO);//회원가입 dao에 값 저장하기위해서
			return "joinSuccess";
		} catch (DuplicateIdException e) {
		//그러나 만약에 이미 존재하는 아이디로 한다면 다시 회원가입 폼으로 돌아간다.
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

}
